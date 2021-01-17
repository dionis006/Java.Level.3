package com.chat;

import java.io.*;

public class LogService {

    public void doBufferedWriter(File file, String values) {
        if (values != "cmd auth: Status OK") {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                bw.newLine();
                bw.write(values);
                bw.flush();
            } catch (Exception e) {
                throw new RuntimeException("SWW", e);
            }
        }
    }

    public StringBuilder doBufferedReader(int n) {
        File file = new File(
                "E:\\Rustam\\Java\\=Обучение=\\Java.Level.3\\Lesson 3\\src\\com\\chat\\ServerLog.txt"
        );
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            StringBuilder stringBuilder = new StringBuilder();
            long length = file.length() - 1;
            int readLines = 0;
            for (long i = length; i >= 0; i--) {
                randomAccessFile.seek(i);
                char c = (char) randomAccessFile.read();
                if (c == '\n') {
                    readLines++;
                    if (readLines == n) {
                        break;
                    }
                }
                stringBuilder.append(c);
            }
            stringBuilder.reverse();
            return stringBuilder;
        } catch (Exception e) {
            throw new RuntimeException("SWW", e);
        }
    }

//    public List<String> doBufferedReader(int n) {
//        File file = new File(
//                "E:\\Rustam\\Java\\=Обучение=\\Java.Level.3\\Lesson 3\\src\\com\\chat\\ServerLog.txt"
//        );
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
//            List<String> lines = new ArrayList<>();
//            String line;
//
//            while ((bufferedReader.readLine()) != null) {
//                line = bufferedReader.readLine();
//                lines.add(line);
//            }
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int i = lines.size() - 1; i >= lines.size() - n ; i--) {
//
//            }
//
//            return lines;
//        } catch (Exception e) {
//            throw new RuntimeException("SWW", e);
//        }
//    }

}
