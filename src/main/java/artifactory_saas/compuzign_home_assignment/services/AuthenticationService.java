package artifactory_saas.compuzign_home_assignment.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${artifactory.base-url}")
    private String baseUrl;

    @Value("${artifactory.token}")
    private String apiKey;

//    private final WebClient.Builder webClientBuilder;


    public HttpHeaders createAuthHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        return headers;
    }

    public Mono<Boolean> authenticate(String username, String password) {
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();

        return client.get()
                .uri("/api/system/ping")
                .headers(headers -> {
                    String credentials = username + ":" + password;
                    String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
                    headers.set("Authorization", "Basic " + encodedCredentials);
                })
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> response != null && response.contains("OK"))
                .onErrorReturn(false);
    }

    public String generateToken(String username, String password) {
        // In a real-world scenario, this would interact with Artifactory's token generation API
        String credentials = username + ":" + password;
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
