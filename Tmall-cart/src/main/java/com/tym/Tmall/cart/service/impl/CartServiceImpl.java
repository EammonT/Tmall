package com.tym.Tmall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tym.Tmall.cart.feign.ProductFeignService;
import com.tym.Tmall.cart.service.CartService;
import com.tym.Tmall.cart.vo.CartItemVO;
import com.tym.Tmall.cart.vo.CartVO;
import com.tym.Tmall.cart.vo.SkuInfoVO;
import com.tym.Tmall.common.constant.AuthServerConstant;
import com.tym.Tmall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Resource
    ProductFeignService productFeignService;

    @Override
    public CartItemVO addToCart(Long skuId, Integer num) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();

        String o = (String) cartOps.get(skuId.toString());
        if (StringUtils.hasLength(o)){
            CartItemVO item = JSON.parseObject(o, CartItemVO.class);
            item.setCount(item.getCount()+num);
            cartOps.put(skuId.toString(),JSON.toJSONString(item));
            return item;
        }
        R skuInfo = productFeignService.getSkuInfo(skuId);
        SkuInfoVO data = skuInfo.getData("skuInfo", new TypeReference<SkuInfoVO>() {});
        CartItemVO cartItemVO = new CartItemVO();
        cartItemVO.setCheck(true);
        cartItemVO.setCount(num);
        cartItemVO.setTitle(data.getSkuTitle());
        cartItemVO.setSkuId(data.getSkuId());
        cartItemVO.setPrice(data.getPrice());
        List<String> values = productFeignService.getSkuSaleAttrValues(skuId);
        cartItemVO.setSkuAttrValues(values);
        String s = JSON.toJSONString(cartItemVO);
        cartOps.put(skuId.toString(),s);
        return cartItemVO;
    }

    @Override
    public CartItemVO getCartItem(Long skuId) {

        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String o = (String) cartOps.get(skuId.toString());
        CartItemVO item = JSON.parseObject(o, CartItemVO.class);
        return item;
    }

    @Override
    public CartVO getCart() {
        CartVO cartVO = new CartVO();
        List<CartItemVO> cartItems = getCartItems(redisTemplate.opsForValue().get(AuthServerConstant.LOGIN_USER));
        cartVO.setItems(cartItems);
        return cartVO;
    }

    @Override
    public void checkItem(Long skuId, Integer check) {
        CartItemVO cartItem = getCartItem(skuId);
        cartItem.setCheck(check==1?true:false);
        String s = JSON.toJSONString(cartItem);
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.put(skuId.toString(),s);

    }

    @Override
    public void changeItemCount(Long skuId, Integer num) {
        CartItemVO cartItem = getCartItem(skuId);
        cartItem.setCount(num);
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.put(skuId.toString(),JSON.toJSONString(cartItem));
    }

    @Override
    public void deleteItem(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.delete(skuId.toString());
    }

    @Override
    public List<CartItemVO> getUserCartItems() {
        String s = redisTemplate.opsForValue().get(AuthServerConstant.LOGIN_USER);
        if (StringUtils.hasLength(s)){
            List<CartItemVO> cartItems = getCartItems(s);
            List<CartItemVO> collect = cartItems.stream().filter(item -> item.getCheck()).map(item->{
                R price = productFeignService.getPrice(item.getSkuId());
                String data = (String) price.get("data");
                item.setPrice(new BigDecimal(data));
                return item;
            }).collect(Collectors.toList());

            return collect;
        }else {
            return null;
        }

    }

    private List<CartItemVO> getCartItems(String cartKey) {
        //获取购物车里面的所有商品
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(cartKey);
        List<Object> values = operations.values();
        if (values != null && values.size() > 0) {
            List<CartItemVO> cartItemVoStream = values.stream().map((obj) -> {
                String str = (String) obj;
                CartItemVO cartItem = JSON.parseObject(str, CartItemVO.class);
                return cartItem;
            }).collect(Collectors.toList());
            return cartItemVoStream;
        }
        return null;

    }
    private BoundHashOperations<String, Object, Object> getCartOps() {
        String cartKey = redisTemplate.opsForValue().get(AuthServerConstant.LOGIN_USER);
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(cartKey);
        return ops;
    }
}
