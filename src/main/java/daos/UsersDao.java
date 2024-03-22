package daos;

import business.Message;
import business.Search;
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

            // If email is unique, proceed with the insert
            String insertQuery ="INSERT INTO users(email, userName, profilePicture, password, dateOfBirth, userType, suspended, bio, online) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.setString(2, uname);
            ps.setString(3, pPicture);
            ps.setString(4, hashPassword);
            ps.setDate(5, Date.valueOf(dOBirth));
            ps.setInt(6, userType);
            ps.setInt(7, suspended);
            ps.setString(8, bio);
            ps.setInt(9, online);

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                newId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("\tA problem occurred during the register method:");
            System.err.println("\t" + e.getMessage());
        } finally {
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
                String category = rs.getString("searchCategory");
                u = new Users(userId, email, username, profilePicture, password, dateOfBirth, userType, suspended,bio,online,category);
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
     * getAllUsers method able to list out all user.
     *
     * @return a list of User
     */
    @Override
    public List<Users> getAllUsers() {
        List<Users> users = new ArrayList<Users>();

        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM USERS";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next())
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
                Users u = new Users(userId, email, username, profilePicture, password,dateOfBirth,userType,suspended,bio,online);
                users.add(u);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getAllUsers() method: " + e.getMessage());
        }
        finally
        {
            freeConnection("An error occurred when shutting down the getAllUsers() method: ");
        }
        return users;
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
            freeConnectionUpdate("An error occurred when shutting down the changePassword() method:");
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
        List<Users> users = new ArrayList<>();

        try{

            con = this.getConnection();

            String query = "SELECT * FROM USERS WHERE userName LIKE ? LIMIT 10";
            ps = con.prepareStatement(query);
            ps.setString(1, username+"%");

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
            freeConnection("An error occurred when shutting down the searchUserByUsername() method: ");
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

    /**
     * checkUsername(unique) method able to check username that already register.
     *
     * @param uname is the user's name that want to be checked.
     * @return true when the username is register else return false .
     */
    @Override
    public boolean checkUsername(String uname) {
        boolean isPresent = false;
        try {
            con = this.getConnection();
            String query = "SELECT * from users where username=? ";
            ps = con.prepareStatement(query);
            ps.setString(1, uname);

            rs = ps.executeQuery();

            if (rs.next()) {
                isPresent = true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(" A problem occurred during the checkUsername() method:");
        } finally {
            freeConnection("An error occurred when shutting down the checkUsername() method: ");
        }
        return isPresent;
    }

    /**
     * checkEmail(unique) method able to check email that already register.
     *
     * @param email is the user's email that want to be checked.
     * @return true when the email is register else return false .
     */
    @Override
    public boolean checkEmail(String email) {
        boolean isPresent = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.getConnection();
            String query = "SELECT * from users where email=? ";
            ps = con.prepareStatement(query);
            ps.setString(1, email);

            rs = ps.executeQuery();

            if (rs.next()) {
                isPresent = true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("A problem occurred during the checkEmail() method:");
        } finally {
            freeConnection("An error occurred when shutting down the checkEmail() method: ");
        }
        return isPresent;
    }

    /**
     * updateUser method allow user and admin to update their detail.
     * userID will not be able to change.
     *
     * @param u is the user's detail that able to change.
     * @return an int after update else return 0 when no rows are affected by the update.
     */
    @Override
    public int updateUser(Users u) {
        int rowsAffected = 0;
        try {
            con = getConnection();

            String hashPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());

            String command = "UPDATE users SET email=? ,userName=? , profilePicture=? , password=? , dateOfBirth=? , userType=? , suspended=? , bio=? , online=? WHERE userID=?";
            ps = con.prepareStatement(command);
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getUserName());
            ps.setString(3, u.getProfilePicture());
            ps.setString(4, hashPassword);
            ps.setDate(5, Date.valueOf(u.getDateOfBirth()));
            ps.setInt(6, u.getUserType());
            ps.setInt(7, u.getSuspended());
            ps.setString(8, u.getBio());
            ps.setInt(9, u.getOnline());
            ps.setInt(10, u.getUserId());

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("An error occurred in the updateUser() method: " + e.getMessage());
        } finally {
            freeConnectionUpdate("An error occurred when shutting down the updateUser() method: ");
        }
        return rowsAffected;
    }

    public int updateNameAndBio(int userId, String userName, String bio){
        int rowsAffected = 0;
        try {
            con = getConnection();

            String command = "UPDATE users SET userName=?, bio=? WHERE userID=?";
            ps = con.prepareStatement(command);
            ps.setString(1, userName);
            ps.setString(2, bio);
            ps.setInt(3, userId);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("An error occurred in the updateNameAndBio() method: " + e.getMessage());
        } finally {
            freeConnectionUpdate("An error occurred when shutting down the updateNameAndBio() method: ");
        }
        return rowsAffected;
    }

    /**
     * getOnlineUsers method able to list out all online user to admin.
     *
     * @return a list of online user
     */
    @Override
    public int getOnlineUsers() {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        try {
            con = getConnection();

            String command = "SELECT COUNT(*) FROM users WHERE online = 1";

            ps = con.prepareStatement(command);
            rs = ps.executeQuery();

            if(rs.next()){
                count = rs.getInt("count(*)");
            }

        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getOnlineUsers() method: " + e.getMessage());
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
                System.out.println("An error occurred when shutting down the getOnlineUsers() method: " + e.getMessage());
            }
        }
        return count;

//        Connection con = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try
//        {
//            con = this.getConnection();
//
//            String query = "SELECT COUNT(*) FROM users WHERE online = 1";
//            ps = con.prepareStatement(query);
//
//            rs = ps.executeQuery();
//            while (rs.next())
//            {
//                int userId = rs.getInt("userID");
//                String email = rs.getString("email");
//                String username = rs.getString("userName");
//                String profilePicture = rs.getString("profilePicture");
//                String password = rs.getString("password");
//                LocalDate dateOfBirth = rs.getDate("dateOfBirth").toLocalDate();
//                int userType = rs.getInt("userType");
//                int suspended = rs.getInt("suspended");
//                String bio = rs.getString("bio");
//                int online = rs.getInt("online");
//                Users u = new Users(userId, email, username, profilePicture, password,dateOfBirth,userType,suspended,bio,online);
//                users.add(u);
//            }
//        }
//        catch (SQLException e)
//        {
//            System.out.println("An error occurred in the getOnlineUsers() method: " + e.getMessage());
//        }
//        finally
//        {
//            try
//            {
//                if (rs != null)
//                {
//                    rs.close();
//                }
//                if (ps != null)
//                {
//                    ps.close();
//                }
//                if (con != null)
//                {
//                    freeConnection("");
//                }
//            }
//            catch (SQLException e)
//            {
//                System.out.println("An error occurred when shutting down the getOnlineUsers() method: " + e.getMessage());
//            }
//        }
//        return users;
    }

    public List<Search> generalSearch(String word,Users u) {
        /*Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;*/
        List<Search> searchs = new ArrayList<>();

        try{

            con = getConnection();

            String query = "select inbox.inboxId, inbox.groupName, inbox.searchCategory from inbox,inboxparticipants where groupName like ? and inboxparticipants.userId=? and inboxparticipants.inboxId=inbox.inboxId UNION select users.userId, users.userName, users.searchCategory from users WHERE userName like ? and users.userType=1 and userId!=? LIMIT 10";
            //"select inbox.inboxId, inbox.groupName, searchCategory from inbox,inboxparticipants where groupName like ? and inboxparticipants.userId=? and inboxparticipants.inboxId=inbox.inboxId UNION select users.userId, users.userName, searchCategory from users WHERE userName like  ? and users.userType=!2 LIMIT 10";
            ps = con.prepareStatement(query);
            ps.setString(1, word+"%");
            ps.setInt(2,u.getUserId());
            ps.setString(3, word+"%");
            ps.setInt(4,u.getUserId());
            rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String category = rs.getString(3);
                Search s = new Search(id,name,category);
                searchs.add(s);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("A problem occurred during the generalSearch() method: " +  e.getMessage());
        } finally {
            freeConnection("An error occurred when shutting down the generalSearch() method: ");
        }
        return searchs;
    }

}
