package com.lzr.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzr.dto.ImageHolder;
import com.lzr.dto.ShopExecution;
import com.lzr.entity.Area;
import com.lzr.entity.PersonInfo;
import com.lzr.entity.Shop;
import com.lzr.entity.ShopCategory;
import com.lzr.enums.ShopStateEnum;
import com.lzr.exceptions.ShopOperationException;
import com.lzr.service.AreaService;
import com.lzr.service.ShopCategoryService;
import com.lzr.service.ShopService;
import com.lzr.util.CodeUtil;
import com.lzr.util.HttpServletRequestUtil;
import com.lzr.util.ImageUtil;
import com.lzr.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.lang.model.element.NestingKind;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
        Map<String,Object> modelmap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId <= 0){
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if(currentShopObj == null){
                modelmap.put("redirect",true);
                modelmap.put("url","/shopadmin/shoplist");
            }else {
                Shop currentShop = new Shop();
                currentShop.setShopId(shopId);
                request.getSession().setAttribute("currentShop",currentShop);
                modelmap.put("redirect",false);
            }
        }else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelmap.put("redirect",false);
        }
        return modelmap;
    }

    @RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopList(HttpServletRequest request){
        Map<String,Object> modelmap = new HashMap<String, Object>();
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        user.setName("ziran");
        request.getSession().setAttribute("user",user);
        user = (PersonInfo) request.getSession().getAttribute("user");
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShopList(shopCondition,0,100);
            modelmap.put("shopList",se.getShopList());
            modelmap.put("user",user);
            modelmap.put("success",true);
        }catch (Exception e){
            modelmap.put("success",false);
            modelmap.put("errmsg",e.getMessage());
        }
        return modelmap;
    }

    @RequestMapping(value = "/getshopinitinfo" ,method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopInitInfo(){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopId(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //request.getParameter(shopId) 取前端的值
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId > -1){
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop",shop);
                modelMap.put("areaList",areaList);
                modelMap.put("success",true);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }

    /**
     * 店铺注册
     * @param request
     * @return
     */
    //RequestMethod.POST from 表单提交
    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    //Map<String,Object> 键值对 转换成json对象
    @ResponseBody
    //request 前端传入的所有信息
    private Map<String,Object> registerShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码输入错误");
            return modelMap;
        }
        //1.接受并转化相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        //接受前端的文件流
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }
        //2.注册店铺
        if(shop != null && shopImg != null){
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
           /* File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
            try {
                shopImgFile.createNewFile();
            }catch (IOException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }

            try {
                inputStreamToFile(shopImg.getInputStream(),shopImgFile);
            }catch (IOException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }*/
            ShopExecution se;
           try {
               ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
               se = shopService.addShop(shop,imageHolder);
               //前端传入的店铺的state值为0才能添加
               if(se.getState() == ShopStateEnum.CHECK.getState()){
                   modelMap.put("success",true);
                   //取出该用户可以操作的店铺列表
                   @SuppressWarnings("unchecked")
                   List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                   if (shopList == null || shopList.size()== 0){
                       shopList = new ArrayList<Shop>();
                       shopList.add(se.getShop());
                       request.getSession().setAttribute("shopList",shopList);
                   }else {
                       shopList.add(se.getShop());
                       request.getSession().setAttribute("shopList",shopList);
                   }
               }else {
                   modelMap.put("success",false);
                   modelMap.put("errMsg",se.getStateInfo());
               }
           }catch (ShopOperationException e){
               modelMap.put("success",false);
               modelMap.put("errMsg",e.getMessage());
           }catch (IOException e){
               modelMap.put("success",false);
               modelMap.put("errMsg",e.getMessage());
           }

            return modelMap;
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }
    }

    /**
     * 店铺信息修改
     * @param request
     * @return
     */
    //RequestMethod.POST from 表单提交
    @RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
    //Map<String,Object> 键值对 转换成json对象
    @ResponseBody
    //request 前端传入的所有信息
    private Map<String,Object> modifyShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码输入错误");
            return modelMap;
        }
        //1.接受并转化相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        //接受前端的文件流
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        //2.修改店铺信息
        if(shop != null && shop.getShopId() != null){

            ShopExecution se;
            try {
                if(shopImg == null){
                    se = shopService.modifyShop(shop,null);
                }else{
                    ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                    se = shopService.modifyShop(shop,imageHolder);
                }

                //前端传入的店铺的state值为0才能添加
                if(se.getState() == ShopStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",se.getStateInfo());
                }
            }catch (ShopOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }catch (IOException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }

            return modelMap;
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺ID");
            return modelMap;
        }
    }

    /*private static void inputStreamToFile(InputStream ins, File file){
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = ins.read(buffer)) != -1){
                os.write(buffer,0,bytesRead);
            }
        }catch (Exception e){
            throw new RuntimeException("调用inputStreamToFile产生异常" + e.getMessage());
        }finally {
            try {
                if(os != null){
                    os.close();
                }
                if (ins != null){
                    ins.close();
                }
            }catch (IOException e){
                throw new RuntimeException("inputStreamToFile关闭io流产生异常" + e.getMessage());
            }

        }
    }*/
}
