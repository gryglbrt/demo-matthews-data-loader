package unicon.matthews.dataloader.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import unicon.matthews.dataloader.MatthewsClient;

@SpringBootApplication
public class App {
  
  @Value("${matthews.baseurl:http://localhost:9966}")
  private String baseUrl;
  
  @Value("${matthews.apikey}")
  private String key;
  
  @Value("${matthews.apisecret}")
  private String secret;
 
  @Bean
  public MatthewsClient matthewsClient() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    httpHeaders.add("X-Requested-With", "XMLHttpRequest");
    
    return 
        new MatthewsClient
        .Builder()
        .withBaseUrl(baseUrl)
        .withHttpHeaders(httpHeaders)
        .withKey(key)
        .withRestTemplate(new RestTemplate())
        .withSecret(secret)
        .build();
  }
  
  public static void main(String[] args) throws IOException {
    ApplicationContext ctx = SpringApplication.run(App.class, args);
    ctx.getBean(DemoDataLoader.class).run();
  }

}
