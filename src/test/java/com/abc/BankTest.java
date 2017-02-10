package com.abc;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.abc.AccountI;
import com.abc.Bank;
import com.abc.CheckingAccount;
import com.abc.Customer;
import com.abc.DateProvider;
import com.abc.SavingsAccount;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.DateTime;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateProvider.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummary() {
        Bank bank = Bank.getBank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount(john, 1));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testTotalInterestPaid() {
        Bank bank = Bank.getBank();
        Customer bill = new Customer("Bill");
        
		Date aYearAgo = new DateTime().minusDays(365).toDate();
		Date aYearAgoNoTime = DateProvider.removeTime(aYearAgo);

		
		PowerMockito.mockStatic(DateProvider.class);
		BDDMockito.given(DateProvider.now()).willReturn(aYearAgo);
		BDDMockito.given(DateProvider.removeTime(aYearAgo)).willReturn(aYearAgoNoTime);

		AccountI checkingAccount = new CheckingAccount(bill, 2);
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.11, bank.totalInterestPaid(), DOUBLE_DELTA);

        Customer john = new Customer("John");
        AccountI savingsAccount = new SavingsAccount(john, 1);
        john.openAccount(savingsAccount);
        bank.addCustomer(john);
        checkingAccount.deposit(1500.0);

        assertEquals(1.61, bank.totalInterestPaid(), DOUBLE_DELTA);
    
    }


}
