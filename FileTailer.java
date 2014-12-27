package mltailer;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
 
public class FileTailer {
    public static void main(String[] args) {
        File file = new File("C:/Program Files/MarkLogic/Data/Logs/ErrorLog.txt");
        int delay = 1;
        
      TailerListener listener = new MyTailerListener();
      Tailer tailer = new Tailer(file, listener, delay);
      Thread thread = new Thread(tailer);
      thread.setDaemon(true); // optional
      thread.start();
    }
}