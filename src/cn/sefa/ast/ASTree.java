package cn.sefa.ast;

import java.util.Iterator;

import cn.sefa.symbol.IEnvironment;
import cn.sefa.symbol.Symbols;
import cn.sefa.vm.Code;

/**
 * @author Lionel
 *
 */
public abstract class ASTree implements Iterable<ASTree> {

	//���ص�i���ӽ��
	public abstract ASTree child(int i);
	//�����ӽ�����Ŀ
	public abstract int numOfChildren();
	//����һ�����ڱ����ĵ����� 
	public abstract Iterator<ASTree>getChildren();
	public abstract String location();
	public abstract Object eval(IEnvironment env);
	public abstract void lookup(Symbols sym	);
	public abstract void compile(Code c);
	public abstract void setBegin(Code c ,int pos) ;
	public abstract void setEnd(Code c ,int pos);
}
