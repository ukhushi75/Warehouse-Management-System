package adminModule;

import javax.swing.*;
import java.awt.*;

/**
 * @author Judy Kalenga
 */

public class NewPage extends JFrame {
    
    //page that is presented when login succeeds or fails
    //future update: this page should be the client or server UI
    public NewPage() {
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Welcome");
        setSize(400, 200);
        setLocationRelativeTo(null);
    }

}
