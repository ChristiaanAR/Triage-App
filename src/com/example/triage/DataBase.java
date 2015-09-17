package com.example.triage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.content.Context;
import android.util.Log;

public class DataBase {
	
	private final String fileNamePatients; // must be file path
	private final String dir;
	private ArrayList<String> fileNameVisits = new ArrayList<String>(); // change to file path
	
	public int numPatients = 0;
	public ArrayList<PatientFile> patientFiles = new ArrayList<PatientFile>();
	
	public DataBase(String fileName, String dir){
		this.fileNamePatients = fileName;
		this.dir = dir;
		try {
			load();
		} catch (IOException e) {}
	}
	
	public int getIndex(int healthNum){
		for(int i=0; i < numPatients; i++){
			if(healthNum == patientFiles.get(i).getHealthNum()){
				return i;
			}
		}
		return -1;
	}

	public void addPatient(int healthNum, String name, String birthdate){ //private for phase 2
		numPatients++;
		patientFiles.add(new PatientFile(healthNum, name, birthdate));
	}
	
	private void load() throws IOException{
		File file = new File(dir,fileNamePatients);
		//Scanner patients = new Scanner(new FileInputStream(file));
		FileInputStream fisp = new FileInputStream(file);//cont.openFileInput(fileNamePatients);
		InputStreamReader isrp = new InputStreamReader(fisp);
		BufferedReader patients = new BufferedReader(isrp);
		
		String line = patients.readLine();
		while(line != null){
			Log.d("tag", line);
			addPatient(Integer.parseInt(line.split(",")[0]), line.split(",")[1], line.split(",")[2]);
			Log.d("Tag", Integer.toString(numPatients-1));
			fileNameVisits.add(String.valueOf(patientFiles.get(numPatients-1).getHealthNum())); // out of bound exception
			//get file of visitRecords
			try{
				//Scanner visits = new Scanner(new FileInputStream(fileNameVisits[numPatients]));
				Log.d("file name", fileNameVisits.get(numPatients-1));
				File filev = new File(dir, fileNameVisits.get(numPatients-1));
				FileInputStream fisv = new FileInputStream(filev);//cont.openFileInput(fileNamePatients);
				InputStreamReader isrv = new InputStreamReader(fisv);
				BufferedReader visits = new BufferedReader(isrv);
				
				String vline = visits.readLine();// healthNum,arrivalTime
				while(vline != null){
					patientFiles.get(numPatients-1).addVisitRecord(line.split(",")[1]);
					//Log.d("tag", vline);
					int numReadings = Integer.parseInt(vline.split(",")[2]);
					for(int i=0; i < numReadings; i++){
						vline = visits.readLine(); // timeStamp,bodyTemp,heartRate,BPSystolic,BPDiastolic,urgencyPoints
						Log.d("tag", vline);
						patientFiles.get(numPatients-1).visitRecords.get(i).newVitalSigns(vline.split(",")[0], Double.parseDouble(vline.split(",")[1]), Double.parseDouble(vline.split(",")[2]), 
								Double.parseDouble(vline.split(",")[3]), Double.parseDouble(vline.split(",")[4]), Integer.parseInt(vline.split(",")[5]), visits.readLine());
					}
					vline = visits.readLine(); // healthNum,arrivalTime
				}
			visits.close();
			}catch (FileNotFoundException e){}
			line = patients.readLine();
		}
		patients.close();
		
	}
	
	public void save(Context cont) throws IOException{
		//FileOutputStream patientsStream = cont.openFileOutput(fileNamePatients, Context.MODE_PRIVATE); // not in phase 2
		for(int i=0; i<numPatients; i++){
			//try{
				//patientsStream.write(patientFiles[i].getString().getBytes()); // not in phase 2
			//}catch(IOException e){}
			
			FileOutputStream visitsStream = cont.openFileOutput(fileNameVisits.get(i), Context.MODE_PRIVATE);
			
			for(int v=0; v<patientFiles.get(i).getNumVisits(); v++){
				try{
					visitsStream.write(patientFiles.get(i).visitRecords.get(v).getVisitRecord().getBytes());
				}catch(IOException e){}
				
			}
			visitsStream.close();
			
		}// end for
		//patientsStream.close(); // not in phase 2
		
	}
	
}