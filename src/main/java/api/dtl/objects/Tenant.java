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
public class Tenant {
    @JsonProperty("name")
    private String name;

    @JsonProperty("adminEmailAddress")
    private String adminEmailAddress;

    @JsonProperty("adminPassword")
    private String adminPassword;

    @JsonProperty("id")
    private String id;
}
