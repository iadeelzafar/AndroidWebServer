package com.mikhaellopez.androidwebserver;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Mikhael LOPEZ on 14/12/2015.
 */
public class AndroidWebServer extends NanoHTTPD {
private Context context;
    public AndroidWebServer(int port, Context context) {
        super(port);
        this.context=context;
    }

    public AndroidWebServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        //String msg = "<html><body><h1>Hello server</h1>\n";
        //Map<String, String> parms = session.getParms();
        //if (parms.get("username") == null) {
        //    msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
        //} else {
        //    msg += "<p>Hello, " + parms.get("username") + "!</p>";
        //}
        //return newFixedLengthResponse( msg + "</body></html>\n" );

      InputStream myInput = null;
      try {
        myInput = context.getAssets().open("sound.mp3");
      } catch (IOException e) {
        e.printStackTrace();
      }
      return createResponse(Response.Status.OK, "audio/mpeg", myInput);
    }

  //Announce that the file server accepts partial content requests
  private Response createResponse(Response.Status status, String mimeType,
      InputStream message) {
    return newChunkedResponse(status, mimeType, message);
  }

}
