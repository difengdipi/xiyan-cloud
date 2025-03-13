package com.ruoyi.file.service;

import com.ruoyi.common.core.web.domain.AjaxResult;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/13
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Service
public class OssSysFileServiceImpl implements ISysFileService{
    @Autowired
    private FileStorageService fileStorageService;//注入实列
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "/";
        FileInfo fileInfo = fileStorageService.of(file)
                .setPath(format) //保存到相对路径下，为了方便管理，不需要可以不写
                .upload();
        return fileInfo.getUrl();
    }
}
