package demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Api(tags = {"图片上传或下载接口"})
@RestController
@RequestMapping("/image")
public class ImageController {

    private String prefix = "images/";

    @ApiOperation("下载指定图片")
    @GetMapping("/download")
    public void getFileByUrl(@RequestParam(value = "img_url", required = true) String url,
                             HttpServletResponse response) {
        Path path = Paths.get(prefix, url);
        System.out.println("图片绝对路径：" + path.toAbsolutePath().toString());
        if (!Files.exists(path)) {
            System.out.println("不存在图片路径：" + path.toAbsolutePath().toString());
//            return Result.BAD().data("该图片不存在").build();
        }
        try {
            response.reset();
            response.setContentType("application/x-download;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(url.getBytes("utf-8"), "ISO8859-1"));
            Files.copy(path, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        return Result.OK().data("成功下载图片").build();
    }


    public static void main(String[] args) {
        String url = "images/games/111222/icon.jpg";
        Path path = Paths.get(url);
        System.out.println(Files.exists(path));
        System.out.println(path.toAbsolutePath().toString());
    }
}
