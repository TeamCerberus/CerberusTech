package teamcerberus.cerberustech;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import teamcerberus.cerberuscore.config.ConfigurationParser;
import teamcerberus.cerberuscore.util.ServerUtil;
import teamcerberus.cerberustech.blocks.CTBlocks;
import teamcerberus.cerberustech.items.CTItems;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(
		modid = CerberusTech.id,
		name = CerberusTech.id,
		version = CerberusTech.version,
		dependencies = "required-after:CerberusPower")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class CerberusTech {
	public final static String	id		= "CerberusTech";
	public final static String	version	= "@VERSION@";
	
	@Instance(value=id)
	public static CerberusTech instance;
	public static CreativeTabs creativeTab = CreativeTabs.tabRedstone;
	
	@PreInit
	public void preinit(FMLPreInitializationEvent e) {
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
//		ConfigurationParser.Parse(this, config);
		CTBlocks.config(config);
		CTItems.config(config);
		config.save();
	}

	@Init
	public void init(FMLInitializationEvent e) {
		CTBlocks.init();
		CTItems.init();
	}
	
	public static File getWorldFolder(){
		return new File(ServerUtil.getWorldFolder(),
				"CerberusTech");
	}
}
