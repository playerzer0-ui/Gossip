package daos;

import business.Users;

import java.time.LocalDate;

public interface UsersDaoInterface {

    /**
     * Login method able to let user login.
     * @param uemail is user's email
     * @param pword is user's password
     *
     * @return user's detail
     */
    public Users Login(String uemail, String pword);
    /**
     * Register(with 4 args and 5 default args) method able to register a new user.
     * userID will increase automatic.
     *
     * @param email is user's email
     * @param uname is user's username
     * @param pPicture is user's profile picture
     * @param pword is user's password
     * @param dOBirth is user's date of Birth
     * @param userType is user's type of user, userType set as default 1 for users
     * @param suspended is user's suspended, set as default 0
     * @param bio is user's bio, set as null
     * @param online is to see is user online, set as default 0

     * @return int of user id if added else added fail will return -1
     */
    public int Register(String email, String uname, String pPicture, String pword, LocalDate dOBirth, int userType, int suspended, String bio, int online);
    /**
     * getUserById method able to get user by userId.
     * @param id is the user's id that want to get.
     * @return that userId's user detail
     */
    public Users getUserById(int id);

    /**
     * Register(with Users) method able to register a new user.
     * userID will increase automatic.
     *
     * @param newUser is the new user's detail to be added

     * @return int of user id if added else added fail will return -1
     */
    public int Register(Users newUser);
}
