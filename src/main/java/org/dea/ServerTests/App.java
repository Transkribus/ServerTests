package org.dea.ServerTests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.dea.ServerTests.tests.ImageIOTest;
import org.dea.ServerTests.tests.ProxyTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility for testing Java proxy settings on UIBK servers
 * 
 * @author philip
 *
 */
public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
		
		final String testName;
		if(args != null && args.length > 0) {
			testName = args[0];
		} else {
			logger.error("No test specified. Exiting.");
			return;
		}
		URL url = null;
		switch(testName) {
		case "proxy":
			ProxyTest proxyTest = new ProxyTest();
			
			if(args.length > 1) {
				String urlStr = args[1];
				try {
					url = new URL(urlStr);
				} catch (MalformedURLException e) {
					logger.warn("Invalid URL specified: " + urlStr);
					logger.warn("Falling back to default.");
					url = null;
				}
			}
			proxyTest.doTest(url);
			break;
		case "imageio":
			ImageIOTest imageIOTest = new ImageIOTest();

			if(args.length > 1) {
				String urlStr = args[1];
				try {
					url = new URL(urlStr);
				} catch (MalformedURLException e) {
					logger.warn("Invalid URL specified: " + urlStr);
					logger.warn("Falling back to default.");
					url = null;
				}
			}
			try {
				imageIOTest.doTest(url);
			} catch (IOException e) {
				logger.error("Test failed: " + e.getMessage(), e);
			}
			break;
		default:
			logger.error("Unknown test: " + testName);
			return;
		}
	}
}
