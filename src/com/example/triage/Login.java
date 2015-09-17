package com.example.triage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.example.triage.DataBase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends Activity {

	public static DataBase allPatientdb;
	public static String dir = "data/data/com.example.triage/files/";
	public static String text = "patient_records.txt";
	public static String textP = "passwords.txt";
	private ArrayList<String> userNames = new ArrayList<String>(); 
	private ArrayList<String> passWords = new ArrayList<String>(); 
	private ArrayList<String> accountType = new ArrayList<String>(); 
	private int numUsers=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        CheckBox checkbox = (CheckBox) findViewById(R.id.checkbox);
        final EditText password = (EditText) findViewById(R.id.password);
    	
    	checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
    		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    			if (!isChecked) {
    				password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    			} else {
    				password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    			}
            }
        });
    }
    
    public String getDir(){
    	return text;
    }
    
    public void login(View view) throws IOException {
    	Intent intent = new Intent(this, SelectActivity.class);
    	Intent intentPhy = new Intent(this, PhysicianActivity.class);
    	           
    	EditText username = (EditText) findViewById(R.id.username);
    	String usernameS = username.getText().toString();
    	EditText password = (EditText) findViewById(R.id.password);
    	String passwordS = password.getText().toString();
    	TextView tv = (TextView) findViewById(R.id.tv);
    	
    	loadPasswords(dir, textP);
    	// tv.setText(userNames.get(4) + " " + passWords.get(4));
    	// tv.setText(usernameS);

		if (userNames.contains(usernameS)) {
			int index = userNames.indexOf(usernameS);
			if (passwordS.equals(passWords.get(index))) {
				if (accountType.get(index).equals("nurse")) {
					allPatientdb = new DataBase(text, dir);
					startActivity(intent);
				} else if (accountType.get(index).equals("physician")){
					allPatientdb = new DataBase(text, dir);
					startActivity(intentPhy);
				} else {
					tv.setText(accountType.get(index));
					Toast.makeText(this, "Internal Error (We dont give a Fuck)", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, "Invalid Credentials Password", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
		}
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
    
    //added these three methods, two variables and an import for java.io.File
    
    /**
     * Loads data from the file given by dir and fileName into ArrayLists userNames, passWords and accountType.
     * Each account has its own index shared between all three ArrayLists, which is how the account information is separated.
     * @param dir the directory of the file containing user names, passwords and account types
     * @param fileName name of the file containing user names, passwords and account types
     * @throws IOException
     */
    public void loadPasswords(String dir, String fileName) throws IOException{
		BufferedReader pass = new BufferedReader(new InputStreamReader(new FileInputStream(new File(dir, fileName))));
		
    	String line = pass.readLine();
    	while(line != null){
    		userNames.add(line.split(",")[0]);
    		passWords.add(line.split(",")[1]);
    		accountType.add(line.split(",")[2]);
    		numUsers++;
    		line = pass.readLine();
    	}
    	pass.close();
    }
    
    /**
     * Returns True if the given password matches the given user name, 
	 * otherwise returns False.
     * @param user user name of the account
     * @param pass password being checked against account
     * @return whether or not the password matched the user name.
     */
    public boolean passwordCheck(String user, String pass){
		for(int i=0; i < numUsers; i++){
			if(user == userNames.get(i)){
				return pass == passWords.get(i);
			}
		}
    	return false;
    }
    /**
     * returns the account type of the account with given account name.
     * @param user the user name of the account having its type checked
     * @return the type of account
     */
    public String getAccountType(String user){
    	for(int i=0; i < numUsers; i++){
			if(user == userNames.get(i)){
				return accountType.get(i);
			}
    	}
    	return "account not found";
    }
}

