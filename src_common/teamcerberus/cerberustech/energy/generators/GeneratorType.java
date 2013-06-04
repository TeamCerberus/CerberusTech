package teamcerberus.cerberustech.energy.generators;

import net.minecraft.tileentity.TileEntity;

public enum GeneratorType {
	Coal("cerberusGeneratorCoal", "Coal Generator", TileEntityCoalGenerator.class, 100);
	
	private String unlocalizedName;
	private String localizedName;
	private int output;
	private Class<? extends TileEntityGeneratorBase> claSS;
	
	private GeneratorType(String unlocalizaedName, String localizedName, Class<? extends TileEntityGeneratorBase> claSS, int output) {
		this.unlocalizedName = unlocalizaedName;
		this.localizedName = localizedName;
		this.output = output;
	}
	
	public TileEntity createTileEntity() {
		try {
			return claSS.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public String getLocalizedName() {
		return localizedName;
	}

	public int getOutput() {
		return output;
	}

}
