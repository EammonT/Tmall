package com.tym.Tmall.order.web;

import com.tym.Tmall.order.service.OrderService;
import com.tym.Tmall.order.vo.OrderConfirmVO;
import com.tym.Tmall.order.vo.OrderSubmitVO;
import com.tym.Tmall.order.vo.SubmitOrderResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller
public class OrderWebController {

    @Resource
    OrderService orderService;

    @GetMapping("/toTrade")
    public String toTrade(Model model){
        OrderConfirmVO orderConfirmVO = orderService.confirmOrder();
        model.addAttribute("orderConfirmData",orderConfirmVO);
        return "confirm";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVO submitVO, Model model, RedirectAttributes attributes){
        SubmitOrderResponseVO responseVO = orderService.submitOrder(submitVO);
        if (responseVO.getCode()==0){
            model.addAttribute("submitOrderResponse",responseVO);
            return "pay";
        }else {
            String msg = "";
             if(responseVO.getCode()==1){
                 msg  = "下单失败！";
             }
            attributes.addFlashAttribute("msg",msg);
            return "redirect:http://order.tmall.com/toTrade";
        }
    }
}
