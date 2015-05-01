package edu.asu.cse360.team25.client.nurse;

import java.io.IOException;

import edu.asu.cse360.team25.client.Client;
import edu.asu.cse360.team25.client.nurse.NurseMainFrame;
import edu.asu.cse360.team25.client.nurse.NurseServerConnection;
import edu.asu.cse360.team25.client.Client;
import edu.asu.cse360.team25.protocol.CaseInfo;
import edu.asu.cse360.team25.protocol.PatientInfo;
import edu.asu.cse360.team25.protocol.exception.InvalidProtocolStateException;
import edu.asu.cse360.team25.protocol.exception.ProtocolErrorException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class NurseClient extends Client {

    protected NurseMainFrame nmf;
    
    protected NurseServerConnection nsc;
        
    protected int state = 0; // 0 - login, 1 - signup, 2 - main

    protected int whichButtonOnLogin = 0; // 0 - cancel, 1 - login, 2 - signup

    protected int whichButtonOnSignUp = 0; // 0 - cancel, 1 - signup
    
    // for protocol
    protected boolean loginOK = false;
    protected boolean signupOK = false;

    // model
    // for login
    protected int nurseID = -1; // also used for register
    protected String password = null;

    protected String usernameSU;
    protected String passwordSU; 
    
    protected PatientInfo pi;
    protected PatientInfo piUpdate;

    
    public NurseClient() {
        super();
        
        nmf = new NurseMainFrame(this);
        
        //nsc = new NurseServerConnection(this);
        
        nmf.nsc = nsc;
//        nsc.nmf = nmf;
        
        nmf.addWindowListener(new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent arg0) {
            
            //System.out.println("window closing handler called!!!");
         
            try {
                nsc.disconnect();
            } catch (IOException ex) {
                Logger.getLogger(NurseClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

    });

    }

    public NurseServerConnection getNsc() {
        return nsc;
    }

    protected void doLogin() throws IOException, ProtocolErrorException, InterruptedException {
        // Login
        loginOK = false;
        whichButtonOnLogin = 0;

        NurseLoginDialog ld = new NurseLoginDialog(null, this, nurseID, password);
        ld.setLocationRelativeTo(null);
        ld.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ld.setVisible(true);

        if(whichButtonOnLogin == 1) {
        
//            nsc.sendLogin(nurseID, password);
//            // wait for login ack
//
//            synchronized (this) {
//                wait();
//            }
//            
//            if (!loginOK) {
//                JOptionPane.showMessageDialog(null, "User ID and password does not match!",
//                        "Error", JOptionPane.ERROR_MESSAGE);
//            } else {
//                state = 2; // goto main
//            }
            state = 2;
            
        } else if(whichButtonOnLogin == 0) {
            
            state = 3; // exit application
            
        } else if(whichButtonOnLogin == 2) {
            
            state = 1; // go to signup
        }
       

    }

    protected void doSignUp() throws IOException, InterruptedException, ProtocolErrorException {

       System.out.println("NO SIGNUP FOR NURSES!!!!");

    }

    protected void doMain() throws IOException, InterruptedException, InvalidProtocolStateException {
        
                // Query profile
//        nsc.sendQueryPatientProfile(0);
//
//        synchronized (this) {
//                wait();
//            }
//        
//    nsc.sendQueryCaseList();
//        
//        synchronized (this) {
//                wait();
//            }
        
        nmf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nmf.setVisible(true);
        nmf.showPatient();




//        nsc.waitForReceivingThread();
        
        state = 3;
        
    }
    
    public void start() throws InterruptedException, IOException, ProtocolErrorException {

//        nsc.connect();
//        nsc.startReceiving();

        while(true) {
            
            if(state == 0) {
                doLogin();
            } else if(state == 1) {
                doSignUp();
            } else if(state == 2 ) {
                doMain();
            } else {
                break;
            }
            
            
        }


    }

    public void stop() throws IOException {

//        nsc.disconnect();
    }

    public void setLoginInfo(int patientID, String password, boolean login) {

        this.nurseID = nurseID;
        this.password = password;
    }

    public void setSignUpInfo(String username, String password, String gender, String height, String weight, String birthday) {
        
				usernameSU = username;
				passwordSU = password;

    }


    public void setPatientInfo(PatientInfo pi) {

        this.pi = pi;
    }

    public static void main(String[] argv) {

        /* Set the Aqua look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Aqua".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
//            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NurseMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NurseMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NurseMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NurseMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        try {

            NurseClient nc = new NurseClient();

            nc.start();

            nc.stop();

//            nc.nsc.waitForReceivingThread();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ProtocolErrorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
