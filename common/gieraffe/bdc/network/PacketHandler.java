package gieraffe.bdc.network;

import gieraffe.bdc.lib.BlockIDs;
import gieraffe.bdc.lib.Channels;
import gieraffe.bdc.tile.TileDetector;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
	
	private World world = ModLoader.getMinecraftInstance().theWorld;

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
		
		int[] values = new int[5];		// first 5 variables for identification
		for (int i = 0; i < 5; i++) {
			values[i] = data.readInt();
		}
		
		// read out the message in the data
		int[] message = new int[data.readInt()];
		for (int i = 0; i < message.length; i++) {
			message[0] = data.readInt();
		}
		
		TileEntity tile = world.getBlockTileEntity(values[1], values[2], values[3]); 	//values = x,y,z
		
		if (values[0] == BlockIDs.BLOCK_DETECTOR) {										//value[0] = tile identification
			((TileDetector) tile).buttonPowerSwitchClicked(message);
		}
	}
}







