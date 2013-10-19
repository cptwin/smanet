package smanet;

import java.util.HashSet;

/**
 *
 * @author Dajne Win
 */
public class SMANet {

    private HashSet<Device> devices;
    private MainState state;

    public static void main(String[] args) {
        Device a = new Device("Test1", "abc123", "SolarPanel");
        Channel b = new Channel("channelbc1", 10, ChannelType.Digital);
        a.addChannel(b);
        HashSet<Device> test = new HashSet<>();
        test.add(a);
        for (Device d : test) {
            System.out.println("# Device: " + d.toString());
            for (Channel c : d.getChannels()) {
                System.out.println("- Channel: " + c.toString());
            }
        }
        Data d = new Data((short)1,(short)2,(byte)3,Command.GetChannelInfo,a.deviceData());
        for(byte z : d.encodePacket())
        {
            System.out.print(z + " ");
        }
    }

    public SMANet() {
        devices = new HashSet<>();
        state = MainState.RUNNING;
    }

    public int getNumberOfDevices() {
        if (state.equals(MainState.RUNNING)) {
            return devices.size();
        } else {
            return -1;
        }
    }

    public void shutdown() {
        state = MainState.SHUTDOWN;
        devices = null;
    }

    public void restart() {
        state = MainState.RESTARTING;
        devices = new HashSet<>();
        state = MainState.RUNNING;
    }
}
