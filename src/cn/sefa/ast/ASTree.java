package cn.sefa.ast;

import java.util.Iterator;

/**
 * @author Lionel
 *
 */
public abstract class ASTree{

	//���ص�i���ӽ��
	public abstract ASTree child(int i);
	//�����ӽ�����Ŀ
	public abstract int numOfChildren();
	//����һ�����ڱ����ĵ����� 
	public abstract Iterator<ASTree>getChildren();
	public abstract String location();
	public abstract Object eval(IEnvironment env);

}
