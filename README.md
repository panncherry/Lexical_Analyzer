# *Lexical Analyzer*

**Lexical Analyzer** is program is to read an input text file, extract the tokens in it, and write them out one by one on separate lines. Each token should be flagged with its category. The output should be sent to an output text file. Whenever invalid tokens are found, error messages should be printed, and the reading process should continue. 

Consider the following EBNF defining 19 token categories ⟨id⟩ through ⟨period⟩: 

⟨letter⟩ → a | b | ... | z | A | B | ... | Z 
⟨digit⟩ → 0 | 1 | ... | 9 
⟨basic id⟩ → ⟨letter⟩ {⟨letter⟩ | ⟨digit⟩} 
⟨letters and digits⟩ → {⟨letter⟩ | ⟨digit⟩}+ 
⟨id⟩ → ⟨basic id⟩ { ("_" | "−") ⟨letters and digits⟩ }    // Note: "_" is the underscore char, "−" is the hyphen (i.e. minus) char. 
⟨int⟩ → [+|−] {⟨digit⟩}+ 
⟨float⟩ → [+|−] ( {⟨digit⟩}+ "." {⟨digit⟩}  |  "." {⟨digit⟩}+ ) 
⟨floatE⟩ → ⟨float⟩ (e|E) [+|−] {⟨digit⟩}+ 
⟨add⟩ → + 
⟨sub⟩ → − 
⟨mul⟩ → * 
⟨div⟩ → / 
⟨lt⟩ → "<" 
⟨le⟩ → "<=" 
⟨gt⟩ → ">" 
⟨ge⟩ → ">=" 
⟨eq⟩ → "=" 
⟨LParen⟩ → "(" 
⟨RParen⟩ → ")" 
⟨quote⟩ → " ' "    // the single quote char 
⟨false⟩ → "#f" | "#F" 
⟨true⟩ → "#t" | "#T" 
⟨period⟩ → "." 

⟨letter⟩, ⟨digit⟩, ⟨basic id⟩, ⟨letters and digits⟩ are not token categories by themselves; rather, they are auxiliary categories to assist the definitions of the tokens ⟨id⟩, ⟨int⟩, ⟨float⟩, ⟨floatE⟩. 

According to the above definitions, the integers and floating-point numbers may be signed with "+" or "−". Moreover, the integer or fractional part, but not both, of a string in ⟨float⟩ may be empty. 

The following is a DFA to accept the 19 token categories. 
<img src='https://i.imgur.com/gXTwHt3.png' title='lexical analyzer' width='' alt='Lexical Analyzer' />

The objective of this project is to implement a lexical analyzer that accepts the 19 token categories plus the following keywords, all in lowercase letters only: define, if, cond, else, and, or, not, equal, car, cdr, cons
These keywords cannot be used as identifiers, but can be parts of identifiers, like "iff" and "delse". In this and the next three projects, we assume that the identifiers and keywords are case-sensitive. The implementation should be based on the above DFA. Your lexical analyzer program should clearly separate the driver and the state-transition function so that the driver will remain invariant and only state-transition functions will change from DFA to DFA. The enumerated or integer type is suggested for representation of states. 

The following keyword recognition method is adequate for this project.
- [x] Create 11 additional DFA states for the keywords.
- [x] The DFA initially accepts the keywords as identifiers.
- [x] Each time the DFA accepts an identifier, check if it is one of the keywords, and if so, move the DFA to the corresponding state.

## License

Copyright [2018] [Pann Cherry]
