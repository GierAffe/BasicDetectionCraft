package gieraffe.bdc.gui;

import gieraffe.bdc.container.ContainerDetector;
import gieraffe.bdc.tile.TileDetector;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

public class GuiDetector extends GuiContainer {
	
	private TileDetector tileEntity;

    public GuiDetector (InventoryPlayer par1inventoryPlayer, TileDetector par2tileEntity) {
        //the container is instanciated and passed to the superclass for handling
        super(new ContainerDetector(par1inventoryPlayer, par2tileEntity));
        
        this.tileEntity = par2tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/mods/basicdetectioncraft/gui/detector.png");
        
        this.xSize = 255;
        this.ySize = 200;
        
        this.guiLeft = (width - xSize) / 2;
        this.guiTop = (height - ySize) / 2;
        
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	// Draw Text
    	int titleOffsetLeft = 6;
    	int titleOffsetTop = 6;
    	mc.fontRenderer.drawString(this.tileEntity.getInvName(), titleOffsetLeft, titleOffsetTop, 4210751);
    }

}