package br.com.concepting.framework.ui.taglibs;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.tagext.Tag;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.controller.form.BaseActionForm;
import br.com.concepting.framework.controller.form.helpers.ActionFormMessage;
import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.controller.form.util.ActionFormMessageUtil;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.ui.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.util.DateTimeUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.PositionType;
import br.com.concepting.framework.util.types.ScopeType;

/**
 * Classe que define o estrutura básica para o componente vinculado a uma propriedade de um 
 * modelo de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BasePropertyTag extends BaseActionFormElementTag{
    private String                   valueMap                            = "";
    private String                   valueMapScope                       = null;
    private Map                      valueMapInstance                    = null;
    private Boolean                  onBlurActionValidate                = false;
    private String                   onBlurActionValidateProperties      = "";
    private Boolean                  onFocusActionValidate               = false;
    private String                   onFocusActionValidateProperties     = "";
    private Boolean                  onClickActionValidate               = false;
    private String                   onClickActionValidateProperties     = "";
    private Boolean                  onMouseOverActionValidate           = false;
    private String                   onMouseOverActionValidateProperties = "";
    private Boolean                  onMouseOutActionValidate            = false;
    private String                   onMouseOutActionValidateProperties  = "";
	private String                   onChange                            = "";
	private String                   onChangeAction                      = "";
	private String                   onChangeActionForward               = "";
	private String                   onChangeActionForwardOnFail         = "";
	private String                   onChangeActionUpdateViews           = "";
	private Boolean                  onChangeActionValidate              = false;
	private String                   onChangeActionValidateProperties    = "";
	private String                   onKeyPress                          = "";
	private String                   onKeyPressAction                    = "";
	private String                   onKeyPressActionForward             = "";
	private String                   onKeyPressActionForwardOnFail       = "";
	private String                   onKeyPressActionUpdateViews         = "";
	private Boolean                  onKeyPressActionValidate            = false;
	private String                   onKeyPressActionValidateProperties  = "";
	private String                   onKeyUp                             = "";
	private String                   onKeyUpAction                       = "";
    private String                   onKeyUpActionForward                = "";
    private String                   onKeyUpActionForwardOnFail          = "";
    private String                   onKeyUpActionUpdateViews            = "";
    private Boolean                  onKeyUpActionValidate               = false;
    private String                   onKeyUpActionValidateProperties     = "";
	private String                   onKeyDown                           = "";
	private String                   onKeyDownAction                     = "";
    private String                   onKeyDownActionForward              = "";
    private String                   onKeyDownActionForwardOnFail        = "";
    private String                   onKeyDownActionUpdateViews          = "";
    private Boolean                  onKeyDownActionValidate             = false;
    private String                   onKeyDownActionValidateProperties   = "";
    private Boolean                  focus                               = false;
	private String                   pattern                             = "";
	private Object                   value                               = null;
	private String                   validationStyle                     = "";
	private String                   validationStyleClass                = "";
	private SearchPropertiesGroupTag searchPropertiesGroupTag            = null;
	private String                   invalidPropertyMessage              = "";
    private String                   dataIsEmptyMessage                  = "";
	private Boolean                  showValidationMessages              = false;
    private PropertyInfo             propertyInfo                        = null;
    
    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação do evento de mudança de valor.
     * 
     * @return True/False.
     */
    public Boolean getOnChangeActionValidate(){
        return onChangeActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação do evento de mudança de valor.
     * 
     * @param onChangeActionValidate True/False.
     */
    public void setOnChangeActionValidate(Boolean onChangeActionValidate){
        this.onChangeActionValidate = onChangeActionValidate;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação do evento de digitação.
     * 
     * @return True/False.
     */
    public Boolean getOnKeyPressActionValidate(){
        return onKeyPressActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação do evento de digitação.
     * 
     * @param onKeyPressActionValidate True/False.
     */
    public void setOnKeyPressActionValidate(Boolean onKeyPressActionValidate){
        this.onKeyPressActionValidate = onKeyPressActionValidate;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação do evento de soltar uma tecla.
     * 
     * @return True/False.
     */
    public Boolean getOnKeyUpActionValidate(){
        return onKeyUpActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação do evento de soltar uma tecla.
     * 
     * @param onKeyUpActionValidate True/False.
     */
    public void setOnKeyUpActionValidate(Boolean onKeyUpActionValidate){
        this.onKeyUpActionValidate = onKeyUpActionValidate;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação do evento de apertar uma tecla.
     * 
     * @return True/False.
     */
    public Boolean getOnKeyDownActionValidate(){
        return onKeyDownActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação do evento de apertar uma tecla.
     * 
     * @param onKeyDownActionValidate True/False.
     */
    public void setOnKeyDownActionValidate(Boolean onKeyDownActionValidate){
        this.onKeyDownActionValidate = onKeyDownActionValidate;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação do evento de tirar o foco.
     * 
     * @return True/False.
     */
    public Boolean getOnBlurActionValidate(){
        return onBlurActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação do evento de tirar o foco.
     * 
     * @param onBlurActionValidate True/False.
     */
    public void setOnBlurActionValidate(Boolean onBlurActionValidate){
        this.onBlurActionValidate = onBlurActionValidate;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de tirar o foco.
     * 
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnBlurActionValidateProperties(){
        return onBlurActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de tirar o foco.
     * 
     * @param onBlurActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnBlurActionValidateProperties(String onBlurActionValidateProperties){
        this.onBlurActionValidateProperties = onBlurActionValidateProperties;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação do evento de colocar o foco.
     * 
     * @return True/False.
     */
    public Boolean getOnFocusActionValidate(){
        return onFocusActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação do evento de colocar o foco.
     * 
     * @param onFocusActionValidate True/False.
     */
    public void setOnFocusActionValidate(Boolean onFocusActionValidate){
        this.onFocusActionValidate = onFocusActionValidate;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de colocar o foco.
     * 
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnFocusActionValidateProperties(){
        return onFocusActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de colocar o foco.
     * 
     * @param onFocusActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnFocusActionValidateProperties(String onFocusActionValidateProperties){
        this.onFocusActionValidateProperties = onFocusActionValidateProperties;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação do evento de clique.
     * 
     * @return True/False.
     */
    public Boolean getOnClickActionValidate(){
        return onClickActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação do evento de clique.
     * 
     * @param onClickActionValidate True/False.
     */
    public void setOnClickActionValidate(Boolean onClickActionValidate){
        this.onClickActionValidate = onClickActionValidate;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de clique.
     * 
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnClickActionValidateProperties(){
        return onClickActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de clique.
     * 
     * @param onClickActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnClickActionValidateProperties(String onClickActionValidateProperties){
        this.onClickActionValidateProperties = onClickActionValidateProperties;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação do evento de passar o mouse.
     * 
     * @return True/False.
     */
    public Boolean getOnMouseOverActionValidate(){
        return onMouseOverActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação do evento de passar o mouse.
     * 
     * @param onMouseOverActionValidate String contendo os identificadores das propriedades.
     */
    public void setOnMouseOverActionValidate(Boolean onMouseOverActionValidate){
        this.onMouseOverActionValidate = onMouseOverActionValidate;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de passar o mouse.
     * 
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnMouseOverActionValidateProperties(){
        return onMouseOverActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de passar o mouse.
     * 
     * @param onMouseOverActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnMouseOverActionValidateProperties(String onMouseOverActionValidateProperties){
        this.onMouseOverActionValidateProperties = onMouseOverActionValidateProperties;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação do evento de tirar o mouse.
     * 
     * @return True/False.
     */
    public Boolean getOnMouseOutActionValidate(){
        return onMouseOutActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação do evento de tirar o mouse.
     * 
     * @param onMouseOutActionValidate True/False.
     */
    public void setOnMouseOutActionValidate(Boolean onMouseOutActionValidate){
        this.onMouseOutActionValidate = onMouseOutActionValidate;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de tirar o mouse.
     * 
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnMouseOutActionValidateProperties(){
        return onMouseOutActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de tirar o mouse.
     * 
     * @param onMouseOutActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnMouseOutActionValidateProperties(String onMouseOutActionValidateProperties){
        this.onMouseOutActionValidateProperties = onMouseOutActionValidateProperties;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de mudança de valor.
     * 
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnChangeActionValidateProperties(){
        return onChangeActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de mudança de valor.
     * 
     * @param onChangeActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnChangeActionValidateProperties(String onChangeActionValidateProperties){
        this.onChangeActionValidateProperties = onChangeActionValidateProperties;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de digitação.
     * 
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnKeyPressActionValidateProperties(){
        return onKeyPressActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de digitação.
     * 
     * @param onKeyPressActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnKeyPressActionValidateProperties(String onKeyPressActionValidateProperties){
        this.onKeyPressActionValidateProperties = onKeyPressActionValidateProperties;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de soltar uma tecla.
     * 
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnKeyUpActionValidateProperties(){
        return onKeyUpActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de soltar uma tecla.
     * 
     * @param onKeyUpActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnKeyUpActionValidateProperties(String onKeyUpActionValidateProperties){
        this.onKeyUpActionValidateProperties = onKeyUpActionValidateProperties;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de apertar uma tecla.
     * 
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnKeyDownActionValidateProperties(){
        return onKeyDownActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação do evento de apertar uma tecla.
     * 
     * @param onKeyDownActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnKeyDownActionValidateProperties(String onKeyDownActionValidateProperties){
        this.onKeyDownActionValidateProperties = onKeyDownActionValidateProperties;
    }

    /**
     * Retorna o identificador do redirecionamento após a execução da ação do evento de mudança de valor.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnChangeActionForward(){
        return onChangeActionForward;
    }

    /**
     * Define o identificador do redirecionamento após a execução da ação do evento de mudança de valor.
     * 
     * @param onChangeActionForward String contendo o identificador do redirecionamento.
     */
    public void setOnChangeActionForward(String onChangeActionForward){
        this.onChangeActionForward = onChangeActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha após a execução da ação do evento de mudança de valor.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnChangeActionForwardOnFail(){
        return onChangeActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha após a execução da ação do evento de mudança de valor.
     * 
     * @param onChangeActionForwardOnFail String contendo o identificador do redirecionamento.
     */
    public void setOnChangeActionForwardOnFail(String onChangeActionForwardOnFail){
        this.onChangeActionForwardOnFail = onChangeActionForwardOnFail;
    }

    /**
     * Retorna os identificadores das views a serem atualizadas após a execução da ação do evento de mudança de valor.
     * 
     * @return String contendo os identificadores das views.
     */
    public String getOnChangeActionUpdateViews(){
        return onChangeActionUpdateViews;
    }

    /**
     * Define os identificadores das views a serem atualizadas após a execução da ação do evento de mudança de valor.
     * 
     * @param onChangeActionUpdateViews String contendo os identificadores das views.
     */
    public void setOnChangeActionUpdateViews(String onChangeActionUpdateViews){
        this.onChangeActionUpdateViews = onChangeActionUpdateViews;
    }
 
    /**
     * Retorna o identificador do redirecionamento após a execução da ação do evento de digitação.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnKeyPressActionForward(){
        return onKeyPressActionForward;
    }

    /**
     * Define o identificador do redirecionamento após a execução da ação do evento de digitação.
     * 
     * @param onKeyPressActionForward String contendo o identificador do redirecionamento.
     */
    public void setOnKeyPressActionForward(String onKeyPressActionForward){
        this.onKeyPressActionForward = onKeyPressActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha após a execução da ação do evento de digitação.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnKeyPressActionForwardOnFail(){
        return onKeyPressActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha após a execução da ação do evento de digitação.
     * 
     * @param onKeyPressActionForwardOnFail String contendo o identificador do redirecionamento.
     */
    public void setOnKeyPressActionForwardOnFail(String onKeyPressActionForwardOnFail){
        this.onKeyPressActionForwardOnFail = onKeyPressActionForwardOnFail;
    }

    /**
     * Retorna o identificador das views a serem atualizadas após a execução da ação do evento de digitação.
     * 
     * @return String contendo o identificador das views.
     */
    public String getOnKeyPressActionUpdateViews(){
        return onKeyPressActionUpdateViews;
    }

    /**
     * Define o identificador das views a serem atualizadas após a execução da ação do evento de digitação.
     * 
     * @param onKeyPressActionUpdateViews String contendo o identificador das views.
     */
    public void setOnKeyPressActionUpdateViews(String onKeyPressActionUpdateViews){
        this.onKeyPressActionUpdateViews = onKeyPressActionUpdateViews;
    }

    /**
     * Retorna o identificador do redirecionamento após a execução da ação do evento de soltar uma tecla.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnKeyUpActionForward(){
        return onKeyUpActionForward;
    }

    /**
     * Define o identificador do redirecionamento após a execução da ação do evento de soltar uma tecla.
     * 
     * @param onKeyUpActionForward String contendo o identificador do redirecionamento.
     */
    public void setOnKeyUpActionForward(String onKeyUpActionForward){
        this.onKeyUpActionForward = onKeyUpActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha após a execução da ação do evento de soltar uma tecla.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnKeyUpActionForwardOnFail(){
        return onKeyUpActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha após a execução da ação do evento de soltar uma tecla.
     * 
     * @param onKeyUpActionForwardOnFail String contendo o identificador do redirecionamento.
     */
    public void setOnKeyUpActionForwardOnFail(String onKeyUpActionForwardOnFail){
        this.onKeyUpActionForwardOnFail = onKeyUpActionForwardOnFail;
    }

    /**
     * Retorna o identificador das views a serem atualizadas após a execução da ação do evento de soltar uma tecla.
     * 
     * @return String contendo o identificador das views.
     */
    public String getOnKeyUpActionUpdateViews(){
        return onKeyUpActionUpdateViews;
    }

    /**
     * Define o identificador das views a serem atualizadas após a execução da ação do evento de soltar uma tecla.
     * 
     * @param onKeyUpActionUpdateViews String contendo o identificador das views.
     */
    public void setOnKeyUpActionUpdateViews(String onKeyUpActionUpdateViews){
        this.onKeyUpActionUpdateViews = onKeyUpActionUpdateViews;
    }

    /**
     * Retorna o identificador do redirecionamento após a execução da ação do evento de apertar uma tecla.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnKeyDownActionForward(){
        return onKeyDownActionForward;
    }

    /**
     * Define o identificador do redirecionamento após a execução da ação do evento de apertar uma tecla.
     * 
     * @param onKeyDownActionForward String contendo o identificador do redirecionamento.
     */
    public void setOnKeyDownActionForward(String onKeyDownActionForward){
        this.onKeyDownActionForward = onKeyDownActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha após a execução da ação do evento de apertar uma tecla.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnKeyDownActionForwardOnFail(){
        return onKeyDownActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha após a execução da ação do evento de apertar uma tecla.
     * 
     * @param onKeyDownActionForwardOnFail String contendo o identificador do redirecionamento.
     */
    public void setOnKeyDownActionForwardOnFail(String onKeyDownActionForwardOnFail){
        this.onKeyDownActionForwardOnFail = onKeyDownActionForwardOnFail;
    }

    /**
     * Retorna o identificador das views a serem atualizadas após a execução da ação do evento de apertar uma tecla.
     * 
     * @return String contendo o identificador das views.
     */
    public String getOnKeyDownActionUpdateViews(){
        return onKeyDownActionUpdateViews;
    }

    /**
     * Define o identificador das views a serem atualizadas após a execução da ação do evento de apertar uma tecla.
     * 
     * @param onKeyDownActionUpdateViews String contendo o identificador das views.
     */
    public void setOnKeyDownActionUpdateViews(String onKeyDownActionUpdateViews){
        this.onKeyDownActionUpdateViews = onKeyDownActionUpdateViews;
    }

    /**
     * Retorna o identificador da ação do evento de mudança de valor.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getOnChangeAction(){
        return onChangeAction;
    }

    /**
     * Define o identificador da ação do evento de mudança de valor.
     * 
     * @param onChangeAction String contendo o identificador da ação.
     */
    public void setOnChangeAction(String onChangeAction){
        this.onChangeAction = onChangeAction;
    }

    /**
     * Define o identificador da ação do evento de mudança de valor.
     * 
     * @param onChangeAction String contendo o identificador da ação.
     */
    protected void setOnChangeActionType(ActionType onChangeActionType){
        if(onChangeActionType != null)
            this.onChangeAction = onChangeActionType.getMethod();
        else
        	this.onChangeAction = "";
    }

    /**
     * Retorna o identificador da ação do evento de pressionar uma techa.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getOnKeyPressAction(){
        return onKeyPressAction;
    }

    /**
     * Define o identificador da ação do evento de soltar uma techa.
     * 
     * @param onKeyPressAction String contendo o identificador da ação.
     */
    public void setOnKeyPressAction(String onKeyPressAction){
        this.onKeyPressAction = onKeyPressAction;
    }

    /**
     * Retorna o identificador da ação do evento de soltar uma techa.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getOnKeyUpAction(){
        return onKeyUpAction;
    }

    /**
     * Define o identificador da ação do evento de soltar uma techa.
     * 
     * @param onKeyUpAction String contendo o identificador da ação.
     */
    public void setOnKeyUpAction(String onKeyUpAction){
        this.onKeyUpAction = onKeyUpAction;
    }

    /**
     * Retorna o identificador da ação do evento de apertar uma techa.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getOnKeyDownAction(){
        return onKeyDownAction;
    }

    /**
     * Define o identificador da ação do evento de apertar uma techa.
     * 
     * @param onKeyDownAction String contendo o identificador da ação.
     */
    public void setOnKeyDownAction(String onKeyDownAction){
        this.onKeyDownAction = onKeyDownAction;
    }

    /**
     * Retorna o identificador do mapa de valores das opções de seleção.
     *
     * @return String contendo o identificador do mapa de valores.
     */
    public String getValueMap(){
        return valueMap;
    }

    /**
     * Define o identificador do mapa de valores das opções de seleção.
     *
     * @param valueMap String contendo o identificador do mapa de valores.
     */
    public void setValueMap(String valueMap){
        this.valueMap = valueMap;
    }

    /**
     * Retorna o escopo de armazenamento do mapa de valores das opções de seleção.
     *
     * @return String que define o escopo de armazenamento.
     */
    public String getValueMapScope(){
        return valueMapScope;
    }

    /**
     * Define o escopo de armazenamento do mapa de valores das opções de seleção.
     *
     * @param valueMapScope String que define o escopo de armazenamento.
     */
    public void setValueMapScope(String valueMapScope){
        this.valueMapScope = StringUtil.trim(valueMapScope).toUpperCase();
    }

    /**
     * Retorna o escopo de armazenamento do mapa de valores das opções de seleção.
     *
     * @return Constante que define o escopo de armazenamento.
     */
    protected ScopeType getValueMapScopeType(){
        try{
            return ScopeType.valueOf(valueMapScope);
        }
        catch(Throwable e){
            return null;
        }
    }

    /**
     * Define o escopo de armazenamento do mapa de valores das opções de seleção.
     *
     * @param valueMapScope Constante que define o escopo de armazenamento.
     */
    protected void setValueMapScopeType(ScopeType valueMapScope){
        if(valueMapScope != null)
            this.valueMapScope = valueMapScope.toString();
        else
            this.valueMapScope = "";
    }

    /**
     * Retorna a instância contendo o mapa de valores das opções de seleção.
     *
     * @return Instância contendo o mapa de valores.
     */
    protected Map getValueMapInstance(){
        return valueMapInstance;
    }

    /**
     * Define a instância contendo o mapa de valores das opções de seleção.
     *
     * @param valueMapInstance Instância contendo o mapa de valores.
     */
    protected void setValueMapInstance(Map valueMapInstance){
        this.valueMapInstance = valueMapInstance;
    }
    
    /**
     * Retorna o estilo CSS para a mensagem de validação.
     * 
     * @return String contendo o estilo CSS.
     */
    public String getValidationStyle(){
        return validationStyle;
    }

    /**
     * Define o estilo CSS para a mensagem de validação.
     * 
     * @param validationStyle String contendo o estilo CSS.
     */
    public void setValidationStyle(String validationStyle){
        this.validationStyle = validationStyle;
    }

    /**
     * Retorna o estilo CSS para a mensagem de validação.
     * 
     * @return String contendo o estilo CSS.
     */
    public String getValidationStyleClass(){
        return validationStyleClass;
    }

    /**
     * Define o estilo CSS para a mensagem de validação.
     * 
     * @param validationStyleClass String contendo o estilo CSS.
     */
    public void setValidationStyleClass(String validationStyleClass){
        this.validationStyleClass = validationStyleClass;
    }

    /**
     * Retorna a mensagem quando não existem dados a serem exibidos.
     *
     * @return String contendo a mensagem.
     */
    protected String getDataIsEmptyMessage(){
        return dataIsEmptyMessage;
    }

    /**
     * Define a mensagem quando não existem dados a serem exibidos.
     *
     * @param dataIsEmptyMessage String contendo a mensagem.
     */
    protected void setDataIsEmptyMessage(String dataIsEmptyMessage){
        this.dataIsEmptyMessage = dataIsEmptyMessage;
    }

    /**
     * Retorna a instância contendo os atributos da propriedade do modelo de dados 
     * relacionada ao componente.
     * 
     * @return Instância contendo os atributos da propriedade do modelo de dados.
     */
    protected PropertyInfo getPropertyInfo(){
        return propertyInfo;
    }
    
    /**
     * Define a instância contendo os atributos da propriedade do modelo de dados 
     * relacionada ao componente.
     * 
     * @param propertyInfo Instância contendo os atributos da propriedade do modelo de dados.
     */
    protected void setPropertyInfo(PropertyInfo propertyInfo){
        this.propertyInfo = propertyInfo;
    }

    /**
	 * Indica se a mensagem de validação para esse componente deve ser exibida.
	 * 
	 * @return True/False.
	 */
	public Boolean showValidationMessages(){
    	return showValidationMessages;
    }

    /**
     * Indica se a mensagem de validação para esse componente deve ser exibida.
     * 
     * @return True/False.
     */
    public Boolean isShowValidationMessages(){
        return showValidationMessages();
    }
    
    /**
     * Indica se a mensagem de validação para esse componente deve ser exibida.
     * 
     * @return True/False.
     */
    public Boolean getShowValidationMessages(){
        return showValidationMessages();
    }

    /**
	 * Define se a mensagem de validação para esse componente deve ser exibida.
	 * 
	 * @param showValidationMessages True/False.
	 */
	public void setShowValidationMessages(Boolean showValidationMessages){
    	this.showValidationMessages = showValidationMessages;
    }

	/**
	 * Indica se o foco deve ser aplicado ao componente.
	 *
	 * @return True/False.
	 */
	public Boolean focus(){
     	return focus;
    }
	
	/**
	 * Define se o foco deve ser aplicado ao componente.
	 *
	 * @param focus True/False.
	 */
	public void setFocus(Boolean focus){
		this.focus = focus;
	}

	/**
	 * Retorna a instância do grupo de propriedades de pesquisa.
	 *
	 * @return Instância do grupo de propriedades de pesquisa.
	 */
	protected SearchPropertiesGroupTag getSearchPropertiesGroupTag(){
    	return searchPropertiesGroupTag;
    }

	/**
	 * Define a instância do grupo de propriedades de pesquisa.
	 *
	 * @param searchPropertiesGroupTag Instância do grupo de propriedades de pesquisa.
	 */
	protected void setSearchPropertiesGroupTag(SearchPropertiesGroupTag searchPropertiesGroupTag){
    	this.searchPropertiesGroupTag = searchPropertiesGroupTag;
    }

	/**
	 * Retorna a máscara de formatação para o componente.
	 * 
	 * @return String contendo a máscara de formatação.
	 */
	protected String getPattern(){
	    if(propertyInfo != null){
	        if(this.pattern.length() == 0){
			    String  pattern         = propertyInfo.getPattern();
                Integer precision       = propertyInfo.getPrecision();
                Locale  currentLanguage = systemController.getCurrentLanguage();
				
				if(pattern.length() == 0){
				    Boolean useAdditionalFormatting = useAdditionalFormatting();
				    
					if(propertyInfo.isDate()){
					    if(useAdditionalFormatting)
					        pattern = DateTimeUtil.getDefaultDateTimePattern(currentLanguage);
					    else
					        pattern = DateTimeUtil.getDefaultDatePattern(currentLanguage);
					}
					else if(propertyInfo.isNumber())
						pattern = NumberUtil.getDefaultPattern(propertyInfo.getClazz(), useAdditionalFormatting, precision);
				}
				
				this.pattern = pattern;
			}
		}
		
		return this.pattern;
	}

	/**
	 * Define a máscara de formatação para o componente.
	 * 
	 * @param pattern String contendo a máscara de formatação.
	 */
	public void setPattern(String pattern){
		this.pattern = pattern;
	}

	/**
	 * Retorna a instância contendo o valor do componente.
	 * 
	 * @return Instância contendo o valor do componente.
	 */
	public <O> O getValue(){
		return (O)value;
	}

	/**
	 * Define a instância contendo o valor do componente.
	 * 
	 * @param value Instância contendo o valor do componente.
	 */
	public <O> void setValue(O value){
		this.value = value;
	}

	/**
	 * Retorna o evento a ser executado quando o valor do componente for alterado.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnChange(){
		return onChange;
	}

	/**
	 * Define o evento a ser executado quando o valor do componente for alterado.
	 * 
	 * @param onChange String contendo o evento a ser executado.
	 */
	public void setOnChange(String onChange){
		this.onChange = onChange;
	}

	/**
	 * Retorna o evento a ser executando quando uma tecla for pressionada.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnKeyPress(){
		return onKeyPress;
	}

	/**
	 * Define o evento a ser executando quando uma tecla for pressionada.
	 * 
	 * @param onKeyPress String contendo o evento a ser executado.
	 */
	public void setOnKeyPress(String onKeyPress){
		this.onKeyPress = onKeyPress;
	}

	/**
	 * Retorna o evento a ser executando quando uma tecla for pressionada.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnKeyUp(){
		return onKeyUp;
	}

	/**
	 * Define o evento a ser executando quando uma tecla for pressionada.
	 * 
	 * @param onKeyUp String contendo o evento a ser executado.
	 */
	public void setOnKeyUp(String onKeyUp){
		this.onKeyUp = onKeyUp;
	}

	/**
	 * Retorna o evento a ser executando quando a tecla pressionada for liberada.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnKeyDown(){
		return onKeyDown;
	}

	/**
	 * Define o evento a ser executando quando a tecla pressionada for liberada.
	 * 
	 * @param onKeyDown String contendo o evento a ser executado.
	 */
	public void setOnKeyDown(String onKeyDown){
		this.onKeyDown = onKeyDown;
	}

	/**
	 * Indica se o componente foi definido para ser uma propriedade de pesquisa.
	 * 
	 * @return True/False.
	 */
	protected Boolean isForSearch(){
		searchPropertiesGroupTag = (SearchPropertiesGroupTag)findAncestorWithClass(this, SearchPropertiesGroupTag.class);
		
		return (searchPropertiesGroupTag != null || getName().startsWith(AttributeConstants.SEARCH_KEY));
	}
	
	/**
	 * Retorna a mensagem que define que a propriedade é inválida.
	 * 
	 * @return String contendo a mensagem de propriedade inválida.
	 */
	protected String getInvalidPropertyMessage(){
		return invalidPropertyMessage;
	}

	/**
	 * Define a mensagem que define que a propriedade é inválida.
	 * 
	 * @param invalidPropertyMessage String contendo a mensagem de propriedade inválida.
	 */
	protected void setInvalidPropertyMessage(String invalidPropertyMessage){
     	this.invalidPropertyMessage = invalidPropertyMessage;
    }
	
	/**
	 * Indica se deve ser usado as configurações adicionais de formatação.
	 * 
	 * @return True/False.
	 */
	protected Boolean useAdditionalFormatting(){
	    if(propertyInfo != null)
	        return propertyInfo.useAdditionalFormatting();
	    
	    return false;
	}
	
	/**
	 * Retorna a quantidade de decimais de precisão.
	 * 
	 * @return Valor inteiro contendo a quantidade de decimais.
	 */
	protected Integer getPrecision(){
	    if(propertyInfo != null)
	        return propertyInfo.getPrecision();
	    
	    return Constants.DEFAULT_NUMBER_PRECISION;
	}
	
	/**
	 * Retorna o idioma a ser utilizado pelo componente.
	 * 
	 * @return Instância contendo as propriedades do idioma.
	 */
	private Locale getLanguage(){
        Locale       language     = systemController.getCurrentLanguage();
        PropertyInfo propertyInfo = getPropertyInfo();
        
        if(propertyInfo != null && !propertyInfo.isModel() && !propertyInfo.isCollection()){
            String languageId = propertyInfo.getLanguage();
            
            if(languageId.length() == 0){
                String languagePropertyId = propertyInfo.getLanguagePropertyId();
                
                if(languagePropertyId.length() > 0){
                    String         actionFormName = getActionFormName();
                    BaseActionForm actionForm     = systemController.getActionForm(actionFormName);
                    BaseModel      model          = (actionForm != null ? (isForSearch() ? actionForm.getSearchModel() : actionForm.getModel()) : null);
                    
                    if(model != null){
                        try{
                            Integer pos = getName().lastIndexOf(".");
                            
                            if(pos >= 0){
                                String propertyId = getName().substring(0, pos);
                                
                                model = (BaseModel)PropertyUtil.getProperty(model, propertyId);
                            }
                            
                            languageId = (String)PropertyUtil.getProperty(model, languagePropertyId);
                            
                            if(languageId.length() > 0)
                                language = LanguageUtil.getLanguageByString(languageId);
                        }
                        catch(Throwable e){
                        }
                    }
                }
            }
            else
                language = LanguageUtil.getLanguageByString(languageId);
        }
        
        return language;
	}

	/**
	 * Retorna o valor do componente formatado.
	 * 
	 * @return String contendo o valor formatado.
	 * @throws Throwable
	 */
	protected String getFormattedValue() throws Throwable{
	    if(getPropertyInfo() != null || getValue() != null)
    		return PropertyUtil.format(getValue(), valueMapInstance, getPattern(), useAdditionalFormatting(), getPrecision(), getLanguage());
	    
	    return getInvalidPropertyMessage();
	}

	/**
	 * Renderiza o valor do componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderValue() throws Throwable{
		print(" value=\"");
		print(getFormattedValue());
		print("\"");
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
        if(validationStyleClass.length() == 0)
            validationStyleClass = TaglibConstants.DEFAULT_VALIDATION_LABEL_STYLE_CLASS;

        Boolean isForSearch = isForSearch();
        String  name        = getName();
        
        if(isForSearch){
            name = StringUtil.replaceAll(getName(), AttributeConstants.SEARCH_KEY.concat("."), "");
            
            setName(name);
        }

        super.initialize();

        String         actionFormName = getActionFormName();
        BaseActionForm actionForm     = systemController.getActionForm(actionFormName);
        
        if(valueMap.length() > 0){
            ScopeType valueMapScope = getValueMapScopeType();
            
            if(valueMapScope == null){
                valueMapScope = ScopeType.FORM;
                
                setValueMapScopeType(valueMapScope);
            }

            if(!valueMap.startsWith(actionFormName)){
                StringBuilder propertyId = new StringBuilder();

                if(valueMapScope == ScopeType.FORM || valueMapScope == ScopeType.MODEL){
                    propertyId.append(actionFormName);
                    propertyId.append(".");
    
                    if(valueMapScope == ScopeType.MODEL){
                        if(isForSearch)
                            propertyId.append(AttributeConstants.SEARCH_MODEL_KEY);
                        else
                            propertyId.append(AttributeConstants.MODEL_KEY);
                        
                        propertyId.append(".");
                    }
                }
                
                propertyId.append(valueMap);
            
                valueMap = propertyId.toString();
            }
            
            valueMapInstance = systemController.findAttribute(valueMap, valueMapScope);
        }

        PropertiesResource resources = getDefaultI18nResource();
        
        dataIsEmptyMessage = StringUtil.trim(resources.getProperty(AttributeConstants.DATA_IS_EMPTY_KEY));

        if(propertyInfo == null){
    		invalidPropertyMessage = StringUtil.trim(resources.getProperty(AttributeConstants.INVALID_PROPERTY_KEY));
        
    		if(actionFormName.length() > 0){
     			BaseModel model = (actionForm != null ? (isForSearch ? actionForm.getSearchModel() : actionForm.getModel()) : null);
         
     			if(model != null){
    				ModelInfo modelInfo = ModelUtil.getModelInfo(model.getClass());
    				
     				if(modelInfo != null){
    				    propertyInfo = modelInfo.getPropertyInfo(name);
    
    				    if(propertyInfo != null){
    						if(!propertyInfo.isForSearch() && isForSearch){
    						    propertyInfo = null;
    						    value        = null;
    						}
    						else{
    						    try{
    						        value = PropertyUtil.getProperty(model, name);
    						    }
    						    catch(Throwable e){
    						        propertyInfo = null;
    						        value        = null;
    						    }
    						}
    					}
     				}
     			}
    		}
	    }
        
        if(isForSearch)
            setName(AttributeConstants.SEARCH_KEY.concat(".").concat(name));
		
		if(propertyInfo != null){
		    AlignmentType alignment = getAlignmentType();
		    
			if(alignment == null){
				if(propertyInfo.isNumber())
				    alignment = AlignmentType.RIGHT;
				else if(propertyInfo.isDate() || propertyInfo.isTime() || propertyInfo.isBoolean())
				    alignment = AlignmentType.CENTER;
				else
				    alignment = AlignmentType.LEFT;
				
				setAlignmentType(alignment);
			}
			
			AlignmentType labelAlignment = getLabelAlignmentType();
			
			if(labelAlignment == null){
			    labelAlignment = alignment;
			
			    setLabelAlignmentType(labelAlignment);
			}
		}
			
		String onBlur          = getOnBlur();
		String onFocus         = getOnFocus();
		String onClick         = getOnClick();
		String onMouseOut      = getOnMouseOut();
		String onMouseOver     = getOnMouseOver();
		String styleClass      = getStyleClass();
		String style           = getStyle();
		Locale currentLanguage = systemController.getCurrentLanguage();

		onBlur      = PropertyUtil.fillPropertiesInString(value, onBlur, currentLanguage);
		onFocus     = PropertyUtil.fillPropertiesInString(value, onFocus, currentLanguage);
		onChange    = PropertyUtil.fillPropertiesInString(value, onChange, currentLanguage);
		onClick     = PropertyUtil.fillPropertiesInString(value, onClick, currentLanguage);
		onKeyDown   = PropertyUtil.fillPropertiesInString(value, onKeyDown, currentLanguage);
		onKeyUp     = PropertyUtil.fillPropertiesInString(value, onKeyUp, currentLanguage);
		onKeyPress  = PropertyUtil.fillPropertiesInString(value, onKeyPress, currentLanguage);
		onMouseOut  = PropertyUtil.fillPropertiesInString(value, onMouseOut, currentLanguage);
		onMouseOver = PropertyUtil.fillPropertiesInString(value, onMouseOver, currentLanguage);
		styleClass  = PropertyUtil.fillPropertiesInString(value, styleClass, currentLanguage);
		style       = PropertyUtil.fillPropertiesInString(value, style, currentLanguage);
        onBlur      = ExpressionProcessorUtil.fillVariablesInString(onBlur, currentLanguage);
        onFocus     = ExpressionProcessorUtil.fillVariablesInString(onFocus, currentLanguage);
        onChange    = ExpressionProcessorUtil.fillVariablesInString(onChange, currentLanguage);
        onClick     = ExpressionProcessorUtil.fillVariablesInString(onClick, currentLanguage);
        onKeyDown   = ExpressionProcessorUtil.fillVariablesInString(onKeyDown, currentLanguage);
        onKeyUp     = ExpressionProcessorUtil.fillVariablesInString(onKeyUp, currentLanguage);
        onKeyPress  = ExpressionProcessorUtil.fillVariablesInString(onKeyPress, currentLanguage);
        onMouseOut  = ExpressionProcessorUtil.fillVariablesInString(onMouseOut, currentLanguage);
        onMouseOver = ExpressionProcessorUtil.fillVariablesInString(onMouseOver, currentLanguage);
        styleClass  = ExpressionProcessorUtil.fillVariablesInString(styleClass, currentLanguage);
        style       = ExpressionProcessorUtil.fillVariablesInString(style, currentLanguage);
        
        if(getOnBlurAction().length() > 0){
            StringBuilder onBlurContent = new StringBuilder();

            onBlurContent.append(" document.");
            onBlurContent.append(actionFormName);
            onBlurContent.append(".");
            
            if(isForSearch)
                onBlurContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
            else
                onBlurContent.append(AttributeConstants.VALIDATE_MODEL_KEY);
            
            onBlurContent.append(".value = ");
            onBlurContent.append(onBlurActionValidate);
            onBlurContent.append(";");
            
            if(onBlurActionValidateProperties.length() > 0){
                onBlurContent.append(" document.");
                onBlurContent.append(actionFormName);
                onBlurContent.append(".");
                onBlurContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                onBlurContent.append(".value = '");
                onBlurContent.append(onBlurActionValidateProperties);
                onBlurContent.append("';");
            }
            
            if(onBlur.length() > 0){
                onBlurContent.append(" ");
                onBlurContent.append(onBlur);
            }
            
            onBlur = onBlurContent.toString();
        }
        
        if(getOnFocusAction().length() > 0){
            StringBuilder onFocusContent = new StringBuilder();

            onFocusContent.append(" document.");
            onFocusContent.append(actionFormName);
            onFocusContent.append(".");
            
            if(isForSearch)
                onFocusContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
            else
                onFocusContent.append(AttributeConstants.VALIDATE_MODEL_KEY);
            
            onFocusContent.append(".value = ");
            onFocusContent.append(onFocusActionValidate);
            onFocusContent.append(";");
            
            if(onFocusActionValidateProperties.length() > 0){
                onFocusContent.append(" document.");
                onFocusContent.append(actionFormName);
                onFocusContent.append(".");
                onFocusContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                onFocusContent.append(".value = '");
                onFocusContent.append(onFocusActionValidateProperties);
                onFocusContent.append("';");
            }
            
            if(onFocus.length() > 0){
                onFocusContent.append(" ");
                onFocusContent.append(onFocus);
            }
            
            onFocus = onFocusContent.toString();
        }

        if(getOnClickAction().length() > 0){
            StringBuilder onClickContent = new StringBuilder();

            onClickContent.append(" document.");
            onClickContent.append(actionFormName);
            onClickContent.append(".");
            
            if(isForSearch)
                onClickContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
            else
                onClickContent.append(AttributeConstants.VALIDATE_MODEL_KEY);
            
            onClickContent.append(".value =");
            onClickContent.append(onClickActionValidate);
            onClickContent.append(";");
            
            if(onClickActionValidateProperties.length() > 0){
                onClickContent.append(" document.");
                onClickContent.append(actionFormName);
                onClickContent.append(".");
                onClickContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                onClickContent.append(".value = '");
                onClickContent.append(onClickActionValidateProperties);
                onClickContent.append("';");
            }
            
            if(onClick.length() > 0){
                onClickContent.append(" ");
                onClickContent.append(onClick);
            }
            
            onClick = onClickContent.toString();
        }

        if(getOnMouseOverAction().length() > 0){
            StringBuilder onMouseOverContent = new StringBuilder();

            onMouseOverContent.append(" document.");
            onMouseOverContent.append(actionFormName);
            onMouseOverContent.append(".");
            
            if(isForSearch)
                onMouseOverContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
            else
                onMouseOverContent.append(AttributeConstants.VALIDATE_MODEL_KEY);
            
            onMouseOverContent.append(".value = ");
            onMouseOverContent.append(onMouseOverActionValidate);
            onMouseOverContent.append(";");
            
            if(onMouseOverActionValidateProperties.length() > 0){
                onMouseOverContent.append(" document.");
                onMouseOverContent.append(actionFormName);
                onMouseOverContent.append(".");
                onMouseOverContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                onMouseOverContent.append(".value = '");
                onMouseOverContent.append(onMouseOverActionValidateProperties);
                onMouseOverContent.append("';");
            }
            
            if(onMouseOver.length() > 0){
                onMouseOverContent.append(" ");
                onMouseOverContent.append(onMouseOver);
            }
            
            onMouseOver = onMouseOverContent.toString();
        }

        if(getOnMouseOutAction().length() > 0){
            StringBuilder onMouseOutContent = new StringBuilder();

            onMouseOutContent.append(" document.");
            onMouseOutContent.append(actionFormName);
            onMouseOutContent.append(".");
            
            if(isForSearch)
                onMouseOutContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
            else
                onMouseOutContent.append(AttributeConstants.VALIDATE_MODEL_KEY);
            
            onMouseOutContent.append(".value = ");
            onMouseOutContent.append(onMouseOutActionValidate);
            onMouseOutContent.append(";");
            
            if(onMouseOutActionValidateProperties.length() > 0){
                onMouseOutContent.append(" document.");
                onMouseOutContent.append(actionFormName);
                onMouseOutContent.append(".");
                onMouseOutContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                onMouseOutContent.append(".value = '");
                onMouseOutContent.append(onMouseOutActionValidateProperties);
                onMouseOutContent.append("';");
            }
            
            if(onMouseOut.length() > 0){
                onMouseOutContent.append(" ");
                onMouseOutContent.append(onMouseOut);
            }
            
            onMouseOut = onMouseOutContent.toString();
        }

        if(onChangeAction.length() > 0){
            StringBuilder onChangeContent = new StringBuilder();

            if(onChange.length() > 0){
                onChangeContent.append(onChange);
                
                if(!onChange.endsWith(";"))
                    onChangeContent.append(";");
                
                onChangeContent.append(" ");
            }
            
            onChangeContent.append("document.");
            onChangeContent.append(actionFormName);
            onChangeContent.append(".");
            
            if(isForSearch)
                onChangeContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
            else
                onChangeContent.append(AttributeConstants.VALIDATE_MODEL_KEY);
            
            onChangeContent.append(".value = ");
            onChangeContent.append(onChangeActionValidate);
            onChangeContent.append(";");
            
            if(onChangeActionValidateProperties.length() > 0){
                onChangeContent.append(" document.");
                onChangeContent.append(actionFormName);
                onChangeContent.append(".");
                onChangeContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                onChangeContent.append(".value = '");
                onChangeContent.append(onChangeActionValidateProperties);
                onChangeContent.append("'; ");
            }
            
            if(onChangeActionForward.length() > 0){
                onChangeContent.append("document.");
                onChangeContent.append(actionFormName);
                onChangeContent.append(".");
                onChangeContent.append(AttributeConstants.FORWARD_KEY);
                onChangeContent.append(".value = '");
                onChangeContent.append(onChangeActionForward);
                onChangeContent.append("; ");
            }
            
            if(onChangeActionForwardOnFail.length() > 0){
                onChangeContent.append("document.");
                onChangeContent.append(actionFormName);
                onChangeContent.append(".");
                onChangeContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                onChangeContent.append(".value = '");
                onChangeContent.append(onChangeActionForwardOnFail);
                onChangeContent.append("; ");
            }

            if(onChangeActionUpdateViews.length() > 0){
                onChangeContent.append("document.");
                onChangeContent.append(actionFormName);
                onChangeContent.append(".");
                onChangeContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                onChangeContent.append(".value = '");
                onChangeContent.append(onChangeActionUpdateViews);
                onChangeContent.append("; ");
            }

            onChangeContent.append("document.");
            onChangeContent.append(actionFormName);
            onChangeContent.append(".");
            onChangeContent.append(AttributeConstants.ACTION_KEY);
            onChangeContent.append(".value = '");
            onChangeContent.append(onChangeAction);
            onChangeContent.append("'; submitForm(document.");
            onChangeContent.append(actionFormName);
            onChangeContent.append(");");
            
            onChange = onChangeContent.toString();
        }
        
        if(onKeyUpAction.length() > 0){
            StringBuilder onKeyUpContent = new StringBuilder();

            if(onKeyUp.length() > 0){
                onKeyUpContent.append(onKeyUp);
                
                if(!onKeyUp.endsWith(";"))
                    onKeyUpContent.append(";");
                
                onKeyUpContent.append(" ");
            }
            
            onKeyUpContent.append("document.");
            onKeyUpContent.append(actionFormName);
            onKeyUpContent.append(".");
            
            if(isForSearch)
                onKeyUpContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
            else
                onKeyUpContent.append(AttributeConstants.VALIDATE_MODEL_KEY);
            
            onKeyUpContent.append(".value = ");
            onKeyUpContent.append(onKeyUpActionValidate);
            onKeyUpContent.append(";");
            
            if(onKeyUpActionValidateProperties.length() > 0){
                onKeyUpContent.append(" document.");
                onKeyUpContent.append(actionFormName);
                onKeyUpContent.append(".");
                onKeyUpContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                onKeyUpContent.append(".value = '");
                onKeyUpContent.append(onKeyUpActionValidateProperties);
                onKeyUpContent.append("'; ");
            }

            if(onKeyUpActionForward.length() > 0){
                onKeyUpContent.append("document.");
                onKeyUpContent.append(actionFormName);
                onKeyUpContent.append(".");
                onKeyUpContent.append(AttributeConstants.FORWARD_KEY);
                onKeyUpContent.append(".value = '");
                onKeyUpContent.append(onKeyUpActionForward);
                onKeyUpContent.append("; ");
            }
            
            if(onKeyUpActionForwardOnFail.length() > 0){
                onKeyUpContent.append("document.");
                onKeyUpContent.append(actionFormName);
                onKeyUpContent.append(".");
                onKeyUpContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                onKeyUpContent.append(".value = '");
                onKeyUpContent.append(onKeyUpActionForwardOnFail);
                onKeyUpContent.append("; ");
            }

            if(onKeyUpActionUpdateViews.length() > 0){
                onKeyUpContent.append("document.");
                onKeyUpContent.append(actionFormName);
                onKeyUpContent.append(".");
                onKeyUpContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                onKeyUpContent.append(".value = '");
                onKeyUpContent.append(onKeyUpActionUpdateViews);
                onKeyUpContent.append("; ");
            }

            onKeyUpContent.append("document.");
            onKeyUpContent.append(actionFormName);
            onKeyUpContent.append(".");
            onKeyUpContent.append(AttributeConstants.ACTION_KEY);
            onKeyUpContent.append(".value = '");
            onKeyUpContent.append(onKeyUpAction);
            onKeyUpContent.append("'; submitForm(document.");
            onKeyUpContent.append(actionFormName);
            onKeyUpContent.append(");");
            
            onKeyUp = onKeyUpContent.toString();
        }
        
        if(onKeyDownAction.length() > 0){
            StringBuilder onKeyDownContent = new StringBuilder();

            if(onKeyDown.length() > 0){
                onKeyDownContent.append(onKeyDown);
                
                if(!onKeyDown.endsWith(";"))
                    onKeyDownContent.append(";");
                
                onKeyDownContent.append(" ");
            }
            
            onKeyDownContent.append("document.");
            onKeyDownContent.append(actionFormName);
            onKeyDownContent.append(".");
            
            if(isForSearch)
                onKeyDownContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
            else
                onKeyDownContent.append(AttributeConstants.VALIDATE_MODEL_KEY);
            
            onKeyDownContent.append(".value = ");
            onKeyDownContent.append(onKeyDownActionValidate);
            onKeyDownContent.append(";");
            
            if(onKeyDownActionValidateProperties.length() > 0){
                onKeyDownContent.append(" document.");
                onKeyDownContent.append(actionFormName);
                onKeyDownContent.append(".");
                onKeyDownContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                onKeyDownContent.append(".value = '");
                onKeyDownContent.append(onKeyDownActionValidateProperties);
                onKeyDownContent.append("'; ");
            }
            
            if(onKeyDownActionForward.length() > 0){
                onKeyDownContent.append("document.");
                onKeyDownContent.append(actionFormName);
                onKeyDownContent.append(".");
                onKeyDownContent.append(AttributeConstants.FORWARD_KEY);
                onKeyDownContent.append(".value = '");
                onKeyDownContent.append(onKeyDownActionForward);
                onKeyDownContent.append("; ");
            }
            
            if(onKeyDownActionForwardOnFail.length() > 0){
                onKeyDownContent.append("document.");
                onKeyDownContent.append(actionFormName);
                onKeyDownContent.append(".");
                onKeyDownContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                onKeyDownContent.append(".value = '");
                onKeyDownContent.append(onKeyDownActionForwardOnFail);
                onKeyDownContent.append("; ");
            }

            if(onKeyDownActionUpdateViews.length() > 0){
                onKeyDownContent.append("document.");
                onKeyDownContent.append(actionFormName);
                onKeyDownContent.append(".");
                onKeyDownContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                onKeyDownContent.append(".value = '");
                onKeyDownContent.append(onKeyDownActionUpdateViews);
                onKeyDownContent.append("; ");
            }

            onKeyDownContent.append("document.");
            onKeyDownContent.append(actionFormName);
            onKeyDownContent.append(".");
            onKeyDownContent.append(AttributeConstants.ACTION_KEY);
            onKeyDownContent.append(".value = '");
            onKeyDownContent.append(onKeyDownAction);
            onKeyDownContent.append("'; submitForm(document.");
            onKeyDownContent.append(actionFormName);
            onKeyDownContent.append(");");
            
            onKeyDown = onKeyDownContent.toString();
        }

        if(onKeyPressAction.length() > 0){
            StringBuilder onKeyPressContent = new StringBuilder();

            if(onKeyPress.length() > 0){
                onKeyPressContent.append(onKeyPress);
                
                if(!onKeyPress.endsWith(";"))
                    onKeyPressContent.append(";");
                
                onKeyPressContent.append(" ");
            }
            
            onKeyPressContent.append("document.");
            onKeyPressContent.append(actionFormName);
            onKeyPressContent.append(".");
            
            if(isForSearch)
                onKeyPressContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
            else
                onKeyPressContent.append(AttributeConstants.VALIDATE_MODEL_KEY);
            
            onKeyPressContent.append(".value = ");
            onKeyPressContent.append(onKeyPressActionValidate);
            onKeyPressContent.append(";");
            
            if(onKeyPressActionValidateProperties.length() > 0){
                onKeyPressContent.append(" document.");
                onKeyPressContent.append(actionFormName);
                onKeyPressContent.append(".");
                onKeyPressContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                onKeyPressContent.append(".value = '");
                onKeyPressContent.append(onKeyPressActionValidateProperties);
                onKeyPressContent.append("'; ");
            }

            if(onKeyPressActionForward.length() > 0){
                onKeyPressContent.append("document.");
                onKeyPressContent.append(actionFormName);
                onKeyPressContent.append(".");
                onKeyPressContent.append(AttributeConstants.FORWARD_KEY);
                onKeyPressContent.append(".value = '");
                onKeyPressContent.append(onKeyPressActionForward);
                onKeyPressContent.append("; ");
            }
            
            if(onKeyPressActionForwardOnFail.length() > 0){
                onKeyPressContent.append("document.");
                onKeyPressContent.append(actionFormName);
                onKeyPressContent.append(".");
                onKeyPressContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                onKeyPressContent.append(".value = '");
                onKeyPressContent.append(onKeyPressActionForwardOnFail);
                onKeyPressContent.append("; ");
            }

            if(onKeyPressActionUpdateViews.length() > 0){
                onKeyPressContent.append("document.");
                onKeyPressContent.append(actionFormName);
                onKeyPressContent.append(".");
                onKeyPressContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                onKeyPressContent.append(".value = '");
                onKeyPressContent.append(onKeyPressActionUpdateViews);
                onKeyPressContent.append("; ");
            }
            
            if(isForSearch){
                if(searchPropertiesGroupTag != null){
                    onKeyPressContent = new StringBuilder(onKeyPress);
                    onKeyPressContent.append("if(getKeyPressed(event) == 13) ");
                    onKeyPressContent.append("getObject('");
                    onKeyPressContent.append(searchPropertiesGroupTag.getSearchButtonTag().getName());
                    onKeyPressContent.append("').click();");
        
                    onKeyPress = onKeyPressContent.toString();
                }
                else{
                    onKeyPressContent.append("document.");
                    onKeyPressContent.append(actionFormName);
                    onKeyPressContent.append(".");
                    onKeyPressContent.append(AttributeConstants.ACTION_KEY);
                    onKeyPressContent.append(".value = '");
                    onKeyPressContent.append(onKeyPressAction);
                    onKeyPressContent.append("'; submitForm(document.");
                    onKeyPressContent.append(actionFormName);
                    onKeyPressContent.append(");");
                    
                    onKeyPress = onKeyPressContent.toString();
                }
            }
            else{
                onKeyPressContent.append("document.");
                onKeyPressContent.append(actionFormName);
                onKeyPressContent.append(".");
                onKeyPressContent.append(AttributeConstants.ACTION_KEY);
                onKeyPressContent.append(".value = '");
                onKeyPressContent.append(onKeyPressAction);
                onKeyPressContent.append("'; submitForm(document.");
                onKeyPressContent.append(actionFormName);
                onKeyPressContent.append(");");
                
                onKeyPress = onKeyPressContent.toString();
            }
        }

        setOnBlur(onBlur);
        setOnFocus(onFocus);
        setOnClick(onClick);
        setOnMouseOut(onMouseOut);
        setOnMouseOver(onMouseOver);
        setStyleClass(styleClass);
        setStyle(style);
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseActionFormElementTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
	    renderPatternAttribute();
	    
	    super.renderOpen();
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderAttributes()
	 */
	protected void renderAttributes() throws Throwable{
		super.renderAttributes();

		String onKeyPress = getOnKeyPress();
		
		if(onKeyPress.length() > 0){
			print(" onKeyPress=\"");
			print(onKeyPress);
			print("\"");
		}

		String onKeyDown = getOnKeyDown();
		
		if(onKeyDown.length() > 0){
			print(" onKeyDown=\"");
			print(onKeyDown);
			print("\"");
		}
		
		String onKeyUp = getOnKeyUp();

		if(onKeyUp.length() > 0){
			print(" onKeyUp=\"");
			print(onKeyUp);
			print("\"");
		}
		
		String onChange = getOnChange();

		if(onChange.length() > 0){
			print(" onChange=\"");
			print(onChange);
			print("\"");
		}
	}
	
	protected void renderLabelAttribute() throws Throwable{
        Tag parent = getParent();
        
        if(getPropertyInfo() != null && (parent == null || (parent instanceof GridColumnTag) || !(parent instanceof BaseOptionsPropertyTag)))
            super.renderLabelAttribute();
	}
	
	/**
	 * Renderiza o atributo que define a máscara de formatação do componente visual.
	 * 
	 * @throws Throwable
	 */
	protected void renderPatternAttribute() throws Throwable{
        String pattern = getPattern();
        
        if(pattern.length() > 0 && isEnabled()){
            Tag parent = getParent();
            
            if(getPropertyInfo() != null && (parent == null || (parent instanceof GridColumnTag) || !(parent instanceof BaseOptionsPropertyTag))){
                StringBuilder tagName = new StringBuilder();
    
                tagName.append(getName());
                tagName.append(".");
                tagName.append(AttributeConstants.PATTERN_KEY);

                HiddenPropertyTag patternTag = new HiddenPropertyTag();
                
                patternTag.setPageContext(pageContext);
                patternTag.setName(tagName.toString());
                patternTag.setValue(pattern);
                patternTag.doStartTag();
                patternTag.doEndTag();
            }
        }
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		print("<input");

		renderAttributes();
		renderValue();

		println(">");
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseActionFormElementTag#renderLabelBody()
	 */
	protected void renderLabelBody() throws Throwable{
	    PropertyInfo propertyInfo = getPropertyInfo();
	    
	    if(propertyInfo != null && !isForSearch()){
	        ValidationType validations[] = propertyInfo.getValidations();
	        
	        if(validations != null){
    	        for(ValidationType validation : validations){
    	            if(validation == ValidationType.REQUIRED){
    	                println("(*) ");
    	                
    	                break;
    	            }
    	        }
	        }
	    }
	    
	    super.renderLabelBody();
	}

	/**
	 * Exibe as mensagens de validação para o componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderValidationMessages() throws Throwable{
		List<ActionFormMessage> validationMessages = actionFormMessageController.getValidationMessages(getName());
		
		if(validationMessages != null && validationMessages.size() > 0){
			println("</td>");
			println("</tr>");

			println("<tr>");
			
			PositionType labelPosition = getLabelPositionType();
			
	        if(labelPosition == PositionType.LEFT)
	            println("<td></td>");
			
			print("<td");
			
			if(validationStyle.length() > 0){
			    print(" style=\"");
			    print(validationStyle);
			    print("\"");
			}
			    
			print(" class=\"");
			print(validationStyleClass);
			println("\">");
			
			PropertiesResource    resources         = getI18nResource();
			PropertiesResource    defaultResources  = getDefaultI18nResource();
			ActionFormMessage     validationMessage = null;
			ActionFormMessageType type              = null;
			String                key               = "";
			String                message           = "";
			Locale                currentLanguage   = systemController.getCurrentLanguage();
			StringBuilder         propertyId        = null;
			
			for(Integer cont = 0 ; cont < validationMessages.size() ; cont++){
				validationMessage = validationMessages.get(cont);
                key               = validationMessage.getKey();
                type              = validationMessage.getType();
                
                if(!validationMessage.displayed()){
                    if(propertyId == null)
                        propertyId = new StringBuilder();
                    else
                        propertyId.delete(0, propertyId.length());
                    
                    propertyId.append(type);
                    propertyId.append(".");
                    propertyId.append(key);
                    
                    message = resources.getProperty(propertyId.toString(), false);
                    
                    if(message == null)
                        message = StringUtil.trim(defaultResources.getProperty(propertyId.toString()));
                    
                    message = PropertyUtil.fillPropertiesInString(validationMessage, message, currentLanguage);
                    message = ActionFormMessageUtil.fillAttributesInString(validationMessage, message, currentLanguage);
    				
    				print("&#8226;&nbsp;");
        			print(message);
        			println("<br/>");

        			validationMessage.setDisplayed(true);
                }
			}
			
            if(labelPosition == PositionType.RIGHT)
                println("</td></td>");
		}
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseActionFormElementTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		if(showValidationMessages())
			renderValidationMessages();

		super.renderClose();
		
		if(focus()){
			StringBuilder focusScriptContent = new StringBuilder();
			
			focusScriptContent.append("focusObject('");
			focusScriptContent.append(getName());
			focusScriptContent.append("');");
			
            ScriptTag focusScriptTag = new ScriptTag();

            focusScriptTag.setPageContext(pageContext);
			focusScriptTag.setContent(focusScriptContent.toString());
			focusScriptTag.doStartTag();
			focusScriptTag.doEndTag();
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();
		
        setValueMap("");
        setValueMapScope("");
        setValueMapInstance(null);
        setOnBlurActionValidate(false);
        setOnBlurActionValidateProperties("");
        setOnFocusActionValidate(false);
        setOnFocusActionValidateProperties("");
        setOnClickActionValidate(false);
        setOnClickActionValidateProperties("");
        setOnMouseOverActionValidate(false);
        setOnMouseOverActionValidateProperties("");
        setOnMouseOutActionValidate(false);
        setOnMouseOutActionValidateProperties("");
		setOnChange("");
		setOnChangeAction("");
		setOnChangeActionForward("");
		setOnChangeActionForwardOnFail("");
		setOnChangeActionUpdateViews("");
		setOnChangeActionValidate(false);
		setOnChangeActionValidateProperties("");
		setOnKeyPress("");
        setOnKeyPressActionForward("");
        setOnKeyPressActionForwardOnFail("");
        setOnKeyPressActionUpdateViews("");
        setOnKeyPressActionValidate(false);
        setOnKeyPressActionValidateProperties("");
		setOnKeyDown("");
        setOnKeyDownActionForward("");
        setOnKeyDownActionForwardOnFail("");
        setOnKeyDownActionUpdateViews("");
        setOnKeyDownActionValidate(false);
        setOnKeyDownActionValidateProperties("");
		setOnKeyUp("");
        setOnKeyUpActionForward("");
        setOnKeyUpActionForwardOnFail("");
        setOnKeyUpActionUpdateViews("");
        setOnKeyUpActionValidate(false);
        setOnKeyUpActionValidateProperties("");
		setPattern("");
		setFocus(false);
		setValue(null);
		setValidationStyle("");
		setValidationStyleClass("");
        setInvalidPropertyMessage("");
		setShowValidationMessages(false);
		setSearchPropertiesGroupTag(null);
		setPropertyInfo(null);
	}
}