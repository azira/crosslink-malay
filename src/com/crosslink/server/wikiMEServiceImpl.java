package com.crosslink.server;

/**
 * @author Hazirah Hamdani
 * 
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.crosslink.client.Webcontent;
import com.crosslink.client.wikiMEService;


@SuppressWarnings("serial")
public class wikiMEServiceImpl extends RemoteServiceServlet implements
		wikiMEService {

	public static final int STATUS_CODE_OK = 200;
	private static String message = null;

	public Webcontent getwebcontent(String weburl) throws IOException {
		String messageString = "";
		URL pageURL = new URL(weburl);
		String hostName = pageURL.getHost();
		// String messageString = "Hello " + weburl + "!";

		// Get HTML contents from website
		messageString = doGet(weburl);

		// Replace broken links
		messageString = rBrokenLinks(messageString, weburl, hostName);

		
		// Find anchor words
		// messageString = anchorArticle(messageString);

		Webcontent content = new Webcontent();
		content.setwebContent(messageString);

		return content;
	}
	
	/**
	 * Replaces any broken links and images by replacing ../
	 * 
	 * @param webcontent 
	 * @param web url
	 * @param host
	 * @return fix broken links within webcontent
	 * 
	 */

	private String rBrokenLinks(String content, String theurl, String host) throws MalformedURLException {
		String replacedContent = null;
		// replace broken links & images if appropriate
		replacedContent = content.replace("../", "http://" + host + "/");

		// only for Media Permata website
		if (theurl.contains("mediapermata")) {
			// Getting current file name 
			String fileName = theurl.substring(theurl.lastIndexOf('/'), theurl.length());
			// and then remove file name to get directory
			fileName = theurl.replace(fileName, "");
			int tmp = 20;
			String jpgWord = "src=\"" + Integer.toString(tmp);
			replacedContent = replacedContent.replace(jpgWord, "src=\"" + fileName + "/20");
		}

		return replacedContent;
	}

	private String anchorArticle(String content) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Grabs the HTML source from given url and returns as string
	 * 
	 * @param Website url
	 * @return website HTML source
	 */

	public String doGet(String weburl) {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			URL pageURL = new URL(weburl);
			// "http://www.mediapermata.com.bn/khamis/sep20t4.htm"
			HttpURLConnection urlConnection = (HttpURLConnection) pageURL
					.openConnection();
			urlConnection.setReadTimeout(10000);
			// int respCode = urlConnection.getResponseCode();
			// message = urlConnection.getResponseMessage();
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			reader = new BufferedReader(new InputStreamReader(
					pageURL.openStream()));

			while (reader.ready()) {
				sb.append(reader.readLine());
			}

			reader.close();

		} catch (MalformedURLException e) {
			message = e.getMessage();
		} catch (IOException e) {
			message = e.getMessage();
		}

		if (message == null) {
			return sb.toString();
		}

		return message;
	}
}