package poepart2;

public class registration {

    String firstName;
    String lastName;
    String userName;
    String passWord;
    String cellPhone;

    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        return password != null &&
                password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[^a-zA-Z0-9].*");
    }

    public boolean checkCellPhoneNumber(String cellphone) {
        return cellphone != null && cellphone.matches("^\\+27\\d{9}$");
    }

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

}
