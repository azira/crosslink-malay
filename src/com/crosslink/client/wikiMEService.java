package com.crosslink.client;

import java.io.IOException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("Webcontent")
public interface wikiMEService extends RemoteService {
   Webcontent getwebcontent(String weburl, String malayTable) throws IOException;
   String getMalayWiki(String word, String malayTable);
}