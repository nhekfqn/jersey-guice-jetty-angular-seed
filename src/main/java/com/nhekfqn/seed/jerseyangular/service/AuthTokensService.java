package com.nhekfqn.seed.jerseyangular.service;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.inject.Singleton;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class AuthTokensService {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokensService.class);

    // todo: should be persisted (both structures!)
    // todo: should be thread-safe (both structures!) update generateNewAuthToken to respect thread-safety
    private final BiMap<String, String> userIdsByAuthToken = HashBiMap.create();
    private final Set<String> allAuthTokensEverIssued = new HashSet<>();

    public String issueAuthTokenForUser(String userId) {
        if (StringUtils.isBlank(userId)) {
            logger.error("User Id is blank: {}", userId);

            throw new IllegalArgumentException("User Id is blank: " + userId);
        }

        logger.info("Authorizing user {}", userId);

        String oldAuthToken = userIdsByAuthToken.inverse().get(userId);
        String newAuthToken = generateNewAllUniqueAuthToken();
        userIdsByAuthToken.forcePut(newAuthToken, userId);

        logger.info("User {} authorized. Old auth token: {}, new auth token: {}", userId, oldAuthToken, newAuthToken);

        return newAuthToken;
    }

    private String generateNewAllUniqueAuthToken() {
        String newAuthTokenCandidate;
        do {
            newAuthTokenCandidate = generateNextAuthTokenCandidate();
        } while (allAuthTokensEverIssued.contains(newAuthTokenCandidate));

        allAuthTokensEverIssued.add(newAuthTokenCandidate);

        return newAuthTokenCandidate;
    }

    private String generateNextAuthTokenCandidate() {
        return RandomStringUtils.randomAlphanumeric(20);
    }

    // todo: tokens should expire after some time (preferable) or number of uses (may be easier to implement and test)
    public String getUserIdByAuthToken(String authToken) {
        return userIdsByAuthToken.get(authToken);
    }

}
