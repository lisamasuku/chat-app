import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 * Message class for QuickChat application
 * Handles message creation, validation, and storage
 * Phase 3: Enhanced with data management and reporting features
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
    
    // Phase 3: Additional arrays for data management
    private static List<String> messageHashArray = new ArrayList<>();
    private static List<String> messageIDArray = new ArrayList<>();
    
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
                populateArrays(); // Update arrays when message is sent
                return "Message successfully sent";
            case "store":
                storedMessages.add(this);
                saveToJSON();
                populateArrays(); // Update arrays when message is stored
                return "Message successfully stored";
            case "disregard":
                disregardedMessages.add(this);
                populateArrays(); // Update arrays when message is disregarded
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
    
    // ========== PHASE 3: DATA MANAGEMENT & REPORTING FEATURES ==========
    
    /**
     * Populates arrays with message data from all message lists
     * Task 3.3: Implement array population methods
     */
    public static void populateArrays() {
        messageHashArray.clear();
        messageIDArray.clear();
        
        // Add from sent messages
        for (Message msg : sentMessages) {
            messageHashArray.add(msg.messageHash);
            messageIDArray.add(msg.messageID);
        }
        
        // Add from stored messages
        for (Message msg : storedMessages) {
            messageHashArray.add(msg.messageHash);
            messageIDArray.add(msg.messageID);
        }
        
        // Add from disregarded messages
        for (Message msg : disregardedMessages) {
            messageHashArray.add(msg.messageHash);
            messageIDArray.add(msg.messageID);
        }
    }
    
    /**
     * Searches for a message by Message ID
     * Task 3.4: Implement search functionality
     * @param messageID the ID to search for
     * @return the message text if found, null otherwise
     */
    public static String searchByMessageID(String messageID) {
        // Search in sent messages
        for (Message msg : sentMessages) {
            if (msg.messageID.equals(messageID)) {
                return msg.messageText;
            }
        }
        
        // Search in stored messages
        for (Message msg : storedMessages) {
            if (msg.messageID.equals(messageID)) {
                return msg.messageText;
            }
        }
        
        // Search in disregarded messages
        for (Message msg : disregardedMessages) {
            if (msg.messageID.equals(messageID)) {
                return msg.messageText;
            }
        }
        
        return null; // Not found
    }
    
    /**
     * Searches for messages by recipient cell number
     * Task 3.4: Implement search functionality
     * @param recipient the recipient to search for
     * @return list of messages for that recipient
     */
    public static List<Message> searchByRecipient(String recipient) {
        List<Message> results = new ArrayList<>();
        
        // Search in sent messages
        for (Message msg : sentMessages) {
            if (msg.recipientCell.equals(recipient)) {
                results.add(msg);
            }
        }
        
        // Search in stored messages
        for (Message msg : storedMessages) {
            if (msg.recipientCell.equals(recipient)) {
                results.add(msg);
            }
        }
        
        // Search in disregarded messages
        for (Message msg : disregardedMessages) {
            if (msg.recipientCell.equals(recipient)) {
                results.add(msg);
            }
        }
        
        return results;
    }
    
    /**
     * Finds the longest message across all message lists
     * Task 3.4: Implement search functionality
     * @return the longest message text
     */
    public static String findLongestMessage() {
        String longestMessage = "";
        int maxLength = 0;
        
        // Check sent messages
        for (Message msg : sentMessages) {
            if (msg.messageText.length() > maxLength) {
                maxLength = msg.messageText.length();
                longestMessage = msg.messageText;
            }
        }
        
        // Check stored messages
        for (Message msg : storedMessages) {
            if (msg.messageText.length() > maxLength) {
                maxLength = msg.messageText.length();
                longestMessage = msg.messageText;
            }
        }
        
        // Check disregarded messages
        for (Message msg : disregardedMessages) {
            if (msg.messageText.length() > maxLength) {
                maxLength = msg.messageText.length();
                longestMessage = msg.messageText;
            }
        }
        
        return longestMessage;
    }
    
    /**
     * Deletes a message by its hash
     * Task 3.5: Implement message deletion by hash
     * @param hash the hash of the message to delete
     * @return true if message was found and deleted, false otherwise
     */
    public static boolean deleteMessageByHash(String hash) {
        // Try to remove from sent messages
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).messageHash.equals(hash)) {
                sentMessages.remove(i);
                populateArrays(); // Update arrays
                return true;
            }
        }
        
        // Try to remove from stored messages
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).messageHash.equals(hash)) {
                storedMessages.remove(i);
                populateArrays(); // Update arrays
                return true;
            }
        }
        
        // Try to remove from disregarded messages
        for (int i = 0; i < disregardedMessages.size(); i++) {
            if (disregardedMessages.get(i).messageHash.equals(hash)) {
                disregardedMessages.remove(i);
                populateArrays(); // Update arrays
                return true;
            }
        }
        
        return false; // Message not found
    }
    
    /**
     * Loads stored messages from JSON file
     * Task 3.7: Integrate JSON reading (AI-assisted)
     * AI Attribution: This method uses Gson library for JSON deserialization
     */
    public static void loadFromJSON() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("data/stored_messages.json");
            Type listType = new TypeToken<List<Message>>(){}.getType();
            List<Message> loadedMessages = gson.fromJson(reader, listType);
            reader.close();
            
            if (loadedMessages != null) {
                storedMessages.clear();
                storedMessages.addAll(loadedMessages);
                populateArrays(); // Update arrays after loading
            }
        } catch (IOException e) {
            System.err.println("Error loading from JSON: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("JSON file not found or empty, starting with empty stored messages");
        }
    }
    
    /**
     * Displays comprehensive report of all sent messages
     * Task 3.6: Implement comprehensive reporting
     */
    public static void displayComprehensiveReport() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         QUICKCHAT COMPREHENSIVE REPORT");
        System.out.println("=".repeat(50));
        
        System.out.println("\n📊 SUMMARY STATISTICS:");
        System.out.println("   Total Sent Messages: " + sentMessages.size());
        System.out.println("   Total Stored Messages: " + storedMessages.size());
        System.out.println("   Total Disregarded Messages: " + disregardedMessages.size());
        System.out.println("   Total Messages: " + (sentMessages.size() + storedMessages.size() + disregardedMessages.size()));
        
        if (!sentMessages.isEmpty()) {
            System.out.println("\n📤 SENT MESSAGES DETAILS:");
            System.out.println("-".repeat(50));
            for (int i = 0; i < sentMessages.size(); i++) {
                Message msg = sentMessages.get(i);
                System.out.printf("Message %d:\n", i + 1);
                System.out.printf("   Hash: %s\n", msg.messageHash);
                System.out.printf("   Recipient: %s\n", msg.recipientCell);
                System.out.printf("   Message: %s\n", msg.messageText);
                System.out.printf("   Message ID: %s\n\n", msg.messageID);
            }
        }
        
        if (!storedMessages.isEmpty()) {
            System.out.println("💾 STORED MESSAGES:");
            System.out.println("-".repeat(50));
            for (int i = 0; i < storedMessages.size(); i++) {
                Message msg = storedMessages.get(i);
                System.out.printf("Stored Message %d:\n", i + 1);
                System.out.printf("   Hash: %s\n", msg.messageHash);
                System.out.printf("   Recipient: %s\n", msg.recipientCell);
                System.out.printf("   Message: %s\n\n", msg.messageText);
            }
        }
        
        if (!disregardedMessages.isEmpty()) {
            System.out.println("🗑️  DISREGARDED MESSAGES:");
            System.out.println("-".repeat(50));
            for (int i = 0; i < disregardedMessages.size(); i++) {
                Message msg = disregardedMessages.get(i);
                System.out.printf("Disregarded Message %d:\n", i + 1);
                System.out.printf("   Hash: %s\n", msg.messageHash);
                System.out.printf("   Recipient: %s\n", msg.recipientCell);
                System.out.printf("   Message: %s\n\n", msg.messageText);
            }
        }
        
        // Show longest message
        String longest = findLongestMessage();
        if (!longest.isEmpty()) {
            System.out.println("📏 LONGEST MESSAGE:");
            System.out.println("-".repeat(50));
            System.out.println("   \"" + longest + "\"");
            System.out.println("   Length: " + longest.length() + " characters\n");
        }
        
        System.out.println("=".repeat(50));
    }
    
    /**
     * Interactive search menu for testing Phase 3 features
     */
    public static void showSearchMenu(Scanner scanner) {
        boolean searching = true;
        
        while (searching) {
            System.out.println("\n🔍 SEARCH & MANAGEMENT MENU:");
            System.out.println("1. Search by Message ID");
            System.out.println("2. Search by Recipient");
            System.out.println("3. Find Longest Message");
            System.out.println("4. Delete Message by Hash");
            System.out.println("5. Show Comprehensive Report");
            System.out.println("6. Load Messages from JSON");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter Message ID to search: ");
                    String messageID = scanner.nextLine();
                    String result = searchByMessageID(messageID);
                    if (result != null) {
                        System.out.println("Found message: \"" + result + "\"");
                    } else {
                        System.out.println("Message not found.");
                    }
                    break;
                    
                case 2:
                    System.out.print("Enter recipient cell number: ");
                    String recipient = scanner.nextLine();
                    List<Message> messages = searchByRecipient(recipient);
                    if (!messages.isEmpty()) {
                        System.out.println("Found " + messages.size() + " message(s) for " + recipient + ":");
                        for (int i = 0; i < messages.size(); i++) {
                            System.out.printf("  %d. %s\n", i + 1, messages.get(i).messageText);
                        }
                    } else {
                        System.out.println("No messages found for that recipient.");
                    }
                    break;
                    
                case 3:
                    String longest = findLongestMessage();
                    if (!longest.isEmpty()) {
                        System.out.println("Longest message: \"" + longest + "\"");
                        System.out.println("Length: " + longest.length() + " characters");
                    } else {
                        System.out.println("No messages found.");
                    }
                    break;
                    
                case 4:
                    System.out.print("Enter message hash to delete: ");
                    String hash = scanner.nextLine();
                    boolean deleted = deleteMessageByHash(hash);
                    if (deleted) {
                        System.out.println("Message successfully deleted.");
                    } else {
                        System.out.println("Message with that hash not found.");
                    }
                    break;
                    
                case 5:
                    displayComprehensiveReport();
                    break;
                    
                case 6:
                    loadFromJSON();
                    System.out.println("Messages loaded from JSON file.");
                    break;
                    
                case 7:
                    searching = false;
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // ========== END PHASE 3 FEATURES ==========
    
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
            System.out.println("2. Search & Management (Phase 3 Features)");
            System.out.println("3. Show Comprehensive Report");
            System.out.println("4. Quit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    handleSendMessages(scanner);
                    break;
                case 2:
                    showSearchMenu(scanner);
                    break;
                case 3:
                    displayComprehensiveReport();
                    break;
                case 4:
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
    
    // Phase 3: Getters for new arrays
    public static List<String> getMessageHashArray() { return messageHashArray; }
    public static List<String> getMessageIDArray() { return messageIDArray; }
    
    // Method to clear static lists (for testing)
    public static void clearAllMessages() {
        sentMessages.clear();
        storedMessages.clear();
        disregardedMessages.clear();
        messageHashArray.clear();
        messageIDArray.clear();
    }
} 