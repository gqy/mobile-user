package com.fx.mobile.user.h5.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import Decoder.BASE64Decoder;

import com.fx.marketing.hessian.IFileUploadRemote;
import com.fx.mobile.bean.JSONResult;
import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.action.BaseAction;
import com.fx.mobile.user.action.SpringContextUtil;
import com.fx.mobile.user.constants.enums.UserStatusEnum;
import com.fx.mobile.user.service.ResourceProperties;
import com.fx.mobile.user.service.UserOperateService;
import com.fx.mobile.util.CustomizedPropertyConfigurer;

@Controller
@RequestMapping(value = "/profile/h5")
public class UserProfileController extends BaseAction{
	
	
	private static final String ERRORSTATUS = "000";

	@Autowired
	private UserOperateService operateService;
	
	@Autowired
	public  ResourceProperties urlProperties;
	
	
	/**
	 * 修改
	 */
	@RequestMapping("/toedit")
	public String toEdit(HttpServletResponse response) {
		return "/muser/edit_body";
	}
		
	
	@RequestMapping(value = "/edit",method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult updateUserInfo(HttpServletRequest request) throws UnsupportedEncodingException {
		log.debug("调用更新用户信息接口,参数==="+request.getQueryString());
		String id = request.getParameter("id");
		String userName = request.getParameter("username");
		String nickname = request.getParameter("nickname");
		String sex = request.getParameter("sex");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String openId = request.getParameter("openId");
		String birthday = request.getParameter("birthday");
		String userPhoneNumb = request.getParameter("userPhoneNumb");

		UserOperate userOp = new UserOperate();
		userOp.setOpenId(openId);
		userOp.setUserName(userName);
		userOp.setUserPhoneNumb(userPhoneNumb);

		JSONResult jsonResult = null;
		try {
			
			userOp.setNickname(nickname);
			userOp.setSex(sex);
			userOp.setProvince(province);
			userOp.setCity(city);
			userOp.setBirthday(birthday);
			operateService.updateUserInfo(userOp);
			userOp = operateService.findUserByOpenId(openId);
			 userOp.setUserPassword("");
		    userOp.setFigureurl(urlProperties.getFileHttpUrl()+userOp.getFigureurl());
			if(log.isDebugEnabled()){
				log.debug("login user :"+userOp);
			}
			 jsonResult = setRtnResult(UserStatusEnum.USER_UPDATEINFO_SUC);
			 jsonResult.setData(userOp);
		} catch (Exception e) {
			log.error(e, e);
			jsonResult = setRtnResult(UserStatusEnum.USER_UPDATEINFO_FAIL);
		}

		return jsonResult;
	}
	
	//用户上传头像
	// 修改用户密码
	@RequestMapping(value = "/uploadheadportrait", method = { RequestMethod.POST})
	public void  uploadHeadPortrait() throws FileUploadException, IOException{
		log.debug("start upload........");
	getRequest().setCharacterEncoding("utf-8");  
    //获得磁盘文件条目工厂。  
    DiskFileItemFactory factory = new DiskFileItemFactory();  
    //获取文件上传需要保存的路径，upload文件夹需存在。  
    String path = "D:\\test";  
    //设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。  
    factory.setRepository(new File(path));  
    //设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。  
    factory.setSizeThreshold(1024*1024);  
    //上传处理工具类（高水平API上传处理？）  
    ServletFileUpload upload = new ServletFileUpload(factory);  
       
    try{  
        //调用 parseRequest（request）方法  获得上传文件 FileItem 的集合list 可实现多文件上传。  
        List<FileItem> list = (List<FileItem>)upload.parseRequest(getRequest());  
        for(FileItem item:list){  
            //获取表单属性名字。  
            String name = item.getFieldName();  
            //如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。  
            if(item.isFormField()){  
                //获取用户具体输入的字符串，  
                String value = item.getString();  
                getRequest().setAttribute(name, value);  
            }  
            //如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。  
            else{   
                //获取路径名  
                String value = item.getName();  
                //取到最后一个反斜杠。  
                int start = value.lastIndexOf("\\");  
                //截取上传文件的 字符串名字。+1是去掉反斜杠。  
                String filename = value.substring(start+1);  
                getRequest().setAttribute(name, filename);  
                   
                /*第三方提供的方法直接写到文件中。 
                 * item.write(new File(path,filename));*/ 
                //收到写到接收的文件中。  
                OutputStream out = new FileOutputStream(new File(path,filename));  
                InputStream in = item.getInputStream();  
                   
                int length = 0;  
                byte[] buf = new byte[1024];  
                System.out.println("获取文件总量的容量:"+ item.getSize());  
                   
                while((length = in.read(buf))!=-1){  
                    out.write(buf,0,length);  
                }  
                in.close();  
                out.close();  
            }  
        }  
    }catch(Exception e){  
        e.printStackTrace();  
    }  
    }
	
	
	
	
}
