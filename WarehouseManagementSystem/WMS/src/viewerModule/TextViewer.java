package viewerModule;

/**
 * @author Judy Kalenga
 */

public class TextViewer extends Viewer {
    
    // singleton design pattern
    private static TextViewer instance = null;
    
    private TextViewer TextViewer() {
        return new TextViewer();
    }
    
    public static TextViewer getInstance() {
        if (instance == null) {
            instance = new TextViewer();
        }
        return instance;
    }

    @Override
    public void update() {
        System.out.println("TextViewer has been updated.");
        
        // Trigger UI refresh
        WarehouseServerUI.getInstance().updateDataAndRefreshUI();
    }

}
