package teamcerberus.cerberustech.client.network;

import teamcerberus.cerberuscore.microblock.MultiblockManager;
import teamcerberus.cerberuscore.render.CerbRenderManager;
import teamcerberus.cerberustech.network.CommonProxy;

public class ClientProxy extends CommonProxy {
	@Override
	public void clientSetup() {
		CerbRenderManager.init();
		MultiblockManager.clientInit();
	}
}
