package daos;

import business.Message;
import business.Users;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;

public class UsersDao extends Dao {
    public UsersDao(String dbName) {
        super(dbName);
    }

    public UsersDao(Connection con) {
        super(con);
    }

    public Users Login(String email, String password){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Message m = null;
        Users user=null;
        try {
            con = getConnection();

            String query = "Select * from users where email=? and password=?";
            ps = con.prepareStatement(query);
            ps.setString(1,email);
            ps.setString(2,password);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new Users(rs.getInt("userId"),rs.getString("email"),rs.getString("userName"),rs.getString("profilePicture"),rs.getString("password"),rs.getDate("dateOfBirth").toLocalDate(),rs.getInt("userType"),rs.getInt("suspended"),rs.getString("bio"),rs.getInt("online"));
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the Login() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occurred in the final section of the Login() method: " + e.getMessage());
            }
        }
        return user;
    }

    public Users getUserById(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Users u = null;
        try {
            con = this.getConnection();

            String query = "SELECT * FROM USERS WHERE userID = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next())
            {
                int userId = rs.getInt("userID");
                String email = rs.getString("email");
                String username = rs.getString("userName");
                String profilePicture = rs.getString("profilePicture");
                String password = rs.getString("password");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                int userType = rs.getInt("userType");
                int suspended = rs.getInt("suspended");
                String bio = rs.getString("bio");
                int online = rs.getInt("online");
                u = new Users(userId, email, username, profilePicture, password, dateOfBirth.toLocalDate(), userType, suspended,bio,online);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getUserById() method: " + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                System.out.println("An error occurred when shutting down the getUserById() method: " + e.getMessage());
            }
        }
        return u;
    }
}
