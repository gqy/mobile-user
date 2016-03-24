package com.fx.mobile.weixin.connect;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class WeiXinConnectException extends Exception{
	
	private static final long serialVersionUID = 8029876118142755615L;
	private int statusCode = -1;
	  public WeiXinConnectException(String msg)
	  {
	    super(msg);
	  }

	  public WeiXinConnectException(Exception cause) {
	    super(cause);
	  }

	  public WeiXinConnectException(String msg, int statusCode) throws JSONException {
	    super(msg);
	    this.statusCode = statusCode;
	  }

	  public WeiXinConnectException(String msg, JSONObject json, int statusCode) throws JSONException {
	    super(msg + "\n error:" + json.getString("msg"));
	    this.statusCode = statusCode;
	  }

	  public WeiXinConnectException(String msg, Exception cause) {
	    super(msg, cause);
	  }

	  public WeiXinConnectException(String msg, Exception cause, int statusCode) {
	    super(msg, cause);
	    this.statusCode = statusCode;
	  }

	  public int getStatusCode()
	  {
	    return this.statusCode;
	  }
}
