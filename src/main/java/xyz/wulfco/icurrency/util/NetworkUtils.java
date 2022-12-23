package xyz.wulfco.icurrency.util;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;

public class NetworkUtils {
    public static JsonObject decodeJson(String json) {
        try {
            JsonReader jsonReader = Json.createReader(new StringReader(json));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return Json.createObjectBuilder().add("message", "Failed to decode JSON").add("status", "fail").build();
        }
    }

    public static JsonObject post(String url, JsonObject json) {
        try {
            // Send the request
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(json.toString()));
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");

            // Get the response
            String responseString = EntityUtils.toString(httpclient.execute(post).getEntity());

            return decodeJson(responseString);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JsonObject get(String url) {
        try {
            // Send the request
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);

            // Get the response
            String responseString = EntityUtils.toString(httpclient.execute(httpget).getEntity());

            return decodeJson(responseString);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
