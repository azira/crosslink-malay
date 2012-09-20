/**
 * Copyright 2012
 * Author: Hazirah Hamdani
 * Title: WikiME: Cross Language Wikipedia Link Discovery Malay to English
 * Date: July - Nov 2012
 * 
 */
package com.crosslink.client;
import java.io.BufferedInputStream;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.http.client.Response;
	/**
	 * Entry point classes define onModuleLoad().
	 */
	public class Crosslink_malayv4 implements EntryPoint {
 
		public static final int STATUS_CODE_OK = 200;
		
		
 
		public static void doGet(String url) {
			// Send request to server by replacing RequestBuilder code with a call to a JSNI method.
		    
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
 
			try {
				Request response = builder.sendRequest(null, new RequestCallback() {
					public void onError(Request request, Throwable exception) {
						if (exception instanceof RequestTimeoutException) {
							Window.alert("The request has timed out");
						} else {
	        			Window.alert(exception.getMessage());
						}
					}
					public void onResponseReceived(Request request, Response response) {
						if (STATUS_CODE_OK == response.getStatusCode()) {
							HTML responseLabel=new HTML("<h3>Response is gonna be updated here</H3>");
							responseLabel.setHTML(response.getText());
							responseLabel.addStyleName("response");
							RootPanel.get().add(responseLabel);
						} else {
							Window.alert(""+response.getStatusCode());
						}
					}
				});
			} catch (RequestException e) {
				// Code omitted for clarity
			}
		}
 
		public void onModuleLoad() {
			Button button=new Button("Get Response");
			button.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					doGet("Response1.html");
				}
			});
			RootPanel.get("container").add(button);
		}	
		
		
		
	
	}