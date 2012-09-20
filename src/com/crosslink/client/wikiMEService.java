package com.crosslink.client;

import java.io.IOException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Webcontent")
public interface wikiMEService extends RemoteService {
   Webcontent getwebcontent(String weburl) throws IOException;
}