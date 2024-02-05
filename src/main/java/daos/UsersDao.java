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

    public Users Login(String uemail, String pword){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Message m = null;
        Users u = null;
        try {
            con = getConnection();

            String query = "Select * from users where email=? and password=?";
            ps = con.prepareStatement(query);
            ps.setString(1,uemail);
            ps.setString(2,pword);
            rs = ps.executeQuery();

            if (rs.next()) {
               // user = new Users(rs.getInt("userId"),rs.getString("email"),rs.getString("userName"),rs.getString("profilePicture"),rs.getString("password"),rs.getDate("dateOfBirth").toLocalDate(),rs.getInt("userType"),rs.getInt("suspended"),rs.getString("bio"),rs.getInt("online"));
                String password = rs.getString("password");
                if(BCrypt.checkpw(pword, password)){
                    int userId = rs.getInt("userID");
                    String email = rs.getString("email");
                    String username = rs.getString("userName");
                    String profilePicture = rs.getString("profilePicture");
                    Date dateOfBirth = rs.getDate("dateOfBirth");
                    int userType = rs.getInt("userType");
                    int suspended = rs.getInt("suspended");
                    String bio = rs.getString("bio");
                    int online = rs.getInt("online");

                    u = new Users(userId, email, username, password, profilePicture, dateOfBirth.toLocalDate(), userType, suspended, bio,online);
                }
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
        return u;
    }

    public int Register(String email, String uname, String pPicture, String pword, Date dOBirth, int userType, int suspended, String bio, int online) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet generatedKeys = null;
        int newId = -1;
        String hashPassword = BCrypt.hashpw(pword, BCrypt.gensalt());
        try {
            con = this.getConnection();

            String query = "INSERT INTO users(email, userName, profilePicture, password, dateOfBirth, userType, suspended, bio, online) VALUES (?, ?, ?, ?, ?,?,?,?,?)";

            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, email);
            ps.setString(2, uname);
            ps.setString(3,pPicture);
            ps.setString(4, hashPassword);
            ps.setDate(5,dOBirth);
            ps.setInt(6,userType);
            ps.setInt(7,suspended);
            ps.setString(8,bio);
            ps.setInt(9,online);

            ps.executeUpdate();

            generatedKeys = ps.getGeneratedKeys();

            if(generatedKeys.next())
            {
                newId = generatedKeys.getInt(1);
            }
        }
        catch (SQLException e)
        {
            System.err.println("\tA problem occurred during the register method:");
            System.err.println("\t"+e.getMessage());
            newId = -1;
        }
        finally
        {
            try
            {
                if(generatedKeys != null){
                    generatedKeys.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection("");
                }
            }
            catch (SQLException e)
            {
                System.err.println("A problem occurred when closing down the register method:\n" + e.getMessage());
            }
        }
        return newId;
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
