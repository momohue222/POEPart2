/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package  poepart2;

 
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Message {

    ArrayList<String> messageIds = new ArrayList<>();
    ArrayList<String> recipientNumber = new ArrayList<>();
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<String> msgHash = new ArrayList<>();
    ArrayList<String> messageStatus = new ArrayList<>();
     
    Scanner input = new Scanner(System.in);
    Random random = new Random();
   
    int numMessageSent = 0;

    public void sendingMessages() {
        // System.out.println("Testing");

        System.out.println("How many messages do you want to send?");
        int numberOfMessages = input.nextInt();

        input.nextLine();

        for (int i = 1; i <= numberOfMessages; i++) {

            System.out.println("MESSAGE: " + i);

            String messageID = generateMessageID();

            System.out.println("Enter recipient  cell number!!");
            String recipient = input.nextLine();

            String recipientResult = checkRecipientCell(recipient);

            if (!recipientResult.equals("Valid number")) {
                System.out.println(recipientResult);
                i = i - 1;
                continue;
            }
            recipientNumber.add(recipient);

            System.out.println("Enter your message!!");
            String messageText = input.nextLine();

            if (messageText.length() > 250
                    || messageText.trim().isEmpty()) {
                System.out.println("Your message length should be between 1 to 250");
                i = i - 1;
                continue;
            }

            messages.add(messageText);

            String messageHash = createMessageHash(messageID,
                    messages.size(), messageText);

            msgHash.add(messageHash);
             
           
            String status = SentMessage();
           
            messageStatus.add(status);
           
            if (status.equals("sent")){
                numMessageSent++;
            }
             
        }
       
        printMessages();
       
        System.out.println("Total messages sent:" + returnTotalMessages());
         
    }
   
    public int returnTotalMessages(){
       
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
   

    public String generateMessageID() {

        String messageID;

        do {
            long number = 1000000000L + random.nextLong(9000000000L);

            messageID = String.valueOf(number);

        } while (messageIds.contains(messageID));

        messageIds.add(messageID);

        return messageID;
    }
   
     /*for (int i = 0; i < messages.size(); i++) {
            System.out.println("\n=====Message details=====");
            System.out.println("Mesage Id:" + messageIds.get(i));
            System.out.println("Recipient:" + recipientNumber.get(i));
            System.out.println("Message:" + messages.get(i));
            System.out.println("Messages Has:" + msgHash.get(i));

        }*/

    public String checkRecipientCell(String cellphone) {
        if (cellphone.matches("^\\+27[0-9]{9}$")
                || cellphone.matches("^0[0-9]{9}$")) {

            return "Valid number";

        } else {

            return "Invalid number. Please enter a valid South African number.";
        }
    }
   
    public String SentMessage(){
       
        System.out.println("\nChoose what you what do with this message");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");
       
        System.out.println("Enter your choice");
        int choice = input.nextInt();
       
       
        switch(choice){
           
            case 1 -> {
                System.out.println("Message successfully sent");
                return "Sent";
            }
           
            case 2 ->{
                System.out.println("Press 0 to delete the messasge");
                int deleteChoice = input.nextInt();
               
                input.nextLine();
               
                if(deleteChoice == 0){
                    System.out.println("Message deleted");
                   
                    int lastIndex = messages.size() - 1;
                   
                    messageIds.remove(lastIndex);
                    recipientNumber.remove(lastIndex);
                    messageIds.remove(lastIndex);
                    msgHash.remove(lastIndex);
                   
                    return "Disregarded";
                   
                }else{
                    System.out.println("Invalid option!");
                    return "Disregarded";
                }
        }
           
            case 3 -> {
                 
                System.out.println("Message successfully stored");
                return"Stored";
            }
           
            default ->{
                System.out.println("Invalid option");
                return "Disregarded";
            }
        }
       
        }
    public String printMessages(){
        StringBuilder allMessages = new StringBuilder();
       
        System.out.println("\n=====All Messages=====");
       
        for(int i = 0; i < messages.size(); i++){
            allMessages.append("\nMessage Number:").append(i + 1);
            allMessages.append("\nMessage ID:").append(messageIds.get(i));
            allMessages.append("\nRecipient:").append(recipientNumber.get(i));
            allMessages.append("\nMessage :").append( messages.get(i));
            allMessages.append("\nMessage Hash:").append(msgHash.get(i));
            allMessages.append("\nStatus:").append(messageStatus.get(i));
           
             System.out.println("\nMessage Number:" + (i + 1));
            System.out.println("Mesage Id:" + messageIds.get(i));
            System.out.println("Recipient:" + recipientNumber.get(i));
            System.out.println("Message:" + messages.get(i));
            System.out.println("Messages Has:" + msgHash.get(i));
        }
       
        return allMessages.toString();
        
    }
   
}//end of class