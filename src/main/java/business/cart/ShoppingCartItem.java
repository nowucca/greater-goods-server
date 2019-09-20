package business.cart;

import business.product.ProductForm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A shopping cart item arrives in an order form from the client.
 * This class holds the de-serialized JSON data for cart items.
 *
 * (We ignore any extra elements that the client sends
 *  that this class does not require, for example "_type").
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCartItem {

	private int quantity;

	// Client sends "product", which we deserialize as a
	// "product form" to distinguish it as incoming product
	// rather than a full Product model.
	@JsonProperty("product")
	private ProductForm productForm;

	public ShoppingCartItem() {
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ProductForm getProductForm() {
		return productForm;
	}

	public void setProductForm(ProductForm productForm) {
		this.productForm = productForm;
	}

	/**
	 * A quick accessor for getting the product id for this item.
	 * @return the id of the product in this cart item
	 */
	@JsonIgnore
	public long getProductId() {
		return productForm.getProductId();
	}

}
