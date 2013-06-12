package teamcerberus.cerberustech.energy.generators;

public class TileEntityGeneratorSolar extends TileEntityGeneratorBase {

	public TileEntityGeneratorSolar() {
		super(GeneratorType.Solar);
	}

	@Override
	public void updateEntity() {
		if (--tick > 0) {
			tick = 64;
			return;
		}
		if (!worldObj.provider.hasNoSky
				&& !(worldObj.isRaining() || worldObj.isThundering())
				&& worldObj.isDaytime()
				&& worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord)) {
			generateEnergy(GeneratorType.Solar.getOutput());
		} else {
			generateEnergy(0);
		}
	}

}
