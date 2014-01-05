package stomp;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
* A factory that creates or pools {@link ChannelBuffer}s.
*/
public interface ChannelBufferFactory {



	    /**
	* Returns a {@link ChannelBuffer} with the specified {@code capacity}.
	* This method is identical to {@code getBuffer(getDefaultOrder(), capacity)}.
	*
	* @param capacity the capacity of the returned {@link ChannelBuffer}
	* @return a {@link ChannelBuffer} with the specified {@code capacity},
	* whose {@code readerIndex} and {@code writerIndex} are {@code 0}
	*/
	    ChannelBuffer getBuffer(int capacity);

	    /**
	* Returns a {@link ChannelBuffer} with the specified {@code endianness}
	* and {@code capacity}.
	*
	* @param endianness the endianness of the returned {@link ChannelBuffer}
	* @param capacity the capacity of the returned {@link ChannelBuffer}
	* @return a {@link ChannelBuffer} with the specified {@code endianness} and
	* {@code capacity}, whose {@code readerIndex} and {@code writerIndex}
	* are {@code 0}
	*/
	    ChannelBuffer getBuffer(ByteOrder endianness, int capacity);

	    /**
	* Returns a {@link ChannelBuffer} whose content is equal to the sub-region
	* of the specified {@code array}. Depending on the factory implementation,
	* the returned buffer could wrap the {@code array} or create a new copy of
	* the {@code array}.
	* This method is identical to {@code getBuffer(getDefaultOrder(), array, offset, length)}.
	*
	* @param array the byte array
	* @param offset the offset of the byte array
	* @param length the length of the byte array
	*
	* @return a {@link ChannelBuffer} with the specified content,
	* whose {@code readerIndex} and {@code writerIndex}
	* are {@code 0} and {@code (length - offset)} respectively
	*/
	    ChannelBuffer getBuffer(byte[] array, int offset, int length);

	    /**
	* Returns a {@link ChannelBuffer} whose content is equal to the sub-region
	* of the specified {@code array}. Depending on the factory implementation,
	* the returned buffer could wrap the {@code array} or create a new copy of
	* the {@code array}.
	*
	* @param endianness the endianness of the returned {@link ChannelBuffer}
	* @param array the byte array
	* @param offset the offset of the byte array
	* @param length the length of the byte array
	*
	* @return a {@link ChannelBuffer} with the specified content,
	* whose {@code readerIndex} and {@code writerIndex}
	* are {@code 0} and {@code (length - offset)} respectively
	*/
	    ChannelBuffer getBuffer(ByteOrder endianness, byte[] array, int offset, int length);

	    /**
	* Returns a {@link ChannelBuffer} whose content is equal to the sub-region
	* of the specified {@code nioBuffer}. Depending on the factory
	* implementation, the returned buffer could wrap the {@code nioBuffer} or
	* create a new copy of the {@code nioBuffer}.
	*
	* @param nioBuffer the NIO {@link ByteBuffer}
	*
	* @return a {@link ChannelBuffer} with the specified content,
	* whose {@code readerIndex} and {@code writerIndex}
	* are {@code 0} and {@code nioBuffer.remaining()} respectively
	*/
	    ChannelBuffer getBuffer(ByteBuffer nioBuffer);

	    /**
	* Returns the default endianness of the {@link ChannelBuffer} which is
	* returned by {@link #getBuffer(int)}.
	*
	* @return the default endianness of the {@link ChannelBuffer} which is
	* returned by {@link #getBuffer(int)}
	*/
	    ByteOrder getDefaultOrder();
	}



