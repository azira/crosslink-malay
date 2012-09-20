package com.crosslink.client;
import java.io.BufferedReader;
import java.net.URLConnection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
 
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Crosslink_malayv3 implements EntryPoint {
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
    	final HTML html = new HTML();
    	try {
    	  URL url = "http://zee-note.net";
    	    URLConnection urlConn = url.openConnection();
    	    urlConn.setReadTimeout(100000);
    	    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
    	    String line;

    	    while ((line = reader.readLine()) != null) {
    	        message = message.concat(line);
    	    }
    	    reader.close();

    	} catch (MalformedURLException e) {
    	message = e.getMessage();
    	} catch (IOException e) {
    	message = e.getMessage();
    	}
	 RootPanel.get("html").add(html);
    }
}