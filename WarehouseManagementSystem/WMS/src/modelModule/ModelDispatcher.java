package modelModule;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import orderStatuses.Approved;
import orderStatuses.Cancelled;
import orderStatuses.OrderStatus;
import orderStatuses.Pending;
import orderStatuses.Rejected;
import productDatabaseModule.ProductDB;
import viewerModule.BarChartViewer;
import viewerModule.TextViewer;
import viewerModule.Viewer;

/**
 * @author Judy Kalenga
 */

public class ModelDispatcher implements IModelManagement {

    private ProductDB productDB = ProductDB.getInstance();

    // initialize list of observers with the observers themselves 
    private List<Viewer> viewers = new ArrayList<Viewer>(Arrays.asList(BarChartViewer.getInstance(), TextViewer.getInstance()));

    // singleton design pattern
    private static ModelDispatcher instance = null;

    private ModelDispatcher ModelDispatcher() {
        return new ModelDispatcher();
    }

    public static ModelDispatcher getInstance() {
        if (instance == null) {
            instance = new ModelDispatcher();
        }
        return instance;
    }

    @Override
    public Order createOrder(String productName, int requestedQuantity, Date timeStamp) {
        return new Order(productName, requestedQuantity, timeStamp); // create order object to represent the order information
    }

    @Override
    public OrderStatus fulfillOrder(Order anOrder) throws IOException {
        
        int remainingQuantity = 0;

        // step 1: go to database to confirm product id is valid and create product object to represent product information
        Product aProduct = this.getProductInfo(anOrder.getProductName());
        
        if (aProduct != null) {
            anOrder.setProductID(aProduct.getProductID());
        }

        // step 2: compare quantity fields
        // factory design pattern
        if (aProduct == null) {
            // if not able to find product in database, cancel order
            return new Cancelled();
            
        } else if (anOrder.getRequestedQuantity() > aProduct.getMaxStockQuantity()) {
            // If the ordered quantity exceeds the target max stock quantity for this product in the warehouse, then the server will reject the order, and a response 
            // message from the server will be displayed in the client that the “Order exceeds the max quantity set for this product and cannot be processed”.
            return new Rejected();
            
        } else if (anOrder.getRequestedQuantity() > aProduct.getAvailableQuantity()) {
            // If the quantity exceeds the available quantity in the warehouse for this product at the time the order is processed by the server, a message is displayed
            // in the server UI that the “Order for Product X Quantity Y is pending – order exceeds available quantity” (The client is not notified at this point.)
            return new Pending();
            
        } else if (anOrder.getRequestedQuantity() <= aProduct.getAvailableQuantity()) {
            // step 3: if order can be fulfilled, update database with remaining quantity
            remainingQuantity = aProduct.getAvailableQuantity() - anOrder.getRequestedQuantity();
            this.updateDatabase(aProduct.getProductID(), remainingQuantity);
        }

        // step 4: update viewers
        this.notifyViewers();

        // Once the order is fully processed then a response message from the server is displayed in the client that 
        // the “Order is finalized for Product X and Quantity Y with total price Z”
        return new Approved();

    }
    
    public Product getProductInfo(String productName) throws IOException {

        // obtaining input bytes from file  
        FileInputStream fileInputStream = new FileInputStream(productDB.getDatabase());

        // creating workbook instance that refers to .xlsx file
        Workbook workbook = new XSSFWorkbook(fileInputStream);

        // creating a Sheet object to retrieve the object  
        Sheet sheet = workbook.getSheetAt(0);

        // iterating over excel file 
        Iterator<Row> iterator = sheet.iterator();
        
        Product aProduct = null;

        // iterate through rows (each row represents a product)
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            
            // second column is product name
            Cell productNameCell = currentRow.getCell(1);

            String storedProductName = productNameCell.getStringCellValue();

            // if product id is in the database, create product object to hold all of the product details
            if (productName.equals(storedProductName)) {
                
                // first column is product id
                Cell productIDCell = currentRow.getCell(0);
                // third column is product available quantity
                Cell availableQuantityCell = currentRow.getCell(2);
                // fourth column is product max stock quantity
                Cell maxStockQuantityCell = currentRow.getCell(3);
                // fifth column is product unit price
                Cell productUnitPriceCell = currentRow.getCell(4);
                // sixth column is product restock schedule
                Cell productRestockScheduleCell = currentRow.getCell(5);

                // create product object with information retrieved from database 
                aProduct = new Product(productIDCell.getStringCellValue(), productName,(int) availableQuantityCell.getNumericCellValue(), (int) maxStockQuantityCell.getNumericCellValue(), productUnitPriceCell.getNumericCellValue(), (int) productRestockScheduleCell.getNumericCellValue());
                
                workbook.close();
                
            }
        }
        return aProduct;
    }
    
    public List<Product> getProducts() throws IOException {
        
        List<Product> productList = new ArrayList<Product>();

        // obtaining input bytes from file  
        FileInputStream fileInputStream = new FileInputStream(productDB.getDatabase());

        // creating workbook instance that refers to .xlsx file
        Workbook workbook = new XSSFWorkbook(fileInputStream);

        // creating a Sheet object to retrieve the object  
        Sheet sheet = workbook.getSheetAt(0);

        // iterating over excel file 
        Iterator<Row> iterator = sheet.iterator();
        
        Product aProduct = null;

        // iterate through rows (each row represents a product)
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            
            // first column is product id
            Cell productIDCell = currentRow.getCell(0);
            // second column is product name
            Cell productNameCell = currentRow.getCell(1);
            // third column is product available quantity
            Cell availableQuantityCell = currentRow.getCell(2);
            // fourth column is product max stock quantity
            Cell maxStockQuantityCell = currentRow.getCell(3);
            // fifth column is product unit price
            Cell productUnitPriceCell = currentRow.getCell(4);
            // sixth column is product restock schedule
            Cell productRestockScheduleCell = currentRow.getCell(5);
            
            String id = productIDCell.getStringCellValue();
            
            String name = productNameCell.getStringCellValue();
            
            // skip the first row which solely contains column headers 
            if (availableQuantityCell.getCellType() == CellType.STRING) {
                continue;
            }
            
            int available = (int) availableQuantityCell.getNumericCellValue();
            
            int max = (int) maxStockQuantityCell.getNumericCellValue();
            
            double price = productUnitPriceCell.getNumericCellValue();
            
            int restock = (int) productRestockScheduleCell.getNumericCellValue();
            
            aProduct = new Product(id, name, available, max, price, restock);
           
            productList.add(aProduct);               
             
            }
        
        workbook.close();
        return productList;
    }
       

    public void updateDatabase(String productID, int newQuantity) throws IOException {
        System.out.println("Product " + productID + "'s new quantity: " + newQuantity);
        System.out.println("Updating product database...");
        
        FileInputStream fileInputStream = new FileInputStream(productDB.getDatabase());
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        while (iterator.hasNext()) {
            Row currentRow = iterator.next();          
            Cell productIDCell = currentRow.getCell(0);
            String storedProductID = productIDCell.getStringCellValue();
            
            if (productID.equals(storedProductID)) {
                Cell availableQuantityCell = currentRow.getCell(2);
                // update available quantity to the new remaining quantity
                availableQuantityCell.setCellValue(newQuantity);
            }
        }            
        
        // save the changes made to excel file
        FileOutputStream fileOutputStream = new FileOutputStream(productDB.getDatabase());
        workbook.write(fileOutputStream);
        System.out.println("Product database updated successfully!");
        
        workbook.close();        
    }
    
    // observer design pattern
    public void notifyViewers() {
        for (Viewer view: viewers) {
            view.update();
            
            // in the future: notifyViewers() will take parameters and pass them to update()
            // and inside the update method, will call createCharts method and pass parameters to it in order modify the viewers
        }
    }
    
}
