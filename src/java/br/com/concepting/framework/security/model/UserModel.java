package br.com.concepting.framework.security.model;

import java.util.Date;
import java.util.List;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.ObjectModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ContextSearchType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.persistence.types.RelationType;

/**
 * Classe que define o modelo de dados de um usuário.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model
public class UserModel extends BaseModel{
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

	@Property
	private Boolean active = true;

	@Property(isForSearch=true)
	private String email = "";

	@Property
	private Date createDate = new Date();

	@Property
	private Date lastUpdateDate = null;

	@Property
	private Boolean changePassword = false;

	@Property
	private Date expirePasswordDate = null;

	@Property
	private Boolean superUser = false;
	
    @Property
    private byte logo[] = null;
    
    @Property
    private String logoFileName = "";
    
    @Property
    private String logoContentType = "";

	@Property
	private LoginParametersModel loginParameters = null;

	@Property(relationType = RelationType.MANY_TO_MANY)
	private List<GroupModel> groups = null;

    public String getLogoFileName(){
        return logoFileName;
    }

    public void setLogoFileName(String logoFileName){
        this.logoFileName = logoFileName;
    }

    public String getLogoContentType(){
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType){
        this.logoContentType = logoContentType;
    }

    public byte[] getLogo(){
        return logo;
    }

    public void setLogo(byte[] logo){
        this.logo = logo;
    }

    /**
	 * Indica se o usuário está ativo.
	 * 
	 * @return True/False.
	 */
	public Boolean isActive(){
		return active;
	}

	/**
	 * Indica se o usuário está ativo.
	 * 
	 * @return True/False.
	 */
	public Boolean getActive(){
		return isActive();
	}

	/**
	 * Define se o usuário está ativo.
	 * 
	 * @param active True/False.
	 */
	public void setActive(Boolean active){
		this.active = active;
	}

	/**
	 * Retorna a lista de grupos do usuário.
	 * 
	 * @return Instância contendo a lista de grupos.
	 */
    public <G extends GroupModel> List<G> getGroups(){
		return (List<G>)groups;
	}

	/**
	 * Define a lista de grupos do usuário.
	 * 
	 * @param groups Instância contendo a lista de grupos.
	 */
    public <G extends GroupModel> void setGroups(List<G> groups){
		this.groups = (List)groups;
	}

	/**
	 * Retorna o identificador do usuário.
	 * 
	 * @return Valor inteiro contendo o identificador do usuário.
	 */
	public Integer getId(){
		return id;
	}

	/**
	 * Define o identificador do usuário.
	 * 
	 * @param id Valor inteiro contendo o identificador do usuário.
	 */
	public void setId(Integer id){
		this.id = id;
	}

	/**
	 * Retorna o nome do usuário.
	 * 
	 * @return String contendo o nome do usuário.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Define o nome do usuário.
	 * 
	 * @param name String contendo o nome do usuário.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Retorna a senha do usuário.
	 * 
	 * @return String contendo a senha do usuário.
	 */
	public String getPassword(){
		return password;
	}

	/**
	 * Define a senha do usuário.
	 * 
	 * @param password String contendo a senha do usuário.
	 */
	public void setPassword(String password){
		this.password = password;
	}

	/**
	 * Retorna o e-Mail do usuário.
	 * 
	 * @return String contendo o e-Mail do usuário.
	 */
	public String getEmail(){
		return email;
	}

	/**
	 * Define o e-Mail do usuário.
	 * 
	 * @param email String contendo o e-Mail do usuário.
	 */
	public void setEmail(String email){
		this.email = email;
	}

	/**
	 * Indica se usuário deve trocar a senha no próximo login.
	 * 
	 * @return True/False.
	 */
	public Boolean changePassword(){
		return changePassword;
	}

	/**
	 * Indica se usuário deve trocar a senha no próximo login.
	 * 
	 * @return True/False.
	 */
	public Boolean getChangePassword(){
		return changePassword();
	}

	/**
	 * Define se usuário deve trocar a senha no próximo login.
	 * 
	 * @param changePassword True/False.
	 */
	public void setChangePassword(Boolean changePassword){
		this.changePassword = changePassword;
	}

	/**
	 * Retorna a data/horário de criação do usuário.
	 * 
	 * @return Instância contendo a data/horário de criação do usuário.
	 */
	public Date getCreateDate(){
		return createDate;
	}

	/**
	 * Define a data/horário de criação do usuário.
	 * 
	 * @param createDate Instância contendo a data/horário de criação do usuário.
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	/**
	 * Retorna a data/horário de expiração da senha do usuário.
	 * 
	 * @return Instância contendo a data/horário de expiração da senha do usuário.
	 */
	public Date getExpirePasswordDate(){
		return expirePasswordDate;
	}

	/**
	 * Define a data/horário de expiração da senha do usuário.
	 * 
	 * @param expirePasswordDate Instância contendo a data/horário de expiração da senha do usuário.
	 */
	public void setExpirePasswordDate(Date expirePasswordDate){
		this.expirePasswordDate = expirePasswordDate;
	}

	/**
	 * Retorna a data/horário da última atualização dos dados do usuário.
	 * 
	 * @return Instância contendo a data/horário da última atualização dos dados do usuário.
	 */
	public Date getLastUpdateDate(){
		return lastUpdateDate;
	}

	/**
	 * Define a data/horário da última atualização dos dados do usuário.
	 * 
	 * @param lastUpdateDate Instância contendo a data/horário da última atualização dos dados do 
	 * usuário.
	 */
	public void setLastUpdateDate(Date lastUpdateDate){
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * Retorna o nome completo do usuário.
	 * 
	 * @return String contendo o nome completo do usuário.
	 */
	public String getFullName(){
		return fullName;
	}

	/**
	 * Define o nome completo do usuário.
	 * 
	 * @param fullName String contendo o nome completo do usuário.
	 */
	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	/**
	 * Indica se é um superusuário.
	 * 
	 * @return True/False.
	 */
	public Boolean isSuperUser(){
		return superUser;
	}

	/**
	 * Indica se é um superusuário.
	 * 
	 * @return True/False.
	 */
	public Boolean getSuperUser(){
		return isSuperUser();
	}

	/**
	 * Define se é um superusuário.
	 * 
	 * @param superUser True/False.
	 */
	public void setSuperUser(Boolean superUser){
		this.superUser = superUser;
	}

	/**
	 * Retorna a senha de confirmação do usuário.
	 * 
	 * @return String contendo a senha de confirmação do usuário.
	 */
	public String getConfirmPassword(){
		return confirmPassword;
	}

	/**
	 * Define a senha de confirmação do usuário.
	 * 
	 * @param confirmPassword String contendo a senha de confirmação do usuário.
	 */
	public void setConfirmPassword(String confirmPassword){
		this.confirmPassword = confirmPassword;
	}

	/**
	 * Retorna a nova senha do usuário.
	 * 
	 * @return String contendo a nova senha do usuário.
	 */
	public String getNewPassword(){
		return newPassword;
	}

	/**
	 * Define a nova senha do usuário.
	 * 
	 * @param newPassword String contendo a nova senha do usuário.
	 */
	public void setNewPassword(String newPassword){
		this.newPassword = newPassword;
	}
	
	/**
	 * Retorna os parâmetros de login do usuário (Ex: Data de expiração de senha, etc).
	 * 
	 * @return Instância contendo os parâmetros de login.
	 */
	public <LP extends LoginParametersModel> LP getLoginParameters(){
    	return (LP)loginParameters;
    }

	/**
	 * Define os parâmetros de login do usuário (Ex: Data de expiração de senha, etc).
	 * 
	 * @param loginParameters Instância contendo os parâmetros de login.
	 */
	public <LP extends LoginParametersModel> void setLoginParameters(LP loginParameters){
    	this.loginParameters = loginParameters;
    }

	/**
	 * Indica se o usuário possui permissões.
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
	 * Verifica se o grupo possui permissões para um módulo do sistema
	 * específico.
	 * 
	 * @param compareSystemModule Instância contendo as propriedade do módulo do sistema.
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
	 * Verifica se o grupo possui permissões para um objeto específico.
	 * 
	 * @param compareObject Instância contendo as propriedade do objeto.
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