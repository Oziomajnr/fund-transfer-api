package service.implementation

import api.model.FundTransferResponse
import dao.TransferProcessorDao
import exception.InsufficientBalanceException
import model.FundTransferRequest
import service.FundTransferService

import javax.inject.Inject

class DirectFundTransferService @Inject
constructor(private val transferProcessorDao: TransferProcessorDao) : FundTransferService {

    @Throws(InsufficientBalanceException::class)
    override fun transferFund(fundTransferRequest: FundTransferRequest): FundTransferResponse {
        return if (transferProcessorDao.processFundTransfer(fundTransferRequest))
            FundTransferResponse.getSuccessFulResponse(fundTransferRequest)
        else {
            FundTransferResponse.getUnknownErrorResponse(fundTransferRequest)
        }
    }
}
