package miscellaneous;

import business.Users;

public class Miscellaneous {

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

}
