package com.crosslink.server;

/**
 * @author Hazirah Hamdani
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.crosslink.client.Webcontent;
import com.crosslink.client.wikiMEService;

@SuppressWarnings("serial")
public class wikiMEServiceImpl extends RemoteServiceServlet implements
		wikiMEService {

	public static final int STATUS_CODE_OK = 200;
	private static String message = null;
	public ArrayList<String> MalayWordList;

	public Webcontent getwebcontent(String weburl, String MalayTable)
			throws IOException {
		String messageString = "";
		URL pageURL = new URL(weburl);
		String hostName = pageURL.getHost();

		// Get HTML contents from website
		messageString = doGet(weburl);

		// Replace broken links
		messageString = rBrokenLinks(messageString, weburl, hostName);

		// Find anchor words
		messageString = findWord(messageString, MalayTable);

		Webcontent content = new Webcontent();
		content.setwebContent(messageString);

		return content;
	}

	/**
	 * Tag Malay word
	 * 
	 * @param content
	 * @param malayTable
	 * @return Tagged Malay Word
	 */
	private String findWord(String content, String malayTable) {
		ArrayList<String> MalayList = new ArrayList<String>();
		String con = "";
		MalayList = getMalayWord(malayTable);

		// Adding next line - because it is retrieved all one whole string,
		// there's no space in between
		content = content.replace("<P>", "\n<P>");
		content = content.replace("</P>", "</P>\n");

		String contentP[] = content.split("\\r?\\n");

		// System.out.println(contentP.length);
		for (int i = 0; i < contentP.length; i++) {

			if (contentP[i].startsWith("<P>") && contentP[i].endsWith("</P>")) {
				// Split word on current paragraph line
				String WordsToAnchor[] = contentP[i].split("\\s+");
				int index = 0;
				do {
					// Find word from Malay Text File and compare with current
					// paragraph
					for (int k = 0; k < MalayList.size(); k++) {
						// Check if word found in current paragraph
						// find word and add <anchor></anchor> around it to be
						// anchored
						if (WordsToAnchor[index].equals(MalayList.get(k))) {
							WordsToAnchor[index] = WordsToAnchor[index]
									.replace(MalayList.get(k), "<anchor>"
											+ MalayList.get(k) + "</anchor>");
						}
					}
					con = con.concat(WordsToAnchor[index] + " ");
					index++;

				} while (index < WordsToAnchor.length);

			} else {

				// Join replaced text
				con = con.concat(contentP[i]);
			}
		}
		return con;

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

	private String rBrokenLinks(String content, String theurl, String host)
			throws MalformedURLException {
		String replacedContent = null;
		// replace broken links & images if appropriate
		replacedContent = content.replace("../", "http://" + host + "/");

		// only for Media Permata website
		if (theurl.contains("mediapermata")) {
			// Getting current file name
			String fileName = theurl.substring(theurl.lastIndexOf('/'),
					theurl.length());
			// and then remove file name to get directory
			fileName = theurl.replace(fileName, "");
			int tmp = 20;
			String jpgWord = "src=\"" + Integer.toString(tmp);
			replacedContent = replacedContent.replace(jpgWord, "src=\""
					+ fileName + "/20");
		}

		return replacedContent;
	}

	/**
	 * Grab all Malay Word only
	 * 
	 * @return
	 */
	protected ArrayList<String> getMalayWord(String malayTable) {

		String MalayList[] = MalayFileList(malayTable);
		ArrayList<String> MalayArray = new ArrayList<String>();
		for (int i = 0; i < MalayList.length; i++) {
			String MalayWord[] = MalayList[i].split("\\r?\\:");
			MalayArray.add(MalayWord[0]);
		}
		return MalayArray;
	}

	/**
	 * Load Malay Words and Wikipedia Index from File
	 * 
	 * @return
	 */
	private String[] MalayFileList(String malayTable) {
		String MalayList[] = malayTable.split("\\r?\\n");
		return MalayList;
	}

	/**
	 * Grabs the HTML source from given url and returns as string
	 * 
	 * @param Website
	 *            url
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
			// InputStream in = new
			// BufferedInputStream(urlConnection.getInputStream());
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

	/**
	 * Store MalayTable into HashMap and Map the Wiki from Hashmap 
	 * using get(Word) 
	 * 
	 * @param Malay Word, MalayTable
	 */
	@Override
	public String getMalayWiki(String word, String malayTable) {
		String MalayList[] = MalayFileList(malayTable);
		HashMap<String, String> wordList = new HashMap<String, String>();
		String mWord = "";
		String wiki = "";
		// Take both Malay Word and Wikipedia Index
		for (int i = 0; i < MalayList.length; i++) {
			String MalayWord[] = MalayList[i].split("\\r?\\:");
			mWord = MalayWord[0];
			wiki = MalayWord[1];
			// Insert mWord and wiki into HashMap
			wordList.put(mWord, wiki);
		}
		

		return wordList.get(word);
	}
}
