package br.ufc.great.greatdc.act;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import br.ufc.great.greatdc.R;
import br.ufc.great.greatdc.model.GreatDataCenter;
import br.ufc.great.greatdc.notifications.Constants;
import br.ufc.great.greatdc.servlet.GDCServletClient;
import br.ufc.great.greatdc.swipe.SwipeToRefreshListView;
import br.ufc.great.greatdc.swipe.SwipeToRefreshListener;

public class MainActivity extends AppCompatActivity {

    private static String newRegID;
    private static final String TAG = "RegServicePush";

    TextView txStatus;
    TextView txTemperature;
    TextView txHumidity;
    TextView txEnergy;
    GreatDataCenter gdc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txStatus=(TextView)findViewById(R.id.tx_status);
        txTemperature=(TextView)findViewById(R.id.tx_temperature);
        txHumidity=(TextView)findViewById(R.id.tx_humidity);
        txEnergy=(TextView) findViewById(R.id.tx_energy);
        updateMetrics();
        changeFont();
        notification();


        //ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_expandable_list_item_1, col.toArray());

        //SwipeToRefreshListView swipeListView = (SwipeToRefreshListView)findViewById(R.id.lst);
        //swipeListView.setAdapter(adapter);

        //swipeListView.setRefreshListener(new SwipeToRefreshListener() {

        //    @Override
        //    public void onRefresh() {
        //        Toast.makeText(MainActivity.this, "ATUALIZANDO", Toast.LENGTH_SHORT).show();
        //    }
        //});
    }

    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String token) {
        MainActivity.newRegID = token;
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
            dataMap.put("regID", MainActivity.newRegID);

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

    public void notification(){

        // Espera 100ms e vibra por 250ms, espera por mais 100ms e vibra por 500ms
        long[] vibrate = new long[] { 100, 250, 100, 500 };
        // PendingIntent para executar a Activity se o usuário selecionar a notificação
        PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)
                                                new NotificationCompat.Builder(this)
                                                    .setSmallIcon(R.mipmap.ic_launcher)
                                                    .setContentTitle("My notification")
                                                    .setContentText("Hello World!")
                                                        .setVibrate(vibrate)
                                                        .setAutoCancel(true)
                                                        .setContentIntent(p);
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(R.string.app_name, mBuilder.build());
    }

    private final void updateMetrics() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GDCServletClient ex = new GDCServletClient();
                gdc = ex.getUpdatedInfos();
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    txHumidity.setText(gdc.getCr().getHumidity()+"%");
                    txTemperature.setText(gdc.getCr().getTemperature()+"º");
                    break;
            }
            return false;
        }
    });

    public void changeFont(){
        Typeface typeFace = Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/CalvertMTStd.otf");
        txStatus.setTypeface(typeFace);
        txTemperature.setTypeface(typeFace);
        txHumidity.setTypeface(typeFace);
        txEnergy.setTypeface(typeFace);
    }

    public void callTemperatureActivity(View view){
        Intent intent = new Intent(this, TempActivity.class);
        startActivity(intent);
    }
}