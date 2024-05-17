package sebastiantrasca;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Record<T> {
    private long id;
    private Type deviceType;
    private String deviceInfo;
    @JsonCreator
    public Record(@JsonProperty("id") long id, @JsonProperty("deviceType") Type deviceType, @JsonProperty("deviceInfo") String deviceInfo) {
        this.id = id;
        this.deviceType = deviceType;
        this.deviceInfo = deviceInfo;
    }

    public long getId() {
        return id;
    }
    public String getIdAsString(){
        String idString = new String(id+"");
        return idString;
    }

    public Type getDeviceType() {
        return deviceType;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", device type=" + deviceType +
                ", device info=" + deviceInfo +
                '}';
    }
}
