package exception.mappers

import javax.validation.ConstraintViolationException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
open class ValidationExceptionMapper : ExceptionMapper<ConstraintViolationException> {
    override fun toResponse(ex: ConstraintViolationException): Response {
        ex.printStackTrace()
        val errors = StringBuilder()

        for (violation in ex.constraintViolations) {
            errors.append("\n").append(violation.message)
        }
        return Response.status(Status.BAD_REQUEST.statusCode)
                .entity(errors).type(MediaType.APPLICATION_JSON).build()
    }
}
