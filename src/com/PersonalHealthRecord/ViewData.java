package com.PersonalHealthRecord;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ViewData extends Activity{
	
	TextView log;
	TextView logName;
	Cursor mCursor;
	DBAdapter db = new DBAdapter(this);
	Session ss = new Session();
	String user="";
	String temp="";
	Button back;
	//String array_spinner[];
	//Spinner s;
	//int flagAll = 0;
	static final int DATE_DIALOG_ID1 = 1;
	static final int DATE_DIALOG_ID2 = 2;
	ImageButton changeDate1;
	ImageButton changeDate2;
	TextView dateDisplay1;
	TextView dateDisplay2;
	private int mYear1;
    private int mMonth1;
    private int mDay1;
    private int mYear2;
    private int mMonth2;
    private int mDay2;
    String startDate="";
    String endDate="";
    Button search;
    LinearLayout l11;
    TableLayout tl;
    String currentLog;
    
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.view_data1);
        l11 = (LinearLayout)findViewById(R.id.l10);
        //l11.setPadding(0, 20, 0, 0);
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
              //l11.setPadding(0, 20, 0, 0);

         }
        user = ss.getSessionMemberID();
        work();
        
    }
    
    DatePickerDialog.OnDateSetListener mDateSetListener1 =
        new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, 
                                  int monthOfYear, int dayOfMonth) {
                mYear1 = year;
                mMonth1 = monthOfYear;
                mDay1 = dayOfMonth;
                updateDisplay1();
            }
        };
    
        DatePickerDialog.OnDateSetListener mDateSetListener2 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear2 = year;
                    mMonth2 = monthOfYear;
                    mDay2 = dayOfMonth;
                    updateDisplay2();
                }
            };
        
            @Override
            protected Dialog onCreateDialog(int id) {
                switch (id) {
                case DATE_DIALOG_ID1:
                    return new DatePickerDialog(this,
                                mDateSetListener1,
                                mYear1, mMonth1, mDay1);
                case DATE_DIALOG_ID2:
                	return new DatePickerDialog(this,
                            mDateSetListener2,
                            mYear2, mMonth2, mDay2);
                }
                return null;
            }
           
            void updateDisplay1() {
            	dateDisplay1.setText(
                    new StringBuilder()
                            // Month is 0 based so add 1
                            .append(mMonth1 + 1).append("/")
                            .append(mDay1).append("/")
                            .append(mYear1).append(" "));
            	
            	startDate = dateDisplay1.getText().toString();
            }
            
            void updateDisplay2() {
            	dateDisplay2.setText(
                    new StringBuilder()
                            // Month is 0 based so add 1
                            .append(mMonth2 + 1).append("/")
                            .append(mDay2).append("/")
                            .append(mYear2).append(" "));
            	
            	endDate = dateDisplay2.getText().toString();
            }
    
    void hba1c()
    {
    	TableRow tr222 = new TableRow(this);
        
        TextView Date22= new TextView(this);
        Date22.setId(1);
        Date22.setText("  ");
       
        tr222.addView(Date22);
        
        TextView t422= new TextView(this);
        t422.setText("  ");
        t422.setTextColor(Color.BLACK);
        tr222.addView(t422);
        
        TextView Value22= new TextView(this);
        Value22.setId(2);
        Value22.setText("Personal HbA1c Log");
        Value22.setTextColor(Color.BLACK);
        tr222.addView(Value22);
        
        
            
        
        tl.addView(tr222, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
                       
        TableRow tr2 = new TableRow(this);
        tr2.setBackgroundColor(Color.GRAY);
        TextView Date= new TextView(this);
        Date.setId(1);
        Date.setText("  Date & Time");
        Date.setTextColor(Color.BLACK);
        tr2.addView(Date);
        
        TextView t4= new TextView(this);
        t4.setText("  |");
        t4.setTextColor(Color.BLACK);
        tr2.addView(t4);
        
        TextView Value= new TextView(this);
        Value.setId(2);
        Value.setText("   Value in mmol/mol");
        Value.setTextColor(Color.BLACK);
        tr2.addView(Value);
                
        tl.addView(tr2, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        mCursor = db.getHbA1cLogFromDate(user, startDate+"00:00:00", endDate+"24:00:00");
        int current = 0;
      
        while(mCursor.moveToNext())
        {
            TableRow tr = new TableRow(this);
            tr.setId(100+current);
                     
            tr.setLayoutParams(new LayoutParams(
            		LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));  
            
            TextView date = new TextView(this);
            String d = mCursor.getString(4);
            String []d1 = new String[2];
            d1 = d.split(" ");
            date.setText("| "+d1[0]+"\n| "+d1[1]);
            date.setTextColor(Color.BLACK);
            tr.addView(date);
            
                                
            TextView condition = new TextView(this);
            condition.setText(" "+mCursor.getString(3)+"\t\t|"+"\n\t\t\t\t\t|");
            condition.setTextColor(Color.BLACK);
            tr.addView(condition);
            
            
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            
            current++;
           
        }
        TableRow tr3 = new TableRow(this);
        tr3.setBackgroundColor(Color.GRAY);
        
        TextView t1= new TextView(this);
        t1.setText("");
        tr3.addView(t1);
        TextView t2= new TextView(this);
        t2.setText("");
        tr3.addView(t2);
        TextView t3= new TextView(this);
        t3.setText("");
        tr3.addView(t3);
                
        tl.addView(tr3, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TableRow tr4 = new TableRow(this);
        tr4.setBackgroundColor(Color.WHITE);
        
        TextView t11= new TextView(this);
        t11.setText("");
        tr4.addView(t11);
        TextView t21= new TextView(this);
        t21.setText("");
                         
        tl.addView(tr4, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
    }
            
    void hemoglobin()
    {
    	
    	 TableRow tr222 = new TableRow(this);
         
         TextView Date22= new TextView(this);
         Date22.setId(1);
         Date22.setText("  ");
        
         tr222.addView(Date22);
         
         TextView t422= new TextView(this);
         t422.setText("  ");
         t422.setTextColor(Color.BLACK);
         tr222.addView(t422);
         
         TextView Value22= new TextView(this);
         Value22.setId(2);
         Value22.setText("Blood Hemoglobin Log");
         Value22.setTextColor(Color.BLACK);
         tr222.addView(Value22);
         
         
             
         
         tl.addView(tr222, new TableLayout.LayoutParams(
                 LayoutParams.FILL_PARENT,
                 LayoutParams.WRAP_CONTENT));
         
                        
         TableRow tr2 = new TableRow(this);
         tr2.setBackgroundColor(Color.GRAY);
         TextView Date= new TextView(this);
         Date.setId(1);
         Date.setText("  Date & Time");
         Date.setTextColor(Color.BLACK);
         tr2.addView(Date);
         
         TextView t4= new TextView(this);
         t4.setText("  |");
         t4.setTextColor(Color.BLACK);
         tr2.addView(t4);
         
         TextView Value= new TextView(this);
         Value.setId(2);
         Value.setText("      Value in g/dL");
         Value.setTextColor(Color.BLACK);
         tr2.addView(Value);
                 
         tl.addView(tr2, new TableLayout.LayoutParams(
                 LayoutParams.FILL_PARENT,
                 LayoutParams.WRAP_CONTENT));
         
         mCursor = db.getHGLogFromDate(user, startDate+"00:00:00", endDate+"24:00:00");
         int current = 0;
       
         while(mCursor.moveToNext())
         {
             TableRow tr = new TableRow(this);
             tr.setId(100+current);
                      
             tr.setLayoutParams(new LayoutParams(
             		LayoutParams.FILL_PARENT,
                     LayoutParams.WRAP_CONTENT));  
             
             TextView date = new TextView(this);
             String d = mCursor.getString(4);
             String []d1 = new String[2];
             d1 = d.split(" ");
             date.setText("| "+d1[0]+"\n| "+d1[1]);
             date.setTextColor(Color.BLACK);
             tr.addView(date);
             
                                 
             TextView condition = new TextView(this);
             condition.setText(" "+mCursor.getString(3)+"\t\t|"+"\n\t\t\t\t\t|");
             condition.setTextColor(Color.BLACK);
             tr.addView(condition);
             
             
             tl.addView(tr, new TableLayout.LayoutParams(
                     LayoutParams.FILL_PARENT,
                     LayoutParams.WRAP_CONTENT));
             
             current++;
            
         }
         TableRow tr3 = new TableRow(this);
         tr3.setBackgroundColor(Color.GRAY);
         
         TextView t1= new TextView(this);
         t1.setText("");
         tr3.addView(t1);
         TextView t2= new TextView(this);
         t2.setText("");
         tr3.addView(t2);
         TextView t3= new TextView(this);
         t3.setText("");
         tr3.addView(t3);
                 
         tl.addView(tr3, new TableLayout.LayoutParams(
                 LayoutParams.FILL_PARENT,
                 LayoutParams.WRAP_CONTENT));
         
         TableRow tr4 = new TableRow(this);
         tr4.setBackgroundColor(Color.WHITE);
         
         TextView t11= new TextView(this);
         t11.setText("");
         tr4.addView(t11);
         TextView t21= new TextView(this);
         t21.setText("");
                          
         tl.addView(tr4, new TableLayout.LayoutParams(
                 LayoutParams.FILL_PARENT,
                 LayoutParams.WRAP_CONTENT));
    }
            
    void wt()
    {
    	//logName.setText("Personal Weight Log");
    	    	
    	//TableLayout tl = (TableLayout) findViewById(R.id.maintable);
    	/*if(flagAll == 0)
    		tl.removeAllViews();*/
    	
    	TableRow tr222 = new TableRow(this);
        
        TextView Date22= new TextView(this);
        Date22.setId(1);
        Date22.setText("  ");
       
        tr222.addView(Date22);
        
        TextView t422= new TextView(this);
        t422.setText("  ");
        t422.setTextColor(Color.BLACK);
        tr222.addView(t422);
        
        TextView Value22= new TextView(this);
        Value22.setId(2);
        Value22.setText("Blood Weight Log");
        Value22.setTextColor(Color.BLACK);
        tr222.addView(Value22);
        
        TextView t522= new TextView(this);
        t522.setText("  ");
        t522.setTextColor(Color.BLACK);
        tr222.addView(t522);
        
        TextView Condition22= new TextView(this);
        Condition22.setId(3);
        Condition22.setText("  ");
       
        tr222.addView(Condition22);
            
        
        tl.addView(tr222, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
    	
        TableRow tr2 = new TableRow(this);
        tr2.setBackgroundColor(Color.GRAY);
        TextView Date= new TextView(this);
        Date.setId(1);
        Date.setText("  Date &\n  Time");
        Date.setTextColor(Color.BLACK);
        tr2.addView(Date);
        
        TextView t4= new TextView(this);
        t4.setText("  |\n  |");
        t4.setTextColor(Color.BLACK);
        tr2.addView(t4);
        
        TextView Value= new TextView(this);
        Value.setId(2);
        Value.setText("  Value"+"\n"+"  in LB");
        Value.setTextColor(Color.BLACK);
        tr2.addView(Value);
                
        TextView t5= new TextView(this);
        t5.setText("  |"+"\n  |");
        t5.setTextColor(Color.BLACK);
        tr2.addView(t5);
        
        TextView Condition= new TextView(this);
        Condition.setId(3);
        Condition.setText("      Test"+"\n"+" Condition1");
        Condition.setTextColor(Color.BLACK);
        tr2.addView(Condition);
            
        TextView t12= new TextView(this);
        t12.setText("  |"+"\n  |");
        t12.setTextColor(Color.BLACK);
        tr2.addView(t12);
        
        TextView Condition1= new TextView(this);
        Condition1.setId(4);
        Condition1.setText("      Test"+"\n"+" Condition2");
        Condition1.setTextColor(Color.BLACK);
        tr2.addView(Condition1);
        
               
        tl.addView(tr2, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        mCursor = db.getWTLogFromDate(user, startDate+"00:00:00", endDate+"24:00:00");
        int current = 0;
      
        while(mCursor.moveToNext())
        {
            TableRow tr = new TableRow(this);
            tr.setId(100+current);
                     
            tr.setLayoutParams(new LayoutParams(
            		LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));  
            
            TextView date = new TextView(this);
            String d = mCursor.getString(6);
            String []d1 = new String[2];
            d1 = d.split(" ");
            date.setText("| "+d1[0]+"\n| "+d1[1]);
            date.setTextColor(Color.BLACK);
            tr.addView(date);
            
            TextView t6= new TextView(this);
            t6.setText("  |"+"\n  |");
            t6.setTextColor(Color.BLACK);
            tr.addView(t6);
            
            TextView value = new TextView(this);
            value.setText("  "+mCursor.getString(4));
            value.setTextColor(Color.BLACK);
            tr.addView(value);
            
            TextView t7= new TextView(this);
            t7.setText("  |"+"\n  |");
            t7.setTextColor(Color.BLACK);
            tr.addView(t7);
            
            TextView condition = new TextView(this);
            condition.setText("  "+mCursor.getString(3));
            condition.setTextColor(Color.BLACK);
            tr.addView(condition);
            
            TextView t8= new TextView(this);
            t8.setText("  |"+"\n  |");
            t8.setTextColor(Color.BLACK);
            tr.addView(t8);
            
            TextView condition1 = new TextView(this);
            String tmp = mCursor.getString(5);
            String []t = tmp.split(" ");
            condition1.setText("   "+t[0]+"\t  |\n   "+t[1]+"|");
            condition1.setTextColor(Color.BLACK);
            tr.addView(condition1);
            
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            
            current++;
           
        }
        TableRow tr3 = new TableRow(this);
        tr3.setBackgroundColor(Color.GRAY);
        
        TextView t1= new TextView(this);
        t1.setText("");
        tr3.addView(t1);
        TextView t2= new TextView(this);
        t2.setText("");
        tr3.addView(t2);
        TextView t3= new TextView(this);
        t3.setText("");
        tr3.addView(t3);
                
        tl.addView(tr3, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TableRow tr4 = new TableRow(this);
        tr4.setBackgroundColor(Color.WHITE);
        
        TextView t11= new TextView(this);
        t11.setText("");
        tr4.addView(t11);
        TextView t21= new TextView(this);
        t21.setText("");
        tr4.addView(t21);
        TextView t31= new TextView(this);
        t31.setText("");
        tr4.addView(t31);
                
        tl.addView(tr4, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
    }
    
    void bp()
    {
    	//logName.setText("Blood Pressure Log");
    	
    	//TableLayout tl = (TableLayout) findViewById(R.id.maintable);
    	/*if(flagAll == 0)
    		tl.removeAllViews();*/
    	
    	TableRow tr222 = new TableRow(this);
        
        TextView Date22= new TextView(this);
        Date22.setId(1);
        Date22.setText("  ");
       
        tr222.addView(Date22);
        
        TextView t422= new TextView(this);
        t422.setText("  ");
        t422.setTextColor(Color.BLACK);
        tr222.addView(t422);
        
        TextView Value22= new TextView(this);
        Value22.setId(2);
        Value22.setText("Blood Pressure Log");
        Value22.setTextColor(Color.BLACK);
        tr222.addView(Value22);
        
        TextView t522= new TextView(this);
        t522.setText("  ");
        t522.setTextColor(Color.BLACK);
        tr222.addView(t522);
        
        TextView Condition22= new TextView(this);
        Condition22.setId(3);
        Condition22.setText("  ");
       
        tr222.addView(Condition22);
            
        
        tl.addView(tr222, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
    	
        TableRow tr2 = new TableRow(this);
        tr2.setBackgroundColor(Color.GRAY);
        TextView Date= new TextView(this);
        Date.setId(1);
        Date.setText("\tDate & Time");
        Date.setTextColor(Color.BLACK);
        tr2.addView(Date);
        
        TextView t4= new TextView(this);
        t4.setText("  |");
        t4.setTextColor(Color.BLACK);
        tr2.addView(t4);
        
        TextView Value= new TextView(this);
        Value.setId(2);
        Value.setText("  mm of Hg");
        Value.setTextColor(Color.BLACK);
        tr2.addView(Value);
        
        TextView t5= new TextView(this);
        t5.setText("  |");
        t5.setTextColor(Color.BLACK);
        tr2.addView(t5);
        
        TextView Condition= new TextView(this);
        Condition.setId(3);
        Condition.setText("        Type");
        Condition.setTextColor(Color.BLACK);
        tr2.addView(Condition);
            
        
        tl.addView(tr2, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        mCursor = db.getBPLogFromDate(user, startDate+"00:00:00", endDate+"24:00:00");
        int current = 0;
      
        while(mCursor.moveToNext())
        {
            TableRow tr = new TableRow(this);
            tr.setId(100+current);
                     
            tr.setLayoutParams(new LayoutParams(
            		LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));  
            
            TextView date = new TextView(this);
            String d = mCursor.getString(6);
            String []d1 = new String[2];
            d1 = d.split(" ");
            date.setText("|  "+d1[0]+"\n| "+d1[1]);
            date.setTextColor(Color.BLACK);
            tr.addView(date);
            
            TextView t6= new TextView(this);
            t6.setText("  |"+"\n  |");
            t6.setTextColor(Color.BLACK);
            tr.addView(t6);
            
            TextView value = new TextView(this);
            value.setText(" "+mCursor.getString(3));
            value.setTextColor(Color.BLACK);
            tr.addView(value);
            
            TextView t7= new TextView(this);
            t7.setText("  |"+"\n  |");
            t7.setTextColor(Color.BLACK);
            tr.addView(t7);
            
            TextView condition = new TextView(this);
            condition.setText("\t DIASTOLIC"+"\t  |"+"\n\t\t\t\t\t  |");
            condition.setTextColor(Color.BLACK);
            tr.addView(condition);
            
            
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            
            TableRow tr1 = new TableRow(this);
            tr1.setId(10+current);
                     
            tr1.setLayoutParams(new LayoutParams(
            		LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));  
            
            TextView date1 = new TextView(this);
            date1.setText("|  "+d1[0]+"\n| "+d1[1]);
            date1.setTextColor(Color.BLACK);
            tr1.addView(date1);
            
            TextView t8= new TextView(this);
            t8.setText("  |"+"\n  |");
            t8.setTextColor(Color.BLACK);
            tr1.addView(t8);
            
            TextView value1 = new TextView(this);
            value1.setText(" "+mCursor.getString(4));
            value1.setTextColor(Color.BLACK);
            tr1.addView(value1);
            
            TextView t9= new TextView(this);
            t9.setText("  |"+"\n  |");
            t9.setTextColor(Color.BLACK);
            tr1.addView(t9);
            
            TextView condition1 = new TextView(this);
            condition1.setText("\t Pulse Count"+"\t  |"+"\n\t\t\t\t\t  |");
            condition1.setTextColor(Color.BLACK);
            tr1.addView(condition1);
            
            
            tl.addView(tr1, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            
            TableRow tr3 = new TableRow(this);
            tr3.setId(1000+current);
                     
            tr3.setLayoutParams(new LayoutParams(
            		LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));  
            
            TextView date3 = new TextView(this);
            date3.setText("|  "+d1[0]+"\n| "+d1[1]);
            date3.setTextColor(Color.BLACK);
            tr3.addView(date3);
            
            TextView t10= new TextView(this);
            t10.setText("  |"+"\n  |");
            t10.setTextColor(Color.BLACK);
            tr3.addView(t10);
            
            TextView value3 = new TextView(this);
            value3.setText(" "+mCursor.getString(5));
            value3.setTextColor(Color.BLACK);
            tr3.addView(value3);
            
            TextView t11= new TextView(this);
            t11.setText("  |"+"\n  |");
            t11.setTextColor(Color.BLACK);
            tr3.addView(t11);
            
            TextView condition3 = new TextView(this);
            condition3.setText("\t SYSTOLIC"+"\t  |"+"\n\t\t\t\t\t  |");
            condition3.setTextColor(Color.BLACK);
            tr3.addView(condition3);
            
            
            tl.addView(tr3, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            
            current++;
           
        }
        TableRow tr3 = new TableRow(this);
        tr3.setBackgroundColor(Color.GRAY);
        
        TextView t1= new TextView(this);
        t1.setText("");
        tr3.addView(t1);
        TextView t2= new TextView(this);
        t2.setText("");
        tr3.addView(t2);
        TextView t3= new TextView(this);
        t3.setText("");
        tr3.addView(t3);
                
        tl.addView(tr3, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TableRow tr4 = new TableRow(this);
        tr4.setBackgroundColor(Color.WHITE);
        
        TextView t11= new TextView(this);
        t11.setText("");
        tr4.addView(t11);
        TextView t21= new TextView(this);
        t21.setText("");
        tr4.addView(t21);
        TextView t31= new TextView(this);
        t31.setText("");
        tr4.addView(t31);
                
        tl.addView(tr4, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
    }
    
    void glucose()
    {
    	//logName.setText("Blood Glucose Log");
    	
    	//TableLayout tl = (TableLayout) findViewById(R.id.maintable);
    	/*if(flagAll == 0)
    		tl.removeAllViews();*/
    	
    	
      /*  TextView name= new TextView(this);
        name.setText("\t\t\tBlood Glucose Log\n");*/
        
    	System.out.println("Inside glucose()");
        TableRow tr222 = new TableRow(this);
        
        TextView Date22= new TextView(this);
        Date22.setId(1);
        Date22.setText("  ");
       
        tr222.addView(Date22);
        
        TextView t422= new TextView(this);
        t422.setText("  ");
        t422.setTextColor(Color.BLACK);
        tr222.addView(t422);
        
        TextView Value22= new TextView(this);
        Value22.setId(2);
        Value22.setText("Blood Glucose Log");
        Value22.setTextColor(Color.BLACK);
        tr222.addView(Value22);
        
        TextView t522= new TextView(this);
        t522.setText("  ");
        t522.setTextColor(Color.BLACK);
        tr222.addView(t522);
        
        TextView Condition22= new TextView(this);
        Condition22.setId(3);
        Condition22.setText("  ");
       
        tr222.addView(Condition22);
            
        
        tl.addView(tr222, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
                       
        TableRow tr2 = new TableRow(this);
        tr2.setBackgroundColor(Color.GRAY);
        TextView Date= new TextView(this);
        Date.setId(1);
        Date.setText("  Date & Time");
        Date.setTextColor(Color.BLACK);
        tr2.addView(Date);
        
        TextView t4= new TextView(this);
        t4.setText("  |");
        t4.setTextColor(Color.BLACK);
        tr2.addView(t4);
        
        TextView Value= new TextView(this);
        Value.setId(2);
        Value.setText("Value in mg/dl");
        Value.setTextColor(Color.BLACK);
        tr2.addView(Value);
        
        TextView t5= new TextView(this);
        t5.setText("  |");
        t5.setTextColor(Color.BLACK);
        tr2.addView(t5);
        
        TextView Condition= new TextView(this);
        Condition.setId(3);
        Condition.setText("  Test Condition");
        Condition.setTextColor(Color.BLACK);
        tr2.addView(Condition);
            
        
        tl.addView(tr2, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        mCursor = db.getGlucoseLogFromDate(user, startDate+"00:00:00", endDate+"24:00:00");
        
        System.out.println("Calling query glucose(): "+user+" "+startDate+"00:00:00"+" "+endDate+"24:00:00" );
        System.out.println("Start: "+startDate);
        System.out.println("End: "+endDate);
        
        int current = 0;
        System.out.println("No of records glucose(): "+mCursor.getCount());
        
        while(mCursor.moveToNext())
        {
            TableRow tr = new TableRow(this);
            tr.setId(100+current);
                     
            tr.setLayoutParams(new LayoutParams(
            		LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));  
            
            TextView date = new TextView(this);
            String d = mCursor.getString(5);
            String []d1 = new String[2];
            d1 = d.split(" ");
            date.setText("| "+d1[0]+"\n| "+d1[1]);
            date.setTextColor(Color.BLACK);
            tr.addView(date);
            
            TextView t6= new TextView(this);
            t6.setText("  |"+"\n  |");
            t6.setTextColor(Color.BLACK);
            tr.addView(t6);
            
            TextView value = new TextView(this);
            value.setText(" "+mCursor.getString(4));
            value.setTextColor(Color.BLACK);
            tr.addView(value);
            
            TextView t7= new TextView(this);
            t7.setText("  |"+"\n  |");
            t7.setTextColor(Color.BLACK);
            tr.addView(t7);
            
            TextView condition = new TextView(this);
            condition.setText(" "+mCursor.getString(3)+"\t\t|"+"\n\t\t\t\t\t|");
            condition.setTextColor(Color.BLACK);
            tr.addView(condition);
            
            
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            
            current++;
           
        }
        TableRow tr3 = new TableRow(this);
        tr3.setBackgroundColor(Color.GRAY);
        
        TextView t1= new TextView(this);
        t1.setText("");
        tr3.addView(t1);
        TextView t2= new TextView(this);
        t2.setText("");
        tr3.addView(t2);
        TextView t3= new TextView(this);
        t3.setText("");
        tr3.addView(t3);
                
        tl.addView(tr3, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TableRow tr4 = new TableRow(this);
        tr4.setBackgroundColor(Color.WHITE);
        
        TextView t11= new TextView(this);
        t11.setText("");
        tr4.addView(t11);
        TextView t21= new TextView(this);
        t21.setText("");
        tr4.addView(t21);
        TextView t31= new TextView(this);
        t31.setText("");
        tr4.addView(t31);
                
        tl.addView(tr4, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
    }

    void work()
    {
    	//user = ss.getSessionMemberID();
        db.open();
        
        tl = (TableLayout) findViewById(R.id.maintable);
        
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
        
        final String currentLog = ss.getSessionCurrentLog();
        
        
        
       // log = (TextView)findViewById(R.id.log);
        
        //logName = (TextView)findViewById(R.id.logName);
        
        /*array_spinner=new String[6];
        array_spinner[0]="Blood Glucose Log";
        array_spinner[1]="Blood Pressure Log";
        array_spinner[2]="Personal Weight Log";
        array_spinner[3]="Personal Hemoglobin Log";
        array_spinner[4]="Personal HbA1c Log";
        array_spinner[5]="All Logs";
           
        s = (Spinner) findViewById(R.id.selectLog);
        ArrayAdapter adapter = new ArrayAdapter(this,
        android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);
        
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String  item = (String) parent.getItemAtPosition(pos);
                
                if(item.equals("Blood Glucose Log"))
                {
                	//flagAll = 0;
                	tl.removeAllViews();
                   	glucose();
                }
                else if(item.equals("Blood Pressure Log"))
                {
                	//flagAll = 0;
                	tl.removeAllViews();
                   	bp();
                }
                else if(item.equals("Personal Weight Log"))
                {
                	//flagAll = 0;
                	tl.removeAllViews();
                   	wt();
                }
                else if(item.equals("Personal Hemoglobin Log"))
                {
                	//flagAll = 0;
                	tl.removeAllViews();
                   	hemoglobin();
                }
                else if(item.equals("Personal HbA1c Log"))
                {
                	//flagAll = 0;
                	tl.removeAllViews();
                	hba1c();
                }
                else if(item.equals("All Logs"))
                {
                	//flagAll = 1;
                	tl.removeAllViews();
                	glucose();
                	bp();
                   	wt();
                   	hemoglobin();
                   	hba1c();
                }
              }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

        dateDisplay1 = (TextView)findViewById(R.id.dateDisplay1);
        dateDisplay2 = (TextView)findViewById(R.id.dateDisplay2);
        
        changeDate1 = (ImageButton)findViewById(R.id.changeDate1);
        changeDate1.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				showDialog(DATE_DIALOG_ID1);
			}
		});
        
        changeDate2 = (ImageButton)findViewById(R.id.changeDate2);
        changeDate2.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				showDialog(DATE_DIALOG_ID2);
			}
		});
        
        final Calendar c = Calendar.getInstance();
        mYear1 = mYear2 = c.get(Calendar.YEAR);
        mMonth1 =mMonth2 = c.get(Calendar.MONTH);
        mDay1 = mDay2 = c.get(Calendar.DAY_OF_MONTH);
        
        updateDisplay1();
        updateDisplay2();
        
        
        if(currentLog.equals("Blood Glucose Log"))
        {
        	//flagAll = 0;
        	tl.removeAllViews();
           	glucose();
           	System.out.println("Calling glucose()");
        }
        else if(currentLog.equals("Blood Pressure Log"))
        {
        	//flagAll = 0;
        	tl.removeAllViews();
           	bp();
        }
        else if(currentLog.equals("Personal Weight Log"))
        {
        	//flagAll = 0;
        	tl.removeAllViews();
           	wt();
        }
        else if(currentLog.equals("Personal Hemoglobin Log"))
        {
        	//flagAll = 0;
        	tl.removeAllViews();
           	hemoglobin();
        }
        else if(currentLog.equals("Personal HbA1c Log"))
        {
        	//flagAll = 0;
        	tl.removeAllViews();
        	hba1c();
        }
        
        
        search = (Button)findViewById(R.id.search);
        search.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
			           
                if(currentLog.equals("Blood Glucose Log"))
                {
                	//flagAll = 0;
                	tl.removeAllViews();
                   	glucose();
                }
                else if(currentLog.equals("Blood Pressure Log"))
                {
                	//flagAll = 0;
                	tl.removeAllViews();
                   	bp();
                }
                else if(currentLog.equals("Personal Weight Log"))
                {
                	//flagAll = 0;
                	tl.removeAllViews();
                   	wt();
                }
                else if(currentLog.equals("Personal Hemoglobin Log"))
                {
                	//flagAll = 0;
                	tl.removeAllViews();
                   	hemoglobin();
                }
                else if(currentLog.equals("Personal HbA1c Log"))
                {
                	//flagAll = 0;
                	tl.removeAllViews();
                   	hba1c();
                }
                /*else if(currentLog.equals("All Logs"))
                {
                	//flagAll = 1;
                	tl.removeAllViews();
                	glucose();
                	bp();
                   	wt();
                   	hemoglobin();
                   	hba1c();
                }*/
				//Cursor c = db.getGlucoseLogFromDate(user, startDate, endDate);
			}
		});          
        
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				back.setBackgroundResource(R.drawable.back_h);
				Intent i = new Intent(ViewData.this, HomePage.class);
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
            //l11.setPadding(0, 20, 0, 0);

       } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
       	 RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.rLogin);
            Resources res = getResources(); //resource handle
            Drawable drawable = res.getDrawable(R.drawable.background_2); //new Image that was added to the res folder

            rLayout.setBackgroundDrawable(drawable);
            
           // l11.setPadding(0, 20, 0, 0);
       }
    }
    
}
