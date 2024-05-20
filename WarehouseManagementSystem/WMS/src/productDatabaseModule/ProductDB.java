package productDatabaseModule;

/**
 * @author Judy Kalenga
 */

public class ProductDB {
    
    private static final String DATABASE = "C:/Users/Owner/Downloads/product_database.xlsx"; 
    
    // singleton design pattern
    private static ProductDB instance = null;
    
    private ProductDB ProductDB() {
        return new ProductDB();
    }
    
    public static ProductDB getInstance() {
        if (instance == null) {
            instance = new ProductDB();
        }
        return instance;
    }
    
    public String getDatabase() {
        return DATABASE;
    }

}
