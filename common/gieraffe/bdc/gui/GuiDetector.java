package gieraffe.bdc.gui;

import gieraffe.bdc.container.ContainerDetector;
import gieraffe.bdc.tile.TileDetector;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;



public class GuiDetector extends GuiContainer {
	
	private TileDetector tileEntity;
	private GuiTextField guitextfield;

    public GuiDetector (InventoryPlayer par1inventoryPlayer, TileDetector par2tileEntity) 
    {
        super(new ContainerDetector(par1inventoryPlayer, par2tileEntity));

        this.tileEntity = par2tileEntity;
        
        this.xSize = 255;		//inventory horizontal size
        this.ySize = 200;		//inventory vertical size
        this.guiLeft = (width - xSize) / 2;		
        this.guiTop = (height - ySize) / 2;


    }
    
    public void initGui() {
        this.guiLeft = (width - xSize) / 2;		
        this.guiTop = (height - ySize) / 2;
    	
        this.guitextfield = new GuiTextField(mc.fontRenderer, this.guiLeft + 17, this.guiTop + 22, 200, 83);
        //this.guitextfield.setFocused(true);
        //this.guitextfield.setEnabled(true);
        //this.guitextfield.setVisible(true);
        this.guitextfield.setMaxStringLength(70);
        //this.guitextfield.moveCursorBy(par1)
    }

    public void drawScreen(int par1, int par2, float par3) {
    	super.drawScreen(par1, par2, par3);
    	this.guitextfield.drawTextBox();
    }
    
    public void keyTyped(char par1, int par2) {
    	this.guitextfield.textboxKeyTyped(par1, par2);
    	if (par2 != 18 || par2 == 18 && !this.guitextfield.isFocused()) {
    		super.keyTyped(par1, par2);		// disable "e" closing gui when in textfield.. :(
    	}
    }
    
    protected void mouseClicked(int par1, int par2, int par3) {
    	this.guitextfield.mouseClicked(par1, par2, par3);
    	super.mouseClicked(par1, par2, par3);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/mods/basicdetectioncraft/gui/detector.png");

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