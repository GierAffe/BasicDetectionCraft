package gieraffe.bdc.container;

import gieraffe.bdc.tile.TileDetector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerDetector extends Container {

    protected TileDetector tileEntity;

    public ContainerDetector (InventoryPlayer inventoryPlayer, TileDetector te){
            tileEntity = te;

            bindPlayerInventory(inventoryPlayer);
            bindUpgradeSlots();
    }

    public boolean canInteractWith(EntityPlayer player) {
            return tileEntity.isUseableByPlayer(player);
    }

    protected void bindUpgradeSlots() {
    	addSlotToContainer(new Slot(tileEntity, 0, 222, 22));
    	addSlotToContainer(new Slot(tileEntity, 1, 222, 40));
    	addSlotToContainer(new Slot(tileEntity, 2, 222, 58));
    	addSlotToContainer(new Slot(tileEntity, 3, 222, 66));
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
}
