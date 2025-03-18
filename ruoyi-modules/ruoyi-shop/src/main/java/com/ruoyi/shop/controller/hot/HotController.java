package com.ruoyi.shop.controller.hot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaJoinQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.shop.domain.goods.Goods;
import com.ruoyi.shop.domain.hot.*;
import com.ruoyi.shop.domain.hot.vo.HotGoods;
import com.ruoyi.shop.service.goods.GoodsService;
import com.ruoyi.shop.service.hot.HotGoodsService;
import com.ruoyi.shop.service.hot.HotService;
import com.ruoyi.shop.service.hot.RecommendService;
import com.ruoyi.shop.service.hot.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/home/hot/")
@Tag(name = "显示热门推荐")
@Slf4j
public class HotController {
    @Autowired
    private HotService hotService;
    @Autowired
    private HotGoodsService hotGoodsService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private RecommendService recommendService;

    @Operation(summary = "获得所有的热门推荐")
    @GetMapping(value = "/getAllHot")
    public R getAllHot() {
        LambdaJoinQueryWrapper<HotGoods> wrapper = new LambdaJoinQueryWrapper<>(HotGoods.class);
        wrapper.innerJoin(Hot.class, Hot::getHotPicturesId, HotGoods::getHotId)
                .innerJoin(Goods.class, Goods::getGoodsId, HotGoods::getGoodsId);
        QueryWrapper< HotGoods > queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("rand()");
        List< HotGoods > hotGoodsList = hotGoodsService.list(wrapper);
        Collections.shuffle(hotGoodsList, new Random());
        List<HotVo> hotVoList = new ArrayList<>();
        Set< Integer > addedHotIds = new HashSet<>();
        hotGoodsList.sort(Comparator.comparingInt(hotGoods -> hotGoods.getHot().getHotId()));
        hotGoodsList.forEach(item -> {
            int hotId = item.getHot().getHotId();
            if (!addedHotIds.contains(hotId)) {
                String[] hotImages = hotGoodsList.stream()
                        .filter(hotGoods -> hotGoods.getHot().getHotId() == hotId)
                        .map(hotGoods -> hotGoods.getGoods().getGoodsPicture())
                        .limit(2)
                        .toArray(String[]::new);
                HotVo hotVo = new HotVo();
                BeanUtils.copyProperties(item, hotVo);
                hotVo.setHotId(hotId);
                hotVo.setHotPictures(hotImages);
                hotVo.setHotTitle(item.getHot().getHotTitle());
                hotVo.setHotAlt(item.getHot().getHotAlt());
                hotVo.setHotTarget(item.getHot().getHotTarget());
                hotVo.setHotType(item.getHot().getHotType());
                hotVoList.add(hotVo);
                addedHotIds.add(hotId);
            }
        });
        return R.ok( hotVoList);
    }

    @Operation(summary = "获得热门商品推荐")
    @GetMapping(value = "/getHotGoods")
    public R getHotGoods() {
        LambdaJoinQueryWrapper< HotGoods > wrapper = new LambdaJoinQueryWrapper<>(HotGoods.class);
        wrapper.innerJoin(Hot.class, Hot::getHotPicturesId, HotGoods::getHotId)
                .innerJoin(Goods.class, Goods::getGoodsId, HotGoods::getGoodsId);
        List< HotGoods > hotGoodsList = hotGoodsService.list(wrapper);
        return R.ok( hotGoodsList);
    }

    @Operation(summary = "获得特惠推荐")
    @GetMapping(value = "/getPreference")
    public R getPreference(@RequestParam(name = "page", defaultValue = "1") Long page,
                           @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize) {
        return getRecommendData(page, pageSize, 0, "获得特惠推荐成功");
    }

    @Operation(summary = "获得爆款推荐")
    @GetMapping(value = "/getInVogue")
    public R getInVogue(@RequestParam(name = "page", defaultValue = "1") Long page,
                        @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize) {
        return getRecommendData(page, pageSize, 1, "获得爆款推荐成功");
    }

    @Operation(summary = "获得一站买全")
    @GetMapping(value = "/getOneStop")
    public R getOneStop(@RequestParam(name = "page", defaultValue = "1") Long page,
                        @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize) {
        return getRecommendData(page, pageSize, 2, "获得一站买全成功");
    }

    @Operation(summary = "获得新鲜好物")
    @GetMapping(value = "/getNewShop")
    public R getNewShop(@RequestParam(name = "page", defaultValue = "1") Long page,
                        @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize) {
        return getRecommendData(page, pageSize, 3, "获得新鲜好物成功");
    }

    private R getRecommendData(Long page, Long pageSize, int index, String message) {
        LambdaJoinQueryWrapper< Recommend > wrapper = new LambdaJoinQueryWrapper<>(Recommend.class);
        wrapper.innerJoin(Type.class, Type::getTypeId, Recommend::getTypeId);
        List<Recommend> recommendList = recommendService.list(wrapper);
        Map< Integer, Type > typeMap = typeService.list().stream()
                .collect(Collectors.toMap(Type::getTypeId, Function.identity()));

        Recommend recommend = recommendList.isEmpty() ? null : recommendList.get(index);
        List< RecommendVo > recommendVoList = new ArrayList<>();

        if (recommend != null) {
            String[] typeSplit = recommend.getTypeId().split(",");
            List< Type > types = Arrays.stream(typeSplit)
                    .map(Integer::valueOf)
                    .map(typeMap::get)
                    .collect(Collectors.toList());

            RecommendVo recommendVo = new RecommendVo();
            BeanUtils.copyProperties(recommend, recommendVo);

            List< TypeVo > typeVoList = new ArrayList<>();
            for (Type type : types) {
                TypeVo typeVo = new TypeVo();
                BeanUtils.copyProperties(type, typeVo);
                Page< Goods > goodsPage = new Page<>(page, pageSize);
                QueryWrapper< Goods > queryWrapper = new QueryWrapper<>();
                queryWrapper.orderByDesc("rand()");
                goodsService.page(goodsPage, queryWrapper);
                List< Goods > goodsList = goodsPage.getRecords();
                Long counts = goodsPage.getTotal();
                long currentPageSize = goodsPage.getSize();
                long pages = goodsPage.getPages();
                long currentPage = goodsPage.getCurrent();

                Map< String, Object > map = new HashMap<>();
                map.put("counts", counts);
                map.put("pageSize", currentPageSize);
                map.put("pages", pages);
                map.put("page", currentPage);
                map.put("goodsList", goodsList);
                typeVo.setGoodsItems(map);
                typeVoList.add(typeVo);
            }
            recommendVo.setTypeList(typeVoList);
            recommendVoList.add(recommendVo);
        }
        Map< String, Object > maps = new HashMap<>();
        maps.put("recommendList", recommendVoList);
        return R.ok(maps);
    }

    @Operation(summary = "增加热门推荐")
    @PostMapping(value = "/addHot")
    public R addHot(@RequestBody Hot hot) {
        boolean flag = hotService.save(hot);
        if (flag) {
            return R.ok("增加热门推荐成功");
        } else {
            return R.fail("增加热门推荐失败");
        }
    }

    @Operation(summary = "删除热门推荐数据")
    @DeleteMapping(value = "/deleteHot/{id}")
    public R deleteHot(@PathVariable("id") Integer bannerId) {
        boolean flag = hotService.removeById(bannerId);
        if (flag) {
            return R.ok("删除热门推荐成功");
        } else {
            return R.fail("删除热门推荐失败");
        }
    }

    @Operation(summary = "按照热门推荐编号查询数据")
    @GetMapping(value = "/getHotById/{id}")
    public R getByIdHot(@PathVariable("id") Integer bannerId) {
        Hot hot = hotService.getById(bannerId);
        return R.ok( hot);
    }

    @Operation(summary = "修改热门推荐数据")
    @PutMapping(value = "/updateHot")
    public R updateHot(@RequestBody Hot hot) {
        boolean flag = hotService.updateById(hot);
        if (flag) {
            return R.ok("修改热门推荐成功");
        } else {
            return R.fail("修改热门推荐失败");
        }
    }
}
