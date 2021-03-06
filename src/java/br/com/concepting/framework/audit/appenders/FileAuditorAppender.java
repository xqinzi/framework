package br.com.concepting.framework.audit.appenders;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import br.com.concepting.framework.audit.Auditor;

/**
 * Classe que define o mecanismo para armazenamento das mensagens de auditoria em 
 * um arquivo.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class FileAuditorAppender extends BaseAuditorAppender{
    private String fileName = "";
    
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param auditor Instância da classe responsável por efetuar a auditoria.
     */
	public FileAuditorAppender(Auditor auditor){
        super(auditor);
    }

	/**
	 * Retorna o nome do arquivo de armazenamento.
	 * 
	 * @return String contendo o nome do arquivo.
	 */
    public String getFileName(){
        return fileName;
    }

    /**
     * Define o nome do arquivo de armazenamento.
     * 
     * @param fileName String contendo o nome do arquivo.
     */
    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    /**
	 * @see org.apache.log4j.spi.OptionHandler#activateOptions()
	 */
	public void activateOptions(){
		try{
            setWriter(new PrintWriter(new FileOutputStream(new File(fileName), true)));
        }
        catch(Throwable e){
        }
	}
}