
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatCLI {
    static PrintWriter out;
    Thread thread = new Thread();
    public static void main(String[] args) throws IOException {
        System.out.println("Hej, hvilken server vil du tilsutte dig? (Tryk ENTER for localhost.)");
        Scanner scanner = new Scanner(System.in);
        String serverString = scanner.nextLine();
        if (serverString.equals("")) { serverString = "localhost"; }

        System.out.println("Tilslutter til " + serverString);
        var socket = new Socket(serverString, 59002);
        Scanner in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        while (in.hasNextLine()) {
            var line = in.nextLine();
            if (line.startsWith("SUBMITNAME")) {
                System.out.println("Skriv dit navn");
                String mitNavn =  scanner.nextLine();
                out.println(mitNavn);
            } else if (line.startsWith("NAMEACCEPTED")) {
                System.out.println("Chatter - " + line.substring(13));
                sendMessage(Thread.currentThread());
            } else if (line.startsWith("MESSAGE")) {
                System.out.println(line.substring(8) + "\n");
                sendMessage(Thread.currentThread());
            }

        }

    }

    public static void sendMessage(Thread thread) {
        thread = new Thread() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Skriv en besked");
                String besked = scanner.nextLine();
                out.println(besked);
            }
        };
    }
}

