package daos;

import business.Blockedusers;
import business.InboxParticipants;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class BlockedusersDao extends Dao implements BlockedusersDaoInterface{
    public BlockedusersDao(String databaseName) {
        super(databaseName);
    }

    public BlockedusersDao(Connection con) {
        super(con);
    }

    /**
     * addBlockUser method let user able to block other user.
     *
     * @param userId is user's id who want to block other user
     * @param blockedId is other user's id who blocked by user
     *
     * @return an int if added block else added block fail will return -1
     */
    @Override
    public int addBlockUser(int userId, int blockedId) {
        int rowsAffected = -1;

        try{
            con = getConnection();

            String query = "INSERT INTO blockedusers (userId, blockedId) VALUES (?, ?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, blockedId);

            rowsAffected = ps.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the addBlockUser() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the addBlockUser() final method:");
        }
        return rowsAffected;
    }

    /**
     * gets all BlockedUsers for a particular user
     *
     * @param userId, takes in the userId
     * @return an arrayList of BlockedUsers
     **/
    public ArrayList<Blockedusers> getBlockedUsers(int userId) {
        ArrayList<Blockedusers> blockedusers = new ArrayList();
        try {
            con = getConnection();

            String query = "Select * from blockedusers where blockedId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Blockedusers blockeduser = new Blockedusers(rs.getInt("userId"),rs.getInt("blockedId"));
                blockedusers.add(blockeduser);
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the getBlockedUsers() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the getBlockedUsers() method: ");
        }
        return blockedusers;
    }

    /**
     * deleteBlockUser method able to delete the blocked user' id .
     *
     * @param blockedId is the blocked user's id
     *
     * @return an int after deleted else return 0 when no rows are affected by the deleted.
     */
    @Override
    public int deleteBlockUser(int blockedId) {
        return deleteItem(blockedId, "blockedusers", "blockedId");
    }

    @Override
    public Blockedusers checkBlock(int userId, int blockedId) {
        Blockedusers bUser = null;
        try {
            con = getConnection();

            String query = "Select * from blockedUsers where userId=? and blockedId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, blockedId);
            rs = ps.executeQuery();

            if (rs.next()) {
                bUser = new Blockedusers(rs.getInt("userId"), rs.getInt("blockId"));
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the checkBlock() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the checkBlock() final method: ");
        }
        return bUser;
    }

}
