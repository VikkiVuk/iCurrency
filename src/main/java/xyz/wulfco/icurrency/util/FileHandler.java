package xyz.wulfco.icurrency.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    public static boolean write(String path, String content) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(content);
            fileWriter.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String read(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            String content = "";
            int i;
            while ((i = fileReader.read()) != -1) {
                content += (char) i;
            }
            fileReader.close();

            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean exists(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            fileReader.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
