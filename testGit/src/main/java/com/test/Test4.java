package com.test;

public class Test4 {
	
	int ageEle=18;

	public static void main(String args[]) {
		System.out.println("Hey I Am Test4");
	}
	
	public void verifyVoterEligibility() {
		if(ageEle>=18)
			System.out.println("We welcome you: ECI");
		else {
			System.out.println("You are not eligible: ECI");
		}
	}
}
