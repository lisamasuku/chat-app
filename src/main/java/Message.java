import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Dimension;
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
     * 
     * The following string manipulation logic for hash generation was developed 
     * with assistance from Claude AI
     * Anthropic. (2024). Claude (Version 3.5) [Large language model].
     * https://claude.ai/
     * Prompt: "how do i make a hash like XX:Y:FIRSTLAST from message id and text? 
     * need to split words and get first/last ones"
     * 
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
     * 
     * The following JSON serialization method was developed with assistance from Claude AI
     * Anthropic. (2024). Claude (Version 3.5) [Large language model].
     * https://claude.ai/
     * Prompt: "gson is confusing me, how do i save my java objects to a json file? 
     * want it to look nice and handle errors properly"
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
     * 
     * The following JSON deserialization method was developed with assistance from Claude AI
     * Anthropic. (2024). Claude (Version 3.5) [Large language model].
     * https://claude.ai/
     * Prompt: "Create a method to read JSON file back into Java objects using Gson library 
     * with TypeToken for generic lists and proper error handling"
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
     * Displays comprehensive report using JOptionPane with scrollable text area
     */
    public static void displayComprehensiveReportGUI() {
        StringBuilder report = new StringBuilder();
        
        report.append("QUICKCHAT COMPREHENSIVE REPORT\n");
        report.append("=".repeat(50)).append("\n\n");
        
        report.append("📊 SUMMARY STATISTICS:\n");
        report.append("   Total Sent Messages: ").append(sentMessages.size()).append("\n");
        report.append("   Total Stored Messages: ").append(storedMessages.size()).append("\n");
        report.append("   Total Disregarded Messages: ").append(disregardedMessages.size()).append("\n");
        report.append("   Total Messages: ").append(sentMessages.size() + storedMessages.size() + disregardedMessages.size()).append("\n\n");
        
        if (!sentMessages.isEmpty()) {
            report.append("📤 SENT MESSAGES DETAILS:\n");
            report.append("-".repeat(50)).append("\n");
            for (int i = 0; i < sentMessages.size(); i++) {
                Message msg = sentMessages.get(i);
                report.append("Message ").append(i + 1).append(":\n");
                report.append("   Hash: ").append(msg.messageHash).append("\n");
                report.append("   Recipient: ").append(msg.recipientCell).append("\n");
                report.append("   Message: ").append(msg.messageText).append("\n");
                report.append("   Message ID: ").append(msg.messageID).append("\n\n");
            }
        }
        
        if (!storedMessages.isEmpty()) {
            report.append("💾 STORED MESSAGES:\n");
            report.append("-".repeat(50)).append("\n");
            for (int i = 0; i < storedMessages.size(); i++) {
                Message msg = storedMessages.get(i);
                report.append("Stored Message ").append(i + 1).append(":\n");
                report.append("   Hash: ").append(msg.messageHash).append("\n");
                report.append("   Recipient: ").append(msg.recipientCell).append("\n");
                report.append("   Message: ").append(msg.messageText).append("\n\n");
            }
        }
        
        if (!disregardedMessages.isEmpty()) {
            report.append("🗑️  DISREGARDED MESSAGES:\n");
            report.append("-".repeat(50)).append("\n");
            for (int i = 0; i < disregardedMessages.size(); i++) {
                Message msg = disregardedMessages.get(i);
                report.append("Disregarded Message ").append(i + 1).append(":\n");
                report.append("   Hash: ").append(msg.messageHash).append("\n");
                report.append("   Recipient: ").append(msg.recipientCell).append("\n");
                report.append("   Message: ").append(msg.messageText).append("\n\n");
            }
        }
        
        // Show longest message
        String longest = findLongestMessage();
        if (!longest.isEmpty()) {
            report.append("📏 LONGEST MESSAGE:\n");
            report.append("-".repeat(50)).append("\n");
            report.append("   \"").append(longest).append("\"\n");
            report.append("   Length: ").append(longest.length()).append(" characters\n\n");
        }
        
        report.append("=".repeat(50));
        
        // Create a scrollable text area for the report
        javax.swing.JTextArea textArea = new javax.swing.JTextArea(report.toString());
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 12));
        
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(600, 400));
        
        JOptionPane.showMessageDialog(null,
            scrollPane,
            "QuickChat - Comprehensive Report",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Displays comprehensive report of all sent messages (Console version for testing)
     * Task 3.6: Implement comprehensive reporting
     * 
     * The following report formatting and data aggregation logic was developed 
     * with assistance from Claude AI
     * Anthropic. (2024). Claude (Version 3.5) [Large language model].
     * https://claude.ai/
     * Prompt: "need to make a nice looking report that shows all my messages with 
     * stats and stuff. how do i format it so it looks professional?"
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
     * Shows search and management menu using JOptionPane
     */
    public static void showSearchMenuGUI() {
        boolean searching = true;
        
        while (searching) {
            String[] searchOptions = {
                "Search by Message ID",
                "Search by Recipient",
                "Find Longest Message",
                "Delete Message by Hash",
                "Show Comprehensive Report",
                "Load Messages from JSON",
                "Back to Main Menu"
            };
            
            int choice = JOptionPane.showOptionDialog(null,
                "Search & Management Options:",
                "QuickChat - Search Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                searchOptions,
                searchOptions[0]);
            
            switch (choice) {
                case 0: // Search by Message ID
                    String messageID = JOptionPane.showInputDialog(null,
                        "Enter Message ID to search:",
                        "Search by Message ID",
                        JOptionPane.QUESTION_MESSAGE);
                    
                    if (messageID != null && !messageID.trim().isEmpty()) {
                        String result = searchByMessageID(messageID);
                        if (result != null) {
                            JOptionPane.showMessageDialog(null,
                                "Found message:\n\"" + result + "\"",
                                "Search Result",
                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                "Message not found.",
                                "Search Result",
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    break;
                    
                case 1: // Search by Recipient
                    String recipient = JOptionPane.showInputDialog(null,
                        "Enter recipient cell number:",
                        "Search by Recipient",
                        JOptionPane.QUESTION_MESSAGE);
                    
                    if (recipient != null && !recipient.trim().isEmpty()) {
                        List<Message> messages = searchByRecipient(recipient);
                        if (!messages.isEmpty()) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Found ").append(messages.size()).append(" message(s) for ").append(recipient).append(":\n\n");
                            for (int i = 0; i < messages.size(); i++) {
                                sb.append(i + 1).append(". ").append(messages.get(i).messageText).append("\n");
                            }
                            JOptionPane.showMessageDialog(null,
                                sb.toString(),
                                "Search Results",
                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                "No messages found for that recipient.",
                                "Search Result",
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    break;
                    
                case 2: // Find Longest Message
                    String longest = findLongestMessage();
                    if (!longest.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                            "Longest message:\n\"" + longest + "\"\n\nLength: " + longest.length() + " characters",
                            "Longest Message",
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                            "No messages found.",
                            "Longest Message",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                    
                case 3: // Delete Message by Hash
                    String hash = JOptionPane.showInputDialog(null,
                        "Enter message hash to delete:",
                        "Delete Message",
                        JOptionPane.QUESTION_MESSAGE);
                    
                    if (hash != null && !hash.trim().isEmpty()) {
                        boolean deleted = deleteMessageByHash(hash);
                        if (deleted) {
                            JOptionPane.showMessageDialog(null,
                                "Message successfully deleted.",
                                "Delete Result",
                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                "Message with that hash not found.",
                                "Delete Result",
                                JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    break;
                    
                case 4: // Show Comprehensive Report
                    displayComprehensiveReportGUI();
                    break;
                    
                case 5: // Load Messages from JSON
                    loadFromJSON();
                    JOptionPane.showMessageDialog(null,
                        "Messages loaded from JSON file.",
                        "Load Complete",
                        JOptionPane.INFORMATION_MESSAGE);
                    break;
                    
                case 6: // Back to Main Menu
                case JOptionPane.CLOSED_OPTION:
                    searching = false;
                    break;
                    
                default:
                    JOptionPane.showMessageDialog(null,
                        "Invalid choice. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // ========== END PHASE 3 FEATURES ==========
    
    /**
     * Main application menu using JOptionPane
     */
    public static void showMainMenu() {
        boolean running = true;
        
        JOptionPane.showMessageDialog(null, 
            "Welcome to QuickChat!", 
            "QuickChat Application", 
            JOptionPane.INFORMATION_MESSAGE);
        
        while (running) {
            String[] options = {
                "Send Messages",
                "Search & Management (Phase 3 Features)", 
                "Show Comprehensive Report",
                "Quit"
            };
            
            int choice = JOptionPane.showOptionDialog(null,
                "Please select an option:",
                "QuickChat - Main Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            
            switch (choice) {
                case 0:
                    handleSendMessagesGUI();
                    break;
                case 1:
                    showSearchMenuGUI();
                    break;
                case 2:
                    displayComprehensiveReportGUI();
                    break;
                case 3:
                case JOptionPane.CLOSED_OPTION:
                    JOptionPane.showMessageDialog(null, 
                        "Thank you for using QuickChat!", 
                        "Goodbye", 
                        JOptionPane.INFORMATION_MESSAGE);
                    running = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, 
                        "Invalid choice. Please try again.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Handles the send messages workflow using JOptionPane
     */
    private static void handleSendMessagesGUI() {
        String input = JOptionPane.showInputDialog(null,
            "How many messages would you like to send?",
            "Send Messages",
            JOptionPane.QUESTION_MESSAGE);
        
        if (input == null) return; // User cancelled
        
        try {
            int numMessages = Integer.parseInt(input);
            
            if (numMessages <= 0) {
                JOptionPane.showMessageDialog(null,
                    "Please enter a positive number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            for (int i = 0; i < numMessages; i++) {
                // Get recipient
                String recipient;
                while (true) {
                    recipient = JOptionPane.showInputDialog(null,
                        String.format("Message %d of %d\nEnter recipient cell number:", i + 1, numMessages),
                        "Recipient Information",
                        JOptionPane.QUESTION_MESSAGE);
                    
                    if (recipient == null) return; // User cancelled
                    
                    Message tempMsg = new Message();
                    String validation = tempMsg.checkRecipientCell(recipient);
                    if (validation.contains("successfully")) {
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null,
                            validation,
                            "Invalid Phone Number",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
                
                // Get message text
                String messageText;
                while (true) {
                    messageText = JOptionPane.showInputDialog(null,
                        String.format("Message %d of %d\nRecipient: %s\nEnter your message:", i + 1, numMessages, recipient),
                        "Message Content",
                        JOptionPane.QUESTION_MESSAGE);
                    
                    if (messageText == null) return; // User cancelled
                    
                    Message tempMsg = new Message();
                    String validation = tempMsg.checkMessageLength(messageText);
                    if (validation.equals("Message ready to send")) {
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null,
                            validation,
                            "Message Too Long",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
                
                // Create message
                Message message = new Message(recipient, messageText, i);
                
                // Show message details
                message.displayMessage();
                
                // Get action
                String[] actionOptions = {"Send", "Store", "Disregard"};
                int actionChoice = JOptionPane.showOptionDialog(null,
                    "What would you like to do with this message?",
                    "Message Action",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    actionOptions,
                    actionOptions[0]);
                
                if (actionChoice == JOptionPane.CLOSED_OPTION) return; // User cancelled
                
                String action = actionOptions[actionChoice];
                String result = message.sentMessage(action);
                JOptionPane.showMessageDialog(null,
                    result,
                    "Action Result",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                "Please enter a valid number.",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE);
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