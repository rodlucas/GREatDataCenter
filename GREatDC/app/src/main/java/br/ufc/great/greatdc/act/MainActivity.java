package br.ufc.great.greatdc.act;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.TextView;

import br.ufc.great.greatdc.R;
import br.ufc.great.greatdc.model.GreatDataCenter;
import br.ufc.great.greatdc.servlet.GDCServletClient;

public class MainActivity extends AppCompatActivity {

    TextView txStatus;
    TextView txTemperature;
    TextView txHumidity;
    TextView txEnergy;
    GreatDataCenter gdc;
    private int mInterval = 30000; // 5 seconds by default, can be changed later
    private Handler mHandler;

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
        mHandler = new Handler();
        startRepeatingTask();
    }

    private final void updateMetrics() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GDCServletClient ex = new GDCServletClient();
                gdc = ex.getStatus();
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    txStatus.setText("Seguro");
                    txHumidity.setText(gdc.getCr().getHumidity()+"%");
                    txTemperature.setText(gdc.getCr().getTemperature()+"º");

                    if(gdc.getS().getCodStatus() == 1){ //Sistema em estato de atenção
                        txStatus.setText("Atenção");
                        notificationAtencao("GDC Notificação", gdc.getS().getStrStatus());
                    }else if(gdc.getS().getCodStatus() == 2){  //Sistema em estato de alerta
                        txStatus.setText("Alerta");
                        notificationAlerta("GDC Alerta", gdc.getS().getStrStatus());
                    }

                    break;
            }
            return false;
        }
    });

    public void notificationAtencao(String title, String msg){
        // Espera 100ms e vibra por 250ms, espera por mais 100ms e vibra por 500ms
        long[] vibrate = new long[] { 100, 250, 100, 500 };
        // PendingIntent para executar a Activity se o usuário selecionar a notificação
        PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)
                                                new NotificationCompat.Builder(this)
                                                    .setSmallIcon(R.mipmap.ic_launcher)
                                                    .setContentTitle(title)
                                                    .setContentText(msg)
                                                        .setVibrate(vibrate)
                                                        .setAutoCancel(true)
                                                        .setContentIntent(p);
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(R.string.app_name, mBuilder.build());
    }

    public void notificationAlerta(String title, String msg){
        // Espera 100ms e vibra por 250ms, espera por mais 100ms e vibra por 500ms
        long[] vibrate = new long[] { 100, 250, 100, 500 };
        // PendingIntent para executar a Activity se o usuário selecionar a notificação
        PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(msg)
                        .setVibrate(vibrate)
                        .setAutoCancel(true)
                        .setContentIntent(p);
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(R.string.app_name, mBuilder.build());
    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                updateMetrics(); //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

}