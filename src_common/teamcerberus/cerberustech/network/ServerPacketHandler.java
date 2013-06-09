package teamcerberus.cerberustech.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import teamcerberus.cerberustech.CerberusTech;
import teamcerberus.cerberustech.computer.OSKeyboardEvents;
import teamcerberus.cerberustech.computer.OSKeyboardLetters;
import teamcerberus.cerberustech.computer.TileEntityComputer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class ServerPacketHandler implements IPacketHandler {
	
	public static void sendPacket(Packet250CustomPayload packet, ByteArrayOutputStream outbytes){
		byte[] bytes = outbytes.toByteArray();
		outbytes = null; //Flags as garbage, this will auto be cleaned out of memory;
		packet.channel = CerberusTech.network;
		packet.data = bytes;
		packet.length = packet.data.length;
		PacketDispatcher.sendPacketToAllPlayers(packet);
	}
	
	public static void sendPacketToPlayer(String user, Packet250CustomPayload packet, ByteArrayOutputStream outbytes){
		byte[] bytes = outbytes.toByteArray();
		packet.channel = CerberusTech.network;
		packet.data = bytes;
		packet.length = packet.data.length;
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		PacketDispatcher.sendPacketToPlayer(packet, (Player) server.getConfigurationManager().getPlayerForUsername(user));
	}
	
	public static void writeChannelData(DataOutputStream dos, Channels channel) throws Exception{
		dos.writeInt(channel.id);
		dos.writeInt(channel.sub);
	}
	
	public static void writePlayerData(DataOutputStream dos, EntityPlayer player) throws Exception{
		dos.writeUTF(player.username);
	}
	
	public static void writeSenderData(DataOutputStream dos, Senders sender) throws Exception{
		dos.writeInt(sender.id);
	}
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player1){
		try{
			ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
			Channels channel = Channels.channels.get(dat.readInt()+"-"+dat.readInt());
			Senders sender = Senders.senders.get(dat.readInt());
			if(sender == Senders.client){
				if(channel.equals(Channels.computer_keyboardEvent)){
					World world = MinecraftServer.getServer().getConfigurationManager().getServerInstance().worldServerForDimension(dat.readInt());
					TileEntityComputer pc = ((TileEntityComputer)world.getBlockTileEntity(dat.readInt(), dat.readInt(), dat.readInt()));
					pc.keyboardEvent(OSKeyboardEvents.getEventFromID(dat.readInt()), OSKeyboardLetters.getFromID(dat.readInt()));
				}
				if(channel.equals(Channels.computer_powerEvent)){
					World world = MinecraftServer.getServer().getConfigurationManager().getServerInstance().worldServerForDimension(dat.readInt());
					TileEntityComputer pc = ((TileEntityComputer)world.getBlockTileEntity(dat.readInt(), dat.readInt(), dat.readInt()));
					int id = dat.readInt();
					switch(id){
					case 0:
						pc.startComputer();
						break;
					case 1:
						pc.stopComputer();
						break;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}