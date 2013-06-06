package teamcerberus.cerberustech.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import teamcerberus.cerberustech.CerberusTech;
import teamcerberus.cerberustech.computer.ComputerType;
import teamcerberus.cerberustech.computer.TileEntityComputer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockComputer extends BlockContainer {
	public ComputerType	type;
	public Icon[]		icons;

	public BlockComputer(int id, ComputerType type) {
		super(id, Material.rock);
		setUnlocalizedName(type.simpleName);
		this.type = type;
		this.setCreativeTab(CerberusTech.creativeTab);
		this.setHardness(1.0F);
		this.setResistance(1.0F);
		this.setStepSound(soundStoneFootstep);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return type.makeTileEntity();
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLiving par5EntityLiving, ItemStack par6ItemStack) {
		int yaw = MathHelper
				.floor_double((double) (par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (yaw == 0) // zneg
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);

		if (yaw == 1) // xpos
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);

		if (yaw == 2) // zpos
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);

		if (yaw == 3) // xneg
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {//side - meta
		return icons[side == meta ? 0 : (side == 0 || side == 1) ? 2 : 1];
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icons = new Icon[3];
		icons[0] = par1IconRegister.registerIcon(CerberusTech.id+":"+type.simpleName+"_front");
		icons[1] = par1IconRegister.registerIcon(CerberusTech.id+":"+type.simpleName+"_side");
		icons[2] = par1IconRegister.registerIcon(CerberusTech.id+":"+type.simpleName+"_other");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int unused, float unused2, float unused3, float unused4) {
		if (player.isSneaking()) 
			return false;
		if(world.isRemote)
			return true;
		TileEntity computer = world.getBlockTileEntity(x, y, z);
		if(computer != null){
			player.openGui(CerberusTech.instance, 1, world, x, y, z);
		}
		return true;
	}
	
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		TileEntityComputer computer = ((TileEntityComputer)par1World.getBlockTileEntity(par2, par3, par4));
		if(computer != null)
			computer.blockDestroy();
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
}
