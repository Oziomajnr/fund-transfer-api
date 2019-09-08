package exception

class InsufficientBalanceException(override val data: Any, message: String) : ApiException(message, data)
