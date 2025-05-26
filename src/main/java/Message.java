import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Message class for QuickChat application
 * Handles message creation, validation, and storage
 */
public class Message {
    private String messageID;
    private String recipientCell;
    private String messageText;
    private String messageHash;
    private int messageNumber;
    
    // Static lists to store messages
    private static List<Message> sentMessages = new ArrayList<>();
    private static List<Message> storedMessages = new ArrayList<>();
    private static List<Message> disregardedMessages = new ArrayList<>();
    
    // Constructor
    public Message() {
        this.messageID = generateMessageID();
    }
    
    // Constructor with parameters
    public Message(String recipientCell, String messageText, int messageNumber) {
        this.messageID = generateMessageID();
        this.recipientCell = recipientCell;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash();
    }
    
    /**
     * Generates a random 10-digit message ID
     * @return String representation of the message ID
     */
    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }
    
    /**
     * Validates message ID length
     * @param messageID the message ID to validate
     * @return true if valid (10 digits), false otherwise
     */
    public boolean checkMessageID(String messageID) {
        return messageID != null && messageID.length() == 10 && messageID.matches("\\d{10}");
    }
    
    /**
     * Validates recipient cell phone number
     * Reuses validation from Login class
     * @param cellNumber the cell number to validate
     * @return appropriate validation message
     */
    public String checkRecipientCell(String cellNumber) {
        Login login = new Login();
        boolean isValid = login.checkCellPhoneNumber(cellNumber);
        
        if (isValid) {
            return "Cell phone number successfully validated.";
        } else {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
    }
    
    /**
     * Validates message length
     * @param message the message to validate
     * @return appropriate validation message
     */
    public String checkMessageLength(String message) {
        if (message == null) {
            return "Message cannot be null";
        }
        
        if (message.length() <= 250) {
            return "Message ready to send";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds " + excess + " characters";
        }
    }
    
    /**
     * Creates message hash in format XX:Y:FIRSTLAST
     * @return the generated hash
     */
    public String createMessageHash() {
        if (messageID == null || messageText == null) {
            return "";
        }
        
        // Get first 2 digits of message ID
        String firstTwoDigits = messageID.substring(0, 2);
        
        // Get message number
        String msgNum = String.valueOf(messageNumber);
        
        // Get first and last words
        String trimmedText = messageText.trim();
        String firstWord = "";
        String lastWord = "";
        
        if (!trimmedText.isEmpty()) {
            String[] words = trimmedText.split("\\s+");
            firstWord = words.length > 0 ? words[0].toUpperCase() : "";
            lastWord = words.length > 1 ? words[words.length - 1].toUpperCase() : firstWord;
        }
        
        return firstTwoDigits + ":" + msgNum + ":" + firstWord + lastWord;
    }
    
    /**
     * Handles message actions (Send, Store, Disregard)
     * @param action the action to perform
     * @return appropriate response message
     */
    public String sentMessage(String action) {
        switch (action.toLowerCase()) {
            case "send":
                sentMessages.add(this);
                return "Message successfully sent";
            case "store":
                storedMessages.add(this);
                saveToJSON();
                return "Message successfully stored";
            case "disregard":
                disregardedMessages.add(this);
                return "Press 0 to delete message";
            default:
                return "Invalid action. Please choose Send, Store, or Disregard";
        }
    }
    
    /**
     * Saves stored messages to JSON file
     * AI-assisted method for JSON handling
     */
    private void saveToJSON() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter("data/stored_messages.json");
            gson.toJson(storedMessages, writer);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving to JSON: " + e.getMessage());
        }
    }
    
    /**
     * Displays message details using JOptionPane
     */
    public void displayMessage() {
        String details = String.format(
            "Message ID: %s\nHash: %s\nRecipient: %s\nMessage: %s",
            messageID, messageHash, recipientCell, messageText
        );
        JOptionPane.showMessageDialog(null, details, "Message Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Prints all sent messages
     */
    public static void printMessages() {
        System.out.println("\n=== SENT MESSAGES ===");
        for (int i = 0; i < sentMessages.size(); i++) {
            Message msg = sentMessages.get(i);
            System.out.printf("Message %d:\n", i + 1);
            System.out.printf("  Hash: %s\n", msg.messageHash);
            System.out.printf("  Recipient: %s\n", msg.recipientCell);
            System.out.printf("  Message: %s\n\n", msg.messageText);
        }
    }
    
    /**
     * Returns total number of sent messages
     * @return total count of sent messages
     */
    public static int returnTotalMessages() {
        return sentMessages.size();
    }
    
    /**
     * Main application menu
     */
    public static void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        System.out.println("Welcome to QuickChat");
        
        while (running) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recent messages (Coming Soon)");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    handleSendMessages(scanner);
                    break;
                case 2:
                    System.out.println("Feature coming soon!");
                    break;
                case 3:
                    System.out.println("Thank you for using QuickChat!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
    
    /**
     * Handles the send messages workflow
     */
    private static void handleSendMessages(Scanner scanner) {
        System.out.print("How many messages would you like to send? ");
        int numMessages = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        for (int i = 0; i < numMessages; i++) {
            System.out.printf("\n--- Message %d ---\n", i + 1);
            
            // Get recipient
            String recipient;
            while (true) {
                System.out.print("Enter recipient cell number: ");
                recipient = scanner.nextLine();
                Message tempMsg = new Message();
                String validation = tempMsg.checkRecipientCell(recipient);
                if (validation.contains("successfully")) {
                    break;
                } else {
                    System.out.println(validation);
                }
            }
            
            // Get message text
            String messageText;
            while (true) {
                System.out.print("Enter your message: ");
                messageText = scanner.nextLine();
                Message tempMsg = new Message();
                String validation = tempMsg.checkMessageLength(messageText);
                if (validation.equals("Message ready to send")) {
                    break;
                } else {
                    System.out.println(validation);
                }
            }
            
            // Create message
            Message message = new Message(recipient, messageText, i);
            
            // Show message details
            message.displayMessage();
            
            // Get action
            System.out.print("What would you like to do? (Send/Store/Disregard): ");
            String action = scanner.nextLine();
            String result = message.sentMessage(action);
            System.out.println(result);
        }
    }
    
    // Getters and setters
    public String getMessageID() { return messageID; }
    public String getRecipientCell() { return recipientCell; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public int getMessageNumber() { return messageNumber; }
    
    public void setRecipientCell(String recipientCell) { this.recipientCell = recipientCell; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    public void setMessageNumber(int messageNumber) { this.messageNumber = messageNumber; }
    
    // Static getters for testing
    public static List<Message> getSentMessages() { return sentMessages; }
    public static List<Message> getStoredMessages() { return storedMessages; }
    public static List<Message> getDisregardedMessages() { return disregardedMessages; }
    
    // Method to clear static lists (for testing)
    public static void clearAllMessages() {
        sentMessages.clear();
        storedMessages.clear();
        disregardedMessages.clear();
    }
} 