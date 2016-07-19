// Generated from lisp.g4 by ANTLR 4.5.3

package org.tros.lisp.antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class lispParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		STRING=1, WHITESPACE=2, NUMBER=3, SYMBOL=4, LPAREN=5, LITERALSTART=6, 
		RPAREN=7, DOT=8;
	public static final int
		RULE_sexpr = 0, RULE_item = 1, RULE_list = 2, RULE_seq = 3, RULE_atom = 4;
	public static final String[] ruleNames = {
		"sexpr", "item", "list", "seq", "atom"
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

	@Override
	public String getGrammarFileName() { return "lisp.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public lispParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class SexprContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(lispParser.EOF, 0); }
		public List<ListContext> list() {
			return getRuleContexts(ListContext.class);
		}
		public ListContext list(int i) {
			return getRuleContext(ListContext.class,i);
		}
		public SexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).enterSexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).exitSexpr(this);
		}
	}

	public final SexprContext sexpr() throws RecognitionException {
		SexprContext _localctx = new SexprContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_sexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LPAREN) {
				{
				{
				setState(10);
				list();
				}
				}
				setState(15);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(16);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ItemContext extends ParserRuleContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public ItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_item; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).enterItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).exitItem(this);
		}
	}

	public final ItemContext item() throws RecognitionException {
		ItemContext _localctx = new ItemContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_item);
		try {
			setState(20);
			switch (_input.LA(1)) {
			case STRING:
			case NUMBER:
			case SYMBOL:
				enterOuterAlt(_localctx, 1);
				{
				setState(18);
				atom();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(19);
				list();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(lispParser.LPAREN, 0); }
		public SeqContext seq() {
			return getRuleContext(SeqContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(lispParser.RPAREN, 0); }
		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).enterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).exitList(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_list);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			match(LPAREN);
			setState(23);
			seq();
			setState(24);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SeqContext extends ParserRuleContext {
		public ItemContext item() {
			return getRuleContext(ItemContext.class,0);
		}
		public SeqContext seq() {
			return getRuleContext(SeqContext.class,0);
		}
		public SeqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_seq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).enterSeq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).exitSeq(this);
		}
	}

	public final SeqContext seq() throws RecognitionException {
		SeqContext _localctx = new SeqContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_seq);
		try {
			setState(30);
			switch (_input.LA(1)) {
			case RPAREN:
				enterOuterAlt(_localctx, 1);
				{
				}
				break;
			case STRING:
			case NUMBER:
			case SYMBOL:
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(27);
				item();
				setState(28);
				seq();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(lispParser.STRING, 0); }
		public TerminalNode SYMBOL() { return getToken(lispParser.SYMBOL, 0); }
		public TerminalNode NUMBER() { return getToken(lispParser.NUMBER, 0); }
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).exitAtom(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_atom);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << NUMBER) | (1L << SYMBOL))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\n%\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\7\2\16\n\2\f\2\16\2\21\13\2\3\2\3\2\3\3"+
		"\3\3\5\3\27\n\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5!\n\5\3\6\3\6\3\6\2"+
		"\2\7\2\4\6\b\n\2\3\4\2\3\3\5\6\"\2\17\3\2\2\2\4\26\3\2\2\2\6\30\3\2\2"+
		"\2\b \3\2\2\2\n\"\3\2\2\2\f\16\5\6\4\2\r\f\3\2\2\2\16\21\3\2\2\2\17\r"+
		"\3\2\2\2\17\20\3\2\2\2\20\22\3\2\2\2\21\17\3\2\2\2\22\23\7\2\2\3\23\3"+
		"\3\2\2\2\24\27\5\n\6\2\25\27\5\6\4\2\26\24\3\2\2\2\26\25\3\2\2\2\27\5"+
		"\3\2\2\2\30\31\7\7\2\2\31\32\5\b\5\2\32\33\7\t\2\2\33\7\3\2\2\2\34!\3"+
		"\2\2\2\35\36\5\4\3\2\36\37\5\b\5\2\37!\3\2\2\2 \34\3\2\2\2 \35\3\2\2\2"+
		"!\t\3\2\2\2\"#\t\2\2\2#\13\3\2\2\2\5\17\26 ";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}