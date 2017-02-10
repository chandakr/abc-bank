package com.abc;

import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.abc.AccountI;
import com.abc.AccountType;
import com.abc.BaseAccount;
import com.abc.CheckingAccount;
import com.abc.Customer;
import com.abc.DateProvider;
import com.abc.MaxiSavingsAccount;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateProvider.class)

public class MaxiSavingsAccountTest {

	@Test
	public void testGetAccountType(){
		
		AccountI account = new MaxiSavingsAccount(new Customer("henry"),1);
		Assert.assertTrue("Savings account Expected", account.getAccountType()==AccountType.MAXI_SAVINGS);
	}
	
	@Test
	public void testInterestEarnedNoTransactions(){
		
		AccountI account = new CheckingAccount(new Customer("Henry"),1);
		Assert.assertEquals(0.0, account.interestEarned(),0);
		Assert.assertEquals(0,account.getTransactions().size());
	}

	@Test
	public void testInterestEarnedForCurrentDateTransactions() {

		//First Transaction
		
		BaseAccount baseAccount = new MaxiSavingsAccount(new Customer("abc"),1);
		baseAccount.deposit(5.5);
		Map<Date, Double> txSumMap = baseAccount.getDailyTransactionsSum();
		
		Assert.assertEquals(1, txSumMap.size());
		Assert.assertEquals(1, baseAccount.getTransactions().size());
		Assert.assertEquals(0.0, baseAccount.interestEarned(),0);
		
		Date currentDay = DateProvider.removeTime(new Date());

		Double txSum = txSumMap.get(currentDay);
		Assert.assertEquals(5.5, txSum,0);

		//Second Transaction
		baseAccount.withdraw(3.5);
		txSumMap = baseAccount.getDailyTransactionsSum();
		Assert.assertEquals(1, txSumMap.size());
		Assert.assertEquals(2, baseAccount.getTransactions().size());
		txSum = txSumMap.get(currentDay);
		Assert.assertEquals(2.0, txSum,0);
	}

	@Test
	public void testInterestEarned() {

		// Add a transaction for -365 days
		
		Date aYearAgo = new DateTime().minusDays(365).toDate();
		Date aYearAgoNoTime = DateProvider.removeTime(aYearAgo);

		Date nineDaysAgo = new DateTime().minusDays(9).toDate();
		Date nineDaysAgoNoTime = DateProvider.removeTime(nineDaysAgo);

		
		PowerMockito.mockStatic(DateProvider.class);
		BDDMockito.given(DateProvider.now()).willReturn(aYearAgo);
		BDDMockito.given(DateProvider.removeTime(aYearAgo)).willReturn(aYearAgoNoTime);
		
		BaseAccount baseAccount = new MaxiSavingsAccount(new Customer("abc"),1);
		baseAccount.deposit(1000);
		Map<Date, Double> txSumMap = baseAccount.getDailyTransactionsSum();
		
		Assert.assertEquals(1, txSumMap.size());
		Assert.assertEquals(1000, txSumMap.get(aYearAgoNoTime),0);
		Assert.assertEquals(5.02,baseAccount.interestEarned(),0.00001);
		
	
		
		BDDMockito.given(DateProvider.now()).willReturn(nineDaysAgo);
		BDDMockito.given(DateProvider.removeTime(nineDaysAgo)).willReturn(nineDaysAgoNoTime);
		baseAccount.withdraw(100);
		txSumMap = baseAccount.getDailyTransactionsSum();
		
		Assert.assertEquals(2, txSumMap.size());
		Assert.assertEquals(-100, txSumMap.get(nineDaysAgoNoTime),0);
		
		Assert.assertEquals(1.0,baseAccount.interestEarned(),0.00001);
		
	}
	
	
}

