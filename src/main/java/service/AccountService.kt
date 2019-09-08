package service

import model.Account

interface AccountService {
    fun createAccount(account: Account): Account
    fun getAccountByIdentifier(identifier: String): Account
}
