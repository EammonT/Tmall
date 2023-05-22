package com.tym.Tmall.cart.controller;

import com.tym.Tmall.cart.service.CartService;
import com.tym.Tmall.cart.vo.CartItemVO;
import com.tym.Tmall.cart.vo.CartVO;
import com.tym.Tmall.common.constant.AuthServerConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {

    @Resource
    private CartService cartService;

    @GetMapping("/currentUserCartItems")
    @ResponseBody
    public List<CartItemVO> getcurrentUserCartItems(){
        return cartService.getUserCartItems();
    }

    @GetMapping("deleteItem")
    public String deleteItem(@RequestParam("skuId")Long skuId){
        cartService.deleteItem(skuId);
        return "redirect:http://cart.tmall.com/cart.html";
    }

    @GetMapping("/countItem")
    public String countItem(@RequestParam("skuId") Long skuId,@RequestParam("num") Integer num){
        cartService.changeItemCount(skuId,num);
        return "redirect:http://cart.tmall.com/cart.html";
    }

    @GetMapping("/checkItem")
    public String checkItem(@RequestParam("skuId") Long skuId,@RequestParam("check") Integer check){
        cartService.checkItem(skuId,check);
        return "redirect:http://cart.tmall.com/cart.html";
    }

    @GetMapping("/cart.html")
    public String cartListPage(HttpSession session,Model model){
        Object attribute = session.getAttribute(AuthServerConstant.LOGIN_USER);
        if (attribute==null){
            return "redirect:http://auth.tmall.com/login.html";
        }

        CartVO cart = cartService.getCart();
        model.addAttribute("cart",cart);
        return "cartList";
    }

    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId")Long skuId, @RequestParam("num")Integer num, RedirectAttributes attributes){
        CartItemVO cartItemVO = cartService.addToCart(skuId,num);
//        model.addAttribute("item",cartItemVO);
        attributes.addAttribute("skuId",skuId);
        return "redirect:http://cart.tmall.com/addToCartSuccess.html";
    }

    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId,Model model){
        CartItemVO cartItemVO = cartService.getCartItem(skuId);
        model.addAttribute("item",cartItemVO);
        return "success";
    }
}
