package teamcerberus.cerberustech.energy.generators;

import net.minecraft.tileentity.TileEntity;
import teamcerberus.cerberuspower.prefab.TileEntityProducer;
import teamcerberus.cerberuspower.util.ElectricityDirection;

public class TileEntityGeneratorBase extends TileEntityProducer {
	
	private GeneratorType type;
	
	public TileEntityGeneratorBase(GeneratorType type) {
		this.type = type;
	}
	
	@Override
	public boolean emitsEnergyTo(TileEntity paramTileEntity,
			ElectricityDirection paramDirection) {
		return true;
	}

	@Override
	public int getElectricityOutputLimit() {
		return type.getOutput();
	}

}
