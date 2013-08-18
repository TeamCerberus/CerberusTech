package teamcerberus.cerberustech.computer;


/**
 * 
 * 
 * 0 - monitor
 * 
 * 
 * @author Chandler
 */
public interface IPeripheral {
	
	public String getName();
	public int getType();
	public String getDeveloper();
	
	public void computerStarting(Computer computer);
	public void computerStoped(Computer computer);
	public void clientStarting();
	public void clientStoped();
}
