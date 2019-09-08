package service

import dao.TransferProcessorDao
import exception.InsufficientBalanceException
import model.FundTransferRequest

import javax.inject.Inject

class DirectFundTransferService @Inject
constructor(private val transferProcessorDao: TransferProcessorDao) : FundTransferService {

    @Throws(InsufficientBalanceException::class)
    override fun transferFund(fundTransferRequest: FundTransferRequest): Boolean {
        return transferProcessorDao.processFundTransfer(fundTransferRequest)
    }
}
