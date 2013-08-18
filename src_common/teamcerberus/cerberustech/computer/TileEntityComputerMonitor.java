package teamcerberus.cerberustech.computer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import teamcerberus.cerberuscore.util.NetworkUtil;

public class TileEntityComputerMonitor extends TileEntity implements IPeripheral{
	public int[][] pixels;
	
	public TileEntityComputerMonitor(){
		pixels = new int[200][200];
//			netid = "monitor_"+IDGenerator.getNextID("CTPeripheralMonitor");
	}
	
	public void sendPacket() {
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,
				worldObj.getBlockId(xCoord, yCoord, zCoord));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void computerStarting(Computer computer) {
		pixels = new int[200][200];
		sendPacket();
	}

	@Override
	public void computerStoped(Computer computer) {
		pixels = new int[200][200];
		sendPacket();
	}
	
	@Override
	public void clientStarting() {
	}
	
	@Override
	public void clientStoped() {
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound com = new NBTTagCompound();
		com.setByteArray(
				"pixels",
				convertToByteArray(convertToOneDim(pixels)));
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, com);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		NBTTagCompound com = pkt.customParam1;

		pixels = convertFromOneDim(
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
	
	@Override
	public String getName() {
		return "CTMonitor";
	}

	@Override
	public int getType() {
		return 0;
	}

	@Override
	public String getDeveloper() {
		return "Team Cerberus";
	}
}
