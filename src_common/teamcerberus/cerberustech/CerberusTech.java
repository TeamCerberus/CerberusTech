package teamcerberus.cerberustech;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = CerberusTech.id, name = CerberusTech.id,
		version = CerberusTech.version, dependencies = "required-after:CerberusCore")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class CerberusTech {
	public final static String	id		= "CerberusTech";
	public final static String	version	= "@VERSION@";
}
