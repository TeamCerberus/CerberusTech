package teamcerberus.cerberustech.computer;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import teamcerberus.cerberustech.CerberusTech;

public class TileEntityComputer extends TileEntity{
	public File computerFolder;
	public int id = -1;
	public int[][] monitorPixels;
	
	public TileEntityComputer(){
		monitorPixels = new int[200][200];
	}
	
	public void blockDestroy() {
		
	}
	
	@Override
	public Packet getDescriptionPacket() {
		if(id == -1 && !worldObj.isRemote){
			id = ComputerIdGenerator.getNextID();
			updateSaveFolder();
		}
			
		NBTTagCompound com = new NBTTagCompound();
		com.setInteger("computerID", id);
		com.setByteArray("pixels", convertToByteArray(convertToOneDim(monitorPixels)));
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, com);
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		NBTTagCompound com = pkt.customParam1;
		id = com.getInteger("computerID");
		monitorPixels = convertFromOneDim(convertFromByteArray(com.getByteArray("pixels")), 200, 200);
	}
	
	public void updateSaveFolder() {
		if (id == -1)
			return;
		computerFolder = new File(CerberusTech.getWorldFolder(),
				"computers/" + id);
		computerFolder.mkdirs();
	}
	
	private static int[] convertToOneDim(int ints[][]) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < ints.length; i++)
			for (int j = 0; j < ints[i].length; j++)
				list.add(ints[i][j]);
		
		int result[] = new int[list.size()];
		for(int i = 0; i < list.size(); i++)
			result[i] = list.get(i);
		return result;
	}
	
	private static int[][] convertFromOneDim(int ints[], int numberOfRows, int rowSize) {
		int returnArray[][] = new int[numberOfRows][rowSize];
		int pos = 0;
		for (int i = 0; i < numberOfRows; i++)
			for (int j = 0; j < rowSize; j++) 
				returnArray[i][j] = ints[(i*rowSize)+j];

		return returnArray;
	}
	
	private static byte[] convertToByteArray(int[] values) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(values.length * 4);        
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(values);

        return byteBuffer.array();
	}
	
	private static int[] convertFromByteArray(byte[] bytes){
        ByteBuffer buff = ByteBuffer.allocate(bytes.length);
        buff.put(bytes);
        buff.rewind();
        IntBuffer intbuff = buff.asIntBuffer();
        int[] dest = new int[bytes.length/4];
        intbuff.get(dest);
        return dest;
	}
	
}
