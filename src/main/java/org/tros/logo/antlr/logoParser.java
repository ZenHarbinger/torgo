// Generated from logo.g4 by ANTLR 4.5

package org.tros.logo.antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class logoParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		T__59=60, T__60=61, T__61=62, T__62=63, T__63=64, T__64=65, T__65=66, 
		T__66=67, T__67=68, T__68=69, T__69=70, T__70=71, T__71=72, T__72=73, 
		T__73=74, HEX=75, STRINGLITERAL=76, STRING=77, NUMBER=78, COMMENT=79, 
		EOL=80, WS=81;
	public static final int
		RULE_prog = 0, RULE_line = 1, RULE_cmd = 2, RULE_procedureInvocation = 3, 
		RULE_procedureDeclaration = 4, RULE_parameterDeclarations = 5, RULE_func = 6, 
		RULE_repeat = 7, RULE_block = 8, RULE_ife = 9, RULE_comparison = 10, RULE_comparisonOperator = 11, 
		RULE_make = 12, RULE_localmake = 13, RULE_print = 14, RULE_quotedstring = 15, 
		RULE_name = 16, RULE_value = 17, RULE_parenExpression = 18, RULE_signExpression = 19, 
		RULE_powerExpression = 20, RULE_multiplyingExpression = 21, RULE_expression = 22, 
		RULE_deref = 23, RULE_fd = 24, RULE_bk = 25, RULE_rt = 26, RULE_lt = 27, 
		RULE_cs = 28, RULE_pu = 29, RULE_pd = 30, RULE_ht = 31, RULE_st = 32, 
		RULE_home = 33, RULE_stop = 34, RULE_setxy = 35, RULE_random = 36, RULE_getangle = 37, 
		RULE_getx = 38, RULE_gety = 39, RULE_repcount = 40, RULE_fore = 41, RULE_pc = 42, 
		RULE_cc = 43, RULE_hexcolor = 44, RULE_pause = 45, RULE_ds = 46, RULE_fontname = 47, 
		RULE_fontsize = 48, RULE_fontstyle = 49, RULE_style = 50, RULE_number = 51, 
		RULE_comment = 52;
	public static final String[] ruleNames = {
		"prog", "line", "cmd", "procedureInvocation", "procedureDeclaration", 
		"parameterDeclarations", "func", "repeat", "block", "ife", "comparison", 
		"comparisonOperator", "make", "localmake", "print", "quotedstring", "name", 
		"value", "parenExpression", "signExpression", "powerExpression", "multiplyingExpression", 
		"expression", "deref", "fd", "bk", "rt", "lt", "cs", "pu", "pd", "ht", 
		"st", "home", "stop", "setxy", "random", "getangle", "getx", "gety", "repcount", 
		"fore", "pc", "cc", "hexcolor", "pause", "ds", "fontname", "fontsize", 
		"fontstyle", "style", "number", "comment"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'to'", "'end'", "':'", "','", "'repeat'", "'['", "']'", "'if'", 
		"'<'", "'>'", "'='", "'!'", "'<='", "'>='", "'=='", "'!='", "'<>'", "'make'", 
		"'localmake'", "'print'", "'('", "')'", "'+'", "'-'", "'^'", "'*'", "'/'", 
		"'\\'", "'%'", "'fd'", "'forward'", "'bk'", "'backward'", "'back'", "'rt'", 
		"'right'", "'lt'", "'left'", "'cs'", "'clearscreen'", "'cls'", "'clear'", 
		"'pu'", "'penup'", "'pd'", "'pendown'", "'ht'", "'hideturtle'", "'st'", 
		"'showturtle'", "'home'", "'stop'", "'setxy'", "'random'", "'getangle'", 
		"'getx'", "'gety'", "'repcount'", "'for'", "'pc'", "'pencolor'", "'cc'", 
		"'canvascolor'", "'#'", "'pause'", "'ds'", "'drawstring'", "'label'", 
		"'fontname'", "'fontsize'", "'fontstyle'", "'bold'", "'plain'", "'italic'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, "HEX", "STRINGLITERAL", "STRING", "NUMBER", "COMMENT", 
		"EOL", "WS"
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
	public String getGrammarFileName() { return "logo.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public logoParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public List<LineContext> line() {
			return getRuleContexts(LineContext.class);
		}
		public LineContext line(int i) {
			return getRuleContext(LineContext.class,i);
		}
		public List<TerminalNode> EOL() { return getTokens(logoParser.EOL); }
		public TerminalNode EOL(int i) {
			return getToken(logoParser.EOL, i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitProg(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			int _alt;
			setState(118);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(106);
				line();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(111); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(108);
						switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
						case 1:
							{
							setState(107);
							line();
							}
							break;
						}
						setState(110);
						match(EOL);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(113); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(116);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__4) | (1L << T__7) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << T__52) | (1L << T__58) | (1L << T__59) | (1L << T__60) | (1L << T__61) | (1L << T__62))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (T__64 - 65)) | (1L << (T__65 - 65)) | (1L << (T__66 - 65)) | (1L << (T__67 - 65)) | (1L << (T__68 - 65)) | (1L << (T__69 - 65)) | (1L << (T__70 - 65)) | (1L << (STRING - 65)) | (1L << (COMMENT - 65)) | (1L << (EOL - 65)))) != 0)) {
					{
					setState(115);
					line();
					}
				}

				}
				break;
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

	public static class LineContext extends ParserRuleContext {
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public CommentContext comment() {
			return getRuleContext(CommentContext.class,0);
		}
		public PrintContext print() {
			return getRuleContext(PrintContext.class,0);
		}
		public ProcedureDeclarationContext procedureDeclaration() {
			return getRuleContext(ProcedureDeclarationContext.class,0);
		}
		public TerminalNode EOL() { return getToken(logoParser.EOL, 0); }
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitLine(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_line);
		int _la;
		try {
			setState(135);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(121); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(120);
					cmd();
					}
					}
					setState(123); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__7) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << T__52) | (1L << T__58) | (1L << T__59) | (1L << T__60) | (1L << T__61) | (1L << T__62))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (T__64 - 65)) | (1L << (T__65 - 65)) | (1L << (T__66 - 65)) | (1L << (T__67 - 65)) | (1L << (T__68 - 65)) | (1L << (T__69 - 65)) | (1L << (T__70 - 65)) | (1L << (STRING - 65)))) != 0) );
				setState(126);
				_la = _input.LA(1);
				if (_la==COMMENT) {
					{
					setState(125);
					comment();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(128);
				comment();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(129);
				print();
				setState(131);
				_la = _input.LA(1);
				if (_la==COMMENT) {
					{
					setState(130);
					comment();
					}
				}

				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(133);
				procedureDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(134);
				match(EOL);
				}
				break;
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

	public static class CmdContext extends ParserRuleContext {
		public RepeatContext repeat() {
			return getRuleContext(RepeatContext.class,0);
		}
		public FdContext fd() {
			return getRuleContext(FdContext.class,0);
		}
		public BkContext bk() {
			return getRuleContext(BkContext.class,0);
		}
		public RtContext rt() {
			return getRuleContext(RtContext.class,0);
		}
		public LtContext lt() {
			return getRuleContext(LtContext.class,0);
		}
		public CsContext cs() {
			return getRuleContext(CsContext.class,0);
		}
		public PuContext pu() {
			return getRuleContext(PuContext.class,0);
		}
		public PdContext pd() {
			return getRuleContext(PdContext.class,0);
		}
		public HtContext ht() {
			return getRuleContext(HtContext.class,0);
		}
		public StContext st() {
			return getRuleContext(StContext.class,0);
		}
		public HomeContext home() {
			return getRuleContext(HomeContext.class,0);
		}
		public SetxyContext setxy() {
			return getRuleContext(SetxyContext.class,0);
		}
		public MakeContext make() {
			return getRuleContext(MakeContext.class,0);
		}
		public LocalmakeContext localmake() {
			return getRuleContext(LocalmakeContext.class,0);
		}
		public ProcedureInvocationContext procedureInvocation() {
			return getRuleContext(ProcedureInvocationContext.class,0);
		}
		public IfeContext ife() {
			return getRuleContext(IfeContext.class,0);
		}
		public StopContext stop() {
			return getRuleContext(StopContext.class,0);
		}
		public ForeContext fore() {
			return getRuleContext(ForeContext.class,0);
		}
		public PcContext pc() {
			return getRuleContext(PcContext.class,0);
		}
		public CcContext cc() {
			return getRuleContext(CcContext.class,0);
		}
		public PauseContext pause() {
			return getRuleContext(PauseContext.class,0);
		}
		public DsContext ds() {
			return getRuleContext(DsContext.class,0);
		}
		public FontsizeContext fontsize() {
			return getRuleContext(FontsizeContext.class,0);
		}
		public FontstyleContext fontstyle() {
			return getRuleContext(FontstyleContext.class,0);
		}
		public FontnameContext fontname() {
			return getRuleContext(FontnameContext.class,0);
		}
		public PrintContext print() {
			return getRuleContext(PrintContext.class,0);
		}
		public CmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitCmd(this);
		}
	}

	public final CmdContext cmd() throws RecognitionException {
		CmdContext _localctx = new CmdContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_cmd);
		try {
			setState(163);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(137);
				repeat();
				}
				break;
			case T__29:
			case T__30:
				enterOuterAlt(_localctx, 2);
				{
				setState(138);
				fd();
				}
				break;
			case T__31:
			case T__32:
			case T__33:
				enterOuterAlt(_localctx, 3);
				{
				setState(139);
				bk();
				}
				break;
			case T__34:
			case T__35:
				enterOuterAlt(_localctx, 4);
				{
				setState(140);
				rt();
				}
				break;
			case T__36:
			case T__37:
				enterOuterAlt(_localctx, 5);
				{
				setState(141);
				lt();
				}
				break;
			case T__38:
			case T__39:
			case T__40:
			case T__41:
				enterOuterAlt(_localctx, 6);
				{
				setState(142);
				cs();
				}
				break;
			case T__42:
			case T__43:
				enterOuterAlt(_localctx, 7);
				{
				setState(143);
				pu();
				}
				break;
			case T__44:
			case T__45:
				enterOuterAlt(_localctx, 8);
				{
				setState(144);
				pd();
				}
				break;
			case T__46:
			case T__47:
				enterOuterAlt(_localctx, 9);
				{
				setState(145);
				ht();
				}
				break;
			case T__48:
			case T__49:
				enterOuterAlt(_localctx, 10);
				{
				setState(146);
				st();
				}
				break;
			case T__50:
				enterOuterAlt(_localctx, 11);
				{
				setState(147);
				home();
				}
				break;
			case T__52:
				enterOuterAlt(_localctx, 12);
				{
				setState(148);
				setxy();
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 13);
				{
				setState(149);
				make();
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 14);
				{
				setState(150);
				localmake();
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 15);
				{
				setState(151);
				procedureInvocation();
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 16);
				{
				setState(152);
				ife();
				}
				break;
			case T__51:
				enterOuterAlt(_localctx, 17);
				{
				setState(153);
				stop();
				}
				break;
			case T__58:
				enterOuterAlt(_localctx, 18);
				{
				setState(154);
				fore();
				}
				break;
			case T__59:
			case T__60:
				enterOuterAlt(_localctx, 19);
				{
				setState(155);
				pc();
				}
				break;
			case T__61:
			case T__62:
				enterOuterAlt(_localctx, 20);
				{
				setState(156);
				cc();
				}
				break;
			case T__64:
				enterOuterAlt(_localctx, 21);
				{
				setState(157);
				pause();
				}
				break;
			case T__65:
			case T__66:
			case T__67:
				enterOuterAlt(_localctx, 22);
				{
				setState(158);
				ds();
				}
				break;
			case T__69:
				enterOuterAlt(_localctx, 23);
				{
				setState(159);
				fontsize();
				}
				break;
			case T__70:
				enterOuterAlt(_localctx, 24);
				{
				setState(160);
				fontstyle();
				}
				break;
			case T__68:
				enterOuterAlt(_localctx, 25);
				{
				setState(161);
				fontname();
				}
				break;
			case T__19:
				enterOuterAlt(_localctx, 26);
				{
				setState(162);
				print();
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

	public static class ProcedureInvocationContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ProcedureInvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedureInvocation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterProcedureInvocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitProcedureInvocation(this);
		}
	}

	public final ProcedureInvocationContext procedureInvocation() throws RecognitionException {
		ProcedureInvocationContext _localctx = new ProcedureInvocationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_procedureInvocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			name();
			setState(169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__20) | (1L << T__22) | (1L << T__23) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57))) != 0) || _la==NUMBER) {
				{
				{
				setState(166);
				expression();
				}
				}
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class ProcedureDeclarationContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public List<ParameterDeclarationsContext> parameterDeclarations() {
			return getRuleContexts(ParameterDeclarationsContext.class);
		}
		public ParameterDeclarationsContext parameterDeclarations(int i) {
			return getRuleContext(ParameterDeclarationsContext.class,i);
		}
		public List<TerminalNode> EOL() { return getTokens(logoParser.EOL); }
		public TerminalNode EOL(int i) {
			return getToken(logoParser.EOL, i);
		}
		public List<LineContext> line() {
			return getRuleContexts(LineContext.class);
		}
		public LineContext line(int i) {
			return getRuleContext(LineContext.class,i);
		}
		public ProcedureDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedureDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterProcedureDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitProcedureDeclaration(this);
		}
	}

	public final ProcedureDeclarationContext procedureDeclaration() throws RecognitionException {
		ProcedureDeclarationContext _localctx = new ProcedureDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_procedureDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			match(T__0);
			setState(173);
			name();
			setState(177);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(174);
				parameterDeclarations();
				}
				}
				setState(179);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(181);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(180);
				match(EOL);
				}
				break;
			}
			setState(187); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(184);
				switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
				case 1:
					{
					setState(183);
					line();
					}
					break;
				}
				setState(186);
				match(EOL);
				}
				}
				setState(189); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__4) | (1L << T__7) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << T__52) | (1L << T__58) | (1L << T__59) | (1L << T__60) | (1L << T__61) | (1L << T__62))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (T__64 - 65)) | (1L << (T__65 - 65)) | (1L << (T__66 - 65)) | (1L << (T__67 - 65)) | (1L << (T__68 - 65)) | (1L << (T__69 - 65)) | (1L << (T__70 - 65)) | (1L << (STRING - 65)) | (1L << (COMMENT - 65)) | (1L << (EOL - 65)))) != 0) );
			setState(191);
			match(T__1);
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

	public static class ParameterDeclarationsContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public List<ParameterDeclarationsContext> parameterDeclarations() {
			return getRuleContexts(ParameterDeclarationsContext.class);
		}
		public ParameterDeclarationsContext parameterDeclarations(int i) {
			return getRuleContext(ParameterDeclarationsContext.class,i);
		}
		public ParameterDeclarationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterDeclarations; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterParameterDeclarations(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitParameterDeclarations(this);
		}
	}

	public final ParameterDeclarationsContext parameterDeclarations() throws RecognitionException {
		ParameterDeclarationsContext _localctx = new ParameterDeclarationsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_parameterDeclarations);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			match(T__2);
			setState(194);
			name();
			setState(199);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(195);
					match(T__3);
					setState(196);
					parameterDeclarations();
					}
					} 
				}
				setState(201);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
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

	public static class FuncContext extends ParserRuleContext {
		public RandomContext random() {
			return getRuleContext(RandomContext.class,0);
		}
		public RepcountContext repcount() {
			return getRuleContext(RepcountContext.class,0);
		}
		public GetangleContext getangle() {
			return getRuleContext(GetangleContext.class,0);
		}
		public GetxContext getx() {
			return getRuleContext(GetxContext.class,0);
		}
		public GetyContext gety() {
			return getRuleContext(GetyContext.class,0);
		}
		public FuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitFunc(this);
		}
	}

	public final FuncContext func() throws RecognitionException {
		FuncContext _localctx = new FuncContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_func);
		try {
			setState(207);
			switch (_input.LA(1)) {
			case T__53:
				enterOuterAlt(_localctx, 1);
				{
				setState(202);
				random();
				}
				break;
			case T__57:
				enterOuterAlt(_localctx, 2);
				{
				setState(203);
				repcount();
				}
				break;
			case T__54:
				enterOuterAlt(_localctx, 3);
				{
				setState(204);
				getangle();
				}
				break;
			case T__55:
				enterOuterAlt(_localctx, 4);
				{
				setState(205);
				getx();
				}
				break;
			case T__56:
				enterOuterAlt(_localctx, 5);
				{
				setState(206);
				gety();
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

	public static class RepeatContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public RepeatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_repeat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterRepeat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitRepeat(this);
		}
	}

	public final RepeatContext repeat() throws RecognitionException {
		RepeatContext _localctx = new RepeatContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_repeat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(T__4);
			setState(210);
			expression();
			setState(211);
			block();
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

	public static class BlockContext extends ParserRuleContext {
		public List<TerminalNode> EOL() { return getTokens(logoParser.EOL); }
		public TerminalNode EOL(int i) {
			return getToken(logoParser.EOL, i);
		}
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitBlock(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(T__5);
			setState(217);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EOL) {
				{
				{
				setState(214);
				match(EOL);
				}
				}
				setState(219);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(227); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(220);
				cmd();
				setState(224);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==EOL) {
					{
					{
					setState(221);
					match(EOL);
					}
					}
					setState(226);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(229); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__7) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << T__52) | (1L << T__58) | (1L << T__59) | (1L << T__60) | (1L << T__61) | (1L << T__62))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (T__64 - 65)) | (1L << (T__65 - 65)) | (1L << (T__66 - 65)) | (1L << (T__67 - 65)) | (1L << (T__68 - 65)) | (1L << (T__69 - 65)) | (1L << (T__70 - 65)) | (1L << (STRING - 65)))) != 0) );
			setState(231);
			match(T__6);
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

	public static class IfeContext extends ParserRuleContext {
		public ComparisonContext comparison() {
			return getRuleContext(ComparisonContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public IfeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ife; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterIfe(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitIfe(this);
		}
	}

	public final IfeContext ife() throws RecognitionException {
		IfeContext _localctx = new IfeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_ife);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			match(T__7);
			setState(234);
			comparison();
			setState(235);
			block();
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

	public static class ComparisonContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ComparisonOperatorContext comparisonOperator() {
			return getRuleContext(ComparisonOperatorContext.class,0);
		}
		public ComparisonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparison; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitComparison(this);
		}
	}

	public final ComparisonContext comparison() throws RecognitionException {
		ComparisonContext _localctx = new ComparisonContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_comparison);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			expression();
			setState(238);
			comparisonOperator();
			setState(239);
			expression();
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

	public static class ComparisonOperatorContext extends ParserRuleContext {
		public ComparisonOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterComparisonOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitComparisonOperator(this);
		}
	}

	public final ComparisonOperatorContext comparisonOperator() throws RecognitionException {
		ComparisonOperatorContext _localctx = new ComparisonOperatorContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_comparisonOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16))) != 0)) ) {
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

	public static class MakeContext extends ParserRuleContext {
		public TerminalNode STRINGLITERAL() { return getToken(logoParser.STRINGLITERAL, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public MakeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_make; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterMake(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitMake(this);
		}
	}

	public final MakeContext make() throws RecognitionException {
		MakeContext _localctx = new MakeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_make);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			match(T__17);
			setState(244);
			match(STRINGLITERAL);
			setState(245);
			value();
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

	public static class LocalmakeContext extends ParserRuleContext {
		public TerminalNode STRINGLITERAL() { return getToken(logoParser.STRINGLITERAL, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public LocalmakeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localmake; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterLocalmake(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitLocalmake(this);
		}
	}

	public final LocalmakeContext localmake() throws RecognitionException {
		LocalmakeContext _localctx = new LocalmakeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_localmake);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			match(T__18);
			setState(248);
			match(STRINGLITERAL);
			setState(249);
			value();
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

	public static class PrintContext extends ParserRuleContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public QuotedstringContext quotedstring() {
			return getRuleContext(QuotedstringContext.class,0);
		}
		public PrintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_print; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterPrint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitPrint(this);
		}
	}

	public final PrintContext print() throws RecognitionException {
		PrintContext _localctx = new PrintContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_print);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(251);
			match(T__19);
			setState(254);
			switch (_input.LA(1)) {
			case T__2:
			case T__20:
			case T__22:
			case T__23:
			case T__53:
			case T__54:
			case T__55:
			case T__56:
			case T__57:
			case STRINGLITERAL:
			case NUMBER:
				{
				setState(252);
				value();
				}
				break;
			case T__5:
				{
				setState(253);
				quotedstring();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class QuotedstringContext extends ParserRuleContext {
		public List<QuotedstringContext> quotedstring() {
			return getRuleContexts(QuotedstringContext.class);
		}
		public QuotedstringContext quotedstring(int i) {
			return getRuleContext(QuotedstringContext.class,i);
		}
		public QuotedstringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quotedstring; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterQuotedstring(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitQuotedstring(this);
		}
	}

	public final QuotedstringContext quotedstring() throws RecognitionException {
		QuotedstringContext _localctx = new QuotedstringContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_quotedstring);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			match(T__5);
			setState(261);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57) | (1L << T__58) | (1L << T__59) | (1L << T__60) | (1L << T__61) | (1L << T__62))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (T__63 - 64)) | (1L << (T__64 - 64)) | (1L << (T__65 - 64)) | (1L << (T__66 - 64)) | (1L << (T__67 - 64)) | (1L << (T__68 - 64)) | (1L << (T__69 - 64)) | (1L << (T__70 - 64)) | (1L << (T__71 - 64)) | (1L << (T__72 - 64)) | (1L << (T__73 - 64)) | (1L << (HEX - 64)) | (1L << (STRINGLITERAL - 64)) | (1L << (STRING - 64)) | (1L << (NUMBER - 64)) | (1L << (COMMENT - 64)) | (1L << (EOL - 64)) | (1L << (WS - 64)))) != 0)) {
				{
				setState(259);
				switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
				case 1:
					{
					setState(257);
					quotedstring();
					}
					break;
				case 2:
					{
					setState(258);
					_la = _input.LA(1);
					if ( _la <= 0 || (_la==T__6) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					}
					break;
				}
				}
				setState(263);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(264);
			match(T__6);
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

	public static class NameContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(logoParser.STRING, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitName(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(266);
			match(STRING);
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

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode STRINGLITERAL() { return getToken(logoParser.STRINGLITERAL, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DerefContext deref() {
			return getRuleContext(DerefContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_value);
		try {
			setState(271);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(268);
				match(STRINGLITERAL);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(269);
				expression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(270);
				deref();
				}
				break;
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

	public static class ParenExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parenExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterParenExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitParenExpression(this);
		}
	}

	public final ParenExpressionContext parenExpression() throws RecognitionException {
		ParenExpressionContext _localctx = new ParenExpressionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_parenExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			match(T__20);
			setState(274);
			expression();
			setState(275);
			match(T__21);
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

	public static class SignExpressionContext extends ParserRuleContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public DerefContext deref() {
			return getRuleContext(DerefContext.class,0);
		}
		public FuncContext func() {
			return getRuleContext(FuncContext.class,0);
		}
		public ParenExpressionContext parenExpression() {
			return getRuleContext(ParenExpressionContext.class,0);
		}
		public SignExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_signExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterSignExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitSignExpression(this);
		}
	}

	public final SignExpressionContext signExpression() throws RecognitionException {
		SignExpressionContext _localctx = new SignExpressionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_signExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(278);
			_la = _input.LA(1);
			if (_la==T__22 || _la==T__23) {
				{
				setState(277);
				_la = _input.LA(1);
				if ( !(_la==T__22 || _la==T__23) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
			}

			setState(284);
			switch (_input.LA(1)) {
			case NUMBER:
				{
				setState(280);
				number();
				}
				break;
			case T__2:
				{
				setState(281);
				deref();
				}
				break;
			case T__53:
			case T__54:
			case T__55:
			case T__56:
			case T__57:
				{
				setState(282);
				func();
				}
				break;
			case T__20:
				{
				setState(283);
				parenExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class PowerExpressionContext extends ParserRuleContext {
		public List<SignExpressionContext> signExpression() {
			return getRuleContexts(SignExpressionContext.class);
		}
		public SignExpressionContext signExpression(int i) {
			return getRuleContext(SignExpressionContext.class,i);
		}
		public PowerExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_powerExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterPowerExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitPowerExpression(this);
		}
	}

	public final PowerExpressionContext powerExpression() throws RecognitionException {
		PowerExpressionContext _localctx = new PowerExpressionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_powerExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			signExpression();
			setState(289);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(287);
				match(T__24);
				setState(288);
				signExpression();
				}
				break;
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

	public static class MultiplyingExpressionContext extends ParserRuleContext {
		public List<PowerExpressionContext> powerExpression() {
			return getRuleContexts(PowerExpressionContext.class);
		}
		public PowerExpressionContext powerExpression(int i) {
			return getRuleContext(PowerExpressionContext.class,i);
		}
		public MultiplyingExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplyingExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterMultiplyingExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitMultiplyingExpression(this);
		}
	}

	public final MultiplyingExpressionContext multiplyingExpression() throws RecognitionException {
		MultiplyingExpressionContext _localctx = new MultiplyingExpressionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_multiplyingExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(291);
			powerExpression();
			setState(296);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(292);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28))) != 0)) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(293);
					powerExpression();
					}
					} 
				}
				setState(298);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
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

	public static class ExpressionContext extends ParserRuleContext {
		public List<MultiplyingExpressionContext> multiplyingExpression() {
			return getRuleContexts(MultiplyingExpressionContext.class);
		}
		public MultiplyingExpressionContext multiplyingExpression(int i) {
			return getRuleContext(MultiplyingExpressionContext.class,i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_expression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(299);
			multiplyingExpression();
			setState(304);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(300);
					_la = _input.LA(1);
					if ( !(_la==T__22 || _la==T__23) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(301);
					multiplyingExpression();
					}
					} 
				}
				setState(306);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
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

	public static class DerefContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public DerefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deref; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterDeref(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitDeref(this);
		}
	}

	public final DerefContext deref() throws RecognitionException {
		DerefContext _localctx = new DerefContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_deref);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			match(T__2);
			setState(308);
			name();
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

	public static class FdContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterFd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitFd(this);
		}
	}

	public final FdContext fd() throws RecognitionException {
		FdContext _localctx = new FdContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_fd);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(310);
			_la = _input.LA(1);
			if ( !(_la==T__29 || _la==T__30) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(311);
			expression();
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

	public static class BkContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BkContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bk; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterBk(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitBk(this);
		}
	}

	public final BkContext bk() throws RecognitionException {
		BkContext _localctx = new BkContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_bk);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__31) | (1L << T__32) | (1L << T__33))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(314);
			expression();
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

	public static class RtContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public RtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterRt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitRt(this);
		}
	}

	public final RtContext rt() throws RecognitionException {
		RtContext _localctx = new RtContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_rt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316);
			_la = _input.LA(1);
			if ( !(_la==T__34 || _la==T__35) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(317);
			expression();
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

	public static class LtContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterLt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitLt(this);
		}
	}

	public final LtContext lt() throws RecognitionException {
		LtContext _localctx = new LtContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_lt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			_la = _input.LA(1);
			if ( !(_la==T__36 || _la==T__37) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(320);
			expression();
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

	public static class CsContext extends ParserRuleContext {
		public CsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterCs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitCs(this);
		}
	}

	public final CsContext cs() throws RecognitionException {
		CsContext _localctx = new CsContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_cs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(322);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41))) != 0)) ) {
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

	public static class PuContext extends ParserRuleContext {
		public PuContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pu; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterPu(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitPu(this);
		}
	}

	public final PuContext pu() throws RecognitionException {
		PuContext _localctx = new PuContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_pu);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			_la = _input.LA(1);
			if ( !(_la==T__42 || _la==T__43) ) {
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

	public static class PdContext extends ParserRuleContext {
		public PdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterPd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitPd(this);
		}
	}

	public final PdContext pd() throws RecognitionException {
		PdContext _localctx = new PdContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_pd);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(326);
			_la = _input.LA(1);
			if ( !(_la==T__44 || _la==T__45) ) {
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

	public static class HtContext extends ParserRuleContext {
		public HtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ht; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterHt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitHt(this);
		}
	}

	public final HtContext ht() throws RecognitionException {
		HtContext _localctx = new HtContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_ht);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(328);
			_la = _input.LA(1);
			if ( !(_la==T__46 || _la==T__47) ) {
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

	public static class StContext extends ParserRuleContext {
		public StContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_st; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterSt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitSt(this);
		}
	}

	public final StContext st() throws RecognitionException {
		StContext _localctx = new StContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_st);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(330);
			_la = _input.LA(1);
			if ( !(_la==T__48 || _la==T__49) ) {
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

	public static class HomeContext extends ParserRuleContext {
		public HomeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_home; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterHome(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitHome(this);
		}
	}

	public final HomeContext home() throws RecognitionException {
		HomeContext _localctx = new HomeContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_home);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(332);
			match(T__50);
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

	public static class StopContext extends ParserRuleContext {
		public StopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterStop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitStop(this);
		}
	}

	public final StopContext stop() throws RecognitionException {
		StopContext _localctx = new StopContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_stop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(334);
			match(T__51);
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

	public static class SetxyContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public SetxyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setxy; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterSetxy(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitSetxy(this);
		}
	}

	public final SetxyContext setxy() throws RecognitionException {
		SetxyContext _localctx = new SetxyContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_setxy);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			match(T__52);
			setState(337);
			expression();
			setState(338);
			expression();
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

	public static class RandomContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public RandomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_random; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterRandom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitRandom(this);
		}
	}

	public final RandomContext random() throws RecognitionException {
		RandomContext _localctx = new RandomContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_random);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(340);
			match(T__53);
			setState(341);
			expression();
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

	public static class GetangleContext extends ParserRuleContext {
		public GetangleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_getangle; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterGetangle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitGetangle(this);
		}
	}

	public final GetangleContext getangle() throws RecognitionException {
		GetangleContext _localctx = new GetangleContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_getangle);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(343);
			match(T__54);
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

	public static class GetxContext extends ParserRuleContext {
		public GetxContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_getx; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterGetx(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitGetx(this);
		}
	}

	public final GetxContext getx() throws RecognitionException {
		GetxContext _localctx = new GetxContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_getx);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(345);
			match(T__55);
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

	public static class GetyContext extends ParserRuleContext {
		public GetyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gety; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterGety(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitGety(this);
		}
	}

	public final GetyContext gety() throws RecognitionException {
		GetyContext _localctx = new GetyContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_gety);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(347);
			match(T__56);
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

	public static class RepcountContext extends ParserRuleContext {
		public RepcountContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_repcount; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterRepcount(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitRepcount(this);
		}
	}

	public final RepcountContext repcount() throws RecognitionException {
		RepcountContext _localctx = new RepcountContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_repcount);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(349);
			match(T__57);
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

	public static class ForeContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ForeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fore; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterFore(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitFore(this);
		}
	}

	public final ForeContext fore() throws RecognitionException {
		ForeContext _localctx = new ForeContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_fore);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(351);
			match(T__58);
			setState(352);
			match(T__5);
			setState(353);
			name();
			setState(354);
			expression();
			setState(355);
			expression();
			setState(357);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__20) | (1L << T__22) | (1L << T__23) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57))) != 0) || _la==NUMBER) {
				{
				setState(356);
				expression();
				}
			}

			setState(359);
			match(T__6);
			setState(360);
			block();
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

	public static class PcContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public HexcolorContext hexcolor() {
			return getRuleContext(HexcolorContext.class,0);
		}
		public PcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterPc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitPc(this);
		}
	}

	public final PcContext pc() throws RecognitionException {
		PcContext _localctx = new PcContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_pc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(362);
			_la = _input.LA(1);
			if ( !(_la==T__59 || _la==T__60) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(371);
			switch (_input.LA(1)) {
			case STRING:
				{
				setState(363);
				name();
				}
				break;
			case T__2:
			case T__20:
			case T__22:
			case T__23:
			case T__53:
			case T__54:
			case T__55:
			case T__56:
			case T__57:
			case NUMBER:
				{
				setState(364);
				expression();
				setState(365);
				expression();
				setState(366);
				expression();
				setState(368);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__20) | (1L << T__22) | (1L << T__23) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57))) != 0) || _la==NUMBER) {
					{
					setState(367);
					expression();
					}
				}

				}
				break;
			case T__63:
				{
				setState(370);
				hexcolor();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class CcContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public HexcolorContext hexcolor() {
			return getRuleContext(HexcolorContext.class,0);
		}
		public CcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterCc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitCc(this);
		}
	}

	public final CcContext cc() throws RecognitionException {
		CcContext _localctx = new CcContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_cc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(373);
			_la = _input.LA(1);
			if ( !(_la==T__61 || _la==T__62) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(380);
			switch (_input.LA(1)) {
			case STRING:
				{
				setState(374);
				name();
				}
				break;
			case T__2:
			case T__20:
			case T__22:
			case T__23:
			case T__53:
			case T__54:
			case T__55:
			case T__56:
			case T__57:
			case NUMBER:
				{
				setState(375);
				expression();
				setState(376);
				expression();
				setState(377);
				expression();
				}
				break;
			case T__63:
				{
				setState(379);
				hexcolor();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class HexcolorContext extends ParserRuleContext {
		public TerminalNode HEX() { return getToken(logoParser.HEX, 0); }
		public HexcolorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hexcolor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterHexcolor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitHexcolor(this);
		}
	}

	public final HexcolorContext hexcolor() throws RecognitionException {
		HexcolorContext _localctx = new HexcolorContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_hexcolor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(382);
			match(T__63);
			setState(383);
			match(HEX);
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

	public static class PauseContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterPause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitPause(this);
		}
	}

	public final PauseContext pause() throws RecognitionException {
		PauseContext _localctx = new PauseContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_pause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(385);
			match(T__64);
			setState(386);
			expression();
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

	public static class DsContext extends ParserRuleContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public DsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ds; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterDs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitDs(this);
		}
	}

	public final DsContext ds() throws RecognitionException {
		DsContext _localctx = new DsContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_ds);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(388);
			_la = _input.LA(1);
			if ( !(((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (T__65 - 66)) | (1L << (T__66 - 66)) | (1L << (T__67 - 66)))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(389);
			value();
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

	public static class FontnameContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public FontnameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fontname; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterFontname(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitFontname(this);
		}
	}

	public final FontnameContext fontname() throws RecognitionException {
		FontnameContext _localctx = new FontnameContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_fontname);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(391);
			match(T__68);
			setState(392);
			name();
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

	public static class FontsizeContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FontsizeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fontsize; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterFontsize(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitFontsize(this);
		}
	}

	public final FontsizeContext fontsize() throws RecognitionException {
		FontsizeContext _localctx = new FontsizeContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_fontsize);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(394);
			match(T__69);
			setState(395);
			expression();
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

	public static class FontstyleContext extends ParserRuleContext {
		public StyleContext style() {
			return getRuleContext(StyleContext.class,0);
		}
		public FontstyleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fontstyle; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterFontstyle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitFontstyle(this);
		}
	}

	public final FontstyleContext fontstyle() throws RecognitionException {
		FontstyleContext _localctx = new FontstyleContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_fontstyle);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(397);
			match(T__70);
			setState(398);
			style();
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

	public static class StyleContext extends ParserRuleContext {
		public StyleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_style; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterStyle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitStyle(this);
		}
	}

	public final StyleContext style() throws RecognitionException {
		StyleContext _localctx = new StyleContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_style);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(400);
			_la = _input.LA(1);
			if ( !(((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (T__71 - 72)) | (1L << (T__72 - 72)) | (1L << (T__73 - 72)))) != 0)) ) {
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

	public static class NumberContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(logoParser.NUMBER, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitNumber(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_number);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			match(NUMBER);
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

	public static class CommentContext extends ParserRuleContext {
		public TerminalNode COMMENT() { return getToken(logoParser.COMMENT, 0); }
		public CommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).enterComment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof logoListener ) ((logoListener)listener).exitComment(this);
		}
	}

	public final CommentContext comment() throws RecognitionException {
		CommentContext _localctx = new CommentContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_comment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(404);
			match(COMMENT);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3S\u0199\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\3\2\3\2\5\2o\n\2\3\2\6\2r\n\2\r\2\16\2s\3\2\5"+
		"\2w\n\2\5\2y\n\2\3\3\6\3|\n\3\r\3\16\3}\3\3\5\3\u0081\n\3\3\3\3\3\3\3"+
		"\5\3\u0086\n\3\3\3\3\3\5\3\u008a\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\5\4\u00a6\n\4\3\5\3\5\7\5\u00aa\n\5\f\5\16\5\u00ad\13\5\3\6\3\6\3\6\7"+
		"\6\u00b2\n\6\f\6\16\6\u00b5\13\6\3\6\5\6\u00b8\n\6\3\6\5\6\u00bb\n\6\3"+
		"\6\6\6\u00be\n\6\r\6\16\6\u00bf\3\6\3\6\3\7\3\7\3\7\3\7\7\7\u00c8\n\7"+
		"\f\7\16\7\u00cb\13\7\3\b\3\b\3\b\3\b\3\b\5\b\u00d2\n\b\3\t\3\t\3\t\3\t"+
		"\3\n\3\n\7\n\u00da\n\n\f\n\16\n\u00dd\13\n\3\n\3\n\7\n\u00e1\n\n\f\n\16"+
		"\n\u00e4\13\n\6\n\u00e6\n\n\r\n\16\n\u00e7\3\n\3\n\3\13\3\13\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\20"+
		"\3\20\3\20\5\20\u0101\n\20\3\21\3\21\3\21\7\21\u0106\n\21\f\21\16\21\u0109"+
		"\13\21\3\21\3\21\3\22\3\22\3\23\3\23\3\23\5\23\u0112\n\23\3\24\3\24\3"+
		"\24\3\24\3\25\5\25\u0119\n\25\3\25\3\25\3\25\3\25\5\25\u011f\n\25\3\26"+
		"\3\26\3\26\5\26\u0124\n\26\3\27\3\27\3\27\7\27\u0129\n\27\f\27\16\27\u012c"+
		"\13\27\3\30\3\30\3\30\7\30\u0131\n\30\f\30\16\30\u0134\13\30\3\31\3\31"+
		"\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36"+
		"\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3%\3%\3&\3&\3&\3"+
		"\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3+\3+\3+\3+\5+\u0168\n+\3+\3+\3+\3,\3,"+
		"\3,\3,\3,\3,\5,\u0173\n,\3,\5,\u0176\n,\3-\3-\3-\3-\3-\3-\3-\5-\u017f"+
		"\n-\3.\3.\3.\3/\3/\3/\3\60\3\60\3\60\3\61\3\61\3\61\3\62\3\62\3\62\3\63"+
		"\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\66\2\2\67\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhj"+
		"\2\23\3\2\13\23\3\2\t\t\3\2\31\32\3\2\34\37\3\2 !\3\2\"$\3\2%&\3\2\'("+
		"\3\2),\3\2-.\3\2/\60\3\2\61\62\3\2\63\64\3\2>?\3\2@A\3\2DF\3\2JL\u01a6"+
		"\2x\3\2\2\2\4\u0089\3\2\2\2\6\u00a5\3\2\2\2\b\u00a7\3\2\2\2\n\u00ae\3"+
		"\2\2\2\f\u00c3\3\2\2\2\16\u00d1\3\2\2\2\20\u00d3\3\2\2\2\22\u00d7\3\2"+
		"\2\2\24\u00eb\3\2\2\2\26\u00ef\3\2\2\2\30\u00f3\3\2\2\2\32\u00f5\3\2\2"+
		"\2\34\u00f9\3\2\2\2\36\u00fd\3\2\2\2 \u0102\3\2\2\2\"\u010c\3\2\2\2$\u0111"+
		"\3\2\2\2&\u0113\3\2\2\2(\u0118\3\2\2\2*\u0120\3\2\2\2,\u0125\3\2\2\2."+
		"\u012d\3\2\2\2\60\u0135\3\2\2\2\62\u0138\3\2\2\2\64\u013b\3\2\2\2\66\u013e"+
		"\3\2\2\28\u0141\3\2\2\2:\u0144\3\2\2\2<\u0146\3\2\2\2>\u0148\3\2\2\2@"+
		"\u014a\3\2\2\2B\u014c\3\2\2\2D\u014e\3\2\2\2F\u0150\3\2\2\2H\u0152\3\2"+
		"\2\2J\u0156\3\2\2\2L\u0159\3\2\2\2N\u015b\3\2\2\2P\u015d\3\2\2\2R\u015f"+
		"\3\2\2\2T\u0161\3\2\2\2V\u016c\3\2\2\2X\u0177\3\2\2\2Z\u0180\3\2\2\2\\"+
		"\u0183\3\2\2\2^\u0186\3\2\2\2`\u0189\3\2\2\2b\u018c\3\2\2\2d\u018f\3\2"+
		"\2\2f\u0192\3\2\2\2h\u0194\3\2\2\2j\u0196\3\2\2\2ly\5\4\3\2mo\5\4\3\2"+
		"nm\3\2\2\2no\3\2\2\2op\3\2\2\2pr\7R\2\2qn\3\2\2\2rs\3\2\2\2sq\3\2\2\2"+
		"st\3\2\2\2tv\3\2\2\2uw\5\4\3\2vu\3\2\2\2vw\3\2\2\2wy\3\2\2\2xl\3\2\2\2"+
		"xq\3\2\2\2y\3\3\2\2\2z|\5\6\4\2{z\3\2\2\2|}\3\2\2\2}{\3\2\2\2}~\3\2\2"+
		"\2~\u0080\3\2\2\2\177\u0081\5j\66\2\u0080\177\3\2\2\2\u0080\u0081\3\2"+
		"\2\2\u0081\u008a\3\2\2\2\u0082\u008a\5j\66\2\u0083\u0085\5\36\20\2\u0084"+
		"\u0086\5j\66\2\u0085\u0084\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u008a\3\2"+
		"\2\2\u0087\u008a\5\n\6\2\u0088\u008a\7R\2\2\u0089{\3\2\2\2\u0089\u0082"+
		"\3\2\2\2\u0089\u0083\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u0088\3\2\2\2\u008a"+
		"\5\3\2\2\2\u008b\u00a6\5\20\t\2\u008c\u00a6\5\62\32\2\u008d\u00a6\5\64"+
		"\33\2\u008e\u00a6\5\66\34\2\u008f\u00a6\58\35\2\u0090\u00a6\5:\36\2\u0091"+
		"\u00a6\5<\37\2\u0092\u00a6\5> \2\u0093\u00a6\5@!\2\u0094\u00a6\5B\"\2"+
		"\u0095\u00a6\5D#\2\u0096\u00a6\5H%\2\u0097\u00a6\5\32\16\2\u0098\u00a6"+
		"\5\34\17\2\u0099\u00a6\5\b\5\2\u009a\u00a6\5\24\13\2\u009b\u00a6\5F$\2"+
		"\u009c\u00a6\5T+\2\u009d\u00a6\5V,\2\u009e\u00a6\5X-\2\u009f\u00a6\5\\"+
		"/\2\u00a0\u00a6\5^\60\2\u00a1\u00a6\5b\62\2\u00a2\u00a6\5d\63\2\u00a3"+
		"\u00a6\5`\61\2\u00a4\u00a6\5\36\20\2\u00a5\u008b\3\2\2\2\u00a5\u008c\3"+
		"\2\2\2\u00a5\u008d\3\2\2\2\u00a5\u008e\3\2\2\2\u00a5\u008f\3\2\2\2\u00a5"+
		"\u0090\3\2\2\2\u00a5\u0091\3\2\2\2\u00a5\u0092\3\2\2\2\u00a5\u0093\3\2"+
		"\2\2\u00a5\u0094\3\2\2\2\u00a5\u0095\3\2\2\2\u00a5\u0096\3\2\2\2\u00a5"+
		"\u0097\3\2\2\2\u00a5\u0098\3\2\2\2\u00a5\u0099\3\2\2\2\u00a5\u009a\3\2"+
		"\2\2\u00a5\u009b\3\2\2\2\u00a5\u009c\3\2\2\2\u00a5\u009d\3\2\2\2\u00a5"+
		"\u009e\3\2\2\2\u00a5\u009f\3\2\2\2\u00a5\u00a0\3\2\2\2\u00a5\u00a1\3\2"+
		"\2\2\u00a5\u00a2\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a5\u00a4\3\2\2\2\u00a6"+
		"\7\3\2\2\2\u00a7\u00ab\5\"\22\2\u00a8\u00aa\5.\30\2\u00a9\u00a8\3\2\2"+
		"\2\u00aa\u00ad\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\t"+
		"\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00af\7\3\2\2\u00af\u00b3\5\"\22\2"+
		"\u00b0\u00b2\5\f\7\2\u00b1\u00b0\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3\u00b1"+
		"\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b7\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b6"+
		"\u00b8\7R\2\2\u00b7\u00b6\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00bd\3\2"+
		"\2\2\u00b9\u00bb\5\4\3\2\u00ba\u00b9\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb"+
		"\u00bc\3\2\2\2\u00bc\u00be\7R\2\2\u00bd\u00ba\3\2\2\2\u00be\u00bf\3\2"+
		"\2\2\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1"+
		"\u00c2\7\4\2\2\u00c2\13\3\2\2\2\u00c3\u00c4\7\5\2\2\u00c4\u00c9\5\"\22"+
		"\2\u00c5\u00c6\7\6\2\2\u00c6\u00c8\5\f\7\2\u00c7\u00c5\3\2\2\2\u00c8\u00cb"+
		"\3\2\2\2\u00c9\u00c7\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\r\3\2\2\2\u00cb"+
		"\u00c9\3\2\2\2\u00cc\u00d2\5J&\2\u00cd\u00d2\5R*\2\u00ce\u00d2\5L\'\2"+
		"\u00cf\u00d2\5N(\2\u00d0\u00d2\5P)\2\u00d1\u00cc\3\2\2\2\u00d1\u00cd\3"+
		"\2\2\2\u00d1\u00ce\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d1\u00d0\3\2\2\2\u00d2"+
		"\17\3\2\2\2\u00d3\u00d4\7\7\2\2\u00d4\u00d5\5.\30\2\u00d5\u00d6\5\22\n"+
		"\2\u00d6\21\3\2\2\2\u00d7\u00db\7\b\2\2\u00d8\u00da\7R\2\2\u00d9\u00d8"+
		"\3\2\2\2\u00da\u00dd\3\2\2\2\u00db\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc"+
		"\u00e5\3\2\2\2\u00dd\u00db\3\2\2\2\u00de\u00e2\5\6\4\2\u00df\u00e1\7R"+
		"\2\2\u00e0\u00df\3\2\2\2\u00e1\u00e4\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e2"+
		"\u00e3\3\2\2\2\u00e3\u00e6\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e5\u00de\3\2"+
		"\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8"+
		"\u00e9\3\2\2\2\u00e9\u00ea\7\t\2\2\u00ea\23\3\2\2\2\u00eb\u00ec\7\n\2"+
		"\2\u00ec\u00ed\5\26\f\2\u00ed\u00ee\5\22\n\2\u00ee\25\3\2\2\2\u00ef\u00f0"+
		"\5.\30\2\u00f0\u00f1\5\30\r\2\u00f1\u00f2\5.\30\2\u00f2\27\3\2\2\2\u00f3"+
		"\u00f4\t\2\2\2\u00f4\31\3\2\2\2\u00f5\u00f6\7\24\2\2\u00f6\u00f7\7N\2"+
		"\2\u00f7\u00f8\5$\23\2\u00f8\33\3\2\2\2\u00f9\u00fa\7\25\2\2\u00fa\u00fb"+
		"\7N\2\2\u00fb\u00fc\5$\23\2\u00fc\35\3\2\2\2\u00fd\u0100\7\26\2\2\u00fe"+
		"\u0101\5$\23\2\u00ff\u0101\5 \21\2\u0100\u00fe\3\2\2\2\u0100\u00ff\3\2"+
		"\2\2\u0101\37\3\2\2\2\u0102\u0107\7\b\2\2\u0103\u0106\5 \21\2\u0104\u0106"+
		"\n\3\2\2\u0105\u0103\3\2\2\2\u0105\u0104\3\2\2\2\u0106\u0109\3\2\2\2\u0107"+
		"\u0105\3\2\2\2\u0107\u0108\3\2\2\2\u0108\u010a\3\2\2\2\u0109\u0107\3\2"+
		"\2\2\u010a\u010b\7\t\2\2\u010b!\3\2\2\2\u010c\u010d\7O\2\2\u010d#\3\2"+
		"\2\2\u010e\u0112\7N\2\2\u010f\u0112\5.\30\2\u0110\u0112\5\60\31\2\u0111"+
		"\u010e\3\2\2\2\u0111\u010f\3\2\2\2\u0111\u0110\3\2\2\2\u0112%\3\2\2\2"+
		"\u0113\u0114\7\27\2\2\u0114\u0115\5.\30\2\u0115\u0116\7\30\2\2\u0116\'"+
		"\3\2\2\2\u0117\u0119\t\4\2\2\u0118\u0117\3\2\2\2\u0118\u0119\3\2\2\2\u0119"+
		"\u011e\3\2\2\2\u011a\u011f\5h\65\2\u011b\u011f\5\60\31\2\u011c\u011f\5"+
		"\16\b\2\u011d\u011f\5&\24\2\u011e\u011a\3\2\2\2\u011e\u011b\3\2\2\2\u011e"+
		"\u011c\3\2\2\2\u011e\u011d\3\2\2\2\u011f)\3\2\2\2\u0120\u0123\5(\25\2"+
		"\u0121\u0122\7\33\2\2\u0122\u0124\5(\25\2\u0123\u0121\3\2\2\2\u0123\u0124"+
		"\3\2\2\2\u0124+\3\2\2\2\u0125\u012a\5*\26\2\u0126\u0127\t\5\2\2\u0127"+
		"\u0129\5*\26\2\u0128\u0126\3\2\2\2\u0129\u012c\3\2\2\2\u012a\u0128\3\2"+
		"\2\2\u012a\u012b\3\2\2\2\u012b-\3\2\2\2\u012c\u012a\3\2\2\2\u012d\u0132"+
		"\5,\27\2\u012e\u012f\t\4\2\2\u012f\u0131\5,\27\2\u0130\u012e\3\2\2\2\u0131"+
		"\u0134\3\2\2\2\u0132\u0130\3\2\2\2\u0132\u0133\3\2\2\2\u0133/\3\2\2\2"+
		"\u0134\u0132\3\2\2\2\u0135\u0136\7\5\2\2\u0136\u0137\5\"\22\2\u0137\61"+
		"\3\2\2\2\u0138\u0139\t\6\2\2\u0139\u013a\5.\30\2\u013a\63\3\2\2\2\u013b"+
		"\u013c\t\7\2\2\u013c\u013d\5.\30\2\u013d\65\3\2\2\2\u013e\u013f\t\b\2"+
		"\2\u013f\u0140\5.\30\2\u0140\67\3\2\2\2\u0141\u0142\t\t\2\2\u0142\u0143"+
		"\5.\30\2\u01439\3\2\2\2\u0144\u0145\t\n\2\2\u0145;\3\2\2\2\u0146\u0147"+
		"\t\13\2\2\u0147=\3\2\2\2\u0148\u0149\t\f\2\2\u0149?\3\2\2\2\u014a\u014b"+
		"\t\r\2\2\u014bA\3\2\2\2\u014c\u014d\t\16\2\2\u014dC\3\2\2\2\u014e\u014f"+
		"\7\65\2\2\u014fE\3\2\2\2\u0150\u0151\7\66\2\2\u0151G\3\2\2\2\u0152\u0153"+
		"\7\67\2\2\u0153\u0154\5.\30\2\u0154\u0155\5.\30\2\u0155I\3\2\2\2\u0156"+
		"\u0157\78\2\2\u0157\u0158\5.\30\2\u0158K\3\2\2\2\u0159\u015a\79\2\2\u015a"+
		"M\3\2\2\2\u015b\u015c\7:\2\2\u015cO\3\2\2\2\u015d\u015e\7;\2\2\u015eQ"+
		"\3\2\2\2\u015f\u0160\7<\2\2\u0160S\3\2\2\2\u0161\u0162\7=\2\2\u0162\u0163"+
		"\7\b\2\2\u0163\u0164\5\"\22\2\u0164\u0165\5.\30\2\u0165\u0167\5.\30\2"+
		"\u0166\u0168\5.\30\2\u0167\u0166\3\2\2\2\u0167\u0168\3\2\2\2\u0168\u0169"+
		"\3\2\2\2\u0169\u016a\7\t\2\2\u016a\u016b\5\22\n\2\u016bU\3\2\2\2\u016c"+
		"\u0175\t\17\2\2\u016d\u0176\5\"\22\2\u016e\u016f\5.\30\2\u016f\u0170\5"+
		".\30\2\u0170\u0172\5.\30\2\u0171\u0173\5.\30\2\u0172\u0171\3\2\2\2\u0172"+
		"\u0173\3\2\2\2\u0173\u0176\3\2\2\2\u0174\u0176\5Z.\2\u0175\u016d\3\2\2"+
		"\2\u0175\u016e\3\2\2\2\u0175\u0174\3\2\2\2\u0176W\3\2\2\2\u0177\u017e"+
		"\t\20\2\2\u0178\u017f\5\"\22\2\u0179\u017a\5.\30\2\u017a\u017b\5.\30\2"+
		"\u017b\u017c\5.\30\2\u017c\u017f\3\2\2\2\u017d\u017f\5Z.\2\u017e\u0178"+
		"\3\2\2\2\u017e\u0179\3\2\2\2\u017e\u017d\3\2\2\2\u017fY\3\2\2\2\u0180"+
		"\u0181\7B\2\2\u0181\u0182\7M\2\2\u0182[\3\2\2\2\u0183\u0184\7C\2\2\u0184"+
		"\u0185\5.\30\2\u0185]\3\2\2\2\u0186\u0187\t\21\2\2\u0187\u0188\5$\23\2"+
		"\u0188_\3\2\2\2\u0189\u018a\7G\2\2\u018a\u018b\5\"\22\2\u018ba\3\2\2\2"+
		"\u018c\u018d\7H\2\2\u018d\u018e\5.\30\2\u018ec\3\2\2\2\u018f\u0190\7I"+
		"\2\2\u0190\u0191\5f\64\2\u0191e\3\2\2\2\u0192\u0193\t\22\2\2\u0193g\3"+
		"\2\2\2\u0194\u0195\7P\2\2\u0195i\3\2\2\2\u0196\u0197\7Q\2\2\u0197k\3\2"+
		"\2\2\"nsvx}\u0080\u0085\u0089\u00a5\u00ab\u00b3\u00b7\u00ba\u00bf\u00c9"+
		"\u00d1\u00db\u00e2\u00e7\u0100\u0105\u0107\u0111\u0118\u011e\u0123\u012a"+
		"\u0132\u0167\u0172\u0175\u017e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}