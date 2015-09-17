package com.example.triage;

import java.util.ArrayList;

public class PatientFile {
	
	private final int healthNum;
	private final String name;
	private final String birthdate;
	public ArrayList<VisitRecord> visitRecords = new ArrayList<VisitRecord>();
	private int numVisits = 0;
	
	public PatientFile(int healthNum, String name, String birthdate){
		this.healthNum = healthNum;
		this.name = name;
		this.birthdate = birthdate;
	}
	
	public void setInHouse(Boolean inHouse){
	}
	
	public void addVisitRecord(String arrivalTime){
		numVisits += 1;
		visitRecords.add(new VisitRecord(healthNum, arrivalTime));
	}

	public int getHealthNum() {
		return healthNum;
	}
	
	public String getName() {
		return name;
	}
	
	public String getBirthdate() {
		return birthdate;
	}

	public String getString() {
		return healthNum + "," + name + "," + birthdate;
	}

	public int getNumVisits() {
		return numVisits;
	}

}
