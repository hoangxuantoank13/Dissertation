/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpcrawler.ulti;

/**
 *
 * @author toanhx
 */
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import cpcrawler.ulti.CPConfig;

public class MongoDB {

	
	
	private static String HOST;
	private static int PORT;

	//
	private static String USERNAME;
	private static String PASSWORD;
	private static String NAME;

	public MongoDB(String name) {
		NAME = name;
		HOST = CPConfig.Instance.getString("MongoDB." + name + ".host", "localhost");
		PORT = CPConfig.Instance.getInt("MongoDB." + name + ".port", 27017);
		USERNAME = CPConfig.Instance.getString("MongoDB." + name + ".username", "");
		PASSWORD = CPConfig.Instance.getString("MongoDB." + name + ".password", "");
	}

	// Cách kết nối vào MongoDB không bắt buộc bảo mật.
	private MongoDatabase getMongoDBNoCredential() {
		MongoClient mongoClient = new MongoClient(HOST, PORT);
		return mongoClient.getDatabase(NAME);

	}

	// Cách kết nối vào DB MongoDB có bảo mật.
	private MongoDatabase getMongoDBWithCredential() {
		MongoCredential credential = MongoCredential.createMongoCRCredential(
				USERNAME, NAME, PASSWORD.toCharArray());

		MongoClient mongoClient = new MongoClient(
				new ServerAddress(HOST, PORT), Arrays.asList(credential));
		return mongoClient.getDatabase(NAME);
	}
	
	public MongoDatabase getMongoDB() {
		if(USERNAME.equals("") && PASSWORD.equals("")) {
			return getMongoDBNoCredential();
		} else {
			return getMongoDBWithCredential();
		}
	
	}
	
	
	

}
