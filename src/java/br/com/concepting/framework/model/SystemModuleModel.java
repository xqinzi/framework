package br.com.concepting.framework.model;

import java.util.List;

import org.hibernate.collection.PersistentCollection;

import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ContextSearchType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.persistence.types.RelationType;

/**
 * Classe que define a estrutura básica para o modelo de dados que armazena as informações de sistema.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model
public class SystemModuleModel extends BaseModel{
	@Property(isIdentity = true, isForSearch = true)
	private Integer id = null;

	@Property(isForSearch = true, validations = ValidationType.REQUIRED)
	private String name = "";

	@Property(isForSearch = true, searchCondition = ConditionType.CONTEXT, contextSearchType = ContextSearchType.BOTH, validations = ValidationType.REQUIRED)
	private String title = "";

	@Property(isForSearch = true, validations = ValidationType.REQUIRED)
	private String url = "";

	@Property
	private byte logo[] = null;
	
	@Property
	private String logoFileName = "";
	
	@Property
	private String logoContentType = "";

	@Property
	private String description = "";

	@Property
	private String version = "";

	@Property
	private Boolean active = true;

	@Property(relationType = RelationType.ONE_TO_MANY)
	private List<FormModel> forms = null;

	/**
	 * Retorna o nome do arquivo de logo.
	 * 
	 * @return String contendo o nome do arquivo.
	 */
	public String getLogoFileName(){
        return logoFileName;
    }

    /**
     * Define o nome do arquivo de logo.
     * 
     * @param logoFileName String contendo o nome do arquivo.
     */
    public void setLogoFileName(String logoFileName){
        this.logoFileName = logoFileName;
    }

    /**
     * Retorna o formato do arquivo de logo.
     * 
     * @return String contendo o formato do arquivo.
     */
    public String getLogoContentType(){
        return logoContentType;
    }

    /**
     * Define o formato do arquivo de logo.
     * 
     * @param logoContentType String contendo o formato do arquivo.
     */
    public void setLogoContentType(String logoContentType){
        this.logoContentType = logoContentType;
    }

    /**
	 * Retorna o identificador do módulo do sistema.
	 * 
	 * @return Valor inteiro contendo o identificador.
	 */
	public Integer getId(){
		return id;
	}

	/**
	 * Define o identificador do módulo do sistema.
	 * 
	 * @param id Valor inteiro contendo o identificador.
	 */
	public void setId(Integer id){
		this.id = id;
	}

	/**
	 * Retorna o nome do módulo do sistema.
	 * 
	 * @return String contendo o nome do módulo.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Define o nome do módulo do sistema.
	 * 
	 * @param name String contendo o nome do módulo.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Retorna a URL para acesso ao módulo.
	 * 
	 * @return String contendo a URL do módulo.
	 */
	public String getUrl(){
		return url;
	}

	/**
	 * Define a URL para acesso ao módulo.
	 * 
	 * @param url String contendo a URL do módulo.
	 */
	public void setUrl(String url){
		this.url = url;
	}

	/**
	 * Retorna uma instância contendo as propriedades de um formulário específico.
     *
	 * @param formName String contendo o identificador do formulário desejado.
	 * @return Instância contendo as propriedades de um formulário.
	 */
    public <F extends FormModel> F getForm(String formName){
		if(forms != null && !(forms instanceof PersistentCollection))
			for(FormModel form : forms)
				if(form.getName().equals(formName))
					return (F)form;

		return null;
	}

	/**
	 * Atualiza uma instância contendo as propriedades de um formulário na lista de formulários do 
	 * módulo do sistema.
	 *
	 * @param formModel Instância contendo as propriedades do formulário.
	 */
	public <F extends FormModel> void setForm(FormModel formModel){
		if(forms != null){
			Integer pos = forms.indexOf(formModel);
			
			if(pos >= 0)
				forms.set(pos, formModel);
		}
	}

	/**
	 * Retorna a lista de formulários vinculados ao módulo do sistema.
	 * 
	 * @return Lista contendo os dados dos formulários vinculados.
	 */
    public <F extends FormModel> List<F> getForms(){
		return (List)forms;
	}

	/**
	 * Define a lista de formulários vinculados ao módulo do sistema.
	 * 
	 * @param forms Lista contendo os dados dos formulários vinculados.
	 */
    public <F extends FormModel> void setForms(List<F> forms){
		this.forms = (List)forms;
	}

	/**
	 * Retorna o título que identifica o módulo do sistema.
	 * 
	 * @return String contendo o título que identifica o módulo.
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * Define o título que identifica o módulo do sistema.
	 * 
	 * @param title String contendo o título que identifica o módulo.
	 */
	public void setTitle(String title){
		this.title = title;
	}

	/**
	 * Retorna o array bytes contendo os dados do logo vinculado ao módulo do sistema.
	 * 
	 * @return Array de bytes contendo os dados do logo.
	 */
	public byte[] getLogo(){
		return logo;
	}

	/**
	 * Define o array bytes contendo os dados do logo vinculado ao módulo do sistema.
	 * 
	 * @param logo Array de bytes contendo os dados do logo.
	 */
	public void setLogo(byte[] logo){
		this.logo = logo;
	}

	/**
	 * Retorna a descrição do módulo do sistema.
	 * 
	 * @return String contendo a descrição do módulo do sistema.
	 */
	public String getDescription(){
		return description;
	}

	/**
	 * Define a descrição do módulo do sistema.
	 * 
	 * @param description String contendo a descrição do módulo do sistema.
	 */
	public void setDescription(String description){
		this.description = description;
	}

	/**
	 * Retorna o identificador da versão do módulo do sistema.
	 *
	 * @return String contendo o identificador da versão.
	 */
	public String getVersion(){
		return version;
	}

	/**
	 * Define o identificador da versão do módulo do sistema.
	 *
	 * @param version String contendo o identificador da versão.
	 */
	public void setVersion(String version){
		this.version = version;
	}

	/**
	 * Indica se o módulo do sistema está ativo.
	 * 
	 * @return True/False.
	 */
	public Boolean isActive(){
		return active;
	}

	/**
	 * Indica se o módulo do sistema está ativo.
	 * 
	 * @return True/False.
	 */
	public Boolean getActive(){
		return active;
	}

	/**
	 * Define se o módulo do sistema está ativo.
	 * 
	 * @param active True/False.
	 */
	public void setActive(Boolean active){
		this.active = active;
	}
}