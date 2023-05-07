package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DeleteInfo {
    FileReader reader;
    App app = new App();
    String line = app.getLine();
    int findPrice;
    int size=0;

    public void deleteInfo(String line) {
        priceThree(line);//Определяем из запроса сколько акциий надо отнять size переменная
        String temp = edSell(line);//Опредляем из запроса откуда снимать из ask or bid
        if (temp.equals("sell")) {
            findSell();//определяем самую высокую цену
            deleteStringSell();

        }
        //удалить предложение продавцов в самой высокой ценой
        if (temp.equals("buy")) {
            findBuy();//определяем самую низкую цену
            deleteStringBuy();

        }

    }

    public String edSell(String ed) {//метод определения действия из запроса
        String sell = null;
        String[] words = ed.split(",");
        int i = 0;
        for (String word : words) {
            if (i == 1) {
                sell = word;
            }
            i++;
        }
        return sell;
    }

    public int priceTwo(String scan) {//Метод для выделения из сторки файла второго слова "" значения цены
        int price = 0;
        String[] words = scan.split(",");
        int i = 0;
        for (String word : words) {
            if (i == 1) {
                String word1 = word;
                price = Integer.valueOf(word1).intValue();
            }
            i++;
        }
        return price;
    }

    public int findSell() { //Метод нахождения строки покупателя с миним ценами
        try {
            reader = new FileReader("input.txt");
            Scanner scanner = new Scanner(reader);
            int constPrice = 1000000000;
            while (scanner.hasNextLine()) {
                String scan = scanner.nextLine();
                if (scan.startsWith("u") && scan.endsWith("bid")) {
                    int pr = priceTwo(scan);
                    if (pr < constPrice) {
                        constPrice = pr;
                    }
                }
            }
            findPrice = constPrice;
            reader.close();
            scanner.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return findPrice;
    }

    public void deleteStringSell() {//Метод удаления строки продавца с высокими ценами

        try {
            Scanner in = new Scanner(new File("input.txt"));
            while (in.hasNextLine()) {
                String scan = in.nextLine();
                if (scan.startsWith("u") && scan.endsWith("bid")) {
                    String[] words = scan.split(",");
                    int i = 0;
                    int q=0;
                    for (String word : words) {
                        if (i == 1) {
                            String word1 = word;
                            if(findPrice == Integer.valueOf(word1).intValue()){
                                i=2;
                                String word3=words[i];

                                q=Integer.valueOf(word3).intValue();
                                size=q-size;
                            }
                        }
                        i++;
                    }

                }

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String lineEd="u,"+findPrice+","+size+",bid";
        orderWrite(lineEd);

    }

    public boolean edPrice(String check) {//Метод для выделения из сторки запроса второго слова "size"
        boolean price = false;
        String word2=Integer.valueOf(findPrice).toString();
        String[] words = check.split(",");
        int i = 0;
        for (String word : words) {
            if (i == 1) {
                if (word.equals(word2)) {
                    price = true;
                }
            }
            i++;
        }
        return price;
    }
    public int findBuy(){ //Метод нахождения строки продавца максим ценами
        try {
            reader = new FileReader("input.txt");
            Scanner scanner=new Scanner(reader);
            int constPrice=0;
            while (scanner.hasNextLine()) {
                String scan= scanner.nextLine();
                if(scan.startsWith("u") && scan.endsWith("ask")) {
                    int pr = priceTwo(scan);
                    if (pr > constPrice) {
                        constPrice = pr;
                    }
                }
            }
            findPrice=constPrice;
            reader.close();
            scanner.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return findPrice;
    }
    public void deleteStringBuy(){//Метод удаления строки покупателя с низкими ценами
        try {
            Scanner in = new Scanner(new File("input.txt"));
            while (in.hasNextLine()) {
                String scan = in.nextLine();
                if (scan.startsWith("u") && scan.endsWith("ask")) {
                    String[] words = scan.split(",");
                    int i = 0;
                    int q=0;
                    for (String word : words) {
                        if (i == 1) {
                            String word1 = word;
                            if(findPrice == Integer.valueOf(word1).intValue()){
                                i=2;
                                String word3=words[i];
                                q=Integer.valueOf(word3).intValue();
                                size=q-size;
                            }
                        }
                        i++;
                    }

                }

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String lineEd="u,"+findPrice+","+size+",ask";
        orderWrite(lineEd);
    }
    public int priceThree(String scan){//Метод для выделения из сторки файла третьего слова "price" значения цены

        String[]words=scan.split(",");
        int i=0;
        for(String word: words){
            if(i==2){
                String word1 =word;
                size=Integer.valueOf(word1).intValue();
            }
            i++;
        }
        return size;
    }
    public void orderWrite(String line) {//Записывает в файл и возвращает записанную строку
        FileWriter writer;

        {
            try {
                writer = new FileWriter("input.txt", true);

                writer.write(line+"\n");
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
