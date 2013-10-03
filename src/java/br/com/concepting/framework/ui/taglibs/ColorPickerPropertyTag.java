package br.com.concepting.framework.ui.taglibs;

import java.awt.Color;

import br.com.concepting.framework.ui.constants.TaglibConstants;
import br.com.concepting.framework.util.ColorUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;

/**
 * Classe que define o componente visual colorPicker (seletor de cor).
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class ColorPickerPropertyTag extends BasePropertyTag{
    /**
     * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        HiddenPropertyTag colorPickerPropertyTag = new HiddenPropertyTag();
        
        colorPickerPropertyTag.setPageContext(pageContext);
        colorPickerPropertyTag.setActionFormTag(getActionFormTag());
        colorPickerPropertyTag.setName(getName());
        colorPickerPropertyTag.setPropertyInfo(getPropertyInfo());
        colorPickerPropertyTag.doStartTag();
        colorPickerPropertyTag.doEndTag();
        
        print("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"");
        print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
        println("\">");
        
        println("<tr>");
        
        println("<td>");
        
        String            name                = getName();
        String            value               = StringUtil.trim(getValue());
        Color             color               = (value.length() > 0 ? ColorUtil.toColor(value) : Color.WHITE);
        SliderPropertyTag redValuePropertyTag = new SliderPropertyTag();
        
        redValuePropertyTag.setPageContext(pageContext);
        redValuePropertyTag.setActionFormTag(getActionFormTag());
        redValuePropertyTag.setLabel("R");
        redValuePropertyTag.setLabelStyleClass(TaglibConstants.DEFAULT_COLOR_PICKER_LABEL_PROPERTIES_STYLE_CLASS);
        redValuePropertyTag.setName(name.concat(".redValue"));
        redValuePropertyTag.setMaximumValue(255l);
        redValuePropertyTag.setOnChange("changeColorPickerThumbnail('".concat(name).concat("');"));
        redValuePropertyTag.setAlignmentType(AlignmentType.RIGHT);
        redValuePropertyTag.setSize(3);
        redValuePropertyTag.setMaxlength(3);
        redValuePropertyTag.setValue(color.getRed());
        redValuePropertyTag.doStartTag();
        redValuePropertyTag.doEndTag();
        
        SliderPropertyTag greenValuePropertyTag = new SliderPropertyTag();
        
        greenValuePropertyTag.setPageContext(pageContext);
        greenValuePropertyTag.setActionFormTag(getActionFormTag());
        greenValuePropertyTag.setLabel("G");
        greenValuePropertyTag.setLabelStyleClass(TaglibConstants.DEFAULT_COLOR_PICKER_LABEL_PROPERTIES_STYLE_CLASS);
        greenValuePropertyTag.setName(name.concat(".greenValue"));
        greenValuePropertyTag.setMaximumValue(255l);
        greenValuePropertyTag.setOnChange("changeColorPickerThumbnail('".concat(name).concat("');"));
        greenValuePropertyTag.setAlignmentType(AlignmentType.RIGHT);
        greenValuePropertyTag.setSize(3);
        greenValuePropertyTag.setMaxlength(3);
        greenValuePropertyTag.setValue(color.getGreen());
        greenValuePropertyTag.doStartTag();
        greenValuePropertyTag.doEndTag();

        SliderPropertyTag blueValuePropertyTag = new SliderPropertyTag();
        
        blueValuePropertyTag.setPageContext(pageContext);
        blueValuePropertyTag.setActionFormTag(getActionFormTag());
        blueValuePropertyTag.setLabel("B");
        blueValuePropertyTag.setLabelStyleClass(TaglibConstants.DEFAULT_COLOR_PICKER_LABEL_PROPERTIES_STYLE_CLASS);
        blueValuePropertyTag.setName(name.concat(".blueValue"));
        blueValuePropertyTag.setMaximumValue(255l);
        blueValuePropertyTag.setOnChange("changeColorPickerThumbnail('".concat(name).concat("');"));
        blueValuePropertyTag.setAlignmentType(AlignmentType.RIGHT);
        blueValuePropertyTag.setSize(3);
        blueValuePropertyTag.setMaxlength(3);
        blueValuePropertyTag.setValue(color.getBlue());
        blueValuePropertyTag.doStartTag();
        blueValuePropertyTag.doEndTag();
        
        println("</td>");
        
        println("<td width=\"5\"></td>");
        
        println("<td>");
        
        print("<div id=\"");
        print(name);
        print(".colorPickerThumbnail\" class=\"");
        print(TaglibConstants.DEFAULT_COLOR_PICKER_THUMBNAIL_STYLE_CLASS);
        println("\"></div>");

        println("</td>");
        
        println("</tr>");
        
        println("</table>");
    }
}
