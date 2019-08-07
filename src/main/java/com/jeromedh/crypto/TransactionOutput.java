
package com.jeromedh.crypto;

/**
 * La transaction sortante
 *
 * <b>La transaction sortante pour un wallet</b>
 *
 * @date 29/07/2019
 * 
 * @author Jerome Dh
 *
 */
 
import java.security.PublicKey;

public class TransactionOutput
{
	/**
	 * Indentifiant de la transaction
	 *
	 * @var int
	 */
	private String id;
	
	/**
	 * La transaction parente
	 *
	 * @var String
	 */
	private String parentTransactionId;
	
	/**
	 * La clé public du receiver
	 *
	 * @var PublicKey
	 */
	private PublicKey receiver;
	
	/**
	 * Le montant de la transaction
	 *
	 * @var double
	 */
	public double amount;
	
	
	/**
	 * Constructeur
	 *
	 * @param receiver
	 * @param amount
	 * @param parentTransactionId
	 */
	public TransactionOutput(PublicKey receiver, double amount, String parentTransactionId) throws Exception
	{
		setReceiver(receiver);
		setAmount(amount);
		setParentTransactionId(parentTransactionId);
		
		generateId();
	}

	/**
	 * Générer un identifiant unique pour la transaction
	 */
	public void generateId() throws Exception
	{
		this.id = CryptoHelper.generateHash(
			receiver.toString() +
			Double.toString(amount) +
			parentTransactionId
		);
	}
	
	/**
	 * Vérifier le owner de la transaction
	 *
	 * @return boolean
	 */
	public boolean isMine(PublicKey publicKey)
	{
		return receiver == publicKey;
	}


	// ========== Getters ==========
	
	public String getId()
	{
		return id;
	}

	public String getParentTransactionId()
	{
		return parentTransactionId;
	}
	
	public double getAmount()
	{
		return amount;
	}

	// ========== Setters ==========
	
	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public void setReceiver(PublicKey receiver)
	{
		this.receiver = receiver;
	}

	public void setParentTransactionId(String parentTransactionId)
	{
		this.parentTransactionId = parentTransactionId;
	}	


}
	
	