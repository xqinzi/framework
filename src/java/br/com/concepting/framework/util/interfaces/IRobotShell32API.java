package br.com.concepting.framework.util.interfaces;

import com.sun.jna.win32.StdCallLibrary;

/**
 * Interface que define m�todos da API do Windows (Shell32.dll) atrav�s do recurso JNA.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public interface IRobotShell32API extends StdCallLibrary{
    /**
     * Consulte a API do Windows
     */
	Long ShellExecuteA(Long hwnd, String lpOperation, String lpFile, String lpParameters, String lpDirectory, Long nShowCmd);
}
