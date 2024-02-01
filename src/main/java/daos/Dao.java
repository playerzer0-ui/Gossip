package daos;

import java.sql.*;

public class Dao {
    protected PreparedStatement ps = null;
    protected ResultSet rs = null;
    private String databaseName;
    private Connection con;

    public Dao(String databaseName) {
        this.databaseName = databaseName;
    }

    public Dao(Connection con) {
        this.con = con;
    }

    public Connection getConnection() {
        // If there is a connection already present in the object, use that
        if (con != null) {
            return con;
        }
        // Otherwise, create one and return it
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;
        String username = "root";
        String password = "";
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex1) {
            System.out.println("Failed to find driver class " + ex1.getMessage());
            System.exit(1);
        } catch (SQLException ex2) {
            System.out.println("Connection failed " + ex2.getMessage());
        }
        return con;
    }

    /**
     * free the connection
     */
    public void freeConnection(String error) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(error);
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * free connection, for insert, update, delete
     */
    public void freeConnectionUpdate(String error) {
        try {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(error);
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }

    public void freeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
                con = null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * updates the increment in the table
     *
     * @param tableName the table name
     * @param num       the number increment to set to
     */
    public void updateIncrement(String tableName, int num) {
        try {
            con = this.getConnection();

            String query = "ALTER TABLE " + tableName + " AUTO_INCREMENT = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, num);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("\tA problem occurred during the updateIncrement method:");
            System.err.println("\t" + e.getMessage());
        } finally {
            freeConnectionUpdate("fail to close connection at updateIncrement");
        }
    }


}
