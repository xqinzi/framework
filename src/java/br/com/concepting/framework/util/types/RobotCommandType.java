package br.com.concepting.framework.util.types;

/**
 * Classe que define as constantes para os tipos de comando do robô.
 * 
 * @author fvilarinho
 * @since 2.0
 */
public enum RobotCommandType{
	/**
	 * Constante que define o comando "mouseMove" (Movimento do mouse).
	 */
	MOUSE_MOVE("mouseMove", new Class[]{int.class, int.class}),
	
	/**
	 * Constante que define o comando "mousePress" (Clique do mouse).
	 */
	MOUSE_PRESS("mousePress", new Class[]{int.class}), 

	/**
	 * Constante que define o comando "mouseRelease" (Clique do mouse).
	 */
	MOUSE_RELEASE("mouseRelease", new Class[]{int.class}), 

	/**
	 * Constante que define o comando "mouseWheel" (Rolagem do mouse).
	 */
	MOUSE_WHEEL("mouseWheel", new Class[]{int.class}), 

	/**
	 * Constante que define o comando "keyRelease" (Pressionar uma tecla).
	 */
	KEY_PRESS("keyPress", new Class[]{int.class}), 

	/**
	 * Constante que define o comando "keyRelease" (Soltar uma tecla).
	 */
	KEY_RELEASE("keyRelease", new Class[]{int.class}), 

	/**
	 * Constante que define o comando "execute" (Abre um processo/aplicativo).
	 */
	EXECUTE("execute", new Class[]{String.class, int.class, int.class}),

	/**
	 * Constante que define o comando "destroy" (Fecha um processo/aplicativo em execução).
	 */
	DESTROY("destroy", new Class[]{String.class}),

	/**
	 * Constante que define o comando "setTimeout" (Tempo máximo para processamento).
	 */
	SET_TIMEOUT("setTimeout", new Class[]{long.class}),

	/**
	 * Constante que define o comando "delay" (Espera de processamento).
	 */
	DELAY("delay", new Class[]{int.class}), 

	/**
	 * Constante que define o comando "type" (Digitação).
	 */
	TYPE("type", new Class[]{String.class, int.class, int.class}), 

	/**
	 * Constante que define o comando "mouseClick" (Clique do mouse).
	 */
	MOUSE_CLICK("mouseClick", new Class[]{int.class, int.class}), 

	/**
	 * Constante que define o comando "mouseRightClick" (Clique direito do mouse).
	 */
	MOUSE_RIGHT_CLICK("mouseRightClick", new Class[]{int.class, int.class});
	
	private String method;
	private Class  argumentsTypes[];
	
	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param method String contendo o tipo de comando.
	 * @param argumentsTypes Array contendo os tipos de argumentos do tipo de comando.
	 */
	private RobotCommandType(String method, Class argumentsTypes[]){
		setMethod(method);
		setArgumentsTypes(argumentsTypes);
	}
	
	/**
	 * Retorna um array contendo os tipos de argumentos do tipo de comando.
	 *  
	 * @return Array contendo os tipos de argumentos do tipo de comando.
	 */
	public Class[] getArgumentsTypes(){
		return argumentsTypes;
	}
	
	/**
	 * Define um array contendo os tipos de argumentos do tipo de comando.
	 *  
	 * @param argumentsTypes Array contendo os tipos de argumentos do tipo de comando.
	 */
	public void setArgumentsTypes(Class[] argumentsTypes){
		this.argumentsTypes = argumentsTypes;
	}
	
	/**
	 * Retorna o tipo de comando.
	 * 
	 * @return String contendo o tipo de comando.
	 */
	public String getMethod(){
		return method;
	}
	
	/**
	 * Define o tipo de comando.
	 * 
	 * @param method String contendo o tipo de comando.
	 */
	public void setMethod(String method){
		this.method = method;
	}
}