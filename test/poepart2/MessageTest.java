package poepart2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MessageTest {

    private Message msg;

    /** Populates the arrays with the five test data messages before every test. */
    @BeforeMethod
    public void setUp() {
        msg = new Message();
        msg.addMessage("+27834557896", "Did you get the cake?", "Sent");
        msg.addMessage("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        msg.addMessage("+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        msg.addMessage("0838884567", "It is dinner time!", "Sent");
        msg.addMessage("+27838884567", "Ok, I am leaving without you.", "Stored");
    }

    @Test
    public void sentMessagesArrayCorrectlyPopulated() {
        assertEquals(msg.getSentMessages(),
                Arrays.asList("Did you get the cake?", "It is dinner time!"));
    }

    @Test
    public void displayTheLongestMessage() {
        assertEquals(msg.longestMessage(),
                "Where are you? You are late! I have asked you to be on time.");
    }

    @Test
    public void searchForMessageID() {
        String id = msg.getMessageIDs().get(3); // test data message 4
        String[] result = msg.findByMessageID(id);
        assertEquals(result[0], "0838884567");
        assertEquals(result[1], "It is dinner time!");
    }

    @Test
    public void searchAllMessagesForARecipient() {
        assertEquals(msg.findByRecipient("+27838884567"),
                Arrays.asList(
                        "Where are you? You are late! I have asked you to be on time.",
                        "Ok, I am leaving without you."));
    }

    @Test
    public void deleteAMessageUsingAMessageHash() {
        String hash = msg.getMessageHashes().get(1); // test data message 2
        assertEquals(msg.deleteMessageByHash(hash),
                "Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.");
        assertFalse(msg.getStoredMessages().contains(
                "Where are you? You are late! I have asked you to be on time."));
    }

    @Test
    public void displayReport() {
        String report = msg.displaySentReport();
        assertTrue(report.contains(msg.getMessageHashes().get(0)));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains(msg.getMessageHashes().get(3)));
        assertTrue(report.contains("0838884567"));
        assertTrue(report.contains("It is dinner time!"));
    }

    @Test
    public void storedMessagesReadBackFromJson() throws IOException {
        File file = File.createTempFile("messages", ".json");
        file.deleteOnExit();

        msg.saveStoredMessagesToJson(file.getAbsolutePath());

        Message other = new Message();
        assertEquals(other.loadStoredMessagesFromJson(file.getAbsolutePath()), 2);
        assertEquals(other.getStoredMessages(), msg.getStoredMessages());
    }
}
