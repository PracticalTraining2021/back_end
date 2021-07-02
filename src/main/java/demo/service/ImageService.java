package demo.service;


import demo.exception.BusinessException;
import demo.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
        String filePath = prefix + fileName;

        File dist = new File(filePath);
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
