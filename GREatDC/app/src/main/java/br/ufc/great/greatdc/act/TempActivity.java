package br.ufc.great.greatdc.act;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import br.ufc.great.greatdc.R;

public class TempActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        spinner = (Spinner) findViewById(R.id.sp_hours);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hours_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        loadChart();

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void loadChart() {
        String content = null;
        content = getHtmlFromAsset();
        Log.d("Lana", content);

        WebView mCharView = (WebView) findViewById(R.id.char_view);
        WebSettings settings = mCharView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        mCharView.loadData(content, "text/html; charset=utf-8", null);
        //mCharView.loadData(content, "text/html; charset=utf-8", "utf-8");
        //mCharView.loadDataWithBaseURL("file:///android_asset/", content, "text/html", "UTF-8", null);
    }

    private void loadChart2() {
        String url = "https://chart.googleapis.com/chart?" +
                "cht=bhg&" + //define o tipo do gráfico "linha"

                "chs=300x800&" + //define o tamanho da imagem
                "chd=t:23,24,25,23,24,25,23,20,20,23,20,23,24,25,23,20,20,23,24,25,23,20,20,23,24,25,23,20,20,23,24,25,23,20,20&" + //valor de cada coluna do gráfico
                "chxr=0,35&" + //define o valor de início e fim do eixo
                "chds=0,35&" + //define o valor de escala dos dados
                "chco=3D793000&" +
                "chm=B,004a72,0,12,0"; //fundo verde

        WebView mCharView = (WebView) findViewById(R.id.char_view);
        mCharView.loadUrl(url);
    }

    private String getHtmlFromAsset() {
        InputStream is;
        StringBuilder builder = new StringBuilder();
        String htmlString = null;
        try {
            is = getAssets().open(getString(R.string.chart_html));
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                htmlString = builder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return htmlString;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
