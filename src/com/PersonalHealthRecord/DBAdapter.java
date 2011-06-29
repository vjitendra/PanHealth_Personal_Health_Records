package com.PersonalHealthRecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

	private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "PersonalHealthRecord";
    
    //  GlucoseLog Table
    public static final String KEY_GID = "gid";
    public static final String KEY_MEMBERID = "MemberID";
    public static final String KEY_DATATYPE = "DataType";
    public static final String KEY_COM_DEVICEID = "ComDeviceID";
    public static final String KEY_COM_DEVICE_SRNO = "ComDeviceSrNo";
    public static final String KEY_TST_CON_FOOD = "TstConFood";
    public static final String KEY_BG = "Bg";
    public static final String KEY_BPdia = "BPdia";
    public static final String KEY_BPpulse = "BPpulse";
    public static final String KEY_BPsys = "BPsys";
    public static final String KEY_WEIGHT = "Weight";
    public static final String KEY_HG = "Hg";
    public static final String KEY_HBA1C = "HbA1c";
    public static final String KEY_MEDI_NAME = "MediName";
    public static final String KEY_MEDI_QTY = "MediQty";
    public static final String KEY_TST_CON_EXERCISE = "TstConExercise";
    public static final String KEY_READING_DT = "ReadingDT";
    public static final String KEY_STATUS = "status";
    
    public static final String KEY_LID = "lid";
    //public static final String KEY_MEMBERID = "MemberID";
    public static final String KEY_PASS = "pass";
    public static final String KEY_UNAME = "uname";
    public static final String KEY_WORK_OFFLINE = "workOffLine";
    
    
    // Creating all tables
    
    private static final String LOG_TABLE = "LogTable";
    private static final String LOGIN_TABLE = "LoginTable";
    
       
    private static final int DATABASE_VERSION = 3;
                
  
    private static final String LOG_TABLE_CREATE =
        "create table LogTable (gid integer primary key autoincrement, "
        + "MemberID text not null, DataType text not null, ComDeviceID text not null, "
        + "ComDeviceSrNo text not null, TstConFood text not null, "
        + "Bg int not null, BPdia int not null, BPpulse int not null, BPsys int not null, "
        + "Weight int not null, Hg int not null, HbA1c int not null, "
        + "MediName text not null, MediQty int not null, "
        + "TstConExercise text not null, ReadingDT text not null, status text not null);";
    

    private static final String LOGIN_TABLE_CREATE =
        "create table LoginTable (lid integer primary key autoincrement, "
        + "MemberID text not null, pass text not null, uname text not null, "
        + "workOffLine int not null);";
    
    private final Context context; 
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
        	db.execSQL(LOG_TABLE_CREATE);
        	db.execSQL(LOGIN_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
        int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                    + " to "
                    + newVersion + ", which will destroy all old data");
            
            db.execSQL("DROP TABLE IF EXISTS LogTable");
            db.execSQL("DROP TABLE IF EXISTS LoginTable");
            onCreate(db);
        }
    }    
    
    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //------------------------------------------------------------------
    	//                     LOGIN table
    
    //------------------------------------------------------------------
    
    public long insertLogin(String MemberID, String pass, String uname, long workOffLine) 
    {
    	//KEY_TST_CON_EXERCISE
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_MEMBERID, MemberID);
        initialValues.put(KEY_PASS, pass);
        initialValues.put(KEY_UNAME, uname);
        initialValues.put(KEY_WORK_OFFLINE, workOffLine);
        return db.insert(LOGIN_TABLE, null, initialValues);
    }
    
    public boolean deleteLogin(long rowId) 
    {
        return db.delete(LOGIN_TABLE, KEY_LID + 
        		"=" + rowId, null) > 0;
    }

    public boolean deleteLogin(String MemberID) 
    {
        return db.delete(LOGIN_TABLE, KEY_MEMBERID + 
        		"='" + MemberID+"'", null) > 0;
    }
    
    //---retrieves all the GlucoseLogs---
    public Cursor getAllLogins() 
    {
    	//KEY_TST_CON_EXERCISE, TstConExercise
        return db.query(LOGIN_TABLE, new String[] {
        		KEY_LID, 
        		KEY_MEMBERID, 
        		KEY_PASS,
        		KEY_UNAME,
        		KEY_WORK_OFFLINE
        		}, 
                null, 
                null, 
                null,
                null, 
                null);
    }
    
    public boolean checkLogin(String MemberID, String pwd)
    {
    	Cursor mCursor =
            db.query(true, LOG_TABLE, new String[] {
            		KEY_LID	
            		}, 
            		KEY_MEMBERID + "='" + MemberID + "' and "+KEY_PASS+ " = '"+pwd+"'", 
            		null,
            		null, 
            		null, 
            		null, 
            		null);
    	
    	int count = mCursor.getCount();
    	
    	if (count > 0) 
    	   	return true;
    	else
    		return false;
    	
    }
    
    public String getUserName(String MemberID)
    {
    	Cursor mCursor =
            db.query(true, LOG_TABLE, new String[] {
            		KEY_UNAME	
            		}, 
            		KEY_MEMBERID + "='" + MemberID + "'", 
            		null,
            		null, 
            		null, 
            		null, 
            		null);
    	
    	if (mCursor != null) {
            mCursor.moveToFirst();
            return mCursor.getString(0);
        }
    	else
    		return "";
    }
    
    public Cursor getLogin(String MemberID) throws SQLException 
    {
    	
        Cursor mCursor =
                db.query(true, LOG_TABLE, new String[] {
                		KEY_LID, 
                		KEY_MEMBERID, 
                		KEY_PASS,
                		KEY_UNAME,
                		KEY_WORK_OFFLINE
                		}, 
                		KEY_MEMBERID + "='" + MemberID + "'", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        return mCursor;
    }
    
    //						-------------------------------------
    //									Log TABLE
    //						-------------------------------------
    
    
    
    
    //---insert a Log into the database---
    public long insertLog(String MemberID, String DataType, String ComDeviceID, 
    		String ComDeviceSrNo, 
    		String TstConFood, long Bg, long BPdia, long BPpulse, long BPsys, 
    		long Weight, long Hg, long HbA1c, String MediName, long MediQty,
    		String TstConExercise, 
    		String ReadingDT, String status) 
    {
    	ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_MEMBERID, MemberID);
        initialValues.put(KEY_DATATYPE, DataType);
        initialValues.put(KEY_COM_DEVICEID, ComDeviceID);
        initialValues.put(KEY_COM_DEVICE_SRNO, ComDeviceSrNo);
        initialValues.put(KEY_TST_CON_FOOD, TstConFood);
        initialValues.put(KEY_BG, Bg);
        initialValues.put(KEY_BPdia, BPdia);
        initialValues.put(KEY_BPpulse, BPpulse);
        initialValues.put(KEY_BPsys, BPsys);
        initialValues.put(KEY_WEIGHT, Weight);
        initialValues.put(KEY_HG, Hg);
        initialValues.put(KEY_HBA1C, HbA1c);
        initialValues.put(KEY_MEDI_NAME, MediName);
        initialValues.put(KEY_MEDI_QTY, MediQty);
        initialValues.put(KEY_TST_CON_EXERCISE, TstConExercise);
        initialValues.put(KEY_READING_DT, ReadingDT);
        initialValues.put(KEY_STATUS, status);
        return db.insert(LOG_TABLE, null, initialValues);
    }

    
    
    //---deletes a particular GlucoseLog---
    public boolean deleteLog(long rowId) 
    {
        return db.delete(LOG_TABLE, KEY_GID + 
        		"=" + rowId, null) > 0;
    }

    public boolean deleteLog(String MemberID) 
    {
        return db.delete(LOG_TABLE, KEY_MEMBERID + 
        		"='" + MemberID+"'", null) > 0;
    }
    
    //---retrieves all the GlucoseLogs---
    public Cursor getAllLogs() 
    {
    	//KEY_TST_CON_EXERCISE, TstConExercise
        return db.query(LOG_TABLE, new String[] {
        		KEY_GID, 
        		KEY_MEMBERID, 
        		KEY_DATATYPE,
        		KEY_COM_DEVICEID,
        		KEY_COM_DEVICE_SRNO,
        		KEY_TST_CON_FOOD,
        		KEY_BG,
        		KEY_BPdia,
        		KEY_BPpulse,
        		KEY_BPsys,
        		KEY_WEIGHT,
        		KEY_HG,
        		KEY_HBA1C,
        		KEY_MEDI_NAME, 
        	    KEY_MEDI_QTY,
        		KEY_TST_CON_EXERCISE,
        		KEY_READING_DT,
        		KEY_STATUS	
        		}, 
                null, 
                null, 
                null,
                null, 
                null);
    }
        
       
    public int getLogId(String MemberID) 
    {
    	Cursor mCursor=db.rawQuery("SELECT "+KEY_GID+
    			" from "+LOG_TABLE+
        		" where "+KEY_MEMBERID+"='"+MemberID+"'",new String [] {});
     	
    	 if (mCursor.moveToFirst())  
    	 	 return mCursor.getInt(0);
    	 else
    		 return 0;
    }
    
      
    public String[] getLogNames()
    {
    	Cursor mCursor =  db.query(LOG_TABLE, new String[] {
    			KEY_MEMBERID
        		}, 
                null, 
                null, 
                null,
                null, 
                null);
    	
    	String []album_names = new String[mCursor.getCount()];
     	int j=0;
     	
     	while(mCursor.moveToNext())
        {
     		album_names[j] = mCursor.getString(0);
        	j++;
        }
         return album_names;
    }
    
    public int getnoOfLogs() 
    {
        Cursor c =  db.query(LOG_TABLE, new String[] {
        		KEY_GID 
        		}, 
                null, 
                null, 
                null,
                null, 
                null);
        return c.getCount();
    }
    
   
    
    //---retrieves a particular Album---
    
    
    public Cursor getLog(long rowId) throws SQLException 
    {
    	
        Cursor mCursor =
                db.query(true, LOG_TABLE, new String[] {
                		KEY_GID, 
                		KEY_MEMBERID, 
                		KEY_DATATYPE,
                		KEY_COM_DEVICEID,
                		KEY_COM_DEVICE_SRNO,
                		KEY_TST_CON_FOOD,
                		KEY_BG,
                		KEY_BPdia,
                		KEY_BPpulse,
                		KEY_BPsys,
                		KEY_WEIGHT,
                		KEY_HG,
                		KEY_HBA1C,
                		KEY_MEDI_NAME, 
                	    KEY_MEDI_QTY,
                		KEY_TST_CON_EXERCISE,
                		KEY_READING_DT,
                		KEY_STATUS	
                		}, 
                		KEY_GID + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        return mCursor;
    }
    
    
    
       
    public String getLogMemberID(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, LOG_TABLE, new String[] {
                		KEY_MEMBERID
                		}, 
                		KEY_GID + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor.getString(0);
    }
       
    //---updates a GlucoseLog---
    public boolean updateLog(long rowId, String MemberID, String DataType, String ComDeviceID, String ComDeviceSrNo, 
    		String TstConFood, long Bg, long BPdia, long BPpulse, long BPsys, 
    		long Weight, long Hg, long HbA1c, String MediName, long MediQty,
    		String TstConExercise, String ReadingDT, String status) 
    {
    	
        ContentValues args = new ContentValues();
        args.put(KEY_GID, rowId);
        args.put(KEY_MEMBERID, MemberID);
        args.put(KEY_DATATYPE, DataType);
        args.put(KEY_COM_DEVICEID, ComDeviceID);
        args.put(KEY_COM_DEVICE_SRNO, ComDeviceSrNo);
        args.put(KEY_TST_CON_FOOD, TstConFood);
        args.put(KEY_BG, Bg);
        args.put(KEY_BPdia, BPdia);
        args.put(KEY_BPpulse, BPpulse);
        args.put(KEY_BPsys, BPsys);
        args.put(KEY_WEIGHT, Weight);
        args.put(KEY_HG, Hg);
        args.put(KEY_HBA1C, HbA1c);
        args.put(KEY_MEDI_NAME, MediName);
        args.put(KEY_MEDI_QTY, MediQty);
        args.put(KEY_TST_CON_EXERCISE, TstConExercise);
        args.put(KEY_READING_DT, ReadingDT);
        args.put(KEY_STATUS, status);
        return db.update(LOG_TABLE, args, 
        		KEY_GID + "=" + rowId, null) > 0;
    }
    
    				//						GLUCOSE
    
    public Cursor getGlucoseLog(String MemberID) throws SQLException 
    {
    	String bg="BG";
        Cursor mCursor =
                db.query(true, LOG_TABLE, new String[] {
                		KEY_GID, 
                		KEY_COM_DEVICEID,
                		KEY_COM_DEVICE_SRNO,
                		KEY_TST_CON_FOOD,
                		KEY_BG,
                		KEY_READING_DT,
                		KEY_STATUS	
                		}, 
                		KEY_MEMBERID + "='" + MemberID + "' and "+KEY_DATATYPE+ " = '"+bg+"'", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        
        return mCursor;
    }
    
    public Cursor getGlucoseLogFromDate(String MemberID, String startDate, String endDate) throws SQLException 
    {
    	String bg="BG";
    	
    	Cursor mCursor=db.rawQuery("SELECT "+KEY_GID+","+KEY_COM_DEVICEID+","+KEY_COM_DEVICE_SRNO+","+
    			KEY_TST_CON_FOOD+","+KEY_BG+","+KEY_READING_DT+","+KEY_STATUS+
    			" from "+LOG_TABLE+
        		" where "+KEY_MEMBERID+" = '" + MemberID + "' and "+KEY_DATATYPE+ " = '"+bg+"' and "
        		+KEY_READING_DT+" BETWEEN '"+startDate+"' AND '"+endDate+"'",new String [] {});

    	return mCursor;
    }
    
    
    
    public Cursor SyncGlucoseLog(String MemberID) throws SQLException 
    {
    	String bg="BG";
    	String status = "n";
    	
    	String str = "SELECT "+KEY_GID+","+
		KEY_TST_CON_FOOD+","+KEY_BG+","+KEY_READING_DT+
		" from "+LOG_TABLE+
		" where "+KEY_MEMBERID+" = '" + MemberID + 
		"' and "+KEY_DATATYPE+ " = '"+bg+"' and "+KEY_STATUS+" = '"+status+"'";
    	
    	Cursor mCursor=db.rawQuery("SELECT "+KEY_GID+","+
    			KEY_TST_CON_FOOD+","+KEY_BG+","+KEY_READING_DT+
    			" from "+LOG_TABLE+
        		" where "+KEY_MEMBERID+" = '" + MemberID + 
        		"' and "+KEY_DATATYPE+ " = '"+bg+
        		"' and "+KEY_STATUS+" = '"+status+"'",new String [] {});

    	return mCursor;
    }
    
    				//					BP
    
    public Cursor getBPLog(String MemberID) throws SQLException 
    {
    	String bp="BP";
        Cursor mCursor =
                db.query(true, LOG_TABLE, new String[] {
                		KEY_GID, 
                		KEY_COM_DEVICEID,
                		KEY_COM_DEVICE_SRNO,
                		KEY_BPdia,
                		KEY_BPpulse,
                		KEY_BPsys,
                		KEY_READING_DT,
                		KEY_STATUS	
                		}, 
                		KEY_MEMBERID + "='" + MemberID + "' and "+KEY_DATATYPE+ " = '"+bp+"'", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        
        return mCursor;
    }
    
    public Cursor getBPLogFromDate(String MemberID, String startDate, String endDate) throws SQLException 
    {
    	String bp="BP";
    	
    	Cursor mCursor=db.rawQuery("SELECT "+KEY_GID+","+KEY_COM_DEVICEID+","+KEY_COM_DEVICE_SRNO+","+
    			KEY_BPdia+","+KEY_BPpulse+","+KEY_BPsys+","+KEY_READING_DT+","+KEY_STATUS+
    			" from "+LOG_TABLE+
        		" where "+KEY_MEMBERID+" = '" + MemberID + "' and "+KEY_DATATYPE+ " = '"+bp+"' and "
        		+KEY_READING_DT+" BETWEEN '"+startDate+"' AND '"+endDate+"'",new String [] {});

    	return mCursor;
    }
    
    public Cursor SyncBPLog(String MemberID) throws SQLException 
    {
    	String bp="BP";
    	String status = "n";
    	
    	Cursor mCursor=db.rawQuery("SELECT "+KEY_GID+","+
    			KEY_BPdia+","+KEY_BPpulse+","+KEY_BPsys+","+KEY_READING_DT+
    			" from "+LOG_TABLE+
        		" where "+KEY_MEMBERID+" = '" + MemberID + 
        		"' and "+KEY_DATATYPE+ " = '"+bp+"' and "+KEY_STATUS+" = '"+status+"'",new String [] {});

    	return mCursor;
    }
    
					//					WT   
    
    public Cursor getWTLog(String MemberID) throws SQLException 
    {
    	String wt="WT";
        Cursor mCursor =
                db.query(true, LOG_TABLE, new String[] {
                		KEY_GID, 
                		KEY_COM_DEVICEID,
                		KEY_COM_DEVICE_SRNO,
                		KEY_TST_CON_FOOD,
                		KEY_WEIGHT,
                		KEY_TST_CON_EXERCISE,
                		KEY_READING_DT,
                		KEY_STATUS	
                		}, 
                		KEY_MEMBERID + "='" + MemberID + "' and "+KEY_DATATYPE+ " = '"+wt+"'", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        
        return mCursor;
    }
    
    public Cursor getWTLogFromDate(String MemberID, String startDate, String endDate) throws SQLException 
    {
    	String wt="WT";
    	
    	Cursor mCursor=db.rawQuery("SELECT "+KEY_GID+","+KEY_COM_DEVICEID+","+KEY_COM_DEVICE_SRNO+","+
    			KEY_TST_CON_FOOD+","+KEY_WEIGHT+","+KEY_TST_CON_EXERCISE+","+KEY_READING_DT+","+KEY_STATUS+
    			" from "+LOG_TABLE+
        		" where "+KEY_MEMBERID+" = '" + MemberID + "' and "+KEY_DATATYPE+ " = '"+wt+"' and "
        		+KEY_READING_DT+" BETWEEN '"+startDate+"' AND '"+endDate+"'",new String [] {});

    	return mCursor;
    }
    
    public Cursor SyncWTLog(String MemberID) throws SQLException 
    {
    	String wt="WT";
    	String status = "n";
    	
    	Cursor mCursor=db.rawQuery("SELECT "+KEY_GID+","+
    			KEY_TST_CON_FOOD+","+KEY_WEIGHT+","+KEY_TST_CON_EXERCISE+
    			","+KEY_READING_DT+
    			" from "+LOG_TABLE+
        		" where "+KEY_MEMBERID+" = '" + MemberID + 
        		"' and "+KEY_DATATYPE+ " = '"+wt+"' and "+KEY_STATUS+" = '"+status+"'",new String [] {});

    	return mCursor;
    }
    
				//					Hemoglobin  HG
    
    public Cursor getHGLog(String MemberID) throws SQLException 
    {
    	String hg="HG";
        Cursor mCursor =
                db.query(true, LOG_TABLE, new String[] {
                		KEY_GID, 
                		KEY_COM_DEVICEID,
                		KEY_COM_DEVICE_SRNO,
                		KEY_HG,
                		KEY_READING_DT,
                		KEY_STATUS	
                		}, 
                		KEY_MEMBERID + "='" + MemberID + "' and "+KEY_DATATYPE+ " = '"+hg+"'", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        
        return mCursor;
    }
    
    public Cursor getHGLogFromDate(String MemberID, String startDate, String endDate) throws SQLException 
    {
    	String hg="HG";
    	
    	Cursor mCursor=db.rawQuery("SELECT "+KEY_GID+","+KEY_COM_DEVICEID+","+KEY_COM_DEVICE_SRNO+","+
    			KEY_HG+","+KEY_READING_DT+","+KEY_STATUS+
    			" from "+LOG_TABLE+
        		" where "+KEY_MEMBERID+" = '" + MemberID + "' and "+KEY_DATATYPE+ " = '"+hg+"' and "
        		+KEY_READING_DT+" BETWEEN '"+startDate+"' AND '"+endDate+"'",new String [] {});

    	return mCursor;
    }
    
    public Cursor SyncHGLog(String MemberID) throws SQLException 
    {
    	String hg="HG";
    	String status = "n";
    	
    	Cursor mCursor=db.rawQuery("SELECT "+KEY_GID+","+
    			KEY_HG+","+KEY_READING_DT+
    			" from "+LOG_TABLE+
        		" where "+KEY_MEMBERID+" = '" + MemberID + 
        		"' and "+KEY_DATATYPE+ " = '"+hg+"' and "+KEY_STATUS+" = '"+status+"'",new String [] {});

    	return mCursor;
    }
    
				//					Manual  
    
    public Cursor getManualLog(String MemberID) throws SQLException 
    {
    	String mme="MME";
        Cursor mCursor =
                db.query(true, LOG_TABLE, new String[] {
                		KEY_GID, 
                		KEY_COM_DEVICEID,
                		KEY_COM_DEVICE_SRNO,
                		KEY_MEDI_NAME,
                		KEY_MEDI_QTY,
                		KEY_READING_DT,
                		KEY_STATUS	
                		}, 
                		KEY_MEMBERID + "='" + MemberID + "' and "+KEY_DATATYPE+ " = '"+mme+"'", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        
        return mCursor;
    }
    
    public Cursor getManualLogFromDate(String MemberID, String startDate, String endDate) throws SQLException 
    {
    	String mme="MME";
    	
    	Cursor mCursor=db.rawQuery("SELECT "+KEY_GID+","+KEY_COM_DEVICEID+","+KEY_COM_DEVICE_SRNO+","+
    			KEY_MEDI_NAME+","+KEY_MEDI_QTY+","+KEY_READING_DT+","+KEY_STATUS+
    			" from "+LOG_TABLE+
        		" where "+KEY_MEMBERID+" = '" + MemberID + "' and "+KEY_DATATYPE+ " = '"+mme+"' and "
        		+KEY_READING_DT+" BETWEEN '"+startDate+"' AND '"+endDate+"'",new String [] {});

    	return mCursor;
    }
    
    public Cursor SyncManualLog(String MemberID) throws SQLException 
    {
    	String mme="MME";
    	String status = "n";
    	
    	Cursor mCursor=db.rawQuery("SELECT "+KEY_GID+","+
    			KEY_MEDI_NAME+","+KEY_MEDI_QTY+","+KEY_READING_DT+
    			" from "+LOG_TABLE+
        		" where "+KEY_MEMBERID+" = '" + MemberID + 
        		"' and "+KEY_DATATYPE+ " = '"+mme+"' and "+KEY_STATUS+" = '"+status+"'",new String [] {});

    	return mCursor;
    }
    
				//					HbA1c  
    
    public Cursor getHbA1cLog(String MemberID) throws SQLException 
    {
    	String hba1c="HbA1c";
        Cursor mCursor =
                db.query(true, LOG_TABLE, new String[] {
                		KEY_GID, 
                		KEY_COM_DEVICEID,
                		KEY_COM_DEVICE_SRNO,
                		KEY_HBA1C,
                		KEY_READING_DT,
                		KEY_STATUS	
                		}, 
                		KEY_MEMBERID + "='" + MemberID + "' and "+KEY_DATATYPE+ " = '"+hba1c+"'", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        
        return mCursor;
    }
    
    public Cursor getHbA1cLogFromDate(String MemberID, String startDate, String endDate) throws SQLException 
    {
    	String hba1c="HbA1c";
    	
    	Cursor mCursor=db.rawQuery("SELECT "+KEY_GID+","+KEY_COM_DEVICEID+","+KEY_COM_DEVICE_SRNO+","+
    			KEY_HBA1C+","+KEY_READING_DT+","+KEY_STATUS+
    			" from "+LOG_TABLE+
        		" where "+KEY_MEMBERID+" = '" + MemberID + "' and "+KEY_DATATYPE+ " = '"+hba1c+"' and "
        		+KEY_READING_DT+" BETWEEN '"+startDate+"' AND '"+endDate+"'",new String [] {});

    	return mCursor;
    }
    
    public Cursor SyncHbA1cLog(String MemberID) throws SQLException 
    {
    	String hba1c="HbA1c";
    	String status = "n";
    	
    	Cursor mCursor=db.rawQuery("SELECT "+KEY_GID+","+
    			KEY_HBA1C+","+KEY_READING_DT+
    			" from "+LOG_TABLE+
        		" where "+KEY_MEMBERID+" = '" + MemberID + 
        		"' and "+KEY_DATATYPE+ " = '"+hba1c+"' and "+KEY_STATUS+" = '"+status+"'",new String [] {});

    	return mCursor;
    }
    
    
    public boolean updateAllLog(long rowId, String status) 
    {
    	
        ContentValues args = new ContentValues();
        args.put(KEY_STATUS, status);
        return db.update(LOG_TABLE, args, 
        		KEY_GID + "=" + rowId, null) > 0;
    }
    
}
