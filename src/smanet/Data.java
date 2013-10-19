package smanet;

import java.nio.ByteBuffer;

/**
 *
 * ### SMANet Frame Start
 * # HDLC-Sync 
 * # HDLC-Addr
 * # HDLC-Ctrl 
 * # HDLC-ProdID 
 * ### SMAData Packet Start
 * # 2 source address bytes 
 * # 2 destination address bytes
 * # 1 control byte (0x40) 
 * # 1 packet counter byte 
 * # 1 command type byte 
 * ### User Data Start
 * # X user data bytes 
 * ### User Data End 
 * ### SMAData Packet End
 * # FCS 
 * # HDLC-Sync
 * ### SMANet Frame End
 *
 * @author Dajne Win
 */
public class Data {
    
    private byte[] packet;
    
    public static final byte CONTROL = (byte) 0x40;
    
    private short sourceAddress = 0;
    private short destAddress = 0;
    private byte packetCounter = 0;
    private Command cmd = null;
    private byte[] userData = null;
    
    public Data(short sourceAddress, short destAddress, byte packetCounter, Command cmd, byte[] userData)
    {
        this.sourceAddress = sourceAddress;
        this.destAddress = destAddress;
        this.packetCounter = packetCounter;
        this.cmd = cmd;
        this.userData = userData;
        encodePacket();
    }
    
    public byte[] encodePacket()
    {
        packet = new byte[7 + userData.length + 2];
        int offset = 0;
        byte[] temp = shortToByteArray(sourceAddress);
        for(int i = offset; i < (temp.length + offset); i++)
        {
            packet[i] = temp[i - offset];
        }
        
        offset = 2;
        temp = shortToByteArray(destAddress);
        for(int i = offset; i < (temp.length + offset); i++)
        {
            packet[i] = temp[i - offset];
        }
        
        offset = 4;
        packet[offset] = CONTROL;
        
        offset = 5;
        packet[offset] = packetCounter;
        
        offset = 6;
        packet[offset] = cmd.getCode();
        
        offset = 7;
        for(int i = offset; i < (userData.length + offset); i++)
        {
            packet[i] = userData[i - offset];
        }
        
        return packet;
    }
    
    private byte[] shortToByteArray(short var)
    {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(var);
        buffer.flip();
        return buffer.array();        
    }
    
}
