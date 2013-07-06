package gieraffe.bdc.network;

import gieraffe.bdc.lib.BlockIDs;
import gieraffe.bdc.lib.Channels;
import gieraffe.bdc.lib.PacketData;
import gieraffe.bdc.tile.TileDetector;

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
			handleClientPacket(packet, player);
		else if (packet.channel == Channels.CHANNEL_DETECTOR_SERVER)
			handleServerPacket(packet);
		
	}
	
	/**
	 * Client side packet handling
	 */
	private void handleClientPacket(Packet250CustomPayload parPacket, Player parPlayer) {
		CustomBDCPacket packet = new CustomBDCPacket(parPacket);
		TileEntity tile = world.getBlockTileEntity(packet.x, packet.y, packet.z);
		
		if (packet.blockID == BlockIDs.BLOCK_DETECTOR) {
			if (packet.message[0] == PacketData.CHANGE_POWER_STATE) {
				if (packet.message[1] == PacketData.POWER_STATE_ON) {
					((TileDetector) tile).setPowerState(true);
				} else if (packet.message[1] == PacketData.POWER_STATE_OFF) {
					((TileDetector) tile).setPowerState(false);
				}
			}
		}
        
	}
	
	/**
	 * Server side packet handling
	 */
	private void handleServerPacket (Packet250CustomPayload parPacket) {
		CustomBDCPacket packet = new CustomBDCPacket(parPacket);
		TileEntity tile = world.getBlockTileEntity(packet.x, packet.y, packet.z);
		
		if (packet.blockID == BlockIDs.BLOCK_DETECTOR) {
			((TileDetector) tile).buttonClicked(packet.message);
		}
	}
}







