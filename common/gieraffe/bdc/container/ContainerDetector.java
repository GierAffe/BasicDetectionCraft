package gieraffe.bdc.container;

import gieraffe.bdc.tile.TileDetector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDetector extends Container {

	/** containers tile / inventory */
	TileDetector tiledetector;
	IInventory playerIInventory;

    public ContainerDetector (IInventory inventoryPlayer, TileDetector tile) {
    	this.tiledetector = tile;
    	this.playerIInventory = inventoryPlayer;
    	
        addSlotToContainer(new Slot(tiledetector, 0, 18, 89));
    	addSlotToContainer(new Slot(tiledetector, 1, 42, 89));
    	addSlotToContainer(new Slot(tiledetector, 2, 66, 89));
    	addSlotToContainer(new Slot(tiledetector, 3, 90, 89));

    	/** players toolbar inventory (1x9) */
    	for (int i = 0; i < 9; i++) {
    		addSlotToContainer(new Slot(inventoryPlayer, i, 49 + i * 18, 178));
    	}
    	/** players 'inner' inventory (3x9) */
    	for (int i = 0; i < 3; i++) {
    		for (int j = 0; j < 9; j++) {
    			addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 49 + j * 18, 120 + i * 18));
    		}
    	}
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
            return this.tiledetector.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(i);		// slot the player shift-clicked on

        if (slot != null && slot.getHasStack())			// slot exists & has a stack object
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (i < this.tiledetector.getSizeInventory())	{	// tileentity -> inventory
                if (!this.mergeItemStack(itemstack1, this.tiledetector.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, this.tiledetector.getSizeInventory(), false))	// inventory -> tileentity
                return null;

            if (itemstack1.stackSize == 0)
            	slot.putStack((ItemStack)null);
            else
                slot.onSlotChanged();
        }

        return itemstack;
    }
}
