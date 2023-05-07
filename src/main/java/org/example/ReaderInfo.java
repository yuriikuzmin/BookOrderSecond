package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class ReaderInfo {
    App app=new App();
    String line= app.getLine();
    public boolean writerInfo(String line) {
        boolean w=false;
        if(line.endsWith("bid") || line.endsWith("ask")){
            w=bidWrite(line);
           }
        return w;
    }
    public boolean bidWrite(String line){
        int price=0;
        int size=0;
        int i=0;
        int temp=0;
        boolean w=false;
        String[]words=line.split(",");
        for(String word: words){
            if(i==1){
                String word1 =word;
                price=Integer.valueOf(word1).intValue();
                int p=1000000000;
                if((1<price) & (price <= p)){
                  temp=1;
                }
            }
            if(i==2){
                String word2=word;
                size=Integer.valueOf(word2).intValue();
                int p=100000000;
                if((0<=size) & (size<=p) & (temp==1)){
                   w=true;
                }
            }
            i++;
        }
        return w;
    }
}
