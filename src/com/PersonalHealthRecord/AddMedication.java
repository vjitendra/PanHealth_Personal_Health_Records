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
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddMedication extends Activity{
	
	TextView medi_name, text1;
	Button back, submit;// one, two, three, four, five, six;
	LinearLayout l11;
	
	int times_time=0;
	int button_selected=1;
	
	TextView []mTimeDisplay = new TextView[6];
	ImageButton []changeTime= new ImageButton[6];
	TextView []uitextbox= new TextView[6];
	EditText []medi= new EditText[6];
	
	private int []mHour = new int[6];
    private int []mMinute = new int[6];
			
	Session ss = new Session();
    DBAdapter db = new DBAdapter(this);
    TableLayout tl;
    static final int TIME_DIALOG_ID = 1;
    String userID;
    EditText comment;
    
    private static final String NAMESPACE = "http://tempuri.org/";
	private static final String URL = 
		"http://pancare.panhealth.com/test/Service.asmx";	
	private static final String SOAP_ACTION = "http://tempuri.org/addMedication";
	private static final String METHOD_NAME = "addMedication";
    
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.add_medication);
        l11 = (LinearLayout)findViewById(R.id.l10);
        
        text1 = (TextView)findViewById(R.id.text1);
        medi_name = (TextView)findViewById(R.id.medi_name);
        
        tl = (TableLayout) findViewById(R.id.maintable);
        
        comment = (EditText)findViewById(R.id.comment);
        
        
        
    	 mTimeDisplay[0] = (TextView)findViewById(R.id.mTimeDisplay1);
         mTimeDisplay[1] = (TextView)findViewById(R.id.mTimeDisplay2);
         mTimeDisplay[2] = (TextView)findViewById(R.id.mTimeDisplay3);
         mTimeDisplay[3] = (TextView)findViewById(R.id.mTimeDisplay4);
         mTimeDisplay[4] = (TextView)findViewById(R.id.mTimeDisplay5);
         mTimeDisplay[5] = (TextView)findViewById(R.id.mTimeDisplay6);
    	 
         uitextbox[0] = (TextView)findViewById(R.id.uitextbox1);
         uitextbox[1] = (TextView)findViewById(R.id.uitextbox2);
         uitextbox[2] = (TextView)findViewById(R.id.uitextbox3);
         uitextbox[3] = (TextView)findViewById(R.id.uitextbox4);
         uitextbox[4] = (TextView)findViewById(R.id.uitextbox5);
         uitextbox[5] = (TextView)findViewById(R.id.uitextbox6);
         
         changeTime[0] = (ImageButton)findViewById(R.id.changeTime1);
         changeTime[1] = (ImageButton)findViewById(R.id.changeTime2);
         changeTime[2] = (ImageButton)findViewById(R.id.changeTime3);
         changeTime[3] = (ImageButton)findViewById(R.id.changeTime4);
         changeTime[4] = (ImageButton)findViewById(R.id.changeTime5);
         changeTime[5] = (ImageButton)findViewById(R.id.changeTime6);
         
         medi[0] = (EditText)findViewById(R.id.medi1);
         medi[1] = (EditText)findViewById(R.id.medi2);
         medi[2] = (EditText)findViewById(R.id.medi3);
         medi[3] = (EditText)findViewById(R.id.medi4);
         medi[4] = (EditText)findViewById(R.id.medi5);
         medi[5] = (EditText)findViewById(R.id.medi6);
         
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
             
           //  l11.setPadding(0, 20, 0, 0);

        }		 
        else if(width < height)
        {
        	 RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
             Resources res = getResources(); //resource handle
             Drawable drawable = res.getDrawable(R.drawable.background_1); //new Image that was added to the res folder

             rLayout.setBackgroundDrawable(drawable);
             
           //  l11.setPadding(0, 150, 0, 0);

        }	
       
        
        
        db.open();
        
        String memID = ss.getSessionMemberID();
		userID = "A";
		for(int i=0; i<(12-memID.length()); i++)
		{
			userID = userID+"0";
		}
	    memID = memID.replace("A", "");	    		
	    userID = userID + memID;
                
        TextView userIDT = (TextView)findViewById(R.id.userID);
        userIDT.setText("Patient ID: "+userID);
        
        final Calendar c = Calendar.getInstance();
        mHour[0] = c.get(Calendar.HOUR_OF_DAY);
    	mMinute[0] = c.get(Calendar.MINUTE);
    	updateDisplay1(0);
                        
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				back.setBackgroundResource(R.drawable.back_h);
				Intent i = new Intent(AddMedication.this, HomePage.class);
				startActivity(i);
			}
		});  
              
        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				submit.setBackgroundResource(R.drawable.submit_h);
				text1.setText("hello...");
				    			
				if((medi_name.getText().toString()).equals(""))
	      	   		text1.setText("Medicine name not entered!!");
				else if((button_selected == 1) && (medi[0].getText().toString()).equals(""))
					text1.setText("Data not entered!!");
				else if((button_selected == 2) && 
						(((medi[0].getText().toString()).equals("")) ||
						(((medi[1].getText().toString()).equals("")))))
					text1.setText("Data not entered!!");
				else if((button_selected == 3) && 
						(((medi[0].getText().toString()).equals("")) ||
						((medi[1].getText().toString()).equals("")) ||
						(((medi[2].getText().toString()).equals("")))))
					text1.setText("Data not entered!!");
				else if((button_selected == 4) && 
						(((medi[0].getText().toString()).equals("")) ||
						((medi[1].getText().toString()).equals("")) ||
						((medi[2].getText().toString()).equals("")) ||
						(((medi[3].getText().toString()).equals("")))))
					text1.setText("Data not entered!!");
				else if((button_selected == 5) && 
						(((medi[0].getText().toString()).equals("")) ||
						((medi[1].getText().toString()).equals("")) ||
						((medi[2].getText().toString()).equals("")) ||
						((medi[3].getText().toString()).equals("")) ||
						(((medi[4].getText().toString()).equals("")))))
					text1.setText("Data not entered!!");
				else if((button_selected == 6) && 
						(((medi[0].getText().toString()).equals("")) ||
						((medi[1].getText().toString()).equals("")) ||
						((medi[2].getText().toString()).equals("")) ||
						((medi[3].getText().toString()).equals("")) ||
						((medi[4].getText().toString()).equals("")) ||
						(((medi[5].getText().toString()).equals("")))))
					text1.setText("Data not entered!!");
				else
				{
					
					SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        		
	        		request.addProperty("strMemberId", userID); //memId.toString());
	        		request.addProperty("strMedicineName", medi_name.getText().toString());
	        		request.addProperty("strFrequency", ""+button_selected);
	        		
	        		System.out.println("Mem: "+userID);
					System.out.println("Med: "+medi_name.getText().toString());
					System.out.println("Freq: "+button_selected);
	        		
	        		if(!((comment.getText().toString()).equals("")))
	        		    request.addProperty("strComments", comment.getText().toString());
	        		
	        		for(int i=0; i<button_selected; i++)
        			{
        				int j=i+1;
        				request.addProperty("strQti"+j, medi[i].getText().toString());
        				request.addProperty("strTIME"+j, mTimeDisplay[i].getText().toString());
        				System.out.println("strQti"+j+" Quan: "+ medi[i].getText().toString());
        				System.out.println("strTIME"+j+" Time: "+ mTimeDisplay[i].getText().toString());
        			}
        			
	        		SoapSerializationEnvelope envelope = 
	  		  			new SoapSerializationEnvelope(SoapEnvelope.VER11); 

	  		  		envelope.setOutputSoapObject(request);
	  		  		envelope.dotNet=true;
	  		  		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
	  		  		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	        			
	        		
	        		
	        		try {
	        			androidHttpTransport.call(SOAP_ACTION, envelope);
		  	  			Object resultsRequestSOAP = (Object) envelope.getResponse();
	        			
		  	  		System.out.println("Data sent....");
		  	  			
	        			String temp = resultsRequestSOAP.toString();
	        			if(temp.contains("False"))
	        				text1.setText("Could not add medication..");
	        			else if(temp.contains("True"))
	        				text1.setText("Medication added Successfully!!");
	        			else 
	        				text1.setText("Failed to add\nPlease try after some time.. ");
	        			
						medi_name.setText("");
						comment.setText("");
						final Calendar c = Calendar.getInstance();
				        mHour[0] = c.get(Calendar.HOUR_OF_DAY);
				        mMinute[0] = c.get(Calendar.MINUTE);

				        updateDisplay1(0);
	        			
	        		} catch (Exception e) {
					
						text1.setText("Due to some problem..\nData is added only to local database..");
						medi_name.setText("");
						medi[0].setText("");
						comment.setText("");
						final Calendar c = Calendar.getInstance();
				        
				        mHour[0] = c.get(Calendar.HOUR_OF_DAY);
				        mMinute[0] = c.get(Calendar.MINUTE);

				      
				        updateDisplay1(0);
	        			e.printStackTrace();
	        		}

						
					}
				
				 				
				}
           	 
		});
        
        OnClickListener radio_listener = new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                RadioButton rb = (RadioButton) v;
                //Toast.makeText(SelectOption.this, rb.getText(), Toast.LENGTH_SHORT).show();
                            
               /*	ss.setSessionSyncData(rb.getText().toString());
                	
                	Intent i = new Intent(SyncDataToPanHealthOption.this, SyncDataToPanHealth.class);
    				startActivity(i);*/
                
                String sync = rb.getText().toString();
                
                if(sync.equals("1"))
                {
                	button_selected=1;
        			
        			for(int i=1; i<6; i++)
        			{
        				mTimeDisplay[i].setVisibility(View.GONE);
        				uitextbox[i].setVisibility(View.GONE);
        				changeTime[i].setVisibility(View.GONE);
        				medi[i].setVisibility(View.GONE);
        				
        				 
        			}
        				
        			final Calendar c = Calendar.getInstance();
        			mHour[0] = c.get(Calendar.HOUR_OF_DAY);
        		    mMinute[0] = c.get(Calendar.MINUTE);
        		     
        		     times_time = 0;
        		     updateDisplay1(0);
        			
        				        
        			
        			changeTime[0].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 0;
        					showDialog(0);
        				}
        			});
                }
                else if(sync.equals("2"))
                {
                	button_selected=2;
    				final Calendar c = Calendar.getInstance();
    				for(int i=1; i<6; i++)
    				{
    					if(i<=1)
    					{
    						mTimeDisplay[i].setVisibility(View.VISIBLE);
    						uitextbox[i].setVisibility(View.VISIBLE);
    						changeTime[i].setVisibility(View.VISIBLE);
    						medi[i].setVisibility(View.VISIBLE);
    						
    						mHour[i] = c.get(Calendar.HOUR_OF_DAY);
    					    mMinute[i] = c.get(Calendar.MINUTE);
    					    
    					    times_time = i;
    			        	updateDisplay1(i);	
    					}
    					else
    					{
    						mTimeDisplay[i].setVisibility(View.GONE);
    						uitextbox[i].setVisibility(View.GONE);
    						changeTime[i].setVisibility(View.GONE);
    						medi[i].setVisibility(View.GONE);
    					}
    				}
    				
    		        changeTime[0].setOnClickListener(new Button.OnClickListener() 
    				{ public void onClick (View v)
    					{ 
    						times_time = 0;
    						showDialog(0);
    					}
    				});		
    		        
    		       
    		        changeTime[1].setOnClickListener(new Button.OnClickListener() 
    				{ public void onClick (View v)
    					{ 
    						times_time = 1;
    						showDialog(1);
    					}
    				});		
                }
                else if(sync.equals("3"))
                {
                	button_selected=3;
        			final Calendar c = Calendar.getInstance();
        			for(int i=1; i<6; i++)
        			{
        				if(i<=2)
        				{
        					mTimeDisplay[i].setVisibility(View.VISIBLE);
        					uitextbox[i].setVisibility(View.VISIBLE);
        					changeTime[i].setVisibility(View.VISIBLE);
        					medi[i].setVisibility(View.VISIBLE);
        					
        					mHour[i] = c.get(Calendar.HOUR_OF_DAY);
        				    mMinute[i] = c.get(Calendar.MINUTE);
        				    
        				    times_time = i;
        		        	updateDisplay1(i);	
        				}
        				else
        				{
        					mTimeDisplay[i].setVisibility(View.GONE);
        					uitextbox[i].setVisibility(View.GONE);
        					changeTime[i].setVisibility(View.GONE);
        					medi[i].setVisibility(View.GONE);
        				}
        			}
        			
        			
        	        changeTime[0].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 0;
        					showDialog(0);
        				}
        			});		
        	        
        	       
        	        changeTime[1].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 1;
        					showDialog(1);
        				}
        			});		
        			
        	        
        	        changeTime[2].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 2;
        					showDialog(1);
        				}
        			});		
                }
                else if(sync.equals("4"))
                {
                	button_selected=4;
        			final Calendar c = Calendar.getInstance();
        			for(int i=1; i<6; i++)
        			{
        				if(i<=3)
        				{
        					mTimeDisplay[i].setVisibility(View.VISIBLE);
        					uitextbox[i].setVisibility(View.VISIBLE);
        					changeTime[i].setVisibility(View.VISIBLE);
        					medi[i].setVisibility(View.VISIBLE);
        					
        					mHour[i] = c.get(Calendar.HOUR_OF_DAY);
        				    mMinute[i] = c.get(Calendar.MINUTE);
        				    
        				    times_time = i;
        		        	updateDisplay1(i);
        				}
        				else
        				{
        					mTimeDisplay[i].setVisibility(View.GONE);
        					uitextbox[i].setVisibility(View.GONE);
        					changeTime[i].setVisibility(View.GONE);
        					medi[i].setVisibility(View.GONE);
        				}
        			}
        			
        			
        	        changeTime[0].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 0;
        					showDialog(0);
        				}
        			});		
        	        
        	       
        	        changeTime[1].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 1;
        					showDialog(1);
        				}
        			});		
        			
        	        
        	        changeTime[2].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 2;
        					showDialog(2);
        				}
        			});		
        	        
        	        
        	        changeTime[3].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 3;
        					showDialog(3);
        				}
        			});		
                }
                else if(sync.equals("5"))
                {
                	button_selected=5;	
        			final Calendar c = Calendar.getInstance();
        			for(int i=1; i<6; i++)
        			{
        				if(i<=4)
        				{
        					mTimeDisplay[i].setVisibility(View.VISIBLE);
        					uitextbox[i].setVisibility(View.VISIBLE);
        					changeTime[i].setVisibility(View.VISIBLE);
        					medi[i].setVisibility(View.VISIBLE);
        					
        					mHour[i] = c.get(Calendar.HOUR_OF_DAY);
        				    mMinute[i] = c.get(Calendar.MINUTE);
        				    
        				    times_time = i;
        		        	updateDisplay1(i);
        				}
        				else
        				{
        					mTimeDisplay[i].setVisibility(View.GONE);
        					uitextbox[i].setVisibility(View.GONE);
        					changeTime[i].setVisibility(View.GONE);
        					medi[i].setVisibility(View.GONE);
        				}
        			}
        					
        	        changeTime[0].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 0;
        					showDialog(0);
        				}
        			});		
        	        
        	     
        	        changeTime[1].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 1;
        					showDialog(1);
        				}
        			});		
        			
        	      
        	        changeTime[2].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 2;
        					showDialog(2);
        				}
        			});		
        	        
        	      
        	        changeTime[3].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 3;
        					showDialog(3);
        				}
        			});	
        	        
        	     
        	        changeTime[4].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 4;
        					showDialog(4);
        				}
        			});	
                }
                else if(sync.equals("6"))
                {
                	button_selected=6;	
        			final Calendar c = Calendar.getInstance();
        			for(int i=1; i<6; i++)
        			{
        					mTimeDisplay[i].setVisibility(View.VISIBLE);
        					uitextbox[i].setVisibility(View.VISIBLE);
        					changeTime[i].setVisibility(View.VISIBLE);
        					medi[i].setVisibility(View.VISIBLE);
        					
        					mHour[i] = c.get(Calendar.HOUR_OF_DAY);
        				    mMinute[i] = c.get(Calendar.MINUTE);
        				    
        				    times_time = i;
        		        	updateDisplay1(i);
        			}
        			
        			
        	        changeTime[0].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 0;
        					showDialog(0);
        				}
        			});		
        	        
        	       
        	        changeTime[1].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 1;
        					showDialog(1);
        				}
        			});		
        			
        	   
        	        changeTime[2].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 2;
        					showDialog(2);
        				}
        			});		
        	        
        	      
        	        changeTime[3].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 3;
        					showDialog(3);
        				}
        			});	
        	        
        	    
        	        changeTime[4].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 4;
        					showDialog(4);
        				}
        			});	
        			
        	   
        	        changeTime[5].setOnClickListener(new Button.OnClickListener() 
        			{ public void onClick (View v)
        				{ 
        					times_time = 5;
        					showDialog(5);
        				}
        			});	
                }
                
            }
        };
        
        final RadioButton a1 = (RadioButton) findViewById(R.id.a1);
        final RadioButton a2 = (RadioButton) findViewById(R.id.a2);
        final RadioButton a3 = (RadioButton) findViewById(R.id.a3);
        final RadioButton a4 = (RadioButton) findViewById(R.id.a4);
        final RadioButton a5 = (RadioButton) findViewById(R.id.a5);
        final RadioButton a6 = (RadioButton) findViewById(R.id.a6);
        
        a1.setOnClickListener(radio_listener);
        a2.setOnClickListener(radio_listener);
        a3.setOnClickListener(radio_listener);
        a4.setOnClickListener(radio_listener);
        a5.setOnClickListener(radio_listener);
        a6.setOnClickListener(radio_listener);
        
        
        
        
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	
    	 return new TimePickerDialog(this,
                 mTimeSetListener, mHour[id], mMinute[id], false);
    }
    
       
    void updateDisplay1(int j) {
    	String str = ":00 AM";
    	if(mHour[j]>12)
    	{
			mHour[j]=mHour[j]-12;
			str=":00 PM";
    	}
    	
    	mTimeDisplay[j].setText(
                new StringBuilder()
                        .append(pad(mHour[j])).append(":")
                        .append(pad(mMinute[j])).append(str));
    	
    	
    }

    TimePickerDialog.OnTimeSetListener mTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour[times_time] = hourOfDay;
                mMinute[times_time] = minute;
                updateDisplay1(times_time);
            }
        };
    
        
        
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    
       
    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
       	 RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
            Resources res = getResources(); //resource handle
            Drawable drawable = res.getDrawable(R.drawable.background_1); //new Image that was added to the res folder

            rLayout.setBackgroundDrawable(drawable);
            
         //   l11.setPadding(0, 150, 0, 0);

       } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
       	 RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
            Resources res = getResources(); //resource handle
            Drawable drawable = res.getDrawable(R.drawable.background_2); //new Image that was added to the res folder

            rLayout.setBackgroundDrawable(drawable);
            
          //  l11.setPadding(0, 20, 0, 0);
       }
    }

}
