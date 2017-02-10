package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class InterestRateCalculator {

	public static double calculateInterestAccruedDailyToDate(double annualInterestInPercent,
													double principal,Date dateFrom){
		
		DateTime jodaDateFrom = new DateTime(dateFrom);
		DateTime currentDate = new DateTime();
		
		Days daysBetween = Days.daysBetween(jodaDateFrom, currentDate);
		
		double rate = annualInterestInPercent/36500;
		
		double interest = principal* (Math.pow(1+ rate, daysBetween.getDays()) -1);
		
		BigDecimal unscaledInterest = new BigDecimal(interest);
		
		BigDecimal scaledInterest = unscaledInterest.setScale(2,RoundingMode.UP);
		
		double x= scaledInterest.doubleValue();
		return x;

	}
}
