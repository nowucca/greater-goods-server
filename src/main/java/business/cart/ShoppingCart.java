package business.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * A shopping cart arrives in an order form from the client.
 * This class holds the de-serialized JSON data.
 *
 * (We ignore any extra elements that the client sends
 *  that this class does not require, for example "_type").
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCart {

	private static final int SURCHARGE = 500;

	private int numberOfItems;

	private int total;

	private List<ShoppingCartItem> items;

	public ShoppingCart() {
	}

	public int getSurcharge() {
		return SURCHARGE;
	}

	public int getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<ShoppingCartItem> getItems() {
		return items;
	}

	public void setItems(List<ShoppingCartItem> items) {
		this.items = items;
	}

	/**
	 * Returns the sum of the item price multiplied by the quantity for all
	 * items in shopping cart list. This is the total cost *in cents*,
	 * not including the surcharge.
	 *
	 * @return total of items in cart, excluding surcharge
	 */
	@JsonIgnore
	public int getComputedSubtotal() {
		return items.stream()
			.mapToInt(item -> item.getQuantity() * item.getProductForm().getPrice())
			.sum();
	}

}
