package api.dtl.reponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserResponse {
    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("userName")
    private Integer userName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("emailConfirmed")
    private Boolean emailConfirmed;

    @JsonProperty("isActive")
    private Boolean isActive;

    @JsonProperty("id")
    private String id;
}
