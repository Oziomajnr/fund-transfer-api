package exception.mappers

import javax.ws.rs.NotFoundException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper

class NotFoundExecptionMapper : ExceptionMapper<NotFoundException> {
    override fun toResponse(ex: NotFoundException): Response {
        return Response.status(Response.Status.NOT_FOUND.statusCode).entity(ex.message)
                .type(MediaType.APPLICATION_JSON).build()
    }
}
