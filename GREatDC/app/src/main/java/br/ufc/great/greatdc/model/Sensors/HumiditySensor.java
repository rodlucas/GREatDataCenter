package br.ufc.great.greatdc.model.Sensors;

import br.ufc.great.greatdc.R;
import br.ufc.great.greatdc.model.iSensor;

public class HumiditySensor implements iSensor {

    public String name = new String();
    public String status = new String();
    public int color = 0;
    public int data = 0;

    /**
     * Dados de Umidade Seguro - Entre 45% e 55% Alerta -  entre 40% e 44%  e 56% até 65 
     * Emergência  - Abaixo de 40% e maior que 65%
     */

    public HumiditySensor() {
        this.name = "Umidade do ambiente";
        this.status = "Seguro";
    }

    public String getStatus() {
        if (data >= 45 && data <= 55)
            status = "Seguro";
        else if ((data >= 40 && data < 45) || (data > 55 && data <= 65))
            status = "Alerta";
        else status = "Emergência";
        return status;
    }

    public int isOk() {
        if (status.equals("Seguro"))
            return 0;
        return 1;
    }

    public int getColor() {
        if (data < 40)
            return R.color.blueLight;
        else if (data > 40 && data < 75)
            return R.color.blue;
        return R.color.blueDark;
    }

}
