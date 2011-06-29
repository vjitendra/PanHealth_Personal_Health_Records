package com.PersonalHealthRecord;

public class Session {

	private static String MemberID;
	private static String MemberName;
	private static String SyncData;
	private static int workOffLine;
	private static String newID;
	private static String newPass;
	private static String currentLog;
		
	public static void setSessionMemberID(String MemberID) {
	    Session.MemberID = MemberID;
	}

	public static String getSessionMemberID() {
	    return MemberID;
	}
	
	public static void setSessionCurrentLog(String currentLog) {
	    Session.currentLog = currentLog;
	}

	public static String getSessionCurrentLog() {
	    return currentLog;
	}
	
	public static void setSessionMemberName(String MemberName) {
	    Session.MemberName = MemberName;
	}

	public static String getSessionMemberName() {
	    return MemberName;
	}
	public static void setSessionSyncData(String SyncData) {
	    Session.SyncData = SyncData;
	}

	public static String getSessionSyncData() {
	    return SyncData;
	}
	
	public static void setSessionworkOffLine(int workOffLine) {
	    Session.workOffLine = workOffLine;
	}

	public static int getSessionworkOffLine() {
	    return workOffLine;
	}
	
	public static void setSessionNewID(String newID) {
	    Session.newID = newID;
	}

	public static String getSessionNewID() {
	    return newID;
	}
	
	public static void setSessionNewPass(String newPass) {
	    Session.newPass = newPass;
	}

	public static String getSessionNewPass() {
	    return newPass;
	}
}
