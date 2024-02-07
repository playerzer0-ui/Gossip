package daos;

import business.Message;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class MessageDao extends Dao {

    public MessageDao(String dbName) {
        super(dbName);
    }

    public MessageDao(Connection con) {
        super(con);
    }

    /**
     * get the message by ID
     * @param messageId the messageId
     * @return Message
     */
    public Message getMessage(int messageId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Message m = null;
        try {
            con = getConnection();

            String query = "Select * from messages where messageId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, messageId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String[] dateTimeSent = rs.getString("timeSent").split(" ");
                String[] dateSent = dateTimeSent[0].split("-");
                String[] timeSent = dateTimeSent[1].split(":");
                LocalDateTime localDateTime = LocalDateTime.of(Integer.parseInt(dateSent[0]), Integer.parseInt(dateSent[1]), Integer.parseInt(dateSent[2]), Integer.parseInt(timeSent[0]), Integer.parseInt(timeSent[1]), Integer.parseInt(timeSent[2]));
                m = new Message(rs.getInt("messageId"), rs.getInt("inboxId"), rs.getInt("senderId"), rs.getString("message"), rs.getInt("messageType"), localDateTime, rs.getInt("deletedState"));

            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the getMessage() method: " + e.getMessage());
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
                System.out.println("Exception occurred in the final section of the getMessage() method: " + e.getMessage());
            }
        }
        return m;
    }

    /**
     * sends a message to a person or group, depending on the inboxId
     * @param inboxId the inboxId
     * @param senderId the sender based by ID
     * @param message the message written
     * @param messageType what type is it, 1 = words, 2 = image, 3 = video, 4 = document file
     * @return
     */
    public boolean sendMessage(int inboxId, int senderId, String message, int messageType) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        boolean state = false;
        try {

            con = getConnection();
            String command = "insert into messages (inboxId,senderId,message,messageType) values (?,?,?,?) ";
            ps = con.prepareStatement(command);
            ps.setInt(1, inboxId);
            ps.setInt(2, senderId);
            ps.setString(3, message);
            ps.setInt(4, messageType);
            rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                state = true;
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the sendMessage() method: " + e.getMessage());
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
                System.out.println("Exception occurred in the final section of the sendMessage() method: " + e.getMessage());
            }
        }
        return state;
    }

    /**
     * get all messages that are not deleted
     * @param inboxId the inboxId
     * @return all messages on the inbox
     */
    public ArrayList<Message> getMessages(int inboxId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Message> messages = new ArrayList();
        try {
            con = getConnection();

            String query = "Select * from messages where inboxId=? and deletedState=0";
            ps = con.prepareStatement(query);
            ps.setInt(1, inboxId);
            rs = ps.executeQuery();

            while (rs.next()) {
                String[] dateTimeSent = rs.getString("timeSent").split(" ");
                String[] dateSent = dateTimeSent[0].split("-");
                String[] timeSent = dateTimeSent[1].split(":");
                LocalDateTime localDateTime = LocalDateTime.of(Integer.parseInt(dateSent[0]), Integer.parseInt(dateSent[1]), Integer.parseInt(dateSent[2]), Integer.parseInt(timeSent[0]), Integer.parseInt(timeSent[1]), Integer.parseInt(timeSent[2]));
                Message m = new Message(rs.getInt("messageId"), rs.getInt("inboxId"), rs.getInt("senderId"), rs.getString("message"), rs.getInt("messageType"), localDateTime, rs.getInt("deletedState"));

                messages.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the getMessages() method: " + e.getMessage());
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
                System.out.println("Exception occurred in the final section of the getMessages() method: " + e.getMessage());
            }
        }
        return messages;
    }

}
