package exception

import java.lang.Exception

open class ApiException(override val message: String?, open val data: Any? = null) : Exception(message) {

}