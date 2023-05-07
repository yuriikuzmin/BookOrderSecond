package org.example;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RequestInfo {
    App app=new App();
    String line= app.getLine();
    String question;
    int count;
    public boolean requestInfo(String line) {
        boolean wr = false;
        FileReader reader;
        if (!line.endsWith("best_bid") & !line.endsWith("best_ask")) {
            String temp=sizePrice(line);
            if(temp.equals("size")) {//Отработка по запросу с ценой

                try {
                    reader = new FileReader("input.txt");
                    Scanner scanner = new Scanner(reader);

                    int constPrice = priceThree(line);//возвращает значение цены указанное в запросе
                    while (scanner.hasNextLine()) {
                        String scan = scanner.nextLine();
                        if (scan.startsWith("u")) {
                            int pr = price(scan);
                            if (constPrice == pr) {
                                count++;

                            }
                        }
                    }
                    String countReq = Long.toString(count);
                    FileWriter writer = null;
                    try {
                        writer = new FileWriter("output.txt", true);
                        writer.write(countReq + "\n");
                        writer.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    wr=true;
                    reader.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } else {
            if (line.endsWith("best_bid")) {
                bestBid(line);

            }
            if (line.endsWith("best_ask")) {
                bestAsk(line);
            }
            wr=true;
            questionWrite(question);
        }

        return wr;
    }
    //Mетод для выбора bid
    public String bestBid(String line){
        FileReader reader = null;
        try {
            reader = new FileReader("input.txt");
            Scanner scanner=new Scanner(reader);
            int constPrice=0;

            while (scanner.hasNextLine()) {
                String scan= scanner.nextLine();
                if(scan.startsWith("u")&& scan.endsWith("bid")) {
                    int pr = price(scan);
                    if (pr > constPrice) {
                        constPrice = pr;
                        question = scan;
                    }
                }
            }
            reader.close();
            scanner.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return  question;
    }
    //Метод для выделения из сторки файла второго слова "size" значения цены
    public int price(String scan){
        int price=0;
        String[]words=scan.split(",");
        int i=0;
        for(String word: words){
            if(i==1){
                String word1 =word;
                price=Integer.valueOf(word1).intValue();
            }
            i++;
        }

        return price;
    }
    //Для простого запроса с ASK
    public String bestAsk(String line){
        FileReader reader = null;
        try {
            reader = new FileReader("input.txt");
            Scanner scanner=new Scanner(reader);
            int constPrice=1000000000;

            while (scanner.hasNextLine()) {
                String scan= scanner.nextLine();
                if(scan.startsWith("u")&& scan.endsWith("ask")) {
                    int pr = price(scan);
                    if (pr < constPrice) {
                        constPrice = pr;
                        question = scan;
                    }
                }
            }
            reader.close();
            scanner.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return  question;
    }

    public String sizePrice(String scan){//Метод для выделения из сторки запроса второго слова "size"
        String price=null;
        String[]words=scan.split(",");
        int i=0;
        for(String word: words){
            if(i==1){

                price=word;
            }
            i++;
        }
        return price;
    }
    public int priceThree(String scan){//Метод для выделения из сторки файла третьего слова "price" значения цены
        int price=0;
        String[]words=scan.split(",");
        int i=0;
        for(String word: words){
            if(i==2){
                String word1 =word;
                price=Integer.valueOf(word1).intValue();
            }
            i++;
        }
        return price;
    }
    public void questionWrite(String question){ //Метод для записи результатов запроса в файл
        try {
            FileWriter quest=new FileWriter("output.txt", true);
            String str=question.substring(2);
            String str_temp=str.replace("ask","bid" );
            String str_out=str_temp.replace(",bid","");
            quest.write(str_out+"\n");
            quest.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
