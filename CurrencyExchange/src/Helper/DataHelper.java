package Helper;

import java.io.*;
import java.util.Map;
import com.google.gson.Gson;

public class DataHelper {

    public static void exportData(String fileName, Object data) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(data);
        }
    }

    public static Object importData(String fileName) throws IOException, ClassNotFoundException {
        File file = new File(fileName);
        if (file.exists()) {
            System.out.println("Файл загружен "+fileName);
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
                return in.readObject();
            }
        }
        else {
            System.out.println("Файл не существует "+fileName);
            return null;
        }
    }

    private static final Gson gson = new Gson();

    public static void exportDataToJson(String fileName, Object data) throws IOException {
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(data, writer);
        }
    }

    public static <T> T importDataFromJson(String fileName, Class<T> type) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            System.out.println("Файл загружен " + fileName);
            try (Reader reader = new FileReader(fileName)) {
                return gson.fromJson(reader, type);
            }
        } else {
            System.out.println("Файл не существует " + fileName);
            return null;
        }
    }
}
