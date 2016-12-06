package br.ufc.great.greatdc.model.Sensors;

import br.ufc.great.greatdc.R;
import br.ufc.great.greatdc.model.iSensor;

public class EnergySensor implements iSensor {

    public String name = new String();
    public String status = new String();
    public int color = 0;
    public int data = 0;
    public int energy_faults = 0;
    public boolean energy_connection = true;

    /**
     * Dados de energia Estável (status normal), Oscilando(laranja), Sem energia (Fica vermelho)
     */

    public EnergySensor() {
        this.name = "Energia";
        this.status = "Estável";
    }

    public void setEnergy_faults(int energy_faults) {
        this.energy_faults = energy_faults;
    }

    public void setEnergy_connection(boolean energy_connection) {
        this.energy_connection = energy_connection;
    }

    public String getStatus() {
        if (energy_faults < 20 && energy_connection)
            status = "Estável";
        else if (energy_connection)
            status = "Oscilando";
        else status = "Sem energia";
        return status;
    }

    public int isOk() {
        if (status.equals("Estável"))
            return 0;
        return 1;
    }

    public int getColor() {
        if (status.equals("Estável"))
            return R.color.yellow;
        else if (status.equals("Oscilando"))
            return R.color.orange;
        return R.color.red;
    }

}
