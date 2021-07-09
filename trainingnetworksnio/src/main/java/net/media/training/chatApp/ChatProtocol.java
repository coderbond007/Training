package net.media.training.chatApp;


public class ChatProtocol {
    private static final int WAITING = 0;
    private static final int LOGIN = 1;
    private static final int CHATTING = 2;

    private int state;
    private String name;

    public String processInput(String theInput, ChatMultiServerThread user) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Enter Screen Name";
            state = LOGIN;
        } else if (state == LOGIN) {
        	name = theInput;
        	ChatMultiServer.addUser(user);
        	ChatMultiServer.sendToAll(name + " logged in");
        	state = CHATTING;
        	theOutput = "Login Succeeded";
        } else if (state == CHATTING) {
            if ("quit".equalsIgnoreCase(theInput)) {
                ChatMultiServer.sendToAll(name + " exited");
            	ChatMultiServer.removeUser(user);
                theOutput = "Bye.";
            } else {
                ChatMultiServer.sendToAll(name + ':' + theInput);
                theOutput = "Message Sent";
            }
        } 
        return theOutput;
    }
}