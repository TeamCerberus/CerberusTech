package teamcerberus.cerberustech.blocks;

import teamcerberus.cerberustech.computer.ComputerType;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;

public class Blocks {
	public static Block[]	computerBlocks;
	public static int[]	computerIds;

	public static void config(Configuration config) {
		computerBlocks = new Block[ComputerType.values().length];
		computerIds = new int[ComputerType.values().length];
		
		int computerDefaultStart = 600;
		for (int i = 0; i < ComputerType.values().length; i++) {
			ComputerType type = ComputerType.values()[i];
			config.get("Computers", type.simpleName+"-blockId", computerDefaultStart+i);
		}
	}

	public static void init() {
		for (int i = 0; i < ComputerType.values().length; i++) {
			ComputerType type = ComputerType.values()[i];
			computerBlocks[i] = new BlockComputer(computerIds[i], type);
		}
	}

}
