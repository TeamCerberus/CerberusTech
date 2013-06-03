package teamcerberus.cerberustech.energy.generators;

import net.minecraft.tileentity.TileEntity;
import teamcerberus.cerberuspower.api.IElectricityProducer;
import teamcerberus.cerberuspower.prefab.TileEntityProducer;
import teamcerberus.cerberuspower.util.ElectricityDirection;

public class TileEntityCoalGenerator extends TileEntityProducer implements IElectricityProducer {
	
	private boolean initialized;
	private GeneratorType type;
	
	public TileEntityCoalGenerator() {
		this(GeneratorType.Coal);
	}
	
	public TileEntityCoalGenerator(GeneratorType type) {
		super();
		this.type = type;
	}

	@Override
	public boolean emitsEnergyTo(TileEntity paramTileEntity,
			ElectricityDirection paramDirection) {
		return true;
	}

	@Override
	public boolean isAddedToElectricityNetwork() {
		return initialized;
	}

	@Override
	public int getElectricityOutputLimit() {
		return type.getOutput();
	}

}
