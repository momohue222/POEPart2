/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poepart2;

/**
 *
 * @author Student
 */
public class registration { //start of class

    String firstName;
    String lastName;
    String userName;
    String passWord;
    String cellPhone;

    public boolean checkUserName(String username) { //start of username method
        return username != null && username.contains("_") && username.length() <= 5;
    } //end of username method

    public boolean checkPasswordComplexity(String password) { //start of password method
        return password != null &&
                password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&      // capital letter
                password.matches(".*\\d.*") &&        // numbers
                password.matches(".*[^a-zA-Z0-9].*"); // special character
    } //end of password method

    public boolean checkCellPhoneNumber(String cellphone) { //start of cellphone method
        // South African international code (+27) followed by exactly 9 digits
        // = 11 characters after the '+', 12 characters total.
        return cellphone != null && cellphone.matches("^\\+27\\d{9}$");
    } // end of cellphone method

    public String registerUser(String firstName, String lastName, String username, String password, String cellphone) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your "
                    + "username contains an underscore and is no more than five "
                    + "characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the "
                    + "password contains at least eight characters, a capital "
                    + "letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellphone)) {
            return "Cell phone number incorrectly formatted or does not contain "
                    + "international code.";
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = username;
        this.passWord = password;
        this.cellPhone = cellphone;

        return "Username successfully captured. Password successfully captured. Cell phone number successfully added.";
    }

    public boolean registerStatus(String username, String password, String cellphone) {
        return checkUserName(username) &&
                checkPasswordComplexity(password) &&
                checkCellPhoneNumber(cellphone);
    }

} //end of class