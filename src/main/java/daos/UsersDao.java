package daos;

import business.Message;
import business.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
