package daos;

import business.Stories;
import business.Users;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersDaoIsolationTest {

    /**
     * Isolation register_4 args test
     **/
    @Test
    void register_4args_SuccessfulRegistration() throws SQLException {

        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        PreparedStatement checkPs = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        ResultSet checkRs = mock(ResultSet.class);

        // Mock behavior for successful registration
        String checkQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
        when(dbConn.prepareStatement(checkQuery)).thenReturn(checkPs);
        when(checkPs.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false); // Simulate no duplicate email found

        String insertQuery = "INSERT INTO users(email, userName, profilePicture, password, dateOfBirth, userType, suspended, bio, online) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        when(dbConn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1); // Simulate successful execution
        when(ps.getGeneratedKeys()).thenReturn(checkRs);
        when(checkRs.next()).thenReturn(true, false);
        when(checkRs.getInt(1)).thenReturn(123); // Simulate generated user ID

        UsersDao usersDao = new UsersDao(dbConn);
        int result = usersDao.Register("jo@gmail.com", "lily", "default.png", "password", LocalDate.now(), 1, 0, "", 0);

        assertEquals(123, result); // Check if Register method returns generated user ID
    }



    /**
     * Isolation register_4 args with duplicateUsername test
     **/
    @Test
    void register_4args_FailwithDuplicateUsername() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        PreparedStatement checkPs = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        ResultSet checkRs = mock(ResultSet.class);

        // Mock behavior for successful registration
        String checkQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
        when(dbConn.prepareStatement(checkQuery)).thenReturn(checkPs);
        when(checkPs.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false); // Simulate no duplicate email found

        String insertQuery = "INSERT INTO users(email, userName, profilePicture, password, dateOfBirth, userType, suspended, bio, online) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        when(dbConn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(-1); // Simulate successful execution
        when(ps.getGeneratedKeys()).thenReturn(checkRs);
        when(checkRs.next()).thenReturn(true, false);
        when(checkRs.getInt(1)).thenReturn(-1); // Simulate generated user ID

        UsersDao usersDao = new UsersDao(dbConn);
        int result = usersDao.Register("jo@gmail.com", "joseph", "default.png", "password", LocalDate.now(), 1, 0, "", 0);

        assertEquals(-1, result); // Check if Register method returns generated user ID
    }


    /**
     * Isolation register_4 args with duplicateEmail test
     **/
    @Test
    void register_4args_FailwithDuplicateEmail() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        // Assume email already exists
        when(dbConn.prepareStatement("SELECT COUNT(*) FROM users WHERE email = ?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(1); // Email count is greater than 0

        // Call the method being tested
        LocalDate dOfBirth = LocalDate.of(1989, 4, 19);
        UsersDao usersDao = new UsersDao(dbConn);
        int result = usersDao.Register("joe@gmail.com", "lily", "default.png", "password", dOfBirth, 1, 0, "", 0);

        // Verify that the method returned -2 for duplicate email
        assertEquals(-2, result);
    }


    /**
     * Isolation getUserById_Found test
     **/
    @Test
    void getUserById_Found() throws SQLException {

        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        LocalDate dOfBirth = LocalDate.of(2000,8, 2 );
        Users u = new Users("joe@gmail.com", "joseph", "default.png", "$2a$10$rJf3amWgGq0g5AQ90XCPq.1oASojmit/aOI/W7H9hlOvuEnq7TPqa", dOfBirth, 1, 0, "", 0);
        when(dbConn.prepareStatement("SELECT * FROM USERS WHERE userID = ?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, true, false);
        when(rs.getString("email")).thenReturn(u.getEmail());
        when(rs.getString("userName")).thenReturn(u.getUserName());
        when(rs.getString("profilePicture")).thenReturn(u.getProfilePicture());
        when(rs.getString("password")).thenReturn(u.getPassword());
        when(rs.getDate("dateOfBirth")).thenReturn(Date.valueOf(dOfBirth));
        when(rs.getInt("userType")).thenReturn(u.getUserType());
        when(rs.getInt("suspended")).thenReturn(u.getSuspended());
        when(rs.getString("bio")).thenReturn(u.getBio());
        when(rs.getInt("online")).thenReturn(u.getOnline());

        UsersDao usersDao = new UsersDao(dbConn);
        Users actual = usersDao.getUserById(1);
        assertEquals(actual, u);
    }

    /**
     * when details match
     **/
    @Test
    void LoginTest() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        LocalDate dateOfBirth = LocalDate.of(2000, 8, 2);
        Date dob = new Date(2000 - 1900, 7, 2);
        Users u = new Users(1, "joe@gmail.com", "joseph", "default.png", "$2a$10$rJf3amWgGq0g5AQ90XCPq.1oASojmit/aOI/W7H9hlOvuEnq7TPqa", dateOfBirth, 1, 0, "", 0);
        when(dbConn.prepareStatement("Select * from users where email=?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("userId")).thenReturn(u.getUserId());
        when(rs.getString("email")).thenReturn(u.getEmail());
        when(rs.getString("userName")).thenReturn(u.getUserName());
        when(rs.getString("profilePicture")).thenReturn(u.getProfilePicture());
        when(rs.getString("password")).thenReturn(u.getPassword());
        when(rs.getDate("dateOfBirth")).thenReturn(dob);
        when(rs.getInt("userType")).thenReturn(u.getUserType());
        when(rs.getInt("suspended")).thenReturn(u.getSuspended());
        when(rs.getString("bio")).thenReturn(u.getBio());
        when(rs.getInt("online")).thenReturn(u.getOnline());

        UsersDao usersDao = new UsersDao(dbConn);
        Users actual = usersDao.Login("joe@gmail.com", "123");
        assertEquals(actual, u);
        verify(ps).setString(1, "joe@gmail.com");
    }

    /**
     * When password is incorrect
     **/
    @Test
    void LoginTest_IncorrectPassword() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(dbConn.prepareStatement("Select * from users where email=?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(false);

        UsersDao usersDao = new UsersDao(dbConn);
        Users expected = new Users(-5);
        Users actual = usersDao.Login("joe@gmail.com", "444");
        assertEquals(expected, actual);
        verify(ps).setString(1, "joe@gmail.com");
    }

    /**
     * when email doesn't match but password is correct
     **/
    @Test
    void LoginTest_IncorrectEmail() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        when(dbConn.prepareStatement("Select * from users where email=?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(false);

        UsersDao usersDao = new UsersDao(dbConn);
        Users expected = new Users(-5);
        Users actual = usersDao.Login("hey", "123");
        assertEquals(expected, actual);
        verify(ps).setString(1, "hey");
    }
}