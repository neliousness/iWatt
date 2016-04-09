package uk.ac.hw.macs.nl148.iwatt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Author: Neio Lucas
 * File : Wifi.java
 * Platform : Android Operating System
 * Date:  02/04/2016.
 * Description: This activity redirects the user to a webpage
 */

public class Wifi extends AppCompatActivity {
    private WebView webv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        webv = (WebView) findViewById(R.id.wifi_webview);
        WebSettings webSettings = webv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //loading url
        webv.loadUrl("http://www.hw.ac.uk/is/it-essentials/wifi.htm");
    }
}
