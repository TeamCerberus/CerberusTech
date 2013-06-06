package teamcerberus.cerberustech.computer;

import teamcerberus.cerberustech.util.CTLog;

import com.google.common.base.Throwables;

public enum ComputerType {
	Basic("BasicComputer", "Basic Computer", TileEntityComputer.class);
	
	public Class<? extends TileEntityComputer> class_;
	public String name;
	public String simpleName;

	private ComputerType(String simpleName, String name,
			Class<? extends TileEntityComputer> class_) {
		this.simpleName = simpleName;
		this.name = name;
		this.class_ = class_;
	}
	
	public Class<? extends TileEntityComputer> getTileEntity(){
		return class_;
	}
	
	public TileEntityComputer makeTileEntity() {
		try {
			return class_.newInstance();
		} catch (Exception e) {
			CTLog.severe("Unable to create computer tile entity. Type: "+this);
			throw Throwables.propagate(e);
		}
	}

	public String toString() {
		return "CompuerType." +simpleName;
	}
}
