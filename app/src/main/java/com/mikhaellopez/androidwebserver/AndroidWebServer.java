package com.mikhaellopez.androidwebserver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.MimeTypeMap;
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
  String selectedFilePath;
  String type;

    public AndroidWebServer(int port, Context context, String selectedFilePath ) {
        super(port);
        this.context=context;
        this.selectedFilePath=selectedFilePath;
        findMimeType();

    }

  private void findMimeType() {
    String extension = MimeTypeMap.getFileExtensionFromUrl(selectedFilePath);
    if (extension != null) {
      type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
      Log.v("DANG","The type is : "+type);
    }
    else
      Log.v("DANG","In else");

  }

  public AndroidWebServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {

      selectedFile = new File(selectedFilePath);

      FileInputStream fileInputStream=null;
      try {
        fileInputStream = new FileInputStream(selectedFile);
      } catch (IOException e) {
        e.printStackTrace();
      }

      return createResponse(Response.Status.OK, type, fileInputStream);
    }

  //Announce that the file server accepts partial content requests
  private Response createResponse(Response.Status status, String mimeType,
      FileInputStream message) {
    return newChunkedResponse(status, mimeType, message);
  }

}
