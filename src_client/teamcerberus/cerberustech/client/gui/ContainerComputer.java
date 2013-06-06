package teamcerberus.cerberustech.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import teamcerberus.cerberustech.computer.TileEntityComputer;

public class ContainerComputer extends Container{
	public TileEntityComputer computer;
	
	public ContainerComputer(InventoryPlayer inventoryplayer, TileEntityComputer computer){
		this.computer = computer;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer){
		return computer.canInteractWith(entityplayer);
	}
    
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();
			if (slot == 0)
				if (!mergeItemStack(stackInSlot, 1, inventorySlots.size(), true))
					return null;
			else if (!mergeItemStack(stackInSlot, 0, 1, false))
				return null;
			
			if (stackInSlot.stackSize == 0)
				slotObject.putStack(null);
			else
				slotObject.onSlotChanged();
		}

		return stack;
	}

}
