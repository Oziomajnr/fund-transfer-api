package dao

import mapper.AccountMapper
import model.Account
import org.skife.jdbi.v2.sqlobject.*
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

@RegisterMapper(AccountMapper::class)
abstract class AccountDao {
    @SqlQuery("select * "
            + "from account "
            + "where id = :id")
    abstract fun getAccountById(@Bind("id") id: Int): Account

    @SqlQuery("select * "
            + "from account "
            + "where accountIdentifier = :accountIdentifier")
    abstract fun getAccountByIdentifier(@Bind("accountIdentifier") id: String): Account

    @SqlUpdate("insert into account"
            + "		(id, accountIdentifier,  accountBalance, createdAt, updatedAt) "
            + "values"
            + "		(:id, :accountIdentifier, :accountBalance, :createdAt,  :updatedAt)")
    @GetGeneratedKeys
    abstract fun createAccount(@BindBean account: Account): Int

    @SqlUpdate("update account "
            + "set id = :id, accountIdentifier = :accountIdentifier,  accountBalance = :accountBalance, " +
            "createdAt = :createdAt, updatedAt = :updatedAt" +
            " where id = :id")
    abstract fun updateAccount(@BindBean account: Account)
}
