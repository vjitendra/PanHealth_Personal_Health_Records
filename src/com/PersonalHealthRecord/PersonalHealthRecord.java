package com.PersonalHealthRecord;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalHealthRecord extends Activity {
	
	TextView offLine;
	TextView text1;
	EditText uname;
	EditText pwd;
	Button login;
	Button reset;
	TextView lo;
	Session ss = new Session();
	DBAdapter db = new DBAdapter(this);
	private static final String NAMESPACE = "http://tempuri.org/";
		private static final String URL = 
			"http://pancare.panhealth.com/test/Service.asmx";	
		private static final String SOAP_ACTION = "http://tempuri.org/getMemberinfo";
		private static final String METHOD_NAME = "getMemberinfo";
		TextView register;
		//TextView forgot_pwd;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login1);
        
        db.open();
        
        
        	//  First, get the Display from the WindowManager 
          Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

          // Now we can retrieve all display-related infos 
          int width = display.getWidth();
          int height = display.getHeight();
          int orientation = display.getOrientation();
          
          // 3 - hori                  0 - portrait
          setContentView(R.layout.login1);
          register = (TextView)findViewById(R.id.register);
          //forgot_pwd = (TextView)findViewById(R.id.forgot_pwd);
          lo = (TextView)findViewById(R.id.lo);
          lo.setPadding(0, 20, 0, 0);
          
          
          work();
          if(width > height)
          {
          	   RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
               Resources res = getResources(); //resource handle
               Drawable drawable = res.getDrawable(R.drawable.background_2); //new Image that was added to the res folder

               rLayout.setBackgroundDrawable(drawable);
               
               //lo.setPadding(0, 40, 0, 0);

          }
          else if(width < height)
          {
          	  RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
              Resources res = getResources(); //resource handle
              Drawable drawable = res.getDrawable(R.drawable.background_1); //new Image that was added to the res folder

              rLayout.setBackgroundDrawable(drawable);

              //lo.setPadding(0, 250, 0, 0);
              //lo.setPadding(0, 40, 0, 0);
          }
          work();
        /* Toast.makeText(getBaseContext(), 
          		"orientation: "+orientation+"\nwidth: "+width+"\nheight: "+height,
          		Toast.LENGTH_LONG).show();*/
          
         
      }
      
    	public boolean isOnline() 
    	{
    			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    			NetworkInfo netInfo = cm.getActiveNetworkInfo();
    			if (netInfo != null && netInfo.isConnectedOrConnecting()) 
    			{
    				return true;
    			}
    			return false;
    	}
    
      private void reset_func()
      {
      	uname.setText("");
      	pwd.setText("");
      }
      
      void workOffLine()
      {
    	  uname.setText("");
    	  pwd.setText("");
    	  text1.setText("");
    	  offLine.setText("");
    	  register.setText("");
    	 // forgot_pwd.setText("");
    	  ss.setSessionworkOffLine(1);
    	   	 
    	   	   	  
    	  reset = (Button)findViewById(R.id.reset);
          reset.setOnClickListener(new Button.OnClickListener() 
  		{ public void onClick (View v)
  			{ 
  				reset.setBackgroundResource(R.drawable.reset_h);
  				reset_func();
  				reset.setBackgroundResource(R.drawable.reset);
  			}
  		});
     
    	  
    	  login = (Button)findViewById(R.id.login);
          login.setOnClickListener(new Button.OnClickListener() 
  			{ public void onClick (View v)
  				{ 
  					
  					login.setBackgroundResource(R.drawable.login_h);
  					if((uname.getText().toString()).equals(""))
  						text1.setText("Uname not entered!!");
  					else if((pwd.getText().toString()).equals(""))
  						text1.setText("Password not entered!!");
  					else if(db.checkLogin(createUID(uname.getText().toString()), pwd.getText().toString()))
  					{
  						ss.setSessionMemberID(createUID(uname.getText().toString()));	
  						ss.setSessionMemberName(db.getUserName(createUID(uname.getText().toString())));
	    		
  						Intent i = new Intent(PersonalHealthRecord.this, HomePage.class);
  						startActivity(i);
  					}

  					else
  						text1.setText("Invalid Login!!");
  					
  					//login.setBackgroundResource(R.drawable.login);
  				}
  			});
    }
      
      String createUID(String uname3)
      {
    	  String memID = uname3;
			String userID = "A";
			for(int i=0; i<(12-memID.length()); i++)
			{
				userID = userID+"0";
			}
			if(memID.contains("A"))
	 			memID = memID.replace("A", "");
	 		else if(memID.contains("a"))
	 			memID = memID.replace("a", "");
			
		    userID = userID + memID;
		    
		    return userID;
      }

      
      void checkValidate()
      {
      	if((uname.getText().toString()).equals(""))
      	   		text1.setText("Uname not entered!!");
      	else if((pwd.getText().toString()).equals(""))
      	   		text1.setText("Password not entered!!");
      	else    	
      	{
      	
      		
      	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
  		
      	String uname1 = uname.getText().toString();
      	String pwd1 = pwd.getText().toString();
      	ss.setSessionMemberID(createUID(uname1));	
      	
  		request.addProperty("strUserid", uname1);
  		request.addProperty("strPass", pwd1);
  				
  		SoapSerializationEnvelope envelope = 
  			new SoapSerializationEnvelope(SoapEnvelope.VER11); 

  		envelope.setOutputSoapObject(request);
  		envelope.dotNet=true;
  		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
  		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
  		
  		try
  		{
  			androidHttpTransport.call(SOAP_ACTION, envelope);
  			Object resultsRequestSOAP = (Object) envelope.getResponse();
  			//Toast.makeText(getBaseContext(), "Data Retrieved Successfully",Toast.LENGTH_SHORT).show();
  			
  		//	text1.setText("Rply: "+resultsRequestSOAP);
  			
  			
  			if((resultsRequestSOAP.toString()).equals("False"))
  				text1.setText("Invalid Login!!");
  			else
  			{
  				ss.setSessionMemberName(resultsRequestSOAP.toString());
  	    		
  	    		Intent i = new Intent(PersonalHealthRecord.this, HomePage.class);
  	            startActivity(i);
  	            
  			}
  			
  		} catch (Exception e) {
  			text1.setText("Please check your Internet Connection...");
  			/*offLine.setText(R.string.click_offline);
  			offLine.setOnClickListener(new Button.OnClickListener() 
  	   		{ public void onClick (View v)
  	   			{ 
  	   				workOffLine();
  	   			}
  	   		});*/
  			e.printStackTrace();
  		}

      	
      	}
      	
      
      }
      
      void work()
      {
    	   uname = (EditText)findViewById(R.id.uname);
    	   uname.setText("");
    	   
           pwd = (EditText)findViewById(R.id.pwd);
           pwd.setText("");
           
           text1 = (TextView)findViewById(R.id.text1);
           text1.setText("");
           
           offLine = (TextView)findViewById(R.id.offLine);
           
           if(!(isOnline()))
           {
	        	text1.setText("Internet Connection is not available...");
	        	//offLine.setText(R.string.click_offline);
	        	/*offLine.setOnClickListener(new Button.OnClickListener() 
	  	   		{ public void onClick (View v)
	  	   			{ 
	  	   				workOffLine();
	  	   			}
	  	   		});*/
	  	   		
           }
           
           //register = (TextView)findViewById(R.id.register);
           	register.setText(R.string.register);
     	  	register.setOnClickListener(new Button.OnClickListener() 
     		{ public void onClick (View v)
     			{ 
     			
     			//Toast.makeText(getBaseContext(), "Register",Toast.LENGTH_SHORT).show();
     				Intent i = new Intent(PersonalHealthRecord.this, RegisterPatient.class);
 					startActivity(i);
     			}
     		});
           
     	  	/*forgot_pwd.setText(R.string.forgot_pws);
     	  	forgot_pwd.setOnClickListener(new Button.OnClickListener() 
     		{ public void onClick (View v)
     			{ 
     			//Toast.makeText(getBaseContext(), "Register",Toast.LENGTH_SHORT).show();
     				Intent i = new Intent(PersonalHealthRecord.this, ForgotPassword.class);
 					startActivity(i);
     			}
     		});*/
     	  	
           login = (Button)findViewById(R.id.login);
           login.setOnClickListener(new Button.OnClickListener() 
   		{ public void onClick (View v)
   			{ 
   				login.setBackgroundResource(R.drawable.login_h);
   				checkValidate();
   				//login.setBackgroundResource(R.drawable.login);
   			}
   		});
           
           reset = (Button)findViewById(R.id.reset);
           reset.setOnClickListener(new Button.OnClickListener() 
   		{ public void onClick (View v)
   			{ 
   				reset.setBackgroundResource(R.drawable.reset_h);
   				reset_func();
   				//reset.setBackgroundResource(R.drawable.reset);
   			}
   		});
           
      }
      
      @Override
      public void onConfigurationChanged(Configuration newConfig) {

          super.onConfigurationChanged(newConfig);

          if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        	  RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
              Resources res = getResources(); //resource handle
              Drawable drawable = res.getDrawable(R.drawable.background_2); //new Image that was added to the res folder

              rLayout.setBackgroundDrawable(drawable);
              
             // lo.setPadding(0, 250, 0, 0);
              //lo.setPadding(0, 40, 0, 0);

          } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        	  RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
              Resources res = getResources(); //resource handle
              Drawable drawable = res.getDrawable(R.drawable.background_1); //new Image that was added to the res folder

              rLayout.setBackgroundDrawable(drawable);
              
             // lo.setPadding(0, 40, 0, 0);
          }
      }
}