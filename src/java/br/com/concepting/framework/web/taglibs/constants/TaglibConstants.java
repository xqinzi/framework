package br.com.concepting.framework.web.taglibs.constants;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.web.constants.SystemConstants;

/**
 * Classe que define as constantes utilizadas pelos componentes visuais.
 * 
 * @author fvilarinho
 * @since 3.0
 */ 
public abstract class TaglibConstants extends SystemConstants{
    /**
     * Constante que define o identificador do estilo CSS default para o componente visual accordion (conjunto de seções).
     */
    public static final String DEFAULT_ACCORDION_STYLE_CLASS = "accordion";
    
    /**
     * Constante que define o identificador do arquivo de internacionalização default para o componente visual accordion (guia de seções).
     */
    public static final String DEFAULT_ACCORDION_I18N_RESOURCE_ID = DEFAULT_I18N_RESOURCES_DIR.concat("accordion");
    
    /**
     * Constante que define o identificador do arquivo de script default para o componente visual accordion (guia de seções).
     */
    public static final String DEFAULT_ACCORDION_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("accordion.js");
    
    /**
     * Constante que define o identificador do arquivo de estilos CSS default para o componente visual accordion (guia de seções).
     */
    public static final String DEFAULT_ACCORDION_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("accordion.css");

    /**
     * Constante que define o identificador do estilo CSS default para o componente visual calendar (calendário).
     */
    public static final String DEFAULT_CALENDAR_STYLE_CLASS = "calendar";
    
    /**
     * Constante que define o identificador do estilo CSS default para o conteúdo do componente visual calendar (calendário).
     */
    public static final String DEFAULT_CALENDAR_CONTENT_STYLE_CLASS = "calendarContent";

    /**
     * Constante que define o identificador do estilo CSS default para os textos do componente visual calendar (calendário).
     */
    public static final String DEFAULT_CALENDAR_TEXT_STYLE_CLASS = "calendarText";
    
    /**
     * Constante que define o identificador do estilo CSS default para o texto da data selecionada 
     * do componente visual calendar (calendário).
     */
    public static final String DEFAULT_CALENDAR_DISPLAY_STYLE_CLASS = "calendarDisplay";
    
    /**
     * Constante que define o identificador do estilo CSS default para o texto dos dias 
     * do componente visual calendar (calendário).
     */
    public static final String DEFAULT_CALENDAR_DAYS_STYLE_CLASS = "calendarDays";
    
    /**
     * Constante que define o identificador do arquivo de internacionalização default para o componente visual calendar (calendário).
     */
    public static final String DEFAULT_CALENDAR_I18N_RESOURCE_ID = DEFAULT_I18N_RESOURCES_DIR.concat("calendar");
    
    /**
     * Constante que define o identificador do arquivo de script default para o componente visual calendar (calendário).
     */
    public static final String DEFAULT_CALENDAR_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("calendar.js");
    
    /**
     * Constante que define o identificador do arquivo de estilos CSS default para o componente visual calendar (calendário).
     */
    public static final String DEFAULT_CALENDAR_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("calendar.css");
    
    /**
     * Constante que define o identificador do estilo CSS default para o componente visual dialogBox (caixa de diálogos).
     */
    public static final String DEFAULT_DIALOG_BOX_STYLE_CLASS = "dialogBox";
    
    /**
     * Constante que define o identificador do estilo CSS default para o conteúdo do componente visual dialogBox (caixa de diálogos).
     */
    public static final String DEFAULT_DIALOG_BOX_CONTENT_STYLE_CLASS = "dialogBoxContent";

    /**
     * Constante que define o identificador do estilo CSS default para o título do componente visual dialogBox (caixa de diálogos).
     */
    public static final String DEFAULT_DIALOG_BOX_TITLE_STYLE_CLASS = "dialogBoxTitle";

    /**
     * Constante que define o identificador do estilo CSS default para o texto do componente visual dialogBox (caixa de diálogos).
     */
    public static final String DEFAULT_DIALOG_BOX_TEXT_STYLE_CLASS = "dialogBoxText";

    /**
     * Constante que define o identificador do arquivo de script default para o componente visual dialogBox (caixa de diálogo).
     */
    public static final String DEFAULT_DIALOG_BOX_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("dialogBox.js");
    
    /**
     * Constante que define o identificador do arquivo de estilos CSS default para o componente visual dialogBox (caixa de diálogo).
     */
    public static final String DEFAULT_DIALOG_BOX_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("dialogBox.css");

    /**
     * Constante que define o identificador do estilo CSS default para o componente visual clock (relógio).
     */
    public static final String DEFAULT_CLOCK_STYLE_CLASS = "clock";
    
    /**
     * Constante que define o identificador do estilo CSS default para o componente visual downloadBox (caixa de download de arquivo).
     */
    public static final String DEFAULT_DOWNLOAD_BOX_STYLE_CLASS = "downloadBox";
    
    /**
     * Constante que define o identificador do estilo CSS default do campo detalhes da exceção para o 
     * componente messageBox (caixa de mensagens).
     */
    public static final String DEFAULT_ERROR_TRACE_STYLE_CLASS = "errorTrace";

    /**
     * Constante que define o identificador do estilo de cabeçalho default do componente visual grid (tabela de dados).
     */
    public static final String DEFAULT_GRID_HEADER_STYLE_CLASS = "gridHeader";
    
    /**
     * Constante que define o identificador do estilo CSS default dos links do cabeçalho do componente visual grid (tabela de dados).
     */
    public static final String DEFAULT_GRID_HEADER_LINK_STYLE_CLASS = "gridHeaderLink";
    
    /**
     * Constante que define o identificador do estilo CSS default dos agrupamentos de registros do 
     * componente visual grid (tabela de dados).
     */
    public static final String DEFAULT_GRID_AGGREGATE_STYLE_CLASS = "gridAggregate";
    
    /**
     * Constante que define o identificador do estilo CSS default para os controles do componente visual grid (tabela de dados).
     */
    public static final String DEFAULT_GRID_CONTROL_STYLE_CLASS = "gridControl";
    
    /**
     * Constante que define o identificador do estilo CSS default para os registros do componente visual grid (tabela de dados).
     */
    public static final String DEFAULT_GRID_DETAIL_STYLE_CLASS = "gridDetail";
    
    /**
     * Constante que define o identificador do estilo CSS default para links dos registros do 
     * componente visual grid (tabela de dados).
     */
    public static final String DEFAULT_GRID_DETAIL_LINK_STYLE_CLASS = "gridDetailLink";
    
    /**
     * Constante que define o identificador para o arquivo de estilos CSS default para o componente visual grid (tabela de dados).
     */
    public static final String DEFAULT_GRID_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("grid.css");

    /**
     * Constante que define o identificador para o arquivo de script default para o componente visual grid (tabela de dados).
     */
    public static final String DEFAULT_GRID_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("grid.js");
    
    /**
     * Constante que define o identificador do estilo CSS default para o componente visual guides (guias).
     */
    public static final String DEFAULT_GUIDES_STYLE_CLASS = "guides";

    /**
     * Constante que define o identificador do estilo CSS default para a definição dos botões 
     * do componente visual guides (guias).
     */
    public static final String DEFAULT_GUIDES_BUTTONS_STYLE_CLASS = "guidesButtons";
    
    /**
     * Constante que define o identificador do estilo CSS default para o conteúdo do componente visual guide (guia).
     */
    public static final String DEFAULT_GUIDE_CONTENT_STYLE_CLASS = "guideContent";
    
    /**
     * Constante que define o identificador do estilo CSS default para os labels de um guide (guia) do 
     * componente visual guides (guias).
     */
    public static final String DEFAULT_GUIDE_LABEL_STYLE_CLASS = "guideLabel";
    
    /**
     * Constante que define o identificador do arquivo de internacionalização default do componente visual guides (guias).
     */
    public static final String DEFAULT_GUIDES_I18N_RESOURCE_ID = DEFAULT_I18N_RESOURCES_DIR.concat("guides");
    
    /**
     * Constante que define o identificador para o arquivo de script default para o componente visual gyudes (guias).
     */
    public static final String DEFAULT_GUIDES_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("guides.js");

    /**
     * Constante que define o identificador para o arquivo de estilos CSS default para o componente visual guides (guias).
     */
    public static final String DEFAULT_GUIDES_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("guides.css");
    
    /**
     * Constante que define o identificador do estilo CSS default para a pré-visualização pequena de uma imagem.
     */
    public static final String DEFAULT_IMAGE_THUMBNAIL_STYLE_CLASS = "imageThumbnail";

    /**
     * Constante que define o identificador do estilo CSS default para os labels dos componentes visuais.
     */
    public static final String DEFAULT_LABEL_STYLE_CLASS = "label";
    
    /**
     * Constante que define o identificador do estilo CSS default para um link.
     */
    public static final String DEFAULT_LINK_STYLE_CLASS = "link";
    
    /**
     * Constante que define o identificador do estilo CSS default para a caixa de carregamento da página.
     */
    public static final String DEFAULT_LOADING_BOX_STYLE_CLASS = "loadingBox";
    
    /**
     * Constante que define o identificador do estilo CSS default para a mensagem de carregamento com
     * sucesso da caixa de carregamento da página.
     */
    public static final String DEFAULT_LOADING_BOX_INFO_STYLE_CLASS = "loadingBoxInfo";
    
    /**
     * Constante que define o identificador do estilo CSS default para a mensagem de carregamento com
     * erro da caixa de carregamento da página.
     */
    public static final String DEFAULT_LOADING_BOX_ICON_STYLE_CLASS = "loadingBoxIcon";

    /**
     * Constante que define o identificador do estilo CSS default para o texto da caixa de carregamento da página.
     */
    public static final String DEFAULT_LOADING_BOX_TEXT_STYLE_CLASS = "loadingBoxText";
    
    /**
     * Constante que define o identificador do estilo CSS default do componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENU_BAR_STYLE_CLASS = "menuBar";
    
    /**
     * Constante que define o identificador do estilo CSS default do conteúdo de um ítem de menu do 
     * componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENU_BAR_CONTENT_STYLE_CLASS = "menuBarContent";
    
    /**
     * Constante que define o identificador do estilo CSS default do separador de itens de menu do 
     * componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENU_BAR_SEPARATOR_STYLE_CLASS = "menuBarSeparator";
    
    /**
     * Constante que define o identificador do estilo CSS default dos submenus do componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENU_BOX_STYLE_CLASS = "menuBox";

    /**
     * Constante que define o identificador do estilo CSS default do conteúdo de um ítem de menu do 
     * componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENU_BOX_CONTENT_STYLE_CLASS = "menuBoxContent";
    
    /**
     * Constante que define o identificador do estilo CSS default do separador de itens de menu do 
     * componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENU_BOX_SEPARATOR_STYLE_CLASS = "menuBoxSeparator";

    /**
     * Constante que define o identificador do estilo CSS default de um item de menu do componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENU_ITEM_STYLE_CLASS = "menuItem";
    
    /**
     * Constante que define o identificador do estilo CSS default de um item de menu selecionado do componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENU_ITEM_SELECTED_STYLE_CLASS = "menuItemSelected";
    
    /**
     * Constante que define o identificador do estilo CSS default para o ícone do item de menu do componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENU_ITEM_ICON_STYLE_CLASS = "menuItemIcon";
    
    /**
     * Constante que define o identificador do arquivo de internacionalização default para o componenten visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENU_BAR_I18N_RESOURCE_ID = DEFAULT_I18N_RESOURCES_DIR.concat("menuBar");
    
    /**
     * Constante que define o identificador do arquivo de script de página default para o componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENU_BAR_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("menuBar.js");
    
    /**
     * Constante que define o identificador do arquivo de estilos CSS default para o componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENU_BAR_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("menuBar.css");
    
    /**
     * Constante que define o identificador do arquivo de internacionalização default para o componente messageBox (caixa de mensagens).
     */
    public static final String DEFAULT_MESSAGE_BOX_I18N_RESOURCE_ID = DEFAULT_I18N_RESOURCES_DIR.concat("messageBox");
    
    /**
     * Constante que define o identificador do arquivo de script de página default para o componente messageBox (caixa de mensagens).
     */
    public static final String DEFAULT_MESSAGE_BOX_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("messageBox.js");

    /**
     * Constante que define o identificador do arquivo de estilos CSS default para o componente messageBox (caixa de mensagens).
     */
    public static final String DEFAULT_MESSAGE_BOX_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("messageBox.css");

    /**
     * Constante que define o identificador do estilo CSS default para o label do componente visual options (opções de seleção).
     */
    public static final String DEFAULT_OPTIONS_LABEL_STYLE_CLASS = "groupLabel";

    /**
     * Constante que define o número de opções por linha do componente options (opções de seleção).
     */
    public static final Integer DEFAULT_OPTIONS_PER_ROW = 1;
    
    /**
     * Constante que define o identificador do estilo CSS default para o componente visual options (opções de seleção).
     */
    public static final String DEFAULT_OPTIONS_STYLE_CLASS = "group";
    
    /**
     * Constante que define o identificador do estilo CSS para o componente visual pager (paginador).
     */
    public static final String DEFAULT_PAGER_STYLE_CLASS = "pager";

    /**
     * Constante que define o identificador do estilo CSS para o texto contendo a página corrente/número total de páginas
     * do componente visual pager (paginador).
     */
    public static final String DEFAULT_PAGER_DISPLAY_STYLE_CLASS = "pagerDisplay";

    /**
     * Constante que define o identificador da chave de internacionalização para o label do campo itens por página do componente pager (paginador).
     */
    public static final String DEFAULT_PAGER_ITEMS_PER_PAGE_LABEL_KEY = "itemsPerPage.label";
    
    /**
     * Constante que define o identificador da chave de internacionalização para o tooltip do campo itens por página do componente pager (paginador).
     */
    public static final String DEFAULT_PAGER_ITEMS_PER_PAGE_TOOLTIP_KEY = "itemsPerPage.tooltip";
    
    /**
     * Constante que define o número de itens por página default para o componente pager (paginador).
     */
    public static final Integer DEFAULT_PAGER_ITEMS_PER_PAGE_SIZE = 5;

    /**
     * Constante que define o identificador do arquivo de internacionalização default para o componente pager (paginador).
     */
    public static final String DEFAULT_PAGER_I18N_RESOURCE_ID = DEFAULT_I18N_RESOURCES_DIR.concat("pager");

    /**
     * Constante que define o identificador do arquivo de script de página default para o componente pager (paginador).
     */
    public static final String DEFAULT_PAGER_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("pager.js");
    
    /**
     * Constante que define o identificador do arquivo de estilos CSS default para o componente pager (paginador).
     */
    public static final String DEFAULT_PAGER_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("pager.css");
    
    /**
     * Constante que define o identificador do estilo CSS para tabelas na página.
     */
    public static final String DEFAULT_PANEL_STYLE_CLASS = "panel";
    
    /**
     * Constante que define o identificador do estilo CSS default para a lateral esquerda da barra de progressão.
     */
    public static final String DEFAULT_LEFT_PROGRESS_BAR_STYLE_CLASS = "leftProgressBar";
    
    /**
     * Constante que define o identificador do estilo CSS default para a barra de progressão vazia.
     */
    public static final String DEFAULT_EMPTY_PROGRESS_BAR_STYLE_CLASS = "emptyProgressBar";
    
    /**
     * Constante que define o identificador do estilo CSS default para a lateral direita da barra de progressão.
     */
    public static final String DEFAULT_RIGHT_PROGRESS_BAR_STYLE_CLASS = "rightProgressBar";

    /**
     * Constante que define o identificador do estilo CSS default para o texto da barra de progressão.
     */
    public static final String DEFAULT_PROGRESS_BAR_TEXT_STYLE_CLASS = "progressBarText";
    
    /**
     * Constante que define o identificador do estilo CSS default para a barra de progressão de alerta (atingiu o threshold warning)
     * do componente visual progressBar (barra de progressão).
     */
    public static final String DEFAULT_WARNING_PROGRESS_BAR_STYLE_CLASS = "warningProgressBar";
    
    /**
     * Constante que define o identificador do estilo CSS default para a barra de progressão crítica (atingiu o threshold crítico)
     * do componente visual progressBar (barra de progressão).
     */
    public static final String DEFAULT_CRITICAL_PROGRESS_BAR_STYLE_CLASS = "criticalProgressBar";
    
    /**
     * Constante que define o identificador do estilo CSS default para a barra de progressão normal (sem atingir os thresholds) do
     * componente visual progressBar (barra de progressão).
     */
    public static final String DEFAULT_NORMAL_PROGRESS_BAR_STYLE_CLASS = "normalProgressBar";
    
    /**
     * Constante que define o identificador do estilo CSS default para a barra de progressão comum (sem thresholds) do
     * componente visual progressBar (barra de progressão).
     */
    public static final String DEFAULT_GENERAL_PROGRESS_BAR_STYLE_CLASS = "generalProgressBar";
    
    /**
     * Constante que define o valor máximo default para o componente progressBar (barra de progresso).
     */
    public static final Double DEFAULT_PROGRESS_BAR_MAXIMUM_VALUE = 100d;
    
    /**
     * Constante que define o identificador o arquivo de internacionalização default para o componente progressBar (barra de progresso).
     */
    public static final String DEFAULT_PROGRESS_BAR_I18N_RESOURCE_ID = DEFAULT_I18N_RESOURCES_DIR.concat("progressBar");

    /**
     * Constante que define o identificador o arquivo de script de página default para o componente progressBar (barra de progresso).
     */
    public static final String DEFAULT_PROGRESS_BAR_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("progressBar.js");
    
    /**
     * Constante que define o identificador o arquivo de estilos CSS default para o componente progressBar (barra de progresso).
     */
    public static final String DEFAULT_PROGRESS_BAR_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("progressBar.css");

    /**
     * Constante que define o identificador do estilo CSS default do label do componente searchPropertiesGroup (grupo de propriedades de pesquisa). 
     */
    public static final String DEFAULT_SEARCH_PROPERTIES_GROUP_LABEL_STYLE_CLASS = "groupLabel";
    
    /**
     * Constante que define o identificador do estilo CSS default para o componente searchPropertiesGroup (grupo de propriedades de pesquisa). 
     */
    public static final String DEFAULT_SEARCH_PROPERTIES_GROUP_STYLE_CLASS = "group";

    /**
     * Constante que define o identificador do estilo CSS default para o cabeçalho da seção.
     */
    public static final String DEFAULT_SECTION_HEADER_STYLE_CLASS = AttributeConstants.SECTION_HEADER_KEY;
    
    /**
     * Constante que define o identificador do estilo CSS default para o conteúdo da seção.
     */
    public static final String DEFAULT_SECTION_CONTENT_STYLE_CLASS = AttributeConstants.SECTION_CONTENT_KEY;
    
    /**
     * Constante que define o estilo CSS default para uma sombra a ser carregada ao carregar uma modal.
     */
    public static final String DEFAULT_SHADE_STYLE_CLASS = "shade";
    
    /**
     * Constante que define o identificador do diretório de armazenamento dos skins (temas).
     */
    public static final String DEFAULT_SKINS_RESOURCES_DIR = "skins/";

    /**
     * Constante que define o identificador o arquivo de internacionalização default para o componente spinner (Controle de valores numéricos).
     */
    public static final String DEFAULT_SPINNER_I18N_RESOURCE_ID = DEFAULT_I18N_RESOURCES_DIR.concat("spinner");
    
    /**
     * Constante que define o identificador o arquivo de script de página default para o componente spinner (Controle de valores numéricos).
     */
    public static final String DEFAULT_SPINNER_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("spinner.js");
    
    /**
     * Constante que define o identificador o arquivo de estilos CSS default para o componente spinner (Controle de valores numéricos).
     */
    public static final String DEFAULT_SPINNER_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("spinner.css");
    
    /**
     * Constante que define o identificador do estilo CSS default para o botão de acréscimo do componente spinner (Controle de valores numéricos).
     */
    public static final String DEFAULT_ADD_SPINNER_BUTTON = "addSpinnerButton";
    
    /**
     * Constante que define o identificador do estilo CSS default para o botão de decréscimo do componente spinner (Controle de valores numéricos).
     */
    public static final String DEFAULT_SUBTRACT_SPINNER_BUTTON = "subtractSpinnerButton";
    
    /**
     * Constante que define o identificador do estilo CSS default para o label dos botões do componente spinner (Controle de valores numéricos).
     */
    public static final String DEFAULT_SPINNER_BUTTON_LABEL = "spinnerButtonLabel";
    
    /**
     * Constante que define o identificador do estilo CSS default para o componente slider (Barra de deslize).
     */
    public static final String DEFAULT_SLIDER_BAR_STYLE_CLASS = "sliderBar";
    
    /**
     * Constante que define o identificador do estilo CSS default para a barra de controle do componente slider (Barra de deslize).
     */
    public static final String DEFAULT_SLIDER_BAR_CONTROL_STYLE_CLASS = "sliderBarControl";
    
    /**
     * Constante que define o identificador do estilo CSS default para o lado esquerdo do componente slider (Barra de deslize).
     */
    public static final String DEFAULT_LEFT_SLIDER_BAR_STYLE_CLASS = "leftSliderBar";
    
    /**
     * Constante que define o identificador do estilo CSS default para o corpo do componente slider (Barra de deslize).
     */
    public static final String DEFAULT_SLIDER_BAR_BODY_STYLE_CLASS = "sliderBarBody";

    /**
     * Constante que define o identificador do estilo CSS default para o lado direito do componente slider (Barra de deslize).
     */
    public static final String DEFAULT_RIGHT_SLIDER_BAR_STYLE_CLASS = "rightSliderBar";

    /**
     * Constante que define o identificador o arquivo de script de página default para o componente slider (Barra de deslize).
     */
    public static final String DEFAULT_SLIDER_BAR_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("slider.js");
    
    /**
     * Constante que define o identificador o arquivo de estilos CSS default para o componente slider (Barra de deslize).
     */
    public static final String DEFAULT_SLIDER_BAR_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("slider.css");

    /**
     * Constante que define o estilo CSS default para um tracejado.
     */
    public static final String DEFAULT_TRACE_STYLE_CLASS = "trace";

    /**
     * Constante que define o estilo CSS default para o componente visual treeView (árvore).
     */
    public static final String DEFAULT_TREE_VIEW_STYLE_CLASS = "treeView";
    
    /**
     * Constante que define o estilo CSS default para o conteúdo do componente visual treeView (árvore).
     */
    public static final String DEFAULT_TREE_VIEW_CONTENT_STYLE_CLASS = "treeViewContent";
    
    /**
     * Constante que define o estilo CSS default para um nó sem filhos do componente visual treeView (árvore).
     */
    public static final String DEFAULT_NODE_ICON_STYLE_CLASS = "nodeIcon";

    /**
     * Constante que define o estilo CSS default para o label de um nó do componente visual treeView (árvore).
     */
    public static final String DEFAULT_NODE_LABEL_STYLE_CLASS = "nodeLabel";
    
    /**
     * Constante que define o estilo CSS default para o label de um nó selecionado do componente visual treeView (árvore).
     */
    public static final String DEFAULT_NODE_LABEL_SELECTED_STYLE_CLASS = "nodeLabelSelected";
    
    /**
     * Constante que define o estilo CSS default para o ícone de um nó não expandido do componente visual treeView (árvore).
     */
    public static final String DEFAULT_COLLAPSED_NODE_ICON_STYLE_CLASS = "collapsedNodeIcon";

    /**
     * Constante que define o estilo CSS default para o ícone de um nó expandido do componente visual treeView (árvore).
     */
    public static final String DEFAULT_EXPANDED_NODE_ICON_STYLE_CLASS = "expandedNodeIcon";

    /**
     * Constante que define o estilo CSS default para o ícone de um nó expandido do componente visual treeView (árvore).
     */
    public static final String DEFAULT_OPENED_NODE_ICON_STYLE_CLASS  = "openedNodeIcon";
    
    /**
     * Constante que define o estilo CSS default para o ícone de um nó não expandido do componente visual treeView (árvore).
     */
    public static final String DEFAULT_CLOSED_NODE_ICON_STYLE_CLASS  = "closedNodeIcon";
    
    /**
     * Constante que define o identificador do arquivo de script de página default para o componente treeView (árvore).
     */
    public static final String DEFAULT_TREE_VIEW_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("treeView.js");
    
    /**
     * Constante que define o identificador do arquivo de estilos CSS default para o componente treeView (árvore).
     */
    public static final String DEFAULT_TREE_VIEW_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("treeView.css");
    
    /**
     * Constante que define o identificador do estilo CSS default para o componente visual uploadBox (caixa de upload de arquivo).
     */
    public static final String DEFAULT_UPLOAD_BOX_STYLE_CLASS = "uploadBox";
    
    /**
     * Constante que define o identificador do estilo CSS para a mensagem de validação de uma propriedade do modelo de dados.
     */
    public static final String DEFAULT_VALIDATION_LABEL_STYLE_CLASS = "validationLabel";
    
    /**
     * Constante que define o identificador da chave do controle de paginadores.
     */
    public static final String PAGER_MAP_KEY = "pagerMap";
}