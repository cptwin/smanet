package smanet;

/**
 * An SMA channel type.
 * 
* @author Matt
 * @author Dajne Win
 * @version 1.1 05/08/2013
 */
public enum ChannelType {

    Analog(1),
    Digital(2),
    Counter(4),
    Status(8),
    Unknown(-1);
    private byte code;

    private ChannelType(int code) {
        this.code = (byte) code;
    }

    public byte getCode() {
        return this.code;
    }
}
