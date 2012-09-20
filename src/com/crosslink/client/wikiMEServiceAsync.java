package com.crosslink.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface wikiMEServiceAsync {
	void getwebcontent(String weburl, AsyncCallback<Webcontent> callback);
}