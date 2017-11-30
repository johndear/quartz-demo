//package com.ls.quartz;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.concurrent.CountDownLatch;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.concurrent.FutureCallback;
//import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
//import org.apache.http.nio.client.HttpAsyncClient;
//import org.apache.http.nio.reactor.IOReactorException;
//
//public class Main {
//
////	public static void main(String[] args) {
////		// TODO Auto-generated method stub
////		Runnable run = new Runnable() {
////			public void run() {
////				post();
////			}
////		};
////		
////		Executors.newSingleThreadExecutor().execute(run);
////		
//////		run.run();
////		System.out.println("------ done ----------");
////	}
//	
//	public static void post(){
//		String startTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
//		System.out.println(startTime);
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
//		System.out.println(endTime);
//		
//		
////		DefaultHttpClient httpClient = new DefaultHttpClient();
////		HttpGet httpGet = new HttpGet(url);
////		ResponseHandler<String> responseHandler = new BasicResponseHandler();
////		HttpResponse response = httpClient.execute(httpGet);
////		HttpEntity httpEntity = response.getEntity();
////		String html= null;
////		if(httpEntity!=null){
////			html= new String(IOUtils.toByteArray(httpEntity.getContent()));   
////		}
////		System.out.println("Time at " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + html);
//		}
//	
//	/**
//	  * @param args
//	  * @throws IOReactorException 
//	  * @throws InterruptedException 
//	  */
//	 public static void main(String[] args) throws IOReactorException, InterruptedException {
//	  final HttpAsyncClient httpclient = new DefaultHttpAsyncClient();
//	  httpclient.start();
//	  HttpGet[] requests = new HttpGet[] {
//	    new HttpGet("http://www.apache.org/"),
//	    new HttpGet("https://www.verisign.com/"),
//	    new HttpGet("http://www.google.com/")
//	  };
//	  final CountDownLatch latch = new CountDownLatch(requests.length);
//	  try {
//	   for (final HttpGet request: requests) {
//	    httpclient.execute(request, new FutureCallback<HttpResponse>() {
//	     public void completed(final HttpResponse response) {
//	      latch.countDown();
//	      System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
//	     }
//	     public void failed(final Exception ex) {
//	      latch.countDown();
//	      ex.printStackTrace();
//	     }
//	     public void cancelled() {
//	      latch.countDown();
//	     }
//	    });
//	   }
//	   System.out.println("Doing...");
//	  }finally {
//	   latch.await();
//	   httpclient.shutdown();
//	  }
//	  System.out.println("Done");
//	 }
//
//}
