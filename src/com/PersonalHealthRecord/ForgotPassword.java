package com.PersonalHealthRecord;

import java.util.Calendar;
import java.util.regex.Pattern;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ForgotPassword extends Activity{
	
	TextView lo;	
	Button back;
	int contacttype;
	 private static final String NAMESPACE = "http://tempuri.org/";
	 private static final String URL = 
			"http://pancare.panhealth.com/test/Service.asmx";	
		private static final String SOAP_ACTION = "http://tempuri.org/CreateNewMember";
		private static final String METHOD_NAME = "CreateNewMember";
		
		
		  /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.forgot_password);
	        
	        //  First, get the Display from the WindowManager 
	          Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

	          // Now we can retrieve all display-related infos 
	          int width = display.getWidth();
	          int height = display.getHeight();
	          int orientation = display.getOrientation();
	          
	          // 3 - hori                  0 - portrait
	          
	          lo = (TextView)findViewById(R.id.lo);
	          lo.setPadding(0, 20, 0, 0);
	          if(width > height)
	          {
	          	 RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
	               Resources res = getResources(); //resource handle
	               Drawable drawable = res.getDrawable(R.drawable.background_2); //new Image that was added to the res folder

	               rLayout.setBackgroundDrawable(drawable);
	               
	             //  lo.setPadding(0, 20, 0, 0);

	          }
	          else if(width < height)
	          {
	          	RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
	              Resources res = getResources(); //resource handle
	              Drawable drawable = res.getDrawable(R.drawable.background_1); //new Image that was added to the res folder

	              rLayout.setBackgroundDrawable(drawable);

	           //   lo.setPadding(0, 150, 0, 0);
	              //lo.setPadding(0, 20, 0, 0);
	          }
	          work();
	    }
	    
	    public boolean isOnline() {
	    	 ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	    	        return true;
	    	    }
	    	    return false;
	    	}
	    
	    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
	            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
	            "\\@" +
	            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
	            "(" +
	            "\\." +
	            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
	            ")+"
	        );

	    private boolean checkEmail(String email) {
	        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	    }

	    
	    void work()
	    {
	    	final EditText fname = (EditText)findViewById(R.id.fname);
	    	final EditText lname = (EditText)findViewById(R.id.lname);
	    	final EditText phno = (EditText)findViewById(R.id.phno);
	    	final EditText email = (EditText)findViewById(R.id.email);
	    	final TextView text1 = (TextView)findViewById(R.id.text1);
	      	text1.setText("");
	      	final TextView text2 = (TextView)findViewById(R.id.text2);
	      	text2.setText("");
	    	
	        if(!(isOnline()))
	        	text1.setText("Internet Connection is not available...");
	        
	        final Button submit = (Button)findViewById(R.id.submit);
	        submit.setOnClickListener(new Button.OnClickListener() 
	  		{ public void onClick (View v)
	  			{ 
	  			submit.setBackgroundResource(R.drawable.submit_h);
	  			if((fname.getText().toString()).equals(""))
	      	   		text1.setText("First name not entered!!");
	  			else if((lname.getText().toString()).equals(""))
	      	   		text1.setText("Last name not entered!!");
	  			else if(((phno.getText().toString()).equals("")) && ((email.getText().toString()).equals("")))
	      	   		text1.setText("Either EmailID or Phone number is compulsary..");
	  			else if((!((email.getText().toString()).equals(""))) && 
	  					(!(checkEmail(email.getText().toString()))))
  					text1.setText("Invalid EmailID");
  				else
	  			{
	  				
	  						
	  				if(!((email.getText().toString()).equals("")))
	  				{
	  					//contacttype = 3;
	  				}
	  				else
	  				{
	  					//contacttype = 5;
	  				}
	  				
	  				/*SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	  		  			  					  				
	  		  		request.addProperty("strFirst", fname.getText().toString());
	  		  		request.addProperty("strLast", lname.getText().toString());
	  		  		
	  		  		if(!((email.getText().toString()).equals("")))
	  		  			request.addProperty("strEmailAdd", email.getText().toString());
	  		  		if(!((phno.getText().toString()).equals("")))
	  		  			request.addProperty("strPhone", phno.getText().toString());
	  		  		
	  		  		request.addProperty("intcontacttype", contacttype);		
	  		  		
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
	  		  			//String MemberId = resultsRequestSOAP.toString();
	  		  			//Toast.makeText(getBaseContext(), "Data Retrieved Successfully",Toast.LENGTH_SHORT).show();
	  		  			
	  		  			text1.setText("");
  		  			
	  		  			text2.setText("Your password is sent to given EmailID/Phone no..");
	  		  			
	  		  		} catch (Exception e) {
	  		  		text2.setText("");
	  		  			text1.setText("Please check your Internet Connection...");
	  		  			
	  		  			e.printStackTrace();
	  		  		}
	  				*/
	  			}
	  			submit.setBackgroundResource(R.drawable.submit);
	  		  }
	  			
	  		});
	        
	        back = (Button)findViewById(R.id.back);
	        back.setOnClickListener(new Button.OnClickListener() 
			{ public void onClick (View v)
				{ 
					back.setBackgroundResource(R.drawable.back_h);
					Intent i = new Intent(ForgotPassword.this, PersonalHealthRecord.class);
					startActivity(i);
				}
			}); 

	        final Button reset = (Button)findViewById(R.id.reset);
	        reset.setOnClickListener(new Button.OnClickListener() 
	  		{ public void onClick (View v)
	  			{ 
	  				reset.setBackgroundResource(R.drawable.reset_h);
	  				fname.setText("");
	  				lname.setText("");
	  				phno.setText("");
	  				email.setText("");
	  				text1.setText("");
	  				reset.setBackgroundResource(R.drawable.reset);
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
	              
	              //lo.setPadding(0, 150, 0, 0);
	              //lo.setPadding(0, 20, 0, 0);

	          } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	        	  RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
	              Resources res = getResources(); //resource handle
	              Drawable drawable = res.getDrawable(R.drawable.background_2); //new Image that was added to the res folder

	              rLayout.setBackgroundDrawable(drawable);
	              
	              //lo.setPadding(0, 20, 0, 0);
	          }
	      }
}



