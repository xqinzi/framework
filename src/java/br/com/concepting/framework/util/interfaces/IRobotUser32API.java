package br.com.concepting.framework.util.interfaces;

import com.sun.jna.win32.StdCallLibrary;

/**
 * Interface que define métodos da API do Windows (User32.dll) através do recurso JNA.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public interface IRobotUser32API extends StdCallLibrary{
    /**
     * Consulte a API do Windows
     */
	Long FindWindowA(String lpClassName, String lpWindowName);

    /**
     * Consulte a API do Windows
     */
	Long GetWindowTextLengthA(Long hwnd);

    /**
     * Consulte a API do Windows
     */
	Long GetWindowTextA(Long hwnd, String lpString, Long cch);

    /**
     * Consulte a API do Windows
     */
	Long GetClassNameA(Long hwnd, String lpClassName, Long nMaxCount);

    /**
     * Consulte a API do Windows
     */
	Long DestroyWindow(Long hwnd);

    /**
     * Consulte a API do Windows
     */
	Long BringWindowToTop(Long hwnd); 

    /**
     * Consulte a API do Windows
     */
	Long SetFocus(Long hwnd);

    /**
     * Consulte a API do Windows
     */
	Long SetForegroundWindow(Long hwnd);

    /**
     * Consulte a API do Windows
     */
	Long GetForegroundWindow();

    /**
     * Consulte a API do Windows
     */
	Long ShowWindow(Long hwnd, Long nCmdShow);

    /**
     * Consulte a API do Windows
     */
	Long ShowWindowAsync(Long hwnd, Long nCmdShow);
}
