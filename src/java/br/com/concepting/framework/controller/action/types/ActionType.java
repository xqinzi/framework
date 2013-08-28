package br.com.concepting.framework.controller.action.types;

import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que define as constantes para acões genéricas.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ActionType{
	/**
	 * Constante que define a ação de inicialização do formulário.
	 */
	INIT,

	/**
	 * Constante que define a ação de atualização do formulário.
	 */
	REFRESH,

	/**
	 * Constante que define a ação de impressão.
	 */
	PRINT,

	/**
	 * Constante que define a ação de inclusão de um novo item.
	 */
	ADD,

	/**
	 * Constante que define a ação de edição de um item.
	 */
	EDIT,

	/**
	 * Constante que define a ação de gravação dos dados do formulário.
	 */
	SAVE,

	/**
	 * Constante que define a ação de cancelamento.
	 */
	CANCEL,

	/**
	 * Constante que define a ação de pesquisa de itens.
	 */
	SEARCH,

	/**
	 * Constante que define a ação de inclusão de dados.
	 */
	INSERT,

	/**
	 * Constante que define a ação de alteração de dados.
	 */
	UPDATE,

	/**
	 * Constante que define a ação de exclusão de dados.
	 */
	DELETE,
	
	/**
	 * Constante que define a ação para upload de arquivo.
	 */
	UPLOAD,
	
	/**
	 * Constante que define a ação para mudança do tema.
	 */
	CHANGE_CURRENT_SKIN,
	
    /**
     * Constante que define a ação para mudança do idioma.
     */
	CHANGE_CURRENT_LANGUAGE;
	
	/**
	 * Retorna o identificador do método da ação.
	 * 
	 * @return String contendo o identificador do método.
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