package com.crosslink.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.TextResource;

/** 
 * Compile to Javascript
 * \
 *
 */

public interface MyResource extends ClientBundle {
	public static final MyResource INSTANCE = GWT.create(MyResource.class);
	
	@Source("lang_links_M2E_indexed.txt")
	public TextResource loadTable();
	
	@Source("Crosslink_malay.css")
	@CssResource.NotStrict
	CssResource css();

}
