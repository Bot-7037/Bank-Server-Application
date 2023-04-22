package com.cg.bankapp.utils;

public class MenuConstants {
	private MenuConstants() {
		throw new IllegalStateException();
	}

	public static final String MENU = "\n╒===========================================╕\n"
			+ "|                                           |\n" + "| MAIN MENU                                 |\n"
			+ "|                                           |\n" + "| 1. Show Balance                           |\n"
			+ "| 2. Deposit                                |\n" + "| 3. Withdraw                               |\n"
			+ "| 4. Fund Transfer                          |\n" + "| 5. Show last transactions                 |\n"
			+ "| 6. Exit                                   |\n" + "|                                           |"
			+ "\n╘===========================================╛\n\n" + "➤  ";

	public static final String HEADING = "\r\n"
			+ "  ____              _                           _ _           _   _             \r\n"
			+ " |  _ \\            | |        /\\               | (_)         | | (_)            \r\n"
			+ " | |_) | __ _ _ __ | | __    /  \\   _ __  _ __ | |_  ___ __ _| |_ _  ___  _ __  \r\n"
			+ " |  _ < / _` | '_ \\| |/ /   / /\\ \\ | '_ \\| '_ \\| | |/ __/ _` | __| |/ _ \\| '_ \\ \r\n"
			+ " | |_) | (_| | | | |   <   / ____ \\| |_) | |_) | | | (_| (_| | |_| | (_) | | | |\r\n"
			+ " |____/ \\__,_|_| |_|_|\\_\\ /_/    \\_\\ .__/| .__/|_|_|\\___\\__,_|\\__|_|\\___/|_| |_|\r\n"
			+ "                                   | |   | |                                    \r\n"
			+ "                                   |_|   |_|                                    \r\n" + "";

	public static final String SHOWBALANCETOPBAR = "\n┎-------------------------------┒";
	public static final String SHOWBALANCEBOTTOMBAR = "┖-------------------------------┚";
	public static final String DEPOSITTOPBAR = "\n┎-------------------------------------------┒";
	public static final String DEPOSITBOTTOMBAR = "┖-------------------------------------------┚";
	public static final String WITHDRAWTOPBAR = "\n┎-------------------------------------------┒";
	public static final String WITHDRAWBOTTOMBAR = "┖-------------------------------------------┚";
	public static final String FUNDTRANSFERTOPBAR = "\n┎---------------------------------------------------------------┒";
	public static final String FUNDTRANSFERBOTTOMBAR = "┖---------------------------------------------------------------┚";
	public static final String TRANSACTIONTOPBAR = "\n┎-------------------------------------------------------------------------------------------------------┒";
	public static final String TRANSACTIONBOTTOMBAR = "┖-------------------------------------------------------------------------------------------------------┚";

}
