package com.example.app.model;

public class SkillInfo {

	public final static String flag_success="success";
	public SkillInfo()
	{
		id=0;
		name="";
		intro="";
		price=0;
		time=0;
		sale=0;
		score=0;
		saler_name="";
		type="";
	}
	private int id;
	private String name;
	private String intro;
	private float price;
	private int time;//ѧʱ
	private int sale;
	private float score;
	private String saler_name;
	private String type;
	
	public void setID(int i){id=id;}
	public void setName(String n){name=n;}
	public void setIntro(String i){intro=i;}
	public void setPrice(float p){price=p;}
	public void setTime(int t){time=t;}
	public void setSale(int s){	sale=s;}
	public void setScore(float s){	score=s;}
	public void setSalerName(String sn){saler_name=sn;}
	public void setType(String t){type=t;}
	
	public int getID(){return id;}
	public String getName(){return name;}
	public String getIntro(){return intro;}
	public float getPrice(){return price;}
	public int getTime(){return time;}
	public int getSale(){return sale;}
	public float getScore(){return score;}
	public String getSalerName(){return saler_name;}
	public String getType(){return type;}
}
