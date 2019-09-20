package business.order;

import business.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static business.JdbcUtils.getConnection;

public class LineItemDaoJdbc implements LineItemDao {

    private static final String CREATE_LINE_ITEM_SQL =
            "INSERT INTO order_line_item (order_id, product_id, quantity) " +
                    "VALUES (?, ?, ?)";

    private static final String FIND_BY_ORDER_ID_SQL =
            "SELECT order_id, product_id, quantity " +
                    "FROM order_line_item WHERE order_id = ?";

    @Override
    public void create(Connection connection, long orderId, long bookId, int quantity) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_LINE_ITEM_SQL)) {
            statement.setLong(1, orderId);
            statement.setLong(2, bookId);
            statement.setInt(3, quantity);
            int affected = statement.executeUpdate();
            if (affected != 1) {
                throw new DbException("Failed to insert an order line item, affected row count = " + affected);
            }
        } catch (SQLException e) {
            throw new DbException("Encountered problem creating a new line item ", e);
        }
    }

    @Override
    public List<LineItem> findByOrderId(long orderId) {
        List<LineItem> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ORDER_ID_SQL)) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(readLineItem(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DbException("Encountered problem finding ordered books for customer order "
                    + orderId, e);
        }
        return result;
    }

    private LineItem readLineItem(ResultSet resultSet) throws SQLException {
        long orderId = resultSet.getLong("order_id");
        long productId = resultSet.getLong("product_id");
        int quantity = resultSet.getInt("quantity");
        return new LineItem(orderId, productId, quantity);
    }
}
