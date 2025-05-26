import javax.swing.JOptionPane;

/**
 * Main application class for QuickChat
 * Implements proper authentication flow as required by the assignment
 * Users must register and login before accessing messaging features
 */
public class QuickChatApp {
    
    private static Login currentUser = null;
    
    public static void main(String[] args) {
        // Check if console mode is requested via command line argument
        if (args.length > 0 && args[0].equals("--console")) {
            System.out.println("Starting QuickChat in console mode...");
            System.out.println("Console mode not implemented in this version.");
            System.out.println("Please run without arguments for GUI mode.");
            return;
        }
        
        // Start the QuickChat application with proper authentication flow
        showWelcomeScreen();
    }
    
    /**
     * Shows the welcome screen and handles authentication
     */
    private static void showWelcomeScreen() {
        JOptionPane.showMessageDialog(null, 
            "Welcome to QuickChat!\n\nYou must register and login to use the messaging system.", 
            "QuickChat - Welcome", 
            JOptionPane.INFORMATION_MESSAGE);
        
        boolean authenticated = false;
        
        while (!authenticated) {
            String[] authOptions = {
                "Register New Account",
                "Login to Existing Account", 
                "Exit Application"
            };
            
            int choice = JOptionPane.showOptionDialog(null,
                "Please choose an option:",
                "QuickChat - Authentication",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                authOptions,
                authOptions[0]);
            
            switch (choice) {
                case 0:
                    if (handleRegistration()) {
                        authenticated = handleLogin();
                    }
                    break;
                case 1:
                    authenticated = handleLogin();
                    break;
                case 2:
                case JOptionPane.CLOSED_OPTION:
                    JOptionPane.showMessageDialog(null, 
                        "Thank you for visiting QuickChat!", 
                        "Goodbye", 
                        JOptionPane.INFORMATION_MESSAGE);
                    return;
                default:
                    JOptionPane.showMessageDialog(null, 
                        "Invalid choice. Please try again.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // If we reach here, user is authenticated - show main application
        Message.showMainMenu();
    }
    
    /**
     * Handles user registration process
     * @return true if registration successful, false otherwise
     */
    private static boolean handleRegistration() {
        // Get first and last name for personalized welcome
        String firstName = JOptionPane.showInputDialog(null,
            "Enter your first name:",
            "Registration - Personal Information",
            JOptionPane.QUESTION_MESSAGE);
        
        if (firstName == null || firstName.trim().isEmpty()) {
            return false; // User cancelled or entered empty name
        }
        
        String lastName = JOptionPane.showInputDialog(null,
            "Enter your last name:",
            "Registration - Personal Information", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (lastName == null || lastName.trim().isEmpty()) {
            return false; // User cancelled or entered empty name
        }
        
        // Create login instance with user names
        currentUser = new Login(firstName.trim(), lastName.trim());
        
        // Get username with validation
        String username;
        while (true) {
            username = JOptionPane.showInputDialog(null,
                "Create a username:\n\n" +
                "Requirements:\n" +
                "• Must contain an underscore (_)\n" +
                "• Maximum 5 characters\n\n" +
                "Example: kyl_1",
                "Registration - Username",
                JOptionPane.QUESTION_MESSAGE);
            
            if (username == null) return false; // User cancelled
            
            if (currentUser.checkUserName(username)) {
                break;
            } else {
                JOptionPane.showMessageDialog(null,
                    "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.",
                    "Invalid Username",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // Get password with validation
        String password;
        while (true) {
            password = JOptionPane.showInputDialog(null,
                "Create a password:\n\n" +
                "Requirements:\n" +
                "• At least 8 characters long\n" +
                "• Must contain a capital letter\n" +
                "• Must contain a number\n" +
                "• Must contain a special character\n\n" +
                "Example: Ch&&sec@ke99!",
                "Registration - Password",
                JOptionPane.QUESTION_MESSAGE);
            
            if (password == null) return false; // User cancelled
            
            if (currentUser.checkPasswordComplexity(password)) {
                break;
            } else {
                JOptionPane.showMessageDialog(null,
                    "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                    "Invalid Password",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // Get cell phone number with validation
        String cellPhone;
        while (true) {
            cellPhone = JOptionPane.showInputDialog(null,
                "Enter your South African cell phone number:\n\n" +
                "Requirements:\n" +
                "• Must include international code (+27)\n" +
                "• Format: +27xxxxxxxxx\n\n" +
                "Example: +27838968976",
                "Registration - Cell Phone",
                JOptionPane.QUESTION_MESSAGE);
            
            if (cellPhone == null) return false; // User cancelled
            
            if (currentUser.checkCellPhoneNumber(cellPhone)) {
                break;
            } else {
                JOptionPane.showMessageDialog(null,
                    "Cell phone number incorrectly formatted or does not contain international code.",
                    "Invalid Phone Number",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // Register the user
        String registrationResult = currentUser.registerUser(username, password, cellPhone);
        
        if (registrationResult.contains("successfully")) {
            JOptionPane.showMessageDialog(null,
                "Registration successful!\n\nYou can now login with your credentials.",
                "Registration Complete",
                JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null,
                "Registration failed: " + registrationResult,
                "Registration Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Handles user login process
     * @return true if login successful, false otherwise
     */
    private static boolean handleLogin() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(null,
                "No user account found. Please register first.",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Get login credentials
        String username = JOptionPane.showInputDialog(null,
            "Enter your username:",
            "Login - Username",
            JOptionPane.QUESTION_MESSAGE);
        
        if (username == null) return false; // User cancelled
        
        String password = JOptionPane.showInputDialog(null,
            "Enter your password:",
            "Login - Password",
            JOptionPane.QUESTION_MESSAGE);
        
        if (password == null) return false; // User cancelled
        
        // Attempt login
        boolean loginSuccess = currentUser.loginUser(username, password);
        String loginMessage = currentUser.returnLoginStatus(loginSuccess);
        
        if (loginSuccess) {
            JOptionPane.showMessageDialog(null,
                loginMessage,
                "Login Successful",
                JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null,
                loginMessage,
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
} 