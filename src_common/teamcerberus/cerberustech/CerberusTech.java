package teamcerberus.cerberustech;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.Configuration;
import teamcerberus.cerberuscore.config.ConfigurationOption;
import teamcerberus.cerberuscore.config.ConfigurationParser;
import teamcerberus.cerberuscore.config.ItemID;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = CerberusTech.id, name = CerberusTech.id,
		version = CerberusTech.version, dependencies = "required-after:CerberusCore")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class CerberusTech {
	public final static String	id		= "CerberusTech";
	public final static String	version	= "@VERSION@";
	
	
	@PreInit
	public void preinit(FMLPreInitializationEvent e){
		ConfigurationParser.Parse(this, new Configuration(e.getSuggestedConfigurationFile()));
	}
	
	@Init
	public void init(FMLInitializationEvent e){
	}
}
