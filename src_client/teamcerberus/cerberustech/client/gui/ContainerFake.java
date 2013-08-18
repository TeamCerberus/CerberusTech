package teamcerberus.cerberustech.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import teamcerberus.cerberustech.computer.TileEntityComputerCore;

public class ContainerFake extends Container {
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
}
