package teamcerberus.cerberustech.client.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import teamcerberus.cerberustech.CerberusTech;
import teamcerberus.cerberustech.computer.OSKeyboardEvents;
import teamcerberus.cerberustech.computer.OSKeyboardLetters;
import teamcerberus.cerberustech.network.Channels;
import teamcerberus.cerberustech.network.Senders;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler {

	public static void sendPacket(Packet250CustomPayload packet,
			ByteArrayOutputStream outbytes) {
		byte[] bytes = outbytes.toByteArray();
		packet.channel = CerberusTech.network;
		packet.data = bytes;
		packet.length = packet.data.length;
		PacketDispatcher.sendPacketToServer(packet);
	}

	public static void sendComputerKeyboardEvent(int x, int y, int z, int dim,
			OSKeyboardEvents event, OSKeyboardLetters letter) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(540);
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			// writes basic packet data
			writeChannelData(dos, Channels.computer_keyboardEvent);
			writeSenderData(dos, Senders.client);

			dos.writeInt(dim);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			dos.writeInt(event.getID());
			dos.writeInt(letter.keyID);

			sendPacket(new Packet250CustomPayload(), bos);
		} catch (Exception e) {
			System.out.println("Unknown Key Error: " + event + "  " + letter);
		}
	}

	public static void sendComputerPowerEvent(int x, int y, int z, int dim,
			int event) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(540);
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			// writes basic packet data
			writeChannelData(dos, Channels.computer_powerEvent);
			writeSenderData(dos, Senders.client);

			dos.writeInt(dim);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			dos.writeInt(event);

			sendPacket(new Packet250CustomPayload(), bos);
		} catch (Exception e) {}
	}

	public static void writeChannelData(DataOutputStream dos, Channels channel)
			throws Exception {
		dos.writeInt(channel.id);
		dos.writeInt(channel.sub);
	}

	public static void writePlayerData(DataOutputStream dos, EntityPlayer player)
			throws Exception {
		dos.writeUTF(player.username);
	}

	public static void writeSenderData(DataOutputStream dos, Senders sender)
			throws Exception {
		dos.writeInt(sender.id);
	}

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player1) {
		try {
			ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
			// Channels channel = Channels.channels.get(dat.readInt() + "-" +
			// dat.readInt());
			Senders sender = Senders.senders.get(dat.readInt());
			// World world = FMLClientHandler.instance().getClient().theWorld;
			if (sender == Senders.server) {}
			dat = null;
		} catch (Exception e) {
			System.out.println(e.getCause());
			e.printStackTrace();
		}
	}
}