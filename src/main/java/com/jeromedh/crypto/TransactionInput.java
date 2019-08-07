
package com.jeromedh.crypto;

/**
 * La transaction entrante
 *
 * <b>La transaction entrante pour un wallet</b>
 *
 * @date 29/07/2019
 * 
 * @author Jerome Dh
 *
 */
 

public class TransactionInput
{
	/**
	 * Identifiant de la transaction sortante
	 *
	 * @var String
	 */
	private String transactionOutputId;

	/**
	 * UTXOs (Unspent Transaction Outputs
	 *
	 * @var TransactionOutput
	 */
	private TransactionOutput uTXO;


	/**
	 * Constructeur
	 *
	 * @param transactionOutputId Identifiant de la transaction sortante
	 */
	public TransactionInput(String transactionOutputId)
	{
		setTransactionOutputId(transactionOutputId);
	}

	// ============== Getters ==============

	public String getTransactionOutputId()
	{
		return transactionOutputId;
	}

	public TransactionOutput getUTXO()
	{
		return uTXO;
	}


	// ============== Setters ===============
	
	public void setTransactionOutputId(String transactionOutputId)
	{
		this.transactionOutputId = transactionOutputId;
	}

	public void setUTXO(TransactionOutput uTXO)
	{
		this.uTXO = uTXO;
	}

}