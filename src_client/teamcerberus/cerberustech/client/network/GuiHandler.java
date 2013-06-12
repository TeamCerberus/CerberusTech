package teamcerberus.cerberustech.client.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import teamcerberus.cerberustech.client.gui.ContainerComputer;
import teamcerberus.cerberustech.client.gui.GuiComputer;
import teamcerberus.cerberustech.computer.TileEntityComputer;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	/*
	 * Gui Mapping:
	 * 1 - computer
	 */

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);

		if (ID == 1) { return new ContainerComputer(player.inventory,
				(TileEntityComputer) entity); }

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);

		if (ID == 1) { return new GuiComputer(player.inventory,
				(TileEntityComputer) entity); }

		return null;
	}

}
