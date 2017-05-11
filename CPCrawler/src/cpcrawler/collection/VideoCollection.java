/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpcrawler.collection;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cpcrawler.ulti.MongoDB;
import org.bson.Document;

/**
 *
 * @author toanhx
 */
public class VideoCollection {
	private static final String _collectionName = "Video";
	private static final VideoCollection Instance = new VideoCollection();
	private static MongoCollection<Document> _collection;
	
	private VideoCollection (){
		MongoDB mongo = new MongoDB("CommentProcess");
		MongoDatabase mongoDB = mongo.getMongoDB(); 
		_collection = mongoDB.getCollection(_collectionName);
	}
	
	
	public boolean isExists() {
		return true;
	}
	
}
