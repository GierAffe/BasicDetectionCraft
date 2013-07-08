package gieraffe.bdc.lib;

public class PacketData {
	
	/**
	 *  Power state flags
	 */
	
	/* server message, power button click */
	public static final int BUTTON_POWER_CLICKED = 100;
	
	/* client message, change power state to ... */
	public static final int POWER_STATE_ON = 101;
	public static final int POWER_STATE_OFF = 102;
	
}
