package teamcerberus.cerberustech;

import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import teamcerberus.cerberuscore.config.ConfigurationParser;
import teamcerberus.cerberustech.block.BlockComputer;
import teamcerberus.cerberustech.computer.ComputerType;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(
		modid = CerberusTech.id,
		name = CerberusTech.id,
		version = CerberusTech.version,
		dependencies = "required-after:CerberusCore;required-after:CerberusPower")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class CerberusTech {
	public final static String	id		= "CerberusTech";
	public final static String	version	= "@VERSION@";

	public Block[]				computerBlocks;
	public int[]				computerIds;

	@PreInit
	public void preinit(FMLPreInitializationEvent e) {
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		ConfigurationParser.Parse(this, config);

		computerBlocks = new Block[ComputerType.values().length];
		computerIds = new int[ComputerType.values().length];
		
		int computerDefaultStart = 600;
		for (int i = 0; i < ComputerType.values().length; i++) {
			ComputerType type = ComputerType.values()[i];
			config.get("Computers", type.simpleName+"-blockId", computerDefaultStart+i);
		}
		
		config.save();
	}

	@Init
	public void init(FMLInitializationEvent e) {
		for (int i = 0; i < ComputerType.values().length; i++) {
			ComputerType type = ComputerType.values()[i];
			computerBlocks[i] = new BlockComputer(computerIds[i], type);
		}

	}

}
