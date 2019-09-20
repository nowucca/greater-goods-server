package business.order;

import business.customer.CustomerForm;
import business.cart.ShoppingCart;

public interface OrderService {

    long placeOrder(CustomerForm form, ShoppingCart cart);

    OrderDetails getOrderDetails(long orderId);

}
