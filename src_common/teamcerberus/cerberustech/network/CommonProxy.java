package teamcerberus.cerberustech.network;

import net.minecraftforge.common.Configuration;
import teamcerberus.cerberustech.blocks.CTBlocks;
import teamcerberus.cerberustech.items.CTItems;

public class CommonProxy {
	public void commonSetup() {
		CTBlocks.init();
		CTItems.init();
	}

	public void clientSetup() {

	}

	public void commonConfig(Configuration config) {
		
		
		CTBlocks.config(config);
		CTItems.config(config);
	}

	public void clientConfig(Configuration config) {
		// ConfigurationParser.Parse(this, config);
	}
}
