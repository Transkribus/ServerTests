package org.dea.ServerTests.tests;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageIOTest {
	private static final Logger logger = LoggerFactory.getLogger(ImageIOTest.class);
	public void doTest(URL imgUrl) throws IOException {
		if(imgUrl == null) {
			imgUrl = new URL("https://dbis-thure.uibk.ac.at/f/Get?id=NQYJZXUKMSDEFBSBELKQDAPR&fileType=view");
		}
		InputStream input;
		BufferedImage imgBuffer;
		try {
			input = imgUrl.openStream();
			
			imgBuffer = ImageIO.read(input);
		} catch (FileNotFoundException e) {
			logger.error("File was not found at url " + imgUrl);
			URL origUrl = new URL(imgUrl.getProtocol(), imgUrl.getHost(), imgUrl.getFile().replace("view", "orig"));			
			logger.debug("try orig file location " + origUrl);
			input = origUrl.openStream();
			imgBuffer = ImageIO.read(input);
		}
	}

}
