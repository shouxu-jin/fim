package im.protocol;

/**
 * IM服务器返回给发送端的ack
 */
public class MessageAck {
    private boolean receiveSuccess;
    private long messageId;

    public boolean isReceiveSuccess() {
        return receiveSuccess;
    }

    public void setReceiveSuccess(boolean receiveSuccess) {
        this.receiveSuccess = receiveSuccess;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}
