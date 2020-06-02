package com.ls.zy.wordreader.service;

import java.io.*;
import java.net.UnknownHostException;

import com.ls.zy.wordreader.config.MongdbConnFactory;
import com.ls.zy.wordreader.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

import javax.servlet.http.HttpServletResponse;


/**
 * 
 * @ClassName: FileMongodbService 
 * @Description: mongodb方式存储
 * @author: tangjianye
 * @date: 2019年2月18日 下午7:49:23
 */
@Service(value = "mongodbService")
public class FileMongodbService {
	
	private GridFSDBFile gridFSDBFile ;
	private ByteArrayOutputStream byteArrayOut;

	Logger logger = LoggerFactory.getLogger(FileMongodbService.class);

	@SuppressWarnings("restriction")
	BASE64Encoder encoder = new BASE64Encoder();
	@SuppressWarnings("restriction")
	BASE64Decoder decoder = new BASE64Decoder();

	public String deleteFile(String fileId) throws UnknownHostException {
		String s = "";
		try {
			// 获取存放在MongoDB服务器上指定的数据库
			DB db= MongdbConnFactory.getDB();
			GridFS myFS =  new GridFS(db,MongdbConnFactory.getMongodbGridFS());   
			if (StringUtils.isNotBlank(fileId)) {
				ObjectId objId = new ObjectId(fileId);
				myFS.remove(objId);
				s = "true";
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			s = e.getMessage();
		}
		return s;
	}

	
	public String simpleUpload(MultipartFile file, String fileKey)
			throws Exception {
		if (file == null) {
			return null;
		}
		String contentType = file.getContentType();
		String fileName = file.getOriginalFilename();
		String fileSize = String.valueOf(file.getSize());
		InputStream is = file.getInputStream();
		// 处理图片
		fileKey = simpleUpload(is, contentType, fileName, fileSize, fileKey);

		return fileKey;
	}

	public String simpleUploadPicture(String path){
		try {
			FileInputStream fis = new FileInputStream(path);
			String[] names = path.split("\\\\");
			String fileName = null;
			if(names.length>1){fileName= names[names.length-1];}
			return simpleUpload(fis,"",fileName,"","");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	public String simpleUpload(InputStream is, String contentType,
			String fileName, String fileSize, String fileKey) throws Exception {
		// 获取mongo通讯
		DB db=MongdbConnFactory.getDB();
		GridFS myFS =  new GridFS(db, MongdbConnFactory.getMongodbGridFS());   
		//创建存储对象
		GridFSFile fileIn = myFS.createFile(is);
		fileIn.put("filename", fileName);
		fileIn.put("contentType", contentType);
        //如果外部未传入文件id
		if(StringUtil.isEmpty(fileKey)){
		    Object ob = fileIn.getId();
		    fileKey= ob.toString();
		}else{
			deleteFile(fileKey);
			//传入24位  0-9  a-f 标识符
			ObjectId objId = new ObjectId(fileKey);
			fileIn.put("_id", objId);	
		}
		//保存图片
		fileIn.save();
		is.close();
		return fileKey;
	}

	public void downloadFile(String fileKey,HttpServletResponse response)
			throws Exception {

		//拿到mongo链接
		DB db=MongdbConnFactory.getDB();
		GridFS myFS =  new GridFS(db, MongdbConnFactory.getMongodbGridFS());
		ObjectId objId = new ObjectId(fileKey);
		GridFSDBFile gridFSDBFile = (GridFSDBFile) myFS.findOne(objId);
		if (gridFSDBFile != null) {
			OutputStream sos = response.getOutputStream();
			response.setContentType("multipart/form-data");
			// 获取原文件名
			String name = (String) gridFSDBFile.get("filename");
			String fileName = new String(name.getBytes("GBK"), "ISO8859-1");

			// 设置下载文件名
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\"");
			//response.setHeader("fileName", "cxcs");
			// 向客户端输出文件
			gridFSDBFile.writeTo(sos);
			sos.flush();
			sos.close();
		}
	}




    
	public byte[] getFileByteByFileId(String fileKey) throws Exception{
		//拿到mongo链接
		DB db=MongdbConnFactory.getDB();
		GridFS myFS =  new GridFS(db, MongdbConnFactory.getMongodbGridFS());   
		ObjectId objId = new ObjectId(fileKey);
		gridFSDBFile = (GridFSDBFile) myFS.findOne(objId);
		if (gridFSDBFile != null) {
             byteArrayOut = new ByteArrayOutputStream();
			gridFSDBFile.writeTo(byteArrayOut);
			return byteArrayOut.toByteArray();
		}
		return null;
	}





	public String uploadBase64Img(String base64String, String fileName,
			String contentType) {
		String attachID = "";
		try {
			// 获取mongo通讯
			DB db=MongdbConnFactory.getDB();
			GridFS myFS =  new GridFS(db, MongdbConnFactory.getMongodbGridFS());   
			base64String = base64String.substring(base64String
					.lastIndexOf("base64,") + 7);
			byte[] bytes1 = decoder.decodeBuffer(base64String);
			for (int i = 0; i < bytes1.length; ++i) {
				if (bytes1[i] < 0) {
					bytes1[i] += 256;
				}
			}
			InputStream file = new ByteArrayInputStream(bytes1);
			GridFSFile fileIn = myFS.createFile(file);
			fileIn.put("filename", fileName);
			fileIn.put("contentType", contentType);
			Object ob = fileIn.getId();
			attachID = ob.toString();
			fileIn.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attachID;
	}

	
	public GridFSDBFile queryGridById(String id) throws Exception {
		//拿到mongo链接
		DB db=MongdbConnFactory.getDB();
		GridFS myFS =  new GridFS(db, MongdbConnFactory.getMongodbGridFS());
		ObjectId objId = new ObjectId(id);
		GridFSDBFile gridFSDBFile = (GridFSDBFile) myFS.findOne(objId);
		return gridFSDBFile;
	}

}
