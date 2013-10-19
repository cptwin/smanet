package smanet;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 *
 * @author Dajne Win
 * @version 1.0 05/08/2013
 */
public final class Channel {

    private String name;
    private int maxAge;
    private long channelTimestamp;
    private ArrayList<String> channelStatus;
    private ChannelType type;
    private byte typeCode;
    private Data data;

    public Channel(String name, int maxAge, ChannelType type) {
        this.name = name;
        setMaxAge(maxAge);
        this.type = type;
        this.typeCode = type.getCode();
        this.channelStatus = new ArrayList<>();
        updateTimestamp();
    }

    public String getName() {
        return name;
    }

    /**
     * Gets the type of channel.
     *
     * @return ChannelType
     */
    public ChannelType getChannelType() {
        return type;
    }

    /**
     * Sets the maximum age a channel can be before it needs to perform an
     * update.
     *
     * @version 1.0 05/08/2013
     * @param maxAge
     */
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge * 1000; //age given in seconds, converted to milliseconds
    }

    /**
     * Gets the number of statuses that are stored with this channel.
     *
     * @version 1.0 05/08/2013
     * @return channelStatus.size
     */
    public int getNumChannelStatus() {
        return channelStatus.size();
    }

    /**
     * Gets a specific channel status.
     *
     * @version 1.0 05/08/2013
     * @param index
     * @return String channelStatus
     */
    public String getChannelStatus(int index) {
        if (index < channelStatus.size() || index > -1) {
            return channelStatus.get(index);
        }
        return null;
    }

    public byte[] getData() {
        if (channelTimestamp - (maxAge) > maxAge) {
            //update channel data
            updateTimestamp();
        }
        byte[] cName = name.getBytes();
        byte[] cMaxAge = ByteBuffer.allocate(4).putInt(maxAge).array();
        byte[] cCurrentAge = ByteBuffer.allocate(8).putLong(channelTimestamp).array();
        byte[] cType = new byte[1];
        cType[0] = typeCode;
        
        byte[] output = new byte[cName.length + cMaxAge.length + cCurrentAge.length + cType.length];
        System.arraycopy(cName, 0, output, 0, cName.length);
        System.arraycopy(cMaxAge, 0, output, cName.length, cMaxAge.length);
        System.arraycopy(cCurrentAge, 0, output, cName.length + cMaxAge.length, cCurrentAge.length);
        System.arraycopy(cType, 0, output, cName.length + cMaxAge.length + cCurrentAge.length, cType.length);
        
        return output;
    }

    public long getTimestamp() {
        return channelTimestamp;
    }

    public boolean setChannelAge(int age) {
        if (age > 0) {
            this.maxAge = age;
            return true;
        } else {
            return false;
        }
    }

    private void updateTimestamp() {
        channelTimestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        String output = "SmaChannel{name=" + name + ",maxAge=" + maxAge + ",currentAge=" + channelTimestamp + ",type=" + type;
        for (String s : channelStatus) {
            output += ",status=" + s;
        }
        output += '}';
        return output;
    }
}
