package com.abc;

import org.junit.Assert;
import org.junit.Test;

import com.abc.AccountI;
import com.abc.Customer;
import com.abc.InsufficientFundsException;
import com.abc.SavingsAccount;
public class BaseAccountTest {

	@Test
	public void testDepositAndWithDrawAndSumTransactions(){
		
		AccountI baseAccount = new SavingsAccount(new Customer("abc"),1);
		baseAccount.deposit(5.5);
		Assert.assertEquals(1, baseAccount.getTransactions().size());
		Assert.assertEquals(5.5, baseAccount.getTransactionsSum(),0);
		
		baseAccount.deposit(7.5);
		Assert.assertEquals(2, baseAccount.getTransactions().size());
		Assert.assertEquals(13, baseAccount.getTransactionsSum(),0);

		baseAccount.withdraw(7.5);
		Assert.assertEquals(5.5, baseAccount.getTransactionsSum(),0);
		Assert.assertEquals(3, baseAccount.getTransactions().size());
		
	}
	
	@Test (expected=InsufficientFundsException.class)
	public void testInsufficientFundsToWithdraw(){
		
		AccountI baseAccount = new SavingsAccount(new Customer("abc"),1);
		baseAccount.withdraw(5.5);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testNegativeInputAmountWithdraw(){
		
		AccountI baseAccount = new SavingsAccount(new Customer("abc"),1);
		Assert.assertEquals(0,baseAccount.getTransactions().size());
		baseAccount.withdraw(-5.5);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testNegativeInputAmountDeposit(){
		
		AccountI baseAccount = new SavingsAccount(new Customer("abc"),1);
		Assert.assertEquals(0,baseAccount.getTransactions().size());
		baseAccount.withdraw(-5.5);
	}
	
	@Test 
	public void testGetCustomer(){
		
		Customer cust = new Customer("abc");
		AccountI baseAccount = new SavingsAccount(cust,1);
		Assert.assertEquals(cust, baseAccount.getCustomer());
	}


	@Test 
	public void testGetAccountId(){
		Customer cust = new Customer("abc");
		AccountI baseAccount = new SavingsAccount(cust,1);
		Assert.assertTrue("Account Id 1 expected", baseAccount.getAccountId()==1);
	}
	

	
}
