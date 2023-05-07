package org.example;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public App() {
    }
    public String getLine() {
        return line;
    }
    public static final void setLine(String line) {
        App.line = line;
    }
    public static String line;

    public static void main(String[] args) {
        String scan;
            Scanner scanner = new Scanner(System.in);
            scan = scanner.nextLine();

        // чтение ввода, проверка параметров, запись в файл input.txt
            if (scan.startsWith("u")) {
                setLine(scan);
                boolean info=false;
                ReaderInfo readerInfo = new ReaderInfo();
                info=readerInfo.writerInfo(line);
                if(info){
                    infoWriter(scan);
                } else {System.out.println("Проверьте введенный запрос");}
            }

        // обработка запроса по фильтрам и запись в output.txt файл
            if (scan.startsWith("q")) {
                RequestInfo requestInfo = new RequestInfo();
               boolean wr= requestInfo.requestInfo(scan);
                if(wr){
                    infoWriter(scan);
                }
            }

        // удаление из файла по фильтрам файл input.txt
            if (scan.startsWith("o")) {
                DeleteInfo deleteInfo = new DeleteInfo();
                deleteInfo.deleteInfo(scan);
                infoWriter(scan);

            }

        }
        public static  void infoWriter(String line){
            FileWriter writer= null;
            try {
                writer = new FileWriter("input.txt", true);
                writer.write(line+"\n");
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

