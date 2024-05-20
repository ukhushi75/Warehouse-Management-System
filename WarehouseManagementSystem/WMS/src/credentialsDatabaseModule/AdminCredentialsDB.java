package credentialsDatabaseModule;

/**
 * @author Judy Kalenga
 */

public class AdminCredentialsDB {
    
    private static final String DATABASE = "C:/Users/Owner/Downloads/credentials_database.xlsx";
    
    // singleton
    private static AdminCredentialsDB instance = null;
    
    private AdminCredentialsDB AdminCredentialsDB() {
        return new AdminCredentialsDB();
    }
    
    public static AdminCredentialsDB getInstance() {
        if (instance == null) {
            instance = new AdminCredentialsDB();
        }
        return instance;
    }
    
    public String getAdminCredentialDB() {
        return DATABASE;
    }

}
