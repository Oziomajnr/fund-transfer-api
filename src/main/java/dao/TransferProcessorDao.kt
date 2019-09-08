package dao

import api.model.FundTransferResponse
import exception.AccountNotFoundException
import exception.InsufficientBalanceException
import mapper.AccountMapper
import mapper.FundTransferRequestMapper
import model.Account
import model.FundTransferRequest
import org.skife.jdbi.v2.sqlobject.*
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

/**
 * Perform any fund transfer processing here
 */
@RegisterMapper(FundTransferRequestMapper::class, AccountMapper::class)
abstract class TransferProcessorDao {
    @SqlQuery("select * "
            + "from fundTransferRequest "
            + "where transactionId = :transactionId")
    internal abstract fun getFundTransferrequestByTransactionId(@Bind("transactionId") id: Int): FundTransferRequest

    @SqlUpdate("insert into fundTransferRequest"
            + "		(id, transactionId,  sourceAccount, destinationAccount, amountInCents) "
            + "values"
            + "		(:id, :transactionId, :sourceAccount, :destinationAccount,  :amountInCents)")
    @GetGeneratedKeys
    internal abstract fun createFundTransferRequest(@BindBean fundTransferRequest: FundTransferRequest): Int

    @SqlQuery("select * "
            + "from account "
            + "where accountIdentifier = :accountIdentifier")
    internal abstract fun getAccountByIdentifier(@Bind("accountIdentifier") accountIdentifier: String): Account?

    @SqlUpdate("update account "
            + "set id = :id, accountIdentifier = :accountIdentifier,  accountBalance = :accountBalance, " +
            "createdAt = :createdAt, updatedAt = :updatedAt" +
            " where id = :id")
    abstract fun updateAccount(@BindBean account: Account)

    /**
     * Tranfer money from source account to destination account
     *
     * @param fundTransferRequest
     * @return true if fund is successfully transferred and false if there was an error in the process
     */
    @Transaction
    @Throws(InsufficientBalanceException::class)
    fun processFundTransfer(fundTransferRequest: FundTransferRequest): Boolean {

        val sourceAccount = getAccountByIdentifier(fundTransferRequest.sourceAccount)
                ?: throw AccountNotFoundException(FundTransferResponse.getInvalidSourceAccountRespose(fundTransferRequest),
                        "Sender account not found")
        if (sourceAccount.accountBalance < fundTransferRequest.amountInCents) {
            throw InsufficientBalanceException(FundTransferResponse.getInsufficientBalance(fundTransferRequest), "Sender account does not have enough balance")
        }

        val destinationAccount = getAccountByIdentifier(fundTransferRequest.destinationAccount)
                ?: throw AccountNotFoundException(FundTransferResponse.getInvalidDestinationAccountRespose(fundTransferRequest), "Destination account not found")
        fundTransferRequest.id = createFundTransferRequest(fundTransferRequest)

        sourceAccount.deductMoneyToAccount(fundTransferRequest.amountInCents)
        destinationAccount.addMoneyToAccount(fundTransferRequest.amountInCents)

        updateAccount(sourceAccount)
        updateAccount(destinationAccount)

        return true
    }
}
