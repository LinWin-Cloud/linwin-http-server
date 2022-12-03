//Muslti Server For LinWIn Http Server Linux.
/**
 * Auther: LinWin Cloud.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class MultiServer {
    
    // the init value for the multi server.
    public static String ServerDir = "";
    public static int ServerPort = 0;
    public static Boolean Sql_safe = false;
    public static Boolean Xss_safe = false;
    public static String configRoot = "../../config/";
    public static String rulesRoot = "../../rules/";
    public static String Version = "";
    public static String[] IPBlack = {""};
    public static String errorPage = "";
    public static String[] doNotVisit = {""};
    public static String[] urlOK = {""};
    public static String DebugDir = "../";
    public static String[] defaultPage = {""};
    public static String strict_origin_when_cross_origin = "";

    public static void main(String[] args) {

        try {
            //using executorService Function to load run the init code
            //init all the value of the server.
            ExecutorService executorService = Executors.newFixedThreadPool(10000);
            Future<Integer> future = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    //init all the value of the multi server
                    //get the serverpath , server port and other.
                    init.initAllValue(args);
                    return 0;
                }
            });
            executorService.shutdown();
            //get the run result.
            future.get();
            //if finish the options then run the server.

            Thread lookUpInitValueThread = new Thread(new Runnable() {
                public void run() {
                    //this is thread to reload all the value in a time.
                    while (true) {
                        try{
                            Thread.sleep(1000*60*5);
                            init.initAllValue(args);
                        }catch(Exception exception){
                            exception.printStackTrace();
                        }
                    }
                }
            });
            lookUpInitValueThread.start();
            //the main server software
            for (int i = 0;i < MultiServer.defaultPage.length ;i++) {
                //System.out.println(MultiServer.defaultPage[i]);
            }   
            WebServer.mainWebServer();

        }catch(Exception exception) {
            exception.printStackTrace();
        }
    }
    public static void sendAPI(PrintWriter pWriter,Socket socket,String message) {
        pWriter.println("HTTP/1.1 200 OK");
        pWriter.println("Content-Type:text/html");
        pWriter.println();
        pWriter.println(message);
        pWriter.flush();
        try {
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}