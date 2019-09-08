package service.implementation

import dao.AccountDao
import model.Account
import service.AccountService

import javax.inject.Inject

class BankAccountService @Inject
constructor(private val accountDao: AccountDao) : AccountService {

    override fun createAccount(account: Account): Account {
        return accountDao.getAccountById(accountDao.createAccount(account))
    }

    override fun getAccountByIdentifier(identifier: String): Account {
        return accountDao.getAccountByIdentifier(identifier)
    }
}
