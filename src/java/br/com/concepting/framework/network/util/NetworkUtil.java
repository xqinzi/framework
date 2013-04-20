package br.com.concepting.framework.network.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;

import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;

public class NetworkUtil{
    public static Boolean isIpMatches(String ip, String expression){
        if(expression.contains("/")){
            SubnetInfo info = new SubnetUtils(expression).getInfo();
            
            return (info.isInRange(ip));
        }

        StringBuilder regexBuffer = new StringBuilder();
        String        octets[]    = StringUtil.split(expression, ".");
        
        for(String octet : octets){
            if(regexBuffer.length() > 0)
                regexBuffer.append(".");

            if(octet.contains("-")){
                String  range[] = StringUtil.split(octet, "-");
                
                if(range.length  == 2){
                    try{
                        Integer start = NumberUtil.parseInt(range[0]);
                        Integer end   = NumberUtil.parseInt(range[1]);
                        
                        regexBuffer.append("(");
                        
                        for(int cont = start ; cont <= end ; cont++){
                            if(cont != start)
                                regexBuffer.append("|");
                            
                            regexBuffer.append(cont);
                        }
                        
                        regexBuffer.append(")");
                    }
                    catch(Throwable e){
                        regexBuffer.append(octet);
                    }
                }
                else
                    regexBuffer.append(octet);
            }
            else
                regexBuffer.append(octet);
        }
        
        String  regex   = StringUtil.replaceAll(regexBuffer.toString(), "*", "(25[0-4]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        
        return matcher.matches();
    }
}
