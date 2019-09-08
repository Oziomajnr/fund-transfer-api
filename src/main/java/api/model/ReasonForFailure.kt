package api.model

enum class ReasonForFailure {
    INVALID_SENDER_ACCOUNT,
    INVALID_DESTINATION_ACCOUNT,
    INSUFFICIENT_BALANCE,
    UNKNOWN
}