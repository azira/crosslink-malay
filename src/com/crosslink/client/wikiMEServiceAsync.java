package com.crosslink.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface wikiMEServiceAsync {
	void getwebcontent(String weburl, String malayTable, AsyncCallback<Webcontent> callback);
	void getMalayWiki(String word, String malayTable,
			AsyncCallback<String> callback);
} 