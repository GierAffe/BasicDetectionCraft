package gieraffe.bdc.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;


public class TileDetector extends TileEntity implements IInventory {

	private ItemStack[] detectorItemStacks = new ItemStack[4];
	
	private String inventoryName;
	   
    public boolean isUseableByPlayer(EntityPlayer player) {
    	/** same as in minecraft source */
    	if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this)
    			return player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    	return false;
    }

    /** returns the number of slots in the inventory */
	public int getSizeInventory() {
		return this.detectorItemStacks.length;
	}


	/*
	 * ItemStack handling
	 */
	public ItemStack getStackInSlot(int i) {
		return this.detectorItemStacks[i];
	}

	public ItemStack decrStackSize(int i, int j) {
		if (this.detectorItemStacks[i] != null) {
            ItemStack itemstack;

            if (this.detectorItemStacks[i].stackSize <= j) {
                itemstack = this.detectorItemStacks[i];
                this.detectorItemStacks[i] = null;
                return itemstack;
            }
            else {
                itemstack = this.detectorItemStacks[i].splitStack(j);

                if (this.detectorItemStacks[i].stackSize == 0)
                    this.detectorItemStacks[i] = null;
                
                return itemstack;
            }
        }
        else
        	return null;
	}

	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.detectorItemStacks[i] != null) {
            ItemStack itemstack = this.detectorItemStacks[i];
            this.detectorItemStacks[i] = null;
            return itemstack;
        }
        else
            return null;
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.detectorItemStacks[i] = itemstack;

        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        	itemstack.stackSize = this.getInventoryStackLimit();		
	}

	/**
	 * IventoryName stuff
	 */
	public String getInvName() {
		return this.isInvNameLocalized() ? this.inventoryName : "container.detector";
	}

	public boolean isInvNameLocalized() {
		return this.inventoryName != null && this.inventoryName.length() > 0;
	}
	
	public void setInventoryName(String par1Str)
    {
        this.inventoryName = par1Str;
    }
	
	public void func_94047_a(String par1Str)
    {
        this.inventoryName = par1Str;
    }
	
	/**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*  
     */
	public int getInventoryStackLimit() {
		return 64;
	}

	public void openChest() {}

	public void closeChest() {}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	/**
	 * NBT data handling routines
	 */
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList nbtTagList = new NBTTagList();
		
		for (int i = 0; i < this.detectorItemStacks.length; i++) 
		{
			if (this.detectorItemStacks[i] != null) 
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.detectorItemStacks[i].writeToNBT(nbttagcompound1);
				nbtTagList.appendTag(nbttagcompound1);	
			}
		}
		
	    nbt.setTag("Items", nbtTagList);

	    if (this.isInvNameLocalized())
	        nbt.setString("CustomName", this.inventoryName);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList nbtTagList = nbt.getTagList("Items");
        
        this.detectorItemStacks = new ItemStack[this.getSizeInventory()];

        if (nbt.hasKey("CustomName"))
            this.inventoryName = nbt.getString("CustomName");

        for (int i = 0; i < nbtTagList.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbtTagList.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.detectorItemStacks.length) 
                this.detectorItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
        }
    }
	
	
}


















