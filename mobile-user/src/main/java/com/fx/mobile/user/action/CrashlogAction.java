package com.fx.mobile.user.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



import com.fx.mobile.model.CrashLog;
import com.fx.mobile.user.service.CrashLogService;


@Controller("crashlogAction")
public class CrashlogAction extends BaseAction {

	public DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static com.fx.mobile.user.service.ResourceProperties urlProperties;// 获取配置资源服务地址
	
	public String urlHttp;
//	public String urlHttp = urlProperties.getFileHttpUrl();/ 获取资源服务器地址，，，做拼接
//
	@Autowired
	public CrashLogService crashLogService;

	// 用户上传记录日记
	@SuppressWarnings("unchecked")
	public String uploadCrashDiary() throws FileUploadException, IOException {
		// String openid = this.getRequest().getParameter("openId");
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		String crashLogUrl = "";
		try {
			List<FileItem> list = upload.parseRequest(getRequest());
			for (int i = 0; i < list.size(); i++) {
				FileItem fileItem = list.get(i);
				if (fileItem.isFormField() == false) {
					String name = fileItem.getName().substring(
							fileItem.getName().lastIndexOf("\\") + 1);
					String type = name.substring(name.lastIndexOf(".") + 1,
							name.length());
					if (!"".equals(name)) {
						if (!"log".equalsIgnoreCase(type)) {
							this.resultMap.put("status", 0);
							this.resultMap.put("msg", "上传格式错误！");
							log.info("格式错误");
							return "jsonObject";
						}
						// 保存路径
						SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
						String datedir = df.format(new Date());
						String randomdir = RandomStringUtils
								.randomAlphanumeric(10);
						String uploadPath = "/upload/crashLog/" + datedir + "/"
								+ randomdir + "/";
						// 文件转为字节，上传到资源服务器做准备
						InputStream in = fileItem.getInputStream();
						// 创建临时文
						String rootpath = this.getRequest().getSession()
								.getServletContext().getRealPath("\\");
						ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
						int ch;
						while ((ch = in.read()) != -1) {
							bytestream.write(ch);
						}
						byte data[] = bytestream.toByteArray();
						File fileTemp = new File(rootpath + name);
						FileOutputStream out = new FileOutputStream(fileTemp);
						try {
							out.write(data);
							bytestream.close();
						} catch (Exception e) {
							log.info(e.getMessage());
						} finally {
							out.close();
						}
						// 调用远程资源服务器Hessian接口，上传log文件（字节流形式）
//						IFileUploadRemote remote = null;
//						crashLogUrl = remote.upload(data, uploadPath, name);
						int index = crashLogUrl.indexOf("/upload");
						String str_temp = new String();
						str_temp = crashLogUrl.substring(index, crashLogUrl
								.length());
						crashLogUrl = urlHttp + str_temp;

						// 调用保存方法，将地址信息保存在数据库中，提供查询使用
						CrashLog crashLog = new CrashLog();
						crashLog.setUrl(str_temp);
						crashLog.setDescribes("");
						crashLog.setAddDate(dfs.format(new Date()));
						crashLogService.addCrashLog(crashLog);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.resultMap.put("status", 0);
			this.resultMap.put("msg", "上传错误");
			log.info("上传异常");
			return "jsonObject";
		}
		log.info("上传成功");
		this.resultMap.put("status", 1);
		this.resultMap.put("msg", "上传成功");
		return "jsonObject";
	}
}
