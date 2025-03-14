package com.ruoyi.category.controller;

import com.ruoyi.category.domain.Banner;
import com.ruoyi.category.service.BannerService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.domain.SysFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/banner/")
@Tag(name = "显示banner")
public class BannerController {

    @Autowired
    RemoteFileService remoteFileService;

    @Autowired
    private BannerService bannerService;

    @Operation(summary = "获得所有的banner")
    @GetMapping(value = "/getAllBanners")
    public R getAllBanner(@RequestParam(name = "distributionSite", defaultValue = "1") int distributionSite) {
        List<Banner> banners = bannerService.list();

        // 商品分类轮播图
        if (distributionSite == 2) {
            Random random = new Random();
            Collections.shuffle(banners, random);
            String[] randomImages = {
                    "http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-04-15/dfc11bb0-4af5-4e9b-9458-99f615cc685a.jpg",
                    "http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-04-15/4a79180a-1a5a-4042-8a77-4db0b9c800a8.jpg",
                    "http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-04-15/1ba86bcc-ae71-42a3-bc3e-37b662f7f07e.jpg",
                    "http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-04-15/6d202d8e-bb47-4f92-9523-f32ab65754f4.jpg",
                    "http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-04-15/e83efb1b-309c-46f7-98a3-f1fefa694338.jpg"
            };
            for (int i = randomImages.length - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                String temp = randomImages[i];
                randomImages[i] = randomImages[j];
                randomImages[j] = temp;
            }
            for (int i = 0; i < banners.size(); i++) {
                banners.get(i).setImgUrl(randomImages[i % randomImages.length]);
            }
        }
        return R.ok(banners);
    }

    @Operation(summary = "增加轮播图")
    @PostMapping(value = "/addBanner")
    public R addBanner(@RequestBody Banner banner) {
        boolean flag = bannerService.save(banner);
        if (flag) {
            return R.ok("增加轮播图成功");
        } else {
            return R.fail("增加轮播图失败");
        }
    }

    @Operation(summary = "图像上传")
    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R uploadFile(@RequestPart(value = "file") MultipartFile file) {
        //调用业务方法--TODO:调用feign接口实现上传
        R<SysFile> sysfile = remoteFileService.upload(file);
        String url = sysfile.getData().getUrl();
        if (url != null) {
            return R.ok( url);
        } else {
            return R.fail("图像上传失败");
        }
    }

    @Operation(summary = "删除轮播图数据")
    @DeleteMapping(value = "/deleteBanner/{id}")
    public R deleteBanner(@PathVariable("id") Integer bannerId) {
        boolean flag = bannerService.removeById(bannerId);
        if (flag) {
            return R.ok("删除轮播图成功");
        } else {
            return R.fail("删除轮播图失败");
        }
    }

    @Operation(summary = "按照轮播图编号查询数据")
    @GetMapping(value = "/getBannerById/{id}")
    public R getByIdBanner(@PathVariable("id") Integer bannerId) {
        Banner banner = bannerService.getById(bannerId);
        return R.ok( banner);
    }

    @Operation(summary = "修改轮播图数据")
    @PutMapping(value = "/updateBanner")
    public R updateBanner(@RequestBody Banner banner) {
        boolean flag = bannerService.updateById(banner);
        if (flag) {
            return R.ok("修改轮播图成功");
        } else {
            return R.fail("修改轮播图失败");
        }
    }
}
