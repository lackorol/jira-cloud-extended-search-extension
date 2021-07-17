package sk.eea.jira.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Component
public class RunAfterStartup {
    private static final String ATLASSIAN_CLOUD_PROVISIONING_URL = "https://cgirolnik.atlassian.net";
    private static final String PORT = ":443";
    private static final String ATLASSIAN_CLOUD_UPM_URL = ATLASSIAN_CLOUD_PROVISIONING_URL + PORT + "/rest/plugins/1.0/?os_authType=basic";
    private static final String ACCEPT_HEADER_UPM = "Accept";
    private static final String ACCEPT_HEADER_UPM_VALUE = "application/vnd.atl.plugins.installed+json";
    private static final String ADMIN_USERNAME = "ladislav.rolnik@cgi.com";
    private static final String TOKEN = "0aMOs4jdNnCucB6UE4LpD020";

    private static final String ATLASSIAN_CLOUD_UPLOAD_URL = ATLASSIAN_CLOUD_PROVISIONING_URL + PORT + "/rest/plugins/1.0/?token=";
    private static final String ACCEPT_HEADER_UPLOAD = "Accept";
    private static final String ACCEPT_HEADER_UPLOAD_VALUE = "application/json";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/vnd.atl.plugins.install.uri+json";


    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${addon.base-url}")
    private String baseUrl;

    private static String upm;


    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        System.out.println(String.format("Installing spring boot application to the %s", ATLASSIAN_CLOUD_PROVISIONING_URL));
        upm = getUpmToken();
        installApp();
    }

    private String getUpmToken() {
        ResponseEntity<String> response = webClientBuilder.clientConnector(
                new ReactorClientHttpConnector(
                        HttpClient.newConnection().compress(true)))

                .build()
                .get()
                .uri(ATLASSIAN_CLOUD_UPM_URL)
                .header(ACCEPT_HEADER_UPM, ACCEPT_HEADER_UPM_VALUE)
                .headers(httpHeaders -> httpHeaders.setBasicAuth(ADMIN_USERNAME, TOKEN))
                .retrieve()
                .toEntity(String.class)
                .block();

        final String[] upm = new String[1];
        response.getHeaders().forEach((key, value) -> {
            if (key.contains("upm-token")) {
                System.out.println(value);
                upm[0] = value.get(0);
            }
        });
        return upm[0];
    }

    void installApp() {
        ResponseEntity<String> response = webClientBuilder.clientConnector(
                new ReactorClientHttpConnector(
                        HttpClient.newConnection().compress(true)))

                .build()
                .post()
                .uri(ATLASSIAN_CLOUD_UPLOAD_URL + upm)
                .header(ACCEPT_HEADER_UPLOAD, ACCEPT_HEADER_UPLOAD_VALUE)
                .header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
                .headers(httpHeaders -> httpHeaders.setBasicAuth(ADMIN_USERNAME, TOKEN))
                .bodyValue("{\"pluginUri\":" + "\""+ baseUrl + "\"" +", \"pluginName\": \"One Extension\"}")
                .retrieve()
                .toEntity(String.class)
                .block();

        System.out.println(response.getBody());
    }
}
