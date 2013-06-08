package teamcerberus.cerberustech.computer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityComputer extends TileEntity implements IInventory,
		IComputerTE {
	public int[][]		clientPixels;
	private Computer	computer;
	private int			id;
	private Thread		thread;
	public boolean		setup;

	public TileEntityComputer() {
		setup = false;
		id = -1;
	}

	@Override
	public void sendPacket() {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void updateEntity() {
		if (!setup) {
			setup = true;
			if (worldObj.isRemote) {
				clientPixels = new int[200][200];
			} else {
				startComputer();
			}

		}
	}

	@Override
	public void startComputer() {
		if(id == -1)
			id = ComputerIdGenerator.getNextID();
		computer = new Computer(id, this);
		thread = new Thread(computer);
		thread.start();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void stopComputer() {
		try {
			thread.interrupt();
			thread.stop();
		} catch (Exception e) {}
	}

	public void blockDestroy() {
		stopComputer();
	}
	
	public void keyboardEvent(OSKeyboardEvents eventFromID,
			OSKeyboardLetters fromID) {
		computer.keyboardEvent(eventFromID, fromID);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		id = par1nbtTagCompound.getInteger("computer-id");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setInteger("computer-id", id);
	}

	@Override
	public Packet getDescriptionPacket() {
		if (setup) {
			NBTTagCompound com = new NBTTagCompound();
			com.setByteArray("pixels",
					convertToByteArray(convertToOneDim(computer
							.getMonitorPixels())));
			return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, com);
		} else return null;
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		NBTTagCompound com = pkt.customParam1;

		clientPixels = convertFromOneDim(
				convertFromByteArray(com.getByteArray("pixels")), 200, 200);
		setup = true;
	}

	private static int[] convertToOneDim(int ints[][]) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < ints.length; i++)
			for (int j = 0; j < ints[i].length; j++)
				list.add(ints[i][j]);

		int result[] = new int[list.size()];
		for (int i = 0; i < list.size(); i++)
			result[i] = list.get(i);
		return result;
	}

	private static int[][] convertFromOneDim(int ints[], int numberOfRows,
			int rowSize) {
		int returnArray[][] = new int[numberOfRows][rowSize];
		for (int i = 0; i < numberOfRows; i++)
			for (int j = 0; j < rowSize; j++)
				returnArray[i][j] = ints[(i * rowSize) + j];
		return returnArray;
	}

	private static byte[] convertToByteArray(int[] values) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(values.length * 4);
		IntBuffer intBuffer = byteBuffer.asIntBuffer();
		intBuffer.put(values);

		return byteBuffer.array();
	}

	private static int[] convertFromByteArray(byte[] bytes) {
		ByteBuffer buff = ByteBuffer.allocate(bytes.length);
		buff.put(bytes);
		buff.rewind();
		IntBuffer intbuff = buff.asIntBuffer();
		int[] dest = new int[bytes.length / 4];
		intbuff.get(dest);
		return dest;
	}

	/* ----> INV Stuff <---- */
	private ItemStack	ComputerItemStacks[];

	@Override
	public int getSizeInventory() {
		return ComputerItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return ComputerItemStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (ComputerItemStacks[i] != null) {
			if (ComputerItemStacks[i].stackSize <= j) {
				ItemStack itemstack = ComputerItemStacks[i];
				ComputerItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = ComputerItemStacks[i].splitStack(j);
			if (ComputerItemStacks[i].stackSize == 0) {
				ComputerItemStacks[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		ComputerItemStacks[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) itemstack.stackSize = getInventoryStackLimit();
	}

	@Override
	public String getInvName() {
		return "Computer";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord,
				this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(
				(double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
				(double) this.zCoord + 0.5D) <= 64.0D;
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) { return false; }
		return entityplayer.getDistanceSq((double) xCoord + 0.5D,
				(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.ComputerItemStacks[par1] != null) {
			ItemStack var2 = this.ComputerItemStacks[par1];
			this.ComputerItemStacks[par1] = null;
			return var2;
		} else {
			return null;
		}
	}

	@Override
	public boolean isInvNameLocalized() {
		return true;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
}
