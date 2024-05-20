package viewerModule;

import java.util.HashMap;

/**
 * @author Judy Kalenga
 */

public class BarChartViewer extends Viewer {
    
    // singleton
    private static BarChartViewer instance = null;
    
    private BarChartViewer BarChartViewer() {
        return new BarChartViewer();
    }
    
    public static BarChartViewer getInstance() {
        if (instance == null) {
            instance = new BarChartViewer();
        }
        return instance;
    }

    @Override
    public void update() {
        System.out.println("BarChartViewer has been updated.");
        
        // Trigger UI refresh
        WarehouseServerUI.getInstance().updateDataAndRefreshUI();
    }

}
