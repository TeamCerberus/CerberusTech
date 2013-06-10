package teamcerberus.cerberustech.computer.environments;

import luaj.OneArgFunction;
import luaj.ThreeArgFunction;
import luaj.TwoArgFunction;
import luaj.ZeroArgFunction;
import teamcerberus.cerberustech.computer.Computer;

public class LuaComputerFunction {

	public static abstract class ZeroArg extends ZeroArgFunction{
		public Computer computer;
		
		public ZeroArg(Computer computer) {
			this.computer = computer;
		}
	}
	
	public static abstract class OneArg extends OneArgFunction{
		public Computer computer;
		
		public OneArg(Computer computer) {
			this.computer = computer;
		}
	}
	
	public static abstract class TwoArg extends TwoArgFunction{
		public Computer computer;
		
		public TwoArg(Computer computer) {
			this.computer = computer;
		}
	}
	
	public static abstract class ThreeArg extends ThreeArgFunction{
		public Computer computer;
		
		public ThreeArg(Computer computer) {
			this.computer = computer;
		}
	}
}
