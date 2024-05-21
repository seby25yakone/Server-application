package sebastiantrasca;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Record<T> {
    private long id;
    private Type deviceType;
    private String deviceInfo;
    private T device;
    @JsonCreator
    public Record(@JsonProperty("id") long id, @JsonProperty("deviceType") Type deviceType, @JsonProperty("deviceInfo") String deviceInfo) {
        this.id = id;
        this.deviceType = deviceType;
        this.deviceInfo = deviceInfo;
        this.device = null;
    }

    public long getId() {
        return id;
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
    public void setDevice(T device){
        this.device = device;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nDevice type: " + deviceType + "\nDevice specs: " + device.toString();
    }
}
