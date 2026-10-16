/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poepart2;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class POEPart2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        login register = new login();
        Scanner scan = new Scanner(System.in);

        System.out.println("\n===== Register =====");

        System.out.print("Please enter the first name: ");
        String firstName = scan.nextLine();

        System.out.print("Please enter the last name: ");
        String lastName = scan.nextLine();

        //Username input and checker
        System.out.print("Please enter the username: ");
        String userName = scan.nextLine();

        //password input and checker
        System.out.print("Enter your password: ");
        String password = scan.nextLine();

        //cellphone number check and input
        System.out.print("Enter your cell phone number: ");
        String cellphone = scan.nextLine();

        String registerMessage = register.registerUser(firstName, lastName, userName, password, cellphone);

        System.out.println(registerMessage);

        if (!register.registerStatus(userName, password, cellphone)) {
            return;
        }

        //Login
        System.out.println("\n===== Login =====");

        System.out.print("Enter your username: ");
        String loginName = scan.nextLine();

        System.out.print("Enter your password: ");
        String loginPassword = scan.nextLine();

        boolean loginStatus = register.LoginUser(loginName, loginPassword);

        while (!loginStatus) {
            System.out.println(register.returnLoginStatus(loginStatus));

            System.out.print("Enter your username: ");
            loginName = scan.nextLine();

            System.out.print("Enter your password: ");
            loginPassword = scan.nextLine();

            loginStatus = register.LoginUser(loginName, loginPassword);
        
        System.out.println(register.returnLoginStatus(loginStatus));
        }
    
        System.out.println("Login complete.");
 


       Message msg = new Message();
       
       int option;
       
       do{
           System.out.println("\n===== Select any of the option below =====");
           System.out.println("1. Send message");
           System.out.println("2. Coming soon");
           System.out.println("3. Exit");
           
           option = scan.nextInt();
           
           switch(option){
               
               case 1 -> msg.sendingMessages();
               case 2 -> System.out.println("Coming soon");
               case 3 -> System.out.println("Good bye love you");
               default -> System.out.println("Invalid option!!");
               
           }
           
       }while(option !=3);
     
    }
}
