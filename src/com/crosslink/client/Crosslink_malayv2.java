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
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Crosslink_malayv2 implements EntryPoint {
	
   private MessageServiceAsync messageService = 
   GWT.create(MessageService.class);

   private class MessageCallBack implements AsyncCallback<Message> {
      @Override
      public void onFailure(Throwable caught) {
         /* server side error occured */
         Window.alert("Unable to obtain server response: " 
         + caught.getMessage());	
      }
      @Override
      public void onSuccess(Message result) {
          /* server returned result, show user the message */
         Window.alert(result.getMessage());
      }	   
   }

   public void onModuleLoad() {
      /*create UI */
      final TextBox urlText = new TextBox(); 
      urlText.setWidth("200");
      urlText.addKeyUpHandler(new KeyUpHandler() {
         @Override
         public void onKeyUp(KeyUpEvent event) {
            if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
               /* make remote call to server to get the message */
               messageService.getMessage(urlText.getValue(), 
               new MessageCallBack());
            }				
         }
      });
      Label lblName = new Label("Enter Malay Newspaper: ");

      Button goButton= new Button("GO");

      goButton.addClickHandler(new ClickHandler() {			
      @Override
      public void onClick(ClickEvent event) {
         /* make remote call to server to get the message */
         messageService.getMessage(urlText.getValue(), 
         new MessageCallBack());
      }});



      // Add widgets to the root panel.
      RootPanel.get("labelText").add(lblName);
      RootPanel.get("nameFieldContainer").add(urlText);
      RootPanel.get("sendButtonContainer").add(goButton);
   }    
} 

@Override
public void onModuleLoad() {
	final Button goButton = new Button("Go");
	final TextBox nameField = new TextBox();
	nameField.setWidth("200");
	final HTML html1 = new HTML();
	final Frame webFrame = new Frame();
	nameField.setText("http://");
	//final Label errorLabel = new Label();
	
	// We can add style names to widget
	goButton.addStyleName("goButton");
	//webFrame.addStyleName("webFrame");
     
		
	// Add the nameField and goButton to the RootPanel
	// Use RootPanel.get() to get the entire body element
	RootPanel.get("nameFieldContainer").add(nameField);
	RootPanel.get("sendButtonContainer").add(goButton);
	//RootPanel.get("errorLabelContainer").add(errorLabel);
	
	// Focus the cursor on the name field when the app loads
	nameField.setFocus(true);
	nameField.selectAll();
	
	goButton.addClickHandler(new ClickHandler() {
	

		public void onClick(ClickEvent event) {
			String inputUrl = nameField.getText();
			// Checks if input is empty 
			if (inputUrl.isEmpty() || inputUrl.contentEquals("http://")) {
				Window.alert("Please enter a Malay web source!");
				//errorLabel.setText("Please enter Malay source!");
				return;
			}
			