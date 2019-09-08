package exception.mappers

import exception.AccountNotFoundException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper

class AccountNotFoundExceptionMapper : ExceptionMapper<AccountNotFoundException> {
    override fun toResponse(ex: AccountNotFoundException): Response {
        return Response.status(Response.Status.NOT_FOUND.statusCode).entity(ex.data)
                .type(MediaType.APPLICATION_JSON).build()
    }
}
