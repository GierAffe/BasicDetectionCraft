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
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
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
        //top or bottom texture
		if (blockSide == 0 || blockSide == 1)
			return iconBuffer[blockSide];
		
		TileEntity tile = world.getBlockTileEntity(x, y, z);
        byte facing = ((TileDetector) tile).getFacing();
        
        if (blockSide == facing) return iconBuffer[3];
        
        return iconBuffer[2];
    }
	
	
	@Override
	@SideOnly(Side.CLIENT)
    public Icon getIcon(int blockSide, int blockMeta) {
		if (blockSide <= 5)
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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityLiving, ItemStack itemStack) {
		/* get block orientation */
		byte blockFacing = 0;
		int facing = MathHelper.floor_double((double) ((entityLiving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if (facing == 0) 		// south
		{
			blockFacing = 2;
        } 
		if (facing == 1) 		// west
		{
			blockFacing = 5;
        }
        if (facing == 2)		// north
        {
        	blockFacing = 3;
        }
        if (facing == 3)		// east
        {
        	blockFacing = 4;
        }
		
        //world.setBlockMetadataWithNotify(x, y, z, blockFacing.getOpposite().ordinal(),1);
        
		/* save block orientation */
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile != null & (tile instanceof TileDetector)) {
			((TileDetector) tile).setFacing(blockFacing);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileDetector();
    }
}




	