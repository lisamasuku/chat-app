import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Login class
 * Tests all authentication and validation methods
 * 
 * @author Your Name
 * @version 1.0
 */
public class LoginTest {
    
    private Login login;
    
    @BeforeEach
    public void setUp() {
        // Clear all users before each test to ensure clean state
        Login.clearAllUsers();
        login = new Login("John", "Doe");
    }
    
    // ========== USERNAME VALIDATION TESTS ==========
    
    @Test
    public void testUsernameCorrectlyFormatted() {
        // Test with valid username: "kyl_1"
        assertTrue(login.checkUserName("kyl_1"), 
            "Username 'kyl_1' should be valid (contains underscore and ≤5 characters)");
    }
    
    @Test
    public void testUsernameIncorrectlyFormatted() {
        // Test with invalid username: "kyle!!!!!!"
        assertFalse(login.checkUserName("kyle!!!!!!!"), 
            "Username 'kyle!!!!!!!' should be invalid (no underscore and >5 characters)");
    }
    
    @Test
    public void testUsernameWithoutUnderscore() {
        assertFalse(login.checkUserName("kyle"), 
            "Username without underscore should be invalid");
    }
    
    @Test
    public void testUsernameTooLong() {
        assertFalse(login.checkUserName("user_name"), 
            "Username longer than 5 characters should be invalid");
    }
    
    @Test
    public void testUsernameNull() {
        assertFalse(login.checkUserName(null), 
            "Null username should be invalid");
    }
    
    @Test
    public void testUsernameEmpty() {
        assertFalse(login.checkUserName(""), 
            "Empty username should be invalid");
    }
    
    // ========== PASSWORD COMPLEXITY TESTS ==========
    
    @Test
    public void testPasswordMeetsComplexityRequirements() {
        // Test with valid password: "Ch&&sec@ke99!"
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"), 
            "Password 'Ch&&sec@ke99!' should meet complexity requirements");
    }
    
    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        // Test with invalid password: "password"
        assertFalse(login.checkPasswordComplexity("password"), 
            "Password 'password' should not meet complexity requirements");
    }
    
    @Test
    public void testPasswordTooShort() {
        assertFalse(login.checkPasswordComplexity("Ab1!"), 
            "Password shorter than 8 characters should be invalid");
    }
    
    @Test
    public void testPasswordNoUppercase() {
        assertFalse(login.checkPasswordComplexity("password123!"), 
            "Password without uppercase should be invalid");
    }
    
    @Test
    public void testPasswordNoNumber() {
        assertFalse(login.checkPasswordComplexity("Password!"), 
            "Password without number should be invalid");
    }
    
    @Test
    public void testPasswordNoSpecialCharacter() {
        assertFalse(login.checkPasswordComplexity("Password123"), 
            "Password without special character should be invalid");
    }
    
    // ========== CELL PHONE NUMBER VALIDATION TESTS ==========
    
    @Test
    public void testCellPhoneCorrectlyFormatted() {
        // Test with valid phone: "+27838968976"
        assertTrue(login.checkCellPhoneNumber("+27838968976"), 
            "Phone number '+27838968976' should be valid");
    }
    
    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        // Test with invalid phone: "08966553"
        assertFalse(login.checkCellPhoneNumber("08966553"), 
            "Phone number '08966553' should be invalid (no international code)");
    }
    
    @Test
    public void testCellPhoneTooLong() {
        assertFalse(login.checkCellPhoneNumber("+2783896897612345"), 
            "Phone number longer than 10 characters should be invalid");
    }
    
    @Test
    public void testCellPhoneNoInternationalCode() {
        assertFalse(login.checkCellPhoneNumber("0838968976"), 
            "Phone number without international code should be invalid");
    }
    
    // ========== REGISTRATION TESTS ==========
    
    @Test
    public void testSuccessfulRegistration() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(result.contains("successfully"), 
            "Registration with valid data should be successful");
    }
    
    @Test
    public void testRegistrationWithInvalidUsername() {
        String result = login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(result.contains("Username is not correctly formatted"), 
            "Registration should fail with invalid username");
    }
    
    @Test
    public void testRegistrationWithInvalidPassword() {
        String result = login.registerUser("kyl_1", "password", "+27838968976");
        assertTrue(result.contains("Password is not correctly formatted"), 
            "Registration should fail with invalid password");
    }
    
    @Test
    public void testRegistrationWithInvalidPhone() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "08966553");
        assertTrue(result.contains("Cell phone number incorrectly formatted"), 
            "Registration should fail with invalid phone number");
    }
    
    // ========== LOGIN TESTS ==========
    
    @Test
    public void testSuccessfulLogin() {
        // First register a user
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        
        // Then test login
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"), 
            "Login with correct credentials should succeed");
    }
    
    @Test
    public void testFailedLogin() {
        // First register a user
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        
        // Then test login with wrong password
        assertFalse(login.loginUser("kyl_1", "wrongpassword"), 
            "Login with incorrect credentials should fail");
    }
    
    @Test
    public void testLoginWithNonExistentUser() {
        assertFalse(login.loginUser("nonexistent", "password"), 
            "Login with non-existent user should fail");
    }
    
    // ========== LOGIN STATUS MESSAGE TESTS ==========
    
    @Test
    public void testSuccessfulLoginMessage() {
        String message = login.returnLoginStatus(true);
        assertEquals("Welcome John, Doe it is great to see you again.", message,
            "Successful login should return welcome message with user's name");
    }
    
    @Test
    public void testFailedLoginMessage() {
        String message = login.returnLoginStatus(false);
        assertEquals("Username or password incorrect, please try again.", message,
            "Failed login should return error message");
    }
    
    // ========== INTEGRATION TESTS ==========
    
    @Test
    public void testCompleteRegistrationAndLoginFlow() {
        // Test complete flow: register then login
        String registrationResult = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(registrationResult.contains("successfully"), 
            "Registration should succeed");
        
        boolean loginResult = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(loginResult, "Login after registration should succeed");
        
        String loginMessage = login.returnLoginStatus(loginResult);
        assertTrue(loginMessage.contains("Welcome"), 
            "Login message should contain welcome");
    }
} 