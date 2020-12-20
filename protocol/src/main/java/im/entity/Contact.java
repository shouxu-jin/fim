package im.entity;

public class Contact {
    private int userId;
    private int contacterId;
    private String createTime;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContacterId() {
        return contacterId;
    }

    public void setContacterId(int contacterId) {
        this.contacterId = contacterId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
