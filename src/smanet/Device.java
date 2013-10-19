package smanet;

import java.util.LinkedHashSet;

/**
 *
 * @author Dajne Win
 */
public class Device {

    private LinkedHashSet<Channel> channels;
    private String deviceName, serialNumber, deviceType;

    public Device(String deviceName, String serialNumber, String deviceType) {
        this.deviceName = deviceName;
        this.serialNumber = serialNumber;
        this.deviceType = deviceType;
        this.channels = new LinkedHashSet<>();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void addChannel(Channel channel) {
        this.channels.add(channel);
    }

    public String getDeviceSerialNumber() {
        return serialNumber;
    }

    public String getDeviceType() {
        return deviceType;
    }

    @Override
    public String toString() {
        String output = "";
        output += deviceName + " " + serialNumber + " " + deviceType + "|";
        for (Channel c : channels) {
            output += c.getName() + "|";
        }
        return output;
    }

    public LinkedHashSet<Channel> getChannels() {
        return channels;
    }

    public Channel findChannelName(String name) {
        for (Channel c : channels) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }
    
    public byte[] deviceData()
    {
        byte[] dName = this.deviceName.getBytes();
        byte[] dSerial = this.serialNumber.getBytes();
        byte[] dType = this.deviceType.getBytes();
        int counter = 0;
        for(Channel c : channels)
        {            
            counter = counter + c.getData().length;
        }
        byte[] cData = new byte[counter];
        int position = 0;
        for(Channel c : channels)
        {            
            for(int i = 0; i < c.getData().length; i++)
            {
                cData[position] = c.getData()[i];
                position++;
            }
        }
        
        byte[] temp = new byte[dName.length + dSerial.length + dType.length + cData.length];
        
        System.arraycopy(dName, 0, temp, 0, dName.length);
        System.arraycopy(dSerial, 0, temp, dName.length, dSerial.length);
        System.arraycopy(dType, 0, temp, dName.length + dSerial.length, dType.length);
        System.arraycopy(cData, 0, temp, dName.length + dSerial.length + dType.length, cData.length);
        
        return temp;
    }
}
