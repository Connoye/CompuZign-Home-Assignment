package artifactory_saas.compuzign_home_assignment.services;

import artifactory_saas.compuzign_home_assignment.model.Repository;
import artifactory_saas.compuzign_home_assignment.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class ArtifactoryService {
//    private final WebClient.Builder webClientBuilder;
    private final AuthenticationService authService;

    @Value("${artifactory.base-url}")
    private String baseUrl;

    public ArtifactoryService(AuthenticationService authService) {
        this.authService = authService;
    }

    private WebClient createClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(headers -> headers.addAll(authService.createAuthHeader()))
                .build();
    }

    public Mono<String> systemPing() {
        return createClient()
                .get()
                .uri("/api/system/ping")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<Map<String, Object>> systemVersion() {
        return createClient()
                .get()
                .uri("/api/system/version")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
    }

    public Mono<Map<String, Object>> getStorageInfo() {
        return createClient()
                .get()
                .uri("/api/storageinfo")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
    }

    public Mono<User> createUser(User user) {
        return createClient()
                .post()
                .uri("/api/security/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<Void> deleteUser(String username) {
        return createClient()
                .delete()
                .uri("/api/security/users/{username}", username)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Flux<Repository> listRepositories() {
        return createClient()
                .get()
                .uri("/api/repositories")
                .retrieve()
                .bodyToFlux(Repository.class);
    }

    public Mono<Repository> createRepository(Repository repository) {
        return createClient()
                .post()
                .uri("/api/repositories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(repository)
                .retrieve()
                .bodyToMono(Repository.class);
    }

    public Mono<Repository> updateRepository(String repoKey, Repository repository) {
        return createClient()
                .put()
                .uri("/api/repositories/{repoKey}", repoKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(repository)
                .retrieve()
                .bodyToMono(Repository.class);
    }
}
