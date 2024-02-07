package daos;

import business.Users;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UsersDaoTest {

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

    @Test
    void register() {
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

    @Test
    void testRegister() {
    }
}