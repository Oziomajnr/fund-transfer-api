package resource

import api.model.FundTransferApiRequest
import exception.InsufficientBalanceException
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import model.Account
import service.AccountService
import service.FundTransferService

import javax.inject.Inject
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path(value = "/fund")
@Api(value = "/fund")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class FundTransferResource @Inject
constructor(private val fundTransferService: FundTransferService,
            private val accountService: AccountService) {

    @POST
    @Path("/transfer")
    @ApiOperation(value = "Transfer funds from one account to another ", notes = "Logs out a user", response = String::class)
    @Throws(InsufficientBalanceException::class)
    fun transferFunds(@NotNull @Valid fundTransferApiRequest: FundTransferApiRequest): Response {

        return Response.ok().entity(fundTransferService.transferFund(fundTransferApiRequest.toFundTransferRequest())).build()
    }

    @POST
    @Path("/account")
    @ApiOperation(value = "Create a user account", response = String::class)
    fun createUserAccount(@NotNull @Valid account: Account): Response {
        return Response.ok().entity(accountService.createAccount(account)).build()
    }

    @GET
    @Path("/account/identifier/{identifier}")
    @ApiOperation(value = "Get a user account", response = String::class)
    fun getUserAccount(@NotNull @PathParam("identifier") accountIdentifier: String): Response {
        return Response.ok().entity(accountService.getAccountByIdentifier(accountIdentifier)).build()
    }
}
