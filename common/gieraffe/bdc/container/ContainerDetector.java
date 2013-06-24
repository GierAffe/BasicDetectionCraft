package gieraffe.bdc.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDetector extends Container {

	private final IInventory inventory;

    public ContainerDetector (InventoryPlayer par1InventoryPlayer, IInventory par2Inventory) {
    	this.inventory = par2Inventory;

    	bindUpgradeSlots();
        bindPlayerInventory(par1InventoryPlayer); 
    }

    public boolean canInteractWith(EntityPlayer player) {
            return this.inventory.isUseableByPlayer(player);
    }

    protected void bindUpgradeSlots() {
    	addSlotToContainer(new Slot(inventory, 0, 222, 22));
    	addSlotToContainer(new Slot(inventory, 1, 222, 40));
    	addSlotToContainer(new Slot(inventory, 2, 222, 58));
    	addSlotToContainer(new Slot(inventory, 3, 222, 76));
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {    	
    	/** players toolbar inventory (1x9) */
    	for (int i = 0; i < 9; i++) {
    		addSlotToContainer(new Slot(inventoryPlayer, i, 49 + i * 18, 178));	// slot 1-9
    	}
    	/** players 'inner' inventory (3x9) */
    	for (int i = 0; i < 3; i++) {
    		for (int j = 0; j < 9; j++) {
    			addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 49 + j * 18, 120 + i * 18));
    		}
    	}
    }
    

    public ItemStack transferStackInSlot(EntityPlayer player, int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(i);		// slot the player shift-clicked on

        if (slot != null && slot.getHasStack())			// slot exists & has a stack object
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (i < this.inventory.getSizeInventory())	{	// 4 = number of rows
                if (!this.mergeItemStack(itemstack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, this.inventory.getSizeInventory(), false))
                return null;

            if (itemstack1.stackSize == 0)
            	slot.putStack((ItemStack)null);
            else
                slot.onSlotChanged();
        }

        return itemstack;
    }
}
