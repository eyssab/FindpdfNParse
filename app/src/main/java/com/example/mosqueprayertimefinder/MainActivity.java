package com.example.mosqueprayertimefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private String link = "";
    private int clicks = 0;

    docFinder docFin = new docFinder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.com/");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    public void onBtnClickLinkFound(View view) throws InterruptedException {
        link = webView.getUrl();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //TODO your background code
                ArrayList visited = new ArrayList();
                link = docFin.findLink(link, visited);
            }
        });

        //ALLOWS THE ASYNC FUNCTION TO COMPLETE THE CRAWL(PAUSES FOR 4 SECONDS)
        Thread.sleep(4000);

        Intent intent = new Intent(MainActivity.this, dataFormatted.class);

        intent.putExtra("pdfLink", link);

        startActivity(intent);
    }
}