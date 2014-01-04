
public class EchoProtocol implements ServerProtocol{
        
        public EchoProtocol() { }
        
        public String processMessage(String msg)
        {
            return msg;
        }
        
        public boolean isEnd(String msg)
        {
            return msg.equals("bye");
        }
}
