package teamcerberus.cerberustech.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import teamcerberus.cerberustech.CerberusTech;
import teamcerberus.cerberustech.computer.TileEntityComputerCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockComputerCore extends BlockContainer {
	public Icon[] icons;

	public BlockComputerCore(int id) {
		super(id, Material.rock);
		setUnlocalizedName("computercore");
		setCreativeTab(CerberusTech.creativeTab);
		setHardness(1.0F);
		setResistance(1.0F);
		setStepSound(soundStoneFootstep);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityComputerCore();
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
		int yaw = MathHelper
				.floor_double(par5EntityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (yaw == 0) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
		}

		if (yaw == 1) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
		}

		if (yaw == 2) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
		}

		if (yaw == 3) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {// side - meta
		return icons[side == meta ? 0 : 1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icons = new Icon[3];
		icons[0] = par1IconRegister.registerIcon(CerberusTech.id + ":"
				+ "computercore_front");
		icons[1] = par1IconRegister.registerIcon(CerberusTech.id + ":"
				+ "computercore_side");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int unused, float unused2, float unused3,
			float unused4) {
		if (player.isSneaking()) {
			return false;
		}
		if (world.isRemote) {
			return true;
		}
		TileEntity computer = world.getBlockTileEntity(x, y, z);
		if (computer != null) {
			player.openGui(CerberusTech.instance, 1, world, x, y, z);
		}
		return true;
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4,
			int par5, int par6) {
		TileEntityComputerCore computer = (TileEntityComputerCore) par1World
				.getBlockTileEntity(par2, par3, par4);
		if (computer != null) {
			computer.blockDestroy();
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		notifyNeighbors(par1World, par2, par3, par4);
	}

	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		super.onBlockAdded(par1World, par2, par3, par4);
		notifyNeighbors(par1World, par2, par3, par4);
	}

	public void notifyNeighbors(World par1World, int par2, int par3, int par4) {
		
		par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4,
				this.blockID);
		par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4,
				this.blockID);
		par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4,
				this.blockID);
		par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4,
				this.blockID);
		par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1,
				this.blockID);
		par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1,
				this.blockID);
	}

}
