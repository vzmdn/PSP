
import java.io.FileWriter;
import java.io.IOException;

class Log {

    private static final String FILE_NAME = "log.txt";

    public static synchronized void registrar(String missatge) {

        try (FileWriter log = new FileWriter(FILE_NAME, true)) {

            log.write(missatge + "\\n");

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}

