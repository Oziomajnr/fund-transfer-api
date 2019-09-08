package service

import dao.AccountDao
import model.Account

import javax.inject.Inject

class BankAccountService @Inject
constructor(private val accountDao: AccountDao) : AccountService {

    override fun createAccount(account: Account): Int {
        return accountDao.createAccount(account)
    }

    override fun getAccountByIdentifier(identifier: String): Account {
        return accountDao.getAccountByIdentifier(identifier)
    }
}
