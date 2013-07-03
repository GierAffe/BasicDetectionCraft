package gieraffe.bdc.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.network.packet.Packet250CustomPayload;

public class CreateBDCPackage {
	
	private Packet250CustomPayload packet = new Packet250CustomPayload();
	
	/**
	 * Standard package for player-server communication 
	 * @param channel
	 * @param ID button ID
	 * @param x, y, z world
	 * @param data
	 */
	public CreateBDCPackage(String channel, int ID, int x, int y, int z, int[] data) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
				
		try {			
			// set meta
			outputStream.writeInt(ID);
			outputStream.writeInt(x);
			outputStream.writeInt(y);
			outputStream.writeInt(z);
			
			for (int i = 0; i < data.length; i++) {
				outputStream.writeInt(data[i]);
			}
			
		} catch (Exception ex) {
		        ex.printStackTrace();
		}

		
		packet.channel = channel;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
	}
	
	public Packet250CustomPayload getPacket() {
		return (packet != null) ? packet : null;
	}
	
}
