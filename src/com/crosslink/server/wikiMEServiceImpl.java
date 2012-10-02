package com.crosslink.server;

/**
 * @author Hazirah Hamdani
 * 
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.crosslink.client.Webcontent;
import com.crosslink.client.wikiMEService;

@SuppressWarnings("serial")
public class wikiMEServiceImpl extends RemoteServiceServlet implements
		wikiMEService {

	public static final int STATUS_CODE_OK = 200;
	public ArrayList<String> MalayWordList;
	
	/***
	 * Connect to website url to get the HTML contents and 
	 * parse contents to anchor Malay words
	 * 
	 */
	public Webcontent getwebcontent(String weburl, String MalayTable) {

		Document doc = null;
		String content = null;
		try {
			// grab HTML contents form website
			doc = Jsoup.connect(weburl).timeout(0).get();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			content = "Could not catch web content";
		}

		// if website newspaper from mediapermata
		if (weburl.contains("mediapermata")) {
			Elements text = doc.select("p");

			for (Element t : text) {
				String tmp = t.toString();
				// get web content to anchor
				String webText = t.text();
				// Find anchor words
				String anchorContent = findWord(webText, MalayTable);
				t.html(anchorContent);
				t.prepend("<p>");
				t.append("</p>");

				// Replaces content that has <anchor></anchor>
				// content = content.replace(tmp, t.toString());

			}
		}

		// if website newspaper from bharian
		if (weburl.contains("bharian")) {
			Element text = doc.select("div.article-body").first();
				String tmp = text.html();
				// Find anchor words
				String anchorContent = findWord(tmp, MalayTable);
				text.html(anchorContent);
				text.prepend("<div class=\"article-body\">");
				text.append("</div>");
			
		}
		
		content = doc.html();

		// fixing broken images
		Elements imgSrc = doc.select("img[src]");
		for (Element img : imgSrc) {
			String absHref = img.absUrl("abs:src");
			String imgAttr = img.attr("src");
			content = content.replace(imgAttr, absHref);

		}
		// System.out.println(content);
		Webcontent webcontent = new Webcontent();
		webcontent.setwebContent(content);

		return webcontent;
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

		String WordsToAnchor[] = content.split(" ");
		for (int j = 0; j < WordsToAnchor.length; j++) {

			// Find word from Malay Text File and compare with word
			for (int k = 0; k < MalayList.size(); k++) {
				// Check if word found in current paragraph
				// find word and add <anchor></anchor> around it to be
				// anchored
				if (WordsToAnchor[j].equals(MalayList.get(k))) {
					WordsToAnchor[j] = WordsToAnchor[j].replace(
							MalayList.get(k), "<anchor>" + MalayList.get(k)
									+ "</anchor>");
				}
			}
			con = con.concat(WordsToAnchor[j] + " ");
		}

		return con;

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
	 * Store MalayTable into HashMap and Map the Wiki from Hashmap using
	 * get(Word)
	 * 
	 * @param Malay
	 *            Word, MalayTable
	 */
	@Override
	public String getMalayWiki(String word, String malayTable) {
		String MalayList[] = MalayFileList(malayTable);

		HashMap<String, String> wordList = new HashMap<String, String>();
		String mWord = "";
		String wiki = "";
		// Take both Malay Word and Wikipedia Index
		for (int i = 0; i < MalayList.length; i++) {
			String MalayWord[] = MalayList[i].split(":");

			mWord = MalayWord[0];
			wiki = MalayWord[1];

			// Insert mWord and wiki into HashMap
			wordList.put(mWord, wiki);
		}

		return wordList.get(word);
	}
}
