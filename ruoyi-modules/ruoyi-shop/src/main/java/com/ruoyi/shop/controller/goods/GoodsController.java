package com.ruoyi.shop.controller.goods;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.shop.config.Utils;
import com.ruoyi.shop.controller.address.AddressController;
import com.ruoyi.shop.domain.goods.*;
import com.ruoyi.shop.mapper.goods.GoodsSkusMapper;
import com.ruoyi.shop.mapper.goods.GoodsSpecMapper;
import com.ruoyi.shop.mapper.goods.GoodsValueMapper;
import com.ruoyi.shop.service.goods.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/goods")
@Tag(name = "显示商品")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsParticularsService goodsParticularsService;
    @Autowired
    private GoodsBrandService goodsBrandService;

    @Operation(summary = "获得所有的商品")
    @GetMapping(value = "/getAllGoods")
    public R getAllGoods() {
        List<Goods> goods = goodsService.list();
        return R.ok(goods);
    }

    @Operation(summary = "猜你喜欢商品带分页")
    @GetMapping(value = "/guessLikeGoods")
    public R guessLikeGoods(@RequestParam(name = "page", defaultValue = "1") Long page,
                            @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize) {
        //分页功能进行查询
        Page< Goods > goodsPage = new Page<>(page, pageSize);
        //条件构造器
        QueryWrapper< Goods > queryWrapper = new QueryWrapper<>();
        //随机排序
        queryWrapper.orderByDesc("rand()");
        //查询数据
        goodsService.page(goodsPage, queryWrapper);
        //每页的数据集合
        List< Goods > goodsList = goodsPage.getRecords();
        //总的记录数
        Long counts = goodsPage.getTotal();
        //每页条数
        pageSize = goodsPage.getSize();
        //总页数
        Long pages = goodsPage.getPages();
        //当前页数
        page = goodsPage.getCurrent();
        Map< String, Object > map = new HashMap<>();
        map.put("counts", counts);
        map.put("pageSize", pageSize);
        map.put("pages", pages);
        map.put("page", page);
        map.put("goodsList", goodsList);
        return R.ok(map);
    }

    @Operation(summary = "增加商品")
    @PostMapping(value = "/addGoods")
    public R addGoods(@RequestBody Goods goods) {
        boolean flag = goodsService.save(goods);
        if (flag) {
            return R.ok("增加商品成功");
        } else {
            return R.fail("增加商品失败");
        }
    }

    @Operation(summary = "删除商品数据")
    @DeleteMapping(value = "/deleteGoods/{id}")
    public R deleteGoods(@PathVariable("id") Integer goodsId) {
        boolean flag = goodsService.removeById(goodsId);
        if (flag) {
            return R.ok("删除商品成功");
        } else {
            return R.fail("删除商品失败");
        }
    }

    @Operation(summary = "按照商品编号查询数据")
    @GetMapping(value = "/getGoodsById/{id}")
    public R getGoodsById(@PathVariable("id") Integer goodsId) {
        Goods goods = goodsService.getById(goodsId);
        return R.ok( goods);
    }
    @Autowired
    private GoodsSkusMapper goodsSkusMapper;
    @Autowired
    private GoodsSpecMapper goodsSpecMapper;
    @Autowired
    private AddressController addressController;;
    @Autowired
    private GoodsDetailsService goodsDetailsService;

    @Autowired
    private GoodsPropertyService goodsPropertiesService;
    @Autowired
    private GoodsValueMapper goodsValueMapper;
    @Autowired
    private  SkusSpecService skusSpecService;
    @Operation(summary = "按照商品详情编号联表查询数据")
    @GetMapping(value = "")
    public R getGoodsByIds(@RequestParam("id") Integer id) {
        List<GoodsParticularsVo> goodsParticularsVoList = new ArrayList<>();
        GoodsParticulars goodsParticulars = goodsParticularsService.getById(id);
        Goods goods = goodsService.getById(id);

        GoodsBrand brand = goodsBrandService.getOne(new LambdaQueryWrapper<GoodsBrand>().eq(GoodsBrand::getBrandId, goodsParticulars.getBrandId()));
        GoodsParticularsVo goodsParticularsVo = new GoodsParticularsVo();
        BeanUtils.copyProperties(goodsParticularsVo, goodsParticulars);

        List<GoodsSkus> goodsSkuses = goodsSkusMapper
                .selectList(new LambdaQueryWrapper<GoodsSkus>()
                        .in(GoodsSkus::getSkuId, Utils.split(goodsParticulars.getSkusId())
                        ));
        List<GoodsSkusVo> goodsSkusesVo = new ArrayList<>();
        //赋值
        for(GoodsSkus goodsSkus : goodsSkuses){
            GoodsSkusVo goodsSkusVo = new GoodsSkusVo();
            SkusSpec byId = skusSpecService.getById(goodsSkus.getSkuSpecsId());
            BeanUtils.copyProperties(goodsSkus,goodsSkusVo);
            goodsSkusVo.setSkusSpec(byId);
            goodsSkusesVo.add(goodsSkusVo);
        }


        GoodsSpec goodsSpecs = goodsSpecMapper.selectById(goodsParticulars.getSpecId());
        List<GoodsValue> goodsValues = goodsValueMapper.selectList(new LambdaQueryWrapper<GoodsValue>()
                .in(GoodsValue::getValueId, Utils.split(goodsSpecs.getSpecValues())));

        List<GoodsSpecVo> goodsSpecsVo = new ArrayList<>();
        GoodsSpecVo build = GoodsSpecVo.builder()
                .goodsValue(goodsValues)
                .specId(goodsSpecs.getSpecId())
                .specName(goodsSpecs.getSpecName())
                .build();
        goodsSpecsVo.add(build);

        GoodsDetails details = goodsDetailsService.getById(goodsParticulars.getDetailsId());
        Set<String> PropertyIdList = Arrays.stream(details.getDetailsProperty().split(",")).collect(Collectors.toSet());
        LambdaQueryWrapper<GoodsProperty> eq = new LambdaQueryWrapper<GoodsProperty>().in(GoodsProperty::getPropertyId,
                PropertyIdList);

        List<GoodsProperty> list = goodsPropertiesService.list(eq);

        GoodsDetailsVo goodsDetailsVo = GoodsDetailsVo.builder()
                .detailsId(details.getDetailsId())
                .detailsPictures(Arrays.stream(details.getDetailsPictures().split(",")).collect(Collectors.toList()))
                .goodsProperty(list).build();

        List<String> mainPic = Arrays.stream(goodsParticulars.getMainPictures().split(",")).collect(Collectors.toList());
        //还需要goods_sku_spec
         goodsParticularsVo =  GoodsParticularsVo.builder()
                 .id(goods.getGoodsId())
                .goodsName(goods.getGoodsName())
                .goodsDesc(goods.getGoodsDesc())
                .goodsPrice(goods.getGoodsPrice())
                .goodsDetails(goodsDetailsVo)
                .mainPictures(mainPic)
                .goodsBrand(brand)
                .goodsSkus(goodsSkusesVo)
                .goodsSpec(goodsSpecsVo)
                .addressList(addressController.getAddressList().getData())
                .build();
        goodsParticularsVoList.add(goodsParticularsVo);

        return R.ok(goodsParticularsVoList);
    }

    @Operation(summary = "修改商品数据")
    @PutMapping(value = "/updateGoods")
    public R updateGoods(@RequestBody Goods goods) {
        boolean flag = goodsService.updateById(goods);
        if (flag) {
            return R.ok("修改商品成功");
        } else {
            return R.fail("修改商品失败");
        }
    }

    /**
     * 根据商品名模糊查询商品
     * @param goodsName
     * @return
     */
    @GetMapping("/like")
    @Operation(summary = "模糊查询商品")
    public R listGoods(@RequestParam String goodsName){

        return null;
    }

    /**
     * 根据skus查询商品
     * @return
     */
    @PostMapping("/getGoodsBySkus")
    public R selectBySkus(@RequestBody Long[] ids){
        return null;
    }
}
