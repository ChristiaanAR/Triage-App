package com.example.triage;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Dpi extends Activity {

	public int tempI;
	public String hcnS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dpi);
		hcnS = getIntent().getStringExtra("hcnNpS");
	}
	
	public void getPatient(View view) {
		//EditText hcn = (EditText) findViewById(R.id.hcn);
		//String hcnS = hcn.getText().toString();
		// File file = new File(Login.dir, Login.text);
		try {
			
			int i = Login.allPatientdb.getIndex(Integer.parseInt(hcnS));
			if (i != -1) {
				tempI = i;
				Toast.makeText(this, "Patient History For: " + hcnS, Toast.LENGTH_SHORT).show();
			}
			else {
				;
			}
		} catch (Exception e) {
			Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void patHist(View view) {
		VisitRecord visit;
		VitalSigns r;
		TextView all;
		all = (TextView) findViewById(R.id.all);
		getPatient(view);
		String hist = "";
		for (int j = 0; j < Login.allPatientdb.patientFiles.get(tempI).getNumVisits(); j++) {
			visit = Login.allPatientdb.patientFiles.get(tempI).visitRecords.get(j);
			hist += "Visit " + (j+1) + ": Arrival Time: " + visit.getArrivalTime() + "\n";
			for(int k = 0; k < visit.getNumReadings(); k++){
				r = visit.vitalSigns.get(k);
				hist += " Reading " + (k+1) + ": Time Stamp: " + r.getTimeStamp() + "\n";
				hist += "   Body Temperature: " + r.getBodyTemp() + "\n   Heart Rate: " + r.getheartRate() 
						+ "\n   Blood Pressure Systolic: " + r.getBPSystolic() + "\n   Blood Pressure Diastolic: " 
						+ r.getBPDiastolic() + "\n   Urgency Points: " + r.getUrgencyPoints() + "\n   Description: "
						+ r.getTextDescription() + "\n";
			}
		} 
		if (hist == "") {
			all.setText("No Patient History Found: Try Adding Something First");
		} else {
			all.setText(hist);
		}
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dpi, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
