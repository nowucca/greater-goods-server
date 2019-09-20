package business.customer;

import business.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static business.JdbcUtils.getConnection;

public class CustomerDaoJdbc implements CustomerDao {

    private static final String CREATE_CUSTOMER_SQL =
            "INSERT INTO `customer` (name, address, phone, email, cc_number) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private static final String FIND_ALL_SQL =
            "SELECT customer_id, name, address, " +
                    "phone, email, cc_number " +
                    "FROM customer";

    private static final String FIND_BY_CUSTOMER_ID_SQL =
            "SELECT customer_id, name, address, " +
                    "phone, email, cc_number " +
                    "FROM customer WHERE customer_id = ?";

    @Override
    public long create(Connection connection,
					   String name,
					   String address,
					   String phone,
					   String email,
					   String ccNumber) {
        try (PreparedStatement statement =
                     connection.prepareStatement(CREATE_CUSTOMER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setString(5, ccNumber);
            int affected = statement.executeUpdate();
            if (affected != 1) {
                throw new DbException("Failed to insert a customer, affected row count = " + affected);
            }
            long customerId;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                customerId = rs.getLong(1);
            } else {
                throw new DbException("Failed to retrieve customerId auto-generated key");
            }
            return customerId;
        } catch (SQLException e) {
            throw new DbException("Encountered problem creating a new customer ", e);
        }

    }

    @Override
    public List<Customer> findAll() {
        List<Customer> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Customer c = readCustomer(resultSet);
                result.add(c);
            }
        } catch (SQLException e) {
            throw new DbException("Encountered problem finding all categories", e);
        }

        return result;

    }

    @Override
    public Customer findByCustomerId(long customerId) {

        Customer result = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CUSTOMER_ID_SQL)) {
            statement.setLong(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = readCustomer(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DbException("Encountered problem finding customer " + customerId, e);
        }
        return result;
    }

    private Customer readCustomer(ResultSet resultSet) throws SQLException {
        Long customerId = resultSet.getLong("customer_id");
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");
        String ccNumber = resultSet.getString("cc_number");
        return new Customer(customerId, name, address, phone, email, ccNumber);
    }
}
