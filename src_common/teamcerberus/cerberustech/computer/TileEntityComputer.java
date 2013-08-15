package teamcerberus.cerberustech.computer;

import java.lang.Thread.UncaughtExceptionHandler;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import net.minecraft.block.Block;
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
	public int[][] clientPixels;
	private Computer computer;
	private int id;
	private Thread thread;
	public boolean running;

	public TileEntityComputer() {
		clientPixels = new int[200][200];
		running = false;
		id = -1;
	}

	@Override
	public void sendPacket() {
		notifyNeighbors();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void startComputer() {
		if (running) {
			return;
		} else {
			if (id == -1) {
				id = ComputerIdGenerator.getNextID();
			}
			notifyNeighbors();
			running = true;
			computer = new Computer(id, this);
			thread = new Thread(computer);
			thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread arg0, Throwable arg1) {
				}
			});
			thread.start();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void stopComputer() {
		if (!running) {
			return;
		} else {
			try {
				running = false;
				notifyNeighbors();
				thread.interrupt();
				thread.stop();
			} catch (Exception e) {
			}
			sendPacket();
		}
	}

	public void blockDestroy() {
		stopComputer();
	}

	public void keyboardEvent(OSKeyboardEvents eventFromID,
			OSKeyboardLetters fromID) {
		if(running)
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
		NBTTagCompound com = new NBTTagCompound();
		com.setByteArray(
				"pixels",
				convertToByteArray(convertToOneDim(running ? computer
						.getMonitorPixels() : new int[200][200])));
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, com);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		NBTTagCompound com = pkt.customParam1;

		clientPixels = convertFromOneDim(
				convertFromByteArray(com.getByteArray("pixels")), 200, 200);
	}

	private static int[] convertToOneDim(int ints[][]) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int[] k : ints) {
			for (int j = 0; j < k.length; j++) {
				list.add(k[j]);
			}
		}

		int result[] = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}

	private static int[][] convertFromOneDim(int ints[], int numberOfRows,
			int rowSize) {
		int returnArray[][] = new int[numberOfRows][rowSize];
		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < rowSize; j++) {
				returnArray[i][j] = ints[i * rowSize + j];
			}
		}
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
	private ItemStack ComputerItemStacks[];

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
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
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
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false
				: par1EntityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D,
						zCoord + 0.5D) <= 64.0D;
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		return entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D,
				zCoord + 0.5D) <= 64D;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (ComputerItemStacks[par1] != null) {
			ItemStack var2 = ComputerItemStacks[par1];
			ComputerItemStacks[par1] = null;
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
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	public int getRedstoneOutput(LocalDirection side) {
		if (running)
			return computer.getRedstoneOutput(side);
		return 0;
	}

	@Override
	public void notifyNeighbors() {
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,
				worldObj.getBlockId(xCoord, yCoord, zCoord));
	}

	public int isBlockProvidingPowerOnSide(int i1, int j1, int k1, int l) {
		int i = xCoord + i1;
		int j = yCoord + j1;
		int k = zCoord + k1;
		if ((j >= 0) && (j < worldObj.getHeight())) {
			return Math
					.max(Math.max(
							worldObj.getBlockId(i, j, k) == Block.redstoneWire.blockID ? worldObj
									.getBlockMetadata(i, j, k) : 0, worldObj
									.getIndirectPowerLevelTo(i, j, k, l)),
									worldObj.isBlockProvidingPowerTo(i, j, k, l));
		}

		return 0;
	}

	@Override
	public int getSideFacing() {
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}
}
