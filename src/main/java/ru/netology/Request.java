package ru.netology;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Request {
    private final String method;
    private final String path;
    private final String version;
    private static List<NameValuePair> queryParams;

    private Request(String method, String path, String version) throws URISyntaxException {
        this.method = method;
        this.path = path;
        this.version = version;
        queryParams = getQueryParams();
        System.out.println("queryParams: " + getQueryParams());
        System.out.println("queryParam for 'last': " + getQueryParam("last") + " (for example)");
    }

    public static Request parseRequest(String[] parts) throws URISyntaxException {
        String method = parts[0];
        String path = parts[1];
        String version = parts[2];
        return new Request(method, path, version);
    }

    public List<NameValuePair> getQueryParams() throws URISyntaxException {
        return new URIBuilder(new URI(path)).getQueryParams();
    }

    public String getQueryParam(String name) {
        return queryParams.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .map(NameValuePair::getValue)
                .orElse(null);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        if (path.contains("?")) {
            return path.substring(0, path.indexOf("?"));
        }
        return path;
    }

    public String getVersion() {
        return version;
    }
}
