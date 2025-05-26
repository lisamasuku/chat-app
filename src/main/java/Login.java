/**
 * Login class for QuickChat application
 * Handles user registration and authentication
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
     * Prompt: "Create a method to validate password complexity checking for minimum length, 
     * uppercase letters, numbers, and special characters using character iteration"
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
         * Prompt: "Create a regex for South African phone numbers that start with +27 
         * and have no more than 10 total characters including the country code"
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
     * Registers a new user
     * Combines all validations and returns appropriate messages
     * 
     * @param username The username
     * @param password The password
     * @param cellPhoneNumber The phone number
     * @return Registration status message
     */
    public String registerUser(String username, String password, String cellPhoneNumber) {
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
        
        // If all validations pass, store the user credentials and return success
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        
        return "User registered successfully.";
    }
    
    /**
     * Authenticates user login
     * Verifies that login details match stored credentials
     * 
     * @param username The username
     * @param password The password
     * @return true if login successful, false otherwise
     */
    public boolean loginUser(String username, String password) {
        // Check if user has been registered (credentials are stored)
        if (this.username == null || this.password == null) {
            return false;
        }
        
        // Verify that provided credentials match stored credentials
        return this.username.equals(username) && this.password.equals(password);
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
} 