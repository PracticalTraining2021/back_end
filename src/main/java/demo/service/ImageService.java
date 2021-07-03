package demo.service;


import demo.exception.BusinessException;
import demo.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ImageService {

    private Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Value("${prefix}")
    public String prefix = "/images/";

    public void uploadImg(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String distPath = prefix + fileName;

        doUpload(multipartFile, distPath);
    }

    public void uploadImg(MultipartFile multipartFile, String midPath) {
        if (StringUtils.isEmpty(midPath)) uploadImg(multipartFile);

        String fileName = multipartFile.getOriginalFilename();
//        添加中间路径
        String distPath = prefix + midPath + fileName;
        doUpload(multipartFile, distPath);
    }

    private void doUpload(MultipartFile multipartFile, String distPath) {
        File dist = new File(distPath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dist));
            bos.write(multipartFile.getBytes());
            bos.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new BusinessException(ErrorCode.SERVER_EXCEPTION, "上传图片过程出现位置错误");
        }
    }

}
