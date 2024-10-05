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
public class CreateTenantResponse {
    @JsonProperty("name")
    private String name;

    @JsonProperty("activationState")
    private Integer activationState;

    @JsonProperty("id")
    private String id;
}
