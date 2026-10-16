/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poepart2;

/**
 * Extends registration so a login object has access to the stored
 * username/password from registerUser(), plus the ability to check
 * login credentials and report login status.
 *
 * @author Student
 */
public class login extends registration {

    public boolean LoginUser(String username, String password) {
        return this.userName != null && this.passWord != null &&
                this.userName.equals(username) && this.passWord.equals(password);
    }

    public String returnLoginStatus(boolean status) {
        if (status) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

}