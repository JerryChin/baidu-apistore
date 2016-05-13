package com.jerrychin.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public class WebServiceProvider implements WebService {
	private Map<String, String> headers;
	@SuppressWarnings("unused")
	private Map<String, String> parameters;
	private URL url;

	private HttpURLConnection conn;

	public WebServiceProvider(String url, Map<String, String> headers) throws Exception {
		this.headers = headers;
		
		if(url == null || url.length() == 0)
			throw new RuntimeException("URL to web service cannot be empty!");
		
		// URL itself has no knowledge of encoding, as stated in the official
		// document,
		// we need to encode the parameter before creating the URL instance.
		this.url = new URL(new URI(url).toASCIIString());
		
		// Notice: the real connection has NOT established yet after the
		// following call to openConnection.
		conn = (HttpURLConnection) this.url.openConnection();
	}

	public WebServiceProvider(String url, Map<String, String> headers, Map<String, String> parameters) throws Exception {
		this(url, headers);

		// only used with POST
		this.parameters = parameters;
		
		// get ready for dispatching  
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
	}

	@Override
	public String get() throws Exception {
		// unnecessary operation as the default value of method is initialized to be GET
		// conn.setRequestMethod("GET");

		// add/modify headers
		if(headers != null) {
			for (Entry<String, String> entry : headers.entrySet()) {
				conn.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
			}
		}

		return request();
	}
	
	@Override
	public String post() throws Exception {
		throw new UnsupportedOperationException("Method is not implemented yet.");
	}

	private String request() throws Exception {
		// request service, and send back the outcome, try-with-resource requires Java 8 Compiler.
		try(BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
			StringBuffer sb = new StringBuffer();
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				sb.append(inputLine);
			return sb.toString();
		} finally {
			if (conn != null) {
		        conn.disconnect();
		    }
		}
	}
}
