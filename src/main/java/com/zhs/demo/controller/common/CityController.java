package com.zhs.demo.controller.common;

import com.zhs.demo.model.common.City;
import com.zhs.demo.service.CommonService;
import com.zhs.demo.utils.Json;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Zhang on 2018/9/27.
 */
@Controller
@RequestMapping("cityInfo")
public class CityController {

    @Autowired
    private CommonService commonService;

    private Logger logger= LoggerFactory.getLogger(this.getClass());


    /**
     * 导入最新的行政区域信息
     * @param file  省市县信息
     * @return
     */
    @RequestMapping(value = "uploadCity", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Json uploadCity(@RequestParam("file") MultipartFile file){

        try{

            //每次导入新的行政区域信息，清除老的信息
            commonService.deleteAllCity();

            if (file.isEmpty()){
                return Json.fail("导入失败，文件内容为空");
            }

            String fileName=file.getOriginalFilename();   //上传的文件名称，包括后缀名
            String suffixName=fileName.substring(fileName.lastIndexOf("."));  //文件的后缀名

            if(!suffixName.equals(".txt")){
                return Json.fail("导入失败，只支持格式为.txt文件");
            }

            logger.info("开始导入文件，上传的文件名称为[{}]",fileName);

            InputStream inputStream=file.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            List<String> list = new ArrayList<>();

            String line = null;

            while ((line = reader.readLine()) != null){
                list.add(line);
            }

            List<City> cityList = new ArrayList<>();


            if (list.size() > 0){
                for (String cityLine:list){
                    if (StringUtils.isNotBlank(cityLine)){
                        cityLine = cityLine.replace("\t"," ");
                        List<String> newList= Arrays.asList(cityLine.split(" "));
                        String code = newList.get(0);  //行政区域编号
                        String name = newList.get(1);  //行政区域名称

                        City city =  new City();

                        if (StringUtils.isBlank(code) || StringUtils.isBlank(name)){
                            continue;
                        }

                        city.setCode(code);
                        city.setName(name);
                        if (code.endsWith("0000")){    //省份
                            city.setType("province");
                        }else if(code.endsWith("00") && !(code.substring(2,3).equals("00"))){   //市
                            city.setType("city");
                        }else {   //县（区）
                            city.setType("county");
                        }
                        cityList.add(city);
                    }
                }
            }

            if (cityList.size() > 0){
                commonService.saveCity(cityList);
            }


            return Json.ok("行政区域信息更新成功");

        }catch (Exception e){
            logger.error("行政区域信息更新失败");
            return Json.fail("行政区域信息更新失败");
        }

    }


    /**
     *
     * @param code  行政区域编码
     * @param type  行政区域类别
     * @return
     */
    @RequestMapping(value = "getAreaList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Json getAreaList(String code, String type){
        Json json = new Json();

        try {
            List<City> list = commonService.getCityList(code,type);
            json.setSuccess(true);
            json.setObj(list);
        }catch (Exception e){
            json.setSuccess(false);
            json.setMsg("查询行政信息失败");
        }

        return json;
    }

}
