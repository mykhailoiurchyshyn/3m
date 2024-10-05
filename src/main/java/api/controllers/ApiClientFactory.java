package api.controllers;

import api.UserRole;
import api.client.ApiClient;
import api.client.IApiClient;
import api.client.MainAdminApiClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import utils.Environment;

@Data
@AllArgsConstructor
public class ApiClientFactory {
    private final Environment env;
    private final String authToken;

//    public ApiClientFactory(String authToken) {
//        this.authToken = authToken;
//    }

    public IApiClient getApiClient(UserRole userRole, String authToken) {
        return switch (userRole) {
            case MAIN_ADMIN -> new MainAdminApiClient(env, authToken);
            case ADMIN, OWNER, MEMBER -> new ApiClient(env, authToken);
        };
    }

}
