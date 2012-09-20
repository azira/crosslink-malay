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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class Crosslink_malay implements EntryPoint {

	private wikiMEServiceAsync wikiMEService = GWT.create(wikiMEService.class);
	public String webarticle;
	final HTML html = new HTML();


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
		RootPanel.get("htmlContainer").add(html);

	}
	
	protected boolean isUrl(String input) {
		if (input.isEmpty() || input.contentEquals("http://")) {
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
			Window.alert("The webpage make take up to 1 minute to load!");
			/* server returned result, show user the message */
			webarticle = result.getwebContent();
			html.setHTML(webarticle);
		}
	}
}