package daos;

import business.InboxParticipants;
import business.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class InboxParticipantsDao extends Dao {

    public InboxParticipantsDao(String dbName) {
        super(dbName);
    }

    public InboxParticipantsDao(Connection con) {
        super(con);
    }

    /**
     * gets all InboxParticipant for a particular user
     *
     * @param userId, takes in the userId
     * @return an arrayList of InboxParticipant
     **/
    public ArrayList<InboxParticipants> getAllInbox(int userId) {
        ArrayList<InboxParticipants> inboxParticipants = new ArrayList();
        try {
            con = getConnection();

            String query = "Select * from inboxparticipants where userId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                InboxParticipants ibp = new InboxParticipants(rs.getInt("userId"), rs.getInt("inboxId"), rs.getInt("deletedState"), rs.getInt("unseenMessages"), rs.getInt("isOpen"), rs.getTimestamp("timeSent").toLocalDateTime());
                inboxParticipants.add(ibp);
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the getAllInbox() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the getAllInbox() method: ");
        }
        return inboxParticipants;
    }

    /**
     * gets all InboxParticipant by InboxId
     *
     * @param inboxId, the inboxId
     * @return an arrayList of InboxParticipants
     **/
    public ArrayList<InboxParticipants> getAllInboxParticipants(int inboxId) {
        ArrayList<InboxParticipants> inboxParticipants = new ArrayList();
        try {
            con = getConnection();

            String query = "Select * from inboxparticipants where inboxId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, inboxId);
            rs = ps.executeQuery();

            while (rs.next()) {
                InboxParticipants ibp = new InboxParticipants(rs.getInt("userId"), rs.getInt("inboxId"), rs.getInt("deletedState"), rs.getInt("unseenMessages"), rs.getInt("isOpen"), rs.getTimestamp("timeSent").toLocalDateTime());
                inboxParticipants.add(ibp);
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the getAllInbox() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final getAllInbox() method: ");
        }
        return inboxParticipants;
    }

    /***
     * gets the other InboxParticipant for a particular user
     * @param inboxId, the inbox's Id
     * @param userId, the userId
     * @return an InboxParticipant
     * **/
    public InboxParticipants getOtherInboxParticipant(int inboxId, int userId) {
        InboxParticipants ibp = null;
        try {
            con = getConnection();

            String query = "Select * from inboxparticipants where inboxId=? and userId!=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, inboxId);
            ps.setInt(2, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                ibp = new InboxParticipants(rs.getInt("userId"), rs.getInt("inboxId"), rs.getInt("deletedState"), rs.getInt("unseenMessages"), rs.getInt("isOpen"), rs.getTimestamp("timeSent").toLocalDateTime());
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the getOtherInboxParticipant() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the getOtherInboxParticipant() final method: ");
        }
        return ibp;
    }

    /**
     * gets an InboxParticipant By userId and inboxId
     *
     * @param inboxId, the inbox's Id
     * @param userId,  ther user's Id
     **/
    public InboxParticipants getInboxParticipant(int inboxId, int userId) {
        InboxParticipants ibp = null;
        try {
            con = getConnection();

            String query = "Select * from inboxparticipants where inboxId=? and userId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, inboxId);
            ps.setInt(2, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                LocalDateTime localDateTime = rs.getTimestamp("timeSent").toLocalDateTime();
                ibp = new InboxParticipants(rs.getInt("userId"), rs.getInt("inboxId"), rs.getInt("deletedState"), rs.getInt("unseenMessages"), rs.getInt("isOpen"), localDateTime);
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the getOtherInboxParticipant() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the getOtherInboxParticipant() final method: ");
        }
        return ibp;
    }

    /**
     * Opens and closes an inboxParticipant
     *
     * @param inboxId,   the inboxId to be closed or opened
     * @param openState, set to 1 when opening ad set to 0 when closing
     * @return true if the inbox was closed and false for otherwise
     **/
    public boolean openInbox(int inboxId, int userId, int openState) {
        int rowsAffected = 0;
        boolean state = false;
        try {

            con = getConnection();
            String command = "update inboxparticipants set isOpen=? where inboxId=? and userId=?";
            ps = con.prepareStatement(command);
            ps.setInt(1, openState);
            ps.setInt(2, inboxId);
            ps.setInt(3, userId);
            rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                state = true;
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the openInbox() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the openInbox() method: ");
        }
        return state;
    }

    /**
     * increases the unseen messages by 1 for a particular inboxParticipant and user when the inbox is closed
     *
     * @param inboxId, inbox's Id to be updated
     * @param userId,  the userId
     * @return true if the unseenMessages where updated and false for otherwise
     **/

    public boolean updateUnSeenMessages(int inboxId, int userId) {
        int rowsAffected = 0;
        boolean state = false;
        try {

            con = getConnection();
            String command = "update inboxparticipants set unseenMessages=unseenMessages+1 where inboxId=? and userId=? and isOpen=0";
            ps = con.prepareStatement(command);
            ps.setInt(1, inboxId);
            ps.setInt(2, userId);
            rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                state = true;
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the updateUnSeenMessages() method: " + e.getMessage());
        } finally {
            freeConnectionUpdate("Exception occurred in the final section of the updateUnSeenMessages() method: ");
        }
        return state;
    }

    /**
     * Resets the unseenMessages for a particular inboxParticipant and user
     *
     * @param inboxId, inbox's Id to be updated
     * @param userId,  the userId
     * @return true if the unseenMessages where reseted and false for otherwise
     **/
    public boolean resetUnSeenMessages(int inboxId, int userId) {
        int rowsAffected = 0;
        boolean state = false;
        try {

            con = getConnection();
            String command = "update inboxparticipants set unseenMessages=0 where inboxId=? and userId=?";
            ps = con.prepareStatement(command);
            ps.setInt(1, inboxId);
            ps.setInt(2, userId);
            rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                state = true;
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the resetUnSeenMessages() method: " + e.getMessage());
        } finally {
            freeConnectionUpdate("Exception occurred in the final section of the resetUnSeenMessages() method: ");
        }
        return state;
    }

    /**
     * Inserts an InboxParticipant
     *
     * @param inboxId , the inoxId
     * @param userId, the userId
     * @return true if insert was successful and false for otherwise
     **/
    public boolean insertInboxParticipant(int inboxId, int userId) {
        int rowsAffected = 0;
        boolean state = false;
        try {

            con = getConnection();
            String command = "insert into inboxparticipants (inboxId,userId,deletedState,unseenMessages,isOpen) values (?,?,?,?,?)";
            ps = con.prepareStatement(command);
            ps.setInt(1, inboxId);
            ps.setInt(2, userId);
            ps.setInt(3, 0);
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                state = true;
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the insertInboxParticipant() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the insertInboxParticipant() method: ");
        }
        return state;
    }

    /**
     * deletes an InboxParticipant
     *
     * @param inboxId, for the inbox
     * @param userId,  the user's Id
     * @return true if delete was successful and false for otherwise
     **/
    public boolean deleteInboxParticipant(int inboxId, int userId) {
        int rowsAffected = 0;
        boolean state = false;
        try {

            con = getConnection();
            String command = "delete from inboxparticipants where inboxId=? and userId=?";
            ps = con.prepareStatement(command);
            ps.setInt(1, inboxId);
            ps.setInt(2, userId);
            rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                state = true;
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the deleteInboxParticipant() method: " + e.getMessage());
        } finally {
            freeConnectionUpdate("Exception occurred in the final section of the deleteInboxParticipant() method: ");
        }
        return state;
    }

}
