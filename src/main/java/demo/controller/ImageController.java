package demo.controller;

import demo.vo.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Api(tags = {"图片上传或下载接口"})
@RestController
@RequestMapping("/image")
public class ImageController {

    @Value("${prefix}")
    private String prefix = "/images/";

    @PostMapping("/upload")
    public Result uploadImg(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String filePath = prefix + fileName;
        File dist = new File(filePath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dist));
            bos.write(multipartFile.getBytes());
            bos.close();
            return Result.OK().data("上传图片成功").build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.BAD().data("上传图片失败").build();
    }


    public static void main(String[] args) {
        String url = "images/games/111222/icon.jpg";
        Path path = Paths.get(url);
        System.out.println(Files.exists(path));
        System.out.println(path.toAbsolutePath().toString());
    }
}
