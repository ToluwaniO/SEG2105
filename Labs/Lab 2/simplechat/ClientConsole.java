// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package simplechat;

import simplechat.client.ChatClient;
import simplechat.common.ChatIF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF {
    //Class variables *************************************************

    /**
     * The default port to connect on.
     */
    final public static int DEFAULT_PORT = 5555;

    //Instance variables **********************************************

    /**
     * The instance of the client that created this ConsoleChat.
     */
    ChatClient client;


    //Constructors ****************************************************

    /**
     * Constructs an instance of the ClientConsole UI.
     *
     * @param host The host to connect to.
     * @param port The port to connect on.
     * @param username The username to define for myself
     */
    public ClientConsole(String username, String host, int port) {
        try {
            client = new ChatClient(username, host, port, this);
        } catch (IOException exception) {
            System.out.println("Cannot open connection.  Awaiting command.");
        }
    }


    //Instance methods ************************************************

    /**
     * This method waits for input from the console.  Once it is
     * received, it sends it to the client's message handler.
     */
    public void accept() {
        try {
            BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
            String message;

            while ((message = fromConsole.readLine()) != null) {
                client.handleMessageFromClientConsole(message);
            }
        } catch (Exception ex) {
            System.out.println("Unexpected error while reading from console!");
        }
    }

    /**
     * This method overrides the method in the ChatIF interface.  It
     * displays a message onto the screen.
     *
     * @param message The string to be displayed.
     */
    public void display(String message, int messageType) {
        switch (messageType) {
            case 0:
                System.out.println("SERVER MESSAGE > " + message);
                break;
            default:
                System.out.println(message);
        }
    }


    //Class methods ***************************************************

    /**
     * This method is responsible for the creation of the Client UI.
     *
     * @param args[0] The host to connect to.
     * @param args[1] The port to use for connection
     */
    public static void main(String[] args) {
        //**** Changed for E49: Added Exceptions for port input
        String host;
        int port;
        String username;

        try {
            username = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("No username provided!");
            System.exit(1);
            return;
        }

        try {
            host = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            host = "localhost";
        }


        try {
            port = Integer.parseInt(args[2]);
        } catch (Exception e) {
            port = DEFAULT_PORT;
        }
        ClientConsole chat = new ClientConsole(username, host, port);
        chat.accept();  //Wait for console data
    }
}
//End of ConsoleChat class
