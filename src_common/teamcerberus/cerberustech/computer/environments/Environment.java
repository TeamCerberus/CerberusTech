package teamcerberus.cerberustech.computer.environments;

public class Environment {
	private String			name;
	private IInterpreter	masterInterpreter;

	public Environment(String name, IInterpreter masterInterpreter) {
		this.name = name;
		this.masterInterpreter = masterInterpreter;
	}

	public String getName() {
		return name;
	}

	public String getFileType() {
		return masterInterpreter.getFileType();
	}

	public IInterpreter getMasterInterpreter() {
		return masterInterpreter;
	}

	public IInterpreter createSubInterpreter() {
		return masterInterpreter.createSubInterpreter();
	}
}
