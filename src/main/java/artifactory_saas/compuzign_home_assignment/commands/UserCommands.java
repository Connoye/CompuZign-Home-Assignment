package artifactory_saas.compuzign_home_assignment.commands;

import artifactory_saas.compuzign_home_assignment.model.User;
import artifactory_saas.compuzign_home_assignment.services.ArtifactoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UserCommands {

    private final ArtifactoryService artifactoryService;

    public UserCommands(ArtifactoryService artifactoryService) {
        this.artifactoryService = artifactoryService;
    }

    @ShellMethod("Create a new Artifactory user")
    public String createUser(
            @ShellOption String username,
            @ShellOption String email,
            @ShellOption String password
    ) {
        User newUser = User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();

        return artifactoryService.createUser(newUser)
                .map(user -> "User created: " + user.getUsername())
                .block();
    }

    @ShellMethod("Delete an Artifactory user")
    public String deleteUser(@ShellOption String username) {
        artifactoryService.deleteUser(username).block();
        return "User " + username + " deleted successfully";
    }
}
