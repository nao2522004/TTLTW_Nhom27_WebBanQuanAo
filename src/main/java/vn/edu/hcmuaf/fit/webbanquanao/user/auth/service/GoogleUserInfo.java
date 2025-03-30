package vn.edu.hcmuaf.fit.webbanquanao.user.auth.service;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.util.Map;

public class GoogleUserInfo {
    private static final String GOOGLE_USERINFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static String getEmail(String accessToken) throws IOException {
        HttpRequest request = new NetHttpTransport().createRequestFactory()
                .buildGetRequest(new GenericUrl(GOOGLE_USERINFO_URL))
                .setHeaders(new HttpHeaders().setAuthorization("Bearer " + accessToken));

        HttpResponse response = request.execute();
        Map<String, Object> userInfo = JSON_FACTORY.fromInputStream(response.getContent(), Map.class);
        return (String) userInfo.get("email");
    }

    public static String getFullName(String accessToken) throws IOException {
        HttpRequest request = new NetHttpTransport().createRequestFactory()
                .buildGetRequest(new GenericUrl(GOOGLE_USERINFO_URL))
                .setHeaders(new HttpHeaders().setAuthorization("Bearer " + accessToken));

        HttpResponse response = request.execute();
        Map<String, Object> userInfo = JSON_FACTORY.fromInputStream(response.getContent(), Map.class);
        return (String) userInfo.get("name");
    }
}
