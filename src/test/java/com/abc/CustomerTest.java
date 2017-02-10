package com.abc;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.abc.AccountI;
import com.abc.CheckingAccount;
import com.abc.Customer;
import com.abc.DateProvider;
import com.abc.InsufficientFundsException;
import com.abc.SavingsAccount;


@RunWith(PowerMockRunner.class)
@PrepareForTest(DateProvider.class)

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Customer henry = new Customer("Henry");
        
        AccountI checkingAccount = new CheckingAccount(henry,1);
        AccountI savingsAccount = new SavingsAccount(henry, 2);

        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);
        
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }


	@Test (expected=InsufficientFundsException.class)
    public  void testTransferAmountForInsufficientFunds(){
    	
    	Customer henry = new Customer("henry");
    	
    	AccountI fromAccount = new CheckingAccount(henry, 1);
    	
    	henry.openAccount(fromAccount);
    	
    	henry.transferAmount(1, 2, 100);
    	
    }


    @Test
	public  void testTransferAmount(){
    	
    	Customer henry = new Customer("henry");
    	
    	AccountI fromAccount = new CheckingAccount(henry, 1);
    	fromAccount.deposit(100);
    	
    	AccountI toAccount = new SavingsAccount(henry, 2);
    	
    	henry.openAccount(fromAccount);
    	henry.openAccount(toAccount);
    	
    	Assert.assertTrue(henry.transferAmount(1, 2, 100));
    	
    }

	@Test (expected=InsufficientFundsException.class)
    public  void testTransferAmountInsuffficientFunds(){
    	
    	Customer henry = new Customer("henry");
    	
    	AccountI fromAccount = new CheckingAccount(henry, 1);
    	
    	henry.openAccount(fromAccount);
    	
    	henry.transferAmount(1, 2, 100);
    	
    }
    
    @Test
	public void testTotalInterestEarned() throws Exception{
    	Customer henry = new Customer("Henry");
    	
    	
    	
		Date aYearAgo = new DateTime().minusDays(365).toDate();
		Date aYearAgoNoTime = DateProvider.removeTime(aYearAgo);

		
		PowerMockito.mockStatic(DateProvider.class);
		BDDMockito.given(DateProvider.now()).willReturn(aYearAgo);
		BDDMockito.given(DateProvider.removeTime(aYearAgo)).willReturn(aYearAgoNoTime);

    	AccountI checkingAccount = new CheckingAccount(henry, 1);
		checkingAccount.deposit(200);
    	henry.openAccount(checkingAccount);

    	
    	assertEquals(0.21, henry.totalInterestEarned(),0.00001);

    	AccountI savingsAccount = new SavingsAccount(henry,2);
		savingsAccount.deposit(300);
    	henry.openAccount(savingsAccount);
 
    	assertEquals(0.52, henry.totalInterestEarned(),.000001);
		
	}
	@Test
    public void testOpenAccount(){
    	

    	Customer oscar = new Customer("Oscar");
        assertEquals(0, oscar.getNumberOfAccounts());//zero Accounts
    	
    	AccountI mockAccount1 =Mockito.spy(new CheckingAccount(oscar,1));
    	AccountI mockAccount2 = Mockito.spy(new SavingsAccount(oscar,2));
    	
    	oscar.openAccount(mockAccount1);
        assertEquals(1, oscar.getNumberOfAccounts());//1 Account

    	oscar.openAccount(mockAccount2);
    	
        assertEquals(2, oscar.getNumberOfAccounts());//2 accounts
        
        
    }

}
