package artifactory_saas.compuzign_home_assignment.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import artifactory_saas.compuzign_home_assignment.services.ArtifactoryService;

@ShellComponent
public class SystemCommands {

    private final ArtifactoryService artifactoryService;

    public SystemCommands(ArtifactoryService artifactoryService) {
        this.artifactoryService = artifactoryService;
    }

    @ShellMethod("Ping Artifactory system")
    public String ping() {
        return artifactoryService.systemPing().block();
    }

    @ShellMethod("Get Artifactory system version")
    public String version() {
        return artifactoryService.systemVersion()
                .map(Object::toString)
                .block();
    }

    @ShellMethod("Get Artifactory storage information")
    public String storageInfo() {
        return artifactoryService.getStorageInfo()
                .map(Object::toString)
                .block();
    }

}
