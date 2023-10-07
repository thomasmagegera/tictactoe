package com.springbootproject.models;

public enum MoveType {
    X(1), O(2), NOBODY(3);

    private Integer value;
    
    private MoveType(int value){  

    	this.value=value;  

    	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}  
    
}
