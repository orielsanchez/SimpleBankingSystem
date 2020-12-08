package banking;

import banking.model.Account;

import java.sql.*;


public class Database {
    private static String filename;


    public static void createNewDatabase(String filename) {
        Database.filename = filename;
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
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            // set the corresponding param
            preparedStatement.setInt(1, newBalance);
            preparedStatement.setString(2, card_number);
            // update
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(String cardNumber) {
        String sql = "DELETE FROM card WHERE number = ?";

        try (
                Connection connection = this.connect(filename);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void transfer(String senderCardNumber, String recipientCardNumber, int amount) throws SQLException {
        String removeFundsSQL = "UPDATE card SET balance = (balance - ?) WHERE number = ?";
        String addFundsSQL = "UPDATE card SET balance = (balance + ?) WHERE number = ?";

        try (
                Connection connection = this.connect(filename)
        ) {
            connection.setAutoCommit(false);

            try (
                    PreparedStatement removeFunds = connection.prepareStatement(removeFundsSQL);
                    PreparedStatement addFunds = connection.prepareStatement(addFundsSQL)
            ) {
                removeFunds.setInt(1, amount);
                removeFunds.setString(2, senderCardNumber);
                removeFunds.executeUpdate();

                addFunds.setInt(1, amount);
                addFunds.setString(2, recipientCardNumber);
                addFunds.executeUpdate();

                connection.commit();



            }
        }
    }
}
