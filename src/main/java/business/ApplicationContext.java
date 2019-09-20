
package business;

import business.category.CategoryDao;
import business.category.CategoryDaoJdbc;
import business.customer.CustomerDao;
import business.customer.CustomerDaoJdbc;
import business.order.*;
import business.product.ProductDao;
import business.product.ProductDaoJdbc;

public class ApplicationContext {

    private CategoryDao categoryDao;
    private ProductDao productDao;
    private OrderDao orderDao;
    private LineItemDao lineItemDao;
    private CustomerDao customerDao;
    private DefaultOrderService orderService;

    private static ApplicationContext INSTANCE = new ApplicationContext();

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }

    private ApplicationContext() {
        categoryDao = new CategoryDaoJdbc();
        productDao = new ProductDaoJdbc();
        orderDao = new OrderDaoJdbc();
        lineItemDao = new LineItemDaoJdbc();
        customerDao = new CustomerDaoJdbc();

        orderService = new DefaultOrderService();
		orderService.setCustomerDao(customerDao);
		orderService.setOrderDao(orderDao);
        orderService.setLineItemDao(lineItemDao);
		orderService.setProductDao(productDao);
	}

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

	public OrderService getOrderService() {
		return orderService;
	}
}
