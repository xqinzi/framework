package br.com.concepting.framework.web.taglibs.constants;

import br.com.concepting.framework.util.constants.AttributeConstants;
import br.com.concepting.framework.web.constants.SystemConstants;

/**
 * Classe que define as constantes utilizadas pelos componentes visuais.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class TaglibConstants extends SystemConstants{
    public static final String DEFAULT_ACCORDION_STYLE_CLASS = "accordion";
    
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
     * Constante que define o identificador do arquivo de script default para o componente visual dialogBox (caixa de diálogo).
     */
    public static final String DEFAULT_DIALOG_BOX_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("dialogBox.js");
    
    /**
     * Constante que define o identificador do arquivo de estilos CSS default para o componente visual dialogBox (caixa de diálogo).
     */
    public static final String DEFAULT_DIALOG_BOX_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("dialogBox.css");

    /**
     * Constante que define o identificador do estilo default (CSS) para o componente visual clock (relógio).
     */
    public static final String DEFAULT_CLOCK_STYLE_CLASS = "clock";
    
    /**
     * Constante que define o identificador do estilo default (CSS) para o componente visual downloadBox (caixa de download).
     */
    public static final String DEFAULT_DOWNLOAD_BOX_STYLE_CLASS = "downloadBox";
    
    /**
     * Constante que define o identificador do estilo de cabeçalho default do componente visual grid (tabela de dados).
     */
    public static final String DEFAULT_GRID_HEADER_STYLE_CLASS = "gridHeader";
    
    /**
     * Constante que define o identificador do estilo default (CSS) dos links do cabeçalho do componente visual grid (tabela de dados).
     */
    public static final String DEFAULT_GRID_HEADER_LINK_STYLE_CLASS = "gridHeaderLink";
    
    /**
     * Constante que define o identificador do estilo default (CSS) dos agrupamentos de registros do componente visual grid (tabela de dados).
     */
    public static final String DEFAULT_GRID_AGGREGATE_STYLE_CLASS = "gridAggregate";
    
    /**
     * Constante que define o identificador do estilo default (CSS) para os registros do componente visual grid (tabela de dados).
     */
    public static final String DEFAULT_GRID_DETAIL_STYLE_CLASS = "gridDetail";
    
    /**
     * Constante que define o identificador do estilo default (CSS) para links dos registros do componente visual grid (tabela de dados).
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
     * Constante que define o identificador do estilo default (CSS) para os labels de um guide (guia) do componente visual guides (guias).
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
     * Constante que define o identificador do estilo default (CSS) para os labels dos componentes visuais.
     */
    public static final String DEFAULT_LABEL_STYLE_CLASS = "label";

    /**
     * Constante que define o identificador do estilo default (CSS) do componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENUBAR_STYLE_CLASS = "menuBar";

    /**
     * Constante que define o identificador do estilo default (CSS) dos submenus do componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENUBOX_STYLE_CLASS = "menuBox";
    
    /**
     * Constante que define o identificador do estilo default (CSS) de um item de menu do componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENUITEM_STYLE_CLASS = "menuItem";
    
    /**
     * Constante que define o identificador do estilo default (CSS) de um item de menu selecionado do componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENUITEM_SELECTED_STYLE_CLASS = "menuItemSelected";
    
    /**
     * Constante que define o identificador do arquivo de internacionalização default para o componenten visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENUBAR_I18N_RESOURCE_ID = DEFAULT_I18N_RESOURCES_DIR.concat("menuBar");
    
    /**
     * Constante que define o identificador do arquivo de script de página default para o componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENUBAR_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("menuBar.js");
    
    /**
     * Constante que define o identificador do arquivo de estilos default (CSS) para o componente visual menuBar (barra de menus).
     */
    public static final String DEFAULT_MENUBAR_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("menuBar.css");
    
    /**
     * Constante que define o identificador da chave do label do campo erro para o componente messageBox (caixa de mensagens).
     */
    public static final String DEFAULT_MESSAGE_BOX_ERROR_ID_LABEL_KEY = "errorId.label";

    /**
     * Constante que define o identificador da chave do label do campo detalhes do erro para o componente messageBox (caixa de mensagens).
     */
    public static final String DEFAULT_MESSAGE_BOX_ERROR_TRACE_LABEL_KEY = "errorTrace.label";

    /**
     * Constante que define o identificador do estilo (CSS) default do campo detalhes da exceção para o componente messageBox (caixa de mensagens).
     */
    public static final String DEFAULT_MESSAGE_BOX_ERROR_TRACE_STYLE_CLASS = "errorTrace";
    
    /**
     * Constante que define o identificador do arquivo de internacionalização default para o componente messageBox (caixa de mensagens).
     */
    public static final String DEFAULT_MESSAGE_BOX_I18N_RESOURCE_ID = DEFAULT_I18N_RESOURCES_DIR.concat("messageBox");
    
    /**
     * Constante que define o identificador do arquivo de script de página default para o componente messageBox (caixa de mensagens).
     */
    public static final String DEFAULT_MESSAGE_BOX_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("messageBox.js");

    /**
     * Constante que define o identificador do arquivo de estilos (CSS) default para o componente messageBox (caixa de mensagens).
     */
    public static final String DEFAULT_MESSAGE_BOX_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("messageBox.css");
    
    /**
     * Constante que define o identificador do estilo (CSS) default do label do componente options (opções de seleção).
     */
    public static final String DEFAULT_OPTIONS_LABEL_STYLE_CLASS = "groupLabel";
    
    /**
     * Constante que define o número de opções por linha do componente options (opções de seleção).
     */
    public static final Integer DEFAULT_OPTIONS_PER_ROW = 1;

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
     * Constante que define o identificador do arquivo de estilos (CSS) default para o componente pager (paginador).
     */
    public static final String DEFAULT_PAGER_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("pager.css");
    
    /**
     * Constante que define o identificador do estilo (CSS) para tabelas na página.
     */
    public static final String DEFAULT_PANEL_STYLE_CLASS = "panel";
    
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
     * Constante que define o identificador o arquivo de estilos (CSS) default para o componente progressBar (barra de progresso).
     */
    public static final String DEFAULT_PROGRESS_BAR_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("progressBar.css");

    /**
     * Constante que define o identificador do estilo (CSS) default do label do componente searchPropertiesGroup (grupo de propriedades de pesquisa). 
     */
    public static final String DEFAULT_SEARCH_PROPERTIES_GROUP_LABEL_STYLE_CLASS = "groupLabel";
    
    /**
     * Constante que define o identificador do estilo (CSS) default para o componente searchPropertiesGroup (grupo de propriedades de pesquisa). 
     */
    public static final String DEFAULT_SEARCH_PROPERTIES_GROUP_STYLE_CLASS = "group";
    
    public static final String DEFAULT_SECTION_HEADER_LABEL_STYLE_CLASS = AttributeConstants.SECTION_HEADER_KEY.concat(AttributeConstants.LABEL_KEY);

    public static final String DEFAULT_SECTION_HEADER_STYLE_CLASS = AttributeConstants.SECTION_HEADER_KEY;
    
    public static final String DEFAULT_SECTION_CONTENT_STYLE_CLASS = AttributeConstants.SECTION_CONTENT_KEY;

    /**
     * Constante que define o identificador do estilo (CSS) default do ícone de um nó selecionado do componente treeView (árvore).
     */
    public static final String DEFAULT_TREE_VIEW_NODE_ICON_STYLE_CLASS = "nodeIcon";

    /**
     * Constante que define o identificador do estilo (CSS) default do label de um nó do componente treeView (árvore).
     */
    public static final String DEFAULT_TREE_VIEW_NODE_LABEL_STYLE_CLASS = "nodeLabel";
    
    /**
     * Constante que define o identificador do estilo (CSS) default do label de um nó selecionado do componente treeView (árvore).
     */
    public static final String DEFAULT_TREE_VIEW_NODE_LABEL_SELECTED_STYLE_CLASS = "nodeLabelSelected";
    
    /**
     * Constante que define o identificador do estilo (CSS) default para o ícone de retração do nó do componente treeView (árvore).
     */
    public static final String DEFAULT_TREE_VIEW_OPEN_LEAF_ICON_STYLE_CLASS = "openLeafIcon";

    /**
     * Constante que define o identificador do estilo (CSS) default para o ícone de expansão do nó do componente treeView (árvore).
     */
    public static final String DEFAULT_TREE_VIEW_CLOSE_LEAF_ICON_STYLE_CLASS = "closeLeafIcon";

    /**
     * Constante que define o identificador do estilo (CSS) default para o ícone do nó (expandido) do componente treeView (árvore).
     */
    public static final String DEFAULT_TREE_VIEW_OPENED_FOLDER_ICON_STYLE_CLASS  = "openedFolderIcon";
    
    /**
     * Constante que define o identificador do estilo (CSS) default para o ícone do nó (não expandido) do componente treeView (árvore).
     */
    public static final String DEFAULT_TREE_VIEW_CLOSED_FOLDER_ICON_STYLE_CLASS  = "closedFolderIcon";
    
    /**
     * Constante que define o identificador do arquivo de script de página default para o componente treeView (árvore).
     */

    public static final String DEFAULT_TREE_VIEW_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("treeView.js");
    
    /**
     * Constante que define o identificador do arquivo de estilos default (CSS) para o componente treeView (árvore).
     */
    public static final String DEFAULT_TREE_VIEW_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("treeView.css");
    
    /**
     * Constante que define o identificador da chave do controle de paginadores.
     */
    public static final String PAGER_MAP_KEY = "pagerMap";
}