package gieraffe.bdc;

import gieraffe.bdc.block.BlockDetector;
import gieraffe.bdc.gui.GuiHandler;
import gieraffe.bdc.lib.BlockIDs;
import gieraffe.bdc.lib.Channels;
import gieraffe.bdc.lib.Reference;
import gieraffe.bdc.network.PacketHandler;
import gieraffe.bdc.tile.TileDetector;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;



@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {Channels.CHANNEL_DETECTOR_CLIENT, Channels.CHANNEL_DETECTOR_SERVER}, 
			packetHandler = PacketHandler.class)
public class BasicDetectionCraft {
	
	/* 
	 * Register blocks & Items 
	 */
	public final static Block blockDetector = new BlockDetector(BlockIDs.BLOCK_DETECTOR);
	
	
    @Instance(Reference.MOD_ID)
    public static BasicDetectionCraft instance;
   
    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide="gieraffe.bdc.client.ClientProxy", serverSide="gieraffe.bdc.CommonProxy")
    public static CommonProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		// Yeah !
	}
	
	@Init
	public void init(FMLInitializationEvent event) {
		/*
		 * Register blocks to forge
		 */
		LanguageRegistry.addName(blockDetector, "Detector");
        MinecraftForge.setBlockHarvestLevel(blockDetector, "pickaxe", 3);
        GameRegistry.registerBlock(blockDetector, "Detector");
        GameRegistry.registerTileEntity(TileDetector.class, "containerDetector");
        
        
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		// Yeah !
	}
}
