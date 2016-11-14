package br.ufc.great.greatdc.act;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.ufc.great.greatdc.R;
import br.ufc.great.greatdc.dao.ConnectionDB;

public class MainActivity extends AppCompatActivity {

    TextView txStatus;
    TextView txTemperature;
    TextView txHumidity;
    TextView txNoise;
    TextView txEnergy;
    ConnectionDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txStatus=(TextView)findViewById(R.id.tx_status);
        txTemperature=(TextView)findViewById(R.id.tx_temperature);
        txHumidity=(TextView)findViewById(R.id.tx_humidity);
        txNoise=(TextView)findViewById(R.id.tx_noise);
        txEnergy=(TextView) findViewById(R.id.tx_energy);
        changeFont();

        db = new ConnectionDB();
        //db.connect();


    }

    public void changeFont(){
        //Typeface typeFace=Typeface.createFromAsset(getAssets(),"norwester.ttf");
        Typeface typeFace = Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/norwester.otf");
        txStatus.setTypeface(typeFace);
        txTemperature.setTypeface(typeFace);
        txHumidity.setTypeface(typeFace);
        txNoise.setTypeface(typeFace);
        txEnergy.setTypeface(typeFace);
    }


    public void callTemperatureActivity(View view){
        Intent intent = new Intent(this, TempActivity.class);
        startActivity(intent);
    }
}
