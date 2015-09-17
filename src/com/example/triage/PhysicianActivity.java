package com.example.triage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PhysicianActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physician);
	}
	
	public void vitalHist(View view) {
		try {
			EditText hcnNp = (EditText) findViewById(R.id.hcn);
			String hcnNpS = hcnNp.getText().toString();
			int i = Login.allPatientdb.getIndex(Integer.parseInt(hcnNpS));
			if (i != -1){
				Intent intentPhy = new Intent(getApplicationContext(), Dpi.class);
				intentPhy.putExtra("hcnNpS",hcnNpS);
				startActivity(intentPhy);
			} else {
				Toast.makeText(this, "Patient Not Found", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(this, "Invalid Information", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void addPhyVisit(View view) {
		Intent intentPhy = new Intent(getApplicationContext(), Physicians.class);
		startActivity(intentPhy);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.physician, menu);
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
