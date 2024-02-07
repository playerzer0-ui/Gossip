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

    public ArrayList<InboxParticipants> getAllInbox(int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<InboxParticipants> inboxParticipants = new ArrayList();
        try {
            con = getConnection();

            String query = "Select * from inboxparticipants where userId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                InboxParticipants ibp = new InboxParticipants(rs.getInt("userId"), rs.getInt("inboxId"), rs.getInt("deletedState"), rs.getInt("unseenMessages"), rs.getInt("isOpen"));
                inboxParticipants.add(ibp);
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the getAllInbox() method: " + e.getMessage());
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
                System.out.println("Exception occurred in the final section of the getAllInbox() method: " + e.getMessage());
            }
        }
        return inboxParticipants;
    }

    public ArrayList<InboxParticipants> getAllInboxParticipants(int inboxId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<InboxParticipants> inboxParticipants = new ArrayList();
        try {
            con = getConnection();

            String query = "Select * from inboxparticipants where inboxId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, inboxId);
            rs = ps.executeQuery();

            while (rs.next()) {
                InboxParticipants ibp = new InboxParticipants(rs.getInt("userId"), rs.getInt("inboxId"), rs.getInt("deletedState"), rs.getInt("unseenMessages"), rs.getInt("isOpen"));
                inboxParticipants.add(ibp);
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the getAllInbox() method: " + e.getMessage());
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
                System.out.println("Exception occurred in the final section of the getAllInbox() method: " + e.getMessage());
            }
        }
        return inboxParticipants;
    }

    public InboxParticipants getOtherInboxParticipant(int inboxId, int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InboxParticipants ibp = null;
        try {
            con = getConnection();

            String query = "Select * from inboxparticipants where inboxId=? and userId!=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, inboxId);
            ps.setInt(2, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                ibp = new InboxParticipants(rs.getInt("userId"), rs.getInt("inboxId"), rs.getInt("deletedState"), rs.getInt("unseenMessages"), rs.getInt("isOpen"));
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the getOtherInboxParticipant() method: " + e.getMessage());
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
                System.out.println("Exception occurred in the final section of the getOtherInboxParticipant() method: " + e.getMessage());
            }
        }
        return ibp;
    }

    public boolean openInbox(int inboxId, int userId, int openState) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        boolean state = false;
        try {

            con = getConnection();
            String command = " update inboxparticipants set isOpen=? where inboxId=? and userId=? ";
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
                System.out.println("Exception occurred in the final section of the openInbox() method: " + e.getMessage());
            }
        }
        return state;
    }

    public boolean updateUnSeenMessages(int inboxId, int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        boolean state = false;
        try {

            con = getConnection();
            String command = " update inboxparticipants set unseenMessages=unseenMessages+1 where inboxId=? and userId=? and isOpen=0";
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
                System.out.println("Exception occurred in the final section of the updateUnSeenMessages() method: " + e.getMessage());
            }
        }
        return state;
    }

    public boolean resetUnSeenMessages(int inboxId, int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        boolean state = false;
        try {

            con = getConnection();
            String command = " update inboxparticipants set unseenMessages=0 where inboxId=? and userId=?";
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
                System.out.println("Exception occurred in the final section of the resetUnSeenMessages() method: " + e.getMessage());
            }
        }
        return state;
    }

    public boolean insertInboxParticipant(int inboxId, int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        boolean state = false;
        try {

            con = getConnection();
            String command = "insert into inboxparticipants (inboxId,userId,deletedSate,unseenMessages,isOpen) values (?,?,?,?,?) ";
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
                System.out.println("Exception occurred in the final section of the insertInboxParticipant() method: " + e.getMessage());
            }
        }
        return state;
    }

}
