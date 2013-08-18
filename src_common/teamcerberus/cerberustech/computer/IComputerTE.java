package teamcerberus.cerberustech.computer;


public interface IComputerTE {
	public void sendPacket();

	public void startComputer();

	public void stopComputer();

	public void notifyNeighbors();
	
	public int getSideFacing();
}
