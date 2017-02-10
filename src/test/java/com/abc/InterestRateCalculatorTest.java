package com.abc;


import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.junit.Assert;
import org.junit.Test;

import com.abc.InterestRateCalculator;

public class InterestRateCalculatorTest {


/*	@Test
	public void testCalculateInterestAccruedDailyForZero(){
		double interest = InterestRateCalculator.calculateInterestAccruedDailyToDate(0, 0,new Date());
		Assert.assertEquals(0, interest,0);
		
	}*/
	@Test
	public void testCalculateInterestAccruedDailyForNonZero(){
		DateTime dateTime = new DateTime();
		DateTime plusDays = dateTime.minusDays(365);
		
		double interest  = InterestRateCalculator.calculateInterestAccruedDailyToDate(10, 100,plusDays.toDate());
		Assert.assertEquals(10.52, interest,0);
		
		plusDays = dateTime.minusDays(730);
		interest  = InterestRateCalculator.calculateInterestAccruedDailyToDate(10, 100,plusDays.toDate());
		Assert.assertEquals(22.14, interest,0);
	}

}
