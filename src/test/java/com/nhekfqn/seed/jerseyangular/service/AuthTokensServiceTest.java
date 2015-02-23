package com.nhekfqn.seed.jerseyangular.service;

import org.testng.annotations.BeforeMethod;

import static org.mockito.MockitoAnnotations.initMocks;

public class AuthTokensServiceTest {

    private AuthTokensService authTokensService;

    @BeforeMethod
    public void setUp() throws Throwable {
        initMocks(this);

        authTokensService = new AuthTokensService();
    }

    // getUserIdByAuthToken with random (never issued) token returns null
    // issueAuthTokenForUser with several userIds returns different tokens
    // getUserIdByAuthToken with token issued returns userId
    // next issueAuthTokenForUser for same userId returns different token - old token does not work anymore, new one does


}
