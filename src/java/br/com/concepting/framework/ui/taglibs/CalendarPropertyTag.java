package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.ui.constants.TaglibConstants;
import br.com.concepting.framework.ui.types.VisibilityType;
import br.com.concepting.framework.util.types.AlignmentType;

/**
 * Classe que define o componente visual de um calendar (calendário).
 * 
 * @author fvilarinho
 * @since 2.0
 */
public class CalendarPropertyTag extends TextPropertyTag{
    /**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		if(getPropertyInfo() != null && isEnabled() && !isReadOnly()){
			print("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"");
			print(TaglibConstants.DEFAULT_CALENDAR_CONTENT_STYLE_CLASS);
			println("\">");
			println("<tr>");
			print("<td class=\"");
			print(TaglibConstants.DEFAULT_CALENDAR_TEXT_STYLE_CLASS);
			print("\" valign=\"");
			print(AlignmentType.BOTTOM);
			print("\" width=\"1\">");

			super.renderBody();

			println("</td>");
			
			println("<td width=\"5\"></td>");
			
			print("<td valign=\"");
			print(AlignmentType.BOTTOM);
			println("\">");

			ShowCalendarButtonTag showCalendarButtonTag = new ShowCalendarButtonTag(this);

			showCalendarButtonTag.doStartTag();
			showCalendarButtonTag.doEndTag();

			println("</td>");
			println("</tr>");
			println("</table>");

            String name = getName();

            print("<div id=\"");
            print(name);
            print(".");
            print(TaglibConstants.CALENDAR_ID);
            print("\" class=\"");
            print(TaglibConstants.DEFAULT_CALENDAR_STYLE_CLASS);
            print("\" style=\"visibility: ");
            print(VisibilityType.HIDDEN);
            println(";\">");

            print("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"");
            print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
            println("\">");
			println("<tr>");
			println("<td>");

			PreviousYearButtonTag previousYearButtonTag = new PreviousYearButtonTag(this);

			previousYearButtonTag.doStartTag();
			previousYearButtonTag.doEndTag();

			println("</td>");
			println("<td>");

			PreviousMonthButtonTag previousMonthButtonTag = new PreviousMonthButtonTag(this);

			previousMonthButtonTag.doStartTag();
			previousMonthButtonTag.doEndTag();

			println("</td>");
			
			print("<td align=\"");
			print(AlignmentType.CENTER);
			println("\">");
			print("<span id=\"");
			print(name);
			print(".");
			print(TaglibConstants.CALENDAR_DISPLAY_ID);
			print("\" class=\"");
			print(TaglibConstants.DEFAULT_CALENDAR_DISPLAY_STYLE_CLASS);
			println("\"></span>");
			println("</td>");
			
			println("<td>");

			NextMonthButtonTag nextMonthButtonTag = new NextMonthButtonTag(this);

			nextMonthButtonTag.doStartTag();
			nextMonthButtonTag.doEndTag();

			println("</td>");
			println("<td>");

			NextYearButtonTag nextYearButtonTag = new NextYearButtonTag(this);

			nextYearButtonTag.doStartTag();
			nextYearButtonTag.doEndTag();

			println("</td>");
            println("</tr>");
            println("</table>");
            
            print("<span id=\"");
            print(name);
            print(".");
            print(TaglibConstants.CALENDAR_DAYS_ID);
            print("\" class=\"");
            print(TaglibConstants.DEFAULT_CALENDAR_DAYS_STYLE_CLASS);
            println("\"></span>");

            println("</div>");
		}
		else
			super.renderBody();
	}
	
	/**
	 * Classe que define o componente visual para o botão mostrar calendário.
	 * 
	 * @author fvilarinho
	 * @since 2.0
	 */
	private class ShowCalendarButtonTag extends ButtonTag{
		/**
		 * Construtor - Inicializa objetos e/ou variáveis internas.
		 */
		public ShowCalendarButtonTag(CalendarPropertyTag calendarTag){
			super();
			
			setPageContext(calendarTag.getPageContext());
			setOut(calendarTag.getOut());
			
			StringBuilder onClickContent = new StringBuilder();

			onClickContent.append("showHideCalendar('");
			onClickContent.append(calendarTag.getName());
			onClickContent.append("');");
			
			setOnClick(onClickContent.toString());
			setResourceId(TaglibConstants.DEFAULT_CALENDAR_I18N_RESOURCE_ID);
		}
	}
	
	/**
	 * Classe que define o componente visual para o botão ano anterior.
	 * 
	 * @author fvilarinho
	 * @since 2.0
	 */
	private class PreviousYearButtonTag extends ButtonTag{
		/**
		 * Construtor - Inicializa objetos e/ou variáveis internas.
		 */
		public PreviousYearButtonTag(CalendarPropertyTag calendarTag){
            super();
            
            setPageContext(calendarTag.getPageContext());
            setOut(calendarTag.getOut());
			
			StringBuilder onClickContent = new StringBuilder();

        	onClickContent.delete(0, onClickContent.length());
        	onClickContent.append("moveToPreviousYear('");
        	onClickContent.append(calendarTag.getName());
        	onClickContent.append("');");
        	
        	setOnClick(onClickContent.toString());
        	setResourceId(TaglibConstants.DEFAULT_CALENDAR_I18N_RESOURCE_ID);
		}
	}

	/**
	 * Classe que define o componente visual para o botão mês anterior.
	 * 
	 * @author fvilarinho
	 * @since 2.0
	 */
	private class PreviousMonthButtonTag extends ButtonTag{
		/**
		 * Construtor - Inicializa objetos e/ou variáveis internas.
		 */
		public PreviousMonthButtonTag(CalendarPropertyTag calendarTag){
            super();
            
            setPageContext(calendarTag.getPageContext());
            setOut(calendarTag.getOut());
			
			StringBuilder onClickContent = new StringBuilder();

        	onClickContent.delete(0, onClickContent.length());
        	onClickContent.append("moveToPreviousMonth('");
        	onClickContent.append(calendarTag.getName());
        	onClickContent.append("');");
        	
        	setOnClick(onClickContent.toString());
        	setResourceId(TaglibConstants.DEFAULT_CALENDAR_I18N_RESOURCE_ID);
		}
	}

	/**
	 * Classe que define o componente visual para o botão próximo mês.
	 * 
	 * @author fvilarinho
	 * @since 2.0
	 */
	private class NextMonthButtonTag extends ButtonTag{
		/**
		 * Construtor - Inicializa objetos e/ou variáveis internas.
		 */
		public NextMonthButtonTag(CalendarPropertyTag calendarTag){
            super();
            
            setPageContext(calendarTag.getPageContext());
            setOut(calendarTag.getOut());
			
			StringBuilder onClickContent = new StringBuilder();

        	onClickContent.delete(0, onClickContent.length());
        	onClickContent.append("moveToNextMonth('");
        	onClickContent.append(calendarTag.getName());
        	onClickContent.append("');");
        	
        	setOnClick(onClickContent.toString());
        	setResourceId(TaglibConstants.DEFAULT_CALENDAR_I18N_RESOURCE_ID);
		}
	}

	/**
	 * Classe que define o componente visual para o botão próximo ano.
	 * 
	 * @author fvilarinho
	 * @since 2.0
	 */
	private class NextYearButtonTag extends ButtonTag{
		/**
		 * Construtor - Inicializa objetos e/ou variáveis internas.
		 */
		public NextYearButtonTag(CalendarPropertyTag calendarTag){
            super();
            
            setPageContext(calendarTag.getPageContext());
            setOut(calendarTag.getOut());
			
			StringBuilder onClickContent = new StringBuilder();

        	onClickContent.delete(0, onClickContent.length());
        	onClickContent.append("moveToNextYear('");
        	onClickContent.append(calendarTag.getName());
        	onClickContent.append("');");
        	
        	setOnClick(onClickContent.toString());
        	setResourceId(TaglibConstants.DEFAULT_CALENDAR_I18N_RESOURCE_ID);
		}
	}
}