package miscellaneous;

import business.Users;

import java.util.regex.Pattern;

public class Miscellaneous {
 private static final String emailRegExpression="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
         + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String passwordRegExpression= "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=*/])"
            + ".{8,20}$";
    /**checks if a user profile picture is the default picture
     * @return true if it's the default and false for otherwise
     * @param u, takes in a user as a parameter
     * **/
    public boolean checkProfilePic(Users u){
        if(u.getProfilePicture().equalsIgnoreCase("default.png")){
            return true;
        }
        return false;
    }
    /***
     * validates the email. It allows numeric values from 0 to 9.
     * Both uppercase and lowercase letters from a to z are allowed.
     *  underscore “_”, hyphen “-“, and dot “.” are allowed
     * Dot isn’t allowed at the start and end of the local part.
     * Consecutive dots aren’t allowed.
     * For the local part, a maximum of 64 characters are allowed.
     * @param email, the email to be validated
     * @return true if email is valid and false for otherwise
     * **/
    public static boolean checkEmail(String email){
        return Pattern.compile(emailRegExpression).matcher(email).matches();
    }
    /**
     * Validates the password, password must contain at least 8 characters and at most 20 characters.
     * must contain at least one digit.
     * must contain at least one upper case alphabet.
     * must contain at least one lower case alphabet.
     * must contain at ledast one special character which includes.
     * @param password, the password to be validated
     *  @return true if email is valid and false for otherwise
     * **/
    public static boolean checkPassword(String password){
        return Pattern.compile(passwordRegExpression).matcher(password).matches();
    }

}
