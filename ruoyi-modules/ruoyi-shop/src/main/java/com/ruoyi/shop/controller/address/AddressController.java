package com.ruoyi.shop.controller.address;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.shop.domain.address.Address;
import com.ruoyi.shop.service.address.IAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/18
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@RestController
@RequestMapping("/member/address")
@Tag(name = "收货地址管理")
@Slf4j
public class AddressController {

    @Autowired
    private IAddressService addressService;

    // 添加收货地址
    @PostMapping("")
    @Operation(summary = "添加收货地址")
    public R addAddress(@RequestBody Address AddressParams) {
        Long userId = SecurityUtils.getUserId();
        if(userId == null){
            return R.fail("账户未登录");
        }
        AddressParams.setUserId(userId);
        if(AddressParams.getIsDefault() == 1){
            //添加地址后只能有一个是默认地址，需将之前的默认地址取消
            LambdaQueryWrapper<Address> queryWrapper =  new LambdaQueryWrapper<>();
            LambdaQueryWrapper<Address> eq = queryWrapper.eq(Address::getUserId, userId).eq(Address::getIsDefault, 1);
            boolean exists = addressService.exists(eq);
            if(exists){
                Address address = addressService.getOne(eq);
                address.setIsDefault(0);
                addressService.updateById(address);
            }
        }
        boolean success = addressService.save(AddressParams);
        return R.ok(success ? "添加成功" : "添加失败");
    }

    // 获取收货地址列表
    @GetMapping("")
    @Operation(summary = "获取收货地址列表")
    public R<List<Address>> getAddressList() {
        //根据用户iD获取地址列表
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<Address> query = new LambdaQueryWrapper<Address>();
        query.eq(Address::getUserId, userId).orderByDesc(Address::getUpdateTime);
        List<Address> list = addressService.list(query);
        return R.ok(list);
    }

    // 获取收货地址详情
    @GetMapping("/{id}")
    @Operation(summary = "获取收货地址详情")
    public R<Address> getAddressById(@PathVariable("id") Integer id) {
        Address address = addressService.getById(id);

        if (address == null) {
            return R.fail("未找到该地址");
        }
        return R.ok(address);
    }

    // 修改收货地址
    @PutMapping("/{id}")
    @Operation(summary = "修改收货地址")
    public R updateAddress(@PathVariable("id") Integer id, @RequestBody Address address) {
        address.setId(id);
        Long userId = SecurityUtils.getUserId();
        if(address.getIsDefault() == 1){
            //添加地址后只能有一个是默认地址，需将之前的默认地址取消
            LambdaQueryWrapper<Address> queryWrapper =  new LambdaQueryWrapper<>();
            LambdaQueryWrapper<Address> eq = queryWrapper.eq(Address::getUserId, userId).eq(Address::getIsDefault, 1);
            boolean exists = addressService.exists(eq);
            if(exists){
                Address dto = addressService.getOne(eq);
                dto.setIsDefault(0);
                addressService.updateById(dto);
            }
        }
        boolean success = addressService.updateById(address);
        return R.ok(success ? "修改成功" : "修改失败");
    }

    // 删除收货地址
    @DeleteMapping("/{id}")
    @Operation(summary = "删除收货地址")
    public R deleteAddress(@PathVariable("id") Integer id) {
        boolean success = addressService.removeById(id);
        return R.ok(success ? "删除成功" : "删除失败");
    }
}
