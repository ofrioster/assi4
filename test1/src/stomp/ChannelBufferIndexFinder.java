package stomp;

public interface ChannelBufferIndexFinder {


    /**
* Returns {@code true} if and only if the data is found at the specified
* {@code guessedIndex} of the specified {@code buffer}.
* <p>
* The implementation should not perform an operation which raises an
* exception such as {@link IndexOutOfBoundsException} nor perform
* an operation which modifies the content of the buffer.
*/
    boolean find(ChannelBuffer buffer, int guessedIndex);

    /**
* Index finder which locates a {@code NUL (0x00)} byte.
*/
    ChannelBufferIndexFinder NUL = new ChannelBufferIndexFinder() {
        public boolean find(ChannelBuffer buffer, int guessedIndex) {
            return buffer.getByte(guessedIndex) == 0;
        }
    };

    /**
* Index finder which locates a non-{@code NUL (0x00)} byte.
*/
    ChannelBufferIndexFinder NOT_NUL = new ChannelBufferIndexFinder() {
        public boolean find(ChannelBuffer buffer, int guessedIndex) {
            return buffer.getByte(guessedIndex) != 0;
        }
    };

    /**
* Index finder which locates a {@code CR ('\r')} byte.
*/
    ChannelBufferIndexFinder CR = new ChannelBufferIndexFinder() {
        public boolean find(ChannelBuffer buffer, int guessedIndex) {
            return buffer.getByte(guessedIndex) == '\r';
        }
    };

    /**
* Index finder which locates a non-{@code CR ('\r')} byte.
*/
    ChannelBufferIndexFinder NOT_CR = new ChannelBufferIndexFinder() {
        public boolean find(ChannelBuffer buffer, int guessedIndex) {
            return buffer.getByte(guessedIndex) != '\r';
        }
    };

    /**
* Index finder which locates a {@code LF ('\n')} byte.
*/
    ChannelBufferIndexFinder LF = new ChannelBufferIndexFinder() {
        public boolean find(ChannelBuffer buffer, int guessedIndex) {
            return buffer.getByte(guessedIndex) == '\n';
        }
    };

    /**
* Index finder which locates a non-{@code LF ('\n')} byte.
*/
    ChannelBufferIndexFinder NOT_LF = new ChannelBufferIndexFinder() {
        public boolean find(ChannelBuffer buffer, int guessedIndex) {
            return buffer.getByte(guessedIndex) != '\n';
        }
    };

    /**
* Index finder which locates a {@code CR ('\r')} or {@code LF ('\n')}.
*/
    ChannelBufferIndexFinder CRLF = new ChannelBufferIndexFinder() {
        public boolean find(ChannelBuffer buffer, int guessedIndex) {
            byte b = buffer.getByte(guessedIndex);
            return b == '\r' || b == '\n';
        }
    };

    /**
* Index finder which locates a byte which is neither a {@code CR ('\r')}
* nor a {@code LF ('\n')}.
*/
    ChannelBufferIndexFinder NOT_CRLF = new ChannelBufferIndexFinder() {
        public boolean find(ChannelBuffer buffer, int guessedIndex) {
            byte b = buffer.getByte(guessedIndex);
            return b != '\r' && b != '\n';
        }
    };

    /**
* Index finder which locates a linear whitespace
* ({@code ' '} and {@code '\t'}).
*/
    ChannelBufferIndexFinder LINEAR_WHITESPACE = new ChannelBufferIndexFinder() {
        public boolean find(ChannelBuffer buffer, int guessedIndex) {
            byte b = buffer.getByte(guessedIndex);
            return b == ' ' || b == '\t';
        }
    };

    /**
* Index finder which locates a byte which is not a linear whitespace
* (neither {@code ' '} nor {@code '\t'}).
*/
    ChannelBufferIndexFinder NOT_LINEAR_WHITESPACE = new ChannelBufferIndexFinder() {
        public boolean find(ChannelBuffer buffer, int guessedIndex) {
            byte b = buffer.getByte(guessedIndex);
            return b != ' ' && b != '\t';
        }
    };
}
