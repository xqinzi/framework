package br.com.concepting.framework.network.snmp;
 
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.BitString;
import org.snmp4j.smi.Counter32;
import org.snmp4j.smi.Counter64;
import org.snmp4j.smi.Gauge32;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Opaque;
import org.snmp4j.smi.SMIConstants;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import br.com.concepting.framework.network.constants.NetworkConstants;
import br.com.concepting.framework.network.snmp.exceptions.SnmpException;
import br.com.concepting.framework.network.snmp.helpers.SnmpData;
import br.com.concepting.framework.network.snmp.helpers.SnmpRequest;
import br.com.concepting.framework.network.snmp.types.SnmpV3ProtocolsType;
import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe responsável por efetuar a comunicação com um serviço SNMP.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class SnmpSession{
	private static Map<String, SnmpSession> instances = new LinkedHashMap<String, SnmpSession>();

	private String               host            = "";
	private Integer              port            = NetworkConstants.DEFAULT_SNMP_PORT;
	private String               readCommunity   = NetworkConstants.DEFAULT_SNMP_READ_COMMUNITY_ID;
	private String               writeCommunity  = NetworkConstants.DEFAULT_SNMP_WRITE_COMMUNITY_ID;
	private Integer              version         = SnmpConstants.version2c;
	private Integer              timeout         = NetworkConstants.DEFAULT_SNMP_TIMEOUT;
	private String               user            = "";
	private String               password        = "";
	private SnmpV3ProtocolsType  protocol        = SnmpV3ProtocolsType.AUTH_MD5;
	private String               privatePassword = "";
	private SnmpV3ProtocolsType  privateProtocol = SnmpV3ProtocolsType.PRIV_3DES;
	private Integer              responseCode    = 0;              
	private String               responseMessage = "";                                 
	private Collection<SnmpData> response        = null;
	private CommunityTarget      readTarget      = new CommunityTarget();
	private CommunityTarget      writeTarget     = new CommunityTarget();
	private PDU                  pdu             = null;
	private Snmp                 session         = null;
	
	/**
	 * Retorna o nome do usuário para autenticação no serviço SNMP.
	 * 
	 * @return String contendo o nome do usuário.
	 */
	public String getUser(){
    	return user;
    }

    /**
     * Define o nome do usuário para autenticação no serviço SNMP.
     * 
     * @param user String contendo o nome do usuário.
     */
	public void setUser(String user){
    	this.user = user;
    }

    /**
     * Retorna a senha do usuário para autenticação no serviço SNMP.
     * 
     * @return String contendo a senha do usuário.
     */
	public String getPassword(){
    	return password;
    }

    /**
     * Define a senha do usuário para autenticação no serviço SNMP.
     * 
     * @param password String contendo o nome do usuário.
     */
	public void setPassword(String password){
    	this.password = password;
    }

    /**
     * Retorna o protocolo de comunicação com o serviço SNMP.
     * 
     * @return Instância que define o protocolo de comunicação com o serviço SNMP.
     */
	public SnmpV3ProtocolsType getProtocol(){
    	return protocol;
    }

    /**
     * Define o protocolo de comunicação com o serviço SNMP.
     * 
     * @param protocol Instância que define o protocolo de comunicação com o serviço SNMP.
     */
	public void setProtocol(SnmpV3ProtocolsType protocol){
    	this.protocol = protocol;
    }

	/**
	 * Retorna a senha privada para descriptografia.
	 * 
	 * @return String contendo a senha privada para descriptografia.
	 */
	public String getPrivatePassword(){
    	return privatePassword;
    }

    /**
     * Define a senha privada para descriptografia.
     * 
     * @param privatePassword String contendo a senha privada para descriptografia.
     */
	public void setPrivatePassword(String privatePassword){
    	this.privatePassword = privatePassword;
    }

	/**
	 * Retorna o protocolo privado para descriptografia.
	 * 
	 * @return Constante que define o protocolo privado de descriptografia.
	 */
	public SnmpV3ProtocolsType getPrivateProtocol(){
    	return privateProtocol;
    }

    /**
     * Define o protocolo privado para descriptografia.
     * 
     * @param privateProtocol Constante que define o protocolo privado de descriptografia.
     */
	public void setPrivateProtocol(SnmpV3ProtocolsType privateProtocol){
    	this.privateProtocol = privateProtocol;
    }

	/**
	 * Retorna o código de retorno de uma consulta no serviço SNMP.
	 * 
	 * @return Valor inteiro contendo o código de retorno.
	 */
	public Integer getResponseCode(){
    	return responseCode;
    }

    /**
     * Define o código de retorno de uma consulta no serviço SNMP.
     * 
     * @param responseCode Valor inteiro contendo o código de retorno.
     */
	private void setResponseCode(Integer responseCode){
    	this.responseCode = responseCode;
    }

    /**
     * Retorna a mensagem de retorno de uma consulta no serviço SNMP.
     * 
     * @return String contendo a mensagem de retorno.
     */
	public String getResponseMessage(){
    	return responseMessage;
    }

    /**
     * Define a mensagem de retorno de uma consulta no serviço SNMP.
     * 
     * @param responseMessage String contendo a mensagem de retorno.
     */
	private void setResponseMessage(String responseMessage){
    	this.responseMessage = responseMessage;
    }

	/**
	 * Lista contendo o resultado da consulta no serviço SNMP.
	 * 
	 * @return Lista contendo o resultado da consulta.
	 */
	public Collection<SnmpData> getResponse(){
    	return response;
    }

    /**
     * Define a lista contendo o resultado da consulta no serviço SNMP.
     * 
     * @param response Lista contendo o resultado da consulta.
     */
	private void setResponse(Collection<SnmpData> response){
    	this.response = response;
    }

	/**
	 * Retorna o timeout de conexão com o serviço SNMP.
	 * 
	 * @return Valor inteiro contendo o timeout de conexão.
	 */
	public Integer getTimeout(){
    	return timeout;
    }

    /**
     * Define o timeout de conexão com o serviço SNMP.
     * 
     * @param timeout Valor inteiro contendo o timeout de conexão.
     */
	public void setTimeout(Integer timeout){
    	this.timeout = timeout;
    }

	/**
	 * Retorna a versão do serviço SNMP.
	 * 
	 * @return Valor inteiro contendo a versão do serviço SNMP.
	 */
	public Integer getVersion(){
    	return version;
    }

    /**
     * Define a versão do serviço SNMP.
     * 
     * @param version Valor inteiro contendo a versão do serviço SNMP.
     */
	public void setVersion(Integer version){
    	this.version = version;
    }

	/**
	 * Retorna o IP/Hostname do serviço SNMP desejado.
	 * 
	 * @return String contendo o IP/Hostname do serviço SNMP.
	 */
	public String getHost(){
		return host;
	}

    /**
     * Define o IP/Hostname do serviço SNMP desejado.
     * 
     * @param host String contendo o IP/Hostname do serviço SNMP.
     */
	public void setHost(String host){
		this.host = host;
	}

	/**
	 * Retorna a porta de comunicação com o serviço SNMP.
	 * 
	 * @return Valor inteiro contendo a porta de comunicação com o serviço SNMP.
	 */
	public Integer getPort(){
		return port;
	}

    /**
     * Define a porta de comunicação com o serviço SNMP.
     * 
     * @param port Valor inteiro contendo a porta de comunicação com o serviço SNMP.
     */
	public void setPort(Integer port){
		this.port = port;
	}

	/**
	 * Retorna a community para leitura.
	 * 
	 * @return String contendo a community.
	 */
	public String getReadCommunity(){
		return readCommunity;
	}

    /**
     * Define a community para leitura.
     * 
     * @param readCommunity String contendo a community.
     */
	public void setReadCommunity(String readCommunity){
		this.readCommunity = readCommunity;
	}

    /**
     * Retorna a community para escrita.
     * 
     * @return String contendo a community.
     */
	public String getWriteCommunity(){
		return writeCommunity;
	}

    /**
     * Define a community para escrita.
     * 
     * @param writeCommunity String contendo a community.
     */
	public void setWriteCommunity(String writeCommunity){
		this.writeCommunity = writeCommunity;
	}
	
    /**
     * Retorna a instância da conexão com o serviço SNMP desejado.
     * 
     * @param host String contendo o IP/Hostname do serviço SNMP desejado.
     * @return Instância da conexão com o serviço SNMP.
     * @throws SnmpException
     */
	public static SnmpSession getInstance(String host) throws SnmpException{
		SnmpSession session = instances.get(host);
		
		if(session == null){
			session = new SnmpSession();
			
			instances.put(host, session);
		}
		
		session.setHost(host);
		session.initialize();
		
		return session;
	}
	
	/**
	 * Retorna a instância da conexão com o serviço SNMP desejado.
	 * 
	 * @param host String contendo o IP/Hostname do serviço SNMP desejado.
	 * @param port Valor inteiro contendo a porta de comunicação com o serviço SNMP desejado.
	 * @return Instância da conexão com o serviço SNMP.
	 * @throws SnmpException
	 */
	public static SnmpSession getInstance(String host, Integer port) throws SnmpException{
		SnmpSession session = getInstance(host);
		
		session.setPort(port);
		
		return session;
	}
	
	/**
	 * Inicializa comunicação com o serviço SNMP.
	 * 
	 * @throws SnmpException
	 */
	private void initialize() throws SnmpException{
		try{
            TransportMapping<?> transport = new DefaultUdpTransportMapping();

            session = new Snmp(transport);
		
        	if(getVersion() == SnmpConstants.version3){
        		pdu = new ScopedPDU();
			
        		USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
        		
        		SecurityModels.getInstance().addSecurityModel(usm);
        		
        		OID authProtocol    = getProtocol().getOid();
        		OID privateProtocol = getPrivateProtocol().getOid();
        		
        		usm.addUser(new OctetString(getUser()), new UsmUser(new OctetString(getUser()), authProtocol, new OctetString(getPassword()), privateProtocol, new OctetString(getPrivatePassword())));
			}
        	else
        		pdu = new PDU();
    		
            session.listen();
		}
		catch(Throwable e){
			throw new SnmpException(e);
		}
        
		Address address = GenericAddress.parse(getHost().concat("/").concat(getPort().toString()));
		
		readTarget.setAddress(address);
		readTarget.setCommunity(new OctetString(readCommunity));
		readTarget.setVersion(getVersion());
		readTarget.setTimeout(getTimeout());
		
		writeTarget.setAddress(address);
		writeTarget.setCommunity(new OctetString(writeCommunity));
		writeTarget.setVersion(getVersion());
		writeTarget.setTimeout(getTimeout());

		setResponse(null);
		setResponseCode(0);
		setResponseMessage("");
    }
	
	/**
	 * Monta uma requisição usando o método GET.
	 * 
	 * @param type Valor inteiro contendo o tipo da requisição.
     * @param maxResults Valor inteiro contendo a quantidade máxima de registros devem retornar.
	 * @param requests Lista contendo as propriedades das OIDs desejadas.
	 * @throws SnmpException
	 */
	private void buildGetRequest(Integer type, Integer maxResults, Collection<SnmpRequest> requests) throws SnmpException{
		pdu.setType(type);
		
		VariableBinding variable = null;
		
		for(SnmpRequest request : requests){
			variable = new VariableBinding(new OID(request.getOid()));
			
			pdu.add(variable);
		}

		pdu.setMaxRepetitions(maxResults);
	}
	
	/**
	 * Monta a requisição de escrita no serviço SNMP.
	 * 
	 * @param data Lista contendo os dados a serem escritos.
	 * @throws SnmpException
	 */
	private void buildSetRequest(Collection<SnmpData> data) throws SnmpException{
		pdu.setType(PDU.SET);
		
		VariableBinding variable = null;
		Variable        value    = null;
		
		for(SnmpData item : data){
			variable = new VariableBinding(new OID(item.getRequest().getOid()));

			if(variable.getSyntax() == SMIConstants.SYNTAX_TIMETICKS)
				value = new TimeTicks(((Date)item.getValue()).getTime());
			else if(variable.getSyntax() == SMIConstants.SYNTAX_OCTET_STRING)
				value = new OctetString((String)item.getValue());
			else if(variable.getSyntax() == SMIConstants.SYNTAX_NULL)
				value = new Null();
			else if(variable.getSyntax() == SMIConstants.SYNTAX_GAUGE32)
				value = new Gauge32((Long)item.getValue());
			else if(variable.getSyntax() == SMIConstants.SYNTAX_INTEGER)
				value = new Integer32((Integer)item.getValue());
			else if(variable.getSyntax() == SMIConstants.SYNTAX_INTEGER32)
				value = new Integer32((Integer)item.getValue());
			else if(variable.getSyntax() == SMIConstants.SYNTAX_COUNTER64)
				value = new Counter64((Long)item.getValue());
			else if(variable.getSyntax() == SMIConstants.SYNTAX_COUNTER32)
				value = new Counter32((Long)item.getValue());
			else if(variable.getSyntax() == SMIConstants.SYNTAX_BITS)
				value = BitString.fromHexString((String)item.getValue());
			else if(variable.getSyntax() == SMIConstants.SYNTAX_OPAQUE)
				value = new Opaque(((String)item.getValue()).getBytes());
			else if(variable.getSyntax() == SMIConstants.SYNTAX_IPADDRESS)
				value = new IpAddress((InetAddress)item.getValue());
			else if(variable.getSyntax() == SMIConstants.SYNTAX_OBJECT_IDENTIFIER)
				value = new OID(variable.toValueString());

			variable.setVariable(value);
			
			pdu.add(variable);
		}
	}

	/**
	 * Processa o resultado da requisição.
	 * 
	 * @param response Instância contendo as propriedades de execução da requisição.
     * @param requests Lista contendo as propriedades das OIDs desejadas.
	 * @return Lista contendo os dados do resultado da requisiçào.
	 * @throws SnmpException
	 */
    private Collection<SnmpData> buildGetResponse(PDU response, Collection<SnmpRequest> requests) throws SnmpException{
		Collection<SnmpData> results = new LinkedList<SnmpData>();
		
		if(response != null){
    		setResponseCode(response.getErrorStatus());
    		setResponseMessage(response.getErrorStatusText());
    		
    		if(getResponseCode() > 0)
    			throw new SnmpException(getResponseCode(), getResponseMessage());
    		
            @SuppressWarnings("unchecked")
            Vector<VariableBinding> variables           = (Vector<VariableBinding>)response.getVariableBindings();
            VariableBinding         variable            = null;
    		Object                  value               = null;
    		SnmpData                result              = null;
    		SnmpRequest             request             = null;
    		String                  oid                 = "";
    		String                  instance            = "";
    		Boolean                 found               = false;
    		String                  expression          = "";
    		String                  formula             = "";
    		ExpressionProcessor     expressionProcessor = null; 
    		
    		for(Integer cont = 0 ; cont < variables.size() ; cont++){
    			variable = variables.get(cont);
    			oid      = variable.getOid().toString();
    			instance = "";
    			found    = false;
    			
    			result = new SnmpData();
    			result.setRequest(request);
    			
    			for(SnmpRequest item : requests){
    				if(oid.contains(item.getOid())){
    					result.setRequest(item);
    
    					expression = StringUtil.trim(item.getExpression());
    					formula    = StringUtil.trim(item.getFormula());
    					
    					if(expression.length() > 0 || formula.length() > 0)
    						if(expressionProcessor == null)
    							expressionProcessor = new ExpressionProcessor();
    					
    					try{
    						if(!oid.equals(item.getOid()))
    							instance = StringUtil.replaceAll(oid, item.getOid().concat("."), "");
    						
    						found = true;
                        }
                        catch(Throwable e){
                        	found = false;
                        }
    					
    					break;
    				}
    			}
    			
    			if(!found)
    				continue;
    			
    			value = variable.getVariable();
    			
    			if(variable.getSyntax() == SMIConstants.SYNTAX_UNSIGNED_INTEGER32)
    				value = ((Integer32)value).getValue();
    			else if(variable.getSyntax() == SMIConstants.SYNTAX_TIMETICKS)
    				value = ((TimeTicks)value).toMilliseconds();
    			else if(variable.getSyntax() == SMIConstants.SYNTAX_OCTET_STRING)
    				value = new String(((OctetString)value).getValue()); 
    			else if(variable.getSyntax() == SMIConstants.SYNTAX_NULL)
    				value = null;
    			else if(variable.getSyntax() == SMIConstants.SYNTAX_GAUGE32)
    				value = ((Gauge32)value).getValue();
    			else if(variable.getSyntax() == SMIConstants.SYNTAX_INTEGER)
    				value = ((Integer32)value).getValue();
    			else if(variable.getSyntax() == SMIConstants.SYNTAX_INTEGER32)
    				value = ((Integer32)value).getValue();
    			else if(variable.getSyntax() == SMIConstants.SYNTAX_COUNTER64)
    				value = ((Counter64)value).getValue();
    			else if(variable.getSyntax() == SMIConstants.SYNTAX_COUNTER32)
    				value = ((Counter32)value).getValue();
    			else if(variable.getSyntax() == SMIConstants.SYNTAX_BITS)
    				value = ((BitString)value).getValue();
    			else if(variable.getSyntax() == SMIConstants.SYNTAX_OPAQUE)
    				value = ((Opaque)value).getValue();
    			else if(variable.getSyntax() == SMIConstants.SYNTAX_IPADDRESS)
    				value = ((IpAddress)value).getInetAddress();
    			else if(variable.getSyntax() == SMIConstants.SYNTAX_OBJECT_IDENTIFIER)
    				value = variable.toValueString();
    			
    			if(expressionProcessor != null){
    				ExpressionProcessorUtil.addVariable("value", value);
    				
    				if(expression.length() > 0){
        				try{
        					Boolean evaluationResult = expressionProcessor.evaluate(expression);
        					
        					if(!evaluationResult)
        						continue;
                        }
                        catch(Throwable e){
                        	throw new SnmpException(e);
                        }
    				}
    
    				if(formula.length() > 0){
        				try{
        					value = expressionProcessor.evaluate(formula);
                        }
                        catch(Throwable e){
                        	throw new SnmpException(e);
                        }
    				}
    			}
    			
    			result.setInstance(instance);
    			result.setType(variable.getSyntax());
    			result.setValue(value);
    			results.add(result);
    		}
    		
    		setResponse(results);
		}
		
		return results;
	}
	
	/**
	 * Efetua um WALK assíncrono.
	 * 
	 * @param listener Instância da classe de callback.
	 * @param oid String contendo a OID desejada.
	 * @throws SnmpException
	 */
	public void asyncWalk(SnmpResponseListener listener, String oid) throws SnmpException{
		asyncWalk(listener, NetworkConstants.DEFAULT_SNMP_MAX_RESULTS, oid);
	}
	
    /**
     * Efetua um WALK assíncrono.
     * 
     * @param listener Instância da classe de callback.
     * @param maxResults Valor inteiro contendo a quantidade máxima de registros devem retornar.
     * @param oid String contendo a OID desejada.
     * @throws SnmpException
     */
	public void asyncWalk(SnmpResponseListener listener, Integer maxResults, String oid) throws SnmpException{
		SnmpRequest request = new SnmpRequest();
		
		request.setOid(oid);
		
		asyncWalk(listener, maxResults, request);
	}
	
    /**
     * Efetua um WALK assíncrono.
     * 
     * @param listener Instância da classe de callback.
     * @param request Instância contendo as propriedades da OID desejada.
     * @throws SnmpException
     */
	public void asyncWalk(SnmpResponseListener listener, SnmpRequest request) throws SnmpException{
		asyncWalk(listener, NetworkConstants.DEFAULT_SNMP_MAX_RESULTS, request);
	}
	
    /**
     * Efetua um WALK assíncrono.
     * 
     * @param listener Instância da classe de callback.
     * @param maxResults Valor inteiro contendo a quantidade máxima de registros devem retornar.
     * @param request Instância contendo as propriedades da OID desejada.
     * @throws SnmpException
     */
	public void asyncWalk(SnmpResponseListener listener, Integer maxResults, SnmpRequest request) throws SnmpException{
		asyncGet(PDU.GETNEXT, maxResults, listener, Arrays.asList(request));
	}
	
    /**
     * Efetua um WALK síncrono.
     * 
     * @param oid String contendo a OID desejada.
     * @throws SnmpException
     */
	public Collection<SnmpData> walk(String oid) throws SnmpException{
		return walk(NetworkConstants.DEFAULT_SNMP_MAX_RESULTS, oid);
	}

    /**
     * Efetua um WALK síncrono.
     * 
     * @param maxResults Valor inteiro contendo a quantidade máxima de registros devem retornar.
     * @param oid String contendo a OID desejada.
     * @throws SnmpException
     */
	public Collection<SnmpData> walk(Integer maxResults, String oid) throws SnmpException{
		SnmpRequest request = new SnmpRequest();
		
		request.setOid(oid);
		
		return walk(maxResults, request);
	}
	
    /**
     * Efetua um WALK síncrono.
     * 
     * @param request Instância contendo as propriedades da OID desejada.
     * @throws SnmpException
     */
	public Collection<SnmpData> walk(SnmpRequest request) throws SnmpException{
		return walk(NetworkConstants.DEFAULT_SNMP_MAX_RESULTS, request);
	}
	
    /**
     * Efetua um WALK síncrono.
     * 
     * @param maxResults Valor inteiro contendo a quantidade máxima de registros devem retornar.
     * @param request Instância contendo as propriedades da OID desejada.
     * @throws SnmpException
     */
	public Collection<SnmpData> walk(Integer maxResults, SnmpRequest request) throws SnmpException{
		return get(PDU.GETBULK, maxResults, Arrays.asList(request));
	}
	
    /**
     * Efetua um GET assíncrono.
     * 
     * @param listener Instância da classe de callback.
     * @param oids Lista de OIDs a serem consideradas na requisição.
     * @throws SnmpException
     */
	public void asyncGet(SnmpResponseListener listener, String... oids) throws SnmpException{
		Collection<SnmpRequest> requests = new LinkedList<SnmpRequest>();
		SnmpRequest             request  = null;
		
		for(String oid : oids){
			request = new SnmpRequest();
			request.setOid(oid);
			
			requests.add(request);
		}

		asyncGet(PDU.GET, NetworkConstants.DEFAULT_SNMP_MAX_RESULTS, listener, requests);
	}

    /**
     * Efetua um GET assíncrono.
     * 
     * @param listener Instância da classe de callback.
     * @param requests Lista contendo as propriedades das OIDs desejadas.
     * @throws SnmpException
     */
	public void asyncGet(SnmpResponseListener listener, SnmpRequest... requests) throws SnmpException{
		asyncGet(PDU.GET, NetworkConstants.DEFAULT_SNMP_MAX_RESULTS, listener, Arrays.asList(requests));
	}
	
    /**
     * Efetua um GET assíncrono.
     * 
     * @param listener Instância da classe de callback.
     * @param requests Lista contendo as propriedades das OIDs desejadas.
     * @throws SnmpException
     */
	public void asyncGet(SnmpResponseListener listener, Collection<SnmpRequest> requests) throws SnmpException{
		asyncGet(PDU.GET, NetworkConstants.DEFAULT_SNMP_MAX_RESULTS, listener, requests);
	}

    /**
     * Efetua um GET assíncrono.
     * 
     * @param type Valor inteiro contendo o tipo da requisição.
     * @param maxResults Valor inteiro contendo a quantidade máxima de registros devem retornar.
     * @param listener Instância da classe de callback.
     * @param requests Lista contendo as propriedades das OIDs desejadas.
     * @throws SnmpException
     */
	private void asyncGet(Integer type, Integer maxResults, SnmpResponseListener listener, Collection<SnmpRequest> requests) throws SnmpException{
		synchronized(session){
			buildGetRequest(type, maxResults, requests);
			
			try{
				if(listener != null)
					listener.setRequests(requests);
				
	            session.send(pdu, readTarget, null, listener);
            }
            catch(IOException e){
	            throw new SnmpException(e);
            }
        }
	}
	
    /**
     * Efetua um GET síncrono.
     * 
     * @param oids Lista contendo as OIDs desejadas.
     * @throws SnmpException
     */
	public Collection<SnmpData> get(String... oids) throws SnmpException{
		Collection<SnmpRequest> requests = new LinkedList<SnmpRequest>();
		SnmpRequest             request  = null;
		
		for(String oid : oids){
			request = new SnmpRequest();
			request.setOid(oid);
			
			requests.add(request);
		}

		return get(PDU.GET, NetworkConstants.DEFAULT_SNMP_MAX_RESULTS, requests);
	}

    /**
     * Efetua um GET síncrono.
     * 
     * @param requests Lista contendo as propriedades das OIDs desejadas.
     * @throws SnmpException
     */
	public Collection<SnmpData> get(SnmpRequest... requests) throws SnmpException{
		return get(PDU.GET, NetworkConstants.DEFAULT_SNMP_MAX_RESULTS, Arrays.asList(requests));
	}
	
    /**
     * Efetua um GET síncrono.
     * 
     * @param requests Lista contendo as propriedades das OIDs desejadas.
     * @throws SnmpException
     */
	public Collection<SnmpData> get(Collection<SnmpRequest> requests) throws SnmpException{
		return get(PDU.GET, NetworkConstants.DEFAULT_SNMP_MAX_RESULTS, requests);
	}
	
    /**
     * Efetua um GET síncrono.
     * 
     * @param type Valor inteiro contendo o tipo da requisição.
     * @param maxResults Valor inteiro contendo a quantidade máxima de registros devem retornar.
     * @param requests Lista contendo as propriedades das OIDs desejadas.
     * @throws SnmpException
     */
	private Collection<SnmpData> get(Integer type, Integer maxResults, Collection<SnmpRequest> requests) throws SnmpException{
		synchronized(session){
			buildGetRequest(type, maxResults, requests);
			
			ResponseEvent event = null;
			
			try{
				event = session.send(pdu, readTarget);
			}
			catch(IOException e){
				throw new SnmpException(e);
			}
			
			PDU response = event.getResponse();
			
			return buildGetResponse(response, requests);
        }
	}
	
	/**
	 * Efetua um SET.
	 * 
	 * @param data Lista contendo os dados a serem escritos.
	 * @throws SnmpException
	 */
	public void set(Collection<SnmpData> data) throws SnmpException{
		synchronized(session){
			buildSetRequest(data);
			
			try{
				session.send(pdu, writeTarget);
			}
			catch(IOException e){
				throw new SnmpException(e);
			}
        }
	}

	/**
	 * FInaliza a comunicação com o serviço SNMP.
	 */
	public void close(){
		synchronized(session){
			try{
	            session.close();
            }
            catch(IOException e){
            }

			instances.remove(getHost());
        }
	}
	
	/**
	 * Classe responsável por capturar o resultado de requisições assíncronas.
	 * 
	 * @author fvilarinho
	 * @since 2.0
	 */
	public abstract class SnmpResponseListener implements ResponseListener{
		private Collection<SnmpRequest> requests = null;
		
		/**
		 * Retorna a lista contendo as propriedades das requisições.
		 * 
		 * @return Lista contendo as propriedades das requisições.
		 */
        public Collection<SnmpRequest> getRequests(){
        	return requests;
        }

        /**
         * Define a lista contendo as propriedades das requisições.
         * 
         * @param requests Lista contendo as propriedades das requisições.
         */
		public void setRequests(Collection<SnmpRequest> requests){
        	this.requests = requests;
        }

		/**
		 * @see org.snmp4j.event.ResponseListener#onResponse(org.snmp4j.event.ResponseEvent)
		 */
		public void onResponse(ResponseEvent event){
        	PDU response = event.getResponse();
        	
        	try{
        		onResponse(buildGetResponse(response, requests));
            }
            catch(Throwable e){
            }
            finally{
            	((Snmp)event.getSource()).cancel(event.getRequest(), this);
            }
        }

		/**
		 * Método executado no término da execução de requisições assíncronas.
		 * 
		 * @param result Lista contendo o resultado das requisições.
		 */
        public abstract void onResponse(Collection<SnmpData> result);
	}
}
