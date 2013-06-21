package gieraffe.bdc.gui;

import gieraffe.bdc.container.ContainerDetector;
import gieraffe.bdc.tile.TileDetector;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

public class GuiDetector extends GuiContainer {

    public GuiDetector (InventoryPlayer inventoryPlayer, TileDetector tileEntity) {
        //the container is instanciated and passed to the superclass for handling
        super(new ContainerDetector(inventoryPlayer, tileEntity));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        //draw your Gui here, only thing you need to change is the path
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/mods/basicdetectioncraft/gui/template_gui.png");
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	//draw text and stuff here
     	super.drawGuiContainerForegroundLayer(par1, par2);
    }

}