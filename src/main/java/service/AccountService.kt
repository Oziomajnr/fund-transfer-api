package service

import model.Account

interface AccountService {
    fun createAccount(account: Account): Int
    fun getAccountByIdentifier(identifier: String): Account
}
