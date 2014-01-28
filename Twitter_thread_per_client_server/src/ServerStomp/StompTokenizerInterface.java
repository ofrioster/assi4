package ServerStomp;
import java.io.BufferedReader;

import ServerStomp.StompFrame;


public interface StompTokenizerInterface {
	 public StompFrame getFrame(BufferedReader br);

}
