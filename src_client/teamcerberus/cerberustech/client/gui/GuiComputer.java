package teamcerberus.cerberustech.client.gui;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import teamcerberus.cerberustech.computer.TileEntityComputer;

public class GuiComputer extends GuiContainer {
	public TileEntityComputer computer;

	public GuiComputer(InventoryPlayer inventoryplayer,
			TileEntityComputer computer) {
		super(new ContainerComputer(inventoryplayer, computer));
		this.computer = computer;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		int xSize = 222;
		int ySize = 222;
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		
		buttonList.clear();
		buttonList.add(new GuiButton(0, j-102, k+10, 100, 20, "Power On"));
		buttonList.add(new GuiButton(1, j-102, k+10+20+5, 100, 20, "Power Off"));
		buttonList.add(new GuiButton(2, j-102, k+10+20+5+20+5, 100, 20, "Close GUI"));
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
//		if(par1GuiButton.id == 0)
//			ClientPacketHandler.sendComputerPowerEvent(computer.xCoord, computer.yCoord, computer.zCoord, computer.worldObj.provider.dimensionId, 0);
//		else if(par1GuiButton.id == 1)
//			ClientPacketHandler.sendComputerPowerEvent(computer.xCoord, computer.yCoord, computer.zCoord, computer.worldObj.provider.dimensionId, 1);
//		else if(par1GuiButton.id == 2)
//			mc.displayGuiScreen(null);
	}
	
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		
		try{
			computer = (TileEntityComputer) computer.worldObj.getBlockTileEntity(computer.xCoord, computer.yCoord, computer.zCoord);
		}catch(Exception e){
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/mods/CerberusTech/textures/guis/monitor.png");

		int xSize = 222;
		int ySize = 222;
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

		Tessellator var9 = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		var9.startDrawingQuads();
		int colorMissing = Color.black.getRGB();
		for (int x = 0; x < 200; x++) {
			for (int y = 0; y < 200; y++) {
				int color = computer.clientPixels[x][y];
				if (color == 0)
					color = colorMissing;
				pixel(var9, j + x + 11, k + y + 11, j + x + 1 + 11, k + y + 1 + 11, color);

			}
		}
		var9.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		
//		while (Keyboard.next()){
//			int key = Keyboard.getEventKey();
//			if(key != 0){
//				boolean release = Keyboard.getEventKeyState();
//				char character = Keyboard.getEventCharacter();
//				boolean special = !Character.isLetterOrDigit(Character.toLowerCase(character));
//				String name = special ? Keyboard.getKeyName(key) : Character.toString(character);
//				if(name.equals("GRAVE") && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) )
//					name = OSKeyboardLetters.At.letter; //Allows for access to the @ key as it was blocked because of it also being grave!
////				System.out.println("KEYNAME: "+name);
//				if(name.equals("GRAVE") || computer == null){
//					if(!release)
//						Mouse.setGrabbed(!Mouse.isGrabbed());
//				}else if(Mouse.isGrabbed())
//					ClientPacketHandler.sendComputerKeyboardEvent(computer.xCoord, computer.yCoord, computer.zCoord, computer.worldObj.provider.dimensionId, release ? OSKeyboardEvents.KeyPushed : OSKeyboardEvents.KeyReleased, OSKeyboardLetters.getFromLetter(name));
//				else if(!Mouse.isGrabbed() && name.equals("ESCAPE"))
//					mc.displayGuiScreen(null);
//			}
//		}
		
		((GuiButton)buttonList.get(0)).enabled = !Mouse.isGrabbed();
		((GuiButton)buttonList.get(1)).enabled = !Mouse.isGrabbed();
		((GuiButton)buttonList.get(2)).enabled = !Mouse.isGrabbed();
		
//		if(Mouse.isGrabbed()){
//			while(Mouse.next()){
//				System.out.println("Mouse: "+Mouse.getEventButtonState()+" "+Mouse.getEventButton()+" "+Mouse.getDX()+" "+Mouse.getDY()+" "+Mouse.getDWheel());
//				OSMouseEvents event = Mouse.getEventButton() <= 0 ? OSMouseEvents.Button : OSMouseEvents.Move;
//			}
//		}
	}
	
	@Override
	public void onGuiClosed() {
		Mouse.setGrabbed(false);
	}

	public void pixel(Tessellator var9, int par0, int par1, int par2, int par3,
			int color) {
		float var10 = (float) (color >> 24 & 255) / 255.0F;
		float var6 = (float) (color >> 16 & 255) / 255.0F;
		float var7 = (float) (color >> 8 & 255) / 255.0F;
		float var8 = (float) (color & 255) / 255.0F;
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

		var9.addVertex((double) par0, (double) par3, 0.0D);
		var9.addVertex((double) par2, (double) par3, 0.0D);
		var9.addVertex((double) par2, (double) par1, 0.0D);
		var9.addVertex((double) par0, (double) par1, 0.0D);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
