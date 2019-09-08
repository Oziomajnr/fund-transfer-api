package model

import org.joda.time.DateTime

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class Account {

    /**
     * Auto increment database Id
     */
    var id: Int? = null

    /**
     * A unique identifier for the user account
     */
    @NotNull
    lateinit var accountIdentifier: String

    /**
     * Account balance in cents
     */
    @Min(0)
    var accountBalance: Int = 0

    var createdAt: DateTime = DateTime.now()

    var updatedAt: DateTime = DateTime.now()

    fun addMoneyToAccount(amountInCents: Int) {
        if (amountInCents < 0) {
            throw IllegalArgumentException("Amount cannot be negative")
        }
        accountBalance += amountInCents
    }

    fun deductMoneyToAccount(amountInCents: Int) {
        if (amountInCents < 0) {
            throw IllegalArgumentException("Amount cannot be negative")
        }
        accountBalance -= amountInCents
    }
}
