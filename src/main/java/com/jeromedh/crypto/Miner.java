
package com.jeromedh.crypto;

/**
 * Le mineur
 *
 * <b>Valide et opère les transactions sur la blockchain</b>
 *
 * @date 28/07/2019
 * 
 * @author Jerome Dh
 *
 */


public class Miner
{
	/**
	 * Valeur Inter-Miner
	 *
	 * @var double
	 */
	private double reward;


	/**
	 * Valider un block et l'ajouter à la blockchain
	 *
	 * @param block Le block à ajouter
	 * @param blockchain La blockchain courante
	 */
	public void mine(Block block, BlockChain blockchain) throws Exception
	{
		while(notGoldenHash(block))
		{
			block.generateHash();
			block.incrementNonce();
		}

		System.out.println("Le block est créé");
		System.out.println("Le SHA-256 est: " +  block.getHash());

		blockchain.addBlock(block);
		reward += Constants.MINER_REWARD;

	}

	/**
	 * Valider le hashage et les contraintes d'un block
	 *
	 * @param block Le block
	 * 
	 * @return boolean
	 */
	public boolean notGoldenHash(Block block)
	{

		String leadingZero = new String(new char[Constants.DIFFICULTY])
				.replace("\0", "0");

		return ! block.getHash()
				.substring(0, Constants.DIFFICULTY)
				.equals(leadingZero);

	}

	/**
	 * La valeur du miner
	 *
	 * @return double
	 */
	public double getReward()
	{
		return reward;
	}
	
}