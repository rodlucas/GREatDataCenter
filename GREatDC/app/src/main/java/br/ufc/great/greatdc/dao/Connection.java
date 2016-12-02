package br.ufc.great.greatdc.dao;

/**
 * Created by rafaelbraga on 01/12/16.
 */

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;


public class Connection {

    public static void main(String[] args) {
        MongoClientURI uri = new MongoClientURI("mongodb://root:5mEzEp40@52.67.87.15:27017/?authSource=admin");
        MongoClient mongoClient = new MongoClient(uri);
        MongoCursor<Document> cursor = null;
        try {
            // New way to get database
            MongoDatabase db = mongoClient.getDatabase("greatDataCenter");
            // New way to get collection
            MongoCollection<Document> collection = db.getCollection("cluster_room");

            cursor = collection.find().iterator();

            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            mongoClient.close();
        }
    }
}
