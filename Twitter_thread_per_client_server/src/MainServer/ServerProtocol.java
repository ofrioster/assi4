package MainServer;

public interface ServerProtocol {
	
    
    String processMessage(String msg);
    boolean isEnd(String msg);
    

}
