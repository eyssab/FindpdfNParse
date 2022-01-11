package com.example.mosqueprayertimefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.net.URL;

import java.io.*;
import java.util.Scanner;


public class dataFormatted extends AppCompatActivity {
    TextView pdfUrl;
    TextView pdfText;

    String extractedText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_formatted);
    }

    public void onBtnLinkRetrieve(View view) throws IOException{
        MainActivity man = new MainActivity();

        pdfUrl = (TextView)findViewById(R.id.pdfUrl);
        pdfText = (TextView)findViewById(R.id.fajr);

        Intent intent = getIntent();
        String str = intent.getStringExtra("pdfLink");

        pdfUrl.setText(str);


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PdfReader reader = new PdfReader(new URL(str).openStream());

                    int n = reader.getNumberOfPages();

                    for (int i = 0; i < n; i++) {
                        extractedText += PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + " ";
                    }

                    reader.close();
                } catch (Exception e) {
                    // for handling error while extracting the text file.
                    System.out.println("Error found is :" + e);
                }

            }
        });

        if(extractedText != null) {
            Scanner scnr = new Scanner(extractedText);
            while (scnr.hasNext() && scnr.nextLine() == "") {
                pdfText.setText(scnr.nextLine());
                System.out.println(extractedText);
            }
        }
    }
}