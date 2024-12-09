package artifactory_saas.compuzign_home_assignment.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Repository {
    private String key;
    private String type;
    private String packageType;
}
