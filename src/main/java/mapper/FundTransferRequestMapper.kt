package mapper

import model.FundTransferRequest
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet
import java.sql.SQLException

class FundTransferRequestMapper : ResultSetMapper<FundTransferRequest> {
    @Throws(SQLException::class)
    override fun map(i: Int, resultSet: ResultSet, statementContext: StatementContext): FundTransferRequest {
        val fundTransferRequest = FundTransferRequest()
        fundTransferRequest.id = resultSet.getInt("id")
        fundTransferRequest.amountInCents = resultSet.getInt("amountInCents")
        fundTransferRequest.destinationAccount = resultSet.getString("destinationAccount")
        fundTransferRequest.sourceAccount = resultSet.getString("sourceAccount")
        fundTransferRequest.transactionId = resultSet.getString("transactionId")
        return fundTransferRequest
    }
}
