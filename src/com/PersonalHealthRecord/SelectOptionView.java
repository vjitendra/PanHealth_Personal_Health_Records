package com.PersonalHealthRecord;

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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SelectOptionView extends Activity{
	
	Button option;
	Button back;
	Session ss = new Session();
	LinearLayout l11;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.select_option2);
        l11 = (LinearLayout)findViewById(R.id.l10);
        l11.setPadding(0, 20, 0, 0);
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
              
           //   l11.setPadding(0, 20, 0, 0);

         }	
        else if(width < height)
        {
         	 RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
              Resources res = getResources(); //resource handle
              Drawable drawable = res.getDrawable(R.drawable.background_1); //new Image that was added to the res folder

              rLayout.setBackgroundDrawable(drawable);
              
            //  l11.setPadding(0, 150, 0, 0);

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
                
        TextView userIDT = (TextView)findViewById(R.id.userID);
        userIDT.setText("Patient ID: "+userID);
       
        
        ImageButton glucose = (ImageButton)findViewById(R.id.glucose);
        glucose.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				ss.setSessionCurrentLog("Blood Glucose Log");
				Intent i = new Intent(SelectOptionView.this, ViewData.class);
				startActivity(i);
			}
		});
        
        ImageButton pressure = (ImageButton)findViewById(R.id.pressure);
        pressure.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
			ss.setSessionCurrentLog("Blood Pressure Log");
				Intent i = new Intent(SelectOptionView.this, ViewData.class);
				startActivity(i);
			}
		});
        
        ImageButton weight = (ImageButton)findViewById(R.id.weight);
        weight.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
			ss.setSessionCurrentLog("Personal Weight Log");
			Intent i = new Intent(SelectOptionView.this, ViewData.class);
			startActivity(i);
			}
		});
        
        ImageButton HbA1c = (ImageButton)findViewById(R.id.HbA1c);
        HbA1c.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
			ss.setSessionCurrentLog("Personal HbA1c Log");
				Intent i = new Intent(SelectOptionView.this, ViewData.class);
				startActivity(i);
			}
		});
        
        ImageButton hemoglobin = (ImageButton)findViewById(R.id.hemoglobin);
        hemoglobin.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
			ss.setSessionCurrentLog("Personal Hemoglobin Log");
				Intent i = new Intent(SelectOptionView.this, ViewData.class);
				startActivity(i);
			}
		});
        
        /*ImageButton manual = (ImageButton)findViewById(R.id.manual);
        manual.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				Intent i = new Intent(SelectOption.this, EnterGlucose.class);
				startActivity(i);
			}
		});*/
        
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				back.setBackgroundResource(R.drawable.back_h);
				Intent i = new Intent(SelectOptionView.this, HomePage.class);
				startActivity(i);
			}
		});
        
       
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
       	 RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
            Resources res = getResources(); //resource handle
            Drawable drawable = res.getDrawable(R.drawable.background_1); //new Image that was added to the res folder

            rLayout.setBackgroundDrawable(drawable);
            
          //  l11.setPadding(0, 150, 0, 0);

       } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
       	 RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
            Resources res = getResources(); //resource handle
            Drawable drawable = res.getDrawable(R.drawable.background_2); //new Image that was added to the res folder

            rLayout.setBackgroundDrawable(drawable);
            
          //  l11.setPadding(0, 20, 0, 0);
       }
    }

}
