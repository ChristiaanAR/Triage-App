package com.example.triage;

import java.util.ArrayList;
public class VisitRecord {
		
		private final int healthNum;
		private final String arrivalTime;
		
		// These can change
		public ArrayList<VitalSigns> vitalSigns = new ArrayList<VitalSigns>();
		private int numReadings = 0;
		

		public VisitRecord(int healthNum, String arrivalTime){
			this.healthNum = healthNum;
			this.arrivalTime = arrivalTime;
		}
		
		public void newVitalSigns(String timeStamp, double bodyTemp, double heartRate, double BPSystolic, double BPDiastolic,  int urgencyPoints, String textDescription){
			numReadings++;
			vitalSigns.add(new VitalSigns(timeStamp, bodyTemp, heartRate, BPSystolic, BPDiastolic, textDescription, urgencyPoints));
		}
		
		public String getVisitRecord(){
			String visitString = healthNum + "," + arrivalTime + "," + numReadings + "\n";
			
			for(int i=0; i<numReadings; i++){
				if(i==numReadings-1){
					visitString += vitalSigns.get(i).toString();
				}
				else{
					visitString += vitalSigns.get(i).toString() + "\n";
				}
				
			}
			return visitString;
		}	
		
		// getters
		public int getHealthNum (){
			return healthNum;
		}	
		public String getArrivalTime (){
			return arrivalTime;
		}
		public int getNumReadings(){
			return numReadings;
		}
	}
