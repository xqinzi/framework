package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que define o componente visual de um calend�rio.
 * 
 * @author fvilarinho
 * @since 2.0
 */
public class CalendarPropertyTag extends TextPropertyTag{
    /**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		if(getPropertyInfo() != null && isEnabled() && !isReadOnly()){
			println("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"calendarBoxContent\">");
			println("<tr>");
			println("<td class=\"calendarBoxText\" valign=\"bottom\" width=\"1\">");

			super.renderBody();

			println("</td>");
			
			println("<td width=\"5\"></td>");
			println("<td valign=\"bottom\">");

			ShowCalendarBoxButtonTag showCalendarBoxButtonTag = new ShowCalendarBoxButtonTag(this);

			showCalendarBoxButtonTag.doStartTag();
			showCalendarBoxButtonTag.doEndTag();

			println("</td>");
			println("</tr>");
			println("</table>");

            String name = getName();

            print("<div id=\"");
            print(name);
            println(".calendarBox\" class=\"calendarBox\" style=\"visibility: hidden;\">");

			println("<table class=\"panel\">");
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
			
			println("<td align=\"center\">");
			print("<span id=\"");
			print(name);
			println(".calendarBoxDisplay\" class=\"calendarBoxDisplay\"></span>");
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
            println(".calendarBoxDays\" class=\"calendarBoxDays\"></span>");

            println("</div>");
		}
		else
			super.renderBody();
	}
	
	/**
	 * Classe que define o componente visual para o bot�o mostrar calend�rio.
	 * 
	 * @author fvilarinho
	 * @since 2.0
	 */
	private class ShowCalendarBoxButtonTag extends ButtonTag{
		/**
		 * Construtor - Inicializa objetos e/ou vari�veis internas.
		 */
		public ShowCalendarBoxButtonTag(CalendarPropertyTag calendarTag){
			super();
			
			setPageContext(calendarTag.getPageContext());
			setOut(calendarTag.getOut());
			
			StringBuilder onClickContent = new StringBuilder();

			onClickContent.append("showHideCalendarBox('");
			onClickContent.append(calendarTag.getName());
			onClickContent.append("');");
			
			setOnClick(onClickContent.toString());
			setResourceId(TaglibConstants.DEFAULT_CALENDAR_I18N_RESOURCE_ID);
		}
	}
	
	/**
	 * Classe que define o componente visual para o bot�o ano anterior.
	 * 
	 * @author fvilarinho
	 * @since 2.0
	 */
	private class PreviousYearButtonTag extends ButtonTag{
		/**
		 * Construtor - Inicializa objetos e/ou vari�veis internas.
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
	 * Classe que define o componente visual para o bot�o m�s anterior.
	 * 
	 * @author fvilarinho
	 * @since 2.0
	 */
	private class PreviousMonthButtonTag extends ButtonTag{
		/**
		 * Construtor - Inicializa objetos e/ou vari�veis internas.
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
	 * Classe que define o componente visual para o bot�o pr�ximo m�s.
	 * 
	 * @author fvilarinho
	 * @since 2.0
	 */
	private class NextMonthButtonTag extends ButtonTag{
		/**
		 * Construtor - Inicializa objetos e/ou vari�veis internas.
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
	 * Classe que define o componente visual para o bot�o pr�ximo ano.
	 * 
	 * @author fvilarinho
	 * @since 2.0
	 */
	private class NextYearButtonTag extends ButtonTag{
		/**
		 * Construtor - Inicializa objetos e/ou vari�veis internas.
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