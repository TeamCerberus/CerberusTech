package teamcerberus.cerberustech.computer;

import teamcerberus.cerberustech.util.CTLog;

import com.google.common.base.Throwables;

public enum ComputerType {
	Basic("BasicComputer", "Basic Computer", TileEntityComputerCore.class);

	public Class<? extends TileEntityComputerCore>	class_;
	public String								name;
	public String								simpleName;

	private ComputerType(String simpleName, String name,
			Class<? extends TileEntityComputerCore> class_) {
		this.simpleName = simpleName;
		this.name = name;
		this.class_ = class_;
	}

	public Class<? extends TileEntityComputerCore> getTileEntity() {
		return class_;
	}

	public TileEntityComputerCore makeTileEntity() {
		try {
			return class_.newInstance();
		} catch (Exception e) {
			CTLog.severe("Unable to create computer tile entity. Type: " + this);
			throw Throwables.propagate(e);
		}
	}

	@Override
	public String toString() {
		return "CompuerType." + simpleName;
	}
}
