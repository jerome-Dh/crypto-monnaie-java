
package com.jeromedh.crypto;

/**
 * Les constantes pour la blockchain
 *
 * <b>Définission des constantes utiles pour la blockchain</b>
 *
 * @date 28/07/2019
 * 
 * @author Jerome Dh
 *
 */
public class Constants
{

	/**
	 * Niveau de difficulté de hashage
	 * Defini le nombre de "0" qui préffixe un code hashé
	 *
	 * @var int
	 */
	public static final int DIFFICULTY = 5;
	
	/**
	 * Valeur Inter-Miner
	 *
	 * @var double
	 */
	public static final double MINER_REWARD = 10;
	
	/**
	 * Le code de hashage initial
	 *
	 * @var String
	 */
	public static final String GENESIS_PREV_HASH = 
	"0000000000000000000000000000000000000000000000000000000000000000";


	/**
	 * Contructeur
	 */
	private Constants()
	{
	}

}
	
	