package ServerClient;

public class Follower {
	private String id;
	private Client client;
	
	public Follower(String id,Client client){
		this.id=id;
		this.client=client;
	}
	public String getID(){
		return this.id;
	}
	public Client getClient(){
		return this.client;
	}

}
