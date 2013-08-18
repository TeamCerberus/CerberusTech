package teamcerberus.cerberustech.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import teamcerberus.cerberustech.computer.ComputerType;
import teamcerberus.cerberustech.computer.TileEntityComputerCore;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;

public class CTBlocks {
	public static Block computerCore, computerMonitor;
	public static int computerCoreID, computerMonitorID;
	
	public static void config(Configuration config) {
		computerCoreID = config.get("Computers", "core", 600).getInt();
		computerMonitorID = config.get("Computers", "monitor", 601).getInt();

	}

	@SuppressWarnings("deprecation")
	public static void init() {
		computerCore = new BlockComputerCore(computerCoreID);
		computerMonitor = new BlockComputerMonitor(computerMonitorID);
		
		GameRegistry.registerBlock(computerCore);
		LanguageRegistry.addName(computerCore, "Computer Core");
		GameRegistry.registerTileEntity(TileEntityComputerCore.class,
				"ComputerTile");
	}

}
