package com.example.triage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import android.app.Activity;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;

/**
 * @author Pruthvi
 *
 */
public class SelectActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {
	
	/**
	 * 
	 */
	public int tempI;
	/**
	 * 
	 */
	public String hcnNpS;

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}
	
	/**
	 * @param view
	 */
	public void addPatient(View view) {
		EditText bd = (EditText) findViewById(R.id.bd);
		EditText nm = (EditText) findViewById(R.id.nm);
		EditText hcn = (EditText) findViewById(R.id.hcn);
		try {
			int hcnS = Integer.parseInt(hcn.getText().toString());
			String nmS = nm.getText().toString();
			String bdS = bd.getText().toString();
			Login.allPatientdb.addPatient(hcnS, nmS, bdS);
			Toast.makeText(this, "New Patient Added", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(this, "INVALID PATIENT INFORMATION", Toast.LENGTH_SHORT).show();
		}	
	}
	
	/**
	 * @param view
	 */
	public void getPatient(View view) {
		TextView bd = (TextView) findViewById(R.id.bd);
		TextView nm = (TextView) findViewById(R.id.nm);
		EditText hcn = (EditText) findViewById(R.id.hcn);
		TextView hcn1 = (TextView) findViewById(R.id.hcn1);
		File file = new File(Login.dir, Login.text);
		try {
			nm.setText(Integer.toString(Login.allPatientdb.numPatients));
			String hcnS = hcn.getText().toString();
			bd.setText(String.valueOf(file.exists()));
			int i = Login.allPatientdb.getIndex(Integer.parseInt(hcnS));
			if (i != -1) {
				nm.setText(Login.allPatientdb.patientFiles.get(i).getName());
				bd.setText(Login.allPatientdb.patientFiles.get(i).getBirthdate());
				hcn1.setText(Integer.toString(Login.allPatientdb.patientFiles.get(i).getHealthNum()));
				tempI = i;
			}
			else {
				nm.setText("Patient not found.");
				bd.setText("N/A");
				hcn1.setText("N/A");
			}
		} catch (Exception e) {
			Toast.makeText(this, "INVALID PATIENT INFORMATION", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * @param view
	 */
	public void vitalHist(View view) {
		try {
			EditText hcn = (EditText) findViewById(R.id.hcn);
			String hcnS = hcn.getText().toString();
			int i = Login.allPatientdb.getIndex(Integer.parseInt(hcnS));
			if (i != -1){
				onNavigationDrawerItemSelected(6);
			} else {
				Toast.makeText(this, "Patient Not Found", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(this, "Invalid Information", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * @param view
	 */
	public void getList(View view) {
		ArrayList<String> uP = new ArrayList<String>();
		TextView L = (TextView) findViewById(R.id.L);
		String all = "";
		try {
			for (int i = 0; i < Login.allPatientdb.numPatients; i++) {
				if (Login.allPatientdb.patientFiles.get(i).getNumVisits() > 0) {
					int nV = Login.allPatientdb.patientFiles.get(i).getNumVisits();
					if (Login.allPatientdb.patientFiles.get(i).visitRecords.get(nV-1).getNumReadings() > 0) {
						int nN = Login.allPatientdb.patientFiles.get(i).visitRecords.get(nV-1).getNumReadings();
						int jj = Login.allPatientdb.patientFiles.get(i).visitRecords.get(nV-1).vitalSigns.get(nN-1).getUrgencyPoints();
						String name = Login.allPatientdb.patientFiles.get(i).getName();
						int hN = Login.allPatientdb.patientFiles.get(i).getHealthNum();
						
						if (Login.allPatientdb.patientFiles.get(i).visitRecords.get(nV-1).vitalSigns.get(nN-1).getUrgencyPoints() >= 0) {
							for (int p = 0; p < uP.size(); p++) {
								if (Integer.parseInt((String) uP.get(p).substring(uP.get(p).length() - 2, uP.get(p).length() - 1)) < jj) {
									uP.add(name + " - " + Integer.toString(hN) + " - " + Integer.toString(jj)+"\n");
								}
							} 
						} uP.add(name + " - " + Integer.toString(hN) + " - " + Integer.toString(jj)+"\n");
					} else {
						;
					}
				} else {
					;
				}
			}
			
			String temp;
			Boolean bool = true;
			
			while(bool) {
				bool = false;
				for (int l = 0; l < uP.size()-1; l++) {
					if ((Integer.parseInt((String) uP.get(l).substring(uP.get(l).length() - 2, uP.get(l).length() - 1)) < (Integer.parseInt((String) uP.get(l+1).substring(uP.get(l+1).length() - 2, uP.get(l+1).length() - 1))))) {
						temp = uP.get(l);
						uP.set(l, uP.get(l+1));
						uP.remove(l);
						uP.set(l+1, temp);
						bool = true;
					}
				}
			}
			all += "Name - Health Card Number - UrgencyPoints";
			for (int r = 0; r < uP.size(); r++) {
				all += uP.get(r);
			}
			L.setText(all);
		} catch (Exception e) {
			Toast.makeText(this, "Wooooooooo!!!!", Toast.LENGTH_SHORT).show();
		}
	}

	
	/**
	 * @param view
	 * @return
	 */
	public String getTime(View view) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date t1 = calendar.getTime();
		java.sql.Timestamp timec = new java.sql.Timestamp(t1.getTime());
		String timex = timec.toString();
		String timeF = timex.substring(3,16);
		return timeF;
	}
	
	/**
	 * @param view
	 */
	public void newVisit(View view) {
		try {
			EditText hcnNp = (EditText) findViewById(R.id.hcn);
			hcnNpS = hcnNp.getText().toString();
			int i = Login.allPatientdb.getIndex(Integer.parseInt(hcnNpS));
			if (i != -1) {
				Login.allPatientdb.patientFiles.get(i).addVisitRecord(getTime(view));
				onNavigationDrawerItemSelected(2);
			} else {
				Toast.makeText(this, "Patient Not Found", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(this, "INVALID PATIENT INFORMATION", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * @param view
	 */
	public void patHist(View view) {
		VisitRecord visit;
		VitalSigns r;
		TextView all;
		all = (TextView) findViewById(R.id.all);
		
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
	
	/**
	 * @param view
	 */
	public void go(View view) {
		try {
			EditText hcn = (EditText) findViewById(R.id.hcn);
			String hcnS = hcn.getText().toString();
			int i = Login.allPatientdb.getIndex(Integer.parseInt(hcnS));
			if (i != -1){
				Toast.makeText(this, "Please Enter Vital Signs", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Patient Not Found", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(this, "Please Enter a Health Card Number", Toast.LENGTH_SHORT).show();
		}	
	}


	/**
	 * @param bpsF
	 * @param bpdF
	 * @param tempF
	 * @param hrF
	 * @param age
	 * @return
	 */
	public int urgencyPoints(double bpsF, double bpdF, double tempF, double hrF, int age) {
		int uP = 0;
		if (hrF >= 100 || hrF <= 50) {
			uP ++;
		}
		if (bpsF >= 140) {
			uP ++;
		}
		if (bpdF >= 90) {
			uP ++;
		}
		if (tempF >= 39) {
			uP ++;
		}
		if (2014 - age <= 2) {
			uP ++;
		}
		return uP;
	}
	
	
	/**
	 * @param view
	 */
	public void addVitalSigns(View view) {
		double bpsF;
		double bpdF;
		double hrF;
		double tempF;
		
		if (((EditText) findViewById(R.id.bps)).getText().equals(null) || ((EditText) findViewById(R.id.bps)).getText().equals("")) {
			bpsF = 0;
		} else {
			bpsF = Double.parseDouble(((EditText) findViewById(R.id.bps)).getText().toString());
		}
		if (((EditText) findViewById(R.id.bpd)).getText().equals(null) || ((EditText) findViewById(R.id.bpd)).getText().equals("")) {
			bpdF = 0;
		} else {
			bpdF = Double.parseDouble(((EditText) findViewById(R.id.bpd)).getText().toString());
		}
		if (((EditText) findViewById(R.id.hr)).getText().equals(null) || ((EditText) findViewById(R.id.hr)).getText().equals("")) {
			hrF = 75;
		} else {
			hrF = Double.parseDouble(((EditText) findViewById(R.id.hr)).getText().toString());
		}
		if (((EditText) findViewById(R.id.temp)).getText().equals(null) || ((EditText) findViewById(R.id.temp)).getText().equals("")) {
			tempF = 0;
		} else {
			tempF = Double.parseDouble(((EditText) findViewById(R.id.temp)).getText().toString());
		}
		
		int hcnF = Integer.parseInt(((EditText) findViewById(R.id.hcn)).getText().toString());
		String desF = ((EditText) findViewById(R.id.des)).getText().toString();
		
		try {
			int i = Login.allPatientdb.getIndex(hcnF);
			if (i != -1){
				int j = Login.allPatientdb.patientFiles.get(i).getNumVisits();
				int uP = urgencyPoints(bpsF, bpdF, tempF, hrF, Integer.parseInt(Login.allPatientdb.patientFiles.get(i).getBirthdate().substring(0,3)));
				Login.allPatientdb.patientFiles.get(i).visitRecords.get(j-1).newVitalSigns(getTime(view), tempF, hrF, bpsF, bpdF, uP, desF);
				Toast.makeText(this, "Patient Vitals Added!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Patient Not Found", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(this, "Add a New Visit for Patient First", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void save(View view) throws IOException {
		Login.allPatientdb.save(this);
		Toast.makeText(this, "Patient Vitals Saved", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		if (position == 0) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container, 
					PlaceholderFragment.newInstance(position + 1)).commit();
		} else if (position == 1) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container, 
					PlaceholderFragmentNpv.newInstance(position + 1)).commit();
		} else if (position == 2) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container, 
					PlaceholderFragmentUpi.newInstance(position + 1)).commit();
		} else if (position == 3) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container, 
					PlaceholderFragmentDpi.newInstance(position + 1)).commit();
		} else if (position == 4) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container, 
					PlaceholderFragmentPl.newInstance(position + 1)).commit();
		} else if (position == 5) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container, 
					PlaceholderFragmentSv.newInstance(position + 1)).commit();
		} else if (position == 6) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container, 
					PlaceholderFragmentPv.newInstance(position + 1)).commit();
		}
	}

	/**
	 * @param number
	 */
	public void onSectionAttached(int number) {
		switch (number) {
        case 1:
            mTitle = "Add New Patient";
            break;
        case 2:
            mTitle = "Add New Patient Visit";
            break;
        case 3:
            mTitle = "Update Patient Info.";
            break;
        case 4:
        	mTitle = "View Patient History";
        	break;
        case 5:
        	mTitle = "Patient List";
        	break;
        case 6:
        	mTitle = "Save Vitals";
        	break;
		}
	}
	
	/**
	 * 
	 */
	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.select, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
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
