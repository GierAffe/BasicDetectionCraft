package gieraffe.bdc.gui;

import gieraffe.bdc.container.ContainerDetector;
import gieraffe.bdc.lib.BlockIDs;
import gieraffe.bdc.lib.Channels;
import gieraffe.bdc.lib.PacketData;
import gieraffe.bdc.network.CustomBDCPacket;
import gieraffe.bdc.tile.TileDetector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;


public class GuiDetector extends GuiContainer {
	
	private TileDetector tileEntity;
	private GuiTextField guitextfield;
	private GuiButton powerbutton;
	
	private static final int GUI_X_SIZE = 255;
	private static final int GUI_Y_SIZE = 200;
	
	public static final int BUTTON_POWER = 1;

	
	
    public GuiDetector (InventoryPlayer par1inventoryPlayer, TileDetector par2tileEntity) 
    {
        super(new ContainerDetector(par1inventoryPlayer, par2tileEntity));

        this.tileEntity = par2tileEntity;
    }
    
    @Override
    @SuppressWarnings("unchecked")		//buttonList is a raw type
    public void initGui() {
    	/** set Gui size & initialize */
        this.xSize = GuiDetector.GUI_X_SIZE;
        this.ySize = GuiDetector.GUI_Y_SIZE;
        super.initGui();
    	
        /* input field */
        this.guitextfield = new GuiTextField(mc.fontRenderer, this.guiLeft + 17, this.guiTop + 22, 200, 43);
        this.guitextfield.setMaxStringLength(70);
        
        /* buttons */
        this.buttonList.add((Object)new GuiButton(1, this.guiLeft + 112, this.guiTop + 88, 36, 18, "Off"));
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
    	super.drawScreen(par1, par2, par3);
    	this.guitextfield.drawTextBox();
    }
    
    @Override
    public void keyTyped(char par1, int par2) {
    	this.guitextfield.textboxKeyTyped(par1, par2);
    	if (par2 != 18 || par2 == 18 && !this.guitextfield.isFocused()) {
    		super.keyTyped(par1, par2);		// disable "e" closing gui when in textfield.. :(
    	}
    }
    
    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
    	this.guitextfield.mouseClicked(par1, par2, par3);
    	super.mouseClicked(par1, par2, par3);
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
    	switch(button.id) {
        case GuiDetector.BUTTON_POWER:
        	buttonPowerClicked();
            break;
        case 2:
        }
    }
    
    public void buttonPowerClicked() {
    	int[] data = { PacketData.BUTTON_POWER_CLICKED };
    	CustomBDCPacket packet = new CustomBDCPacket(Channels.CHANNEL_DETECTOR_SERVER, BlockIDs.BLOCK_DETECTOR,
    													this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, data);
    	PacketDispatcher.sendPacketToServer(packet.getPacket());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/mods/basicdetectioncraft/gui/detector.png");
        
        // main gui
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 255, 200);
        
        //slots
        this.drawTexturedModalRect(this.guiLeft + 16, this.guiTop + 87, 237, 237, 19, 19);
        this.drawTexturedModalRect(this.guiLeft + 40, this.guiTop + 87, 237, 237, 19, 19);
        this.drawTexturedModalRect(this.guiLeft + 64, this.guiTop + 87, 237, 237, 19, 19);
        this.drawTexturedModalRect(this.guiLeft + 88, this.guiTop + 87, 237, 237, 19, 19);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	// Draw Text
    	int titleOffsetLeft = 6;
    	int titleOffsetTop = 6;
    	mc.fontRenderer.drawString(this.tileEntity.getInvName(), titleOffsetLeft, titleOffsetTop, 4210751);
    }

}