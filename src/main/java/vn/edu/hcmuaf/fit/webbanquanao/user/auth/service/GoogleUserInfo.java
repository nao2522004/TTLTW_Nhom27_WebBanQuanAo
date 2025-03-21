package vn.edu.hcmuaf.fit.webbanquanao.user.auth.service;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.util.Map;

public class GoogleUserInfo {
    private static final String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";

    public static String getEmail(String accessToken) throws IOException {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        HttpRequestFactory requestFactory = transport.createRequestFactory();

        GenericUrl url = new GenericUrl(GOOGLE_USER_INFO_URL);
        HttpRequest request = requestFactory.buildGetRequest(url);
        request.getHeaders().setAuthorization("Bearer " + accessToken);

        HttpResponse response = request.execute();
        JsonParser parser = jsonFactory.createJsonParser(response.getContent());

        Map<String, Object> userInfo = parser.parse(Map.class);
        return (String) userInfo.get("email");
    }
}
