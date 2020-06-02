package com.ls.zy.wordreader.config;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.sgies.operation.base.independ.cache.PropertiesCache;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * new Mongo()的时候，就创建了一个连接池，getDB()只是从这个连接池中拿一个可用的连接。而连接池是不需要我们及时关闭的，
 * 我们可以在程序的生命周期中维护一个这样的单例
 * ，至于从连接池中拿出的连接，我们需要关闭吗？答案是NO。你会发现DB根本没有close()之类的方法。在mongoDB中
 * ，一个连接池会维持一定数目的连接，当你需要的时候调用getDB
 * ()去连接池中拿到连接，而mongo会在这个DB执行完数据操作时候自动收回连接到连接池中待用。
 * 所以在mongoDB中大家不必担心连接没有关闭的问题，在你需要在所有操作结束或者整个程序shutdown的时候调用mongo的close()方法即可。
 * 
 * @author tangjianye
 *
 */
@Component(value="mongdbConnFactory") 
public class MongdbConnFactory {

	
	private static MongoClient mongoClient = null;

	private static String mongodb_host;

	private static String mongodb_port;

	private static String mongodb_dbName;

	private static String mongodb_username;

	private static String mongodb_database;

	private static String mongodb_password;

	private static String mongodb_GridFS;

	public static String getMongodbGridFS() {
		return mongodb_GridFS;
	}

	@Value("${mongodb.host:string}")
	public void setMongodbHost(String mongodbHost) {
		MongdbConnFactory.mongodb_host = mongodbHost;
	}

	@Value("${mongodb.port:110}")
	public void setMongodbPort(String mongodbPort) {
		MongdbConnFactory.mongodb_port = mongodbPort;
	}

	@Value("${mongodb.dbName:string}")
	public void setMongodbDbName(String mongodbDbName) {
		MongdbConnFactory.mongodb_dbName = mongodbDbName;
	}

	@Value("${mongodb.username:string}")
	public void setMongodbUsername(String mongodbUsername) {
		MongdbConnFactory.mongodb_username = mongodbUsername;
	}

	@Value("${mongodb.database:string}")
	public void setMongodbDatabase(String mongodbDatabase) {
		MongdbConnFactory.mongodb_database = mongodbDatabase;
	}

	@Value("${mongodb.password:string}")
	public void setMongodbPassword(String mongodbPassword) {
		MongdbConnFactory.mongodb_password = mongodbPassword;
	}

	@Value("${mongodb.GridFS:string}")
	public void setMongodbGridFS(String mongodbGridFS) {
		MongdbConnFactory.mongodb_GridFS = mongodbGridFS;
	}

	@SuppressWarnings("deprecation")
	public static DB getDB() throws UnknownHostException {
		DB conn = null;
		if (mongoClient == null) {
			intializeMongoClient();
		}
		conn = mongoClient.getDB(mongodb_dbName);
		return conn;
	}

	private static void intializeMongoClient() throws UnknownHostException {
		// 创建MongoDB服务器用户验证对象
		MongoCredential credential = MongoCredential.createCredential(
				mongodb_username, mongodb_database, mongodb_password.toCharArray());
		String[] mongdbIps=mongodb_host.split(",");
		String[] mongdbPorts=mongodb_port.split(",");
		if(mongdbIps.length!=mongdbPorts.length){
			throw new RuntimeException("mongodb的IP端口数不匹配");
		}
		if(mongdbIps.length==1){//单机模式	
			ServerAddress address = new ServerAddress(mongodb_host,
					Integer.parseInt(mongodb_port));
			// 登陆到MongoDB服务器 (账号密码验证方式)
			mongoClient = new MongoClient(address, Arrays.asList(credential));	
		}else if(mongdbIps.length>1){//集群模式
			List<ServerAddress> addressList=new ArrayList<>();
			for(int i=0;i<mongdbIps.length;i++){
				String ip=mongdbIps[i];
				String port=mongdbPorts[i];
				ServerAddress address = new ServerAddress(ip,
						Integer.parseInt(port));
				addressList.add(address);
			}
			
			mongoClient = new MongoClient(addressList, Arrays.asList(credential));	
		}else{
			throw new RuntimeException("mongodb没有正确设置");
		}
		
	}

	/**
	 * 
	 * 方法说明：关闭方法(备用)
	 *
	 * Author： tangjianye Create Date： 2018年3月29日 下午1:41:29 History: 2018年3月29日
	 * 下午1:41:29 tangjianye Created.
	 *
	 *
	 */
	public static synchronized void closeConnection() {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}

}