package com.PersonalHealthRecord;

import java.util.Calendar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GetMedication extends Activity{
	
	private static final String NAMESPACE = "http://tempuri.org/";
	private static final String URL = 
		"http://192.168.1.203/panhealth/PickPillService.asmx";	
	private static final String SOAP_ACTION = "http://tempuri.org/getMedicineInformation";
	private static final String METHOD_NAME = "getMedicineInformation";
	Session ss = new Session();
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView result = (TextView)findViewById(R.id.result);
        
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
        System.out.println("MID: "+ss.getSessionMemberID());
        
		//request.addProperty("Me_id", ss.getSessionMemberID()); 
        request.addProperty("Me_id", "A00000004303"); 
					        		
		SoapSerializationEnvelope envelope = 
			new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		envelope.setOutputSoapObject(request);
		envelope.dotNet=true;
		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			
		try 
		{
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
					
	        result.setText("Result: \n"+resultsRequestSOAP.toString());
	      //  System.out.println("Result:\n"+resultsRequestSOAP.toString());
			
		} catch (Exception e) 
		{
	        result.setText("Execption...");
			e.printStackTrace();
		}        
     
    }

}
