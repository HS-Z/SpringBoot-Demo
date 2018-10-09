package com.zhs.demo.controller.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


/**
 * Created by Zhang on 2018/9/27.
 */
@Controller
@RequestMapping("file")
public class FileController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "toFile", method = {RequestMethod.GET, RequestMethod.POST})
    public String toFile(){
        return "file/fileTest";
    }


    /**
     * 单个文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "uploadFile", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file){

        try{

            if (file.isEmpty()){
                return "上传失败，上传文件为空";
            }

            String fileName=file.getOriginalFilename();   //上传的文件名称
            logger.info("开始上传文件，上传的文件名称为[{}]",fileName);

            String suffixName=fileName.substring(fileName.lastIndexOf("."));  //获取文件的后缀名

            String filePath="D://file//";   //上传文件的路径

            String path = filePath + fileName;

            File dest = new File(path);

            // 检测目类是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();  // 新建文件夹
            }

            file.transferTo(dest);// 文件写入

            return "上传成功";

        }catch (Exception e){
            logger.error("开始上传文件");
            return "文件上传失败";
        }

    }


    /**
     * 批量上传
     * @param request
     * @return
     */
    @RequestMapping(value = "batchUploadFile", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String batchUploadFile(HttpServletRequest request){

        List<MultipartFile> files=((MultipartHttpServletRequest)request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream outputStream = null;

        if (files != null && files.size() > 0){

            for (int i=0; i<files.size(); i++){
                String filePath="D://file//";

                file=files.get(i);

                if (file.isEmpty()){
                    return "上传失败，上传文件为空";
                }else {

                    try {
                        byte[] bytes=file.getBytes();
                        outputStream = new BufferedOutputStream(new FileOutputStream(new File(filePath + file.getOriginalFilename())));//设置文件路径及名字
                        outputStream.write(bytes);// 写入
                        outputStream.close();

                    }catch (Exception e){
                        outputStream = null;
                        return "第 " + i + " 个文件上传失败  ==> " + e.getMessage();
                    }
                }
            }

            return "文件上传成功";
        }

        return "批量上传失败，上为空";
    }



    @RequestMapping("downloadFile")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "aim_test.txt";// 设置文件名，根据业务需要替换成要下载的文件名
        if (fileName != null) {
            //设置文件路径
            String realPath = "D://aim//";
            File file = new File(realPath, fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

}
