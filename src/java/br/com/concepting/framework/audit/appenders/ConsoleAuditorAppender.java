package br.com.concepting.framework.audit.appenders;

import java.io.PrintWriter;

import br.com.concepting.framework.audit.Auditor;

/**
 * Classe que define o mecanismo que armazena as mensagens em um arquivo.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ConsoleAuditorAppender extends BaseAuditorAppender{
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param auditor Instância da classe responsável por efetuar a auditoria.
     */
	public ConsoleAuditorAppender(Auditor auditor){
        super(auditor);
    }

    /**
	 * @see org.apache.log4j.spi.OptionHandler#activateOptions()
	 */
	public void activateOptions(){
		setWriter(new PrintWriter(System.out));
	}
}