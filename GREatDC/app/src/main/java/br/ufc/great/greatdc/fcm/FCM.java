package br.ufc.great.greatdc.fcm;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by rafaelbraga on 05/12/16.
 */

public class FCM {

    private static String newRegID;
    private static final String TAG = "RegServicePush";

    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String token) {
        FCM.newRegID = token;
        WebServerRegistrationTask webServer=new WebServerRegistrationTask();
        webServer.execute();
    }

    public class WebServerRegistrationTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            URL url = null;
            try {
                url = new URL(Constants.WEB_SERVER_URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("regID", FCM.newRegID);

            StringBuilder postBody = new StringBuilder();
            Iterator<Map.Entry<String, String>> iterator = dataMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String,String> param = (Map.Entry<String,String>) iterator.next();
                postBody.append(param.getKey()).append('=')
                        .append(param.getValue());
                if (iterator.hasNext()) {
                    postBody.append('&');
                }
            }
            String body = postBody.toString();
            byte[] bytes = body.getBytes();

            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setFixedLengthStreamingMode(bytes.length);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

                OutputStream out = conn.getOutputStream();
                out.write(bytes);
                out.close();
                String response="";
                InputStream is = null;
                try {
                    is = conn.getInputStream();
                    int ch;
                    StringBuffer sb = new StringBuffer();
                    while ((ch = is.read()) != -1) {
                        sb.append((char) ch);
                    }
                    response=sb.toString();

                } finally {
                    if (is != null) {
                        is.close();
                    }
                }
                int status = conn.getResponseCode();
                if (status == 200) {
                    if(response.equals("1")){
                    }
                } else {
                    throw new IOException("Request failed with error code " + status);
                }
            } catch (ProtocolException pe) {
                pe.printStackTrace();
            } catch (IOException io) {
                io.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            return null;
        }
    }

}
