package daos;

import business.Inbox;

import java.sql.*;

public class InboxDao extends Dao {

    public InboxDao(String dbName) {
        super(dbName);
    }

    public InboxDao(Connection con) {
        super(con);
    }

    /**
     * creates a group chat
     *
     * @param adminId,   the group admins id or the person that created the group's id
     * @param groupName, the groups name
     * @return the groups id
     **/
    public int createGroupChat(int adminId, String groupName) {
        int rowsAffected = 0;
        int id = 0;
        try {

            con = getConnection();
            String command = "insert into inbox (inboxType,adminId,groupName) values (?,?,?)";
            ps = con.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, 2);
            ps.setInt(2, adminId);
            ps.setString(3, groupName);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the inbox() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the inbox() method: ");
        }
        return id;
    }

    /**
     * updates an inbox
     *
     * @param inbox, the inbox to be updated with the already updates details
     * @return true if the update was successful and false if it didn't update
     **/
    public Boolean updateInbox(Inbox inbox) {
        int rowsAffected = 0;
        Boolean state = false;
        try {

            con = getConnection();
            String command = "update inbox set inboxType=? ,adminId=?, groupName=?, groupProfilePicture=? where inboxId=?";
            ps = con.prepareStatement(command);
            ps.setInt(1, inbox.getInboxType());
            ps.setInt(2, inbox.getAdminId());
            ps.setString(3, inbox.getGroupName());
            ps.setString(4, inbox.getGroupProfilePicture());
            ps.setInt(5, inbox.getInboxId());
            rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                state = true;
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the updateInbox() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the updateInbox() method: ");
        }
        return state;
    }

    public int createNormalInbox() {
        //int rowsAffected = 0;
        int id = 0;
        try {

            con = getConnection();
            String command = "insert into inbox (inboxType,groupName) values (?,?)";
            ps = con.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, 1);
            ps.setString(2, "");
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            //ResultSet res = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the createNormalInbox() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the createNormalInbox() method: ");
        }
        return id;
    }

    public Inbox getInbox(int inboxId) {
        Inbox inbox = null;
        try {
            con = getConnection();

            String query = "select * from inbox where inboxId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, inboxId);
            rs = ps.executeQuery();
            if (rs.next()) {
                //String adminId = null;
                int adminId = rs.getInt("adminId");
                if (adminId != 0) {
                    inbox = new Inbox(rs.getInt("inboxId"), rs.getInt("inboxType"), rs.getInt("adminId"), rs.getString("groupName"), rs.getString("groupProfilePicture"));
                } else {
                    inbox = new Inbox(rs.getInt("inboxId"), rs.getInt("inboxType"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in  the getInbox() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in  the getInbox() method:  ");
        }
        return inbox;
    }
}
