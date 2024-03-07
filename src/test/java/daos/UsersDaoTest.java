package daos;

import business.Users;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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

        Users expResult = null;
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
        Users expResult = null;
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
        Users expResult = null;
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
        assertTrue((result == -1));
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

    /**
     * Test of getOnlineUsers_Online method, of class UsersDao.
     */
    @Test
    void getOnlineUsers_Offline(){
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("getOnlineUsers_Offline");

        int onlineNum = usersDao.getOnlineUsers();

        assertEquals(0, onlineNum);
    }

}