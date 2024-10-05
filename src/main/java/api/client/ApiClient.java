package api.client;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import utils.Environment;

@NoArgsConstructor
public class ApiClient extends AbstractApiClient {
    public ApiClient(Environment env, String authToken) {
        super(env, authToken);
    }
}
