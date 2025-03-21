package com.ruoyi.shop.service.impl.cart;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.shop.domain.cart.CartItem;
import com.ruoyi.shop.mapper.cart.CartMapper;
import com.ruoyi.shop.service.cart.ICartService;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/21
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, CartItem> implements ICartService {
}
