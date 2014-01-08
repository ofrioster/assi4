import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Topic extends messageCount implements TopicInterface{
	private String topicName;
	private ArrayList<String[]> message;

	public Topic(String subscription, String message){
			super();//TODO
			String[] t= new String[2];
			t[0]=subscription;
			t[1]=message;
			this.message.add(t);
	}
	public int howManyMessages(){
		return this.message.size();
	}
	public String getString(int index){
		StringBuilder builder = new StringBuilder();
		builder.append("destination:");
		builder.append(this.topicName);
		builder.append('\n');
		builder.append("subscription:");
		builder.append(this.message.get(index)[0]);
		builder.append('\n');
		builder.append("message-id:");
		builder.append(this.count);
		builder.append(this.message.get(index)[1]);
		return builder.toString();
	}
	public String[] getStringArray(int index){
		return this.message.get(index);
	}
}
