package br.ufc.great.greatdc.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.ufc.great.greatdc.model.ClusterRoom;
import br.ufc.great.greatdc.model.GreatDataCenter;

/**
 * Created by rafaelbraga on 03/12/16.
 */

public class GDCServletClient {

    public GreatDataCenter getUpdatedInfos() {
        String line = "";
        StringBuilder getOutput = new StringBuilder();
        try {
            URL openUrl = new URL("http://192.168.0.103:8080/GreatDataCenter/GreatDataCenterCloud");
            HttpURLConnection connection = (HttpURLConnection) openUrl.openConnection();
            connection.setDoInput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = br.readLine()) != null)
                getOutput.append(line);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Str2GDC(getOutput.toString());
    }

    public GreatDataCenter Str2GDC(String gdcStr){
        GreatDataCenter gdc_ = new GreatDataCenter();
        Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
        ClusterRoom ddd = gson1.fromJson(gdcStr, ClusterRoom.class);
        gdc_.setCr(ddd);
        return gdc_;
    }
}