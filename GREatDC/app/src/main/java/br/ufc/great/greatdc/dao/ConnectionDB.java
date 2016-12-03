package br.ufc.great.greatdc.dao;

/**
 * Created on 13/11/2016.
 */

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;


/**
 * user: gdc
 * pass: gdc
 * banco: greatDataCenter
 * collection: cluster_room
 */

public class ConnectionDB {

    MongoDatabase db = null;
    MongoClientURI uri = new MongoClientURI("mongodb://gdc:gdc@52.67.87.15:27017/?authSource=greatDataCenter");
    MongoClient mongoClient = new MongoClient(uri);

    public ConnectionDB() {
        try {
            // Get database
            db = mongoClient.getDatabase("greatDataCenter");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MongoDatabase getConnection() {
        return db;
    }

    public void getCountDocsClusterRoom(){
        MongoCollection<Document> collection = db.getCollection("cluster_room");
        try {
            // read db
            try {
                System.out.println(collection.count());
            } catch (MongoTimeoutException mte) {
                mte.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllDocClusterRoom(){

        MongoCollection<Document> collection = db.getCollection("cluster_room");
        MongoCursor<Document> cursor = collection.find().iterator();

        try {
            // read db
            try {
                while (cursor.hasNext()) {
                    System.out.println(cursor.next().toJson());
                }
            } catch (MongoTimeoutException mte) {
                mte.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    public void getLastDocClusterRoom(){

        MongoCollection<Document> collection = db.getCollection("cluster_room");
        try {
            try {
                FindIterable<Document> find = collection.find().sort(new BasicDBObject("$natural", -1)).limit(1);
                MongoCursor<Document> cursor = find.iterator();
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    double temp = (double) doc.get("temperature");
                    double humi = (double) doc.get("humidity");

                    System.out.println(temp);
                    System.out.println(humi);
                }
            } catch (MongoTimeoutException mte) {
                mte.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        mongoClient.close();
    }

    public static void main(String args[]){
        ConnectionDB c = new ConnectionDB();
        c.getCountDocsClusterRoom();
        c.getAllDocClusterRoom();
        c.getLastDocClusterRoom();
        c.closeConnection();
    }
}
