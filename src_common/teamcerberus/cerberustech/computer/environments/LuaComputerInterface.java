package teamcerberus.cerberustech.computer.environments;

import org.luaj.vm2.LuaValue;

import teamcerberus.cerberustech.computer.Computer;

public class LuaComputerInterface {

	public static LuaValue bind(LuaValue env, Computer computeri) {
		LuaValue library = LuaValue.tableOf();
		library.set("setPixel", new LuaComputerFunction.ThreeArg(computeri) {
			@Override
			public LuaValue call(LuaValue arg1, LuaValue arg2, LuaValue arg3) {
				computer.setPixel(arg1.checknumber().toint(), arg2
						.checknumber().toint(), arg3.checknumber().toint());
				return LuaValue.NIL;
			}
		});
		library.set("updateScreen", new LuaComputerFunction.ZeroArg(computeri) {
			@Override
			public LuaValue call() {
				computer.syncMonitor();
				return LuaValue.NIL;
			}
		});
		env.set("computer", library);
		return library;
	}

}
