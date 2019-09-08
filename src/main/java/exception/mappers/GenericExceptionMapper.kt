package exception.mappers

import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

/**
 * Map all unkown errors as Internal server Error
 */
@Provider
class GenericExceptionMapper : ExceptionMapper<Exception> {
    override fun toResponse(ex: Exception): Response {
        return Response.status(Status.INTERNAL_SERVER_ERROR.statusCode).entity(ex.message)
                .type(MediaType.APPLICATION_JSON).build()
    }
}
