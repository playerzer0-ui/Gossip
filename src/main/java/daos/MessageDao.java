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
        Message m = null;
        try {
            con = getConnection();

            String query = "Select * from messages where messageId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, messageId);
            rs = ps.executeQuery();

            if (rs.next()) {
                LocalDateTime localDateTime = rs.getTimestamp("timeSent").toLocalDateTime();
                 m = new Message(rs.getInt("messageId"), rs.getInt("inboxId"), rs.getInt("senderId"), rs.getString("message"), rs.getInt("messageType"), localDateTime, rs.getInt("deletedState"),rs.getInt("messageKey"),rs.getString("originalFileName"));

            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the getMessage() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the getMessage() method: ");
        }
        return m;
    }

    /**
     * sends a message to a person or group, depending on the inboxId
     * @param inboxId the inboxId
     * @param senderId the sender based by ID
     * @param message the message written
     * @param messageType what type is it, 1 = words, 2 = image, 3 = video, 4 = document file
     * @return true or false, depends on if the message is sent or not
     */
    public boolean sendMessage(int inboxId, int senderId, String message, int messageType) {
        int rowsAffected;
        boolean state = false;
        try {

            con = getConnection();
            String command = "insert into messages (inboxId,senderId,message,messageType) values (?,?,?,?)";
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
            freeConnection("Exception occurred in the final section of the sendMessage() method: ");
        }
        return state;
    }

    /**
     * sends a message to a person or group, depending on the inboxId
     * @param inboxId the inboxId
     * @param senderId the sender based by ID
     * @param message the message written
     * @param messageType what type is it, 1 = words, 2 = image, 3 = video, 4 = document file
     * @return true or false, depends on if the message is sent or not
     */
    public boolean sendMessage(int inboxId, int senderId, String message, int messageType,int messageKey) {
        int rowsAffected;
        boolean state = false;
        try {

            con = getConnection();
            String command = "insert into messages (inboxId,senderId,message,messageType,messageKey) values (?,?,?,?,?)";
            ps = con.prepareStatement(command);
            ps.setInt(1, inboxId);
            ps.setInt(2, senderId);
            ps.setString(3, message);
            ps.setInt(4, messageType);
            ps.setInt(5, messageKey);
            rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                state = true;
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the sendMessage() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the sendMessage() method: ");
        }
        return state;
    }
    /**
     * sends a message to a person or group, depending on the inboxId
     * @param inboxId the inboxId
     * @param senderId the sender based by ID
     * @param message the message written
     * @param messageType what type is it, 1 = words, 2 = image, 3 = video, 4 = document file
     * @return true or false, depends on if the message is sent or not
     */
    public boolean sendMessage(int inboxId, int senderId, String message, int messageType,int messageKey,String originalFileName) {
        int rowsAffected;
        boolean state = false;
        try {

            con = getConnection();
            String command = "insert into messages (inboxId,senderId,message,messageType,messageKey,originalFileName) values (?,?,?,?,?,?)";
            ps = con.prepareStatement(command);
            ps.setInt(1, inboxId);
            ps.setInt(2, senderId);
            ps.setString(3, message);
            ps.setInt(4, messageType);
            ps.setInt(5, messageKey);
            ps.setString(6, originalFileName);
            rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                state = true;
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the sendMessage() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the sendMessage() method: ");
        }
        return state;
    }
    public int sendMessage2(int inboxId, int senderId, String message, int messageType,int messageKey,String originalFileName) {
        int id=0;
        boolean state = false;
        try {

            con = getConnection();
            String command = "insert into messages (inboxId,senderId,message,messageType,messageKey,originalFileName) values (?,?,?,?,?,?)";
            ps = con.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, inboxId);
            ps.setInt(2, senderId);
            ps.setString(3, message);
            ps.setInt(4, messageType);
            ps.setInt(5, messageKey);
            ps.setString(6, originalFileName);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the sendMessage() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the sendMessage() method: ");
        }
        return id;
    }

    /**
     * get all messages that are not deleted, from a certain inbox
     * @param inboxId the inboxId
     * @return all messages on the inbox
     */
    public ArrayList<Message> getMessages(int inboxId) {
        ArrayList<Message> messages = new ArrayList<>();
        try {
            con = getConnection();

            String query = "Select * from messages where inboxId=? and deletedState=0";
            ps = con.prepareStatement(query);
            ps.setInt(1, inboxId);
            rs = ps.executeQuery();

            while (rs.next()) {
                LocalDateTime localDateTime = rs.getTimestamp("timeSent").toLocalDateTime();
                Message m = new Message(rs.getInt("messageId"), rs.getInt("inboxId"), rs.getInt("senderId"), rs.getString("message"), rs.getInt("messageType"), localDateTime, rs.getInt("deletedState"),rs.getInt("messageKey"),rs.getString("originalFileName"));

                messages.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the getMessages() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the getMessages() method: ");
        }
        return messages;
    }

    /**
     * deletes a message by Id
     * @param messageId the messageId
     * @return number of messages deleted
     */
    public int deleteMessages(int messageId){
        return deleteItem(messageId, "messages", "messageId");
    }


    /**
     * get the amount of messages on a daily
     * @return daily messages count
     */
    public int getDailyMessageCount(){
        int count = 0;

        try{
            con = getConnection();
            String query = "SELECT count(*) FROM messages where DATE(timeSent) = CURDATE()";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            if(rs.next()){
                count = rs.getInt("count(*)");
            }
        }catch (SQLException e) {
            System.out.println("Exception occurred in the getMessages() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the getMessages() method: ");
        }
        return count;
    }


    /**
     * get the total amount of messages
     * @return the total messages of all time
     */
    public int getTotalMessageCount(){
        int count = 0;

        try{
            con = getConnection();
            String query = "SELECT count(*) FROM messages";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            if(rs.next()){
                count = rs.getInt("count(*)");
            }
        }catch (SQLException e) {
            System.out.println("Exception occurred in the getMessages() method: " + e.getMessage());
        } finally {
            freeConnection("Exception occurred in the final section of the getMessages() method: ");
        }
        return count;
    }
}
