package br.org.pmc.util;

/**
 * Classe para armazenamento de constantes úteis
 * 
 * @author PlinioMos
 *
 */
public class Constants {

	public static String PERSISTENCE_UNIT_NAME = "pmc-protocolo";
	public static String LOGIN_PATH_NAME = "/login";

	public static String SESSION_USER_LOGED = "userLoged";
	public static String SESSION_USER_LOGED_TYPE = "userLogedType";
	
	//Status Reserva
	
	public static int AVAILABLE_FOR_RESERVATION = 1;
	public static int UNAVAILABLE_FOR_RESERVATION = 0;

	// Entity Status
	public static final boolean ACTIVE_ENTITY = true;
	public static final boolean INACTIVE_ENTITY = false;
	
	// Unidades de Medida
	public static final int UNIDADE_DE_MEDIDA_QUANTIDADE = 1;
	public static final int UNIDADE_DE_MEDIDA_METRO = 2;
	
	// Status da Entrega da Reser
	public static final int STATUS_ENTREGA_RESERVADO = 0;
	public static final int STATUS_ENTREGA_ENTREGUE = 1;
	public static final int STATUS_ENTREGA_DEVOLVIDO = 2;
	public static final int STATUS_ENTREGA_CANCELADA = 3;

	// User Status
	public static final boolean ACTIVE_USER = true;
	public static final boolean INACTIVE_USER = false;
	
	// Header's
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN_EXTRA = "*";

}
