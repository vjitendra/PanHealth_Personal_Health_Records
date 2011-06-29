package com.PersonalHealthRecord;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.Toast;

public class RegisterPatient extends Activity{

	Session ss = new Session();
	DBAdapter db = new DBAdapter(this);
	TextView lo;	
	Button back;
	String gender="M";
	int contacttype=0;
	private int mYear;
    private int mMonth;
    private int mDay;
    TextView dateDisplay;
    ImageButton changeDate;
    static final int DATE_DIALOG_ID = 0;
	 private static final String NAMESPACE = "http://tempuri.org/";
	 private static final String URL = 
			"http://pancare.panhealth.com/test/Service.asmx";	
		private static final String SOAP_ACTION = "http://tempuri.org/CreateNewMember";
		private static final String METHOD_NAME = "CreateNewMember";
		
		
		  /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.register_patient);
	        
	        db.open();
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
	               
	          //     lo.setPadding(0, 20, 0, 0);

	          }
	          else if(width < height)
	          {
	          	RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
	              Resources res = getResources(); //resource handle
	              Drawable drawable = res.getDrawable(R.drawable.background_1); //new Image that was added to the res folder

	              rLayout.setBackgroundDrawable(drawable);

	          //    lo.setPadding(0, 150, 0, 0);
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
	    
	    void ValidatePhoneNumber ()
	    {
	    	//String sPhoneNumber = "605-8889999";
	    	String sPhoneNumber = "9970666666";
	       	   
	        //Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
	        Pattern pattern = Pattern.compile("\\d{10}");
	        Matcher matcher = pattern.matcher(sPhoneNumber);
	   
	        if (matcher.matches()) {
	      	  System.out.println("Phone Number Valid");
	        }
	        else
	        {
	      	  System.out.println("Phone Number must be in the form XXX-XXXXXXX");
	        }

	    }
	    
	    boolean checkNo(String sPhoneNumber)
	    {
	    	
	    	 if (sPhoneNumber == null || sPhoneNumber.length() < 10 )
	             return false;
	         
	         for (int i = 0; i < 10; i++) {

	             //If we find a non-digit character we return false.
	             if (!Character.isDigit(sPhoneNumber.charAt(i)))
	                 return false;
	         }
	         
	         return true;
	    }
	    
	    void work()
	    {
	    	final EditText fname = (EditText)findViewById(R.id.fname);
	    	final EditText mname = (EditText)findViewById(R.id.mname);
	    	final EditText lname = (EditText)findViewById(R.id.lname);
	    	final EditText phno = (EditText)findViewById(R.id.phno);
	    	final EditText email = (EditText)findViewById(R.id.email);
	    	final EditText phno1 = (EditText)findViewById(R.id.phno1);
	    	final EditText email1 = (EditText)findViewById(R.id.email1);
	      	final TextView text1 = (TextView)findViewById(R.id.text1);
	      	text1.setText("");
	      	final TextView text2 = (TextView)findViewById(R.id.text2);
	      	text2.setText("");
	      		      	
	    	OnClickListener radio_listener = new OnClickListener() {
	             public void onClick(View v) {
	                 
	                 RadioButton rb = (RadioButton) v;
	                 if((rb.getText().toString()).equals("Male"))
	                	 gender = "M";
	                 else  if((rb.getText().toString()).equals("Female"))
	                	 gender = "F";
	                 
	             }
	           };
	    	
	    	final RadioButton male = (RadioButton) findViewById(R.id.male);
	        final RadioButton female = (RadioButton) findViewById(R.id.female);
	        
	        male.setOnClickListener(radio_listener);
	        female.setOnClickListener(radio_listener);
	        
	        if(!(isOnline()))
	        	text1.setText("Internet Connection is not available...");
	        
	        back = (Button)findViewById(R.id.back);
	        back.setOnClickListener(new Button.OnClickListener() 
			{ public void onClick (View v)
				{ 
					back.setBackgroundResource(R.drawable.back_h);
					Intent i = new Intent(RegisterPatient.this, PersonalHealthRecord.class);
					startActivity(i);
				}
			});  
	        
	        final Button register = (Button)findViewById(R.id.register);
	        register.setOnClickListener(new Button.OnClickListener() 
	  		{ public void onClick (View v)
	  			{ 
	  			register.setBackgroundResource(R.drawable.register_h);
	  			text1.setText("");
	  			if((fname.getText().toString()).equals(""))
	      	   		text1.setText("First name not entered!!");
	  			/*else if((mname.getText().toString()).equals(""))
	      	   		text1.setText("Middle name not entered!!");*/
	  			else if((lname.getText().toString()).equals(""))
	      	   		text1.setText("Last name not entered!!");
	  			else if(!((phno.getText().toString()).equals(phno1.getText().toString())))
	      	   		text1.setText("Entered and re-entered Phone number do not match!!");
	  			else if(!((email.getText().toString()).equals(email1.getText().toString())))
	      	   		text1.setText("Entered and re-entered Email-ID do not match!!");
	  			else if(((phno.getText().toString()).equals("")) && ((email.getText().toString()).equals("")))
	      	   		text1.setText("Either EmailID or Phone number is compulsary..");
	  			else if((!((phno.getText().toString()).equals(""))) &&
	  					!(checkNo(phno.getText().toString())))
	  				text1.setText("Invalid Phone Number");
	  			else if((!((email.getText().toString()).equals(""))) && 
	  					(!(checkEmail(email.getText().toString()))))
  					text1.setText("Invalid EmailID");
	  			else
	  			{
	  			
	  				if(!((email.getText().toString()).equals("")))
	  					contacttype = 3;
	  				else 
	  					contacttype = 5;
	  				 
	  				
	  				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	  		  		
	  				String fname1 = fname.getText().toString();
	  				String lname1 = lname.getText().toString();
	  				int i = contacttype;
	  				
	  				System.out.println("Fname: "+fname.getText().toString());
	  				System.out.println("Mname: "+mname.getText().toString());
	  				System.out.println("Lname: "+lname.getText().toString());
	  				System.out.println("Gen: "+gender);
	  				System.out.println("Email: "+email.getText().toString());
	  				System.out.println("Phone: "+phno.getText().toString());
	  				System.out.println("type: "+contacttype);
	  				
	  		  		request.addProperty("strFirst", fname.getText().toString());
	  		  		request.addProperty("strMid", mname.getText().toString());
	  		  		request.addProperty("strLast", lname.getText().toString());
	  		  		request.addProperty("strGen", gender);
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
	  		  			
	  		  			System.out.println("Response: "+resultsRequestSOAP);
	  		  			if(resultsRequestSOAP == null)
	  		  			{
	  		  				text2.setText("Duplicate entry...\nPlease try with other details..");
	  		  			}
	  		  			else
	  		  			{
	  		  				String res = resultsRequestSOAP.toString();
	  		  				int start = res.indexOf("=");
	  		  				int end = res.indexOf(";");
	  		  				String id1 = res.substring(start+1, end);
	  		  				start = res.indexOf("=", start+1);
	  		  				end = res.indexOf(";", end+1);
	  		  				String pwd1 = res.substring(start+1, end);
	  		  				System.out.println("ID: "+id1);
	  		  				System.out.println("Pwd: "+pwd1);
	  		  				text2.setText("Your ID: "+id1+"\nPassword: "+pwd1);
	  		  				  		  				
	  		  				final Calendar c = Calendar.getInstance();
	  		  				mYear = c.get(Calendar.YEAR);
	  		  				mMonth = c.get(Calendar.MONTH);
	  		  				mDay = c.get(Calendar.DAY_OF_MONTH);
			       
	  		  				updateDisplay();
	  		  				fname.setText("");
	  		  				mname.setText("");
	  		  				lname.setText("");
	  		  				phno.setText("");
	  		  				email.setText("");
	  		  				phno1.setText("");
	  		  				email1.setText("");
	  		  				text1.setText("");
	  		  				
	  		  				ss.setSessionNewID(id1);
	  		  				ss.setSessionNewPass(pwd1);
  		  				
	  		  				db.insertLogin(id1, pwd1, fname1+", "+lname1, 1);
	  		  				
	  		  				Intent i1 = new Intent(RegisterPatient.this, LoginRegister.class);
	  		  				startActivity(i1);
	  		  				
	  		  			}
	  		  		  		  			
	  		  		} catch (Exception e) {
	  		  			text2.setText("");
	  		  			text1.setText("Due to some problem Registration not done...");
	  		  			register.setBackgroundResource(R.drawable.register);
	  		  			e.printStackTrace();
	  		  		}
	  		  		
	  			}
	  		  }
	  		});
	        
	        changeDate = (ImageButton)findViewById(R.id.changeDate);
	        changeDate.setOnClickListener(new Button.OnClickListener() 
			{ public void onClick (View v)
				{ 
					showDialog(DATE_DIALOG_ID);
				}
			});
	        
	        dateDisplay = (TextView)findViewById(R.id.dateDisplay);
	        
	        final Calendar c = Calendar.getInstance();
	        mYear = c.get(Calendar.YEAR);
	        mMonth = c.get(Calendar.MONTH);
	        mDay = c.get(Calendar.DAY_OF_MONTH);
	        
	        updateDisplay();

	        final Button reset = (Button)findViewById(R.id.reset);
	        reset.setOnClickListener(new Button.OnClickListener() 
	  		{ public void onClick (View v)
	  			{ 
	  				reset.setBackgroundResource(R.drawable.reset_h);
	  				fname.setText("");
	  				mname.setText("");
	  				lname.setText("");
	  				phno.setText("");
	  				email.setText("");
	  				phno1.setText("");
	  				email1.setText("");
	  				text1.setText("");
	  				text2.setText("");
	  				reset.setBackgroundResource(R.drawable.reset);
	  			}
	  		});
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
	    
	    @Override
	      public void onConfigurationChanged(Configuration newConfig) {

	          super.onConfigurationChanged(newConfig);

	          if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
	        	  RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
	              Resources res = getResources(); //resource handle
	              Drawable drawable = res.getDrawable(R.drawable.background_1); //new Image that was added to the res folder

	              rLayout.setBackgroundDrawable(drawable);
	              
	       //       lo.setPadding(0, 150, 0, 0);
	              //lo.setPadding(0, 20, 0, 0);

	          } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	        	  RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
	              Resources res = getResources(); //resource handle
	              Drawable drawable = res.getDrawable(R.drawable.background_2); //new Image that was added to the res folder

	              rLayout.setBackgroundDrawable(drawable);
	              
	         //     lo.setPadding(0, 20, 0, 0);
	          }
	      }
}
