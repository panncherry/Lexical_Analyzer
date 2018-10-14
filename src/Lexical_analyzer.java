
public class Lexical_analyzer extends IO{
	public enum State 
   	{ 
  // non-final states     ordinal number

	Start,             // 0
	Period,            // 1
	E,                 // 2
	EPlusMinus,        // 3
	Underscore,
	Sharp,

  // final states

	Id,                // 4
	Int,               // 5
	Float,             // 6
	FloatE,            // 7
	Plus,              // 8
	Minus,             // 9
	Times,             // 10
	Div,               // 11
	LParen,            // 12
	RParen,            // 13
	Lt,				   //14
	Le,				   //15
	Gt,				   //16
	Ge,				   //17
	Eq,			       //18
	False,
	True,
	Quote,


	UNDEF;

	private boolean isFinal()
	{
		return ( this.compareTo(State.Id) >= 0 );  
	}	
}

// By enumerating the non-final states first and then the final states,
// test for a final state can be done by testing if the state's ordinal number
// is greater than or equal to that of Id.

// The following variables of "IO" class are used:

//   static int a; the current input character
//   static char c; used to convert the variable "a" to the char type whenever necessary

public static String t; // holds an extracted token
public static State state; // the current state of the FA

private static State nextState[][] = new State[24][128];

      // This array implements the state transition function State x (ASCII char set) --> State.
      // The state argument is converted to its ordinal number used as
      // the first array index from 0 through 13. 

private static int driver()

// This is the driver of the FA. 
// If a valid token is found, assigns it to "t" and returns 1.
// If an invalid token is found, assigns it to "t" and returns 0.
// If end-of-stream is reached without finding any non-whitespace character, returns -1.

{
	State nextSt; // the next state of the FA

	t = "";
	state = State.Start;

	if ( Character.isWhitespace((char) a) )
		a = getChar(); // get the next non-whitespace character
	if ( a == -1 ) // end-of-stream is reached
		return -1;

	while ( a != -1 ) // do the body if "a" is not end-of-stream
	{
		c = (char) a;
		nextSt = nextState[state.ordinal()][a];
		if ( nextSt == State.UNDEF ) // The FA will halt.
		{
			if ( state.isFinal() )
				return 1; // valid token extracted
			else // "c" is an unexpected character
			{
				t = t+c;
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
} // end driver

public static void getToken()

// Extract the next token using the driver of the FA.
// If an invalid token is found, issue an error message.

{
	int i = driver();
	if ( i == 0 )
		displayln(t + " : Lexical Error, invalid token");
}

private static void setNextState()
{
	for (int s = 0; s <= 23; s++ )
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
		

	}
	
	nextState[State.Start.ordinal()]['.'] = State.Float;
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
	

} // end setNextState

public static void setLex()

// Sets the nextState array.

{
	setNextState();
}

public static void main(String argv[])

{		
	// argv[0]: input file containing source code using tokens defined above
	// argv[1]: output file displaying a list of the tokens 

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
				else displayln( t +"   : "+ state.toString() );

			}
			//displayln( t +"   : "+ state.toString() );
		}
			
		else if ( i == 0 )
			displayln( t+" : Lexical Error, invalid token");
	} 

	closeIO();
}
}
