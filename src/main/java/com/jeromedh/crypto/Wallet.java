
package com.jeromedh.crypto;

/**
 * Wallet
 *
 * <b>Un porte feuille pour chaque client</b>
 *
 * @date 29/07/2019
 * 
 * @author Jerome Dh
 *
 */
 
import java.util.List;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Map;
import java.util.HashMap;

import java.security.KeyPair;
import java.security.PublicKey ;
import java.security.PrivateKey ;

public class Wallet
{
	/**
	 * Clé privéé
	 *
	 * @var PrivateKey
	 */
	private PrivateKey privateKey;

	/**
	 * Clé publique
	 *
	 * @var PublicKey
	 */
	private PublicKey publicKey;
	
	/**
	 * Constructeur
	 *
	 */
	public Wallet() throws Exception
	{
		KeyPair keyPair = CryptoHelper.ellipticCurveCrypto();
		privateKey = keyPair.getPrivate();
		publicKey = keyPair.getPublic();
	}
	
	/**
	 * Calculer la balance du client à partir de la Mempool
	 *
	 * @return double
	 */
	public double calculateBalance()
	{
		double balance = 0;

		for(Map.Entry<String, TransactionOutput> entry : BlockChain.UTXOs.entrySet())
		{
			TransactionOutput transactionOutput = entry.getValue();
			if(transactionOutput.isMine(publicKey))
			{
				balance += transactionOutput.getAmount();
			}
		}

		return balance;

	}
	
	/**
	 * Transférer un montant à un autre user
	 *
	 * @param receiver 
	 * @param amount
	 *
	 * @return Transaction
	 *
	 * @throws Exception
	 */
	public Transaction transfertMoney(PublicKey receiver, double amount) throws Exception
	{
		if(calculateBalance() < amount)
		{
			throw new Exception("Balance insufisante pour cette transaction");
		}

		//Collecter les fonds du sender
		List<TransactionInput> inputs = new ArrayList<TransactionInput>();

		for(Map.Entry<String, TransactionOutput> entry : BlockChain.UTXOs.entrySet())
		{
			TransactionOutput utxo = entry.getValue();
			if(utxo.isMine(publicKey))
			{
				inputs.add(new TransactionInput(utxo.getId()));
			}
		}

		//Créer la transaction
		Transaction newTransaction = new Transaction(publicKey, receiver, amount, inputs);

		//Générer la signature de la transaction
		newTransaction.generateSignature(privateKey);

		return newTransaction;

	}


	// ========== Getters =============

	public PrivateKey getPrivateKey()
	{
		return privateKey;
	}

	public PublicKey getPublicKey()
	{
		return publicKey;
	}

}
		
		
		