package edu.asu.cse360.team25.client.nurse;

import java.io.IOException;
import java.net.Socket;

import edu.asu.cse360.team25.client.ClientServerConnection;
import edu.asu.cse360.team25.client.doctor.DoctorServerConnection;
import edu.asu.cse360.team25.client.patient.PatientClient;
import edu.asu.cse360.team25.client.patient.PatientServerConnection;
import edu.asu.cse360.team25.protocol.DoctorServerMsg;
import edu.asu.cse360.team25.protocol.PatientInfo;
import edu.asu.cse360.team25.protocol.PatientServerMsg;
import edu.asu.cse360.team25.protocol.exception.InvalidDataRecordException;
import edu.asu.cse360.team25.protocol.exception.InvalidProtocolStateException;
import edu.asu.cse360.team25.protocol.exception.ProtocolErrorException;
import java.util.HashSet;

public class NurseServerConnection extends ClientServerConnection {

	protected NurseClient nc;
	protected NurseMainFrame nmf;
    
        protected static final int nurseListeningPort = 10232;
	protected static final String serverAddress = "localhost";
        
        protected ConnectionState state = ConnectionState.INIT;
        
        protected boolean expectingSpecificMsg = false;
        protected HashSet<PatientServerMsg> emsg = new HashSet<PatientServerMsg>();
	
        PatientClient pc;
	
	protected int nurseID;
        
        protected static enum ConnectionState {

        INIT, ONLINE, IN_CASE
    }
	
	public NurseServerConnection(NurseClient nc) {
		super(serverAddress, nurseListeningPort);
                
                state = ConnectionState.INIT;
                
                this.nc = nc;
	}


	@Override
	protected void dispatchReceivedMsg(String msg) {
		// TODO Auto-generated method stub
		int mark = msg.indexOf('#');
		String type = msg.substring(0, mark);
		String content = msg.substring(mark + 1);
		
		
		if (type.equals("LoginAck"))
			onLoginAck(content);
		else if (type.equals("LogoutAck"))
			onLogoutAck(content);
		else if (type.equals("QueryPatientProfileAck"))
			onQueryPatientProfileAck(content);
		else if (type.equals("AddLabMeasurementAck"))
			onAddLabMeasurementAck(content);
	}
	
	// message sent from server and received by patient
	
	
	protected void onLoginAck(String content) {

//		if(content.equals("OK!"))
//			nc.setLoginAck(true);
//		else
//			nc.setLoginAck(false);

		synchronized(nc) {
			nc.notify();
		}
		
		
		// enter the main page

		System.out.println("LoginAck received.");
	}
	
	protected void onLogoutAck(String content) {
            //to do
		// quit
//            if(content.equals("OK!"))
//			nc.setLogoutAck(true);
//            else
//			nc.setLogoutAck(false);

            synchronized(nc) {
			nc.notify();
		}
            

		System.out.println("LogoutAck received.");
	}
        

	
	protected void onQueryPatientProfileAck(String content) {

		// display patient profile
		System.out.println("QueryPatientProfileAck received. content = <" + content + ">");

		try {
			PatientInfo pi = new PatientInfo(content);
			
			pc.setPatientInfo(pi);
			
			System.out.println("\t" + pi.toString());
			
			synchronized(pc) {
				pc.notify();
			}

			
		} catch (InvalidDataRecordException e) {
			e.printStackTrace();
		}
		
	}

	protected void onAddLabMeasurementAck(String content) {
		System.out.println("AddLabMeasurementAck received. content = <" + content + ">");
		
		
	}
	
	
	// message sent from nurse and received by server
	
	
	
	protected void sendLogin(int id, String password) throws IOException,
			ProtocolErrorException{
		
		nurseID = id;
		
		// <Login#PatientID#Password>
		sendMsg("Login#" + id + "#" + password);
		System.out.println("Login sent.");
	}
	
	protected void sendLogout() throws IOException {
		
		// <Logout#PatientID>

		sendMsg("Logout#" + nurseID);
	}
	
        
        
    public void sendQueryCaseList() throws IOException,
            InvalidProtocolStateException {

        if (state != ConnectionState.ONLINE) {
            throw new InvalidProtocolStateException(
                    "Can not send QueryCaseList message when processing case or before login!!!");
        }

        setSpecificExpectedMsg(PatientServerMsg.QUERY_CASE_LIST_ACK);

		// <QueryCaseList#>
        sendMsg("QueryCaseList#");

    }
    
    
    
    
     public void sendQueryPatientProfile(int patientID) throws IOException,
            InvalidProtocolStateException {

        if (state != ConnectionState.ONLINE && state != ConnectionState.IN_CASE) {
            throw new InvalidProtocolStateException(
                    "Can not send QueryPatientProfile message before login!!!");
        }


		// <QueryPatientProfile#patientID>
        sendMsg("QueryPatientProfile#" + patientID);

    }
        
     
     
     public void sendUpdatePatientProfile(String height, String weight) throws IOException,
            InvalidProtocolStateException {

        if (state != ConnectionState.ONLINE) {
            throw new InvalidProtocolStateException(
                    "Can not send UpdatePatientProfile message when processing case or before login!!!");
        }

        setSpecificExpectedMsg(PatientServerMsg.UPDATE_PATIENT_PROFILE_ACK);

		// <UpdatePatientProfile#Name#Gender#Height#Weight#birthday>
        sendMsg("UpdatePatientProfile#" +  height
                + "#" + weight);

//        pc.piUpdate = new PatientInfo(height, weight);
    }

     
     
     
    protected void sendAddLabMeasurement(int id, String bloodtype) throws IOException {
		// <sendAddLabMeasurement#Height#Weight#BloodType>
		
		String patientID = String.valueOf(id);
		sendMsg("sendAddLabMeasurement#" + patientID + "#" + bloodtype);
	}
     
    
    
    
    protected void setSpecificExpectedMsg(PatientServerMsg msg) {

        emsg.clear();
        emsg.add(msg);
        expectingSpecificMsg = true;
    }
}