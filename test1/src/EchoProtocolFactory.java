
public class EchoProtocolFactory implements ServerProtocolFactory{
	
	
        public ServerProtocol create(){
            return new EchoProtocol();
        }
}
