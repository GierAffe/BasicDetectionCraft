package gieraffe.bdc.network;

import gieraffe.bdc.lib.Channels;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (packet.data == null)
			return;
		
		if (packet.channel == Channels.CHANNEL_DETECTOR_CLIENT)
			handleClientPacket(manager, packet, player);
		else if (packet.channel == Channels.CHANNEL_DETECTOR_SERVER)
			handleServerPacket(manager, packet, player);
		
	}

	private void handleClientPacket(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
		 
        switch (data.readInt()) {
        case 0:
        	break;
        }
	}
	
	private void handleServerPacket (INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
		int x1 = data.readInt();
		int x2 = data.readInt();
		
		data = ByteStreams.newDataInput(packet.data);
		int y1 = data.readInt();
		//data.readInt()
		
        //switch (0) {
        //case 0:
        	
        //	break;
        //}
	}
}
