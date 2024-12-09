package artifactory_saas.compuzign_home_assignment.commands;


import artifactory_saas.compuzign_home_assignment.model.Repository;
import artifactory_saas.compuzign_home_assignment.services.ArtifactoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class RepositoryCommands {
    private final ArtifactoryService artifactoryService;

    public RepositoryCommands(ArtifactoryService artifactoryService) {
        this.artifactoryService = artifactoryService;
    }

    @ShellMethod("List all repositories")
    public String listRepositories() {
        List<Repository> repos = artifactoryService.listRepositories()
                .collectList()
                .block();

        return repos.stream()
                .map(repo -> repo.getKey() + " (" + repo.getType() + ")")
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod("Create a new repository")
    public String createRepository(
            @ShellOption String key,
            @ShellOption String type,
            @ShellOption(defaultValue = "local") String packageType
    ) {
        Repository newRepo = Repository.builder()
                .key(key)
                .type(type)
                .packageType(packageType)
                .build();

        return artifactoryService.createRepository(newRepo)
                .map(repo -> "Repository created: " + repo.getKey())
                .block();
    }

    @ShellMethod("Update an existing repository")
    public String updateRepository(
            @ShellOption String key,
            @ShellOption(defaultValue = "") String newType
    ) {
        Repository updatedRepo = Repository.builder()
                .key(key)
                .type(newType)
                .build();

        return artifactoryService.updateRepository(key, updatedRepo)
                .map(repo -> "Repository updated: " + repo.getKey())
                .block();
    }
}
