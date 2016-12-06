package br.ufc.great.greatdc.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

import br.ufc.great.greatdc.model.ClusterRoom;
import br.ufc.great.greatdc.model.GreatDataCenter;
import br.ufc.great.greatdc.model.PowerGeneratorRoom;
import br.ufc.great.greatdc.model.Status;

/**
 * Created by rafaelbraga on 03/12/16.
 */

public class GDCServletClient {

    public GreatDataCenter getUpdatedInfos() {
        String line = "";
        StringBuilder getOutput = new StringBuilder();
        try {
            URL openUrl = new URL("http://default-environment.znfv9dvjks.sa-east-1.elasticbeanstalk.com/GreatDataCenterCloud");
            HttpURLConnection connection = (HttpURLConnection) openUrl.openConnection();
            connection.setDoInput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = br.readLine()) != null)
                getOutput.append(line);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        GreatDataCenter gdc = new GreatDataCenter();
        gdc.setCr(Str2CR(getOutput.toString()));
        return gdc;
    }

    public GreatDataCenter getStatus() {
        String line = "";
        HttpURLConnection connection;
        GreatDataCenter gdc = new GreatDataCenter();
        StringBuilder getOutput = new StringBuilder();
        try {
            URL openUrl = new URL("http://default-environment.znfv9dvjks.sa-east-1.elasticbeanstalk.com/GDCLastStatus");
            connection = (HttpURLConnection) openUrl.openConnection();
            connection.setDoInput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = br.readLine()) != null)
                getOutput.append(line);
            br.close();

            StringTokenizer multiTokenizer = new StringTokenizer(getOutput.toString(), "}");
            for (int i=0;i<=2;i++)
                if (i==0)
                    gdc.setS(Str2S(multiTokenizer.nextToken()+"}"));
                else if(i==1)
                    gdc.setCr(Str2CR(multiTokenizer.nextToken()+"}"));
                else if(i==2)
                    gdc.setPgr(Str2PGR(multiTokenizer.nextToken()+"}"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return gdc;
    }

    public Status Str2S(String gdcStr){
        Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
        Status s = gson1.fromJson(gdcStr, Status.class);
        return s;
    }
    public ClusterRoom Str2CR(String gdcStr){
        Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
        ClusterRoom cr = gson1.fromJson(gdcStr, ClusterRoom.class);
        return cr;
    }
    public PowerGeneratorRoom Str2PGR(String gdcStr) {
        Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
        PowerGeneratorRoom pgr = gson1.fromJson(gdcStr, PowerGeneratorRoom.class);
        return pgr;
    }
}