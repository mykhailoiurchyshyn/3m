package api.dtl.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("role")
    private String role;

    @JsonProperty("username")
    private String username;

    @JsonProperty("roleNames")
    private String[] roleNames;

    @JsonProperty("organizationUnitIds")
    private String[] organizationUnitIds;
}
