/**
 * 
 */
package cn.sefa.lexer;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sefa.exception.ParseException;

/**
 * @author Lionel
 *
 */
public class Lexer {

	public static String integerPat = "([0-9]+)";	//ƥ�����ͳ���
	/*
	 * ����\\\\�Ľ���˵��
	 * java���ַ�����������\Ϊת���ַ���������Ҫ\\����ת��Ϊ������\��
	 * ����������Ҫ��������ʽ�ڲ�ƥ��\����������ʽ��\Ҳ������ת�������ַ��ģ�����Ҳ��Ҫ\\��ƥ�䣬
	 * ��������������Ҫ\\\\��ƥ��\
	 */
	public static String blaPat = "\\s*" ;
	public static String strPat = "(\"(\\\\\"|\\\\\\\\|\\n|[^\"])*\")" ;		//ƥ���ַ��������� 
	public static String idePat = "([a-z_A-Z][a-z_A-Z0-9]*";//ƥ���ʶ��
	public static String operPat = "==|<=|>=|&&|\\|\\||\\p{Punct})" ;	//ƥ������� \p{Punct} �����ţ�!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~ 
	public static String regexPat = blaPat+"((//.*)|"+integerPat+"|"
			+strPat+"|"+idePat+"|"+operPat+")?";
	
	private Pattern pattern = Pattern.compile(regexPat);
	private ArrayList<Token> tokenList =  new ArrayList<Token>();
	private boolean hasMore;
	private LineNumberReader reader;
	
	public Lexer(Reader r){
		hasMore = true ;
		reader = new LineNumberReader(r) ;
	}
	//�ӻ��������Ƴ�һ��token���������ش�token
	public Token read() throws ParseException{
		
		if(isTokenEnough(0)){
			return tokenList.remove(0);
		}
		
		return Token.EOF;
	}
	//�ӻ�������Ԥȡ��һ��token�������Ƴ���
	public Token peek(int i) throws ParseException{
		if(isTokenEnough(i)){
			return tokenList.get(i);
		}
		
		return Token.EOF;
	}
	
	private boolean isTokenEnough(int i) throws ParseException {
		
		while(i>=tokenList.size()){
			if(hasMore)
				readLine();
			else
				return false ;
		}
		return true ;
	}

	protected void readLine() throws ParseException{
		String line ;
		try {
			line = reader.readLine();
		} catch (IOException e) {

			throw new ParseException(e) ;
		}
		if(line == null){
			hasMore = false ;
			return ;
		}
		int lineNo = reader.getLineNumber();
		Matcher matcher = pattern.matcher(line);
		/*
		 * useTransparentBounds(true)�ǽ��߽�͸�������գ�
		 * http://guoruisheng-163-com.iteye.com/blog/1593604
		 * 
		 * useAnchoringBounds:���ƥ�䷶Χ����������Ŀ���ַ����������趨�Ƿ�ƥ�䷶Χ�ı߽�����Ϊ"�ı���ʼλ��"�͡��ı�����λ�á���
		 * ���Ӱ���ı��б߽�Ԫ�ַ�(\A ^ $ \z \Z)�������־λĬ��Ϊtrue�����գ�
		 * http://blog.csdn.net/claram/article/details/52875925
		 * 
		 */
		matcher.useTransparentBounds(true).useAnchoringBounds(false);
		int pos = 0 , endPos = line.length();
//		boolean isComment = true ;
		while(pos<endPos){
			matcher.region(pos, endPos);
			if(matcher.lookingAt()){
//				isComment = addToken(lineNo,matcher);
				addToken(lineNo,matcher);
				pos = matcher.end();
			}
			else{
				throw new ParseException("bad token at line "+lineNo);
			}
		}
		tokenList.add(new IdToken(Token.EOL,lineNo));
		/*if(!isComment)
			tokenList.add(new IdToken(Token.EOL,lineNo));*/
	}
	
	protected void addToken(int lineNo, Matcher matcher) {
		
		if(matcher.group(1)!=null){	//���ƥ��Ĳ��ǿհ��ַ�,ƥ����ǵڶ���������
			if(matcher.group(2) == null){	//���ƥ�����ע�Ͳ�����������ע�͵Ļ�������������
				Token t = null ;
				if(matcher.group(3) != null){	//���ƥ���������
					String strNum = matcher.group(3);
					t = new NumToken(Integer.parseInt(strNum),lineNo);
				}
				else if(matcher.group(4)!=null){	//if matcher the literal of string
					t = new  StrToken(toLiteral(matcher.group(4)), lineNo);
				}
				/*else if(matcher.group(5)!=null){	//if matcher the Identifier
					t = new IdToken(matcher.group(5),lineNo);
				}*/
				else {
					t = new IdToken(matcher.group(1),lineNo);
				}
				
				tokenList.add(t);
			}
			/*//�����ע�͵Ļ������ѵ�����
			else
				return false;*/
			
		}
//		return true;
		
	}
	/*
	 * �����ַ����������е�ת���ַ�
	 */
	protected String toLiteral(String s){
		
		StringBuilder sb = new StringBuilder()	;
		int len = s.length()-1;
		for(int i = 1 ; i < len ;++i){
			char c1 = s.charAt(i) ;
			if(c1 == '\\' && i+1<len){
				char c2 = s.charAt(i+1);
				if(c2 == '\\' || c2 == '"'){
					c1 = c2 ;
					i++;
				}
				else if(c2 == 'n'){
					c1 = '\n';
					i++;
				}
			}
			sb.append(c1);
			
		}
		
		return sb.toString() ;
	}
	
}




