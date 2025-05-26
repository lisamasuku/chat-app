/**
 * Main application class for QuickChat
 * Updated to use JOptionPane GUI interface for better user experience
 * Demonstrates the complete functionality of the messaging system
 */
public class QuickChatApp {
    
    public static void main(String[] args) {
        // Check if console mode is requested via command line argument
        if (args.length > 0 && args[0].equals("--console")) {
            System.out.println("Starting QuickChat in console mode...");
            // For console mode, we would need to implement a console version
            // For now, we'll just show a message
            System.out.println("Console mode not implemented in this version.");
            System.out.println("Please run without arguments for GUI mode.");
            return;
        }
        
        // Start the QuickChat application with GUI interface
        Message.showMainMenu();
    }
} 