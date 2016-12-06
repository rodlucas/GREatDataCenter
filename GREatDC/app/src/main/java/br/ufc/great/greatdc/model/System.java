package br.ufc.great.greatdc.model;

import br.ufc.great.greatdc.R;
import br.ufc.great.greatdc.model.Sensors.EnergySensor;
import br.ufc.great.greatdc.model.Sensors.HumiditySensor;
import br.ufc.great.greatdc.model.Sensors.TemperatureSensor;

public class System {

    TemperatureSensor temperature;
    HumiditySensor humidity;
    EnergySensor energy;

    public String status = "Seguro";
    public int color = R.color.green;

    /**
     * Níveis de monitoramento do Status do Sistema: Seguro (tudo ok), Alerta (Um dado alterado),
     * Emergência (Dois dados alterados fora doa parâmetro normais).
     */

    public System() {
        temperature = new TemperatureSensor();
        humidity = new HumiditySensor();
        energy = new EnergySensor();
    }

    public void setStatus() {
        int status = temperature.isOk() + humidity.isOk() + energy.isOk();
        if (status == 0)
            this.status = "Seguro";
        else if (status == 1)
            this.status = "Alerta";
        else this.status = "Emergência";
    }

    public String getStatus() {
        return status;
    }

    public void setColor() {
        if (status.equals("Seguro"))
            this.color = R.color.green;
        else if (status.equals("Alerta"))
            this.color = R.color.greenDark;
        else this.color = R.color.red;

    }
}
