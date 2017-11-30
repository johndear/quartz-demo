package com.ls.quartz.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

@SuppressWarnings("deprecation")
public class HttpUtil {
	
	@SuppressWarnings({ "unused", "rawtypes"})
	public static String get(String url, Map map) {
		StringBuffer sb = new StringBuffer();
		Set entrySet = map.entrySet();
		Iterator iter = entrySet.iterator();
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			sb.append("&");
			sb.append(key);
			sb.append("=");
			sb.append(val);
		}
		String visitUrl = null;
		if (url.indexOf("?") == -1) {
			if (sb.length() > 0) {
				sb.deleteCharAt(0);
			}
			visitUrl = url + "?" + sb.toString();
		} else {
			visitUrl = url + sb.toString();
		}

		String html = null;
		DefaultHttpClient httpClient = null;
		try{
			httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				html = new String(IOUtils.toByteArray(httpEntity.getContent()));
			}
		}catch(Exception e){
		} finally {
			httpClient.close();
		}
		// System.out.println(html);

		return html;
	}
	
	public static void AsyncGet(String url) {
		final long startTime = System.currentTimeMillis();
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		try {
			// Start the client
			httpclient.start();
			// One most likely would want to use a callback for operation result
			final CountDownLatch latch1 = new CountDownLatch(1);
			final HttpGet request = new HttpGet(url);
			httpclient.execute(request, new FutureCallback<HttpResponse>() {

				public void completed(final HttpResponse response2) {
					latch1.countDown();
					long endTime = System.currentTimeMillis();
					System.out.println(request.getRequestLine() + "->"
							+ response2.getStatusLine() + ", 执行时长(ms)：" + (endTime - startTime));
				}

				public void failed(final Exception ex) {
					latch1.countDown();
					System.out.println(request.getRequestLine() + "->" + ex);
				}

				public void cancelled() {
					latch1.countDown();
					System.out.println(request.getRequestLine() + " cancelled");
				}

			});
			latch1.await();
			
		}catch(Exception e){
			
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void AsyncPost(String url, Map<String, String> paramsMap) {
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		try {
			// Start the client
			httpclient.start();
			// One most likely would want to use a callback for operation result
			final CountDownLatch latch1 = new CountDownLatch(1);
			final HttpPost request = new HttpPost(url);
			List <NameValuePair> params = new ArrayList<NameValuePair>(); 
			if(paramsMap!=null){
				for (Entry<String, String> entry : paramsMap.entrySet()) {
					params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}
	        request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));  
			httpclient.execute(request, new FutureCallback<HttpResponse>() {

				public void completed(final HttpResponse response2) {
					latch1.countDown();
					System.out.println(request.getRequestLine() + "->"
							+ response2.getStatusLine());
				}

				public void failed(final Exception ex) {
					latch1.countDown();
					System.out.println(request.getRequestLine() + "->" + ex);
				}

				public void cancelled() {
					latch1.countDown();
					System.out.println(request.getRequestLine() + " cancelled");
				}

			});
			latch1.await();
			
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
