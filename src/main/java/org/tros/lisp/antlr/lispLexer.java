// Generated from lisp.g4 by ANTLR 4.5.3

package org.tros.lisp.antlr;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class lispLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		STRING=1, WHITESPACE=2, NUMBER=3, SYMBOL=4, LPAREN=5, LITERALSTART=6, 
		RPAREN=7, DOT=8;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"STRING", "WHITESPACE", "NUMBER", "SYMBOL", "LPAREN", "LITERALSTART", 
		"RPAREN", "DOT", "SYMBOL_START", "DIGIT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, "'('", "''('", "')'", "'.'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "STRING", "WHITESPACE", "NUMBER", "SYMBOL", "LPAREN", "LITERALSTART", 
		"RPAREN", "DOT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public lispLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "lisp.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\nO\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\3\2\3\2\3\2\3\2\7\2\34\n\2\f\2\16\2\37\13\2\3\2\3\2\3\3\6\3$\n\3\r"+
		"\3\16\3%\3\3\3\3\3\4\5\4+\n\4\3\4\6\4.\n\4\r\4\16\4/\3\4\3\4\6\4\64\n"+
		"\4\r\4\16\4\65\5\48\n\4\3\5\3\5\3\5\7\5=\n\5\f\5\16\5@\13\5\3\6\3\6\3"+
		"\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\5\nL\n\n\3\13\3\13\2\2\f\3\3\5\4\7\5\t"+
		"\6\13\7\r\b\17\t\21\n\23\2\25\2\3\2\6\4\2$$^^\5\2\13\f\17\17\"\"\4\2-"+
		"-//\6\2,-/\61C\\c|U\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"+
		"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\3\27\3\2\2\2\5#\3\2"+
		"\2\2\7*\3\2\2\2\t9\3\2\2\2\13A\3\2\2\2\rC\3\2\2\2\17F\3\2\2\2\21H\3\2"+
		"\2\2\23K\3\2\2\2\25M\3\2\2\2\27\35\7$\2\2\30\31\7^\2\2\31\34\13\2\2\2"+
		"\32\34\n\2\2\2\33\30\3\2\2\2\33\32\3\2\2\2\34\37\3\2\2\2\35\33\3\2\2\2"+
		"\35\36\3\2\2\2\36 \3\2\2\2\37\35\3\2\2\2 !\7$\2\2!\4\3\2\2\2\"$\t\3\2"+
		"\2#\"\3\2\2\2$%\3\2\2\2%#\3\2\2\2%&\3\2\2\2&\'\3\2\2\2\'(\b\3\2\2(\6\3"+
		"\2\2\2)+\t\4\2\2*)\3\2\2\2*+\3\2\2\2+-\3\2\2\2,.\5\25\13\2-,\3\2\2\2."+
		"/\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\67\3\2\2\2\61\63\7\60\2\2\62\64\5\25"+
		"\13\2\63\62\3\2\2\2\64\65\3\2\2\2\65\63\3\2\2\2\65\66\3\2\2\2\668\3\2"+
		"\2\2\67\61\3\2\2\2\678\3\2\2\28\b\3\2\2\29>\5\23\n\2:=\5\23\n\2;=\5\25"+
		"\13\2<:\3\2\2\2<;\3\2\2\2=@\3\2\2\2><\3\2\2\2>?\3\2\2\2?\n\3\2\2\2@>\3"+
		"\2\2\2AB\7*\2\2B\f\3\2\2\2CD\7)\2\2DE\7*\2\2E\16\3\2\2\2FG\7+\2\2G\20"+
		"\3\2\2\2HI\7\60\2\2I\22\3\2\2\2JL\t\5\2\2KJ\3\2\2\2L\24\3\2\2\2MN\4\62"+
		";\2N\26\3\2\2\2\r\2\33\35%*/\65\67<>K\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}