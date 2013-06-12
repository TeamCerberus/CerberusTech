package teamcerberus.cerberustech.computer.environments;

import java.io.Reader;

public interface IInterpreter {
	public IInterpreter createSubInterpreter();

	public void executeFile(Reader reader) throws Exception;

	public void executeBlock(String block) throws Exception;

	public void setVariable(String key, Object value) throws Exception;

	public Object getVariable(String key) throws Exception;

	public void unsetVariable(String key) throws Exception;

	public String getFileType();
}
