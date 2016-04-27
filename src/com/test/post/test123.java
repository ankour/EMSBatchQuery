package com.test.post;
import java.io.BufferedReader;  
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;  
import java.io.FileReader;
import java.io.FileWriter;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.io.PrintWriter;  
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;  
import java.net.URL;  
import java.net.URLConnection;  
import java.net.URLEncoder;  
import java.nio.charset.Charset;  
import java.util.ArrayList;
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.StringTokenizer;  


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
  
public class test123 {  
  
 public final static String CONTENT_TYPE = "Content-Type";  
 private static String [] str1;
 private static String [] str2;
 private static ArrayList<String> array=new ArrayList<String>();
 public static void main(String []args) throws IOException {  
  // login  
  //验证码的位置  
  //Content content = getRandom("GET", "http://localhost:8080/back/random.action", null, null, false,"d:/");  
	// post();
	 File file=new File("e:\\in.txt");
	 BufferedReader bw = new BufferedReader(new FileReader(file));
	 
	 String msg;
	 while((msg=bw.readLine() )!= null)
	 {
		 array.add(msg);
	 }
	 int n=array.size();
	 int p2=n%10;
	 int p1=n/10;
	 String mailNum = null;
	if(p2==0){
		 for(int i=0;i<p1;i++)
		 {	mailNum="";
			 for(int j=0;j<10;j++)
			 {
				mailNum+=array.get((i*10)+j)+"\r"; 
			 }
			 //System.out.println(mailNum);
			 post(mailNum);
			 System.out.println("总计"+array.size()+"条      \t"+"剩余"+(array.size()-(i+1)*10)+"条      \t "+" 当前第"+(i*10)+1+"---->"+(i*10+10)+"条");
		 }
	 }
	 else{
		 for(int i=0;i<p1;i++)
		 {	mailNum="";
			 for(int j=0;j<10;j++)
			 {
				mailNum+=array.get((i*10)+j)+"\r";
			 }
			//System.out.println("11111"+mailNum);
			 post(mailNum);
			 System.out.println("总计"+array.size()+"条     \t"+"剩余"+(array.size()-(i+1)*10)+"条     \t "+" 当前第"+(i*10+1)+"---->"+(i*10+10)+"条");
		 }
		 mailNum="";
		 for(int p=0;p<p2;p++)
		 {
			 mailNum+=array.get((p1*10)+p)+"\r";
		 }
		// System.out.println("22222"+mailNum);
		 post(mailNum);
		 System.out.println("已全部完成查询");
	 } 
	
	
 }  
 public static void post(String mailNum)
 {
	 Content content = getRandom("GET", "http://www.ems.com.cn/ems/rand", null, null, false,"e:/");  
	  // build request headers & do rate of user review  
	  List<String> lsit = content.getHeaders().get("Set-Cookie");  
	  try {
		cleanImage.cleanImage1(new File("e:\\code1.jpg"), "e:\\");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  Map<String, String> resmap = new HashMap<String, String>();    
	  if (lsit != null) {  
	   StringBuffer sb = new StringBuffer();  
	   boolean isLast = false;  
	   int i = 0;  
	   for (String val : lsit) {  
	    i++;  
	    if (i == lsit.size()) {  
	     isLast = true;  
	    }  
	    int pos = val.indexOf("=");  
	    if (pos != -1) {  
	     String cookieName = val.substring(0, pos);  
	     String cookieVal = val.substring(pos + 1);  
	     //System.out.println(cookieName+":"+cookieVal);  
	     cookieVal = cookieVal.split(";")[0];  
	     if (isLast) {  
	      sb.append(cookieName + "=" + cookieVal);  
	     } else {  
	      sb.append(cookieName + "=" + cookieVal + ";");  
	     }  
	    }  
	   }     
	  // System.out.println("sb.toString() = "+sb.toString());     
	   resmap.put("Cookie", sb.toString());  
	  }
	  String a="";  
	  String pathcode = "e:\\code1.jpg";
	  try {  
          a = new ocrhelper().recognizeText(new File(pathcode));  
          System.out.println("验证码自动输入"+"  "+a); 
      } catch (IOException e) {  
          e.printStackTrace();  
      } catch (Exception e) {  
          e.printStackTrace();  
      } 
	  
	   /* System.out.print("请输入验证码：");  
	    BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));  
	    try {  
	   a=strin.readLine();  
	  }  catch (IOException e) {  
	   e.printStackTrace();  
	  }  
	   System.out.println("输入的数是："+a);  */
	     
	  String Url = "http://www.ems.com.cn/ems/order/multiQuery_t";  
	  Map<String, String> paramMap = new HashMap<String, String>();  
	  paramMap.put("checkCode",a+"");  
	  paramMap.put("muMailNum",mailNum);  
	  content = curl("POST", Url, paramMap, resmap, false,"");  
	   //System.out.println(content.getBody());
	  //System.out.println("content.getBody()= " + content==null?"no body":content.getBody());  

	  String body=content.getBody();  
	  Document doc=Jsoup.parse(body);
	  Elements link_sp = doc.select("span[style$=#005BAC]");
	  Elements link_pn = doc.select("p:contains(处理地点：)");
	  //Elements link_n = doc.select("");
	  str1=new String[link_sp.size()];
	  str2=new String[link_pn.size()];
	  int i=0,j=0;
	  for (Element p : link_sp) {  
	      //System.out.println(p.text()); 
		  String text=p.text().trim();
		//System.out.println(text);
		String  text1=text.replaceAll(Jsoup.parse("&nbsp;").text()," ");
		str1[i++]=text1; 
		 }  
	 // System.out.println(link_sp.size());
	  for (Element n : link_pn) {  
	      //System.out.println(p.text()); 
		  String text=n.text();
//		System.out.println(text);
		str2[j++]=text; 
		 
		  
	  }  
	 // System.out.println(link_pn.size());
	  
	  for(int k=0;k<j;k++)
	  {
		  String context=str1[k]+"\t"+str2[k]+"\r\n";
		  contentToTxt("e:\\out.txt", context);
	  }
	  
 }
 public static void contentToTxt(String filePath, String content) {  
     String str = new String(); //原有txt内容  
     String s1 = new String();//内容更新  
     try {  
         File f = new File(filePath);  
         if (f.exists()) {  
             //System.out.print("文件存在");  
         } else {  
             //System.out.print("文件不存在");  
             f.createNewFile();// 不存在则创建  
         }  
         BufferedReader input = new BufferedReader(new FileReader(f));  

         while ((str = input.readLine()) != null) {  
             s1 += str + "\n";  
         }  
         //System.out.println(s1);  
         input.close();  
         s1 += content;  

         BufferedWriter output = new BufferedWriter(new FileWriter(f));  
         output.write(s1);  
         output.close();  
     } catch (Exception e) {  
         e.printStackTrace();  

     }  
 }  
 public static Content curl(String method, //方法类型  
          String sUrl,//要解析的URL  
          Map<String, String> paramMap,
          Map<String, String> requestHeaderMap,//存放COOKIE的map  
          boolean isOnlyReturnHeader,  
          String path) {//存放文件路径  
    System.out.println("---------------"+"已成功查询"+"-------------------");  
    Content content = null;  
    HttpURLConnection httpUrlConnection = null;  
    InputStream in = null;  
    try {  
     URL url = new URL(sUrl);  
     boolean isPost = "POST".equals(method);  
       
     if (method == null || (!"GET".equalsIgnoreCase(method) && !"POST".equalsIgnoreCase(method))) {  
      method = "POST";  
     }  
       
     URL resolvedURL = url;  
     URLConnection urlConnection = resolvedURL.openConnection();  
     httpUrlConnection = (HttpURLConnection) urlConnection;  
     httpUrlConnection.setRequestMethod(method);  
     httpUrlConnection.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");  
       
     // Do not follow redirects, We will handle redirects ourself  
     httpUrlConnection.setInstanceFollowRedirects(false);  
     urlConnection.setDoOutput(true);  
     urlConnection.setDoInput(true);  
     urlConnection.setConnectTimeout(5000);  
     urlConnection.setReadTimeout(5000);  
     urlConnection.setUseCaches(false);  
     urlConnection.setDefaultUseCaches(false);  
     // set request header  
     if (requestHeaderMap != null) {  
     for (Map.Entry<String, String> entry : requestHeaderMap.entrySet()) {  
      String key = entry.getKey();  
      String val = entry.getValue();       
      if (key != null && val != null) {  
       urlConnection.setRequestProperty(key, val);  
      }  
     }  
     }  
     if (isPost) {  
      urlConnection.setDoOutput(true);  
      ByteArrayOutputStream bufOut = new ByteArrayOutputStream();  
      boolean firstParam = true;  
      for (Map.Entry<String, String> entry : paramMap.entrySet()) {  
       String encName = URLEncoder.encode(entry.getKey(), "UTF-8");  
       if (firstParam) {  
        firstParam = false;  
       } else {  
        bufOut.write((byte) '&');  
       }  
       String encValue = URLEncoder.encode(entry.getValue(),"UTF-8");  
       bufOut.write(encName.getBytes("UTF-8"));  
       bufOut.write((byte) '=');  
       bufOut.write(encValue.getBytes("UTF-8"));  
      }  
      byte[] postContent = bufOut.toByteArray();  
      if (urlConnection instanceof HttpURLConnection) {  
       ((HttpURLConnection) urlConnection).setFixedLengthStreamingMode(postContent.length);  
      }  
      OutputStream postOut = urlConnection.getOutputStream();  
      postOut.write(postContent);  
      postOut.flush();  
      postOut.close();  
     }  
     httpUrlConnection.connect();  
     int responseCode = httpUrlConnection.getResponseCode();  
       
     // We handle redirects ourself  
     if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {  
     String location = httpUrlConnection.getHeaderField("Location");  
     URL newAction = new URL(url, location);  
     // Recurse  
     StringBuffer newUrlSb = new StringBuffer(newAction.getProtocol() + "://" + newAction.getHost());  
     if (newAction.getPort() != -1) {  
      newUrlSb.append(":" + newAction.getPort());  
     }  
     if (newAction.getPath() != null) {  
      newUrlSb.append(newAction.getPath());  
     }  
     if (newAction.getQuery() != null) {  
      newUrlSb.append("?" + newAction.getQuery());  
     }  
     if (newAction.getRef() != null) {  
      newUrlSb.append("#" + newAction.getRef());  
     }  
       
     return curl("POST", newUrlSb.toString(), paramMap, requestHeaderMap,isOnlyReturnHeader,path);  
     } else if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {  
     byte[] bytes = new byte[0];  
     if (!isOnlyReturnHeader) {  
      if(isPost){  
       in = httpUrlConnection.getInputStream();  
       ByteArrayOutputStream bout = new ByteArrayOutputStream();  
       byte[] buf = new byte[1024];  
       while (true) {  
        int rc = in.read(buf);  
        if (rc <= 0) {  
         break;  
        } else {  
         bout.write(buf, 0, rc);  
        }  
       }  
       bytes = bout.toByteArray();  
       in.close();  
      }  
     }  
     // only fetch Content-Length and Last-Modified header  
     String encoding = null;  
     if (encoding == null) {  
      encoding = getEncodingFromContentType(httpUrlConnection.getHeaderField(CONTENT_TYPE));  
     }      
      content = new Content(sUrl, new String(bytes, encoding),httpUrlConnection.getHeaderFields());  
     }  
    } catch (Exception e) {  
    return null;  
    } finally {  
    if (httpUrlConnection != null) {  
     httpUrlConnection.disconnect();  
    }  
   }  
    return content;  
   }  
   
   
 public static Content getRandom(String method,   
          String sUrl,//要解析的url  
          Map<String, String> paramMap, //存放用户名和密码的map  
          Map<String, String> requestHeaderMap,//存放COOKIE的map  
          boolean isOnlyReturnHeader,  
          String path) {  
    
  Content content = null;  
  HttpURLConnection httpUrlConnection = null;  
  InputStream in = null;  
  try {  
   URL url = new URL(sUrl);  
   boolean isPost = "POST".equals(method);  
   if (method == null || (!"GET".equalsIgnoreCase(method) && !"POST".equalsIgnoreCase(method))) {  
    method = "POST";  
   }  
   URL resolvedURL = url;  
   URLConnection urlConnection = resolvedURL.openConnection();  
   httpUrlConnection = (HttpURLConnection) urlConnection;  
   httpUrlConnection.setRequestMethod(method);  
   httpUrlConnection.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");  
   // Do not follow redirects, We will handle redirects ourself  
   httpUrlConnection.setInstanceFollowRedirects(false);  
   httpUrlConnection.setDoOutput(true);  
   httpUrlConnection.setDoInput(true);  
   httpUrlConnection.setConnectTimeout(5000);  
   httpUrlConnection.setReadTimeout(5000);  
   httpUrlConnection.setUseCaches(false);  
   httpUrlConnection.setDefaultUseCaches(false);  
   httpUrlConnection.connect();  
     
   int responseCode = httpUrlConnection.getResponseCode();  
     
    if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {  
    byte[] bytes = new byte[0];  
    if (!isOnlyReturnHeader) {  
       DataInputStream ins = new DataInputStream(httpUrlConnection.getInputStream());  
       //验证码的位置  
           DataOutputStream out = new DataOutputStream(new FileOutputStream(path+"/code1.jpg"));  
           byte[] buffer = new byte[4096];  
           int count = 0;  
           while ((count = ins.read(buffer)) > 0) {  
            out.write(buffer, 0, count);  
           }  
          out.close();  
          ins.close();  
    }  
    String encoding = null;  
    if (encoding == null) {  
     encoding = getEncodingFromContentType(httpUrlConnection.getHeaderField(CONTENT_TYPE));  
    }      
    content = new Content(sUrl, new String(bytes, encoding),httpUrlConnection.getHeaderFields());  
   }  
  } catch (Exception e) {  
   return null;  
  } finally {  
   if (httpUrlConnection != null) {  
    httpUrlConnection.disconnect();  
   }  
  }  
  return content;  
 }  
  
 public static String getEncodingFromContentType(String contentType) {  
  String encoding = null;  
  if (contentType == null) {  
   return null;  
  }  
  StringTokenizer tok = new StringTokenizer(contentType, ";");  
  if (tok.hasMoreTokens()) {  
   tok.nextToken();  
   while (tok.hasMoreTokens()) {  
    String assignment = tok.nextToken().trim();  
    int eqIdx = assignment.indexOf('=');  
    if (eqIdx != -1) {  
     String varName = assignment.substring(0, eqIdx).trim();  
     if ("charset".equalsIgnoreCase(varName)) {  
      String varValue = assignment.substring(eqIdx + 1).trim();  
      if (varValue.startsWith("\"") && varValue.endsWith("\"")) {  
       // substring works on indices  
       varValue = varValue.substring(1,varValue.length() - 1);  
      }  
      if (Charset.isSupported(varValue)) {  
       encoding = varValue;  
      }  
     }  
    }  
   }  
  }  
  if (encoding == null) {  
   return "UTF-8";  
  }  
  return encoding;  
 }  
  
   
  
 // 这个是输出  
 public static boolean inFile(String content, String path) {  
  PrintWriter out = null;  
  File file = new File(path);  
  try {  
   if (!file.exists()) {  
    file.createNewFile();  
   }  
   out = new PrintWriter(new FileWriter(file));  
  
   out.write(content);  
   out.flush();  
   return true;  
  } catch (Exception e) {  
   e.printStackTrace();  
  } finally {  
   out.close();  
  }  
  return false;  
 }  
  
 public static String getHtmlReadLine(String httpurl){  
  String CurrentLine="";  
  String TotalString="";  
  InputStream urlStream;  
  String content="";  
  
  try {  
   URL url = new URL(httpurl);  
  
   HttpURLConnection connection = (HttpURLConnection)url.openConnection();  
  
   connection.connect();  
   System.out.println(connection.getResponseCode());  
   urlStream = connection.getInputStream();  
  
   BufferedReader reader = new BufferedReader(  
  
   new InputStreamReader(urlStream,"utf-8"));  
  
   while ((CurrentLine = reader.readLine()) != null) {  
    TotalString += CurrentLine+"\n";  
   }  
  
   content = TotalString;     
  
  } catch (Exception e) {}  
  
  return content;    
 }  
}  

class Content {  
 private String url;  
 private String body;  
 private Map<String, List<String>> m_mHeaders = new HashMap<String, List<String>>();  
  
 public Content(String url, String body, Map<String, List<String>> headers) {  
  this.url = url;  
  this.body = body;  
  this.m_mHeaders = headers;  
 }  
  
 public String getUrl() {  
  return url;  
 }  
  
 public String getBody() {  
  return body;  
 }  
  
 public Map<String, List<String>> getHeaders() {  
  return m_mHeaders;  
 }  
  
}  