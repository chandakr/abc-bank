package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public  abstract class BaseAccount implements AccountI{

    
	private  final List<Transaction> transactions;
    private final Customer customer;
    private final int accountId;
    private final Object lock = new Object();

    
    //This may not be a pure approach, but a running total
    //is convinient. In a real world it should include the interest given
    private double runningTransactionsTotal;

    
    
    public BaseAccount(Customer customer, int accountId) {
        this.customer=customer;
        this.accountId=accountId;
        this.transactions=new ArrayList<Transaction>();
    }


	public List<Transaction> getTransactions(){
		return transactions;
	}
	
    public void deposit(double amount) {
    	createTransaction(amount,false);
    }

    public void withdraw(double amount){
    	
    	createTransaction(amount,true);
    	
    }
    
    private  void createTransaction(double amount,boolean withdraw){
  
    	synchronized (lock) {
 
    		if (amount <= 0) {
        		throw new IllegalArgumentException("amount must be greater than zero");
        	}
        	
        	if(withdraw){
            	if(runningTransactionsTotal < amount){
            		throw new InsufficientFundsException(accountId + " does not have sufficient funds");
            	}
            	amount = -amount;
        	}
            
        	Transaction tx = new Transaction(amount);
            transactions.add(tx);
            runningTransactionsTotal+=amount;
		}
        
    }
    
    

	
	public double getTransactionsSum() {
		// TODO Auto-generated method stub
		return runningTransactionsTotal;
	}
	

	public Customer getCustomer() {
		// TODO Auto-generated method stub
		return customer;
	}    
 

	public int getAccountId() {
		return accountId;
	}
	
	
	//Should belong to a helper class?
    protected SortedMap<Date, Double> getDailyTransactionsSum() {

    	SortedMap<Date, Double> result = new TreeMap<Date, Double>();
    	
    	for (Transaction tx : transactions) {
    		Date date = DateProvider.removeTime(tx.getTransactionDate());
    		double currentSum = 0;
    		if(result.containsKey(date)){
    			currentSum = result.get(date);
    		}
    		currentSum+=tx.getAmount();
    		result.put(date, currentSum);
		}

    	return result;
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseAccount other = (BaseAccount) obj;
		if (accountId != other.accountId)
			return false;
		return true;
	}


 
}
