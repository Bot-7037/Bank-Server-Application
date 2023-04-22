package com.cg.bankapp.utils;

public class MenuConstants {
	private MenuConstants() {
		throw new IllegalStateException();
	}

	public static final String menu = "\n------------------------------\n\n" + " Enter choice\n\n"
			+ " 1. Show Balance\n" + " 2. Deposit\n" + " 3. Withdraw\n" + " 4. Fund Transfer\n"
			+ " 5. Show last transactions\n" + " 6. Exit\n\n" + "\n------------------------------\n\n" + "-> ";
}
