package teamcerberus.cerberustech.computer;

public enum LocalDirection {
	Front(0), Back(1), Left(2), Right(3), Up(4), Down(5);
	
	public int id;
	
	private LocalDirection(int id) {
		this.id = id;
	}
	
	public static LocalDirection convertWorldSide(int worldside, int facing){
		int[] ori = new int[6];
		if(facing == 2)
			ori = new int[]{facing, 3, 4, 5, 1, 0};
		else if(facing == 3)
			ori = new int[]{facing, 2, 5, 4, 1, 0};
		else if(facing == 4)
			ori = new int[]{facing, 5, 3, 2, 1, 0};
		else if(facing == 5)
			ori = new int[]{facing, 4, 2, 3, 1, 0};
		if(worldside == ori[0])
			return Back;
		else if(worldside == ori[1])
			return Front;
		else if(worldside == ori[2])
			return Left;
		else if(worldside == ori[3])
			return Right;
		else if(worldside == ori[4])
			return Down;
		else if(worldside == ori[5])
			return Up;
		return Front;
	}
	
	
}
