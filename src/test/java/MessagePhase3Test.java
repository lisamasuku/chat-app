import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Unit tests for Phase 3: Data Management & Reporting Features
 * Tests array management, search functionality, deletion, and reporting
 */
public class MessagePhase3Test {
    
    @BeforeEach
    void setUp() {
        // Clear all messages before each test
        Message.clearAllMessages();
    }
    
    @Test
    @DisplayName("Test 3.1: Array population verification")
    void testArrayPopulation() {
        // Create test messages
        Message msg1 = new Message("+27838968976", "Hello world", 0);
        Message msg2 = new Message("+27838884567", "How are you?", 1);
        Message msg3 = new Message("+27838123456", "Goodbye", 2);
        
        // Send messages to different lists
        msg1.sentMessage("send");
        msg2.sentMessage("store");
        msg3.sentMessage("disregard");
        
        // Verify arrays are populated
        List<String> hashArray = Message.getMessageHashArray();
        List<String> idArray = Message.getMessageIDArray();
        
        assertEquals(3, hashArray.size(), "Hash array should contain 3 elements");
        assertEquals(3, idArray.size(), "ID array should contain 3 elements");
        
        // Verify hash format
        assertTrue(hashArray.get(0).matches("\\d{2}:\\d+:[A-Z]+"), 
                  "Hash should match format XX:Y:FIRSTLAST");
    }
    
    @Test
    @DisplayName("Test 3.2: Longest message detection")
    void testLongestMessage() {
        // Create messages with different lengths
        Message msg1 = new Message("+27838968976", "Short", 0);
        Message msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 1);
        Message msg3 = new Message("+27838123456", "Medium length message here", 2);
        
        // Send all messages
        msg1.sentMessage("send");
        msg2.sentMessage("send");
        msg3.sentMessage("send");
        
        String longest = Message.findLongestMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest,
                    "Should return the longest message");
    }
    
    @Test
    @DisplayName("Test 3.3: Message ID search")
    void testMessageIDSearch() {
        // Create test message
        Message msg = new Message("+27838968976", "It is dinner time!", 4);
        msg.sentMessage("send");
        
        // Search by message ID
        String result = Message.searchByMessageID(msg.getMessageID());
        assertEquals("It is dinner time!", result, "Should find message by ID");
        
        // Test non-existent ID
        String notFound = Message.searchByMessageID("9999999999");
        assertNull(notFound, "Should return null for non-existent ID");
    }
    
    @Test
    @DisplayName("Test 3.4: Recipient search")
    void testRecipientSearch() {
        // Create multiple messages for same recipient
        Message msg1 = new Message("+27838884567", "First message", 0);
        Message msg2 = new Message("+27838884567", "Second message", 1);
        Message msg3 = new Message("+27838968976", "Different recipient", 2);
        
        // Send all messages
        msg1.sentMessage("send");
        msg2.sentMessage("send");
        msg3.sentMessage("send");
        
        // Search for messages to specific recipient
        List<Message> results = Message.searchByRecipient("+27838884567");
        assertEquals(2, results.size(), "Should find 2 messages for recipient +27838884567");
        
        // Verify message content
        assertTrue(results.stream().anyMatch(m -> m.getMessageText().equals("First message")));
        assertTrue(results.stream().anyMatch(m -> m.getMessageText().equals("Second message")));
    }
    
    @Test
    @DisplayName("Test 3.5: Message deletion by hash")
    void testMessageDeletion() {
        // Create test message
        Message msg = new Message("+27838968976", "Test message for deletion", 2);
        msg.sentMessage("send");
        
        // Verify message exists
        assertEquals(1, Message.getSentMessages().size(), "Should have 1 sent message");
        
        // Delete by hash
        String hash = msg.getMessageHash();
        boolean deleted = Message.deleteMessageByHash(hash);
        
        assertTrue(deleted, "Should successfully delete message");
        assertEquals(0, Message.getSentMessages().size(), "Should have 0 sent messages after deletion");
        
        // Test deletion of non-existent hash
        boolean notDeleted = Message.deleteMessageByHash("99:9:NONEXISTENT");
        assertFalse(notDeleted, "Should return false for non-existent hash");
    }
    
    @Test
    @DisplayName("Test 3.6: Message hash generation with test data")
    void testMessageHashGeneration() {
        // Test with provided example: "Hi Mike, can you join us for dinner tonight"
        Message msg = new Message("+27838968976", "Hi Mike, can you join us for dinner tonight", 0);
        
        String hash = msg.getMessageHash();
        
        // Verify hash format: XX:0:HITONIGHT
        assertTrue(hash.matches("\\d{2}:0:HITONIGHT"), 
                  "Hash should match format XX:0:HITONIGHT for test message");
        
        // Test the specific pattern
        String[] parts = hash.split(":");
        assertEquals(3, parts.length, "Hash should have 3 parts separated by colons");
        assertEquals("0", parts[1], "Message number should be 0");
        assertEquals("HITONIGHT", parts[2], "Should combine first and last words in uppercase");
    }
    
    @Test
    @DisplayName("Test 3.7: Array updates after operations")
    void testArrayUpdatesAfterOperations() {
        // Create initial message
        Message msg1 = new Message("+27838968976", "Initial message", 0);
        msg1.sentMessage("send");
        
        assertEquals(1, Message.getMessageHashArray().size(), "Should have 1 hash in array");
        assertEquals(1, Message.getMessageIDArray().size(), "Should have 1 ID in array");
        
        // Add another message
        Message msg2 = new Message("+27838884567", "Second message", 1);
        msg2.sentMessage("store");
        
        assertEquals(2, Message.getMessageHashArray().size(), "Should have 2 hashes in array");
        assertEquals(2, Message.getMessageIDArray().size(), "Should have 2 IDs in array");
        
        // Delete a message
        Message.deleteMessageByHash(msg1.getMessageHash());
        
        assertEquals(1, Message.getMessageHashArray().size(), "Should have 1 hash after deletion");
        assertEquals(1, Message.getMessageIDArray().size(), "Should have 1 ID after deletion");
    }
    
    @Test
    @DisplayName("Test 3.8: Search across different message lists")
    void testSearchAcrossLists() {
        // Create messages in different lists
        Message sentMsg = new Message("+27838968976", "Sent message", 0);
        Message storedMsg = new Message("+27838884567", "Stored message", 1);
        Message disregardedMsg = new Message("+27838123456", "Disregarded message", 2);
        
        sentMsg.sentMessage("send");
        storedMsg.sentMessage("store");
        disregardedMsg.sentMessage("disregard");
        
        // Test search by ID across all lists
        assertEquals("Sent message", Message.searchByMessageID(sentMsg.getMessageID()));
        assertEquals("Stored message", Message.searchByMessageID(storedMsg.getMessageID()));
        assertEquals("Disregarded message", Message.searchByMessageID(disregardedMsg.getMessageID()));
        
        // Test longest message across all lists
        Message longMsg = new Message("+27838999999", "This is a very long message that should be the longest among all messages in all lists", 3);
        longMsg.sentMessage("disregard");
        
        String longest = Message.findLongestMessage();
        assertEquals(longMsg.getMessageText(), longest, "Should find longest message across all lists");
    }
    
    @Test
    @DisplayName("Test 3.9: Empty state handling")
    void testEmptyStateHandling() {
        // Test with no messages
        String longest = Message.findLongestMessage();
        assertEquals("", longest, "Should return empty string when no messages exist");
        
        String notFound = Message.searchByMessageID("1234567890");
        assertNull(notFound, "Should return null when searching empty lists");
        
        List<Message> noResults = Message.searchByRecipient("+27838999999");
        assertTrue(noResults.isEmpty(), "Should return empty list when no messages for recipient");
        
        boolean notDeleted = Message.deleteMessageByHash("00:0:NONEXISTENT");
        assertFalse(notDeleted, "Should return false when trying to delete from empty lists");
    }
    
    @Test
    @DisplayName("Test 3.10: Total message count verification")
    void testTotalMessageCount() {
        // Create messages in different lists
        Message msg1 = new Message("+27838968976", "Message 1", 0);
        Message msg2 = new Message("+27838884567", "Message 2", 1);
        Message msg3 = new Message("+27838123456", "Message 3", 2);
        
        msg1.sentMessage("send");
        msg2.sentMessage("store");
        msg3.sentMessage("disregard");
        
        // Verify individual counts
        assertEquals(1, Message.getSentMessages().size());
        assertEquals(1, Message.getStoredMessages().size());
        assertEquals(1, Message.getDisregardedMessages().size());
        
        // Verify total through returnTotalMessages (only counts sent messages)
        assertEquals(1, Message.returnTotalMessages());
        
        // Add more sent messages
        Message msg4 = new Message("+27838777777", "Message 4", 3);
        msg4.sentMessage("send");
        
        assertEquals(2, Message.returnTotalMessages());
    }
} 