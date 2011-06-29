package com.PersonalHealthRecord;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SyncDataToPanHealthOption extends Activity{
	Button back;
	Session ss = new Session();
	DBAdapter db = new DBAdapter(this);
	private static final String NAMESPACE = "http://tempuri.org/";
	private static final String URL = 
		"http://pancare.panhealth.com/test/Service.asmx";	
	private static final String SOAP_ACTION = "http://tempuri.org/UpdatePhr";
	private static final String METHOD_NAME = "UpdatePhr";
	String MemberID="";
	TextView text;
	LinearLayout l11;
	
	 /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       
       setContentView(R.layout.sync_to_panhealth_option);
       l11 = (LinearLayout)findViewById(R.id.l1);
       l11.setPadding(0, 20, 0, 0);
       
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
             
           //  l11.setPadding(0, 20, 0, 0);

        }	
       else if(width < height)
       {
        	 RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
             Resources res = getResources(); //resource handle
             Drawable drawable = res.getDrawable(R.drawable.background_1); //new Image that was added to the res folder

             rLayout.setBackgroundDrawable(drawable);
             
          //   l11.setPadding(0, 150, 0, 0);

        }
       work();
       
   }
   void All()
   {
	   glucose();
	   Pressure();
	   Weight();
	   Hemoglobin();
	   HbA1c();
   }
   
   void HbA1c()
   {
	   Cursor mCursor = db.SyncHbA1cLog(MemberID);
	   if(mCursor.getCount() > 0)
		 {
		 while(mCursor.moveToNext())
	     {
			 int gid = Integer.parseInt(mCursor.getString(0));
			 int hba1c = Integer.parseInt(mCursor.getString(1));
			 String date_time = mCursor.getString(2);
			 
			 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
     		
     		request.addProperty("MemberID", MemberID);
     		request.addProperty("DataType", "hba1c");
     		request.addProperty("ComDeviceID", "SPB502");
     		request.addProperty("ComDeviceSrNo", "NVX8696SPB");
     		request.addProperty("hba1c", hba1c);
     		request.addProperty("Source", "Android");
     		request.addProperty("Method", "Web");
     		request.addProperty("dtReadingDT", date_time);
            		
     		SoapSerializationEnvelope envelope = 
     			new SoapSerializationEnvelope(SoapEnvelope.VER11); 

     		envelope.setOutputSoapObject(request);
     		envelope.dotNet=true;
     		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
     		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
     			
     		
     		
     		try {
     			//Toast.makeText(getBaseContext(), "Data Uploaded Successfully",Toast.LENGTH_SHORT).show();
     			androidHttpTransport.call(SOAP_ACTION, envelope);
     			SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
     			
     			db.updateAllLog(gid, "y");
     			
     			text.append("\nHbA1c Data Sync successfully!!");
     			
     		} catch (Exception e) {
     			
     			
     			text.append("\nProblem for Sync the HbA1c Data...\nPlease check your internet connection...");
     			e.printStackTrace();
     		}
	     }
		 }
		 else
			 text.append("\nNo more Hemoglobin Data to Sync...");
   }
   
   void Hemoglobin()
   {
	   Cursor mCursor = db.SyncHGLog(MemberID);
	   if(mCursor.getCount() > 0)
		 {
		 while(mCursor.moveToNext())
	     {
			 int gid = Integer.parseInt(mCursor.getString(0));
			 int hg = Integer.parseInt(mCursor.getString(1));
			 String date_time = mCursor.getString(2);
			 
			 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
     		
     		request.addProperty("MemberID", MemberID);
     		request.addProperty("DataType", "HG");
     		request.addProperty("ComDeviceID", "SPB502");
     		request.addProperty("ComDeviceSrNo", "NVX8696SPB");
     		request.addProperty("HG", hg);
     		request.addProperty("Source", "Android");
     		request.addProperty("Method", "Web");
     		request.addProperty("dtReadingDT", date_time);
            		
     		SoapSerializationEnvelope envelope = 
     			new SoapSerializationEnvelope(SoapEnvelope.VER11); 

     		envelope.setOutputSoapObject(request);
     		envelope.dotNet=true;
     		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
     		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
     			
     		
     		
     		try {
     			//Toast.makeText(getBaseContext(), "Data Uploaded Successfully",Toast.LENGTH_SHORT).show();
     			androidHttpTransport.call(SOAP_ACTION, envelope);
     			SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
     			
     			db.updateAllLog(gid, "y");
     			
     			text.append("\nHemoglobin Data Sync successfully!!");
     			
     		} catch (Exception e) {
     			
     			
     			text.append("\nProblem for Sync the Hemoglobin Data...\nPlease check your internet connection...");
     			e.printStackTrace();
     		}
	     }
		 }
		 else
			 text.append("\nNo more Hemoglobin Data to Sync...");
	   
   }
   
   void Weight()
   {
	   Cursor mCursor = db.SyncWTLog(MemberID);
	   if(mCursor.getCount() > 0)
		 {
		 while(mCursor.moveToNext())
	     {
			 int gid = Integer.parseInt(mCursor.getString(0));
			 String testCondition1 = mCursor.getString(1);
			 int wt = Integer.parseInt(mCursor.getString(2));
			 String testCondition = mCursor.getString(3);
			 String date_time = mCursor.getString(4);
			 
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
     		request.addProperty("MemberID", MemberID);
     		request.addProperty("DataType", "WT");
     		request.addProperty("ComDeviceID", "SPB502");
     		request.addProperty("ComDeviceSrNo", "NVX8696SPB");
     		request.addProperty("TstConFood", testCondition1);
     		request.addProperty("Source", "Android");
     		request.addProperty("Method", "Web");
     		request.addProperty("Weight", wt);
     		request.addProperty("TstConExercise", testCondition);
     		request.addProperty("dtReadingDT", date_time);// "01/27/2011 HH:MM:00 AM"
     		
				
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
     			
     			db.updateAllLog(gid, "y");
     			text.append("\nWeight Data Sync successfully!!");
					
     		} catch (Exception e) {
     			
     			text.append("\nProblem for Sync the Weight Data...\nPlease check your internet connection...");
     			e.printStackTrace();
     		}
	     }
		 }
		 else
			 text.append("\nNo more Weight Data to Sync...");
   }
   
   void Pressure()
   {
	   Cursor mCursor = db.SyncBPLog(MemberID);
	   if(mCursor.getCount() > 0)
		 {
		 while(mCursor.moveToNext())
	     {
			 int gid = Integer.parseInt(mCursor.getString(0));
			 int BPdia = Integer.parseInt(mCursor.getString(1));
			 int BPpulse = Integer.parseInt(mCursor.getString(2));
			 int BPsys = Integer.parseInt(mCursor.getString(3));
			 String date_time = mCursor.getString(4);
			 
			 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
     		
     		request.addProperty("MemberID", MemberID);
     		request.addProperty("DataType", "BP");
     		request.addProperty("ComDeviceID", "SPB502");
     		request.addProperty("ComDeviceSrNo", "NVX8696SPB");
     		request.addProperty("BPsys", BPsys);
     		request.addProperty("BPdia", BPdia);
     		request.addProperty("BPpulse", BPpulse);
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
     			
     			db.updateAllLog(gid, "y");
     			
    			text.append("\nBP Data Sync successfully!!");
     			
     			
     		} catch (Exception e) {
     		
     			text.append("\nProblem for Sync the BP data...\nPlease check your internet connection...");
     			e.printStackTrace();
     		}


	     }
		 }
		 else
			 text.append("\nNo more BP Data to Sync...");
   }
   
   void glucose()
   {
	 Cursor mCursor = db.SyncGlucoseLog(MemberID);
	 if(mCursor.getCount() > 0)
	 {
	 while(mCursor.moveToNext())
     {
		 int gid = Integer.parseInt(mCursor.getString(0));
		 String testCondition = mCursor.getString(1);
		 int bg = Integer.parseInt(mCursor.getString(2));
		 String date_time = mCursor.getString(3);
		 
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
 		
 		request.addProperty("MemberID", MemberID); 
 		request.addProperty("DataType", "BG");
 		request.addProperty("ComDeviceID", "SPB502");
 		request.addProperty("ComDeviceSrNo", "NVX8696SPB");
 		request.addProperty("TstConFood", testCondition);
 		request.addProperty("Bg", bg);
 		request.addProperty("dtReadingDT", date_time);
 		request.addProperty("Source", "Android");
 		request.addProperty("Method", "Web");
 		
 		SoapSerializationEnvelope envelope = 
 			new SoapSerializationEnvelope(SoapEnvelope.VER11); 

 		envelope.setOutputSoapObject(request);
 		envelope.dotNet=true;
 		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
 				
 		try {
 		 			
 			androidHttpTransport.call(SOAP_ACTION, envelope);
 			SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
 			
 			db.updateAllLog(gid, "y");
 			
			text.append("\nGlucose Data Sync successfully!!");
 			
 		} catch (Exception e) {

			text.append("\nProblem for Sync the glucose data...\nPlease check your internet connection...");
 			e.printStackTrace();
 		}
     }
	 }
	 else
		 text.append("\nNo more Glucose Data to Sync...");
     
	 
	 
	 
   }
   
   void work()
   {
	   db.open();
       
       MemberID = ss.getSessionMemberID();
       
       text = (TextView)findViewById(R.id.text);
       text.setText("\n");
       
       OnClickListener radio_listener = new OnClickListener() {
           public void onClick(View v) {
               // Perform action on clicks
               RadioButton rb = (RadioButton) v;
               //Toast.makeText(SelectOption.this, rb.getText(), Toast.LENGTH_SHORT).show();
                           
              /*	ss.setSessionSyncData(rb.getText().toString());
               	
               	Intent i = new Intent(SyncDataToPanHealthOption.this, SyncDataToPanHealth.class);
   				startActivity(i);*/
               
               String sync = rb.getText().toString();
               
               if(sync.equals("Blood Glucose Log"))
               {
            	   text.setText("\n");
            	   glucose();
               }
               else if(sync.equals("Blood Pressure Log"))
               {
            	   text.setText("\n");
            	   Pressure();
               }
               else if(sync.equals("Personal Weight Log"))
               {
            	   text.setText("\n");
            	   Weight();
               }
               else if(sync.equals("Hemoglobin Log"))
               {
            	   text.setText("\n");
            	   Hemoglobin();
               }
               else if(sync.equals("HbA1c Log"))
               {
            	   text.setText("\n");
            	   HbA1c();
               }
               else if(sync.equals("All Logs"))
               {
            	   text.setText("\n");
            	   All();
               }
               
           }
       };
       
       final RadioButton glucose = (RadioButton) findViewById(R.id.glucose);
       final RadioButton pressure = (RadioButton) findViewById(R.id.pressure);
       final RadioButton weight = (RadioButton) findViewById(R.id.weight);
       final RadioButton HbA1c = (RadioButton) findViewById(R.id.HbA1c);
       //final RadioButton manual = (RadioButton) findViewById(R.id.manual);
       final RadioButton hemoglobin = (RadioButton) findViewById(R.id.hemoglobin);
       final RadioButton all = (RadioButton) findViewById(R.id.all);
       
       glucose.setOnClickListener(radio_listener);
       pressure.setOnClickListener(radio_listener);
       weight.setOnClickListener(radio_listener);
       HbA1c.setOnClickListener(radio_listener);
       //manual.setOnClickListener(radio_listener);
       hemoglobin.setOnClickListener(radio_listener);
       all.setOnClickListener(radio_listener);
       
       back = (Button)findViewById(R.id.back);
       back.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				back.setBackgroundResource(R.drawable.back_h);
				Intent i = new Intent(SyncDataToPanHealthOption.this, HomePage.class);
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
           
          // l11.setPadding(0, 150, 0, 0);

      } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      	 RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
           Resources res = getResources(); //resource handle
           Drawable drawable = res.getDrawable(R.drawable.background_2); //new Image that was added to the res folder

           rLayout.setBackgroundDrawable(drawable);
           
       //    l11.setPadding(0, 20, 0, 0);
      }
  }
}

