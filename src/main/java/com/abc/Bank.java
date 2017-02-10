package com.abc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Bank {
	
	//Customer name is assumed as key
    private Map<String,Customer> customers; 
    
    private static Bank bank =new Bank();

    private Object lock = new Object();
    

    private Bank(){
        customers = new ConcurrentHashMap<String, Customer>();//Thread safety required
    }

    public static Bank getBank(){
    	return bank;
    }
    
    public void addCustomer(Customer customer) {
        customers.put(customer.getName(), customer);
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers.values())
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers.values())
            total += c.totalInterestEarned();
        return total;
    }

    public boolean transferMoney(String customerName,int fromAccountId,int toAccountId,double amount){
    	
    	Customer customer = customers.get(customerName);
    	if(customer==null){
    		throw new IllegalArgumentException(customerName + " does not exist");
    	}
        return customer.transferAmount(fromAccountId, toAccountId, amount);
    }
    
    public String getFirstCustomer() {
        try {
            customers = null;
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
