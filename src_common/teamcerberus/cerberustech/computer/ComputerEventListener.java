package teamcerberus.cerberustech.computer;

import teamcerberus.cerberustech.computer.event.ComputerEvent;

public interface ComputerEventListener {
	public void handleEvent(ComputerEvent event);
}
