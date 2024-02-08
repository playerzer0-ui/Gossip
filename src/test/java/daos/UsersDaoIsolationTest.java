package daos;

import business.Users;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersDaoIsolationTest {

    /**
     * Isolation register_4 args test
     **/
    @Test
    void register_4args() throws SQLException {

        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        String query ="Insert into users(email,userName,profilePicture,password,dateOfBirth,userType,suspended,bio,online)values(?,?,?,?,?,?,?,?,?)";
        when(dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);
        when(ps.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt(1)).thenReturn(1);

        LocalDate dOfBirth = LocalDate.of(1999, 12, 1);
        UsersDao usersDao = new UsersDao(dbConn);
        int result = usersDao.Register("lily@gmail.com", "Lily", "default.png", "password", dOfBirth, 1, 0, "", 0);

        verify(ps).setString(1, "lily@gmail.com");
        verify(ps).setString(2, "Lily");
        verify(ps).setString(3, "default.png");
        verify(ps).setString(eq(4), anyString());
        verify(ps).setDate(5, Date.valueOf(dOfBirth));
        verify(ps).setInt(6, 1);
        verify(ps).setInt(7, 0);
        verify(ps).setString(8, "");
        verify(ps).setInt(9, 0);

        assertTrue((result > 0));

    }

    /**
     * Isolation register_4 args with duplicateUsername test
     **/
    @Test
    void register_4args_FailwithDuplicateUsername() throws SQLException {

        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        String query ="Insert into users(email,userName,profilePicture,password,dateOfBirth,userType,suspended,bio,online)values(?,?,?,?,?,?,?,?,?)";
        when(dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(-1);
        when(ps.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt(1)).thenReturn(-1);

        LocalDate dOfBirth = LocalDate.of(1989, 4, 19);
        UsersDao usersDao = new UsersDao(dbConn);
        int result = usersDao.Register("lily@gmail.com", "joseph", "default.png", "password", dOfBirth, 1, 0, "", 0);

        verify(ps).setString(1, "lily@gmail.com");
        verify(ps).setString(2, "joseph");
        verify(ps).setString(3, "default.png");
        verify(ps).setString(eq(4), anyString());
        verify(ps).setDate(5, Date.valueOf(dOfBirth));
        verify(ps).setInt(6, 1);
        verify(ps).setInt(7, 0);
        verify(ps).setString(8, "");
        verify(ps).setInt(9, 0);

        assertTrue((result == -1));

    }

    /**
     * Isolation register_4 args with duplicateEmail test
     **/
    @Test
    void register_4args_FailwithDuplicateEmail() throws SQLException {

        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        String query ="Insert into users(email,userName,profilePicture,password,dateOfBirth,userType,suspended,bio,online)values(?,?,?,?,?,?,?,?,?)";
        when(dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(-1);
        when(ps.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt(1)).thenReturn(-1);

        LocalDate dOfBirth = LocalDate.of(1989, 4, 19);
        UsersDao usersDao = new UsersDao(dbConn);
        int result = usersDao.Register("joe@gmail.com", "lily", "default.png", "password", dOfBirth, 1, 0, "", 0);

        verify(ps).setString(1, "joe@gmail.com");
        verify(ps).setString(2, "lily");
        verify(ps).setString(3, "default.png");
        verify(ps).setString(eq(4), anyString());
        verify(ps).setDate(5, Date.valueOf(dOfBirth));
        verify(ps).setInt(6, 1);
        verify(ps).setInt(7, 0);
        verify(ps).setString(8, "");
        verify(ps).setInt(9, 0);

        assertEquals(-1, result);

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
        Users expected = null;
        Users actual = usersDao.Login("joe@gmail.com", "444");
        assertEquals(actual, expected);
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
        LocalDate dateOfBirth = LocalDate.of(2000, 8, 2);
        Date dob = new Date(2000 - 1900, 7, 2);
        Users u = new Users(1, "joe@gmail.com", "joseph", "default.png", "$2a$10$rJf3amWgGq0g5AQ90XCPq.1oASojmit/aOI/W7H9hlOvuEnq7TPqa", dateOfBirth, 1, 0, "", 0);
        when(dbConn.prepareStatement("Select * from users where email=?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(false);
//        when(rs.getInt("userId")).thenReturn(u.getUserId());
//        when(rs.getString("email")).thenReturn(u.getEmail());
//        when(rs.getString("userName")).thenReturn(u.getUserName());
//        when(rs.getString("profilePicture")).thenReturn(u.getProfilePicture());
//        when(rs.getString("password")).thenReturn(u.getPassword());
//        when(rs.getDate("dateOfBirth")).thenReturn(dob);
//        when(rs.getInt("userType")).thenReturn(u.getUserType());
//        when(rs.getInt("suspended")).thenReturn(u.getSuspended());
//        when(rs.getString("bio")).thenReturn(u.getBio());
//        when(rs.getInt("online")).thenReturn(u.getOnline());

        UsersDao usersDao = new UsersDao(dbConn);
        Users expected = null;
        Users actual = usersDao.Login("hey", "123");
        assertEquals(expected, actual);
        verify(ps).setString(1, "hey");

    }
}