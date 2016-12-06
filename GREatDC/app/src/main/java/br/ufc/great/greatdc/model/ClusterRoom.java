package br.ufc.great.greatdc.model;

public class ClusterRoom {
	private String date;
	private float temperature;
	private float humidity;

	public String getDate() {return date;}
	public void setDate(String date) {this.date = date;}
	public float getTemperature() {
		return temperature;
	}
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	public float getHumidity() {
		return humidity;
	}
	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}
}
