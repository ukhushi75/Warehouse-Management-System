package viewerModule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import modelModule.AvailableProductList;
import modelModule.LastOrder;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jfree.chart.ChartPanel;

public class WarehouseServerUI extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static Map<String, Integer> productData;
    private static LastOrder theLastOrder;

    private static WarehouseServerUI instance;

    public static WarehouseServerUI getInstance() {
        if (instance == null)
            instance = new WarehouseServerUI();

        return instance;
    }
    
    private JPanel east = new JPanel();
    private JPanel west = new JPanel();

    private WarehouseServerUI() {
        // Set window title
        super("Warehouse Server UI");

        productData = AvailableProductList.getInstance().findAvailableProductsAndQuantities();
        theLastOrder = LastOrder.getInstance().findLastOrder();


        // Set charts region
        west.setLayout(new GridLayout(2, 0));
        east.setLayout(new GridLayout(2, 0));

        getContentPane().add(west, BorderLayout.WEST);
        getContentPane().add(east, BorderLayout.EAST);

        createCharts(west, east);

    }

    private void createCharts(JPanel west, JPanel east) {

        createBar(west);
        createReport(east);

    }

    private void createReport(JPanel west) {
        JTextArea report = new JTextArea();
        report.setEditable(false);
        report.setPreferredSize(new Dimension(400, 300));
        report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        report.setBackground(Color.white);
        String reportMessage1, reportMessage2;

        reportMessage1 = "Last Order\n" + "==========================\n" + "\t";
        reportMessage1 = reportMessage1 + "Product: " + theLastOrder.getProductName() + "\n" 
                         + "\tQuantity:" +  theLastOrder.getQuantity() + "\n"
                         + "\tTimeStamp:" +  theLastOrder.getDate() + "\n";

        reportMessage2 = "Current Product Quantity in Warehouse\n" + "==============================\n";

        for (Map.Entry<String, Integer> entry : productData.entrySet()) {
            //System.out.println("IN LOOP " + entry.getKey() + " " + entry.getValue());
            reportMessage2 = reportMessage2 + entry.getKey();
            reportMessage2 = reportMessage2 + "\n \t Quantity ==> " + entry.getValue() + "\n";

        }


    

        report.setText(reportMessage1 + reportMessage2);
        JScrollPane outputScrollPane = new JScrollPane(report);
        west.add(outputScrollPane);
    }

    private void createBar(JPanel west) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : productData.entrySet()) {
            dataset.setValue(entry.getValue(), entry.getKey(), "");

        }


        CategoryPlot plot = new CategoryPlot();
        BarRenderer barrenderer1 = new BarRenderer();
        BarRenderer barrenderer2 = new BarRenderer();

        plot.setDataset(0, dataset);
        plot.setRenderer(0, barrenderer1);
        CategoryAxis domainAxis = new CategoryAxis("");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis(""));

        plot.setRenderer(1, barrenderer2);

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis

        JFreeChart barChart = new JFreeChart("Warehouse Product Monitor System",
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);


        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        west.add(chartPanel);
    }
    
    public void updateDataAndRefreshUI() {
        // Fetch new data
       productData = AvailableProductList.getInstance().findAvailableProductsAndQuantities();
       theLastOrder = LastOrder.getInstance().findLastOrder();

       // Refresh UI components
       west.removeAll();
       east.removeAll();

       createCharts(west, east);

       west.revalidate();
       west.repaint();
       east.revalidate();
       east.repaint();
   }
    
    public static void main(String[] args) {

        JFrame frame = WarehouseServerUI.getInstance();
        frame.setSize(800, 600);
        // frame.pack();
        frame.setVisible(true);
    }

}
