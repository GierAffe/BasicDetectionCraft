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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
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
	
	/**
	 * getIcon() gets invoked by block.getBlockTexture
	 * blockSide is the side that gets displayed
	 * blockFacing is the block Metadata
	 */
	@Override
	@SideOnly(Side.CLIENT)
    public Icon getIcon(int blockSide, int blockFacing) {
		
		// no meta set -> front icon display
		if (blockFacing == 0 && blockSide == 3) return iconBuffer[3];
		
		// bottom side
		if (blockSide == 0)	return iconBuffer[0];
		
		// top side
		if (blockSide == 1) return iconBuffer[1];
		
		// front side
		if (blockSide == blockFacing) return iconBuffer[3];
		
		// sides
		return iconBuffer[2];
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
		int blockFacing = 0;
		int facing = MathHelper.floor_double((double) ((entityLiving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		
		if (facing == 0) { 				
			blockFacing = 2;		// south
        } else if (facing == 1) {		
			blockFacing = 5;		// west
        } else if (facing == 2) {		
        	blockFacing = 3;		// north
        } else if (facing == 3) {		
        	blockFacing = 4;		// east
        }
		
        world.setBlockMetadataWithNotify(x, y, z, blockFacing, 1);
	}
	
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6)		//analog minecraft blockHopper
    {
        TileDetector tile = (TileDetector)world.getBlockTileEntity(x, y, z);

        if (tile != null)
        {
            for (int i = 0; i < tile.getSizeInventory(); ++i)	//iterate through 
            {
                ItemStack itemstack = tile.getStackInSlot(i);

                if (itemstack != null) {
                    float f = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
                    
                    while (itemstack.stackSize > 0)
                    {
                    	/* size of stack dropped this iteration */
                        int k1 = world.rand.nextInt(21) + 10;
                        if (k1 > itemstack.stackSize) 	k1 = itemstack.stackSize;
                        itemstack.stackSize -= k1;
                        
                        EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2),
                        										new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.1F;
                        entityitem.motionX = (double)((float)world.rand.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)world.rand.nextGaussian() * f3);
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }
        }
        super.breakBlock(world, x, y, z, par5, par6);
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileDetector();
    }
}




	