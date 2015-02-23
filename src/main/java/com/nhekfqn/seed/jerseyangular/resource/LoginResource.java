package com.nhekfqn.seed.jerseyangular.resource;

import com.google.inject.Inject;
import com.nhekfqn.seed.jerseyangular.protocol.base.ErrorResponse;
import com.nhekfqn.seed.jerseyangular.protocol.base.Response;
import com.nhekfqn.seed.jerseyangular.protocol.login.TokenLoginRequest;
import com.nhekfqn.seed.jerseyangular.protocol.login.UsernameLoginRequest;
import com.nhekfqn.seed.jerseyangular.protocol.login.LoginResponse;
import com.nhekfqn.seed.jerseyangular.service.AuthTokensService;
import com.nhekfqn.seed.jerseyangular.service.LoginService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang3.StringUtils;

@Path("/login")
public class LoginResource {

    private final LoginService loginService;
    private final AuthTokensService authTokensService;

    @Inject
    public LoginResource(LoginService loginService, AuthTokensService authTokensService) {
        this.loginService = loginService;
        this.authTokensService = authTokensService;
    }

    @POST
    @Path("username") // todo: propose a better name (credentials?)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response usernameLogin(UsernameLoginRequest usernameLoginRequest) {
        if (StringUtils.isBlank(usernameLoginRequest.getUsername())) {
            return new ErrorResponse("Username must not be blank.");
        }

        if (StringUtils.isBlank(usernameLoginRequest.getPassword())) {
            return new ErrorResponse("Password must not be blank.");
        }

        String userId = loginService.getUserId(usernameLoginRequest.getUsername(), usernameLoginRequest.getPassword());

        String newAuthToken = authTokensService.issueAuthTokenForUser(userId);

        return new LoginResponse(newAuthToken);
    }

    @POST
    @Path("token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response tokenLogin(TokenLoginRequest tokenLoginRequest) {
        String userId = authTokensService.getUserIdByAuthToken(tokenLoginRequest.getOldAuthToken());

        if (userId == null) {
            return new ErrorResponse("Invalid token.");
        }

        String newAuthToken = authTokensService.issueAuthTokenForUser(userId);

        return new LoginResponse(newAuthToken);
    }

}
