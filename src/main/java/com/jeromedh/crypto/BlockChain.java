
package com.jeromedh.crypto;

/**
 * La blockchain
 *
 * <b>Réprésente la blockchain par la liaison des Blocks</b>
 *
 * @date 28/07/2019
 * 
 * @author Jerome Dh
 *
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class BlockChain
{
	/**
	 * La liste chainée des blocks
	 *
	 * @var List
	 */
	public static List<Block> blockchain;

	/**
	 * Structure de données pour la mempool: Répresentes les transactions
	 * non encore ajoutées à la Blockchain par un miner
	 *
	 * @var Map<String, TransactionOutput>
	 */
	public static Map<String, TransactionOutput> UTXOs; 

	/**
	 * Constructeur
	 */
	public BlockChain()
	{
		blockchain = new ArrayList<Block>();

		UTXOs = new HashMap<String, TransactionOutput>();
	}

	/**
	 * Ajouter un block la blockchain
	 *
	 * @param block Le block à ajouter
	 */
	public void addBlock(Block block)
	{
		blockchain.add(block);
	}

	/**
	 * Liste des blocks de la blockchain
	 *
	 * @return List
	 */
	public List<Block> getBlockChain()
	{
		return blockchain;
	}

	/**
	 * Taille de la blockchain
	 *
	 * @return int
	 */
	public int size()
	{
		return blockchain.size();
	}

	/**
	 * Réprésentation textuelle de la blockchain
	 *
	 * @return String
	 */
	@Override
	public String toString()
	{
		String text = "";
		for(Block block : blockchain)
		{
			text += block.toString() + "\n";
		}
		
		return text;
	}

}	