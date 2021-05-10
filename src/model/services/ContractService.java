package model.services;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	private OnlinePaymentService onlinePaymentService;

	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}
	
	public void processContract(Contract contract, int months) {
		double basicQuota = contract.getTotalValue() / months;
		for(int i=1; i<=months; i++) {
			Date date = addMonths(contract.getDate(), i);
			double updatedQuota = basicQuota + onlinePaymentService.interest(basicQuota, i);
			double fullQuota = updatedQuota + onlinePaymentService.paymentFee(updatedQuota);
			contract.addInstallment(new Installment(date, fullQuota));
		}
	}
	
	public Date addMonths(Date date, int n) {
		Calendar cal = Calendar.getInstance(); //pegar a instancia do calendario (formato etc)
		cal.setTime(date); //indicar a data através de um date por exemplo: 25/06/2018
		cal.add(Calendar.MONTH, n); //acrescentar os meses de acordo com o numero n digitado
		return cal.getTime(); //retornar o calendario após a operação
	}
	
}
