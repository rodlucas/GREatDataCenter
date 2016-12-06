package br.ufc.great.greatdc.model;

public class GreatDataCenter {

	private Status s;
	private ClusterRoom cr;
	private PowerGeneratorRoom pgr;

	public Status getS() {return s;}
	public void setS(Status s) {this.s = s;}
	public ClusterRoom getCr() {return cr;}
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
