package exception

class AccountNotFoundException(override val data: Any, message: String) : ApiException(message, data) {
}