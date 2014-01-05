package stomp;

public interface StompMessageFactory {
	StompMessage createMessage(Headers headers, ChannelBuffer channelBuffer, boolean isError);

}
