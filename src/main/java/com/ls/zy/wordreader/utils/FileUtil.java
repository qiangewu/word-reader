package com.ls.zy.wordreader.utils;

import com.ls.zy.wordreader.enums.FileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;

public class FileUtil {
    static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 判断是否是word文档
     * @param fileName
     * @return
     */
    public static boolean isWord(String fileName) {
        if (StringUtils.isEmpty(fileName)) return false;
        String[] fileParts = fileName.split("\\.");
        String fileType = fileParts[fileParts.length - 1];
        if (FileType.DOC.getDesc().equals(fileType) || FileType.DOCX.getDesc().equals(fileType)) return true;
        return false;
    }

    /**
     * 安全地删除文件目录
     * 避免删除重要文件(文件假至少有2级)
     */
    public static boolean deleteAllSafely(String fileName) {
//        if(StringUtil.isBlank(fileName)||fileName.split("\\"+File.separator).length<3){
//            logger.error("删除当前路径不安全，请尝试其他路径（删除路径需要有三级）");
//            return false;
//        }
//        return deleteAll(fileName);
        return true;
    }

    /**
     * 判断指定的文件或文件夹删除是否成功
     * @param fileName 文件或文件夹的路径
     * @return true or false 成功返回true，失败返回false
     */
    public static boolean deleteAll(String fileName) {
        File file = new File(fileName);/*根据指定的文件名创建File对象*/
        if (!file.exists()) {  /*要删除的文件不存在*/
            logger.warn("文件" + fileName + "不存在，删除失败！");
            return false;
        } else { /*要删除的文件存在*/
            if (file.isFile()) {
                /*如果目标文件是文件*/
                return deleteFile(fileName);
            } else {  /*如果目标文件是目录*/
                return deleteDir(fileName);
            }
        }
    }

    /**
     * 判断指定的文件删除是否成功
     * @param fileName 文件路径
     * @return true or false 成功返回true，失败返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);/*根据指定的文件名创建File对象*/
        if (file.exists() && file.isFile()) { /*要删除的文件存在且是文件*/
            if (file.delete()) {
//               logger.info("文件" + fileName + "删除成功！");
                return true;
            } else {
                logger.error("文件" + fileName + "删除失败！");
                return false;
            }
        } else {
            logger.warn("文件" + fileName + "不存在，删除失败！");
            return false;
        }
    }

    /**
     * 删除指定的目录以及目录下的所有子文件
     * @param dirName is 目录路径
     * @return true or false 成功返回true，失败返回false
     */
    public static boolean deleteDir(String dirName) {
        if (dirName.endsWith(File.separator)) {
            /*dirName不以分隔符结尾则自动添加分隔符*/
            dirName = dirName + File.separator;
        }
        File file = new File(dirName);
        /*根据指定的文件名创建File对象*/
        if (!file.exists() || (!file.isDirectory())) {
            logger.warn("目录删除失败" + dirName + "目录不存在！");
            return false;
        }
        File[] fileArrays = file.listFiles();
        /*列出源文件下所有文件，包括子目录*/
        for (int i = 0; i < fileArrays.length; i++) {
            /*将源文件下的所有文件逐个删除*/
            deleteAll(fileArrays[i].getAbsolutePath());
        }
        if (file.delete()){
            /*删除当前目录*/
//            logger.info("目录" + dirName + "删除成功！");
        }
        return true;
    }

    /**
     * 把文件流写入本地
     * @param input
     * @param destination 目标文件地址
     * @throws IOException
     */
    public static void writeToLocal(InputStream input,String destination)
            throws IOException {
        File localFile = new File(destination);
        if(!localFile.exists()){
            File dir = new File(localFile.getParent());
            dir.mkdirs();
            localFile.createNewFile();
        }
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(destination);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        input.close();
        downloadFile.close();
    }

    /**
     * 把resources中的文件写入本地
     * @param sourceFile sources中文件地址
     * @param destination 目标文件地址
     * @throws IOException
     */
    public static void writeSourceToLocal(String sourceFile,String destination)
            throws IOException {
        ClassPathResource resource = new ClassPathResource(sourceFile);
        InputStream ips = resource.getInputStream();
        writeToLocal( ips, destination);
    }

}
