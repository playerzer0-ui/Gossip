package daos;

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


}
