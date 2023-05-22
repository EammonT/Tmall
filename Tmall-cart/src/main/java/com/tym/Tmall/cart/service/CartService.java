package com.tym.Tmall.cart.service;

import com.tym.Tmall.cart.vo.CartItemVO;
import com.tym.Tmall.cart.vo.CartVO;

import java.util.List;

public interface CartService {
    CartItemVO addToCart(Long skuId, Integer num);

    CartItemVO getCartItem(Long skuId);

    CartVO getCart();

    void checkItem(Long skuId, Integer check);

    void changeItemCount(Long skuId, Integer num);

    void deleteItem(Long skuId);

    List<CartItemVO> getUserCartItems();
}
