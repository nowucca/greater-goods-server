package business.order;

import api.ApiException;
import business.DbException;
import business.JdbcUtils;
import business.customer.CustomerForm;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.product.Product;
import business.product.ProductDao;
import business.product.ProductForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

    private OrderDao orderDao;
    private LineItemDao lineItemDao;
    private CustomerDao customerDao;
    private ProductDao productDao;
    private Random random = new Random();

    @Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		try (Connection connection = JdbcUtils.getConnection()) {
            return performPlaceOrderTransaction(customerForm.getName(), customerForm.getAddress(), customerForm.getPhone(), customerForm.getEmail(),
                    customerForm.getCcNumber(), cart, connection);
        } catch (SQLException e) {
            throw new DbException("Error during close connection for customer order", e);
        }
    }

    @Override
    public OrderDetails getOrderDetails(long orderId) {
        Order order = orderDao.findByOrderId(orderId);
        Customer customer = customerDao.findByCustomerId(order.getCustomerId());
        List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
        List<Product> products = lineItems
                .stream()
                .map(lineItem -> productDao.findByProductId(lineItem.getProductId()))
                .collect(Collectors.toList());

        return new OrderDetails(order, customer, lineItems, products);
    }


	private void validateCustomer(CustomerForm customerForm) {

    	boolean hasError;

    	String name = customerForm.getName();

		if (name == null || name.equals("") || name.length() > 45) {
			hasError = true;
		}

		String address = customerForm.getAddress();
		if (address == null || address.equals("")) {
			hasError = true;
		}

		String phone = customerForm.getPhone();
		if (phone == null || phone.equals("")) {
			hasError = true;
		} else {
			String digits = phone.replaceAll("\\D", "");
			if (digits.length() != 10) {
				hasError = true;
			}
		}

		String email = customerForm.getEmail();
		if (email == null || email.length() == 0 || doesNotLookLikeAnEmail(email)) {
			hasError = true;
		} else {
			hasError = email.endsWith(".");
		}

		String ccNumber = customerForm.getCcNumber();
		if (ccNumber == null || ccNumber.equals("")) {
			hasError = true;
		} else {
			String digits = ccNumber.replaceAll("\\D", "");
			if (digits.length() < 14 || digits.length() > 16){
				hasError = true;
			}
		}
		if (hasError) {
			throw new ApiException.InvalidParameter("Invalid customer data");
		}
	}

	private void validateCart(ShoppingCart cart) {
		// Ensure we can load up products, validate quantity of products
		cart.getItems().forEach(item-> {
			if (item.getQuantity() < 0 || item.getQuantity() > 99) {
				throw new ApiException.InvalidParameter("Invalid quantity");
			}
			ProductForm productRequested = item.getProductForm();
			Product productCatalog = productDao.findByProductId(productRequested.getProductId());
			// Check the products agree on price, points, category
			if (productRequested.getPrice() != productCatalog.getPrice()) {
				throw new ApiException.InvalidParameter("Invalid price");
			}
			if (productRequested.getPoints() != productCatalog.getPoints()) {
				throw new ApiException.InvalidParameter("Invalid points");
			}
			if (productRequested.getCategoryId() != productCatalog.getCategoryId()) {
				throw new ApiException.InvalidParameter("Invalid category");
			}
		});

		// Check claimed total against computed total
		if(cart.getTotal() != cart.getComputedSubtotal() + cart.getSurcharge()) {
			throw new ApiException.AuthenticationFailure.InvalidParameter("Payment mismatch");
		};
	}

	private static Pattern SIMPLE_EMAIL_REGEX = Pattern.compile("^\\S+@\\S+$");

	private boolean doesNotLookLikeAnEmail(String email) {
		return !SIMPLE_EMAIL_REGEX.matcher(email).matches();
	}

	private long performPlaceOrderTransaction(String name, String address, String phone, String email,
											  String ccNumber, ShoppingCart cart,
											  Connection connection) {
        try {
            connection.setAutoCommit(false);

            long customerId = customerDao.create(connection, name, address, phone, email, ccNumber);
            long customerOrderId = orderDao.create(connection, cart.getComputedSubtotal() + cart.getSurcharge(), generateConfirmationNumber(), customerId);
            for (ShoppingCartItem item : cart.getItems()) {
                lineItemDao.create(connection, customerOrderId, item.getProductId(), item.getQuantity());
            }
            connection.commit();
            return customerOrderId;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DbException("Failed to roll back transaction", e1);
            }
            return 0;
        }
    }

    private int generateConfirmationNumber() {
        return random.nextInt(999999999);
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setLineItemDao(LineItemDao lineItemDao) {
        this.lineItemDao = lineItemDao;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

}
