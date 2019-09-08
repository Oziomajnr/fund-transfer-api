package exception.mappers

import exception.InsufficientBalanceException

import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper

class InssuficientBalanceExceptionMapper : ExceptionMapper<InsufficientBalanceException> {
    override fun toResponse(ex: InsufficientBalanceException): Response {
        return Response.status(Response.Status.BAD_REQUEST.statusCode).entity(ex.message)
                .type(MediaType.APPLICATION_JSON).build()
    }
}
