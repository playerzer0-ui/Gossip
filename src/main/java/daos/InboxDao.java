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

    public int createGroupChat(int adminId, String groupName) {
        Connection con = null;
        int rowsAffected = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = 0;
        try {

            con = getConnection();
            String command = "insert into inbox (inboxType,adminId,groupName) values (?,?,?) ";
            ps = con.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, 2);
            ps.setInt(2, adminId);
            ps.setString(3, groupName);
            rowsAffected = ps.executeUpdate();
            ResultSet res = ps.getGeneratedKeys();
            if (res.next()) {
                id = Integer.parseInt(res.getString(1));
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the inbox() method: " + e.getMessage());
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
                System.out.println("Exception occurred in the final section of the inbox() method: " + e.getMessage());
            }
        }
        return id;
    }

    public int createNormalInbox() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        int id = 0;
        try {

            con = getConnection();
            String command = "insert into inbox (inboxType,groupName) values (?,?) ";
            ps = con.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, 1);
            ps.setString(2, "");
            rowsAffected = ps.executeUpdate();
            ResultSet res = ps.getGeneratedKeys();
            if (res.next()) {
                id = Integer.parseInt(res.getString(1));
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the createNormalInbox() method: " + e.getMessage());
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
                System.out.println("Exception occurred in the final section of the createNormalInbox() method: " + e.getMessage());
            }
        }
        return id;
    }

    public Inbox getInbox(int inboxId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Inbox inbox = null;
        try {
            con = getConnection();

            String query = "select * from inbox where inboxId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, inboxId);
            rs = ps.executeQuery();
            if (rs.next()) {
                String adminId = null;
                adminId = rs.getInt("adminId") + "";
                if (adminId != null) {
                    inbox = new Inbox(rs.getInt("inboxId"), rs.getInt("inboxType"), rs.getInt("adminId"), rs.getString("groupName"));
                } else {
                    inbox = new Inbox(rs.getInt("inboxId"), rs.getInt("inboxType"), -1, rs.getString("groupName"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in  the getInbox() method: " + e.getMessage());
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
                System.out.println("Exception occurred in  the getInbox() method:  " + e.getMessage());
            }
        }
        return inbox;
    }
}
