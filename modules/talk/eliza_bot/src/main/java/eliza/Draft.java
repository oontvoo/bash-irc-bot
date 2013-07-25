/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eliza;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch.Cse;
import com.google.api.services.customsearch.Customsearch.Cse.List;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Scanner;
/**
 *
 * @author vy
 */
public class Draft
{
    
    
    
//    public static void main(String[] args) {
//		try {
//			InetAddress inetAddress = InetAddress.getLocalHost();
//			displayStuff("local host", inetAddress);
//			System.out.print("--------------------------");
//			inetAddress = InetAddress.getByName("penguinv.dhcp.tripadvisor.com");
//			displayStuff("www.google.com", inetAddress);
//			System.out.print("--------------------------");
//                        
//                        
////			InetAddress[] inetAddressArray = InetAddress.getAllByName("www.google.com");
////			
////                        
////                        
////                        for (int i = 0; i < inetAddressArray.length; i++) {
////				displayStuff("www.google.com #" + (i + 1), inetAddressArray[i]);
////			}
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void displayStuff(String whichHost, InetAddress inetAddress) {
//		System.out.println("--------------------------");
//		System.out.println("Which Host:" + whichHost);
//		System.out.println("Canonical Host Name:" + inetAddress.getCanonicalHostName());
//		System.out.println("Host Name:" + inetAddress.getHostName());
//		System.out.println("Host Address:" + inetAddress.getHostAddress());
//	}
        
    public static void main(String[] args) throws IOException
    {
//        HttpRequestInitializer init;
//      Customsearch search = new Customsearch(new NetHttpTransport(), new JacksonFactory(), null);
//       
//      Cse cse = search.cse();
//      
//      //014648620843487951246:3ja_jg_nro0
//      List list = search.cse().list("weather").setKey("AIzaSyAbgYyCdSciyzTs5bJp51gtjGYSb4MIS9o").setCx("014648620843487951246:3ja_jg_nro0");
//      
//      Search res = list.execute();
//      
//      for (Result r : res.getItems())
//          System.out.println(r);
//      
      
        
 
    String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
    String search = "location";
    String charset = "UTF-8";

    URL url = new URL(google + URLEncoder.encode(search, charset));
    Reader reader = new InputStreamReader(url.openStream(), charset);
    
    Scanner i = new Scanner(reader);
    
    while (i.hasNextLine())
        System.out.println(i.nextLine());
//    GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
//
//    // Show title and URL of 1st result.
//    System.out.println(results.getResponseData().getResults().get(0).getTitle());
//    System.out.println(results.getResponseData().getResults().get(0).getUrl());
}
      
      
      
    
}


/*
  <repositories>
        <repository>
            <id>google-api-services</id>
            <url>https://oss.sonatype.org/content/repositories/releases/</url>
        </repository>
        <repository>
            <id>google-api-services-beta</id>
            <url>http://google-api-client-libraries.appspot.com/mavenrepo</url>
        </repository>
    </repositories>

    <dependencies>
       <!-- must install manually -->
       <dependency>
           <groupId>com.google.apis</groupId>
           <artifactId>google-api-servies-customsearch</artifactId>
           <version>v1-rev21-1.15.0-rc</version>    
       </dependency>  
       
       <dependency>
           <groupId>com.google.api-client</groupId>
           <artifactId>google-api-client-java6</artifactId>
           <version>1.15.0-rc</version>
       </dependency>
       <dependency>
           <groupId>com.google.oauth-client</groupId>
           <artifactId>google-oauth-client-jetty</artifactId>
           <version>1.15.0-rc</version>
       </dependency>
       <dependency>
           <groupId>com.google.http-client</groupId>
           <artifactId>google-http-client-jackson2</artifactId>
           <version>1.15.0-rc</version>
       </dependency>
    </dependencies>

*/