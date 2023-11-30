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
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return in.readObject();
        }
    }
}
