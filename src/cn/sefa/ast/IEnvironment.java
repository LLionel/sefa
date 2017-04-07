package cn.sefa.ast;

/**
 * @author Lionel
 *
 */
public interface IEnvironment {

	void put(String name , Object obj);
	void putInCrtEnv(String name ,Object obj) ;
	Object get(String name);
	IEnvironment where(String name) ;
	void setOuter(IEnvironment env);
	IEnvironment getOuter();
	
}
