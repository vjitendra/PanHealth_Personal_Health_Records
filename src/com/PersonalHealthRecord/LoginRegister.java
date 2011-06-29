package com.PersonalHealthRecord;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginRegister extends Activity{
	
	TextView offLine;
	TextView text1;
	TextView l51;
	EditText uname;
	EditText pwd;
	Button login;
	Button reset;
	TextView lo;
	DBAdapter db = new DBAdapter(this);
	Session ss = new Session();
	 private static final String NAMESPACE = "http://tempuri.org/";
		private static final String URL = 
			"http://pancare.panhealth.com/test/Service.asmx";	
		private static final String SOAP_ACTION = "http://tempuri.org/getMemberinfo";
		private static final String METHOD_NAME = "getMemberinfo";
		
		String uname1="";
		String pwd1="";
		
		 /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.confirm_register);
	        
	        db.open();
	        
	        l51 = (TextView)findViewById(R.id.l51);
	        
	        uname = (EditText)findViewById(R.id.uname);
	        pwd = (EditText)findViewById(R.id.pwd);
	        text1 = (TextView)findViewById(R.id.text1);
	        text1.setText("");
	        
	        uname1 = ss.getSessionNewID();
	        pwd1 = ss.getSessionNewPass();
	        
	        l51.setText("ID: "+uname1+"\nPassword: "+pwd1);
	        
	        uname.setText(uname1);
	        pwd.setText(pwd1);
	        
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
	   				checkValidate();
	   			
	   			}
	   		});
	    }

	    private void reset_func()
	      {
	      	uname.setText("");
	      	pwd.setText("");
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
	      		/*if(db.checkLogin(createUID(uname.getText().toString()), pwd.getText().toString()))
	      		{
	      			String actual_id = createUID(uname.getText().toString());
	      			ss.setSessionMemberID(actual_id);
	      			ss.setSessionMemberName(db.getUserName(actual_id));
	  	    		
	  	    		Intent i = new Intent(LoginRegister.this, HomePage.class);
	  	            startActivity(i);
	      		}*/
	      		
	      	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	  		
	      	String uname1 = uname.getText().toString();
	      	String pwd1 = pwd.getText().toString();
	      	ss.setSessionMemberID(uname1);	
	      	
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
	  			  			
	  			if((resultsRequestSOAP.toString()).equals("False"))
	  				text1.setText("Invalid Login!!");
	  			else
	  			{
	  				ss.setSessionMemberName(resultsRequestSOAP.toString());
	  	    		
	  	    		Intent i = new Intent(LoginRegister.this, HomePage.class);
	  	            startActivity(i);
	  	            
	  			}
	  			
	  		} catch (Exception e) {
	  			text1.setText("Please check your Internet Connection...");
	  			
	  			e.printStackTrace();
	  		}

	      	
	      	}
	      	
	      
	      }
	    
}
