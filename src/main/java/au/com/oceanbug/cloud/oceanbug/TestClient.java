package au.com.oceanbug.cloud.oceanbug;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TestClient {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url  = "http://localhost:8080";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println(response.toString());
    }
}
