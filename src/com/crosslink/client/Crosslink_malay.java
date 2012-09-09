package com.crosslink.client;

import com.crosslink.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Crosslink_malay implements EntryPoint {


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		final Button goButton = new Button("Go");
		final TextBox nameField = new TextBox();
		// Make a new frame
	    final Frame webFrame = new Frame();
		nameField.setText("http://");
		final Label errorLabel = new Label();
		
		// We can add style names to widget
		goButton.addStyleName("goButton");
		webFrame.addStyleName("webFrame");
			
		// Add the nameField and goButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(goButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		
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
					webFrame.setUrl(inputUrl);
					webFrame.getElement().getStyle().setBorderWidth(0, Unit.PX);
					IFrameElement.as(webFrame.getElement()).setFrameBorder(0);
					webFrame.setSize("100%", "700px");
					RootPanel.get("frameContainer").add(webFrame);
				}
			
		 
		});
	}
}
//
//		// Create the popup dialog box
//		final DialogBox dialogBox = new DialogBox();
//		dialogBox.setText("Remote Procedure Call");
//		dialogBox.setAnimationEnabled(true);
//		final Button closeButton = new Button("Close");
//		// We can set the id of a widget by accessing its Element
//		closeButton.getElement().setId("closeButton");
//		final Label textToServerLabel = new Label();
//		final HTML serverResponseLabel = new HTML();
//		VerticalPanel dialogVPanel = new VerticalPanel();
//		//dialogVPanel.addStyleName("dialogVPanel");
//		//dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
//		//dialogVPanel.add(textToServerLabel);
//		//dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
//		//dialogVPanel.add(serverResponseLabel);
//		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
//		dialogVPanel.add(closeButton);
//		dialogBox.setWidget(dialogVPanel);
//
//		// Add a handler to close the DialogBox
//		closeButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				dialogBox.hide();
//				goButton.setEnabled(true);
//				goButton.setFocus(true);
//			}
//		});

//		// Create a handler for the goButton and nameField
//		class MyHandler implements ClickHandler, KeyUpHandler {
//			/**
//			 * Fired when the user clicks on the goButton.
//			 */
//			
//			 
//			public void onClick(ClickEvent event) {
//				String inputUrl = nameField.getText();
//				Window.Location.assign(inputUrl);
//				//sendNameToServer();
//				
//			}
//
//			/**
//			 * Fired when the user types in the nameField.
//			 */
//			public void onKeyUp(KeyUpEvent event) {
//				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//					//sendNameToServer();
//					
//				}
//			}
//			
//			/**
//			 * Validating website URL
//			 */
//			
//		

//			/**
//			 * Send the name from the nameField to the server and wait for a response.
//			 */
//			private void sendNameToServer() {
//				// First, we validate the input.
//				errorLabel.setText("");
//				String textToServer = nameField.getText();
//				if (!FieldVerifier.isValidName(textToServer)) {
//					errorLabel.setText("Please enter at least four characters");
//					return;
//				}
//
//				// Then, we send the input to the server.
//				goButton.setEnabled(false);
//				textToServerLabel.setText(textToServer);
//				serverResponseLabel.setText("");
//				greetingService.greetServer(textToServer,
//						new AsyncCallback<String>() {
//							public void onFailure(Throwable caught) {
//								// Show the RPC error message to the user
//								dialogBox
//										.setText("Remote Procedure Call - Failure");
//								serverResponseLabel
//										.addStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(SERVER_ERROR);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//
//							public void onSuccess(String result) {
//								dialogBox.setText("Remote Procedure Call");
//								serverResponseLabel
//										.removeStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(result);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//						});
			//}
//		}
//
//		// Add a handler to send the name to the server
//		MyHandler handler = new MyHandler();
//		goButton.addClickHandler(handler);
//		nameField.addKeyUpHandler(handler);

