package com.nhekfqn.seed.jerseyangular.resource;

import com.google.inject.Inject;
import com.nhekfqn.seed.jerseyangular.domain.Data;
import com.nhekfqn.seed.jerseyangular.protocol.GetDataRequest;
import com.nhekfqn.seed.jerseyangular.protocol.GetDataResponse;
import com.nhekfqn.seed.jerseyangular.protocol.SaveDataRequest;
import com.nhekfqn.seed.jerseyangular.protocol.base.ErrorResponse;
import com.nhekfqn.seed.jerseyangular.protocol.base.NotAuthorizedResponse;
import com.nhekfqn.seed.jerseyangular.protocol.base.Response;
import com.nhekfqn.seed.jerseyangular.protocol.base.SuccessResponse;
import com.nhekfqn.seed.jerseyangular.service.AuthTokensService;
import com.nhekfqn.seed.jerseyangular.service.DataService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/data")
public class DataResource {

    private static final Logger logger = LoggerFactory.getLogger(DataResource.class);

    private final AuthTokensService authTokensService;
    private final DataService dataService;

    @Inject
    public DataResource(AuthTokensService authTokensService, DataService dataService) {
        this.authTokensService = authTokensService;
        this.dataService = dataService;
    }

    @POST
    @Path("get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(GetDataRequest getDataRequest) {
        logger.info("{}", getDataRequest);

        Response response;

        String userId = authTokensService.getUserIdByAuthToken(getDataRequest.getAuthToken());

        if (userId == null) {
            logger.info("Not authorized {}", getDataRequest);

            response = new NotAuthorizedResponse();
        } else {
            logger.info("User {}", userId);

            Data data = dataService.getData(getDataRequest.getDataKey());

            if (data != null) {
                response = new GetDataResponse(data);
            } else {
                response = new ErrorResponse("Invalid data key: " + getDataRequest.getDataKey());
            }
        }

        logger.info("Response: {}", response);

        return response;
    }

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(SaveDataRequest saveDataRequest) {
        String userId = authTokensService.getUserIdByAuthToken(saveDataRequest.getAuthToken());

        logger.info("DataResource save {} {}", saveDataRequest.getAuthToken(), userId);

        if (userId == null) {
            logger.info("DataResource save not authorized {}", saveDataRequest.getAuthToken());

            return new NotAuthorizedResponse();
        }

        // todo: use bean validation!
        if (StringUtils.isBlank(saveDataRequest.getDataKey())) {
            return new ErrorResponse("Data Key must not be blank");
        }

        dataService.saveData(saveDataRequest.getDataKey(), saveDataRequest.getProduct(), saveDataRequest.getAmount());

        return new SuccessResponse();
    }

}
