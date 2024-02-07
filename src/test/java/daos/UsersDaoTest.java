package daos;

import business.Users;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UsersDaoTest {
/**when details match**/
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

    @Test
    void register_4args() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("register_4args");
        String userName = "Cherry";
        String email = "cherry10@gmail.com";
        String password = "password";
        LocalDate dOfBirth = LocalDate.of(1992,8,11);

        int result = usersDao.Register(email,userName,"default.png",password,dOfBirth,1,0,"",0);
        assertTrue((result > 0));
    }

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
        assertTrue((result > 0));

    }

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

    @Test
    void getUserById_NotFound() {
        UsersDao usersDao = new UsersDao("gossiptest");
        System.out.println("getUserById_NotFound");
        int userID = 101;
        Users expResult = null;
        Users result = usersDao.getUserById(userID);
        assertEquals(expResult, result);
    }

}