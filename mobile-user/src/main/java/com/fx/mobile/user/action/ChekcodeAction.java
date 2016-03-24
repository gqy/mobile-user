package com.fx.mobile.user.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fx.mobile.user.action.BaseAction;


@Scope("prototype")
@Controller("chekcodeAction")
public class ChekcodeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public  DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 检查验证码是否正确
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 */
	public String ckCode() throws IOException, ParseException {

		StringBuffer sb = new StringBuffer();
		// sb.append("[");
		sb.append("{\"");

		String result = "0";
		// 获取用户输入的验证码
		String inputCode = this.getRequest().getParameter("checkCode");
		// 手机号码
		String userPhoneNumb = this.getRequest().getParameter("userPhoneNumb");
		
		//验证码有效时间
		String expireseIn = this.getRequest().getParameter("expireseIn");
		Date date2 = df.parse(expireseIn);
		//获取当前时间
		String dat1 =df.format(new Date());
		Date date1 = df.parse(dat1);
		Long dif = date1.getTime() - date2.getTime();// 得到两者之间的时间差
		Long day = dif / (1000);// 转换成秒
		
		// 获取短信验证码 session中的
		String code = (String) this.getRequest().getSession().getAttribute("code");
		// System.out.println("是否失效："+code);
		// 发送时间
		// String tosend_time = "";
		// 短信有效时间
		// String valid_time = "";
		// if(jbPhone==""&&jbPhone==null){
		// result = "0";// 失败
		// } else {
		if (code.equals(inputCode)) {
			if(day>600||(code == "" && code == null)){//判断是否已失效
				result = "-1";// 失败
				sb.append("msg\":\"");
				sb.append(result + "\"");
				System.out.println("验证码过期");
			}else{
				result = "1";// 成功
				sb.append("msg\":\"");
				sb.append(result + "\"");
				System.out.println("验证码验证成功！");
			}
		} else {
			result = "0";// 失败
			sb.append("msg\":\"");
			sb.append(result + "\"");
			System.out.println("验证失败");
		}
		// }
		sb.append("}");
		System.out.println(sb);
		PrintWriter out = this.getResponse().getWriter();
		// out.write(result.toString());
		out.print(sb.toString());
		return null;
	}
}
