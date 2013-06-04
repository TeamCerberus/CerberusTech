package teamcerberus.cerberustech.blocks;

import teamcerberus.cerberustech.computer.ComputerType;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockComputer extends BlockContainer{
	public ComputerType type;
	
	public BlockComputer(int id, ComputerType type) {
		super(id, Material.rock);
		setUnlocalizedName(type.simpleName);
		this.type = type;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return type.makeTileEntity();
	}
	
	
}
