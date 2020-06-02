package com.ls.zy.wordreader.service;

import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ls.zy.wordreader.utils.StringUtil;
import com.sgies.operation.base.depend.out.Result;
import com.sgies.operation.base.depend.out.ResultUtil;
import com.sgies.operation.base.independ.exception.ResultEnum;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
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
import com.pt.poseidon.api.framework.Service;
import com.pt.poseidon.api.framework.ServiceAutowired;
import com.pt.poseidon.api.framework.ServiceType;
import com.sgies.operation.base.depend.mongdb.MongdbConnFactory;
import com.sgies.tool.archive.attach.service.IAttachService;
import com.sgies.tool.archive.attach.service.IFileManageService;
import com.sgies.tool.archive.attach.vo.AttachVO;
import com.sgies.tool.archive.attach.vo.DownFileVO;


/**
 * 
 * @ClassName: FileMongodbService 
 * @Description: mongodb方式存储
 * @author: tangjianye
 * @date: 2019年2月18日 下午7:49:23
 */
@Service(value = "mongodbService", target = { ServiceType.APPLICATION })
public class FileMongodbService implements IAttachService {
	
	private GridFSDBFile gridFSDBFile ;
	private ByteArrayOutputStream byteArrayOut;

	@ServiceAutowired
	private  IFileManageService  fileManageService;

	@Value("${proxyUploadFile.url:http://172.26.1.11:8089/proxyNetCrossUploadFile}")
	private String url;
	/**
	 * 目前只支持一个附件！
	 */
	@Override
	public List<AttachVO> uploadFile(HttpServletRequest request)
			throws Exception {
        //如果不为空,走修改接口
		String attchId=request.getParameter("docId");//附件ID
		//转换 HttpServletRequest
		MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest)request;//附件实体列表
		 List<AttachVO> res=new ArrayList<>();
		//获取上传的文件---其实就一个
		Iterator<String> itr = multipartRequest.getFileNames();
		while (itr.hasNext()) {
			MultipartFile file = multipartRequest.getFile((String) itr
					.next());
			//上传逻辑
			attchId=simpleUpload(file, attchId);
			//查询数据库接口
			AttachVO vo=new AttachVO();
			vo.setDocId(attchId);
			res.add(vo);
		}
		return res;
	}


	@Override
	public AttachVO getFile(String docId) throws Exception {
		return fileManageService.getAttachMentByid(docId);
	}


	@Override
	public void downloadFile(DownFileVO downFileVO, HttpServletResponse response)
			throws Exception {

		//拿到mongo链接
		DB db=MongdbConnFactory.getDB();
		GridFS myFS =  new GridFS(db, MongdbConnFactory.getMongodbGridFS());
		ObjectId objId = new ObjectId(downFileVO.getDocId());
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


	@Override
	public AttachVO delFile(String docId) throws UnknownHostException {
		String s = "";
		try{
			//获取存放在MongoDb服务器上指定的数据库
			DB db=MongdbConnFactory.getDB();
			GridFS myFS = new GridFS(db,MongdbConnFactory.getMongodbGridFS());
			if(StringUtils.isNotBlank(docId)){
				ObjectId objectId = new ObjectId(docId);
				myFS.remove(objectId);
				s = "true";
			}
		}catch (NumberFormatException e){
			e.printStackTrace();
			s = e.getMessage();
		}
		return null;
	}


	@SuppressWarnings("restriction")
	BASE64Encoder encoder = new BASE64Encoder();
	@SuppressWarnings("restriction")
	BASE64Decoder decoder = new BASE64Decoder();

	public String deleteFile(String fileId) throws UnknownHostException {
		String s = "";
		try {
			// 获取存放在MongoDB服务器上指定的数据库
			DB db=MongdbConnFactory.getDB();
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

	@Override
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
		// 数据库备份附件信息
		AttachVO attachment = new AttachVO(fileKey,fileName,fileSize);
		fileManageService.addAttach(attachment);
		return fileKey;
	}




    @Override
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

	@Override
	public GridFSDBFile queryGridById(String id) throws Exception {
		//拿到mongo链接
		DB db=MongdbConnFactory.getDB();
		GridFS myFS =  new GridFS(db, MongdbConnFactory.getMongodbGridFS());
		ObjectId objId = new ObjectId(id);
		GridFSDBFile gridFSDBFile = (GridFSDBFile) myFS.findOne(objId);
		return gridFSDBFile;
	}

	@Override
	public Result getImg(String img, HttpServletResponse response) throws Exception {
		GridFSDBFile gridFSDBFile = queryGridById(img);
		if (gridFSDBFile != null) {
			try {
				BufferedInputStream reader = new BufferedInputStream(gridFSDBFile.getInputStream());
				BufferedOutputStream writer = new BufferedOutputStream(response.getOutputStream());

				response.setHeader("Content-Type","image/jpeg");

				byte[] bytes = new byte[1024 * 1024];
				int length = reader.read(bytes);
				while ((length > 0)) {
					writer.write(bytes, 0, length);
					length = reader.read(bytes);
				}
				reader.close();
				writer.close();
			} catch (Exception ex) {
				throw new Exception(ex);
			}
			return ResultUtil.success();
		}
		return ResultUtil.error(ResultEnum.EMPTY);
	}

}
