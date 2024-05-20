package adminModule;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import credentialsDatabaseModule.AdminCredentialsDB; 

/**
 * @author Judy Kalenga
 */

public class AuthentificationServer implements IAuthenticate {
    
    // singleton
    private static AuthentificationServer instance = null;
    private AdminCredentialsDB credentialsDB = AdminCredentialsDB.getInstance();
    
    private AuthentificationServer AuthentificationServer() {
        return new AuthentificationServer();
    }
    
    public static AuthentificationServer getInstance() {
        if (instance == null) {
            instance = new AuthentificationServer();
        }
        return instance;
    }
    
    @Override
    public boolean authenticate(String username, String password) throws IOException {
        
        // obtaining input bytes from file  
        FileInputStream fileInputStream = new FileInputStream(credentialsDB.getAdminCredentialDB());
        
        // creating workbook instance that refers to .xlsx file
        Workbook workbook = new XSSFWorkbook(fileInputStream);

        // creating a Sheet object to retrieve the object  
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> iterator = sheet.iterator();

        // iterate through rows (each row represents an admin user)
         while (iterator.hasNext()) {
             Row currentRow = iterator.next();

             // first column is the usernames
             Cell usernameCell = currentRow.getCell(0);
             // second column is the passwords
             Cell passwordCell = currentRow.getCell(1);

             String storedUsername = usernameCell.getStringCellValue();
             String storedPassword = passwordCell.getStringCellValue();
                
             workbook.close();

             // check if the provided username and password match the stored credentials
             if (username.equals(storedUsername) && password.equals(storedPassword)) {
                 return true; // Authentication successful
             }
         }
         
         return false; // Authentication failed
         
    }

}
