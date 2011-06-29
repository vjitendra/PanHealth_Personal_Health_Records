package com.PersonalHealthRecord;

import java.util.Calendar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EnterHbA1c extends Activity{
	private int mYear;
    private int mMonth;
    private int mDay;
    private static final String NAMESPACE = "http://tempuri.org/";
	private static final String URL = 
		"http://pancare.panhealth.com/test/Service.asmx";	
	private static final String SOAP_ACTION = "http://tempuri.org/UpdatePhr";
	private static final String METHOD_NAME = "UpdatePhr";
    static final int DATE_DIALOG_ID = 0;
	Button submit;	
	TextView dateDisplay;
	TextView mTimeDisplay;
	private int mHour;
    private int mMinute;
    TextView uieditbox;
    
    static final int TIME_DIALOG_ID = 1;
    DBAdapter db = new DBAdapter(this);
    Session ss = new Session();
    Button back;
    ImageButton changeDate;
    ImageButton changeTime;
    LinearLayout l11;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.enter_hbac1);
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
              
             // l11.setPadding(0, 20, 0, 0);

         }	
        else if(width < height)
        {
         	 RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
              Resources res = getResources(); //resource handle
              Drawable drawable = res.getDrawable(R.drawable.background_1); //new Image that was added to the res folder

              rLayout.setBackgroundDrawable(drawable);
              
             // l11.setPadding(0, 150, 0, 0);

         }
        work();
        
      
    }
    
    DatePickerDialog.OnDateSetListener mDateSetListener =
        new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, 
                                  int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                updateDisplay();
            }
        };
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        case TIME_DIALOG_ID:
            return new TimePickerDialog(this,
                    mTimeSetListener, mHour, mMinute, false);
        }
        return null;
    }
    
    void updateDisplay() {
    	dateDisplay.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("/")
                    .append(mDay).append("/")
                    .append(mYear).append(" "));
    }
    
    void updateDisplay1() {
    	String str = ":00";
    	/*if(mHour>12)
    	{
			mHour=mHour-12;
			str=":00 PM";
    	}*/
        mTimeDisplay.setText(
            new StringBuilder()
                    .append(pad(mHour)).append(":")
                    .append(pad(mMinute)).append(str));
    }

    TimePickerDialog.OnTimeSetListener mTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                updateDisplay1();
            }
        };
    
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    void work()
    {
    	db.open();
        
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
    	
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				back.setBackgroundResource(R.drawable.back_h);
				Intent i = new Intent(EnterHbA1c.this, SelectOption.class);
				startActivity(i);
			}
		});  
        
        changeDate = (ImageButton)findViewById(R.id.changeDate);
        changeDate.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				showDialog(DATE_DIALOG_ID);
			}
		});
        
        changeTime = (ImageButton)findViewById(R.id.changeTime);
        changeTime.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				showDialog(TIME_DIALOG_ID);
			}
		});
        
        dateDisplay = (TextView)findViewById(R.id.dateDisplay);
       
        
        mTimeDisplay = (TextView)findViewById(R.id.mTimeDisplay);
       
        
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        updateDisplay();
        updateDisplay1();
        
        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				submit.setBackgroundResource(R.drawable.submit_h);	
				String date_time = dateDisplay.getText().toString()+""+mTimeDisplay.getText().toString();
				uieditbox = (EditText)findViewById(R.id.uieditbox); 
				 
				
				System.out.println("Date: "+date_time+" editbox: "+uieditbox.getText().toString());
				
				String memId = ss.getSessionMemberID();
						
				//int value = Integer.parseInt(uieditbox.getText().toString());
				submit.setBackgroundResource(R.drawable.submit);
				
				if(!((uieditbox.getText().toString()).equals("")))
				{	
											
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        		
        		request.addProperty("MemberID", memId);
        		request.addProperty("DataType", "hba1c");
        		request.addProperty("ComDeviceID", "SPB502");
        		request.addProperty("ComDeviceSrNo", "NVX8696SPB");
        		request.addProperty("hba1c", uieditbox.getText().toString());
        		request.addProperty("Source", "Android");
        		request.addProperty("Method", "Web");
        		request.addProperty("dtReadingDT", date_time);//date_time.toString());// "01/27/2011 HH:MM:00 AM"
        		//request.addProperty("dtReadingDT", "03/23/2011 08:45:00");//date_time.toString());// "01/27/2011 HH:MM:00 AM"
        		
        		
        		SoapSerializationEnvelope envelope = 
        			new SoapSerializationEnvelope(SoapEnvelope.VER11); 

        		envelope.setOutputSoapObject(request);
        		envelope.dotNet=true;
        		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
        		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        				//$theVariable = array('MemberID'=> 'A4303','DataType'=> 'BG','ComDeviceID'=>'SPB502','ComDeviceSrNo'=>'NVX8696SPB','TstConFood'=>'A','Bg'=> $bg_value[$i],'ReadingDT' => $bg_date[$i],'Source' => '108 Medical Id','Method' => 'WEB');
        		
        		
        		try {
        			//Toast.makeText(getBaseContext(), "Data Uploaded Successfully",Toast.LENGTH_SHORT).show();
        			androidHttpTransport.call(SOAP_ACTION, envelope);
        			SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
        			
        			long id = db.insertLog(memId, "HbA1c", "SPB502", "NVX8696SPB", 
        					"",0,0,0,0,0,0,
        					Integer.parseInt(uieditbox.getText().toString()),
        					"",0,"", date_time, "y");
    				
    				if(id>0)
    					System.out.println("Added Successfully!");
    				else
    					System.out.println("Failed to Add!");
        			
    				TextView text = (TextView)findViewById(R.id.text);
					text.setText("Data Uploaded Successfully!!");
    				
					uieditbox.setText("");
					final Calendar c = Calendar.getInstance();
			        mYear = c.get(Calendar.YEAR);
			        mMonth = c.get(Calendar.MONTH);
			        mDay = c.get(Calendar.DAY_OF_MONTH);
			        mHour = c.get(Calendar.HOUR_OF_DAY);
			        mMinute = c.get(Calendar.MINUTE);

			        updateDisplay();
			        updateDisplay1();
        			        			
        		} catch (Exception e) {
        			
        			long id = db.insertLog(memId, "HbA1c", "SPB502", "NVX8696SPB", 
        					"",0,0,0,0,0,0,
        					Integer.parseInt(uieditbox.getText().toString()),
        					"",0,"", date_time, "n");
    				
    				if(id>0)
    					System.out.println("Added Successfully!");
    				else
    					System.out.println("Failed to Add!");
        			
        			TextView text = (TextView)findViewById(R.id.text);
					text.setText("As no Internet connection..\nData added to local database..");
					uieditbox.setText("");
					final Calendar c = Calendar.getInstance();
			        mYear = c.get(Calendar.YEAR);
			        mMonth = c.get(Calendar.MONTH);
			        mDay = c.get(Calendar.DAY_OF_MONTH);
			        mHour = c.get(Calendar.HOUR_OF_DAY);
			        mMinute = c.get(Calendar.MINUTE);

			        updateDisplay();
			        updateDisplay1();
        			e.printStackTrace();
        		}

        		
			}
			else
			{
				TextView text = (TextView)findViewById(R.id.text);
				text.setText("Data not Entered!!");
			}
           	 
           	
				
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
            
           // l11.setPadding(0, 150, 0, 0);

       } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
       	 RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
            Resources res = getResources(); //resource handle
            Drawable drawable = res.getDrawable(R.drawable.background_2); //new Image that was added to the res folder

            rLayout.setBackgroundDrawable(drawable);
            
           // l11.setPadding(0, 20, 0, 0);
       }
    }
}

