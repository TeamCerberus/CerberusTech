package teamcerberus.cerberustech;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import teamcerberus.cerberuscore.util.ServerUtil;
import teamcerberus.cerberustech.client.network.ClientPacketHandler;
import teamcerberus.cerberustech.client.network.GuiHandler;
import teamcerberus.cerberustech.network.CommonProxy;
import teamcerberus.cerberustech.network.ServerPacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = CerberusTech.id, name = CerberusTech.id, version = CerberusTech.version, dependencies = "required-after:CerberusPower")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { CerberusTech.network }, packetHandler = ClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { CerberusTech.network }, packetHandler = ServerPacketHandler.class))
public class CerberusTech {
	public final static String id = "CerberusTech";
	public final static String network = "CerberusTech";
	public final static String version = "@VERSION@";

	@Instance(value = id)
	public static CerberusTech instance;
	@SidedProxy(clientSide = "teamcerberus.cerberustech.client.network.ClientProxy", serverSide = "teamcerberus.cerberustech.network.CommonProxy")
	public static CommonProxy proxy;
	public static CreativeTabs creativeTab = CreativeTabs.tabRedstone;

	@PreInit
	public void preinit(FMLPreInitializationEvent e) {
		Configuration config = new Configuration(
				e.getSuggestedConfigurationFile());
		proxy.commonConfig(config);
		proxy.clientConfig(config);
		config.save();
	}

	@Init
	public void init(FMLInitializationEvent e) {
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		proxy.commonSetup();
		proxy.clientSetup();
	}

	public static File getWorldFolder() {
		return new File(ServerUtil.getWorldFolder(), "CerberusTech");
	}
}
