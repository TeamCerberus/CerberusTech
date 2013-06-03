package teamcerberus.cerberustech.energy.generators;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGenerator extends BlockContainer {
	
	public BlockGenerator(int id) {
		super(id, Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return GeneratorType.values()[meta].createTileEntity();
	}

}
