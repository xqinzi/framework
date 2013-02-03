package br.com.concepting.framework.audit.model;

import java.util.Collection;
import java.util.Date;

import br.com.concepting.framework.audit.types.AuditorStatusType;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.persistence.types.RelationType;

/**
 * Classe que define o modelo de dados que armazena o registro de auditoria.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model
public class AuditorModel extends BaseModel{
	@Property(isIdentity=true)
	private Long id = 0l;

	@Property(isForSearch=true, isTime=true)
	private Date date = null;
	
    @Property
    private SystemSessionModel systemSession = null;
    
    @Property(relationType=RelationType.ONE_TO_ONE)
    private SystemModuleModel systemModule = null;
	
	@Property(isForSearch=true)
	private String userName = "";

	@Property(isForSearch=true)
	private String entityId = "";

	@Property(isForSearch=true)
	private String businessId = "";

    @Property(relationType=RelationType.ONE_TO_MANY)
    private Collection<AuditorBusinessComplementModel> businessComplement = null;

    @Property
	private String statusCode = "";
	
    @Property
    private AuditorStatusType statusCodeType = null;

    @Property
	private String statusMessage = "";

    /**
     * Retorna a instância do modelo de dados que armazena as informações do sistema 
     * utilizado.
     * 
     * @return Instância do modelo de dados do sistema.
     */
    public <S extends SystemModuleModel> S getSystemModule(){
	    return (S)systemModule;
	}

    /**
     * Define a instância do modelo de dados que armazena as informações do sistema 
     * utilizado.
     * 
     * @param systemModule Instância do modelo de dados do sistema.
     */
    public <S extends SystemModuleModel> void setSystemModule(S systemModule){
        this.systemModule = systemModule;
    }
    
    /**
     * Retorna a instância da sessão do sistema.
     * 
     * @return Instância contendo as propriedades da sessão do sistema.
     */
    public <S extends SystemSessionModel> S getSystemSession(){
        return (S)systemSession;
    }

    /**
     * Define a instância da sessão do sistema.
     * 
     * @param systemSession Instância contendo as propriedades da sessão do sistema.
     */
    public <S extends SystemSessionModel> void setSystemSession(S systemSession){
        this.systemSession = systemSession;
    }

    /**
     * Retorna o nome do usuário da sessão do sistema.
     * 
     * @return String contendo o nome do usuário.
     */
    public String getUserName(){
        return userName;
    }

    /**
     * Define o nome do usuário da sessão do sistema.
     * 
     * @param userName String contendo o nome do usuário.
     */
    public void setUserName(String userName){
        this.userName = userName;
    }

    /**
     * Retorna a mensagem de status do registro de auditoria.
     * 
     * @return String contendo a mensagem de status.
     */
    public String getStatusMessage(){
        return statusMessage;
    }

    /**
     * Define a mensagem de status do registro de auditoria.
     * 
     * @param statusMessage String contendo a mensagem de status.
     */
    public void setStatusMessage(String statusMessage){
        this.statusMessage = statusMessage;
    }

	/**
	 * Retorna o identificador da regra de negócio a ser auditada.
	 * 
	 * @return String contendo o identificador da regra de negócio.
	 */
	public String getBusinessId(){
		return businessId;
	}

	/**
	 * Define o identificador da regra de negócio a ser auditada.
	 * 
	 * @param businessId String contendo o identificador da regra de negócio.
	 */
	public void setBusinessId(String businessId){
		this.businessId = businessId;
	}

	/**
	 * Retorna a instância das informações complementares da regra de negócio a ser 
	 * auditada.
	 *
	 * @return Instância contendo as informações complementares.
	 */
    public <C extends AuditorBusinessComplementModel> Collection<C> getBusinessComplement(){
		return (Collection<C>)businessComplement;
	}

	/**
	 * Define a instância das informações complementares da regra de negócio a ser 
	 * auditada.
	 *
	 * @param businessComplement Instância contendo as informações complementares.
	 */
    public <C extends AuditorBusinessComplementModel> void setBusinessComplement(Collection<C> businessComplement){
		this.businessComplement = (Collection)businessComplement;
	}

	/**
	 * Retorna a data/horário do registro de auditoria.
	 * 
	 * @return Instância contendo a data/horário do registro de auditoria.
	 */
	public Date getDate(){
		return date;
	}

	/**
	 * Define a data/horário do registro de auditoria.
	 * 
	 * @param date Instância contendo a data/horário do registro de auditoria.
	 */
	public void setDate(Date date){
		this.date = date;
	}

	/**
	 * Retorna o identificador da entidade a ser auditada.
	 * 
	 * @return String contendo o identificador da entidade.
	 */
	public String getEntityId(){
		return entityId;
	}

	/**
	 * Define o identificador da entidade a ser auditada.
	 * 
	 * @param entityId String contendo o identificador da entidade.
	 */
	public void setEntityId(String entityId){
		this.entityId = entityId;
	}

	/**
	 * Retorna o identificador do registro de auditoria.
	 * 
	 * @return Instância contendo o identificador do registro de auditoria.
	 */
	public Long getId(){
		return id;
	}

	/**
	 * Define o identificador do registro de auditoria.
	 * 
	 * @param id Instância contendo o identificador do registro de auditoria.
	 */
	public void setId(Long id){
		this.id = id;
	}

	/**
	 * Retorna o identificador do status de processamento a ser registrado na 
	 * auditoria.
	 * 
	 * @return String contendo o identificador do status.
	 */
	public String getStatusCode(){
		return statusCode;
	}

    /**
     * Define o identificador do status de processamento a ser registrado na 
     * auditoria.
     * 
     * @param statusCode String contendo o identificador do status.
     */
	public void setStatusCode(String statusCode){
		this.statusCode = statusCode;

		try{
			this.statusCodeType = AuditorStatusType.toAuditorStatusType(statusCode);
		}
		catch(Throwable e){
			this.statusCodeType = null;
		}
	}

	/**
	 * Retorna a constante do status de processamento a ser registrado na auditoria.
	 * 
	 * @return Instância da constante do status.
	 */
	public AuditorStatusType getStatusCodeType(){
		return statusCodeType;
	}

    /**
     * Define a constante do status de processamento a ser registrado na auditoria.
     * 
     * @param statusCodeType Instância da constante do status.
     */
	public void setStatusCodeType(AuditorStatusType statusCodeType){
		this.statusCodeType = statusCodeType;

		if(statusCodeType == null)
			this.statusCode = null;
		else
			this.statusCode = statusCodeType.toString();
	}
}