public class Lexical_analyzer extends IO {
	
	public enum State { 
  
		// non-final states     ordinal number
		Start,					// 0
		Period,					// 1
		E,						// 2
		EPlusMinus,				// 3
		Underscore,				// 4
		Sharp,					// 5
		PlusMinusPeriod,		// 6

        // final states
		Id,						// 7
		Int,					// 8
		Float,					// 9
		FloatE,					// 10
		Plus,					// 11
		Minus,					// 12
		Times,					// 13
		Div,					// 14
		LParen,					// 15
		RParen,					// 16
		Lt,						// 17
		Le,						// 18
		Gt,						// 19
		Ge,						// 20
		Eq,						// 21
		False,					// 22
		True,					// 23
		Quote,					// 24
	
		//Undefined state
		UNDEF;
	
		private boolean isFinal() {
			return ( this.compareTo(State.Id) >= 0 );  
		}	
	}

	/**
	 * By enumerating the non-final states first and then the final states,
	 * the test for a final state can be done by testing if the state's ordinal number
	 * is greater than or equal to that of Id.
	 * 
	 * The following variables of "IO" class are used:
	 * Static char "c" is used to convert the variable
	 * "a" to the char type whenever necessary
	 * 
	 * Below 2D array, nextState[][] implements the state transition function State and (ASCII char set) --> State.
	 * The state argument is converted to its ordinal number used as the first array index from 0 to 24
	 * 
	 * Function driver() is the driver of the FA.
	 * If a valid token is found, assigns it to "t" and returns 1.
	 * If an invalid token is found, assigns it to "t" and returns 0.
	 * If end of the stream is reached without finding any non-whitespace character, returns -1.
	 */

	public static String t; // holds an extracted token
	public static State state; // the current state of the FA
	private static State nextState[][] = new State[25][128]; //implements the state transition func state and ASCII char set to State
	
	//Driver of FA
	private static int driver() {
		State nextSt; // the next state of the FA
		t = ""; //initialize an empty token
		state = State.Start; //start from the Start state
		
		// get the next non-whitespace character
		if ( Character.isWhitespace((char) a) )
			a = getChar(); 
		
		// end-of-stream is reached
		if ( a == -1 ) 
			return -1;

		// do the body if "a" is not end-of-stream
		while ( a != -1 ){
			c = (char) a;
			nextSt = nextState[state.ordinal()][a];
			
			// The FA will halt.
			if ( nextSt == State.UNDEF ){
				if ( state.isFinal())
					return 1; // valid token extracted
				else // "c" is an unexpected character
				{
					t = t + c;
					a = getNextChar();
					return 0; // invalid token found
				}
			}
			else // The FA will go on.
			{
				state = nextSt;
				t = t+c;
				a = getNextChar();
			}
		}

		// end-of-stream is reached while a token is being extracted
		if ( state.isFinal() )
			return 1; // valid token extracted
		else
			return 0; // invalid token found
	} 

	
	//Extract the next token using the driver of the FA. if an invalid token is found, issue an error message.
	public static void getToken() {
		
		int i = driver();
		if ( i == 0 )
			displayln(t + " : Lexical Error, invalid token");
	}

	//set the next state
	private static void setNextState() {
		
		for (int s = 0; s <= 24; s++ )
			for (int c = 0; c <= 127; c++ )
				nextState[s][c] = State.UNDEF;
	
		for (char c = 'A'; c <= 'Z'; c++)
		{
			nextState[State.Start.ordinal()][c] = State.Id;
			nextState[State.Id   .ordinal()][c] = State.Id;
		}
	
		for (char c = 'a'; c <= 'z'; c++)
		{
			nextState[State.Start.ordinal()][c] = State.Id;
			nextState[State.Id   .ordinal()][c] = State.Id;
		}
	
		for (char d = '0'; d <= '9'; d++)
		{
			nextState[State.Start     .ordinal()][d] = State.Int;
			nextState[State.Id        .ordinal()][d] = State.Id;
			nextState[State.Int       .ordinal()][d] = State.Int;
			nextState[State.Period    .ordinal()][d] = State.Float;
			nextState[State.Float     .ordinal()][d] = State.Float;
			nextState[State.E         .ordinal()][d] = State.FloatE;
			nextState[State.EPlusMinus.ordinal()][d] = State.FloatE;
			nextState[State.FloatE    .ordinal()][d] = State.FloatE;
			nextState[State.Plus   .ordinal()][d] = State.Int;
			nextState[State.Minus    .ordinal()][d] = State.Int;
	
			nextState[State.Eq    .ordinal()][d] = State.Eq;
			nextState[State.Lt    .ordinal()][d] = State.Lt;
			nextState[State.Le    .ordinal()][d] = State.Le;
			nextState[State.Gt   .ordinal()][d] = State.Gt;
			nextState[State.Ge    .ordinal()][d] = State.Ge;
			
			nextState[State.PlusMinusPeriod   .ordinal()][d] = State.Float;
		}
		
		nextState[State.Start.ordinal()]['.'] = State.Period;
		nextState[State.Start.ordinal()]['+'] = State.Plus;
		nextState[State.Start.ordinal()]['-'] = State.Minus;
		nextState[State.Start.ordinal()]['*'] = State.Times;
		nextState[State.Start.ordinal()]['/'] = State.Div;
		nextState[State.Start.ordinal()]['=']= State.Eq;
		nextState[State.Start.ordinal()]['<']= State.Lt;
		nextState[State.Lt.ordinal()]['='] = State.Le;
		nextState[State.Start.ordinal()]['\''] = State.Quote;
		//nextState[State.Start.ordinal()]['-'] = State.Id;
		nextState[State.Id.ordinal()]['-'] = State.Id;
	
		
		nextState[State.Start.ordinal()]['>']= State.Gt;
		nextState[State.Gt.ordinal()]['='] = State.Ge;
		
		nextState[State.Start.ordinal()]['('] = State.LParen;
		nextState[State.Start.ordinal()][')'] = State.RParen;
		
		nextState[State.Start.ordinal()]['.'] = State.Period;
	
		
		nextState[State.Start.ordinal()]['_'] = State.Id; //underscore + id
		nextState[State.Id   .ordinal()]['_'] = State.Id; //id + underscore
	
		nextState[State.Start.ordinal()]['#'] = State.Sharp;
		nextState[State.Sharp.ordinal()]['f'] = State.False;
		nextState[State.Sharp.ordinal()]['F'] = State.False;
		nextState[State.Sharp.ordinal()]['t'] = State.True;
		nextState[State.Sharp.ordinal()]['T'] = State.True;
	
	
		nextState[State.Int.ordinal()]['.'] = State.Period;
		nextState[State.Int.ordinal()]['.'] = State.Float;
	
			
		nextState[State.Float.ordinal()]['E'] = state.E;
		nextState[State.Float.ordinal()]['e'] = state.E;
		nextState[State.E.ordinal()]['+'] = State.EPlusMinus;
		nextState[State.E.ordinal()]['-'] = State.EPlusMinus;
		
		
		nextState[State.Plus.ordinal()]['.'] = State.PlusMinusPeriod;
		
		nextState[State.Minus.ordinal()]['.'] = State.PlusMinusPeriod;

	}

	// Sets the nextState array.
	public static void setLex()
	{
		setNextState();
	}

	
	public static void main(String argv[])
	{
		setIO( argv[0], argv[1] );
		setLex();
		String[] keywords = {"define", "and", "or", "not", "if", "cond", "else", "equal", "cons", "cdr", "car" };
		int i;
	
		while ( a != -1 ) // while "a" is not end-of-stream
		{
			i = driver(); // extract the next token
			if ( i == 1 ) {
				for (int j = 0; j < keywords.length; j++)
				{
					if (keywords[j].equals(t)) {
						displayln(t + " : Keyword_" + keywords[j]);
					} 
				}
				displayln( t + "   : " + state.toString() );
			}
				
			else if ( i == 0 )
				displayln( t+ " : Lexical Error, invalid token");
		} 
		closeIO();
	}
}
