package com.lukec.currency.repository.impl;

import org.springframework.stereotype.Service;

import com.lukec.currency.repository.UrlReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class UrlReaderImpl implements UrlReader {
	public String readUrl(String urlToRead) {
		String parseLine = ""; 
		StringBuffer parsedLines = new StringBuffer();
        try {
                       
            URL URL = new URL(urlToRead); 
            BufferedReader br = new BufferedReader(new InputStreamReader(URL.openStream()));

            while ((parseLine = br.readLine()) != null) {
            	parsedLines.append(parseLine);
            }
            br.close();

        } catch (MalformedURLException me) {
            System.out.println(me);

        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        return parsedLines.toString();
    }//class end
}
