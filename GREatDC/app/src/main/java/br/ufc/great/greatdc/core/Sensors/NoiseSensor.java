package br.ufc.great.greatdc.core.Sensors;

import br.ufc.great.greatdc.R;
import br.ufc.great.greatdc.core.iSensor;

public class NoiseSensor implements iSensor {

    public String name = new String();
    public String status = new String();
    public int color = 0;
    public int data = 0;

    /**
     * DDados de Ruidos: Precisa ainda ser definido. 
     */

    public NoiseSensor() {
        this.name = "Nível de ruído do gerador";
        this.status = "Seguro";
    }

    public String getStatus() {

        return status;
    }

    public int isOk() {
        if (status.equals("Seguro"))
            return 0;
        return 1;
    }

    public int getColor() {
        return R.color.blueDark;
    }

}
