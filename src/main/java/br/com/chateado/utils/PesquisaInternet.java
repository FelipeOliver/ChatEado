package br.com.chateado.utils;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.google.gson.Gson;

public class PesquisaInternet {  
    public static void main(String args[]) {  
        try {  
            final String google = "https://www.googleapis.com/customsearch/v1?key=AIzaSyC3pR_NB1eVlT24GbKPAtuBAxS5Yi9avJM&cx=017576662512468239146:omuauf_lfve&q=";  
            final String charset = "UTF-8";  

            URL url = new URL(google + URLEncoder.encode("cera" + "wikipedia", charset));  
            Reader reader = new InputStreamReader(url.openStream(), charset);  
            GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);  

            // Mostra o titulo e a url do primeiro resultado  
            System.out.println(results.getResponseData().getResults().get(0).getTitle());  
            System.out.println(results.getResponseData().getResults().get(0).getUrl());  
              
        }catch(Exception e) {  
              
            e.printStackTrace();  
        }  
    }  
      
    public class GoogleResults {  
  
        private ResponseData responseData;  
        public ResponseData getResponseData() { return responseData; }  
        public void setResponseData(ResponseData responseData) { this.responseData = responseData; }  
        public String toString() { return "ResponseData[" + responseData + "]"; }  
  
         class ResponseData {  
            private List<Result> results;  
            public List<Result> getResults() { return results; }  
            public void setResults(List<Result> results) { this.results = results; }  
            public String toString() { return "Results[" + results + "]"; }  
        }  
  
         class Result {  
            private String url;  
            private String title;  
            public String getUrl() { return url; }  
            public String getTitle() { return title; }  
            public void setUrl(String url) { this.url = url; }  
            public void setTitle(String title) { this.title = title; }  
            public String toString() { return "Result[url:" + url +",title:" + title + "]"; }  
        }  
  
    }  
}  