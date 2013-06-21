package gieraffe.bdc.block;

import gieraffe.bdc.BasicDetectionCraft;
import gieraffe.bdc.tile.TileDetector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDetector extends BlockContainer {
	
	private Icon[] iconBuffer;
	
	public BlockDetector(int id) {
        super(id, Material.iron);
        
        setHardness(2.0F);
        setResistance(10.0F);
        setStepSound(Block.soundSnowFootstep);
		setUnlocalizedName("blockDetector");
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		
		iconBuffer = new Icon[6];
	       
        // meta 0, detector
        iconBuffer[0] = par1IconRegister.registerIcon("basicdetectioncraft:detector_bottom"); // bottom
        iconBuffer[1] = par1IconRegister.registerIcon("basicdetectioncraft:detector_top"); // top
        iconBuffer[2] = par1IconRegister.registerIcon("basicdetectioncraft:detector_side"); // north
        iconBuffer[3] = par1IconRegister.registerIcon("basicdetectioncraft:detector_front"); // east
        iconBuffer[4] = par1IconRegister.registerIcon("basicdetectioncraft:detector_side"); // south
        iconBuffer[5] = par1IconRegister.registerIcon("basicdetectioncraft:detector_side"); // west

	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int blockSide)
    {
        //TileEntity te = world.getBlockTileEntity(x, y, z);
        
        //int facing = (te instanceof TileEntityBlock) ? ((int) (((TileEntityBlock)te).getFacing())) : 0;
       
        return iconBuffer[blockSide];
        		//[AdvancedMachinesClient.sideAndFacingToSpriteOffset[blockSide][facing]];
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public Icon getIcon(int blockSide, int blockMeta) {
		if (blockSide < 5)
			return iconBuffer[blockSide];
		
		return null;
    }

	 @Override
     public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are) {
             TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
             if (tileEntity == null || player.isSneaking()) {
                     return false;
             }
             
             player.openGui(BasicDetectionCraft.instance, 0, world, x, y, z);
             return true;
     }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileDetector();
    }


}
