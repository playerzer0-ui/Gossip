package daos;

import business.Users;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDaoIsolationTest {
    /**when details match**/
    @Test
    void LoginTest() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        LocalDate dateOfBirth= LocalDate.of(2000,8,2);
        Date dob= new Date(2000 - 1900,7,2);
        Users u=new Users(1,"joe@gmail.com","joseph","default.png","$2a$10$rJf3amWgGq0g5AQ90XCPq.1oASojmit/aOI/W7H9hlOvuEnq7TPqa",dateOfBirth,1,0,"",0);
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

        UsersDao usersDao =new UsersDao(dbConn);
        Users actual =usersDao.Login("joe@gmail.com","123");
        System.out.println(actual.toString());
        assertEquals(actual,u);
    }

    @Test
    void LoginTest_IncorectPassword() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        LocalDate dateOfBirth= LocalDate.of(2000,8,2);
        Date dob= new Date(2000 - 1900,7,2);
        Users u=new Users(1,"joe@gmail.com","joseph","default.png","$2a$10$rJf3amWgGq0g5AQ90XCPq.1oASojmit/aOI/W7H9hlOvuEnq7TPqa",dateOfBirth,1,0,"",0);
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

        UsersDao usersDao =new UsersDao(dbConn);
        Users expected =null;
        Users actual =usersDao.Login("joe@gmail.com","552");
        assertEquals(actual,expected);
    }
}
