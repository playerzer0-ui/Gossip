package daos;

import business.Users;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UsersDaoTest {

    /**
     * Test of login_Success() method, of class UsersDao.
     */
    @Test
    void login_Success() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("login_Success");
        String email = "joe@gmail.com";
        String password = "123";
        LocalDate dOfBirth = LocalDate.of(2000,8, 2 );

        Users expResult = new Users(1, "joe@gmail.com", "joseph", "default.png", "$2a$10$rJf3amWgGq0g5AQ90XCPq.1oASojmit/aOI/W7H9hlOvuEnq7TPqa",dOfBirth , 1, 0, "", 0);
        Users result = usersDao.Login(email,password);
        assertEquals(expResult,result);
    }

    /**
     * Test of login_UserCantFound() method, of class UsersDao.
     */
    @Test
    void login_UserCantFound() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("login_UserCantFound");
        String email = "Sherry@gmail.com";
        String password = "password";

        Users expResult = new Users(-5);
        Users result = usersDao.Login(email,password);
        assertEquals(expResult,result);
    }
/**when the email match but the password don't match**/
    @Test
    void login_WhenJustEmailMatch() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("login_UserCantFound");
        String email = "joe@gmail.com";
        String password = "password";
        Users expResult = new Users(-10);
        Users result = usersDao.Login(email,password);
        assertEquals(expResult,result);
    }

    /**when just the password match but the email don't match**/
    @Test
    void login_WhenJustPasswordMatch() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("login_UserCantFound");
        String email = "pau@gmail.com";
        String password = "123";
        Users expResult = new Users(-5);
        Users result = usersDao.Login(email,password);
        assertEquals(expResult,result);
    }

    /**
     * Test of register_4args() method, of class UsersDao.
     */
    @Test
    void register_4args() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("register_4args");
        String userName = "Cherry";
        String email = "cherry10@gmail.com";
        String password = "password";
        LocalDate dOfBirth = LocalDate.of(1992,8,11);

        int result = usersDao.Register(email,userName,"default.png",password,dOfBirth,1,0,"",0);
        usersDao.deleteUserById(6);
        usersDao.updateIncrement("users", 6);

        assertTrue((result > 0));
    }

    /**
     * Test of register_4args_FailwithDuplicateEmail() method, of class UsersDao.
     */
    @Test
    void register_4args_FailwithDuplicateEmail() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("register_4args_FailwithDuplicateEmail");
        String userName = "Gary";
        String email = "joe@gmail.com";
        String password = "password";
        LocalDate dOfBirth = LocalDate.of(1992,8,11);

        int result = usersDao.Register(email,userName,"default.png",password,dOfBirth,1,0,"",0);
        usersDao.updateIncrement("users", 6);
        assertTrue((result == -2));
    }

    /**
     * Test of register_4args_FailwithDuplicateUsername() method, of class UsersDao.
     */
    @Test
    void register_4args_FailwithDuplicateUsername() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("register_4args_FailwithDuplicateUsername");
        String userName = "joseph";
        String email = "neo@gmail.com";
        String password = "password";
        LocalDate dOfBirth = LocalDate.of(1999,7,5);

        int result = usersDao.Register(email,userName,"default.png",password,dOfBirth,1,0,"",0);
        usersDao.updateIncrement("users", 6);
        assertTrue((result == -1));
    }

    /**
     * Test of register_Users() method, of class UsersDao.
     */
    @Test
    void register_Users() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("register_Users");
        String userName = "Marry";
        String email = "marry@gmail.com";
        String pPicture = "default.png";
        String password = "password";
        LocalDate dOfBirth = LocalDate.of(1989,7,1);
        int userType = 1;
        int susp = 0;
        String bio = "";
        int online = 0;

        Users u = new Users(email,userName,pPicture,password,dOfBirth,userType,susp,bio,online);

        int result = usersDao.Register(u);
        usersDao.deleteUserById(6);
        usersDao.updateIncrement("users", 6);

        assertTrue((result > 0));

    }

    /**
     * Test of getUserById_Found() method, of class UsersDao.
     */
    @Test
    void getUserById_Found() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("getUserById_Found");
        int userID = 1;
        LocalDate dOfBirth = LocalDate.of(2000,8, 2 );
        Users expResult = new Users(1, "joe@gmail.com", "joseph", "default.png", "$2a$10$rJf3amWgGq0g5AQ90XCPq.1oASojmit/aOI/W7H9hlOvuEnq7TPqa",dOfBirth , 1, 0, "", 0);
        Users result = usersDao.getUserById(userID);
        assertEquals(expResult, result);
    }

    /**
     * Test of getUserById_NotFound() method, of class UsersDao.
     */
    @Test
    void getUserById_NotFound() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("getUserById_NotFound");
        int userID = 101;
        Users expResult = null;
        Users result = usersDao.getUserById(userID);
        assertEquals(expResult, result);
    }

    @Test
    void getAllUsers() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("getAllUsers");
        ArrayList<Users> result = (ArrayList<Users>) usersDao.getAllUsers();
        assertEquals(5, result.size());
    }

    /**
     * Test of getOnlineUsers_Online method, of class UsersDao.
     */
    @Test
    void getOnlineUsers_Online(){
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("getOnlineUsers_withNumber");

        int onlineNum = usersDao.getOnlineUsers();

        assertEquals(2, onlineNum);
    }

    @Test
    void updateUser(){
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("updateUser");

        int userId = 1;
        String userName = "joseph";
        String email = "joe@gmail.com";
        String password = "123";
        String profilePicture = "default.png";
        LocalDate dOfBirth = LocalDate.of(2000,8,2);
        int userType =1;
        int suspended = 0;
        String bio = "happy";
        int online =1;
        Users u = new Users(userId,email,userName,profilePicture,password,dOfBirth,userType,suspended,bio,online);

        int expResult = 1;
        int result = usersDao.updateUser(u);

        assertEquals(expResult, result);

        if (expResult == result) {

            Users expectedUser = new Users(u.getUserId(), u.getUserName(), u.getEmail(),u.getPassword(),u.getProfilePicture(),u.getDateOfBirth(),u.getUserType(),u.getSuspended(),u.getBio(),u.getOnline());
            Users resultUser = usersDao.getUserById(u.getUserId());
            assertEquals(resultUser, expectedUser);

            usersDao.updateUser(u);
        }
    }

    @Test
    void checkUsername_isPresent() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("checkUsername_isPresent");
        String username = "joseph";
        boolean expResult = true;
        boolean result = usersDao.checkUsername(username);

        assertEquals(expResult, result);

    }

    /**
     * Test of checkUsername_notPresent() method, of class UsersDao.
     */
    @Test
    void checkUsername_notPresent() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("checkUsername_notPresent");
        String username = "Henry";
        boolean expResult = false;

        boolean result = usersDao.checkUsername(username);

        assertEquals(expResult, result);
    }

    /**
     * Test of checkEmail_isPresent() method, of class UsersDao.
     */
    @Test
    void checkEmail_isPresent() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("checkEmail_isPresent");
        String email = "joe@gmail.com";
        boolean expResult = true;

        boolean result = usersDao.checkEmail(email);

        assertEquals(expResult, result);
    }

    /**
     * Test of checkEmail_notPresent() method, of class UsersDao.
     */
    @Test
    void checkEmail_notPresent() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("checkEmail_notPresent");
        String email = "Henry123@gmail.com";
        boolean expResult = false;

        boolean result = usersDao.checkEmail(email);

        assertEquals(expResult, result);
    }

}