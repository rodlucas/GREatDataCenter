package br.ufc.great.greatdc.model;

public class PowerGeneratorRoom {
	private float noise;
	private float hourmeter;
	private float fuelLevel;
	private int voltage_companhia;
	private int voltage_gerador;

	public float getNoise() {
		return noise;
	}
	public void setNoise(float noise) {
		this.noise = noise;
	}
	public float getHourmeter() {
		return hourmeter;
	}
	public void setHourmeter(float hourmeter) {
		this.hourmeter = hourmeter;
	}
	public float getFuelLevel() {
		return fuelLevel;
	}
	public void setFuelLevel(float fuelLevel) {
		this.fuelLevel = fuelLevel;
	}
	public int getVoltage_gerador() {
		return voltage_gerador;
	}
	public void setVoltage_gerador(int voltage_gerador) {
		this.voltage_gerador = voltage_gerador;
	}
	public int getVoltage_companhia() {
		return voltage_companhia;
	}
	public void setVoltage_companhia(int voltage_companhia) {
		this.voltage_companhia = voltage_companhia;
	}
}
