package com.mikhaellopez.androidwebserver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Mikhael LOPEZ on 14/12/2015.
 */
public class AndroidWebServer extends NanoHTTPD {
private Context context;
private static final int PICK_FILE_REQUEST = 1;
  File selectedFile;

    public AndroidWebServer(int port, Context context, File selectedFile ) {
        super(port);
        this.context=context;
        this.selectedFile=selectedFile;

    }

    public AndroidWebServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
      //InputStream myInput = null;
      FileInputStream fileInputStream=null;
      try {
        fileInputStream = new FileInputStream(selectedFile);
      } catch (IOException e) {
        e.printStackTrace();
      }

      return createResponse(Response.Status.OK, "audio/mpeg", fileInputStream);
    }

  //Announce that the file server accepts partial content requests
  private Response createResponse(Response.Status status, String mimeType,
      FileInputStream message) {
    return newChunkedResponse(status, mimeType, message);
  }

}
