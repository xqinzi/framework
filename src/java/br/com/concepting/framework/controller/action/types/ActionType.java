package br.com.concepting.framework.controller.action.types;

import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que define as constantes para ac�es gen�ricas.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ActionType{
	/**
	 * Constante que define a a��o de inicializa��o do formul�rio.
	 */
	INIT,

	/**
	 * Constante que define a a��o de atualiza��o do formul�rio.
	 */
	REFRESH,

	/**
	 * Constante que define a a��o de impress�o.
	 */
	PRINT,

	/**
	 * Constante que define a a��o de inclus�o de um novo item.
	 */
	ADD,

	/**
	 * Constante que define a a��o de edi��o de um item.
	 */
	EDIT,

	/**
	 * Constante que define a a��o de grava��o dos dados do formul�rio.
	 */
	SAVE,

	/**
	 * Constante que define a a��o de cancelamento.
	 */
	CANCEL,

	/**
	 * Constante que define a a��o de pesquisa de itens.
	 */
	SEARCH,

	/**
	 * Constante que define a a��o de inclus�o de dados.
	 */
	INSERT,

	/**
	 * Constante que define a a��o de altera��o de dados.
	 */
	UPDATE,

	/**
	 * Constante que define a a��o de exclus�o de dados.
	 */
	DELETE,
	
	/**
	 * Constante que define a a��o para upload de arquivo.
	 */
	UPLOAD,
	
	/**
	 * Constante que define a a��o para mudan�a do tema.
	 */
	CHANGE_CURRENT_SKIN,
	
    /**
     * Constante que define a a��o para mudan�a do idioma.
     */
	CHANGE_CURRENT_LANGUAGE;
	
	/**
	 * Retorna o identificador do m�todo da a��o.
	 * 
	 * @return String contendo o identificador do m�todo.
	 */
    public String getMethod(){
        String        parts[]  = StringUtil.split(toString(), "_");
        StringBuilder methodId = new StringBuilder();
        
        for(int cont = 0 ; cont < parts.length ; cont++){
            if(cont == 0)
                methodId.append(parts[cont].toLowerCase());
            else
                methodId.append(StringUtil.capitalize(parts[cont]));
        }
        
        return methodId.toString();
    }
}