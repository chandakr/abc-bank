package com.abc;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Transaction implements Serializable{
    public final double amount;

    private Date transactionDate;
    private TransactionType transactionType = TransactionType.DEPOSIT;
    
    //transaction type - debit or credit
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.now();
        transactionType = (amount <=0)?TransactionType.WITHDRAW:transactionType;
    }

    
	public double getAmount() {
		return amount;
	}


	public Date getTransactionDate() {
		return transactionDate;
	}


	public TransactionType getTransactionType() {
		return transactionType;
	}


	@Override
	public String toString() {
		return "Transaction [amount=" + amount + ", transactionDate="
				+ transactionDate + "]";
	}
    
    

}
