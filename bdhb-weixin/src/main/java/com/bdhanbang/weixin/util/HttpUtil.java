package com.bdhanbang.weixin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpUtil {

	public static String httpsRequest(String url, String string, String xml) {

		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);

			StringEntity entity = new StringEntity(xml, Charset.forName("UTF-8"));
			httpPost.setEntity(entity);

			HttpResponse response = httpClient.execute(httpPost);

			HttpEntity h = response.getEntity();
			InputStream inputStream = h.getContent();

			String result = new BufferedReader(new InputStreamReader(inputStream)).lines()
					.collect(Collectors.joining(System.lineSeparator()));

			return result;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
