package com.abc;

import java.util.Collection;


public interface AccountI {


	public Customer getCustomer();
	
	public AccountType getAccountType();
    public void deposit(double amount);
    public void withdraw(double amount);

    public double getTransactionsSum();
    
    public double interestEarned();
    
    public int getAccountId();
    public Collection<Transaction> getTransactions();
    
    
    

}
