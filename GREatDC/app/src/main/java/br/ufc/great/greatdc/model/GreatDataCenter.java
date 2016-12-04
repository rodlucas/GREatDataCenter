package br.ufc.great.greatdc.model;

public class GreatDataCenter {

	private ClusterRoom cr;
	private PowerGeneratorRoom pgr;

	
	public ClusterRoom getCr() {
		return cr;
	}
	public void setCr(ClusterRoom cr) {
		this.cr = cr;
	}
	public PowerGeneratorRoom getPgr() {
		return pgr;
	}
	public void setPgr(PowerGeneratorRoom pgr) {
		this.pgr = pgr;
	}
}
