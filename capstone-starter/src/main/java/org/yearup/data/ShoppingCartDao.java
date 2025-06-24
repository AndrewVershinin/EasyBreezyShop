package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao {


    // add additional method signatures here
    ShoppingCart getByUserId(int userId);

    void addProduct(int userId, int productId, int quantity);

    void updateQuantity(int userId, int productId, int quantity);

    void updateProduct(int userId, int productId, int quantity);

    void clearCart(int userId);

}
