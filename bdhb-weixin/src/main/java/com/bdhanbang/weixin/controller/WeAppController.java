package com.bdhanbang.weixin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bdhanbang.base.common.Query;
import com.bdhanbang.weixin.common.AppCommon;
import com.bdhanbang.weixin.entity.OkapiConfig;
import com.bdhanbang.weixin.entity.OkapiConfig.Login;
import com.bdhanbang.weixin.entity.TWeXinOkapi;
import com.bdhanbang.weixin.entity.WeXinData;
import com.bdhanbang.weixin.entity.WeXinLogin;
import com.bdhanbang.weixin.entity.WeXinResoult;
import com.bdhanbang.weixin.entity.WeXinUserinfo;
import com.bdhanbang.weixin.jooq.tables.QWeXinOkapi;
import com.bdhanbang.weixin.jooq.tables.QWeXinUserinfo;
import com.bdhanbang.weixin.service.WeXinOkapiService;
import com.bdhanbang.weixin.service.WeXinUserinfoService;
import com.bdhanbang.weixin.util.AES;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @ClassName: WeAppController
 * @Description: 微信登陆
 * @author yangxz
 * @date 2018年8月4日 上午8:18:28
 * 
 */
@RestController
@RequestMapping("/weapp")
public class WeAppController {

	@Autowired
	private WeXinOkapiService weXinOkapiService;

	@Autowired
	private WeXinUserinfoService weXinUserinfoService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public WeXinResoult login(@RequestHeader(AppCommon.X_WX_TENANT) String tanentId,
			@RequestHeader(AppCommon.X_WX_CODE) String wxCode,
			@RequestHeader(AppCommon.X_WX_ENCRYPTED_DATA) String wxEncryptedData,
			@RequestHeader(AppCommon.X_WX_IV) String wxIv) throws JsonProcessingException {

		WeXinResoult rt = new WeXinResoult();
		WeXinData data = new WeXinData();

		try {

			log.info(String.format("***************tanentId:%s*************", tanentId));

			TWeXinOkapi weXinOkapi = getTWeXinOkapi(tanentId);

			// 从okapi得到token信息，用来跟okapi进行数据交换
			String token = getOkapiToken(weXinOkapi);

			String appId = weXinOkapi.getAppId();
			String appSecret = weXinOkapi.getAppSecret();

			// 得到openId和sessionKey
			WeXinLogin weXinLogin = getWeXinLogin(appId, appSecret, wxCode);
			String sessionKey = weXinLogin.getSession_key();
			String openId = weXinLogin.getOpenid();

			// 角析用户信息
			WeXinUserinfo userinfo = getWeXinUserinfo(wxEncryptedData, sessionKey, wxIv);

			// 保存用户信息
			saveUserinfo(tanentId, userinfo);

			data.setToken(token);
			data.setUserinfo(userinfo);
			data.setSkey(openId);

		} catch (Exception e) {
			log.error(mapper.writeValueAsString(e));

			// 设置错误信息
			data.setError("10001");
			data.setMessage(e.getMessage());

			// 后台出现问题
			rt.setCode(-1);
		}

		rt.setData(data);

		return rt;

	}

	/**
	 * @Title: saveUserinfo
	 * @Description: 保存用户信息
	 * @param @param
	 *            userinfo 设定文件
	 * @return void 返回类型
	 * @throws:
	 */
	private void saveUserinfo(String tenantId, WeXinUserinfo userinfo) {
		List<Query> queryList = new ArrayList<>();

		String realSchema = tenantId + AppCommon.scheam;

		Query query = new Query();

		query.setField("openId");
		query.setValue(userinfo.getOpenId());

		queryList.add(query);

		List<WeXinUserinfo> userList = weXinUserinfoService.queryList(realSchema, QWeXinUserinfo.class,
				WeXinUserinfo.class, queryList);

		// 保存用户信息
		if (Objects.isNull(userList) || userList.size() == 0) {

			userinfo.setId(UUID.randomUUID());
			weXinUserinfoService.insertEntity(realSchema, QWeXinUserinfo.class, userinfo);

		} else {

			userinfo.setId(userList.get(0).getId());

			// 这么做是为了不更新昵称
			userinfo.setNickName(userList.get(0).getNickName());
			weXinUserinfoService.updateEntity(realSchema, QWeXinUserinfo.class, userinfo);

		}

	}

	/**
	 * @Title: getOkapiToken
	 * @Description: 从okapi得到token信息
	 * @param @param
	 *            weXinOkapi
	 * @param @return
	 * @param @throws
	 *            ClientProtocolException
	 * @param @throws
	 *            IOException 设定文件
	 * @return String 返回类型
	 * @throws:
	 */
	private String getOkapiToken(TWeXinOkapi weXinOkapi) throws ClientProtocolException, IOException {

		OkapiConfig okapiConifig = weXinOkapi.getJsonb();

		Login login = okapiConifig.getLogin();
		login.setUsername(weXinOkapi.getUserName());
		login.setPassword(weXinOkapi.getPassword());

		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpPost postMethod = new HttpPost(okapiConifig.getHost());

		postMethod.setHeader("Accept", "application/json,text/plain");
		postMethod.setHeader(AppCommon.X_WX_TENANT, weXinOkapi.getTanentId());
		postMethod.setHeader("Content-type", "application/json; charset=utf-8");
		postMethod.setHeader("Connection", "Close");

		// 构建消息实体
		StringEntity entity = new StringEntity(mapper.writeValueAsString(login), Charset.forName("UTF-8"));
		entity.setContentEncoding("UTF-8");
		// 发送Json格式的数据请求
		entity.setContentType("application/json");

		postMethod.setEntity(entity);

		HttpResponse response = httpClient.execute(postMethod);

		// 检验返回码
		int statusCode = response.getStatusLine().getStatusCode();

		// 关掉连接
		httpClient.close();

		if (statusCode == 201) {
			String token = response.getFirstHeader(AppCommon.X_WX_TOKEN).getValue();
			return token;
		} else {
			// 无数据返回
			return "";
		}

	}

	/**
	 * @Title: getTWeXinOkapi
	 * @Description: 得到数据库设计信息，有微信和okapi的
	 * @param @param
	 *            tanentId
	 * @param @return
	 *            设定文件
	 * @return TWeXinOkapi 返回类型
	 * @throws:
	 */
	private TWeXinOkapi getTWeXinOkapi(String tanentId) {
		List<Query> queryList = new ArrayList<>();

		Query query = new Query();

		query.setField("tanentId");
		query.setValue(tanentId);

		queryList.add(query);

		List<TWeXinOkapi> listData = weXinOkapiService.queryList(tanentId + AppCommon.scheam, QWeXinOkapi.class,
				TWeXinOkapi.class, queryList);

		if (Objects.isNull(listData) || listData.size() == 0) {
			return new TWeXinOkapi();
		} else {
			return listData.get(0);
		}

	}

	/**
	 * @Title: getWeXinLogin
	 * @Description: 后台请求微信登录接口
	 * @param @param
	 *            appId
	 * @param @param
	 *            appSecret
	 * @param @param
	 *            wxCode
	 * @param @return
	 * @param @throws
	 *            ClientProtocolException
	 * @param @throws
	 *            IOException 设定文件
	 * @return WeXinLogin 返回类型
	 * @throws:
	 */
	private WeXinLogin getWeXinLogin(String appId, String appSecret, String wxCode)
			throws ClientProtocolException, IOException {

		StringBuffer urlOverHttps = new StringBuffer();

		urlOverHttps.append("https://api.weixin.qq.com/sns/jscode2session?appid=");
		urlOverHttps.append(appId);
		urlOverHttps.append("&secret=");
		urlOverHttps.append(appSecret);
		urlOverHttps.append("&js_code=");
		urlOverHttps.append(wxCode);
		urlOverHttps.append("&grant_type=authorization_code");

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet getMethod = new HttpGet(urlOverHttps.toString());

		HttpResponse response = httpClient.execute(getMethod);

		HttpEntity h = response.getEntity();
		InputStream inputStream = h.getContent();

		ObjectMapper mapper = new ObjectMapper();
		WeXinLogin w = mapper.readValue(inputStream, WeXinLogin.class);
		inputStream.close();
		httpClient.close();

		return w;
	}

	/**
	 * @Title: getWeXinUserinfo
	 * @Description: 后台解析登录人员信息
	 * @param @param
	 *            wxEncryptedData
	 * @param @param
	 *            sessionKey
	 * @param @param
	 *            wxIv
	 * @param @return
	 * @param @throws
	 *            Exception 设定文件
	 * @return WeXinUserinfo 返回类型
	 * @throws:
	 */
	private WeXinUserinfo getWeXinUserinfo(String wxEncryptedData, String sessionKey, String wxIv) throws Exception {

		Decoder decoder = Base64.getDecoder();

		byte[] result = AES.decrypt(decoder.decode(wxEncryptedData), decoder.decode(sessionKey),
				AES.generateIV(decoder.decode(wxIv)));
		String s = new String(result, "UTF-8");

		ObjectMapper mapper = new ObjectMapper();

		WeXinUserinfo userinfo = mapper.readValue(s, WeXinUserinfo.class);
		return userinfo;
	}
}
