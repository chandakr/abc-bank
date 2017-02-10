package com.abc;

import static java.lang.Math.abs;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Customer {
	
    private String name;
    
    private Map<Integer,AccountI> accounts;
    private Object lock = new Object();

    public Customer(String name) {
        this.name = name;
        this.accounts =new ConcurrentHashMap<Integer, AccountI>();//Thready Safety required
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(AccountI account) {
        accounts.put(account.getAccountId(), account);
        return this;
    }

    
    public int getNumberOfAccounts() {
        return accounts.values().size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (AccountI a : accounts.values())
            total += a.interestEarned();
        return total;
    }

    public boolean transferAmount(int fromAccountId,int toAccountId,double amount){
    	
    	AccountI fromAccount = accounts.get(fromAccountId);

    	AccountI toAccount = accounts.get(toAccountId);
    	
    	synchronized (lock) {
        	fromAccount.withdraw(amount);
        	toAccount.deposit(amount);;
		}
    	
    	return true;
    	
    }
   
   /**
    * TODO Need to be changed to StringBuider 
    * @return
    */
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (AccountI a : accounts.values()) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.getTransactionsSum();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(AccountI a) {
        String s = a.getAccountType().getDescription() + "\n";


        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

 
	
    
}
