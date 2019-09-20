package business.order;

public class LineItem {

    private long productId;
    private long orderId;
    private int quantity;

    public LineItem(long orderId, long productId, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public long getOrderId() {
        return orderId;
    }

    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return "LineItem{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
