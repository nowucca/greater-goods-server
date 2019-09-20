package business.order;

import business.customer.Customer;
import business.product.Product;

import java.util.Collections;
import java.util.List;

public class OrderDetails {

    private Order order;
    private Customer customer;
    private List<LineItem> lineItems;
    private List<Product> products;

    public OrderDetails(Order order, Customer customer,
						List<LineItem> lineItems, List<Product> products) {
        this.order = order;
        this.customer = customer;
        this.lineItems = lineItems;
        this.products = products;
    }

    public Order getOrder() {
        return order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public List<LineItem> getLineItems() {
        return Collections.unmodifiableList(lineItems);
    }
}
