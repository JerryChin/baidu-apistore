package com.jerrychin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractWebServiceProvider implements Provider {
	private Map<String, String> headers;
	@SuppressWarnings("unused")
	private Map<String, String> parameters;
	private URL url;

	private HttpURLConnection conn;

	AbstractWebServiceProvider(String url, Map<String, String> headers) throws Exception {
		this.headers = headers;
		
		if(url == null || url.length() == 0)
			throw new NullPointerException("URL to web service cannot be empty!");
		
		// URL itself has no knowledge of encoding, as stated in the official
		// document,
		// we need to encode the parameter before creating the URL instance.
		this.url = new URL(new URI(url).toASCIIString());
	}

	AbstractWebServiceProvider(String url, Map<String, String> headers, Map<String, String> parameters) throws Exception {
		this(url, headers);

		// only used with POST
		this.parameters = parameters;
	}

	public AbstractWebServiceProvider get() throws IOException {
		// Notice: the real connection has NOT established yet after the
		// following call to openConnection.
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		// add/modify headers
		for (Entry<String, String> entry : headers.entrySet()) {
			conn.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
		}

		return this;
	}

	public AbstractWebServiceProvider post() throws ProtocolException {
		throw new UnsupportedOperationException("Method is not implemented yet.");
	}

	public WebService getService() {
		return new WebService() {
			@Override
			public String request() throws IOException {
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
		};

	}
}
