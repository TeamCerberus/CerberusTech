package teamcerberus.cerberustech;

import net.minecraftforge.common.Configuration;
import teamcerberus.cerberuscore.config.ConfigurationParser;
import teamcerberus.cerberustech.blocks.Blocks;
import teamcerberus.cerberustech.items.Items;
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

	@PreInit
	public void preinit(FMLPreInitializationEvent e) {
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		ConfigurationParser.Parse(this, config);
		Blocks.config(config);
		Items.config(config);
		config.save();
	}

	@Init
	public void init(FMLInitializationEvent e) {
		Blocks.init();
		Items.init();
	}

}
