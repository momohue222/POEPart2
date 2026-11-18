
package poepart2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.testng.annotations.Test;

public class MessageNGTest {

    @Test
    public void testReturnTotalMessages() {
        Message instance = new Message();

        instance.addMessage("+27834557896", "Hello there", "Sent");
        instance.addMessage("+27838884567", "Good morning", "Sent");
        instance.addMessage("+27834484567", "See you later", "Stored");

        assertEquals(instance.returnTotalMessages(), 2);
    }

    @Test
    public void testCreateMessageHash() {
        Message instance = new Message();

        String result = instance.createMessageHash(
                "1234567890", 2, "Hello there friend");

        assertEquals(result, "12:2:HELLOFRIEND");
    }

    @Test
    public void testGenerateMessageID() {
        Message instance = new Message();

        String result = instance.generateMessageID();

        assertTrue(result.matches("[0-9]{10}"));
    }

    @Test
    public void testCheckRecipientCell() {
        Message instance = new Message();

        assertEquals(
                instance.checkRecipientCell("+27834557896"),
                "Valid number");

        assertEquals(
                instance.checkRecipientCell("0834557896"),
                "Valid number");

        assertEquals(
                instance.checkRecipientCell("12345"),
                "Invalid number. Please enter a valid South African number.");
    }

    @Test
    public void testSentMessage() {
        Message instance = new Message(new Scanner("1\n"));

        assertEquals(instance.SentMessage(), "Sent");
    }

    @Test
    public void testPrintMessages() {
        Message instance = new Message();

        instance.addMessage(
                "+27834557896", "Hello there", "Sent");

        String result = instance.printMessages();

        assertTrue(result.contains("Hello there"));
        assertTrue(result.contains("+27834557896"));
        assertTrue(result.contains("Sent"));
    }

    @Test
    public void testStoredMessagesReadBackFromJson()
            throws IOException {

        Message original = new Message();

        original.addMessage(
                "+27834557896", "Hello from JSON", "Stored");

        Path file = Files.createTempFile("messages", ".json");

        original.saveStoredMessagesToJson(file.toString());

        Message restored = new Message();

        assertEquals(
                restored.loadStoredMessagesFromJson(file.toString()), 1);

        assertEquals(
                restored.getStoredMessages(),
                original.getStoredMessages());

        Files.deleteIfExists(file);
    }
}
