package adminModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * @author Judy Kalenga
 */

public class LoginUI extends JFrame implements ActionListener {
    
    private static LoginUI instance = null;
    
    private LoginUI LoginUI() {
        return new LoginUI();
    }
    
    public static LoginUI getInstance() {
        if (instance == null) {
            instance = new LoginUI();
        }
        return instance;
    }
    
    //initialize button, panel, labels, and text fields
    JButton loginButton;
    JPanel loginPanel;
    JLabel userLabel, passLabel;
    final JTextField textField1, textField2;
    
    public LoginUI() {
        
        //label for username
        userLabel = new JLabel();
        userLabel.setText("Username: ");
        userLabel.setHorizontalAlignment(JLabel.CENTER);        
        textField1 = new JTextField(15); //text field to get username from the user
        
        //label for password
        passLabel = new JLabel();
        passLabel.setText("Password: ");
        passLabel.setHorizontalAlignment(JLabel.CENTER);
        textField2 = new JPasswordField(15); //text field to get password from user
        
        //button
        loginButton = new JButton("LOGIN");
        loginButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        //add form elements to panel
        loginPanel = new JPanel(new GridLayout(3, 1));
        loginPanel.add(userLabel);
        loginPanel.add(textField1);
        loginPanel.add(passLabel);
        loginPanel.add(textField2);
        loginPanel.add(loginButton);
        
        //set border to panel
        add(loginPanel, BorderLayout.CENTER);
        
        //perform action on button click
        loginButton.addActionListener(this);
        
        //title to login form
        setTitle("LOGIN FORM");
        
        //center the frame
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        JLabel welcomeLabel;
        
        //get user entered username and password
        String userValue = textField1.getText();
        String passValue = textField2.getText();
        
        // pass credentials to LoginServer
        ILogin loginServer = LoginServer.getInstance();
        
        try {
            loginServer.login(userValue, passValue);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
    }
    
    
    

}
