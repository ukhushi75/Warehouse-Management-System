package adminModule;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import frontEndModule.FrontEndServer;
import viewerModule.WarehouseServerUI;

/**
 * @author Judy Kalenga
 */

public class LoginServer implements ILogin {
    
    // proxy design pattern
    private AuthentificationServer authenticator = AuthentificationServer.getInstance();
    
    // singleton design pattern
    private static LoginServer instance = null;
    
    // reference to the front end server
    private FrontEndServer frontEnd = FrontEndServer.getInstance();
    
    // reference to the login ui
    private LoginUI loginUI = LoginUI.getInstance();
    
    // reference to the warehouse server ui
    private JFrame warehouseUI = WarehouseServerUI.getInstance();
    
    private LoginServer LoginServer() {
        return new LoginServer();
    }
    
    public static LoginServer getInstance() {
        if (instance == null) {
            instance = new LoginServer();
        }
        
        return instance;
    }
    
    @Override
    public void login(String username, String password) throws IOException {
        
        loginUI.setVisible(false); // close login form  
        
        JLabel welcomeLabel;
        
        // call backend service (authentification server) to do the work
        if (authenticator.authenticate(username, password)) {            
            //successful login (entered credentials matches stored credentials)
            
            try {
                // start the Java http Server
                frontEnd.startServer();
                System.out.println("Front end server is running...");
                
                // display the server UI
                warehouseUI.setSize(800, 600);
                warehouseUI.setVisible(true);
                
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        } else {
            //unsuccessful login (deny access)
            NewPage page = new NewPage();
            page.setVisible(true);
            welcomeLabel = new JLabel("Please enter valid username and password");
            page.getContentPane().add(welcomeLabel);
            welcomeLabel.setHorizontalAlignment(JLabel.CENTER);            
        }
    }
    
    public static void main(String[] args) {
        
        try {
            //create instance of LoginForm to access system
            LoginUI form = LoginUI.getInstance();
            form.setSize(400, 200);
            form.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

}
