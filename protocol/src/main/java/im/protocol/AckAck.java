package im.protocol;

/**
 * IM服务器返回给接收方的ack
 */
public class AckAck {
    private boolean receiveSuccess;

    public boolean isReceiveSuccess() {
        return receiveSuccess;
    }

    public void setReceiveSuccess(boolean receiveSuccess) {
        this.receiveSuccess = receiveSuccess;
    }
}
