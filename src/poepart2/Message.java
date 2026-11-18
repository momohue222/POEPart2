package poepart2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Part 2 (sending messages) plus Part 3 (arrays, stored messages menu, JSON).
 *
 * @author Student
 */
public class Message {

    // ---- Master lists: index i in every list describes the same message ----
    ArrayList<String> messageIds = new ArrayList<>();       // Message ID array
    ArrayList<String> recipientNumber = new ArrayList<>();
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<String> msgHash = new ArrayList<>();          // Message Hash array
    ArrayList<String> messageStatus = new ArrayList<>();    // Sent / Stored / Disregarded

    // ---- Part 3 arrays (filled from user input / JSON, never hard-coded) ----
    ArrayList<String> sentMessages = new ArrayList<>();         // Sent Message array
    ArrayList<String> disregardedMessages = new ArrayList<>();  // Disregarded Messages array
    ArrayList<String> storedMessages = new ArrayList<>();       // Stored Messages array

    public static final String JSON_FILE = "messages.json";

    Scanner input;
    Random random = new Random();

    int numMessageSent = 0;
    String sender = "";

    public Message() {
        this(new Scanner(System.in));
    }

    /** Pass the same Scanner used in main so System.in is only wrapped once. */
    public Message(Scanner scanner) {
        this.input = scanner;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    // =====================================================================
    //  PART 2 – sending messages
    // =====================================================================

    public void sendingMessages() {

        System.out.println("How many messages do you want to send?");
        int numberOfMessages = readInt();

        for (int i = 1; i <= numberOfMessages; i++) {

            System.out.println("MESSAGE: " + i);

            System.out.println("Enter recipient  cell number!!");
            String recipient = input.nextLine().trim();

            String recipientResult = checkRecipientCell(recipient);

            if (!recipientResult.equals("Valid number")) {
                System.out.println(recipientResult);
                i = i - 1;
                continue;
            }

            System.out.println("Enter your message!!");
            String messageText = input.nextLine();

            if (messageText.length() > 250
                    || messageText.trim().isEmpty()) {
                System.out.println("Your message length should be between 1 to 250");
                i = i - 1;
                continue;
            }

            String status = SentMessage();

            // Only add once the message is fully valid, so the lists stay in step.
            addMessage(recipient, messageText, status);
        }

        saveStoredMessages();
        printMessages();

        System.out.println("Total messages sent:" + returnTotalMessages());
    }

    public int returnTotalMessages() {
        return numMessageSent;
    }

    public String createMessageHash(String messageID,
            int messageNumber,
            String messageText) {

        String firstTwoNumbers
                = messageID.substring(0, 2);
        String trimmedMessage = messageText.trim();
        String[] words = trimmedMessage.split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String hash = firstTwoNumbers
                + ":"
                + messageNumber
                + ":"
                + firstWord
                + lastWord;
        return hash.toUpperCase();
    }

    /** Generates a unique 10-digit ID (the caller stores it). */
    public String generateMessageID() {

        String messageID;

        do {
            long number = 1000000000L + random.nextLong(9000000000L);

            messageID = String.valueOf(number);

        } while (messageIds.contains(messageID));

        return messageID;
    }

    public String checkRecipientCell(String cellphone) {
        if (cellphone.matches("^\\+27[0-9]{9}$")
                || cellphone.matches("^0[0-9]{9}$")) {

            return "Valid number";

        } else {

            return "Invalid number. Please enter a valid South African number.";
        }
    }

    public String SentMessage() {

        while (true) {
            System.out.println("\nChoose what you want to do with this message");
            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message to send later");

            System.out.println("Enter your choice");
            int choice = readInt();

            switch (choice) {

                case 1 -> {
                    System.out.println("Message successfully sent");
                    return "Sent";
                }

                case 2 -> {
                    System.out.println("Message disregarded");
                    return "Disregarded";
                }

                case 3 -> {
                    System.out.println("Message successfully stored");
                    return "Stored";
                }

                default -> System.out.println("Invalid option, please choose 1, 2 or 3.");
            }
        }
    }

    public String printMessages() {
        StringBuilder allMessages = new StringBuilder();

        allMessages.append("\n=====All Messages=====");

        for (int i = 0; i < messages.size(); i++) {
            allMessages.append("\n\nMessage Number:").append(i + 1);
            allMessages.append("\nMessage ID:").append(messageIds.get(i));
            allMessages.append("\nRecipient:").append(recipientNumber.get(i));
            allMessages.append("\nMessage:").append(messages.get(i));
            allMessages.append("\nMessage Hash:").append(msgHash.get(i));
            allMessages.append("\nStatus:").append(messageStatus.get(i));
        }

        System.out.println(allMessages);

        return allMessages.toString();
    }

    private int readInt() {
        while (true) {
            String line = input.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    // =====================================================================
    //  PART 3 – populating the arrays
    // =====================================================================

    /**
     * Adds a message and fills every array. flag is Sent, Stored or Disregard(ed).
     * Returns the generated message ID.
     */
    public String addMessage(String recipient, String messageText, String flag) {
        String id = generateMessageID();
        String hash = createMessageHash(id, messages.size() + 1, messageText);
        addEntry(id, recipient, messageText, normaliseStatus(flag), hash);
        return id;
    }

    private void addEntry(String id, String recipient, String messageText,
            String status, String hash) {

        messageIds.add(id);
        recipientNumber.add(recipient);
        messages.add(messageText);
        msgHash.add(hash);
        messageStatus.add(status);

        switch (status) {
            case "Sent" -> {
                sentMessages.add(messageText);
                numMessageSent++;
            }
            case "Stored" -> storedMessages.add(messageText);
            default -> disregardedMessages.add(messageText);
        }
    }

    private String normaliseStatus(String flag) {
        if (flag == null) {
            return "Disregarded";
        }
        String f = flag.trim().toLowerCase();
        if (f.startsWith("sent")) {
            return "Sent";
        }
        if (f.startsWith("stor")) {
            return "Stored";
        }
        return "Disregarded";
    }

    // =====================================================================
    //  PART 3 – Stored Messages menu (option 4)
    // =====================================================================

    public void storedMessagesMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n===== Stored Messages =====");
            System.out.println("a. Display the sender and recipient of all stored messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message ID");
            System.out.println("d. Search for all messages stored for a recipient");
            System.out.println("e. Delete a message using the message hash");
            System.out.println("f. Display a report of all stored messages");
            System.out.println("g. Back to main menu");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim().toLowerCase();

            switch (choice) {

                case "a" -> System.out.println(displaySenderAndRecipient());

                case "b" -> {
                    if (storedMessages.isEmpty()) {
                        System.out.println("No stored messages.");
                    } else {
                        System.out.println("Longest stored message: " + longestMessage());
                    }
                }

                case "c" -> {
                    System.out.print("Enter the message ID: ");
                    String[] found = findByMessageID(input.nextLine().trim());
                    if (found == null) {
                        System.out.println("Message ID not found.");
                    } else {
                        System.out.println("Recipient: " + found[0]);
                        System.out.println("Message: " + found[1]);
                    }
                }

                case "d" -> {
                    System.out.print("Enter the recipient number: ");
                    List<String> found = findByRecipient(input.nextLine().trim());
                    if (found.isEmpty()) {
                        System.out.println("No messages found for that recipient.");
                    } else {
                        for (String m : found) {
                            System.out.println("\"" + m + "\"");
                        }
                    }
                }

                case "e" -> {
                    System.out.print("Enter the message hash: ");
                    System.out.println(deleteMessageByHash(input.nextLine().trim()));
                    saveStoredMessages();
                }

                case "f" -> System.out.println(displayStoredReport());

                case "g" -> back = true;

                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }

    /** 2a. Sender and recipient of every stored message. */
    public String displaySenderAndRecipient() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messageStatus.size(); i++) {
            if (messageStatus.get(i).equals("Stored")) {
                sb.append("Sender: ").append(sender)
                        .append(" | Recipient: ").append(recipientNumber.get(i)).append("\n");
            }
        }
        return sb.length() == 0 ? "No stored messages." : sb.toString();
    }

    /** 2b. Longest message in the Stored Messages array ("" if there are none). */
    public String longestMessage() {
        String longest = "";
        for (String m : storedMessages) {
            if (m.length() > longest.length()) {
                longest = m;
            }
        }
        return longest;
    }

    /** 2c. Returns {recipient, message} for a message ID, or null if not found. */
    public String[] findByMessageID(String id) {
        int i = messageIds.indexOf(id);
        if (i < 0) {
            return null;
        }
        return new String[]{recipientNumber.get(i), messages.get(i)};
    }

    /** 2d. All sent or stored messages for a recipient. */
    public List<String> findByRecipient(String recipient) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < recipientNumber.size(); i++) {
            String status = messageStatus.get(i);
            if (recipientNumber.get(i).equals(recipient)
                    && (status.equals("Sent") || status.equals("Stored"))) {
                result.add(messages.get(i));
            }
        }
        return result;
    }

    /** 2e. Deletes a message using its hash and removes it from every array. */
    public String deleteMessageByHash(String hash) {
        int i = -1;
        for (int k = 0; k < msgHash.size(); k++) {
            if (msgHash.get(k).equalsIgnoreCase(hash)) {
                i = k;
                break;
            }
        }
        if (i < 0) {
            return "Message hash not found.";
        }

        String text = messages.get(i);
        String status = messageStatus.get(i);

        switch (status) {
            case "Sent" -> {
                sentMessages.remove(text);
                numMessageSent--;
            }
            case "Stored" -> storedMessages.remove(text);
            default -> disregardedMessages.remove(text);
        }

        messageIds.remove(i);
        recipientNumber.remove(i);
        messages.remove(i);
        msgHash.remove(i);
        messageStatus.remove(i);

        return "Message: \"" + text + "\" successfully deleted.";
    }

    /** 2f. Full details of all stored messages. */
    public String displayStoredReport() {
        StringBuilder sb = new StringBuilder("===== Stored Messages Report =====\n");
        boolean any = false;
        for (int i = 0; i < messageStatus.size(); i++) {
            if (messageStatus.get(i).equals("Stored")) {
                any = true;
                sb.append("Message ID  : ").append(messageIds.get(i)).append("\n")
                        .append("Message Hash: ").append(msgHash.get(i)).append("\n")
                        .append("Recipient   : ").append(recipientNumber.get(i)).append("\n")
                        .append("Message     : ").append(messages.get(i)).append("\n")
                        .append("-----------------------------------\n");
            }
        }
        if (!any) {
            sb.append("No stored messages.\n");
        }
        return sb.toString();
    }

    /** Report of the sent messages: hash, recipient and message. */
    public String displaySentReport() {
        StringBuilder sb = new StringBuilder("===== Sent Messages Report =====\n");
        for (int i = 0; i < messageStatus.size(); i++) {
            if (messageStatus.get(i).equals("Sent")) {
                sb.append("Message Hash: ").append(msgHash.get(i)).append("\n")
                        .append("Recipient   : ").append(recipientNumber.get(i)).append("\n")
                        .append("Message     : ").append(messages.get(i)).append("\n")
                        .append("-----------------------------------\n");
            }
        }
        return sb.toString();
    }

    // =====================================================================
    //  PART 3 – JSON storage
    // =====================================================================

    /** Writes every stored message to messages.json (called after storing/deleting). */
    public void saveStoredMessages() {
        try {
            saveStoredMessagesToJson(JSON_FILE);
        } catch (IOException e) {
            System.out.println("Could not write " + JSON_FILE + ": " + e.getMessage());
        }
    }

    /** Reads messages.json into the Stored Messages array (called at start-up). */
    public int loadStoredMessages() {
        try {
            return loadStoredMessagesFromJson(JSON_FILE);
        } catch (IOException e) {
            System.out.println("Could not read " + JSON_FILE + ": " + e.getMessage());
            return 0;
        }
    }

    public void saveStoredMessagesToJson(String file) throws IOException {
        StringBuilder sb = new StringBuilder("[\n");
        boolean first = true;
        for (int i = 0; i < messageStatus.size(); i++) {
            if (!messageStatus.get(i).equals("Stored")) {
                continue;
            }
            if (!first) {
                sb.append(",\n");
            }
            first = false;
            sb.append("  {\"messageID\": \"").append(escape(messageIds.get(i)))
                    .append("\", \"messageHash\": \"").append(escape(msgHash.get(i)))
                    .append("\", \"recipient\": \"").append(escape(recipientNumber.get(i)))
                    .append("\", \"message\": \"").append(escape(messages.get(i))).append("\"}");
        }
        sb.append("\n]\n");
        Files.write(Paths.get(file), sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    /** Returns how many messages were loaded (0 if the file does not exist yet). */
    public int loadStoredMessagesFromJson(String file) throws IOException {
        Path path = Paths.get(file);
        if (!Files.exists(path)) {
            return 0;
        }
        String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        int added = 0;
        for (Map<String, String> obj : parseJsonArray(json)) {
            String id = obj.get("messageID");
            String recipient = obj.get("recipient");
            String text = obj.get("message");
            if (id == null || recipient == null || text == null || messageIds.contains(id)) {
                continue;
            }
            String hash = obj.get("messageHash");
            if (hash == null || hash.isEmpty()) {
                hash = createMessageHash(id, messages.size() + 1, text);
            }
            addEntry(id, recipient, text, "Stored", hash);
            added++;
        }
        return added;
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\t", "\\t");
    }

    /** Small parser for a JSON array of flat objects: [{"a":"b"},{"a":"c"}]. */
    private static List<Map<String, String>> parseJsonArray(String s) {
        List<Map<String, String>> list = new ArrayList<>();
        int[] p = {0};
        skipWhitespace(s, p);
        if (p[0] >= s.length() || s.charAt(p[0]) != '[') {
            return list;
        }
        p[0]++;
        while (true) {
            skipWhitespace(s, p);
            if (p[0] >= s.length() || s.charAt(p[0]) == ']') {
                break;
            }
            char c = s.charAt(p[0]);
            if (c == '{') {
                p[0]++;
                list.add(parseObject(s, p));
            } else {
                p[0]++; // comma or stray character
            }
        }
        return list;
    }

    private static Map<String, String> parseObject(String s, int[] p) {
        Map<String, String> map = new HashMap<>();
        while (true) {
            skipWhitespace(s, p);
            if (p[0] >= s.length()) {
                break;
            }
            char c = s.charAt(p[0]);
            if (c == '}') {
                p[0]++;
                break;
            }
            if (c == ',') {
                p[0]++;
                continue;
            }
            String key = readString(s, p);
            skipWhitespace(s, p);
            if (p[0] < s.length() && s.charAt(p[0]) == ':') {
                p[0]++;
            }
            skipWhitespace(s, p);
            String value;
            if (p[0] < s.length() && s.charAt(p[0]) == '"') {
                value = readString(s, p);
            } else {
                int start = p[0];
                while (p[0] < s.length() && ",}".indexOf(s.charAt(p[0])) < 0) {
                    p[0]++;
                }
                value = s.substring(start, p[0]).trim();
            }
            map.put(key, value);
        }
        return map;
    }

    private static String readString(String s, int[] p) {
        StringBuilder sb = new StringBuilder();
        p[0]++; // opening quote
        while (p[0] < s.length() && s.charAt(p[0]) != '"') {
            char c = s.charAt(p[0]++);
            if (c == '\\' && p[0] < s.length()) {
                char n = s.charAt(p[0]++);
                switch (n) {
                    case 'n' -> sb.append('\n');
                    case 't' -> sb.append('\t');
                    case 'u' -> {
                        sb.append((char) Integer.parseInt(s.substring(p[0], p[0] + 4), 16));
                        p[0] += 4;
                    }
                    default -> sb.append(n);
                }
            } else {
                sb.append(c);
            }
        }
        p[0]++; // closing quote
        return sb.toString();
    }

    private static void skipWhitespace(String s, int[] p) {
        while (p[0] < s.length() && Character.isWhitespace(s.charAt(p[0]))) {
            p[0]++;
        }
    }

    // =====================================================================
    //  Getters (used by the unit tests)
    // =====================================================================

    public ArrayList<String> getSentMessages() { return sentMessages; }
    public ArrayList<String> getDisregardedMessages() { return disregardedMessages; }
    public ArrayList<String> getStoredMessages() { return storedMessages; }
    public ArrayList<String> getMessageHashes() { return msgHash; }
    public ArrayList<String> getMessageIDs() { return messageIds; }

}//end of class