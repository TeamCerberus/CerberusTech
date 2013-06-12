package teamcerberus.cerberustech.computer.environments;

import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import teamcerberus.cerberustech.computer.Computer;

public class LuaComputerFunction {

	public static abstract class ZeroArg extends ZeroArgFunction {
		public Computer	computer;

		public ZeroArg(Computer computer) {
			this.computer = computer;
		}
	}

	public static abstract class OneArg extends OneArgFunction {
		public Computer	computer;

		public OneArg(Computer computer) {
			this.computer = computer;
		}
	}

	public static abstract class TwoArg extends TwoArgFunction {
		public Computer	computer;

		public TwoArg(Computer computer) {
			this.computer = computer;
		}
	}

	public static abstract class ThreeArg extends ThreeArgFunction {
		public Computer	computer;

		public ThreeArg(Computer computer) {
			this.computer = computer;
		}
	}
}
