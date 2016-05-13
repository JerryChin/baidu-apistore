package com.jerrychin.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import junit.framework.Assert;

public class LookupAPITest {

	public static String addr;
	public static Map<String, String> headers;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// plainURI = new URI("http://www.google.com/?q=<>");
		addr = "http://apis.baidu.com/apistore/mobilenumber/mobilenumber?phone=15210011578";
		headers = new HashMap<>();
		headers.put("apikey", "f64842b48cad4fbee57ebc1c7135cf44");
	}

	@Test
	public void testRequestOutcome() throws IOException, Exception {
		WebService lookup = new WebServiceProvider(addr, headers);
		Assert.assertNotNull(lookup.get());
	}
	
	@Test(expected=Exception.class)
	public void testNullURLShouldThrowException() throws Exception {
		new WebServiceProvider(null, headers);
	}
	
	@Test(expected=Exception.class)
	public void testEmptyURLShouldThrowException() throws Exception {
		new WebServiceProvider("", headers);
	}
	
	@Test
	public void testNullHeadersShouldNoException() throws IOException, Exception {
		WebService lookup = new WebServiceProvider(addr, null);
		try {
			lookup.get();
		} catch(Exception e) {
			Assert.fail("Unexcepted exception!");
		}
	}
	
	@Test(expected=Exception.class)
	public void testPostShouldThrowException() throws Exception {
		WebService lookup = new WebServiceProvider(addr, headers);
		lookup.post();
	}
}