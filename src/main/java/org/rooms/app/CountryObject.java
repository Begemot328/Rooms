package org.rooms.app;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CountryObject {
    private static final String baseURL = "https://http://api.ipstack.com/";
    private static Map <String, String> country = new HashMap<>();
    private static final String key = "66208b3215aa57c0865a949ffcbb39a1";
    private String code;
    private String name;
    private final String ip;

    public CountryObject(String ip) throws CountryException {
        this.ip = ip;
        processAddress(ip);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryObject)) return false;
        CountryObject that = (CountryObject) o;
        return Objects.equals(country, that.country) && Objects.equals(key, that.key) && code.equals(that.code) && name.equals(that.name) && ip.equals(that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, ip);
    }

    public void processAddress(String ip) throws CountryException {
        final JSONObject response;
        country.put("access_key", key);

        String url = getURL(baseURL + ip, country);
        try {
            response = JsonReader.read(url);
        } catch (IOException e) {
            throw new CountryException(e);
        }
        name = response.getString("country_name");
        code = response.getString("country_code");
    }

    private static String getURL(String baseURL, final Map<String, String> params) {
        StringBuilder builder = new StringBuilder(baseURL);
        builder.append("?");
        for (String key :params.keySet()) {
            builder.append(key).append("=").append(URLEncoder.encode(params.get(key), StandardCharsets.UTF_8))
                    .append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public String toString() {
        return code + " " + country + " " + ip;
    }
}
