package dao

import mapper.FundTransferRequestMapper
import model.FundTransferRequest
import org.skife.jdbi.v2.sqlobject.*
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

@RegisterMapper(FundTransferRequestMapper::class)
abstract class FundTransferDao {
    @SqlQuery("select * "
            + "from fundTransferRequest "
            + "where id = :id")
    internal abstract fun getFundTransferrequestById(@Bind("id") id: Int): FundTransferRequest

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

    @SqlUpdate("update fundTransferRequest "
            + "set id = :id, transactionId = :transactionId,  sourceAccount = :sourceAccount, " +
            "destinationAccount = :destinationAccount, amountInCents = :amountInCents" +
            " where id = :id")
    abstract fun updateFundTransferRequest(@BindBean fundTransferRequest: FundTransferRequest)
}
