package com.abc;


public enum AccountType {

    CHECKING("Checking Account"),
    SAVINGS("Savings Account"),
    MAXI_SAVINGS("Maxi");
    
	
    private String description;
	private AccountType(String description) {
		this.description = description;
	}    

    public String getDescription(){
    	return description;
    }
}
