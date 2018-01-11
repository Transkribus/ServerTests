package org.dea.ServerTests.tests;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketAddress;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyTest {
	private static final Logger logger = LoggerFactory.getLogger(ProxyTest.class);
	private static final boolean USE_LOGGER = true;

	public void doTest(URL url) {
		log("http.proxyHost = " + System.getProperty("http.proxyHost"));
		log("http.proxyPort = " + System.getProperty("http.proxyPort"));
		log("http.nonProxyHosts = " + System.getProperty("http.nonProxyHosts"));
		log("https.proxyHost = " + System.getProperty("https.proxyHost"));
		log("https.proxyPort = " + System.getProperty("https.proxyPort"));
		
		String urlStr = "https://archive.thulb.uni-jena.de/ufb/servlets/MCRDFGServlet/MAX/ufb_derivate_00003259/Chart-B-00024_000.tif";
		if(url == null) {
			try {
				url = new URL(urlStr);
			} catch (MalformedURLException e) {
				log("ERROR: Given URL is not valid: " + urlStr, e);
				return;
			}
		}
		log("==================================================================");
		
		
		log("Connectivity test with URL: " + urlStr);
		File out = new File("/tmp/proxyTesterTmpFile");
		out.deleteOnExit();
		
		log("Trying plain download...");
		
		try {
			FileUtils.copyURLToFile(url, out);
			log("Success!");
		} catch (IOException e) {
			log("ERROR: Could not download file at URL: " + urlStr, e);
		}
		log("Trying download with explicit proxy...");
		try {
			Proxy proxy = getHttpProxy();
			InputStream source = url.openConnection(proxy).getInputStream();
			FileUtils.copyInputStreamToFile(source, out);
		} catch (IOException e) {
			log("ERROR: Could not download file at URL: " + urlStr, e);
			return;
		}
		log("Success! Proxy settings work.");
	}
	
	public static Proxy getHttpProxy() {
		SocketAddress sa = new InetSocketAddress("proxy.uibk.ac.at", 3128);
		Proxy p = new Proxy(Type.HTTP, sa);
		return p;
	}
	
	private static void log(final String text, Throwable t) {
		if(t == null) {
			log(text);
			return;
		}
		if(USE_LOGGER) {
			logger.error(text, t);
		} else {
			System.out.println(text);
			t.printStackTrace();
		}
	}
	
	private static void log(final String text) {
		if(USE_LOGGER) {
			logger.info(text);
		} else {
			System.out.println(text);
		}
	}
}
