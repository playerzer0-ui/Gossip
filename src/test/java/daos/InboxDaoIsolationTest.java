package daos;

import business.Inbox;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class InboxDaoIsolationTest {
 /**when creating a groupChat*/
 @Test
 void createGroupChat() throws SQLException {

  Connection dbConn = mock(Connection.class);
  PreparedStatement ps = mock(PreparedStatement.class);
  ResultSet rs = mock(ResultSet.class);

  String query ="insert into inbox (inboxType,adminId,groupName) values (?,?,?)";
  when(dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)).thenReturn(ps);
  when(ps.executeUpdate()).thenReturn(1);
  when(ps.getGeneratedKeys()).thenReturn(rs);
  when(rs.next()).thenReturn(true, false);
  when(rs.getInt(1)).thenReturn(1);

  InboxDao inboxDao= new InboxDao(dbConn);
  int result = inboxDao.createGroupChat(4,"studyGroup");

  verify(ps).setInt(2, 4);
  verify(ps).setString(3, "studyGroup");

  assertTrue(result>0);
 }


 @Test
 void updateInbox() throws SQLException {

  Connection dbConn = mock(Connection.class);
  PreparedStatement ps = mock(PreparedStatement.class);
  ResultSet rs = mock(ResultSet.class);
  String query = "update inbox set inboxType=? ,adminId=?, groupName=?, groupProfilePicture=? where inboxId=?";
  when(dbConn.prepareStatement(query)).thenReturn(ps);
  when(ps.executeUpdate()).thenReturn(1);
  InboxDao inboxDao= new InboxDao(dbConn);
  Inbox inbox = new Inbox(1,1,1,"fun chat","fun.png","g");
  boolean expected= inboxDao.updateInbox(inbox);
  verify(ps).setInt(1,inbox.getInboxType());
  verify(ps).setInt(2,inbox.getAdminId());
  verify(ps).setString(3,inbox.getGroupName());
  verify(ps).setString(4,inbox.getGroupProfilePicture());
  verify(ps).setInt(5,inbox.getInboxId());
  assertTrue(expected);
 }


 @Test
 void updateInbox_whenNoInboxFound() throws SQLException {
  Connection dbConn = mock(Connection.class);
  PreparedStatement ps = mock(PreparedStatement.class);
  ResultSet rs = mock(ResultSet.class);
  String query = "update inbox set inboxType=? ,adminId=?, groupName=?, groupProfilePicture=? where inboxId=?";
  when(dbConn.prepareStatement(query)).thenReturn(ps);
  when(ps.executeUpdate()).thenReturn(0);
  InboxDao inboxDao= new InboxDao(dbConn);
  Inbox inbox = new Inbox(50,1,1,"fun chat","fun.png","g");
  boolean expected= inboxDao.updateInbox(inbox);
  verify(ps).setInt(1,inbox.getInboxType());
  verify(ps).setInt(2,inbox.getAdminId());
  verify(ps).setString(3,inbox.getGroupName());
  verify(ps).setString(4,inbox.getGroupProfilePicture());
  verify(ps).setInt(5,inbox.getInboxId());
  assertFalse(expected);
 }

 @Test
 void createNormalInbox() throws SQLException {

  Connection dbConn = mock(Connection.class);
  PreparedStatement ps = mock(PreparedStatement.class);
  ResultSet rs = mock(ResultSet.class);
  String query = "insert into inbox (inboxType,groupName) values (?,?)";
  when(dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)).thenReturn(ps);
  when(ps.executeUpdate()).thenReturn(1);
  when(ps.getGeneratedKeys()).thenReturn(rs);
  when(rs.next()).thenReturn(true, false);
  when(rs.getInt(1)).thenReturn(1);
  InboxDao inboxDao= new InboxDao(dbConn);
  int expected =inboxDao.createNormalInbox();
  verify(ps).setInt(1,1);
  verify(ps).setString(2,"");
 assertEquals(expected,1);
 }

/**when inbox exists**/
 @Test
 void getInbox() throws SQLException {

  Connection dbConn = mock(Connection.class);
  PreparedStatement ps = mock(PreparedStatement.class);
  ResultSet rs = mock(ResultSet.class);
  Inbox inbox = new Inbox(1,1,1,"fun chat","fun.png","g");
  String query = "select * from inbox where inboxId=?";
  when(dbConn.prepareStatement(query)).thenReturn(ps);
  when(ps.executeQuery()).thenReturn(rs);
  when(rs.next()).thenReturn(true,  false);
  when(rs.getInt("inboxId")).thenReturn(inbox.getInboxId());
  when(rs.getInt("inboxType")).thenReturn(inbox.getInboxType());
  when(rs.getInt("adminId")).thenReturn(inbox.getInboxId());
  when(rs.getString("groupName")).thenReturn(inbox.getGroupName());
  when(rs.getString("groupProfilePicture")).thenReturn(inbox.getGroupProfilePicture());

  InboxDao inboxDao= new InboxDao(dbConn);
  Inbox actual= inboxDao.getInbox(inbox.getInboxId());

  assertEquals(inbox,actual);
 }
/**when inbox is not found**/
 @Test
 void getInbox_whenNotFound() throws SQLException {

  Connection dbConn = mock(Connection.class);
  PreparedStatement ps = mock(PreparedStatement.class);
  ResultSet rs = mock(ResultSet.class);
  Inbox inbox = new Inbox(1,1,1,"fun chat","fun.png","g");
  String query = "select * from inbox where inboxId=?";
  when(dbConn.prepareStatement(query)).thenReturn(ps);
  when(ps.executeQuery()).thenReturn(rs);
  when(rs.next()).thenReturn(false);

  InboxDao inboxDao= new InboxDao(dbConn);
  Inbox actual= inboxDao.getInbox(inbox.getInboxId());

  assertEquals(null,actual);
 }




}
