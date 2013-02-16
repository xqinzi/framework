package br.com.concepting.framework.util;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.imageio.ImageIO;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.util.interfaces.IRobotShell32API;
import br.com.concepting.framework.util.interfaces.IRobotUser32API;

import com.sun.jna.Native;

/**
 * Classe responsável por executar ações programadas em um ambiente desktop.
 * 
 * @author fvilarinho
 * @since 2.0
 */
public class RobotInstance extends Robot{
	private static final Map<String, Integer[]> KEYS_MAP = new LinkedHashMap<String, Integer[]>();
	
	protected static IRobotUser32API  user32api  = null;
	protected static IRobotShell32API shell32api = null;
	
	static{
		if(System.getProperty("os.name").toLowerCase().contains("windows")){
			user32api  = (IRobotUser32API)Native.loadLibrary("user32", IRobotUser32API.class);
			shell32api = (IRobotShell32API)Native.loadLibrary("shell32", IRobotShell32API.class);
		}

		KEYS_MAP.put("0", new Integer[]{KeyEvent.VK_0});
		KEYS_MAP.put("1", new Integer[]{KeyEvent.VK_1});
		KEYS_MAP.put("2", new Integer[]{KeyEvent.VK_2});
		KEYS_MAP.put("3", new Integer[]{KeyEvent.VK_3});
		KEYS_MAP.put("4", new Integer[]{KeyEvent.VK_4});
		KEYS_MAP.put("5", new Integer[]{KeyEvent.VK_5});
		KEYS_MAP.put("6", new Integer[]{KeyEvent.VK_6});
		KEYS_MAP.put("7", new Integer[]{KeyEvent.VK_7});
		KEYS_MAP.put("8", new Integer[]{KeyEvent.VK_8});
		KEYS_MAP.put("9", new Integer[]{KeyEvent.VK_9});
		KEYS_MAP.put("a", new Integer[]{KeyEvent.VK_A});
		KEYS_MAP.put("á", new Integer[]{KeyEvent.VK_A});
		KEYS_MAP.put("ã", new Integer[]{KeyEvent.VK_A});
		KEYS_MAP.put("â", new Integer[]{KeyEvent.VK_A});
		KEYS_MAP.put("à", new Integer[]{KeyEvent.VK_A});
		KEYS_MAP.put("b", new Integer[]{KeyEvent.VK_B});
		KEYS_MAP.put("c", new Integer[]{KeyEvent.VK_C});
		KEYS_MAP.put("ç", new Integer[]{KeyEvent.VK_C});
		KEYS_MAP.put("d", new Integer[]{KeyEvent.VK_D});
		KEYS_MAP.put("e", new Integer[]{KeyEvent.VK_E});
		KEYS_MAP.put("é", new Integer[]{KeyEvent.VK_E});
		KEYS_MAP.put("ê", new Integer[]{KeyEvent.VK_E});
		KEYS_MAP.put("f", new Integer[]{KeyEvent.VK_F});
		KEYS_MAP.put("g", new Integer[]{KeyEvent.VK_G});
		KEYS_MAP.put("h", new Integer[]{KeyEvent.VK_H});
		KEYS_MAP.put("i", new Integer[]{KeyEvent.VK_I});
		KEYS_MAP.put("í", new Integer[]{KeyEvent.VK_I});
		KEYS_MAP.put("j", new Integer[]{KeyEvent.VK_J});
		KEYS_MAP.put("k", new Integer[]{KeyEvent.VK_K});
		KEYS_MAP.put("l", new Integer[]{KeyEvent.VK_L});
		KEYS_MAP.put("m", new Integer[]{KeyEvent.VK_M});
		KEYS_MAP.put("n", new Integer[]{KeyEvent.VK_N});
		KEYS_MAP.put("o", new Integer[]{KeyEvent.VK_O});
		KEYS_MAP.put("ó", new Integer[]{KeyEvent.VK_O});
		KEYS_MAP.put("ô", new Integer[]{KeyEvent.VK_O});
		KEYS_MAP.put("õ", new Integer[]{KeyEvent.VK_O});
		KEYS_MAP.put("p", new Integer[]{KeyEvent.VK_P});
		KEYS_MAP.put("q", new Integer[]{KeyEvent.VK_Q});
		KEYS_MAP.put("r", new Integer[]{KeyEvent.VK_R});
		KEYS_MAP.put("s", new Integer[]{KeyEvent.VK_S});
		KEYS_MAP.put("t", new Integer[]{KeyEvent.VK_T});
		KEYS_MAP.put("u", new Integer[]{KeyEvent.VK_U});
		KEYS_MAP.put("ú", new Integer[]{KeyEvent.VK_U});
		KEYS_MAP.put("ü", new Integer[]{KeyEvent.VK_U});
		KEYS_MAP.put("v", new Integer[]{KeyEvent.VK_V});
		KEYS_MAP.put("x", new Integer[]{KeyEvent.VK_X});
		KEYS_MAP.put("y", new Integer[]{KeyEvent.VK_Y});
		KEYS_MAP.put("z", new Integer[]{KeyEvent.VK_Z});
		KEYS_MAP.put("w", new Integer[]{KeyEvent.VK_W});
		KEYS_MAP.put("A", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_A});
		KEYS_MAP.put("Á", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_A});
		KEYS_MAP.put("Â", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_A});
		KEYS_MAP.put("Ã", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_A});
		KEYS_MAP.put("À", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_A});
		KEYS_MAP.put("B", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_B});
		KEYS_MAP.put("C", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_C});
		KEYS_MAP.put("Ç", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_C});
		KEYS_MAP.put("D", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_D});
		KEYS_MAP.put("E", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_E});
		KEYS_MAP.put("É", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_E});
		KEYS_MAP.put("Ê", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_E});
		KEYS_MAP.put("F", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_F});
		KEYS_MAP.put("G", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_G});
		KEYS_MAP.put("H", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_H});
		KEYS_MAP.put("I", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_I});
		KEYS_MAP.put("Í", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_I});
		KEYS_MAP.put("J", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_J});
		KEYS_MAP.put("K", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_K});
		KEYS_MAP.put("L", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_L});
		KEYS_MAP.put("M", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_M});
		KEYS_MAP.put("N", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_N});
		KEYS_MAP.put("O", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_O});
		KEYS_MAP.put("Ó", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_O});
		KEYS_MAP.put("Ô", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_O});
		KEYS_MAP.put("Õ", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_O});
		KEYS_MAP.put("P", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_P});
		KEYS_MAP.put("Q", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_Q});
		KEYS_MAP.put("R", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_R});
		KEYS_MAP.put("S", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_S});
		KEYS_MAP.put("T", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_T});
		KEYS_MAP.put("U", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_U});
		KEYS_MAP.put("Ú", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_U});
		KEYS_MAP.put("Ü", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_U});
		KEYS_MAP.put("V", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_V});
		KEYS_MAP.put("X", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_X});
		KEYS_MAP.put("Y", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_Y});
		KEYS_MAP.put("Z", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_Z});
		KEYS_MAP.put("W", new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_W});
		KEYS_MAP.put("!", null);
		KEYS_MAP.put("?", null);
		KEYS_MAP.put("+", new Integer[]{KeyEvent.VK_ADD});
		KEYS_MAP.put("-", new Integer[]{KeyEvent.VK_SUBTRACT});
		KEYS_MAP.put("*", new Integer[]{KeyEvent.VK_MULTIPLY});
		KEYS_MAP.put("/", new Integer[]{KeyEvent.VK_DIVIDE});
		KEYS_MAP.put("=", new Integer[]{KeyEvent.VK_EQUALS});
		KEYS_MAP.put(".", new Integer[]{KeyEvent.VK_PERIOD});
		KEYS_MAP.put("_", new Integer[]{KeyEvent.VK_UNDERSCORE});
		KEYS_MAP.put(":", new Integer[]{KeyEvent.VK_COLON});
		KEYS_MAP.put(",", new Integer[]{KeyEvent.VK_COMMA});
		KEYS_MAP.put(";", new Integer[]{KeyEvent.VK_SEMICOLON});
		KEYS_MAP.put("\\", new Integer[]{KeyEvent.VK_BACK_SLASH});
		KEYS_MAP.put("/", new Integer[]{KeyEvent.VK_SLASH});
		KEYS_MAP.put(" ", new Integer[]{KeyEvent.VK_SPACE});
		KEYS_MAP.put("$", new Integer[]{KeyEvent.VK_DOLLAR});
		KEYS_MAP.put("#", new Integer[]{KeyEvent.VK_NUMBER_SIGN});
		KEYS_MAP.put("[", new Integer[]{KeyEvent.VK_OPEN_BRACKET});
		KEYS_MAP.put("]", new Integer[]{KeyEvent.VK_CLOSE_BRACKET});
		KEYS_MAP.put("{", new Integer[]{KeyEvent.VK_BRACELEFT});
		KEYS_MAP.put("}", new Integer[]{KeyEvent.VK_BRACERIGHT});
		KEYS_MAP.put("(", new Integer[]{KeyEvent.VK_LEFT_PARENTHESIS});
		KEYS_MAP.put(")", new Integer[]{KeyEvent.VK_RIGHT_PARENTHESIS});
		KEYS_MAP.put(">", new Integer[]{KeyEvent.VK_GREATER});
		KEYS_MAP.put("<", new Integer[]{KeyEvent.VK_LESS});
		KEYS_MAP.put("'", new Integer[]{KeyEvent.VK_QUOTE});
		KEYS_MAP.put("\"", new Integer[]{KeyEvent.VK_QUOTEDBL});
		KEYS_MAP.put("\n", new Integer[]{KeyEvent.VK_ENTER});
		KEYS_MAP.put("\t", new Integer[]{KeyEvent.VK_TAB});
	}
	
	private static RobotInstance instance = null;
	
	private Map<String, Process> processes  = null;
	private Color                pixelColor = null;
	
	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 * 
	 * @throws AWTException
	 */
	private RobotInstance() throws AWTException{
		super();
		
		setAutoWaitForIdle(true);
	}
	
	/**
	 * Retorna a instância (única) do robô de comandos.
	 * 
	 * @return Instância do robô de comandos.
	 * @throws AWTException
	 */
	public static RobotInstance getInstance() throws AWTException{
	    if(instance == null)
	        instance = new RobotInstance();
	    
	    return instance;
	}

	/**
	 * Captura a cor atual de uma determinada coordenada da tela.
	 * 
	 * @param x Valor inteiro contendo a coordenada X da tela.
	 * @param y Valor inteiro contendo a coordenada Y da tela.
	 */
	public void capturePixel(Integer x, Integer y){
		pixelColor = getPixelColor(x, y);
	}

	/**
	 * Efetua uma pausa na execução enquando a cor de uma determinada coordenada não for 
	 * alterada.
	 * 
     * @param x Valor inteiro contendo a coordenada X da tela.
     * @param y Valor inteiro contendo a coordenada Y da tela.
	 * @throws TimeoutException
	 */
	public void waitForPixelChange(Integer x, Integer y) throws TimeoutException{
		waitForPixelChange(x, y, -1);
	}
	
    /**
     * Efetua uma pausa na execução enquando a cor de uma determinada coordenada não for 
     * alterada.
     * 
     * @param x Valor inteiro contendo a coordenada X da tela.
     * @param y Valor inteiro contendo a coordenada Y da tela.
     * @param timeout Valor inteiro contendo o timeout de espera.
     * @throws TimeoutException
     */
	public void waitForPixelChange(Integer x, Integer y, Integer timeout) throws TimeoutException{
		Color   currentPixelColor = null;
		Integer cont              = 0;
		Boolean changed           = false;
		
		while(true){
			currentPixelColor = getPixelColor(x, y);
			
			if(currentPixelColor.getRed() != pixelColor.getRed() || currentPixelColor.getBlue() != pixelColor.getBlue() || currentPixelColor.getGreen() != pixelColor.getGreen()){
				changed = true;
				
				break;
			}
			
			try{
	            Thread.sleep(100);
            }
            catch(InterruptedException e){
            	break;
            }
			
            if(timeout > 0){
    			cont += 100;
    			
    			if(cont >= timeout)
    				break;
            }
		}
		
		if(!changed)
			throw new TimeoutException(); 
	}
	
	/**
	 * Captura a tela.
	 * OBS: Captura no formato PNG.
	 * 
	 * @return Array de bytes contendo a tela capturada.
	 */
	public byte[] captureScreen(){
		BufferedImage         instanceBuffer = createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		ByteArrayOutputStream byteBuffer     = new ByteArrayOutputStream();
		
		try{
	        ImageIO.write(instanceBuffer, "png", byteBuffer);
	        
	        byteBuffer.close();
        }
        catch(IOException e){
        }
		
		return byteBuffer.toByteArray();
	}

	/**
	 * Dá foco em uma janela específica.
	 * OBS: Só implementado para ambiente Windows.
	 * 
	 * @param title String contendo o título da janela.
	 */
	public void focusWindow(String title){
		if(user32api == null){
			System.out.println("Not implemented for this OS!");
			
			return;
		}
		
		Long windowHandle = user32api.FindWindowA(null, title);
		
		user32api.SetForegroundWindow(windowHandle);
		user32api.BringWindowToTop(windowHandle);
	}
	
	/**
	 * Efetua uma pausa na execução até que uma janela seja carregada.
	 * 
     * @param title String contendo o título da janela.
     * @throws TimeoutException
	 */
	public void waitForWindow(String title) throws TimeoutException{
		waitForWindow(title, 0);
	}
	
    /**
     * Efetua uma pausa na execução até que uma janela seja carregada.
     * 
     * @param title String contendo o título da janela.
     * @param timeout Valor inteiro contendo o timeout de espera.
     * @throws InternalErrorException
     */
	public void waitForWindow(String title, Integer timeout) throws TimeoutException{
		if(user32api == null){
			System.out.println("Not implemented for this OS!");
			
			return;
		}

		Long    windowHandle = 0l;
		Integer cont         = 0;
		Boolean flag         = false;
		
		while(true){
			windowHandle = user32api.FindWindowA(null, title);
			windowHandle = user32api.GetWindowTextLengthA(windowHandle);
			
			if(windowHandle != null && windowHandle > 0){
				flag = true;
				
				break;
			}
			
			try{
	            Thread.sleep(100);
            }
            catch(InterruptedException e){
	            break;
            }
	            
            if(timeout > 0){
	            cont += 100;
	            
	            if(cont >= timeout)
	            	break;
            }
		}

		if(!flag)
			throw new TimeoutException();
		
		focusWindow(title);
	}

	/**
	 * Pressiona a tecla Window.
	 */
	public void pressWindowsKey(){
		keyPress(KeyEvent.VK_WINDOWS);
		keyRelease(KeyEvent.VK_WINDOWS);
	}
	
	/**
	 * Pressiona a tecla Enter.
	 */
	public void pressEnter(){
		keyPress(KeyEvent.VK_ENTER);
		keyRelease(KeyEvent.VK_ENTER);
	}
	
	/**
	 * Pressiona a tecla Tab.
	 */
	public void pressTab(){
		keyPress(KeyEvent.VK_TAB);
		keyRelease(KeyEvent.VK_TAB);
	}

	/**
	 * Pressiona a tecla Home.
	 */
	public void pressHome(){
		keyPress(KeyEvent.VK_HOME);
		keyRelease(KeyEvent.VK_HOME);
	}

	/**
	 * Pressiona a tecla Delete.
	 */
	public void pressDelete(){
		keyPress(KeyEvent.VK_DELETE);
		keyRelease(KeyEvent.VK_DELETE);
	}

    /**
     * Pressiona a tecla Insert.
     */
	public void pressInsert(){
		keyPress(KeyEvent.VK_INSERT);
		keyRelease(KeyEvent.VK_INSERT);
	}

    /**
     * Pressiona a tecla Page Up.
     */
	public void pressPageUp(){
		keyPress(KeyEvent.VK_PAGE_UP);
		keyRelease(KeyEvent.VK_PAGE_UP);
	}

    /**
     * Pressiona a tecla Page Down.
     */
	public void pressPageDown(){
		keyPress(KeyEvent.VK_PAGE_DOWN);
		keyRelease(KeyEvent.VK_PAGE_DOWN);
	}

    /**
     * Segura a tecla Control.
     */
	public void pressControl(){
		keyPress(KeyEvent.VK_CONTROL);
	}
	
    /**
     * Solta a tecla Page Up.
     */
	public void releaseControl(){
		keyRelease(KeyEvent.VK_CONTROL);
	}

	/**
	 * Segura a tela Alt.
	 */
	public void pressAlt(){
		keyPress(KeyEvent.VK_ALT);
	}

	/**
	 * Segura a tecla Alt Gr.
	 */
	public void pressAltGr(){
		keyPress(KeyEvent.VK_ALT_GRAPH);
	}

	/**
	 * Solta a tecla Alt.
	 */
	public void releaseAlt(){
		keyRelease(KeyEvent.VK_ALT);
	}

	/**
	 * Solta a tecla Alt Gr.
	 */
	public void releaseAltGr(){
		keyRelease(KeyEvent.VK_ALT_GRAPH);
	}

	/**
	 * Pressiona a tecla Caps Lock.
	 */
	public void pressCapslock(){
		keyPress(KeyEvent.VK_CAPS_LOCK);
		keyRelease(KeyEvent.VK_CAPS_LOCK);
	}

    /**
     * Pressiona a tecla Num Lock.
     */
	public void pressNumlock(){
		keyPress(KeyEvent.VK_NUM_LOCK);
		keyRelease(KeyEvent.VK_NUM_LOCK);
	}

    /**
     * Pressiona a tecla Scroll Lock.
     */
	public void pressScrolllock(){
		keyPress(KeyEvent.VK_SCROLL_LOCK);
		keyRelease(KeyEvent.VK_SCROLL_LOCK);
	}

    /**
     * Pressiona a tecla Esc.
     */
	public void pressEsc(){
		keyPress(KeyEvent.VK_ESCAPE);
		keyRelease(KeyEvent.VK_ESCAPE);
	}

    /**
     * Pressiona a tecla Backspace.
     */
	public void pressBackspace(){
		keyPress(KeyEvent.VK_BACK_SPACE);
		keyRelease(KeyEvent.VK_BACK_SPACE);
	}

	/**
	 * Pressiona a seta para esquerda.
	 */
	public void pressLeft(){
		keyPress(KeyEvent.VK_LEFT);
		keyRelease(KeyEvent.VK_LEFT);
	}

    /**
     * Pressiona a seta para direita.
     */
	public void pressRight(){
		keyPress(KeyEvent.VK_RIGHT);
		keyRelease(KeyEvent.VK_RIGHT);
	}

    /**
     * Pressiona a seta para baixo.
     */
	public void pressDown(){
		keyPress(KeyEvent.VK_DOWN);
		keyRelease(KeyEvent.VK_DOWN);
	}

    /**
     * Pressiona a seta para cima.
     */
	public void pressUp(){
		keyPress(KeyEvent.VK_UP);
		keyRelease(KeyEvent.VK_UP);
	}

    /**
     * Segura a tecla Shift.
     */
	public void pressShift(){
		keyPress(KeyEvent.VK_SHIFT);
	}

	/**
	 * Solta a tecla Shift.
	 */
	public void releaseShift(){
		keyRelease(KeyEvent.VK_SHIFT);
	}

	/**
	 * Pressiona a tecla F1.
	 */
	public void pressF1(){
		keyPress(KeyEvent.VK_F1);
		keyRelease(KeyEvent.VK_F1);
	}
	
    /**
     * Pressiona a tecla F2.
     */
	public void pressF2(){
		keyPress(KeyEvent.VK_F2);
		keyRelease(KeyEvent.VK_F2);
	}
	
    /**
     * Pressiona a tecla F3.
     */
	public void pressF3(){
		keyPress(KeyEvent.VK_F3);
		keyRelease(KeyEvent.VK_F3);
	}

    /**
     * Pressiona a tecla F4.
     */
	public void pressF4(){
		keyPress(KeyEvent.VK_F4);
		keyRelease(KeyEvent.VK_F4);
	}

    /**
     * Pressiona a tecla F5.
     */
	public void pressF5(){
		keyPress(KeyEvent.VK_F5);
		keyRelease(KeyEvent.VK_F5);
	}

    /**
     * Pressiona a tecla F6.
     */
	public void pressF6(){
		keyPress(KeyEvent.VK_F6);
		keyRelease(KeyEvent.VK_F6);
	}

    /**
     * Pressiona a tecla F7.
     */
	public void pressF7(){
		keyPress(KeyEvent.VK_F7);
		keyRelease(KeyEvent.VK_F7);
	}

    /**
     * Pressiona a tecla F8.
     */
	public void pressF8(){
		keyPress(KeyEvent.VK_F8);
		keyRelease(KeyEvent.VK_F8);
	}

    /**
     * Pressiona a tecla F9.
     */
	public void pressF9(){
		keyPress(KeyEvent.VK_F9);
		keyRelease(KeyEvent.VK_F9);
	}

    /**
     * Pressiona a tecla F10.
     */
	public void pressF10(){
		keyPress(KeyEvent.VK_F10);
		keyRelease(KeyEvent.VK_F10);
	}

    /**
     * Pressiona a tecla F11.
     */
	public void pressF11(){
		keyPress(KeyEvent.VK_F11);
		keyRelease(KeyEvent.VK_F11);
	}

    /**
     * Pressiona a tecla F12.
     */
	public void pressF12(){
		keyPress(KeyEvent.VK_F12);
		keyRelease(KeyEvent.VK_F12);
	}
	
	/**
	 * Pressiona uma tecla.
	 * 
	 * @param key String contendo o identificador da tecla.
	 */
	public void press(String key){
		if(StringUtil.trim(key).length() > 0)
			type(key.substring(0, 1));
	}
    
	/**
	 * Efetua um click com o botão esquerdo do mouse.
	 */
	public void mouseClick(){
		mousePress(InputEvent.BUTTON1_MASK);
		mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
    /**
     * Efetua um click com o botão direito do mouse.
     */
	public void mouseRightClick(){
		mousePress(InputEvent.BUTTON3_MASK);
		mouseRelease(InputEvent.BUTTON3_MASK);
	}
	
	/**
	 * Digita um texto qualquer.
	 * 
	 * @param value String contendo o texto desejado.
	 */
	public void type(String value){
		if(StringUtil.trim(value).length() > 0){
    		Integer keyCodes[] = null;
    		String  keyChar    = "";
    		
    		for(Integer cont1 = 0 ; cont1 < value.length() ; cont1++){
    			keyChar  = value.substring(cont1, cont1 + 1);
    			keyCodes = KEYS_MAP.get(keyChar);
    
    			if(keyCodes != null){
    				for(Integer cont2 = 0 ; cont2 < keyCodes.length ; cont2++)
    					keyPress(keyCodes[cont2]);
    
    				for(Integer cont2 = keyCodes.length - 1 ; cont2 >= 0 ; cont2--)
    					keyRelease(keyCodes[cont2]);
    			}
    		}
		}
	}
	
	/**
	 * Executa um aplicativo ou programa.
	 * 
	 * @param executable String contendo o caminho/nome do aplicativo ou programa.
	 */
	public void execute(String executable) throws InternalErrorException{
		if(StringUtil.trim(executable).length() > 0){
			try{
                Process process = Runtime.getRuntime().exec(executable);
        
                if(processes == null)
                	processes = new LinkedHashMap<String, Process>();
                
                processes.put(executable.concat(Integer.valueOf((int)(Math.random() * 9999)).toString()), process);
			}
			catch(Throwable e){
				throw new InternalErrorException(e);
			}
		}
	}
	
	/**
	 * Finaliza a execução de um aplicativo ou programa.
	 * 
     * @param executable String contendo o caminho/nome do aplicativo ou programa.
	 */
	public void kill(String executable){
		if(StringUtil.trim(executable).length() > 0){
			if(processes != null && processes.size() > 0){
				Process process = null;
				
				for(String key : processes.keySet()){
					if(key.startsWith(executable)){
    					process = processes.get(key);
    
    					if(process != null)
    						process.destroy();
					}
				}
				
				processes.clear();
			}
		}
	}
	
	/**
	 * Finaliza todo o processamento do robô.
	 */
	public void stop(){
		if(processes != null && processes.size() > 0){
			Process process = null;
			
			for(String key : processes.keySet()){
				process = processes.get(key);

				if(process != null)
					process.destroy();
			}
			
			processes.clear();
		}
	}

	/**
	 * Efetua uma pausa até que o aplicativo ou programa seja finalizado.
	 * 
     * @param executable String contendo o caminho/nome do aplicativo ou programa.
	 */
    public void waitUntilEnds(String executable){
		if(processes != null && processes.size() > 0){
			Process process = null;
			
			for(String key : processes.keySet()){
				if(key.startsWith(executable)){
					process = processes.get(key);
					
					break;
				}
			}

			if(process != null){
				try{
	                process.waitFor();
                }
                catch(InterruptedException e){
                }
			}
		}
    }

    /**
     * Executa um aplicativo ou programa e efetua uma pausa até que uma janela seja carregada.
     * 
     * @param executable String contendo o caminho/nome do aplicativo ou programa.
     * @param title String contendo o título da janela.
     * @throws InternalErrorException 
     */
    public void executeAndWaitForWindow(String executable, String title) throws TimeoutException, InternalErrorException{
    	execute(executable);
    	waitForWindow(title);
    }

    /**
     * Pressiona a tecla End.
     */
    public void pressEnd(){
		keyPress(KeyEvent.VK_END);
		keyRelease(KeyEvent.VK_END);
    }

    /**
     * Movimenta o ponteiro do mouse até uma coordenada específica.
     * 
     * @param x Valor inteiro contendo a coordenada X da tela.
     * @param y Valor inteiro contendo a coordenada Y da tela.
     */
    public void mouseMove(Integer x, Integer y){
    	super.mouseMove(x.intValue(), y.intValue());
    }

    /**
     * Segura uma tecla específica.
     * 
     * @param key Valor inteiro contendo o identificador ASCII da tecla.
     */
    public void keyPress(Integer key){
    	super.keyPress(key.intValue());
    }

    /**
     * Solta uma tecla específica.
     * 
     * @param key Valor inteiro contendo o identificador ASCII da tecla.
     */
    public void keyRelease(Integer key){
    	super.keyRelease(key.intValue());
    }

    /**
     * Segura um botão específico do mouse.
     * 
     * @param button Valor inteiro contendo o identificador do botão do mouse.
     */
    public void mousePress(Integer button){
    	super.mousePress(button.intValue());
    }

    /**
     * Solta um botão específico do mouse.
     * 
     * @param button Valor inteiro contendo o identificador do botão do mouse.
     */
    public void mouseRelease(Integer button){
    	super.mouseRelease(button.intValue());
    }

    /**
     * Efetua uma pausa na execução do robô.
     * 
     * @param delay Valor inteiro contendo o tempo (em milisegundos) da pausa.
     */
    public void dalay(Integer delay){
    	super.delay(delay.intValue());
    }
}