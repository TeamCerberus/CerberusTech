package teamcerberus.cerberustech.computer;


public interface IComputerTE {
	public void sendPacket();

	public void startComputer();

	public void stopComputer();

	public void notifyNeighbors();
	
	public int isBlockProvidingPowerOnSide(int i, int j, int k, int l);
	
	public int getSideFacing();
}
