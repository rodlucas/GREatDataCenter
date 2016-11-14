package br.ufc.great.greatdc.core;


public interface iSensor {

    public String name = new String();
    public String status= new String();
    public int color= 0;
    public int data = 0;

    public String getStatus();
    public int isOk(); // 0=ok 1=not ok
    public int getColor();

}
