package teamcerberus.cerberustech.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import teamcerberus.cerberustech.computer.ComputerType;
import teamcerberus.cerberustech.computer.TileEntityComputer;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;

public class CTBlocks {
	public static Block[]	computerBlocks;
	public static int[]		computerIds;

	public static void config(Configuration config) {
		computerBlocks = new Block[ComputerType.values().length];
		computerIds = new int[ComputerType.values().length];

		int computerDefaultStart = 600;
		for (int i = 0; i < ComputerType.values().length; i++) {
			ComputerType type = ComputerType.values()[i];
			computerIds[i] = config.get("Computers",
					type.simpleName + "-blockId", computerDefaultStart + i)
					.getInt();
		}
	}

	@SuppressWarnings("deprecation")
	public static void init() {
		for (int i = 0; i < ComputerType.values().length; i++) {
			ComputerType type = ComputerType.values()[i];
			computerBlocks[i] = new BlockComputer(computerIds[i], type);
			GameRegistry.registerBlock(computerBlocks[i]);
			LanguageRegistry.addName(computerBlocks[i], type.name);
		}

		GameRegistry.registerTileEntity(TileEntityComputer.class,
				"ComputerTile");
	}

}
