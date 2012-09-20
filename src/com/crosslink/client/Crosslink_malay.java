/**
 * Copyright 2012
 * Author: Hazirah Hamdani
 * Title: WikiME: Cross Language Wikipedia Link Discovery Malay to English
 * Date: July - Nov 2012
 * 
 */

package com.crosslink.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class Crosslink_malay implements EntryPoint {

	private wikiMEServiceAsync wikiMEService = GWT.create(wikiMEService.class);
	public String webarticle;
	

	public void onModuleLoad() {
		/* create UI */
		
		final TextBox urlText = new TextBox();
		urlText.setWidth("400");
		urlText.setText("http://");
		urlText.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					// Verify input url
					if (isUrl(urlText.getText())) {
						Window.alert("Please enter a Malay web source!");
						return;
					
					} else {
	
						/* make remote call to server to get the message */
						wikiMEService.getwebcontent(urlText.getValue(),
								new ContentCallBack());
					}
					
				}
			}
		});
		Label lblName = new Label("Enter Malay Newspaper webpage: ");

		Button goButton = new Button("GO");

		goButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// Verify input url
				if (isUrl(urlText.getText())) {
					Window.alert("Please enter a Malay web source!");
					return;
				
				} else {
				/* make remote call to server to get the message */
				wikiMEService.getwebcontent(urlText.getValue(),
						new ContentCallBack());
				}
			}
		});
		
		RootPanel.get("labelText").add(lblName);
		RootPanel.get("urlField").add(urlText);
		RootPanel.get("goButton").add(goButton);


	}
	
	protected boolean isUrl(String input) {
		if (input.isEmpty() || input.contentEquals("http://") || !input.contains("http://")) {
			return true;
		} else {
			return false;
		}
	}

	private class ContentCallBack implements AsyncCallback<Webcontent> {
		@Override
		public void onFailure(Throwable caught) {
			/* server side error occured */
			Window.alert("Unable to obtain server response: "
					+ caught.getMessage());
		}

		@Override
		public void onSuccess(Webcontent result) {
			Window.alert("The webpage might take up to 1 minute to load! Please wait!");
			/* server returned result, show user the message */
			webarticle = result.getwebContent();
			HTMLPanel html = new HTMLPanel(webarticle);
			anchorArticle(html);
			RootPanel.get("htmlContainer").add(html);
			
			
		}

		/**
		 * Add anchor link to respective Malay Word to English Wikipedia
		 * Use Wikipedia Database Dump
		 * 
		 * @param html widget
		 */
		private void anchorArticle(HTMLPanel html) {
			// Find Tagged Words
			NodeList<Element> anchors = html.getElement().getElementsByTagName("anchor");
			
			for ( int i = 0 ; i < anchors.getLength() ; i++ ) {
			    Element anchor = anchors.getItem(i);
			    String anchorWord = anchor.getInnerHTML();
			    // Find Word in Wikipedia Dump and retrieve English Wikipedia Link
			    String anchorURL = "http://en.wikipedia.org/wiki/Transcript_(law)";
			   
			    
			    if (anchor.getInnerHTML().contentEquals("Protokol")) {
			    	
			    	Anchor link = new Anchor(anchorWord, false, anchorURL, "_blank");
			    	html.addAndReplaceElement(link, anchor);
	
			    }
			    
			  
			}
			
		}
	}
}
