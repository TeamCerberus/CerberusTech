package teamcerberus.cerberustech.client.gui;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import teamcerberus.cerberustech.CerberusTech;
import teamcerberus.cerberustech.client.network.ClientPacketHandler;
import teamcerberus.cerberustech.computer.OSKeyboardEvents;
import teamcerberus.cerberustech.computer.OSKeyboardLetters;
import teamcerberus.cerberustech.computer.TileEntityComputerCore;

public class GuiComputerCore extends GuiContainer {
	public TileEntityComputerCore computer;
	public GuiTextField textboxNetworkID;

	public GuiComputerCore(InventoryPlayer inventoryplayer,
			TileEntityComputerCore computer) {
		super(new ContainerFake());
		this.computer = computer;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		int xSize = 222;
		int ySize = 76;
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		buttonList.clear();
		buttonList.add(new GuiButton(0, j + 16, k + 27, 60, 10, "Boot"));
		buttonList
				.add(new GuiButton(1, j + 16 + 65, k + 27, 60, 10, "Shutdown"));
		buttonList.add(new GuiButton(2, j + 16 + 65 + 65, k + 27, 60, 10,
				"Terminate"));

		buttonList.add(new GuiButton(3, j + 16, k + 40, 60, 10, "Boot CD"));
		buttonList.add(new GuiButton(4, j + 16 + 65, k + 40, 60, 10, "Lock"));
		buttonList.add(new GuiButton(5, j + 16 + 65 + 65, k + 40, 60, 10,
				"Rand NetID"));
		textboxNetworkID = new GuiTextField(fontRenderer, j + 65, k + 53, 150,
				15);
		textboxNetworkID.setFocused(false);
		textboxNetworkID.setText("Computer1");
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (textboxNetworkID.isFocused()) {
			textboxNetworkID.textboxKeyTyped(par1, par2);
		} else {
			super.keyTyped(par1, par2);
		}
	}

	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);

		textboxNetworkID.mouseClicked(par1, par2, par3);

	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id == 0) {
			ClientPacketHandler.sendComputerPowerEvent(computer.xCoord,
					computer.yCoord, computer.zCoord,
					computer.worldObj.provider.dimensionId, 0);
		} else if (par1GuiButton.id == 1) {
			ClientPacketHandler.sendComputerPowerEvent(computer.xCoord,
					computer.yCoord, computer.zCoord,
					computer.worldObj.provider.dimensionId, 1);
		} else if (par1GuiButton.id == 2) {
			mc.displayGuiScreen(null);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {

		try {
			computer = (TileEntityComputerCore) computer.worldObj
					.getBlockTileEntity(computer.xCoord, computer.yCoord,
							computer.zCoord);
		} catch (Exception e) {
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		mc.func_110434_K().func_110577_a(
				new ResourceLocation(CerberusTech.id
						+ ":textures/guis/computercore.png"));

		int xSize = 222;
		int ySize = 76;
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

		this.fontRenderer.drawString("Computer Core", j + 5, k + 4, 4210752);
		this.fontRenderer.drawString("Status:", j + 5, k + 5 + 10, 4210752);
		this.fontRenderer.drawString("Off", j + 41, k + 5 + 10,
				Color.red.getRGB());
		this.fontRenderer.drawString("ID: 0", j + 130, k + 5 + 10, 4210752);
		this.fontRenderer.drawString("Network ID:", j + 5, k + 57, 4210752);
		
		textboxNetworkID.drawTextBox();
	}

	public void pixel(Tessellator var9, int par0, int par1, int par2, int par3,
			int color) {
		float var10 = (color >> 24 & 255) / 255.0F;
		float var6 = (color >> 16 & 255) / 255.0F;
		float var7 = (color >> 8 & 255) / 255.0F;
		float var8 = (color & 255) / 255.0F;
		var9.setColorRGBA_F(var6, var7, var8, var10);
		int var5;

		if (par0 < par2) {
			var5 = par0;
			par0 = par2;
			par2 = var5;
		}

		if (par1 < par3) {
			var5 = par1;
			par1 = par3;
			par3 = var5;
		}

		var9.addVertex(par0, par3, 0.0D);
		var9.addVertex(par2, par3, 0.0D);
		var9.addVertex(par2, par1, 0.0D);
		var9.addVertex(par0, par1, 0.0D);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
