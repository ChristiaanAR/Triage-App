package com.example.triage;

public class VitalSigns {
	private String timeStamp;
	private double bodyTemp;
	private double heartRate;
	private double BPSystolic;
	private double BPDiastolic;
	private String textDescription;
	private int urgencyPoints;
	
	public VitalSigns(String timeStamp, double bodyTemp, double heartRate, double BPSystolic, double BPDiastolic, String textDescription, int urgencyPoints){
		this.timeStamp = timeStamp;
		this.bodyTemp = bodyTemp;
		this.heartRate = heartRate;
		this.BPSystolic = BPSystolic;
		this.BPDiastolic = BPDiastolic;
		this.textDescription = textDescription;
		this.urgencyPoints = urgencyPoints;
	}
	
	public String toString(){
		return timeStamp + "," + bodyTemp + "," + heartRate + "," + BPSystolic 
				+ "," + BPDiastolic + "," + urgencyPoints + "\n" + textDescription;
	}
	
	public String getTimeStamp(){
		return timeStamp;
	}
	public double getBodyTemp(){
		return bodyTemp;
	}
	public double getheartRate(){
		return heartRate;
	}
	public double getBPSystolic(){
		return BPSystolic;
	}
	public double getBPDiastolic(){
		return BPDiastolic;
	}
	public String getTextDescription(){
		return textDescription;
	}
	public int getUrgencyPoints(){
		return urgencyPoints;
	}
	
	
}
