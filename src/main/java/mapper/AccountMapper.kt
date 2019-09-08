package mapper

import model.Account
import org.joda.time.DateTime
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet
import java.sql.SQLException

class AccountMapper : ResultSetMapper<Account> {
    @Throws(SQLException::class)
    override fun map(i: Int, resultSet: ResultSet, statementContext: StatementContext): Account {
        val account = Account()
        account.id = resultSet.getInt("id")
        account.accountIdentifier = resultSet.getString("accountIdentifier")
        account.accountBalance = resultSet.getInt("accountBalance")
        account.createdAt = DateTime(resultSet.getTimestamp("createdAt").time)
        account.updatedAt = DateTime(resultSet.getTimestamp("updatedAt").time)
        return account
    }
}
