import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;
import javax.swing.JFileChooser;


class progNd implements RobotProgramNode{
	ArrayList<stmtNd> Statements = new ArrayList<stmtNd>();
	progNd(){}
	public void set(stmtNd r){
		this.Statements.add(r);
	}
	public void execute(Robot robot) {
		for(RobotProgramNode r : Statements){
			r.execute(robot);
		}
	}
	public String toString() {
		String result = "";
		for(RobotProgramNode r : Statements){
			result = result + r.toString();
		}
		return result;
	}
}

class stmtNd implements RobotProgramNode{
	RobotProgramNode state;
	String stmt = "";
	String closed = "";
	stmtNd(RobotProgramNode stmt, String statement){
		this.state = stmt;
		this.stmt = statement;
	}
	stmtNd(){}
	stmtNd(RobotProgramNode r){
		this.state = r;
	}
	public void actNd(actNd n){
		this.state = new actNd(n.actType);
		this.stmt = n.toString();
	}
	public void loopNd(loopNd n){
		this.state = new loopNd(n.actType,n.blockNode);
		this.stmt = n.toString();
	}
	public void setClosed(String str){this.closed = str;}

	@Override
	public void execute(Robot robot) {
		this.state.execute(robot);
	}
	public String toString(){
		return stmt;
	}
}

class actNd implements RobotProgramNode{
	String actType = "";
	enum act{
		move,
		turnL,
		turnR,
		takeF,
		wait,
		//add moves here
		shieldOn,
		shieldOff
	}
	actNd() {}
	actNd(String action) {
		actType = action;
	}

	public void setActType(String action){
		actType = action;
	}
	public String toString(){
		return actType + ";\n";
	}

	@Override
	public void execute(Robot robot) {
		act action = act.valueOf(this.actType);

		switch(action){
			case move:
				robot.move();
			case turnL:
				robot.turnLeft();
			case turnR:
				robot.turnRight();
			case takeF:
				robot.takeFuel();
			case wait:
				robot.idleWait();
			case shieldOn:
				robot.setShield(true);
			case shieldOff:
				robot.setShield(false);
		}
	}
}

class loopNd implements RobotProgramNode{
	String actType = "";
	blockNd blockNode = new blockNd();
	loopNd(){}
	loopNd(String action, blockNd block) {
		actType = action;
		this.blockNode = block;
	}
	public void setActType(String action){
		actType = action;
	}
	public String toString(){
		return "loop" + this.blockNode;
	}

	@Override
	public void execute(Robot robot) {
		while(true) {
			this.blockNode.execute(robot);
		}
	}
}

class blockNd implements RobotProgramNode{
	String open = "";
	ArrayList<stmtNd> Statments = new ArrayList<>();
	String closed = "";
	blockNd(){}
	blockNd(String open, ArrayList<stmtNd> stmt, String closed) {
		this.open = open;
		this.Statments = stmt;
		this.closed = closed;
	}
	public void setOpen(String str){
		this.open = str;
	}
	public void addStmt(stmtNd stmt){
		this.Statments.add(stmt);
	}
	public void setClosed(String str){
		this.closed = str;
	}

	@Override
	public void execute(Robot robot) {
		for(stmtNd n : Statments){
			n.execute(robot);
		}
	}
	public String toString(){
		String result = open;
		for(stmtNd n : Statments){
			result = result + n.toString() + "\n";
		}
		result = result + closed;
		return result;
	}
}

/**		PART 1		*/

class IfLoopNode implements RobotProgramNode{
	String ifOpen = "";
	ConditionNode condition = new ConditionNode();
	String ifClosed = "";
	blockNd block = new blockNd();
	IfLoopNode(){

	}

	public void setOpen(String str){
		this.ifOpen = str;
	}
	public void setCondition(ConditionNode condition){
		this.condition = condition;
	}
	public void setClosed(String str){
		this.ifClosed = str;
	}
	public void setBlock(blockNd block){
		this.block = block;
	}

	@Override
	public void execute(Robot robot) {

	}

	public String toString(){
		return ifOpen+condition.toString()+ifClosed+block.toString();
	}
}

class WhileLoopNode implements RobotProgramNode{
	String actType = "";
	String whileOpen = "";
	ConditionNode condition = new ConditionNode();
	String whileClosed = "";
	blockNd block = new blockNd();

	WhileLoopNode(){

	}

	@Override
	public void execute(Robot robot) {

	}
	public String toString(){
		return actType + whileOpen + condition.toString() + whileClosed + block.toString();
	}

	public void setOpen(String s) {
		this.whileOpen = s;
	}

	public void setCondition(ConditionNode Condition) {
		this.condition = Condition;
	}
	public void setClosed(String s){
		this.whileClosed = s;
	}
	public void setBlock(blockNd Block) {
		this.block = block;
	}
}

class ConditionNode implements RobotProgramNode{
	String Operator = "";
	String open = "";
	SensorNode sensorType = new SensorNode();
	String comma = "";
	Integer number ;
	String closed = "";

	ConditionNode(){}

	ConditionNode(ConditionNode update){
		this.Operator = update.Operator;
		this.open = update.open;
		this.sensorType = update.sensorType;
		this.comma = update.comma;
		this.number = update.number;
		this.closed = update.closed;
	}

	public ConditionNode(String relop, ConditionNode update) {
		this.Operator = relop;
		this.open = update.open;
		this.sensorType = update.sensorType;
		this.comma = update.comma;
		this.number = update.number;
		this.closed = update.closed;
	}

	public void setOperator(String operator){
		this.Operator = operator;
	}
	public void setOpen(String open){
		this.open = open;
	}
	public void setSensor(SensorNode sensor){
		this.sensorType = sensor;
	}
	public void setComma(String comma){
		this.comma = comma;
	}
	public void setNumber(Integer num){
		this.number = num;
	}
	public void setClosed(String closed){
		this.closed = closed;
	}


	@Override
	public void execute(Robot robot) {

	}
	public String toString(){
		return Operator.toString()+open+sensorType.toString()+comma+number+closed;
	}

}



/*
class relopNd {
	String operatorType = "";

	enum operator {
		lt,
		gt,
		eq
	}

	public void evaluate(Robot robot) {
		operator action = relopNd.operator.valueOf(this.operatorType);
		switch (action) {
			case lt:

			case gt:

			case eq:

		}
	}
}


class NumberNode{
	Integer mainNumber;

	NumberNode(){

	}

	public void evaluate(Robot robot) {

	}
	public String toString(){
		return null;
	}
}
*/
/**		PART 2 		*/

class expNd implements RobotProgramNode{
	@Override
	public void execute(Robot robot) {

	}
}



/**
 * The parser and interpreter. The top level parse function, a main method for
 * testing, and several utility methods are provided. You need to implement
 * parseProgram and all the rest of the parser.
 */
public class Parser {
	static Pattern relop = Pattern.compile("lt|gt|eq");
	//static Pattern num = Pattern.compile("-?[0-9][0-9]0");
	static Pattern sen = Pattern.compile("fuelLeft|OpptLR|OppFb|numBarrel|barrelLR|barrelFB|wallDist");

	/**
	 * Top level parse method, called by the World
	 */
	static RobotProgramNode parseFile(File code) {
		Scanner scan = null;
		try {
			scan = new Scanner(code);

			// the only time tokens can be next to each other is
			// when one of them is one of (){},;
			scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");

			RobotProgramNode n = parseProgram(scan); // You need to implement this!!!

			scan.close();
			return n;
		} catch (FileNotFoundException e) {
			System.out.println("Robot program source file not found");
		} catch (ParserFailureException e) {
			System.out.println("Parser error:");
			System.out.println(e.getMessage());
			scan.close();
		}
		return null;
	}

	/** For testing the parser without requiring the world */

	public static void main(String[] args) {
		if (args.length > 0) {
			for (String arg : args) {
				File f = new File(arg);
				if (f.exists()) {
					System.out.println("Parsing '" + f + "'");
					RobotProgramNode prog = parseFile(f);
					System.out.println("Parsing completed ");
					if (prog != null) {
						System.out.println("================\nProgram:");
						System.out.println(prog);
					}
					System.out.println("=================");
				} else {
					System.out.println("Can't find file '" + f + "'");
				}
			}
		} else {
			while (true) {
				JFileChooser chooser = new JFileChooser(".");// System.getProperty("user.dir"));
				int res = chooser.showOpenDialog(null);
				if (res != JFileChooser.APPROVE_OPTION) {
					break;
				}
				RobotProgramNode prog = parseFile(chooser.getSelectedFile());
				System.out.println("Parsing completed");
				if (prog != null) {
					System.out.println("Program: \n" + prog);
				}
				System.out.println("=================");
			}
		}
		System.out.println("Done");
	}

	// Useful Patterns

	static Pattern NUMPAT = Pattern.compile("-?\\d+"); // ("-?(0|[1-9][0-9]*)");
	static Pattern OPENPAREN = Pattern.compile("\\(");
	static Pattern CLOSEPAREN = Pattern.compile("\\)");
	static Pattern OPENBRACE = Pattern.compile("\\{");
	static Pattern CLOSEBRACE = Pattern.compile("\\}");

	/**
	 * See assignment handout for the grammar.
	 */
	static RobotProgramNode parseProgram(Scanner s) {
		// THE PARSER GOES HERE

			// THE PARSER GOES HERE
			if (!s.hasNext()) {
				fail("Empty expr", s);
			}
		
			progNd node = new progNd();
			while (s.hasNext()) {
				node.Statements.add(parseStmt(s));
				s.next();
			}
			//if (s.hasNext()){ node.set(parseStmt(s));}
			return node;
		
	}

	// utility methods for the parser

	static stmtNd parseStmt(Scanner s) {
		if (!s.hasNext()) {
			fail("Empty expr", s);
		}
		stmtNd stmt = new stmtNd();
		if ((s.hasNext("move"))
				| (s.hasNext("turnL"))
				| (s.hasNext("turnR"))
				| (s.hasNext("takeFuel"))
				| (s.hasNext("wait"))
				| (s.hasNext("shieldOn"))
				| (s.hasNext("shieldOff"))){
			//System.out.println("Parse Act");
			stmt.actNd(parseAct(s));
		}
		if (s.hasNext("loop")) {
			//System.out.println("Parse Loop");
			stmt.loopNd(parseLoop(s));
			stmt.setClosed("");
		}
		if (s.hasNext("if")) {
			stmt = new stmtNd(parseIf(s));
		}
		if (s.hasNext("while")) {
			stmt = new stmtNd(parseWhile(s));
		}
		if(s.hasNext(";")){
			//System.out.println("; found");
			stmt.setClosed(";");
		}
		return stmt;
	}

	static actNd parseAct(Scanner s) {
		if (!s.hasNext()) {
			fail("Empty expr", s);
		}
		actNd act = new actNd("");
		if (s.hasNext("move")) {
			act.setActType("move");
		}
		if (s.hasNext("turnL")) {
			act.setActType("turnL");
		}
		if (s.hasNext("turnR")) {
			act.setActType("turnR");
		}
		if (s.hasNext("takeFuel")) {
			act.setActType("takeF");
		}
		if (s.hasNext("wait")) {
			act.setActType("wait");
		}
		return act;
	}

	static loopNd parseLoop(Scanner s) {
		if (!s.hasNext()) {
			fail("Empty expr", s);
		}
		loopNd loop = new loopNd("", null);
		if (s.hasNext("loop")) {
			loop = new loopNd("loop", parseBlock(s));
		}
		return loop;
	}

	static blockNd parseBlock(Scanner s) {
		//ArrayList<stmtNd> children = new ArrayList<stmtNd>();
		blockNd block = new blockNd();
		if (!s.hasNext()) {
			fail("Empty expr", s);
		}
		if (s.hasNext(OPENBRACE)) {
			fail("no {", s);
		}
		block.setOpen(s.next());
		while (!s.hasNext(CLOSEBRACE)) {
			block.addStmt(parseStmt(s));
		}
		if (!s.hasNext(CLOSEBRACE)) {
			fail("no }", s);
		}
		block.setClosed("}");
		return block;
	}

	static ConditionNode ParseCondition(Scanner s){
		ConditionNode condition = new ConditionNode();
		if(!s.hasNext(relop)){
			fail("no internal relop",s);
		}
		s.next();
		if(!s.hasNext(OPENPAREN)){
			fail("no opening (",s);
		}
		condition.setOpen(s.next());

		if(!s.hasNext(sen)){
			fail("no sensor",s);
		}
		condition.setSensor(new SensorNode(s.next()));

		if(!s.hasNext(",")){
			fail("no comma",s);
		}
		condition.setComma(s.next());

		if(!s.hasNext(NUMPAT)){
			fail("no number", s);
		}
		condition.setNumber(s.nextInt());

		if(!s.hasNext(CLOSEPAREN)){
			fail("no cond )", s);
		}
		condition.setClosed(s.next());
		return condition;
	}

	static IfLoopNode parseIf(Scanner s) {
		IfLoopNode node = new IfLoopNode();
		if (!s.hasNext()) {
			fail("Empty expr", s);
		}
		if(!s.hasNext("if")){
			fail("Empty if", s);
		}
		if (s.hasNext(OPENPAREN)) {
			node.setOpen("(");
		}
		if(s.hasNext(relop)){
			node.setCondition(new ConditionNode(s.next(),ParseCondition(s)));
		}else{fail("no relop",s);}
		if (s.hasNext(CLOSEPAREN)) {
			node.setClosed(")");
		}
		node.setBlock(parseBlock(s));

		return node;
	}

	static WhileLoopNode parseWhile(Scanner s) {
		WhileLoopNode whileNode = new WhileLoopNode();
		blockNd block = new blockNd();
		if (!s.hasNext()) {
			fail("Empty expr", s);
		}
		whileNode.actType = s.next();
		if (!s.hasNext(OPENPAREN)) {
			fail("no while (", s);
		}
		whileNode.setOpen(s.next());
		if(!s.hasNext(relop)){
			whileNode.setCondition(new ConditionNode(s.next(), ParseCondition(s)));
		}
		//s.next();
		if (!s.hasNext(CLOSEPAREN)) {
			fail("no while )", s);
		}
		whileNode.setClosed(s.next());
		System.out.println("block commenced");
		whileNode.setBlock(parseBlock(s));

		return whileNode;
	}


	/**
	 * Report a failure in the parser.
	 */
	static void fail(String message, Scanner s) {
		String msg = message + "\n   @ ...";
		for (int i = 0; i < 5 && s.hasNext(); i++) {
			msg += " " + s.next();
		}
		throw new ParserFailureException(msg + "...");
	}

	/**
	 * Requires that the next token matches a pattern if it matches, it consumes
	 * and returns the token, if not, it throws an exception with an error
	 * message
	 */
	static String require(String p, String message, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(message, s);
		return null;
	}

	static String require(Pattern p, String message, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(message, s);
		return null;
	}

	/**
	 * Requires that the next token matches a pattern (which should only match a
	 * number) if it matches, it consumes and returns the token as an integer if
	 * not, it throws an exception with an error message
	 */
	static int requireInt(String p, String message, Scanner s) {
		if (s.hasNext(p) && s.hasNextInt()) {
			return s.nextInt();
		}
		fail(message, s);
		return -1;
	}

	static int requireInt(Pattern p, String message, Scanner s) {
		if (s.hasNext(p) && s.hasNextInt()) {
			return s.nextInt();
		}
		fail(message, s);
		return -1;
	}

	/**
	 * Checks whether the next token in the scanner matches the specified
	 * pattern, if so, consumes the token and return true. Otherwise returns
	 * false without consuming anything.
	 */
	static boolean checkFor(String p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

	static boolean checkFor(Pattern p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

}

// You could add the node classes here, as long as they are not declared public (or private)
