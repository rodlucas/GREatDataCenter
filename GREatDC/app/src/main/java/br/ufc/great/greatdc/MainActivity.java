package br.ufc.great.greatdc;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txStatus;
    TextView txTemperature;
    TextView txHumidity;
    TextView txNoise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txStatus=(TextView)findViewById(R.id.tx_status);
        txTemperature=(TextView)findViewById(R.id.tx_temperature);
        txHumidity=(TextView)findViewById(R.id.tx_humidity);
        txNoise=(TextView)findViewById(R.id.tx_noise);

        changeFont();

    }

    public void changeFont(){

        //Typeface typeFace=Typeface.createFromAsset(getAssets(),"norwester.ttf");
        Typeface typeFace = Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/norwester.otf");
        txStatus.setTypeface(typeFace);
        txTemperature.setTypeface(typeFace);
        txHumidity.setTypeface(typeFace);
        txNoise.setTypeface(typeFace);

    }
}
