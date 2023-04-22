package com.cg.bankapplication.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.cg.bankapplication.beans.Account;
import com.cg.bankapplication.beans.Customer;
import com.cg.bankapplication.util.Database;

import static org.junit.jupiter.api.Assertions.*;

class BankMainTest {

	static InputStream stdin;
	static ByteArrayOutputStream byteArrayOutputStream;
	static PrintStream newPrintStream, stdout;

	@BeforeAll
	static void setupTestCase() {
		Account account = new Account(999, 1000, new Customer());
		Database.accounts.put(999, account);
		Account account2 = new Account(1000, 1000, new Customer());
		Database.accounts.put(1000, account2);
	}

	@BeforeEach
	void setupInputOutputStream() {
		stdin = System.in;
		stdout = System.out;
		byteArrayOutputStream = new ByteArrayOutputStream();
		newPrintStream = new PrintStream(byteArrayOutputStream);
		System.setOut(newPrintStream);
	}

	@ParameterizedTest
	@MethodSource("provideInputForMainMethods")
	void testMain(String input, int lineNumber, String expected) {
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		Main.main(null);

		String outputText = byteArrayOutputStream.toString();
		String[] output = outputText.split("\n");
		outputText = output[lineNumber].trim();
		assertEquals(expected, outputText);
	}

	static Stream<Arguments> provideInputForMainMethods() {
		return Stream.of(Arguments.of("1\n999\n6\n", 17, "Account balance: 1000"),
				Arguments.of("2\n999\n100\n6\n", 18, "Deposited 100"),
				Arguments.of("3\n999\n100\n5\n999\n6\n", 18, "Withdrew 100"),
				Arguments.of("4\n999\n1000\n100\n5\n999\n6\n", 19, "Transferred"));
	}

	@AfterEach
	void resetAccountDetails() {
		Database.accounts.put(999, new Account(999, 1000, new Customer()));
	}

	@AfterAll
	static void cleanupTests() {
		Database.accounts.remove(999);
		Database.accounts.remove(1000);
		System.setIn(stdin);
		System.setOut(stdout);
	}
}
