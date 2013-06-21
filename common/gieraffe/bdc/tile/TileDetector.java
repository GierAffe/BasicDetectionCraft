package gieraffe.bdc.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;


public class TileDetector extends TileEntity {


    public TileDetector(){
          
    }
    
    public boolean isUseableByPlayer(EntityPlayer player) {
    	return true;
    }
    

   
}