
package com.jeromedh.crypto;

/**
 * Le MerkleTree
 *
 * <b>Calculer une valeur de hash (MerkleRoot) pour toutes les transactions de 
 * la liste</b>
 *
 * @date 29/07/2019
 * 
 * @author Jerome Dh
 *
 */


import java.util.ArrayList;
import java.util.List;

public class MerkleTree 
{

	/**
	 * Liste de transactions
	 *
	 * @var List<Transaction>
	 */
	private List<Transaction> transactions;

	/**
	 * Constructor
	 *
	 * @param
	 */
	public MerkleTree(List<Transaction> transactions) 
	{
		this.transactions = transactions;
	}

	/**
	 * Construire un hashage unique pour la liste
	 *
	 * @return List<String>
	 */
	public List<String> getMerkeRoot() throws Exception 
	{
		
		List<String> stringTransactions = new ArrayList<>();
		stringTransactions.add(this.transactions.toString());

		return stringTransactions;

		//Transformer List<Transaction> en List<String>
		// for(Transaction tr : this.transactions)
			// stringTransactions.add(tr.toString());

		// return construct(stringTransactions);

	}
	
	/**
	 * Fonction récursive de calcul des hashs pour chaque couple de Transactions
	 *
	 * @param transactions
	 *
	 * @return List<String>
	 */
	private List<String> construct(List<String> transactions) throws Exception 
	{

		//it contains fewer and fewer items in every iteration
		List<String> updatedList = new ArrayList<>();

		//Condition d'arrêt
		if(transactions.size() == 1)
		{
			updatedList.add(mergeHash(transactions.get(transactions.size()-1),transactions.get(transactions.size()-1)));

			return updatedList;
		}

		//Fusionner les voisins deux à deux
		for(int i = 0; i < transactions.size()-1; i+=2)
		{
			updatedList.add(mergeHash(transactions.get(i),transactions.get(i+1)));
		}

		//S'il manque un élement
		if(transactions.size()%2 == 1)
		{
			updatedList.add(mergeHash(transactions.get(transactions.size()-1),transactions.get(transactions.size()-1)));
		}

		//recursive method call
		return construct(updatedList);

	}

	/**
	 * Fusionner deux hashs et calculer le hash correspondant
	 *
	 * @param hash1
	 * @param hash2
	 *
	 * @return String
	 */
	private String mergeHash(String hash1, String hash2) throws Exception 
	{		
		return CryptoHelper.generateHash(hash1+ hash2);

	}

}
