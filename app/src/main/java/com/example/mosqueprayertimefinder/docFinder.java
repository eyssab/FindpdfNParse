package com.example.mosqueprayertimefinder;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class docFinder {

    public String findLink(String url, ArrayList<String> v) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if (doc != null) {
                if (con.response().statusCode() == 200) {
                    System.out.println("Link: " + url);
                    System.out.println(doc.title());
                    v.add(url);

                    //FIRST CASE SCENARIO: Does the site have a pdf format?
                    Elements links = doc.select("a[href]");

                    for (int i = 0; i < links.size(); i++) {
                        if (links.get(i).absUrl("href").toString().contains("Prayer") == true || links.get(i).absUrl("href").toString().contains("Pray") == true || links.get(i).absUrl("href").toString().contains("times") == true || links.get(i).absUrl("href").toString().contains("schedule") == true || links.get(i).absUrl("href").toString().contains("times") == true || links.get(i).absUrl("href").toString().contains("prayer-times") == true || links.get(i).absUrl("href").toString().contains("times") == true) {
                            //Does the site have a pdf format?
                            if (links.get(i).absUrl("href").toString().contains(".pdf") == true) {
                                //write to a document
                                return links.get(i).absUrl("href").toString();
                            } else {
                                if(!v.contains(links.get(i).absUrl("href").toString())) {
                                    return findLink(links.get(i).absUrl("href").toString(), v);
                                }
                            }
                        }
                    }
                    //SECOND CASE SCENARIO: Does the site have the word "fajr"
                    Elements elements = doc.body().select("*");

                    for (int i = 0; i<elements.size();i++) {
                        if(elements.get(i).toString().contains("Time")){
                            return url;
                        }
                    }
                }
            }
            return "non-existent";
        } catch (IOException e) {
            return null;
        }
    }
}