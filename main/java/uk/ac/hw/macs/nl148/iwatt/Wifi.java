package uk.ac.hw.macs.nl148.iwatt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Wifi extends AppCompatActivity {
    private WebView webv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        webv = (WebView) findViewById(R.id.wifi_webview);
        WebSettings webSettings = webv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webv.loadUrl("http://www.hw.ac.uk/is/it-essentials/wifi.htm");
    }
}
