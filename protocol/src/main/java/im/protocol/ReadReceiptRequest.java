package im.protocol;

public class ReadReceiptRequest {
    private int senderId;
    private long maxMessageId;

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public long getMaxMessageId() {
        return maxMessageId;
    }

    public void setMaxMessageId(long maxMessageId) {
        this.maxMessageId = maxMessageId;
    }
}
