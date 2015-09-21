package information.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtil {

	private CloseableHttpClient httpclient = null;

	public HttpUtil() {
		try {
			httpclient = this.createHttpClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public HttpUtil(boolean noSSL) {
		try {
			httpclient = this.createHttpClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private CloseableHttpClient createHttpClient() throws Exception{
		CloseableHttpClient httpclient = null;
		httpclient = HttpClients.createDefault();
		if (httpclient== null) {
			throw new NullPointerException("HTTPCLIENT没有创建成功");
		}
		return httpclient;
	};

	public HttpEntity sendPost(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
		HttpEntity entity = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (params != null && params.size() > 0) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					nvps.add(new BasicNameValuePair(param.getKey(), param.getValue()));
				}
			}
			if (headers != null && headers.size() > 0) {
				for (Map.Entry<String, String> header : headers.entrySet()) {
					httpPost.addHeader(new BasicHeader(header.getKey(), header.getValue()));
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				entity = response.getEntity();
			} finally {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	public HttpEntity sendGet(String url, Map<String, String> headers) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				httpGet.addHeader(new BasicHeader(header.getKey(), header.getValue()));
			}
		}
		HttpEntity entity = response.getEntity();
		return entity;
	}
	
	public void closeCon() {
		try {
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public CloseableHttpClient getHttpclient() {
		return httpclient;
	}
	public void setHttpclient(CloseableHttpClient httpclient) {
		this.httpclient = httpclient;
	}
}
