package com.PersonalHealthRecord;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class HomePage extends Activity {
	
	TextView welcome;
	TextView userIDT;
	Button logout;
	Session ss = new Session();
	String userID="";
	String userName="";
	LinearLayout l1;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.welcome_user1);
        l1 = (LinearLayout)findViewById(R.id.l1);
        l1.setPadding(0, 20, 0, 0);
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        int width = display.getWidth();
        int height = display.getHeight();
        int orientation = display.getOrientation();
        if(width > height)
        {
        	
        	RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
            Resources res = getResources(); //resource handle
            Drawable drawable = res.getDrawable(R.drawable.background_2); //new Image that was added to the res folder

            rLayout.setBackgroundDrawable(drawable);
            
          //  l1.setPadding(0, 40, 0, 0);

        }
        else if(width < height)
        {
        	
        	RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
            Resources res = getResources(); //resource handle
            Drawable drawable = res.getDrawable(R.drawable.background_1); //new Image that was added to the res folder

            rLayout.setBackgroundDrawable(drawable);
            
        //    l1.setPadding(0, 200, 0, 0);

        }
        work();
        
      
        
       
    }

    void work()
    {
    	
    	String userID = ss.getSessionMemberID();
    	
    	/*String memID = ss.getSessionMemberID();
 		String userID = "A";
 		for(int i=0; i<(12-memID.length()); i++)
 		{
 			userID = userID+"0";
 		}
 		if(memID.contains("A"))
 			memID = memID.replace("A", "");
 		else if(memID.contains("a"))
 			memID = memID.replace("a", "");
 		
 	    userID = userID + memID;*/
                 
         userIDT = (TextView)findViewById(R.id.userID);
         userIDT.setText("Patient ID: "+userID);
         
         userName = ss.getSessionMemberName();
         welcome = (TextView)findViewById(R.id.welcome);
         welcome.setText("Welcome "+userName+" !!");
         
         
         
         logout = (Button)findViewById(R.id.logout);
         logout.setOnClickListener(new Button.OnClickListener() 
 		{ public void onClick (View v)
 			{ 
 				logout.setBackgroundResource(R.drawable.logout_h);
 				Intent i = new Intent(HomePage.this, PersonalHealthRecord.class);
 				startActivity(i);
 			}
 		});        
         
         OnClickListener radio_listener = new OnClickListener() {
             public void onClick(View v) {
                 
                 RadioButton rb = (RadioButton) v;
                                 
                 if((rb.getText()).equals("Enter Data"))
                 {
                 	Intent i = new Intent(HomePage.this, SelectOption.class);
     				startActivity(i);
                 }
                 else if((rb.getText()).equals("View local Data"))
                 {
                 	Intent i = new Intent(HomePage.this, SelectOptionView.class);
     				startActivity(i);
                 }
                /* else if((rb.getText()).equals("Add new Medication"))
                 {
                 	Intent i = new Intent(HomePage.this, AddMedication.class);
     				startActivity(i);
                 }*/
                 else if((rb.getText()).equals("Sync Data to PanHealth"))
                 {
                 	Intent i = new Intent(HomePage.this, SyncDataToPanHealthOption.class);
     				startActivity(i);
                 }
             }
         };
         
         final RadioButton enter_data = (RadioButton) findViewById(R.id.enter_data);
         final RadioButton view_data = (RadioButton) findViewById(R.id.view_data);
         final RadioButton sync_data = (RadioButton) findViewById(R.id.sync_data);
         //final RadioButton add_medication = (RadioButton) findViewById(R.id.add_medication);
         
         enter_data.setOnClickListener(radio_listener);
         view_data.setOnClickListener(radio_listener);
         sync_data.setOnClickListener(radio_listener);
         //add_medication.setOnClickListener(radio_listener);
    }
    
   @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        	RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
            Resources res = getResources(); //resource handle
            Drawable drawable = res.getDrawable(R.drawable.background_1); //new Image that was added to the res folder

            rLayout.setBackgroundDrawable(drawable);
            
         //   l1.setPadding(0, 200, 0, 0);

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        	RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
            Resources res = getResources(); //resource handle
            Drawable drawable = res.getDrawable(R.drawable.background_2); //new Image that was added to the res folder

            rLayout.setBackgroundDrawable(drawable);
            
           // l1.setPadding(0, 40, 0, 0);
        }
    }

}
