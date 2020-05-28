package com.ls.zy.wordreader.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * word文档处理的相应工具类
 */
public class WordFileUtil {


	private static Logger logger = LoggerFactory.getLogger(WordFileUtil.class);

	final static String WORD_PREFIX = "${";

	final static String PICTURE_PREFIX = "$P{";

	final static String SUFFIX = "}";

	/**
	 * DOC宽度
	 */
	final static Double DOC_WIDTH = 435d;

	/**
	 * 使用Map替换word文档中占位符对应的字段
	 * ${}替换对应文字
	 * $P{}替换对应图片
	 * @param in
	 * @param os
	 * @param reuleMap
	 */
	public static void replaceDocx(InputStream in,OutputStream os,Map<String, String> reuleMap) throws Exception{
		try {
			XWPFDocument document = new XWPFDocument(in);
			/**
			 * 替换段落中的指定文字
			 * 段落上支持替换
			 */
			Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
			while (itPara.hasNext()) {
				XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
				replaceInParagraph(paragraph,reuleMap);
			}

			/**
			 * 替换表格中的指定文字
			 */
			Iterator<XWPFTable> itTable = document.getTablesIterator();
			while (itTable.hasNext()) {
				XWPFTable table = (XWPFTable) itTable.next();
				int count = table.getNumberOfRows();
				for (int i = 0; i < count; i++) {
					XWPFTableRow row = table.getRow(i);
					List<XWPFTableCell> cells = row.getTableCells();
					for (XWPFTableCell cell : cells) {
						List<XWPFParagraph> paragraphs= cell.getParagraphs();
						for(XWPFParagraph paragraph:paragraphs){
							replaceInParagraph(paragraph,reuleMap);
						}
					}
				}
			}
			document.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 替换段落中的内容
	 * @param paragraph
	 * @param reuleMap
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	static void replaceInParagraph(XWPFParagraph paragraph,Map<String,String> reuleMap) throws FileNotFoundException, IOException, InvalidFormatException {
		Set<String> set = reuleMap.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			List<XWPFRun> run = paragraph.getRuns();
			for (int i = 0; i < run.size(); i++) {
				if (run.get(i).getText(run.get(i).getTextPosition()) != null &&
						run.get(i).getText(run.get(i).getTextPosition()).equals(key)) {
					/**
					 * 参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始
					 * 就可以把原来的文字全部替换掉了
					 */
					//处理文字
					if(isWord(key)){
						if(!StringUtil.isBlank(reuleMap.get(key))) {
							run.get(i).setText(reuleMap.get(key), 0);
						}else{
							run.get(i).setText("", 0);
						}
					}
					//处理图片
					if(isPicture(key)){
						//删除原来位置的占位符
						run.get(i).setText(null, 0);
						String picturePath = reuleMap.get(key);
						if(!StringUtil.isBlank(picturePath)) {
							BufferedImage sourceImg = null;
							try {
								FileInputStream fis = new FileInputStream(picturePath);
								//ImageIOD读完实际已经关闭了fis,需要重新读取
								sourceImg = ImageIO.read(fis);
								double width = sourceImg.getWidth();
								double height = sourceImg.getHeight();
								fis.close();
								FileInputStream picFis = new FileInputStream(picturePath);
								//根据图片实际长宽比例处理DOC中图片大小
								run.get(i).addPicture(picFis, XWPFDocument.PICTURE_TYPE_PICT, null, Units.toEMU(DOC_WIDTH), Units.toEMU(DOC_WIDTH / width * height));
								Thread.sleep(500l);
								picFis.close();
							} catch (IOException e) {
								logger.error(e.toString());
								logger.error("图表图片读取失败,请检查路径： {}", picturePath);
							} catch (InterruptedException e){

							}
						}
					}
				}
			}
		}
	}

	/**
	 * 判断替换key类型是文字
	 */
	private static boolean isWord(String key){
		if(key.startsWith(WORD_PREFIX)&&key.endsWith(SUFFIX)){
			return true;
		}
		return false;
	}


	/**
	 * 判断替换key类型是图片
	 */
	private static boolean isPicture(String key){
		if(key.startsWith(PICTURE_PREFIX)&&key.endsWith(SUFFIX)){
			return true;
		}
		return false;
	}
	
}
