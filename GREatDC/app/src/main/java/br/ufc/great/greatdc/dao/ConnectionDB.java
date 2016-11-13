package br.ufc.great.greatdc.dao;

/**
 * Created on 13/11/2016.
 */


import android.util.Log;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoTimeoutException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * ip: 52.67.30.209
 * porta: 27017
 * nome do banco: greatDataCenter
 * usuário: gdc
 * senha: gdc
 * nome da collection: cluster_room
 * em python usei esse código para me conectar com o banco:
 * connection = MongoClient('52.67.11.168', 27017)
 * db = connection['greatDataCenter']
 * db.authenticate('gdc', 'gdc')
 * collection = db['cluster_room']
 * print "Conectou com o MongoDB"
 */

public class ConnectionDB {

    public ConnectionDB() {

    }

    public void connect() {
        MongoCredential journaldevAuth = MongoCredential.createPlainCredential("gdc", "greatDataCenter", "gdc".toCharArray());
        List<MongoCredential> auths = new ArrayList<MongoCredential>();
        auths.add(journaldevAuth);

        ServerAddress serverAddress = new ServerAddress("52.67.11.168", 27017);
        MongoClient mongo = new MongoClient(serverAddress, auths);

        MongoDatabase db = mongo.getDatabase("greatDataCenter");
        MongoCollection table = db.getCollection("cluster_room");

        // read db
        try {
            Log.i("GREatDC", "Número de docs:" + table.count());
        } catch (MongoTimeoutException mte) {
            mte.printStackTrace();
        }

    }


}
