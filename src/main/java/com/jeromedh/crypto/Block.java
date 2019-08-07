
package com.jeromedh.crypto;

/**
 * Le Block
 *
 * <b>La réprésentation d'un block pour la blockchain</b>
 *
 * @date 28/07/2019
 * 
 * @author Jerome Dh
 *
 */
 
 
 
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Block
{
	/**
	 * Identifiant du block
	 *
	 * @var int
	 */
	private int id;

	/**
	 * Identifiant unique
	 *
	 * @var int
	 */
	private int nonce;

	/**
	 * Le timestamp UNIX
	 *
	 * @var long
	 */
	private long timestamp;

	/**
	 * Le code de hashage
	 *
	 * @var String
	 */
	private String hash;

	/**
	 * Le code de hashage du block précédent
	 *
	 * @var String
	 */
	private String previousHash;

	/**
	 * Les transactions
	 *
	 * @var List<Transaction>
	 */
	private List<Transaction> transactions;


	/**
	 * Constructeur
	 *
	 * @param id  Identifiant
	 * @param transactions Les transactions
	 * @param previousHash Le code hashage du block précédent
	 */
	public Block(int id, List<Transaction> transactions, String previousHash) throws Exception
	{
		setId(id);
		setTransactions(transactions);
		setPreviousHash(previousHash);
		setTimestamp(new Date().getTime());
		
		//Générer le code de hashage
		this.generateHash();

	}

	public Block(String previousHash ) throws Exception 
	{

		this(0, new ArrayList<Transaction>(), previousHash);

	}

	/**
	 * Générer un code de hashage unique pour ce block
	 */
	public void generateHash() throws Exception
	{
		MerkleTree merkleTree = new MerkleTree(transactions);
		List<String> listTransaction = merkleTree.getMerkeRoot();
		
		String data = Integer.toString(id) + 
						previousHash +
						Long.toString(timestamp) +
						Integer.toString(nonce) +
						listTransaction.toString();
		
		String hashValue = CryptoHelper.generateHash(data);
		this.hash = hashValue;

	}

	/**
	 * Inscrémenter le nonce
	 */
	public void incrementNonce()
	{
		nonce++;
	}

	/**
	 * La répresentation textuelle du block
	 *
	 * @return String 
	 */
	@Override
	public String toString()
	{
		return id + "-" + transactions.toString() + "-" + hash + "-" + previousHash + "-";
	}

	// =============== Getters ===============

	public String getHash()
	{
		return hash;
	}

	// =============== Setters ==============
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setPreviousHash(String previousHash)
	{
		this.previousHash = previousHash;
	}
	
	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}

	public void setTransactions(List<Transaction> transactions)
	{
		this.transactions = transactions;
	}

	/** 
	 * Ajouter une transaction
	 *
	 * @param transaction
	 *
	 * boolean
	 */
	public boolean addTransaction(Transaction transaction) throws Exception
	{
		if(transaction != null)
		{
			//S'il ne s'agit pas du premier block, on vérifie la transaction
			if( ! previousHash.equals(Constants.GENESIS_PREV_HASH)
				&&
				! transaction.verifyTransaction())
			{
				throw new Exception("Transaction invalide");
			}

			this.transactions.add(transaction);

			return true;

		}

		return false;
		
	}

						
}
	
