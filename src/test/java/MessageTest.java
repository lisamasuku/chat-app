import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Message functionality
 * Tests all Phase 2 requirements according to the roadmap
 */
public class MessageTest {
    
    private Message message;
    
    @BeforeEach
    void setUp() {
        // Clear all static message lists before each test
        Message.clearAllMessages();
        message = new Message();
    }
    
    // Test 2.1: Message length validation
    @Test
    @DisplayName("Test message length validation - success case")
    void testMessageLengthValidationSuccess() {
        String validMessage = "This is a valid message under 250 characters";
        String result = message.checkMessageLength(validMessage);
        assertEquals("Message ready to send", result);
    }
    
    @Test
    @DisplayName("Test message length validation - failure case")
    void testMessageLengthValidationFailure() {
        // Create a message over 250 characters
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longMessage.append("a");
        }
        String result = message.checkMessageLength(longMessage.toString());
        assertEquals("Message exceeds 10 characters", result);
    }
    
    @Test
    @DisplayName("Test message length validation - exactly 250 characters")
    void testMessageLengthValidationExactly250() {
        StringBuilder exactMessage = new StringBuilder();
        for (int i = 0; i < 250; i++) {
            exactMessage.append("a");
        }
        String result = message.checkMessageLength(exactMessage.toString());
        assertEquals("Message ready to send", result);
    }
    
    @Test
    @DisplayName("Test message length validation - null message")
    void testMessageLengthValidationNull() {
        String result = message.checkMessageLength(null);
        assertEquals("Message cannot be null", result);
    }
    
    // Test 2.2: Recipient validation (reuse from Part 1)
    @Test
    @DisplayName("Test recipient cell validation - valid number")
    void testRecipientCellValidationValid() {
        String validCell = "+27838968976";
        String result = message.checkRecipientCell(validCell);
        assertTrue(result.contains("successfully"));
    }
    
    @Test
    @DisplayName("Test recipient cell validation - invalid number")
    void testRecipientCellValidationInvalid() {
        String invalidCell = "08966553";
        String result = message.checkRecipientCell(invalidCell);
        assertFalse(result.contains("successfully"));
    }
    
    // Test 2.3: Message hash generation
    @Test
    @DisplayName("Test message hash generation with provided test data")
    void testMessageHashGeneration() {
        Message testMessage = new Message("+27838968976", "Hi Mike, can you join us for dinner tonight", 0);
        String hash = testMessage.createMessageHash();
        
        // The hash should be in format XX:Y:FIRSTLAST
        // XX = first 2 digits of message ID (will vary)
        // Y = message number (0)
        // FIRSTLAST = HI + TONIGHT = HITONIGHT
        
        String[] parts = hash.split(":");
        assertEquals(3, parts.length);
        assertEquals("0", parts[1]); // message number
        assertEquals("HITONIGHT", parts[2]); // first and last words
        assertEquals(2, parts[0].length()); // first two digits of ID
    }
    
    @Test
    @DisplayName("Test message hash generation with single word")
    void testMessageHashGenerationSingleWord() {
        Message testMessage = new Message("+27838968976", "Hello", 1);
        String hash = testMessage.createMessageHash();
        
        String[] parts = hash.split(":");
        assertEquals(3, parts.length);
        assertEquals("1", parts[1]); // message number
        assertEquals("HELLOHELLO", parts[2]); // single word repeated
    }
    
    // Test 2.4: Message ID generation
    @Test
    @DisplayName("Test message ID generation")
    void testMessageIDGeneration() {
        String messageID = message.getMessageID();
        assertNotNull(messageID);
        assertEquals(10, messageID.length());
        assertTrue(messageID.matches("\\d{10}")); // Should be 10 digits
    }
    
    @Test
    @DisplayName("Test message ID validation")
    void testMessageIDValidation() {
        assertTrue(message.checkMessageID("1234567890"));
        assertFalse(message.checkMessageID("123456789")); // too short
        assertFalse(message.checkMessageID("12345678901")); // too long
        assertFalse(message.checkMessageID("123456789a")); // contains letter
        assertFalse(message.checkMessageID(null)); // null
    }
    
    // Test 2.5: Message actions testing
    @Test
    @DisplayName("Test send message action")
    void testSendMessageAction() {
        Message testMessage = new Message("+27838968976", "Test message", 0);
        String result = testMessage.sentMessage("send");
        assertEquals("Message successfully sent", result);
        assertEquals(1, Message.getSentMessages().size());
        assertTrue(Message.getSentMessages().contains(testMessage));
    }
    
    @Test
    @DisplayName("Test store message action")
    void testStoreMessageAction() {
        Message testMessage = new Message("+27838968976", "Test message", 0);
        String result = testMessage.sentMessage("store");
        assertEquals("Message successfully stored", result);
        assertEquals(1, Message.getStoredMessages().size());
        assertTrue(Message.getStoredMessages().contains(testMessage));
    }
    
    @Test
    @DisplayName("Test disregard message action")
    void testDisregardMessageAction() {
        Message testMessage = new Message("+27838968976", "Test message", 0);
        String result = testMessage.sentMessage("disregard");
        assertEquals("Press 0 to delete message", result);
        assertEquals(1, Message.getDisregardedMessages().size());
        assertTrue(Message.getDisregardedMessages().contains(testMessage));
    }
    
    @Test
    @DisplayName("Test invalid message action")
    void testInvalidMessageAction() {
        Message testMessage = new Message("+27838968976", "Test message", 0);
        String result = testMessage.sentMessage("invalid");
        assertEquals("Invalid action. Please choose Send, Store, or Disregard", result);
    }
    
    @Test
    @DisplayName("Test case insensitive message actions")
    void testCaseInsensitiveMessageActions() {
        Message testMessage1 = new Message("+27838968976", "Test message 1", 0);
        Message testMessage2 = new Message("+27838968976", "Test message 2", 1);
        Message testMessage3 = new Message("+27838968976", "Test message 3", 2);
        
        assertEquals("Message successfully sent", testMessage1.sentMessage("SEND"));
        assertEquals("Message successfully stored", testMessage2.sentMessage("Store"));
        assertEquals("Press 0 to delete message", testMessage3.sentMessage("DiSrEgArD"));
    }
    
    // Test message counting
    @Test
    @DisplayName("Test return total messages")
    void testReturnTotalMessages() {
        assertEquals(0, Message.returnTotalMessages());
        
        Message msg1 = new Message("+27838968976", "Message 1", 0);
        Message msg2 = new Message("+27838968976", "Message 2", 1);
        
        msg1.sentMessage("send");
        assertEquals(1, Message.returnTotalMessages());
        
        msg2.sentMessage("send");
        assertEquals(2, Message.returnTotalMessages());
    }
    
    // Test getters and setters
    @Test
    @DisplayName("Test message getters and setters")
    void testMessageGettersAndSetters() {
        Message testMessage = new Message();
        
        testMessage.setRecipientCell("+27838968976");
        testMessage.setMessageText("Test message");
        testMessage.setMessageNumber(5);
        
        assertEquals("+27838968976", testMessage.getRecipientCell());
        assertEquals("Test message", testMessage.getMessageText());
        assertEquals(5, testMessage.getMessageNumber());
        assertNotNull(testMessage.getMessageID());
    }
    
    // Test constructor with parameters
    @Test
    @DisplayName("Test message constructor with parameters")
    void testMessageConstructorWithParameters() {
        Message testMessage = new Message("+27838968976", "Hello World", 3);
        
        assertEquals("+27838968976", testMessage.getRecipientCell());
        assertEquals("Hello World", testMessage.getMessageText());
        assertEquals(3, testMessage.getMessageNumber());
        assertNotNull(testMessage.getMessageID());
        assertNotNull(testMessage.getMessageHash());
    }
    
    // Test edge cases for hash generation
    @Test
    @DisplayName("Test hash generation with empty message")
    void testHashGenerationEmptyMessage() {
        Message testMessage = new Message("+27838968976", "", 0);
        String hash = testMessage.getMessageHash();
        
        String[] parts = hash.split(":");
        assertEquals(3, parts.length);
        assertEquals("0", parts[1]);
        assertEquals("", parts[2]); // empty first and last words
    }
    
    @Test
    @DisplayName("Test hash generation with whitespace message")
    void testHashGenerationWhitespaceMessage() {
        Message testMessage = new Message("+27838968976", "   Hello   World   ", 1);
        String hash = testMessage.getMessageHash();
        
        String[] parts = hash.split(":");
        assertEquals(3, parts.length);
        assertEquals("1", parts[1]);
        assertEquals("HELLOWORLD", parts[2]);
    }
    
    // Test static list management
    @Test
    @DisplayName("Test static list clearing")
    void testStaticListClearing() {
        Message msg1 = new Message("+27838968976", "Message 1", 0);
        Message msg2 = new Message("+27838968976", "Message 2", 1);
        Message msg3 = new Message("+27838968976", "Message 3", 2);
        
        msg1.sentMessage("send");
        msg2.sentMessage("store");
        msg3.sentMessage("disregard");
        
        assertEquals(1, Message.getSentMessages().size());
        assertEquals(1, Message.getStoredMessages().size());
        assertEquals(1, Message.getDisregardedMessages().size());
        
        Message.clearAllMessages();
        
        assertEquals(0, Message.getSentMessages().size());
        assertEquals(0, Message.getStoredMessages().size());
        assertEquals(0, Message.getDisregardedMessages().size());
    }
} 