package br.ufc.great.greatdc.act;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        Typeface typeFace = Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/norwester.otf");
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