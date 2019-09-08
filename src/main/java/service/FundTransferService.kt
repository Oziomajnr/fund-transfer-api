package service

import exception.InsufficientBalanceException
import model.FundTransferRequest

import javax.validation.Valid

interface FundTransferService {
    /**
     * Transfer fund from one account to another
     */
    @Throws(InsufficientBalanceException::class)
    fun transferFund(@Valid fundTransferRequest: FundTransferRequest): Boolean
}
