package teamcerberus.cerberustech.computer;

import java.util.HashMap;

public enum OSKeyboardLetters {
	a(0, "a", false),
	A(1, "A", false),
	b(2, "b", false),
	B(3, "B", false),
	c(4, "c", false),
	C(5, "C", false),
	d(6, "d", false),
	D(7, "D", false),
	e(8, "e", false),
	E(9, "E", false),
	f(10, "f", false),
	F(11, "F", false),
	g(12, "g", false),
	G(13, "G", false),
	h(14, "h", false),
	H(15, "H", false),
	i(16, "i", false),
	I(17, "I", false),
	j(18, "j", false),
	J(19, "J", false),
	k(20, "k", false),
	K(21, "K", false),
	l(22, "l", false),
	L(23, "L", false),
	m(24, "m", false),
	M(25, "M", false),
	n(26, "n", false),
	N(27, "N", false),
	o(28, "o", false),
	O(29, "O", false),
	p(30, "p", false),
	P(31, "P", false),
	q(32, "q", false),
	Q(34, "Q", false),
	r(35, "r", false),
	R(36, "R", false),
	s(37, "s", false),
	S(38, "S", false),
	t(39, "t", false),
	T(40, "T", false),
	u(41, "u", false),
	U(42, "U", false),
	v(43, "v", false),
	V(44, "V", false),
	w(45, "w", false),
	W(46, "W", false),
	x(47, "x", false),
	X(48, "X", false),
	y(49, "y", false),
	Y(50, "Y", false),
	z(51, "z", false),
	Z(52, "Z", false),
	num1(53, "1", false),
	num2(54, "2", false),
	num3(55, "3", false),
	num4(56, "4", false),
	num5(57, "5", false),
	num6(58, "6", false),
	num7(59, "7", false),
	num8(60, "8", false),
	num9(61, "9", false),
	num0(62, "0", false),
	Escape(63, "ESCAPE", true),
	Minus(64, "MINUS", true),
	Equals(65, "EQUALS", true),
	Back(66, "BACK", true),
	Tab(67, "TAB", true),
	LBracket(68, "LBRACKET", true),
	RBracket(69, "RBRACKET", true),
	Return(70, "RETURN", true),
	Capital(71, "Capital", true),
	Semicolon(72, "SEMICOLON", true),
	At(73, "At", true), //SPECIAL Key, Shift On Grave! 
	Apostrophe(74, "APOSTROPHE", true),
	BackSlash(75, "BACKSLASH", true),
	Comma(76, "COMMA", true),
	Period(77, "PERIOD", true),
	Slash(78, "SLASH", true),
	LControl(79, "LCONTROL", true),
	Alt(80, "LMENU", true),
	APPS(81, "APPS", true),
	RControl(82, "RCONTROL", true),
	Up(83, "UP", true),
	Down(84, "DOWN", true),
	Left(85, "LEFT", true),
	Right(86, "RIGHT", true),
	Space(87, "SPACE", true);
	
	public int keyID;
	public String letter;
	public boolean special, cap;
	
	OSKeyboardLetters(int id, String letter, boolean special, boolean cap){
		this.keyID = id;
		this.letter = letter;
		this.special = special;
		this.cap = cap;
	}
	
	OSKeyboardLetters(int id, String letter, boolean special){
		this(id, letter, special, Character.isUpperCase(letter.charAt(0)));
	}
	
	public static HashMap<String, OSKeyboardLetters> letterMapping;
	public static HashMap<Integer, OSKeyboardLetters> idMapping;
	static{
		letterMapping = new HashMap<String, OSKeyboardLetters>();
		idMapping = new HashMap<Integer, OSKeyboardLetters>();
		for(OSKeyboardLetters c : OSKeyboardLetters.values()){
			letterMapping.put(c.letter, c);
			idMapping.put(c.keyID, c);
		}
	}
	
	public static int getIDFromLetter(String letter){
		if(!letterMapping.containsKey(letter))
			return 0;
		return letterMapping.get(letter).keyID;
	}
	
	public static OSKeyboardLetters getFromLetter(String letter){
		if(!letterMapping.containsKey(letter))
			return null;
		return letterMapping.get(letter);
	}
	
	
	public static OSKeyboardLetters getFromID(int id){
		if(!idMapping.containsKey(id))
			return null;
		return idMapping.get(id);
	}
}