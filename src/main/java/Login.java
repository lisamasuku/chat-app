import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Login class for QuickChat application
 * Handles user registration and authentication with persistent JSON storage
 * 
 * @author Your Name
 * @version 1.0
 */
public class Login {
    
    // Instance variables to store user credentials
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;
    
    // Static list to store all registered users
    private static List<Login> registeredUsers = new ArrayList<>();
    
    // Default constructor
    public Login() {
    }
    
    // Constructor with user details
    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Validates username format
     * Username must contain an underscore (_) and be no more than 5 characters long
     * 
     * @param username The username to validate
     * @return true if username is valid, false otherwise
     */
    public boolean checkUserName(String username) {
        // Check if username is null or empty
        if (username == null || username.isEmpty()) {
            return false;
        }
        
        // Check if username contains underscore and is no more than 5 characters
        return username.contains("_") && username.length() <= 5;
    }
    
    /**
     * Validates password complexity
     * Password must be at least 8 characters long, contain a capital letter,
     * a number, and a special character
     * 
     * The following password validation logic was developed with assistance from Claude AI
     * Anthropic. (2024). Claude (Version 3.5) [Large language model].
     * https://claude.ai/
     * Prompt: "help me check if a password is strong enough - needs 8 chars, uppercase, 
     * number and special character. how do i loop through each character to check?"
     * 
     * @param password The password to validate
     * @return true if password meets complexity requirements, false otherwise
     */
    public boolean checkPasswordComplexity(String password) {
        // Check if password is null or empty
        if (password == null || password.isEmpty()) {
            return false;
        }
        
        // Check minimum length (8 characters)
        if (password.length() < 8) {
            return false;
        }
        
        // Check for uppercase letter
        boolean hasUppercase = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
                break;
            }
        }
        if (!hasUppercase) {
            return false;
        }
        
        // Check for number
        boolean hasNumber = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasNumber = true;
                break;
            }
        }
        if (!hasNumber) {
            return false;
        }
        
        // Check for special character
        boolean hasSpecialChar = false;
        String specialChars = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        for (char c : password.toCharArray()) {
            if (specialChars.indexOf(c) != -1) {
                hasSpecialChar = true;
                break;
            }
        }
        
        return hasSpecialChar;
    }
    
    /**
     * Validates cell phone number format
     * Must contain international country code and be no more than 10 characters
     * 
     * @param cellPhoneNumber The phone number to validate
     * @return true if phone number is valid, false otherwise
     */
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        /*
         * The following regular expression was generated using Claude AI
         * Anthropic. (2024). Claude (Version 3.5) [Large language model].
         * https://claude.ai/
         * Prompt: "i need regex for south african phone numbers +27 something, 
         * but max 10 chars total. regex is confusing help!"
         */
        
        // Check if phone number is null or empty
        if (cellPhoneNumber == null || cellPhoneNumber.isEmpty()) {
            return false;
        }
        
        // Check if phone number starts with +27 (South African country code)
        // and has the correct format: +27 followed by 9 digits (total 12 characters)
        // Based on test case: +27838968976 should be valid
        String regex = "^\\+27[0-9]{9}$";
        
        return cellPhoneNumber.matches(regex);
    }
    
    /**
     * Registers a new user with persistent storage
     * Combines all validations and returns appropriate messages
     * 
     * @param username The username
     * @param password The password
     * @param cellPhoneNumber The phone number
     * @return Registration status message
     */
    public String registerUser(String username, String password, String cellPhoneNumber) {
        // Load existing users first
        loadUsersFromJSON();
        
        // Check if username already exists
        if (isUsernameExists(username)) {
            return "Username already exists. Please choose a different username.";
        }
        
        // Validate username
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        
        // Validate password
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        
        // Validate phone number
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        
        // If all validations pass, store the user credentials
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        
        // Add to registered users list and save to JSON
        registeredUsers.add(this);
        saveUsersToJSON();
        
        return "User registered successfully.";
    }
    
    /**
     * Authenticates user login against stored users
     * Verifies that login details match stored credentials
     * 
     * @param username The username
     * @param password The password
     * @return true if login successful, false otherwise
     */
    public static boolean authenticateUser(String username, String password) {
        // Load users from JSON
        loadUsersFromJSON();
        
        // Search for user with matching credentials
        for (Login user : registeredUsers) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Gets user details for successful login
     * @param username The username to find
     * @return Login object if found, null otherwise
     */
    public static Login getUserByUsername(String username) {
        loadUsersFromJSON();
        
        for (Login user : registeredUsers) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        
        return null;
    }
    
    /**
     * Legacy method for backward compatibility
     * @deprecated Use authenticateUser(String, String) instead
     */
    @Deprecated
    public boolean loginUser(String username, String password) {
        return authenticateUser(username, password);
    }
    
    /**
     * Returns login status message
     * 
     * @param loginSuccessful Whether login was successful
     * @return Appropriate login message
     */
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    
    // Getters and setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }
    
    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    // ========== JSON STORAGE METHODS ==========
    
    /**
     * Saves all registered users to JSON file
     */
    private static void saveUsersToJSON() {
        try {
            // Ensure data directory exists
            java.io.File dataDir = new java.io.File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter("data/users.json");
            gson.toJson(registeredUsers, writer);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving users to JSON: " + e.getMessage());
        }
    }
    
    /**
     * Loads registered users from JSON file
     */
    private static void loadUsersFromJSON() {
        try {
            // Ensure data directory exists
            java.io.File dataDir = new java.io.File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            java.io.File jsonFile = new java.io.File("data/users.json");
            if (!jsonFile.exists()) {
                // Create empty JSON file if it doesn't exist
                try (java.io.FileWriter writer = new java.io.FileWriter(jsonFile)) {
                    writer.write("[]");
                }
                return;
            }
            
            Gson gson = new Gson();
            FileReader reader = new FileReader(jsonFile);
            Type listType = new TypeToken<List<Login>>(){}.getType();
            List<Login> loadedUsers = gson.fromJson(reader, listType);
            reader.close();
            
            if (loadedUsers != null) {
                registeredUsers.clear();
                registeredUsers.addAll(loadedUsers);
            }
        } catch (IOException e) {
            System.err.println("Error loading users from JSON: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("JSON parsing error for users: " + e.getMessage());
        }
    }
    
    /**
     * Checks if a username already exists
     * @param username The username to check
     * @return true if username exists, false otherwise
     */
    private static boolean isUsernameExists(String username) {
        for (Login user : registeredUsers) {
            if (user.username.equals(username)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets the total number of registered users
     * @return number of registered users
     */
    public static int getTotalRegisteredUsers() {
        loadUsersFromJSON();
        return registeredUsers.size();
    }
    
    /**
     * Clears all registered users (for testing purposes)
     */
    public static void clearAllUsers() {
        registeredUsers.clear();
        saveUsersToJSON();
    }
} 