package cn.sefa.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Test;

import cn.sefa.ast.ASTree;
import cn.sefa.exception.ParseException;
import cn.sefa.lexer.Lexer;
import cn.sefa.lexer.Token;
import cn.sefa.parse.BasicParser;
import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.NestedEnv;

/**
 * @author Lionel
 *
 */
public class ClosureTest {
	
	@Test
	public void test1() throws ParseException, FileNotFoundException{
		Lexer lexer = getLexer("closure1.sf");
		runTest(lexer);
	}
	
	
	private void runTest(Lexer lexer) throws ParseException {
		BasicParser bp = new BasicParser();
		IEnvironment env = new NestedEnv();
		while(lexer.peek(0)!=Token.EOF){
			if(lexer.peek(0)==null){
				System.out.println("null...");
			}
			ASTree t =bp.parse(lexer);
			System.out.println("->>"+t.eval(env));
		}
	}
	
	
	private Lexer getLexer(String name) throws FileNotFoundException {
		File file = new File("src/cn/sefa/test/testFile/"+name);
		Reader reader = new InputStreamReader(new FileInputStream(file));
		return new Lexer(reader);
	}
}
