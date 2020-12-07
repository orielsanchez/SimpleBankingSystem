package banking;

import banking.model.Account;

import java.sql.*;


public class Database {
    private static String filename;


    public static void createNewDatabase(String filename) {
        Database.filename = filename;
        String url = "jdbc:sqlite:" + filename;

        try (Connection connection = DriverManager.getConnection(url)) {
            if (connection != null) {
                DatabaseMetaData metaData = connection.getMetaData();
                System.out.println("The driver name is " + metaData.getDriverName());
                System.out.println("A new database has been created");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    public static void createNewTable(String filename) {
        String url = "jdbc:sqlite:" + filename;

        String sql = """
                CREATE TABLE IF NOT EXISTS card (
                	id INTEGER PRIMARY KEY,
                	number text NOT NULL,
                	pin TEXT,
                	balance INTEGER DEFAULT 0
                );""";

        try (
                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement()
        ) {
            statement.execute(sql);
            System.out.println("sql statement executed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect(String filename) {
        // SQLite connection string
        String url = "jdbc:sqlite:" + filename;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(String cardNumber, String pin, int balance) {
        String sql = "INSERT INTO card(number, pin, balance) VALUES(?,?,?)";

        try (Connection connection = this.connect(filename)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, cardNumber);
                preparedStatement.setString(2, pin);
                preparedStatement.setInt(3, balance);
                preparedStatement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account getAccount(long cardNumber) {
        Account account = null;
        Long cn = null;
        String pin = null;
        int balance = 0;
        String number = String.valueOf(cardNumber);

        String sql = "SELECT number, pin, balance "
                + "FROM card WHERE number = ?";

        try (Connection connection = this.connect(filename);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cn = resultSet.getLong("number");
                pin = resultSet.getString("pin");
                balance = resultSet.getInt("balance");
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if (cn != null && pin != null) {
            account = new Account(cn, pin, balance);
        }
        return account;
    }

    public void update(String card_number, int newBalance) {
        String sql = "UPDATE card SET balance = ? "
                + "WHERE number = ?";

        try (Connection conn = this.connect(filename);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, newBalance);
            pstmt.setString(2, card_number);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
