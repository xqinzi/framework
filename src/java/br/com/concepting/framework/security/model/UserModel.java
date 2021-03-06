package br.com.concepting.framework.security.model;

import java.util.Collection;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.ObjectModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ContextSearchType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.util.helpers.DateTime;

/**
 * Classe que define o modelo de dados de um usu�rio.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model
public class UserModel extends BaseModel{
    private static final long serialVersionUID = -5952606948187054532L;

    @Property(isIdentity = true, isForSearch = true)
	private Integer id = 0;

	@Property(isForSearch = true, validations = ValidationType.REQUIRED, isAuditable=true)
	private String name = "";

	@Property(isForSearch = true, searchCondition = ConditionType.CONTEXT, contextSearchType = ContextSearchType.BOTH, caseSensitiveSearch = false)
	private String fullName = "";

	@Property
	private String password = "";

	@Property
	private String newPassword = "";

	@Property
	private String confirmPassword = "";

	@Property(isForSearch = true)
	private Boolean active = true;

    @Property
    private Boolean system = true;

    @Property(isForSearch=true)
	private String email = "";

	@Property
	private DateTime createDate = new DateTime();

	@Property
	private DateTime lastUpdateDate = null;

	@Property
	private Boolean changePassword = false;

	@Property
	private DateTime expirePasswordDate = null;

	@Property
	private Boolean superUser = false;
	
    @Property
    private byte logo[] = null;
    
    @Property
    private String logoFileName = "";
    
    @Property
    private String logoContentType = "";

	@Property(relationType = RelationType.ONE_TO_ONE, relationJoinType = RelationJoinType.LEFT_JOIN, cascadeOnDelete = true, cascadeOnSave = true)
	private LoginParametersModel loginParameters = null;

	@Property(relationType = RelationType.MANY_TO_MANY)
	private Collection<? extends GroupModel> groups = null;
	
	public Boolean getSystem(){
        return system;
    }

    public void setSystem(Boolean system){
        this.system = system;
    }

    /**
	 * Retorna o nome do arquivo do logo.
	 * 
	 * @return String contendo o nome do arquivo do logo.
	 */
    public String getLogoFileName(){
        return logoFileName;
    }

    /**
     * Define o nome do arquivo do logo.
     * 
     * @param logoFileName String contendo o nome do arquivo do logo.
     */
    public void setLogoFileName(String logoFileName){
        this.logoFileName = logoFileName;
    }

    /**
     * Retorna o formato do logo.
     * 
     * @return String contendo o formato do logo.
     */
    public String getLogoContentType(){
        return logoContentType;
    }

    /**
     * Define o formato do logo.
     * 
     * @param logoContentType String contendo o formato do logo.
     */
    public void setLogoContentType(String logoContentType){
        this.logoContentType = logoContentType;
    }

    /**
     * Retorna um array de bytes contendo o logo.
     * 
     * @return Array de bytes contendo o logo.
     */
    public byte[] getLogo(){
        return logo;
    }

    /**
     * Define um array de bytes contendo o logo.
     * 
     * @param logo Array de bytes contendo o logo.
     */
    public void setLogo(byte[] logo){
        this.logo = logo;
    }

    /**
	 * Indica se o usu�rio est� ativo.
	 * 
	 * @return True/False.
	 */
	public Boolean isActive(){
		return active;
	}

	/**
	 * Indica se o usu�rio est� ativo.
	 * 
	 * @return True/False.
	 */
	public Boolean getActive(){
		return isActive();
	}

	/**
	 * Define se o usu�rio est� ativo.
	 * 
	 * @param active True/False.
	 */
	public void setActive(Boolean active){
		this.active = active;
	}

	/**
	 * Retorna a lista de grupos do usu�rio.
	 * 
	 * @return Inst�ncia contendo a lista de grupos.
	 */
    @SuppressWarnings("unchecked")
    public <G extends GroupModel, C extends Collection<G>> C getGroups(){
		return (C)groups;
	}

	/**
	 * Define a lista de grupos do usu�rio.
	 * 
	 * @param groups Inst�ncia contendo a lista de grupos.
	 */
    public void setGroups(Collection<GroupModel> groups){
		this.groups = groups;
	}

	/**
	 * Retorna o identificador do usu�rio.
	 * 
	 * @return Valor inteiro contendo o identificador do usu�rio.
	 */
	public Integer getId(){
		return id;
	}

	/**
	 * Define o identificador do usu�rio.
	 * 
	 * @param id Valor inteiro contendo o identificador do usu�rio.
	 */
	public void setId(Integer id){
		this.id = id;
	}

	/**
	 * Retorna o nome do usu�rio.
	 * 
	 * @return String contendo o nome do usu�rio.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Define o nome do usu�rio.
	 * 
	 * @param name String contendo o nome do usu�rio.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Retorna a senha do usu�rio.
	 * 
	 * @return String contendo a senha do usu�rio.
	 */
	public String getPassword(){
		return password;
	}

	/**
	 * Define a senha do usu�rio.
	 * 
	 * @param password String contendo a senha do usu�rio.
	 */
	public void setPassword(String password){
		this.password = password;
	}

	/**
	 * Retorna o e-Mail do usu�rio.
	 * 
	 * @return String contendo o e-Mail do usu�rio.
	 */
	public String getEmail(){
		return email;
	}

	/**
	 * Define o e-Mail do usu�rio.
	 * 
	 * @param email String contendo o e-Mail do usu�rio.
	 */
	public void setEmail(String email){
		this.email = email;
	}

	/**
	 * Indica se usu�rio deve trocar a senha no pr�ximo login.
	 * 
	 * @return True/False.
	 */
	public Boolean changePassword(){
		return changePassword;
	}

	/**
	 * Indica se usu�rio deve trocar a senha no pr�ximo login.
	 * 
	 * @return True/False.
	 */
	public Boolean getChangePassword(){
		return changePassword();
	}

	/**
	 * Define se usu�rio deve trocar a senha no pr�ximo login.
	 * 
	 * @param changePassword True/False.
	 */
	public void setChangePassword(Boolean changePassword){
		this.changePassword = changePassword;
	}

	/**
	 * Retorna a data/hor�rio de cria��o do usu�rio.
	 * 
	 * @return Inst�ncia contendo a data/hor�rio de cria��o do usu�rio.
	 */
	public DateTime getCreateDate(){
		return createDate;
	}

	/**
	 * Define a data/hor�rio de cria��o do usu�rio.
	 * 
	 * @param createDate Inst�ncia contendo a data/hor�rio de cria��o do usu�rio.
	 */
	public void setCreateDate(DateTime createDate){
		this.createDate = createDate;
	}

	/**
	 * Retorna a data/hor�rio de expira��o da senha do usu�rio.
	 * 
	 * @return Inst�ncia contendo a data/hor�rio de expira��o da senha do usu�rio.
	 */
	public DateTime getExpirePasswordDate(){
		return expirePasswordDate;
	}

	/**
	 * Define a data/hor�rio de expira��o da senha do usu�rio.
	 * 
	 * @param expirePasswordDate Inst�ncia contendo a data/hor�rio de expira��o da senha do usu�rio.
	 */
	public void setExpirePasswordDate(DateTime expirePasswordDate){
		this.expirePasswordDate = expirePasswordDate;
	}

	/**
	 * Retorna a data/hor�rio da �ltima atualiza��o dos dados do usu�rio.
	 * 
	 * @return Inst�ncia contendo a data/hor�rio da �ltima atualiza��o dos dados do usu�rio.
	 */
	public DateTime getLastUpdateDate(){
		return lastUpdateDate;
	}

	/**
	 * Define a data/hor�rio da �ltima atualiza��o dos dados do usu�rio.
	 * 
	 * @param lastUpdateDate Inst�ncia contendo a data/hor�rio da �ltima atualiza��o dos dados do 
	 * usu�rio.
	 */
	public void setLastUpdateDate(DateTime lastUpdateDate){
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * Retorna o nome completo do usu�rio.
	 * 
	 * @return String contendo o nome completo do usu�rio.
	 */
	public String getFullName(){
		return fullName;
	}

	/**
	 * Define o nome completo do usu�rio.
	 * 
	 * @param fullName String contendo o nome completo do usu�rio.
	 */
	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	/**
	 * Indica se � um superusu�rio.
	 * 
	 * @return True/False.
	 */
	public Boolean isSuperUser(){
		return superUser;
	}

	/**
	 * Indica se � um superusu�rio.
	 * 
	 * @return True/False.
	 */
	public Boolean getSuperUser(){
		return isSuperUser();
	}

	/**
	 * Define se � um superusu�rio.
	 * 
	 * @param superUser True/False.
	 */
	public void setSuperUser(Boolean superUser){
		this.superUser = superUser;
	}

	/**
	 * Retorna a senha de confirma��o do usu�rio.
	 * 
	 * @return String contendo a senha de confirma��o do usu�rio.
	 */
	public String getConfirmPassword(){
		return confirmPassword;
	}

	/**
	 * Define a senha de confirma��o do usu�rio.
	 * 
	 * @param confirmPassword String contendo a senha de confirma��o do usu�rio.
	 */
	public void setConfirmPassword(String confirmPassword){
		this.confirmPassword = confirmPassword;
	}

	/**
	 * Retorna a nova senha do usu�rio.
	 * 
	 * @return String contendo a nova senha do usu�rio.
	 */
	public String getNewPassword(){
		return newPassword;
	}

	/**
	 * Define a nova senha do usu�rio.
	 * 
	 * @param newPassword String contendo a nova senha do usu�rio.
	 */
	public void setNewPassword(String newPassword){
		this.newPassword = newPassword;
	}
	
	/**
	 * Retorna os par�metros de login do usu�rio (Ex: Data de expira��o de senha, etc).
	 * 
	 * @return Inst�ncia contendo os par�metros de login.
	 */
	@SuppressWarnings("unchecked")
    public <LP extends LoginParametersModel> LP getLoginParameters(){
    	return (LP)loginParameters;
    }

	/**
	 * Define os par�metros de login do usu�rio (Ex: Data de expira��o de senha, etc).
	 * 
	 * @param loginParameters Inst�ncia contendo os par�metros de login.
	 */
	public <LP extends LoginParametersModel> void setLoginParameters(LP loginParameters){
    	this.loginParameters = loginParameters;
    }

	/**
	 * Indica se o usu�rio possui permiss�es.
	 * 
	 * @return True/False.
	 */
	public Boolean hasPermissions(){
		if(groups != null && groups.size() > 0 && !changePassword()){
			GroupModel groupItem = null;

			for(Object item : groups){
				groupItem = (GroupModel)item;

				if(groupItem.hasPermissions())
					return true;
			}
		}

		return false;
	}

	/**
	 * Verifica se o grupo possui permiss�es para um m�dulo do sistema
	 * espec�fico.
	 * 
	 * @param compareSystemModule Inst�ncia contendo as propriedade do m�dulo do sistema.
	 * @return True/False.
	 */
	public Boolean hasPermission(SystemModuleModel compareSystemModule){
		if(groups != null && groups.size() > 0 && !changePassword()){
			GroupModel groupItem = null;

			for(Object item : groups){
				groupItem = (GroupModel)item;

				if(groupItem.hasPermission(compareSystemModule))
					return true;
			}
		}

		return false;
	}

	/**
	 * Verifica se o grupo possui permiss�es para um objeto espec�fico.
	 * 
	 * @param compareObject Inst�ncia contendo as propriedade do objeto.
	 * @return True/False.
	 */
	public Boolean hasPermission(ObjectModel compareObject){
		if(groups != null && groups.size() > 0){
			GroupModel groupItem = null;

			for(Object item : groups){
				groupItem = (GroupModel)item;

				if(groupItem.hasPermission(compareObject))
					return true;
			}
		}

		return false;
	}
}