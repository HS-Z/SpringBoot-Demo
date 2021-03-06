package com.zhs.demo.controller.file;

import com.zhs.demo.utils.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
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
     * @param file  上传的文件信息
     * @param obj  从前台传来的其他参数（预留）
     * @return
     */
    @RequestMapping(value = "uploadFile", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Json uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("obj") String obj){

        try{

            if (file.isEmpty()){
                return Json.fail("上传失败，上传文件为空");
            }

            String fileName=file.getOriginalFilename();   //上传的文件名称，包括后缀名
            logger.info("开始上传文件，上传的文件名称为[{}]",fileName);

            System.out.println("文件的大小为："+file.getContentType());
            System.out.println("文件的名称为："+file.getName());
            System.out.println("文件的大小为："+file.getOriginalFilename());
            System.out.println("文件的大小为："+file.getSize());

            String filePath="D://file//";   //上传文件的路径

            String newFileName=this.newFileName(filePath,fileName);

            String path = filePath + newFileName;

            System.out.println("保存的文件路径："+path);

            File dest = new File(path);

            // 检测目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();  // 新建文件夹
            }

            file.transferTo(dest);// 文件写入

            return Json.ok("上传成功");

        }catch (Exception e){
            logger.error("文件上传失败");
            return Json.fail("文件上传失败");
        }

    }


    /**
     * 批量上传
     * @param request
     * @param obj  从前台传来的其他参数（预留）
     * @return
     */
    @RequestMapping(value = "batchUploadFile", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Json batchUploadFile(HttpServletRequest request, String obj){

        List<MultipartFile> files=((MultipartHttpServletRequest)request).getFiles("fileMore");
        MultipartFile file = null;
        BufferedOutputStream outputStream = null;

        if (files != null && files.size() > 0){

            for (int i=0; i<files.size(); i++){
                String filePath="D://file//";

                file=files.get(i);

                if (file.isEmpty()){
                    return Json.fail("上传失败，文件为空");
                }else {

                    try {

                        String fileName=file.getOriginalFilename();   //上传的文件名称，包括后缀名
                        String newFileName=this.newFileName(filePath,fileName);
                        byte[] bytes=file.getBytes();
                        outputStream = new BufferedOutputStream(new FileOutputStream(new File(filePath + newFileName)));//设置文件路径及名字
                        outputStream.write(bytes);// 写入
                        outputStream.close();

                    }catch (Exception e){
                        outputStream = null;
                        return Json.fail("第 " + i + " 个文件上传失败");
                    }
                }
            }

            return Json.ok("上传成功");
        }

        return Json.fail("上传失败，上传文件为空");
    }


    /**
     *
     * @param request
     * @param response
     * @param obj  从前台传来的其他参数（预留）
     * @return
     */
    @RequestMapping(value = "downloadFile", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Json downloadFile(HttpServletRequest request, HttpServletResponse response, String obj) {
        String fileName = "ac.txt";// 设置文件名，根据业务需要替换成要下载的文件名
        if (fileName != null) {
            //设置文件路径
            String realPath = "D://file//";
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

                    return Json.ok("下载成功");

                } catch (Exception e) {
                    e.printStackTrace();
                    return Json.fail("下载失败");
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return Json.fail("下载失败");
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return Json.fail("下载失败");
                        }
                    }
                }
            }else {
                return Json.fail("下载失败，下载的文件为空");
            }

        }else {
            return Json.fail("下载失败，下载的文件为空");
        }
    }


    /**
     * 判断当前文件名在当前路径下是否重复，重复则生成新的文件名称
     * @param filePath  路径
     * @param fileName  文件名
     * @return
     */
    public String newFileName(String filePath, String fileName){

        // 获得指定路径下的文件
        File file = new File(filePath);

        // 获得该文件夹内所有的文件
        File[] array = file.listFiles();

        ArrayList<String> fileNameList=new ArrayList<>();  //所有文件的名称集合

        for (int i=0; i<array.length; i++){
            if (array[i].isFile()){  //判断是否是文件
                fileNameList.add(array[i].getName());
            }
        }

        if (fileNameList.size() > 0){

            boolean repeat1=fileNameList.contains(fileName);  //判断上传的文件名称是否已存在

            if (repeat1){  //文件名称重复，生成一个新的文件名
                String stFileName=fileName.substring(0,fileName.lastIndexOf("."));  //文件名称，不包含后缀名
                String suffixName=fileName.substring(fileName.lastIndexOf("."));  //文件的后缀名
                for (int j=1; j<10000; j++){  //循环10000次都找不到不重复的名称，我是不信的

                    String newFileName=stFileName+"("+j+")"+suffixName;  //新生成的文件名称
                    boolean repeat=fileNameList.contains(newFileName);  //判断新生成的文件名称是否也重复
                    if (!repeat){  //不重复
                        fileName=newFileName;
                        break;
                    }
                }
            }
        }

        return fileName;
    }


}
