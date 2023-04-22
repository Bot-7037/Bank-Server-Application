package com.cg.bankapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cg.bankapp.Main;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@WebAppConfiguration
class TestBankAppController {
	private MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;

	@BeforeEach
	void setup() {
		mvc = null;
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void welcomUser() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/welcome").accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		assertEquals("Welcome to bank server api", result.getResponse().getContentAsString());
	}

	@Test
	void showBalance() throws Exception {
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get("/showBalance/1").accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		assertEquals("{\"balance\":1000.0}", result.getResponse().getContentAsString());
	}

	@Test
	void withdrawAccount() throws Exception {
		Map<String, String> inputMap = new HashMap<>();
		inputMap.put("accountNumber", "1");
		inputMap.put("amount", "100");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(inputMap);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/withdrawMoney")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).andReturn();
		assertEquals("{\"balance\":1000.0}", mvcResult.getResponse().getContentAsString());
	}

	@Test
	void depositMoney() throws Exception {
		Map<String, String> inputMap = new HashMap<>();
		inputMap.put("accountNumber", "1");
		inputMap.put("amount", "100");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(inputMap);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/depositMoney")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).andReturn();
		assertEquals("{\"balance\":1100.0}", mvcResult.getResponse().getContentAsString());
	}

	@Test
	void fundTransfer() throws Exception {
		Map<String, String> inputMap = new HashMap<>();
		inputMap.put("senderAccount", "1");
		inputMap.put("recieverAccount", "2");
		inputMap.put("amount", "100");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(inputMap);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/fundTransfer")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).andReturn();
		assertEquals("{\"balance\":900.0}", mvcResult.getResponse().getContentAsString());
	}

	@Test
	void showLast10Transaction() throws Exception {
		Map<String, String> inputMap = new HashMap<>();
		inputMap.put("accountNumber", "1");
		inputMap.put("amount", "100");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(inputMap);
		mvc.perform(MockMvcRequestBuilders.post("/depositMoney").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json)).andReturn();
		MvcResult transactionResult = mvc.perform(MockMvcRequestBuilders.get("/showLast10Transaction/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).andReturn();
		System.out.println();
		String[] arr = transactionResult.getResponse().getContentAsString().split(" ");
		assertEquals("", arr[arr.length - 1].split("\"")[0]);
	}
}