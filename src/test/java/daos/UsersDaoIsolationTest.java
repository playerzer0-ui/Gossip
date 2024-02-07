package daos;

import business.Users;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersDaoIsolationTest {

    @Test
    void login() {
    }

    @Test
    void register_4args() throws SQLException {

//        Users newUser = new Users("lily@gmail.com","Lily","default.png","password",dOfBirth,1,0,"",0);

        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String query = "INSERT INTO users(email, userName, profilePicture, password, dateOfBirth, userType, suspended, bio, online) VALUES (?, ?, ?, ?, ?,?,?,?,?)";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        LocalDate dOfBirth = LocalDate.of(1999,12,1);
        UsersDao usersDao = new UsersDao(dbConn);
        int result = usersDao.Register("lily@gmail.com","Lily","default.png","password",dOfBirth,1,0,"",0);

        verify(ps).setString(1, "lily@gmail.com");
        verify(ps).setString(2, "Lily");
        verify(ps).setString(3, "default.png");
        verify(ps).setString(4, "password");
        verify(ps).setDate(5, Date.valueOf(dOfBirth));
        verify(ps).setInt(6, 1);
        verify(ps).setInt(7, 0);
        verify(ps).setString(8, "");
        verify(ps).setInt(9, 0);

        assertTrue((result > 0));

        //Comment debug
//        when(rs.getString("email")).thenReturn(newUser.getEmail());
//        when(rs.getString("userName")).thenReturn(newUser.getUserName());
//        when(rs.getString("profilePicture")).thenReturn(newUser.getProfilePicture());
//        when(rs.getString("password")).thenReturn(newUser.getPassword());
//        when(rs.getDate("dateOfBirth")).thenReturn(newUser.getDateOfBirth());
//        when(rs.getInt("userType")).thenReturn(newUser.getUserType());
//        when(rs.getInt("suspended")).thenReturn(newUser.getSuspended());
//        when(rs.getString("bio")).thenReturn(newUser.getBio());
//        when(rs.getInt("online")).thenReturn(newUser.getOnline());

//        when(dbConn.prepareStatement(
//        when(ps.executeQuery()).thenReturn(rs);
//        when(rs.next()).thenReturn(true);
    }

    @Test
    void getUserById_Found() {

//        Users users = new Users();
//
//        int userID = 1;
//        LocalDate dOfBirth = LocalDate.of(2000,8, 2 );
//        Users user1 = new Users(1, "joe@gmail.com", "joseph", "default.png", "$2a$10$rJf3amWgGq0g5AQ90XCPq.1oASojmit/aOI/W7H9hlOvuEnq7TPqa",dOfBirth , 1, 0, "", 0);
//
    }

    @Test
    void testRegister() {
    }
}