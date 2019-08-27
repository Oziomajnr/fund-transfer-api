package fundtransfer.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(value = "/home")
@Api(value = "/home")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HomeResource {

    @POST
    @Path("/logout")
    @ApiOperation(value = "Logs out a user", notes = "Logs out a user", response = String.class)
    public String logout() {
        return "Test is successful";
    }
}
