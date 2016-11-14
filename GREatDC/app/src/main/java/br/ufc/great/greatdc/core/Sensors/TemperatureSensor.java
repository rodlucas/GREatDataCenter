package br.ufc.great.greatdc.core.Sensors;

import br.ufc.great.greatdc.R;
import br.ufc.great.greatdc.core.iSensor;

public class TemperatureSensor implements iSensor {

    public String name = new String();
    public String status = new String();
    public int color = 0;
    public int data = 0;

    /**
     * Dados de temperatura: Seguro - Entre 20 e 24°, Alerta -  entre 15 e 19°  e 25° até 30°
     * Emergência  - abaixo de 15° e acima de 30° (Vermelho)
     */

    public TemperatureSensor() {
        this.name = "Temperatura";
        this.status = "Seguro";
    }

    public String getStatus() {
        if (data >= 20 && data <= 24)
            status = "Seguro";
        else if ((data >= 15 && data < 20) || (data > 24 && data <= 30))
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
        if (status.equals("Seguro") || status.equals("Alerta"))
            return R.color.blue;
        return R.color.red;
    }

}
