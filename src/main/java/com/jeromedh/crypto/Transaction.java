
package com.jeromedh.crypto;

/**
 * La transaction
 *
 * <b>La transaction entre deux wallets</b>
 *
 * @date 29/07/2019
 * 
 * @author Jerome Dh
 *
 */
 
import java.util.List;
import java.util.ArrayList;

import java.security.PublicKey;
import java.security.PrivateKey ;

public class Transaction
{
	/**
	 * Indentifiant de la transaction
	 *
	 * @var int
	 */
	private String transactionId;

	/**
	 * Le receiver
	 *
	 * @var PublicKey
	 */
	private PublicKey receiver;
	
	/**
	 * Le sender
	 *
	 * @var PublicKey
	 */
	private PublicKey sender;

	/**
	 * Montant de la transaction
	 *
	 * @var double
	 */
	private double amout;
	
	/**
	 * Signature de la transaction
	 *
	 * @var byte[]
	 */
	private byte[] signature;

	/**
	 * Liste des transactions entrantes
	 *
	 * @var List<TransactionInput>
	 */
	private List<TransactionInput> inputs;
	
	/**
	 * Liste des transactions sortantes
	 *
	 * @var List<TransactionOutput>
	 */
	public static List<TransactionOutput> outputs;

	/**
	 * Constructeur
	 *
	 * @param sender
	 * @param receiver
	 * @param amout
	 * @param inputs
	 *
	 * @throws Exception
	 */
	public Transaction(
		PublicKey sender, 
		PublicKey receiver, 
		double amout, 
		List<TransactionInput> inputs
	) throws Exception
	{
		setSender(sender);
		setReceiver(receiver);
		setInputs(inputs);
		setAmout(amout);
		setOutputs();

		calculateHash();

	}
	
	/**
	 * Vérifier la fiabilité d'une transaction
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	public boolean verifyTransaction() throws Exception
	{
		if( ! verifySignature())
		{
			throw new Exception("Echec de vérification de la signature");
		}

		//Unspent transaction
		for(TransactionInput transactionInput : inputs)
		{
			transactionInput.setUTXO(BlockChain.UTXOs.get(
				transactionInput.getTransactionOutputId()
			));
		}

		//Tansaction en deux parties: amout => receiver et (balance-amout) => sender
		outputs.add(new TransactionOutput(this.receiver, amout, transactionId));
		outputs.add(new TransactionOutput(this.sender, (getInputsSum() - amout), transactionId));

		//Mettre les outputs dans la blockchain
		for(TransactionOutput transactionOuput : outputs)
		{
			BlockChain.UTXOs.put(transactionOuput.getId(), transactionOuput);
		}

		//Rétirer les inputs de la blockchain UTXO
		for(TransactionInput transactionInput : inputs)
		{
			if(transactionInput.getUTXO() != null)
			{
				BlockChain.UTXOs.remove(transactionInput.getUTXO().getId());
			}
		}

		return true;

	}

	/**
	 * Sommer les transactions entrantes
	 *
	 * @return double
	 */
	public double getInputsSum()
	{
		double sum = 0;

		for(TransactionInput input : inputs)
		{
			if(input.getUTXO() != null)
			{
				sum += input.getUTXO().getAmount();
			}
		}

		return sum;

	}

	/**
	 * Générer une signature pour la transaction
	 *
	 * @param privateKey
	 *
	 * @throws Exception 
	 */
	public void generateSignature(PrivateKey privateKey) throws Exception
	{
		String data = sender.toString() + receiver.toString() + Double.toString(amout);

		this.signature = CryptoHelper.applyECDSASignature(privateKey, data);

	}
	
	/**
	 * Vérifier la signature de la transaction
	 *
	 * @return boolean
	 */
	public boolean verifySignature() throws Exception
	{
		String data = sender.toString() + receiver.toString() + Double.toString(amout);

		return CryptoHelper.verifyECDSASignature(sender, signature, data);

	}

	/**
	 * Calculer le code de hashage pour la transaction
	 *
	 * @throws Exception
	 */
	public void calculateHash() throws Exception
	{
		String toHash = sender.toString() + receiver.toString() + Double.toString(amout);

		transactionId = CryptoHelper.generateHash(toHash);

	}

	// ============= Getters =============
	
	public double getAmount()
	{
		return amout;
	}

	public PublicKey getSender()
	{
		return sender;
	}

	public PublicKey getReceiver()
	{
		return receiver;
	}

	public List<TransactionInput> getInputs()
	{
		return inputs;
	}
	
	public String getTransactionId()
	{
		return transactionId;
	}

	// ============ Setters ==============

	public void setSender(PublicKey sender)
	{
		this.sender = sender;
	}

	public void setReceiver(PublicKey receiver)
	{
		this.receiver = receiver;
	}

	public void setInputs(List<TransactionInput> inputs)
	{
		this.inputs = inputs;
	}

	public void setOutputs()
	{
		this.outputs = new ArrayList<TransactionOutput>();
	}
	
	public void setTransactionId(String id)
	{
		this.transactionId = id;
	}
	
	public void setAmout(double amout)
	{
		this.amout = amout;
	}

}
	
	
