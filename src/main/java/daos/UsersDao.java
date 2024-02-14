package daos;

import business.Message;
import business.Users;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsersDao extends Dao implements UsersDaoInterface{
    public UsersDao(String dbName) {
        super(dbName);
    }

    public UsersDao(Connection con) {
        super(con);
    }

    /**
     * Login method able to let user login.
     * @param uemail is user's email
     * @param pword is user's password
     *
     * @return user's detail
     */
    public Users Login(String uemail, String pword){
        Message m = null;
        Users user=null;
        try {
            con = getConnection();

            String query = "Select * from users where email=?";
            ps = con.prepareStatement(query);
            ps.setString(1,uemail);
            rs = ps.executeQuery();

            if (rs.next()) {

                String password = rs.getString("password");
                if(BCrypt.checkpw(pword, password)){
                    int userId = rs.getInt("userId");
                    String email = rs.getString("email");
                    String username = rs.getString("userName");
                    String profilePicture = rs.getString("profilePicture");
                    LocalDate dateOfBirth = rs.getDate("dateOfBirth").toLocalDate();
                    int userType = rs.getInt("userType");
                    int suspended = rs.getInt("suspended");
                    String bio = rs.getString("bio");
                    int online = rs.getInt("online");

                    user = new Users(userId, email, username, profilePicture, password, dateOfBirth, userType, suspended, bio,online);
                }

            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the Login() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the Login() method: ");
        }
        return user;
    }

    /**
     * Register(with 4 args and 5 default args) method able to register a new user.
     * userID will increase automatic.
     *
     * @param email is user's email
     * @param uname is user's username
     * @param pPicture is user's profile picture
     * @param pword is user's password
     * @param dOBirth is user's date of Birth
     * @param userType is user's type of user, userType set as default 1 for users
     * @param suspended is user's suspended, set as default 0
     * @param bio is user's bio, set as null
     * @param online is to see is user online, set as default 0

     * @return int of user id if added else added fail will return -1
     */
    public int Register(String email, String uname, String pPicture, String pword, LocalDate dOBirth, int userType, int suspended, String bio, int online) {
        int newId = -1;
        String hashPassword = BCrypt.hashpw(pword, BCrypt.gensalt());
        try {
            con = this.getConnection();

            String query ="Insert into users(email,userName,profilePicture,password,dateOfBirth,userType,suspended,bio,online)values(?,?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, email);
            ps.setString(2, uname);
            ps.setString(3,pPicture);
            ps.setString(4, hashPassword);
            ps.setDate(5, Date.valueOf(dOBirth));
            ps.setInt(6,userType);
            ps.setInt(7,suspended);
            ps.setString(8,bio);
            ps.setInt(9,online);

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();

            if(rs.next())
            {
                newId = rs.getInt(1);
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
            freeConnection("A problem occurred when closing down the register method: ");
        }
        return newId;
    }

    /**
     * getUserById method able to get user by userId.
     * @param id is the user's id that want to get.
     * @return that userId's user detail
     */
    public Users getUserById(int id) {
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
                LocalDate dateOfBirth = rs.getDate("dateOfBirth").toLocalDate();
                int userType = rs.getInt("userType");
                int suspended = rs.getInt("suspended");
                String bio = rs.getString("bio");
                int online = rs.getInt("online");
                u = new Users(userId, email, username, profilePicture, password, dateOfBirth, userType, suspended,bio,online);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getUserById() method: " + e.getMessage());
        }
        finally
        {
            freeConnection("An error occurred when shutting down the getUserById() method: ");
        }
        return u;
    }

    /**
     * Register(with Users) method able to register a new user.
     * userID will increase automatic.
     *
     * @param newUser is the new user's detail to be added

     * @return int of user id if added else added fail will return -1
     */
    @Override
    public int Register(Users newUser) {
        return Register(newUser.getEmail(), newUser.getUserName(), newUser.getProfilePicture(), newUser.getPassword(), newUser.getDateOfBirth(), newUser.getUserType(), newUser.getSuspended(), newUser.getBio(), newUser.getOnline());
    }

    /**
     * changePassword method allow user to change new password.
     *
     * @param username is the user's name
     * @param oldPass is the user's old password
     * @param newPass is new password that user's set
     *
     * @return int of user id if added else added fail will return -1
     */
    @Override
    public int changePassword(String username, String oldPass, String newPass) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = -1;
        try {
            con = this.getConnection();

            String query = "UPDATE USERS SET password = ? WHERE userName = ? AND password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, newPass);
            ps.setString(2, username);
            ps.setString(3, oldPass);

            rowsAffected = ps.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("An error occurred in the changePassword() method: " + e.getMessage());
        }
        finally{
            try{
                if (ps != null){
                    ps.close();
                }
                if (con != null){
                    freeConnection(con);
                }
            }
            catch (SQLException e){
                System.out.println("An error occurred when shutting down the changePassword() method: " + e.getMessage());
            }
        }
        return rowsAffected;
    }

    /**
     * searchUserByUsername method let user able to search other user by username .
     *
     * @param username is the user's name to search
     *
     * @return a list of user that contain the search's username or will return null if not contain the search's username.
     */
    @Override
    public List<Users> searchUserByUsername(String username) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Users> users = new ArrayList<>();

        try{

            con = this.getConnection();

            String query = "SELECT * FROM USERS WHERE userName LIKE ?";
            ps = con.prepareStatement(query);
            ps.setString(1, "%"+username+"%");

            rs = ps.executeQuery();
            while (rs.next()){
                int userId = rs.getInt("userID");
                String email = rs.getString("email");
                String uname = rs.getString("userName");
                String profilePicture = rs.getString("profilePicture");
                String password = rs.getString("password");
                LocalDate dateOfBirth = rs.getDate("dateOfBirth").toLocalDate();
                int userType = rs.getInt("userType");
                int suspended = rs.getInt("suspended");
                String bio = rs.getString("bio");
                int online = rs.getInt("online");
                Users u = new Users(userId, email, uname, profilePicture, password,dateOfBirth,userType,suspended,bio,online);
                users.add(u);
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred in the searchUserByUsername() method: " + e.getMessage());
        }
        finally{
            try{
                if (rs != null){
                    rs.close();
                }
                if (ps != null){
                    ps.close();
                }
                if (con != null){
                    freeConnection(con);
                }
            }
            catch (SQLException e){
                System.out.println("An error occurred when shutting down the searchUserByUsername() method: " + e.getMessage());
            }
        }
        return users;
    }

    /**
     * deleteUserById method able to delete user by userId .
     *
     * @param userId is the user's id to delete
     *
     * @return an int after deleted else return 0 when no rows are affected by the deleted.
     */
    @Override
    public int deleteUserById(int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        try {
            con = getConnection();

            String command = "DELETE FROM Users WHERE userID=?";
            ps = con.prepareStatement(command);
            ps.setInt(1, userId);

            rowsAffected = ps.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the deleteUserById() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection("");
                }
            } catch (SQLException e)
            {
                System.out.println("An error occurred when shutting down the deleteUserById() method: " + e.getMessage());
            }
        }
        return rowsAffected;
    }


}
