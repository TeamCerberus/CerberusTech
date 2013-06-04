package teamcerberus.cerberustech.energy.generators;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;
import teamcerberus.cerberuspower.core.ElectricityNetwork;
import teamcerberus.cerberuspower.core.ElectricityRegistry;
import teamcerberus.cerberuspower.prefab.TileEntityProducer;
import teamcerberus.cerberuspower.util.ElectricityDirection;

public class TileEntityGeneratorBase extends TileEntityProducer {
	
	private GeneratorType type;
	private Random rand;
	private ElectricityNetwork energyNet;
	private int internalBuffer;
	public int tick;
	
	public TileEntityGeneratorBase(GeneratorType type) {
		this.type = type;
		this.energyNet = ElectricityRegistry.getInstance().getInstanceForWorld(worldObj);
		energyNet.addTileEntity(this);
		rand = new Random();
		tick = rand.nextInt(64);
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
	
	public void generateEnergy(int amount) {
		int emitted = energyNet.produceElectricity(this, amount);
		if (amount - emitted != 0) {
			addToBuffer(amount - emitted);
		}
		if (emitted < type.getOutput()) {
			int leftovers = type.getOutput() - emitted;
			energyNet.produceElectricity(this, removeFromBuffer(leftovers));
		}
	}
	
	public int addToBuffer(int amount) {
		if (this.internalBuffer + amount <= this.type.getInternalBuffer()) {
			this.internalBuffer += amount;
			return 0;
		}
		else {
			int leftovers = this.internalBuffer + amount - type.getInternalBuffer();
			return leftovers;
		}
	}
	
	public int removeFromBuffer(int amount) {
		if (this.internalBuffer - amount >= 0) {
			this.internalBuffer -= amount;
			return amount;
		}
		else {
			int leftovers = this.internalBuffer;
			this.internalBuffer = 0;
			return leftovers;
		}
	}
	
	@Override
	public void invalidate() {
		energyNet.removeTileEntity(this);
	}

}
