package Helper;

import java.io.*;
import java.util.Map;

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
}
