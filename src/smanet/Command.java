/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smanet;

/**
 *
 * @author Dajne Win
 */
public enum Command {

    /**
     * The "net start" command, to initiate communicaiton.
     */
    NetStart((byte)6),
    /**
     * Get all available channel info.
     */
    GetChannelInfo((byte)9),
    /**
     * Synchronize channels for reading data.
     */
    SynOnline((byte)10),
    /**
     * Read data from a channel.
     */
    GetData((byte)11),
    /**
     * Set a data value on a channel.
     */
    SetData((byte)12),
    /**
     * Unknown command.
     */
    Unknown((byte)-1);
    private byte code;

    private Command(byte code) {
        this.code = code;
    }

    /**
     * Get the channel type code value.
     *     
* @return code value
     */
    public byte getCode() {
        return this.code;
    }

    /**
     * Get a SmaCommand instance from a code value.
     *     
* @param code the code value
     * @return the SmaCommand
     */
    public static Command forCode(byte code) {
        switch (code) {
            case 6:
                return NetStart;

            case 9:
                return GetChannelInfo;

            case 10:
                return SynOnline;

            case 11:
                return GetData;

            case 12:
                return SetData;

            default:
                return Unknown;
        }
    }
}
