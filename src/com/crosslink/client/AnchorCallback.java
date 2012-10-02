package com.crosslink.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;

public class AnchorCallback implements AsyncCallback<HTMLPanel> {

	@Override
	public void onFailure(Throwable caught) {
		/* server side error occured */
		Window.alert("Unable to access hashmap: "
				+ caught.getMessage());

	}

	@Override
	public void onSuccess(HTMLPanel result) {
		// TODO Auto-generated method stub

	}

}
