package com.jerrychin.util;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhoneLookupAPITest {

	String APIAddress;
	Map<String, String> headers;

	@Before
	public void setUpBeforeClass() throws Exception {
		// plainURI = new URI("http://www.google.com/?q=<>");
		APIAddress = "http://apis.baidu.com/apistore/mobilenumber/mobilenumber?phone=15210011578";
		headers = new HashMap<>();
		headers.put("apikey", "f64842b48cad4fbee57ebc1c7135cf44");
	}

	@Test
	public void testRequest() throws IOException, Exception {
		PhoneLookupAPI lookup = new PhoneLookupAPI(new URL(APIAddress), headers);
		System.out.println(lookup.get().getService().request());

	}

}