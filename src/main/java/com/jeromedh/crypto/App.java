
package com.jeromedh.crypto;

/**
 * Utilisation de la blockchain
 *
 * <b>Crée, valide et affiche les transactions de la blockchain</b>
 *
 * @date 28/07/2019
 * 
 * @author Jerome Dh
 *
 */

import java.util.ArrayList;
import java.security.Security;
import java.security.Provider;

public class App
{

	/**
	 * La méthode main 
	 * 
	 * @param String[]
	 *
	 */
	public static void main(String ... args)
	{
		try
		{

			System.out.println();

			//Charger la couche de sécurité
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			// Créer les wallets
			Wallet user1 = new Wallet(),
				user2 = new Wallet(),
				lender = new Wallet();

			//La blockChain
			BlockChain blockChain = new BlockChain();

			//Le Miner
			Miner miner = new Miner();

			//Transaction initiale
			Transaction genesisTransaction = new Transaction(lender.getPublicKey(), user1.getPublicKey(), 500, null);

			//Générer la signature
			genesisTransaction.generateSignature(lender.getPrivateKey());
			genesisTransaction.setTransactionId("0");
			genesisTransaction.outputs.add(
				new TransactionOutput(
					genesisTransaction.getReceiver(), 
					genesisTransaction.getAmount(), 
					genesisTransaction.getTransactionId()
				)
			);

			//Ajouter à la mempool
			BlockChain.UTXOs.put(genesisTransaction.outputs.get(0).getId(), genesisTransaction.outputs.get(0));

			//Créer un prémier block pour la blockChain
			System.out.println("Création du prémier block");
			Block geneseBlock = new Block(Constants.GENESIS_PREV_HASH);
			geneseBlock.addTransaction(genesisTransaction);
			
			//Ajouter le block à la BlockChain
			miner.mine(geneseBlock, blockChain);

			//Transferer les fonds entre User1 et User2
			Block block1 = new Block(geneseBlock.getHash());
			System.out.println();
			System.out.println("User1 balance: " +  user1.calculateBalance());
			System.out.println("User1 envoi 150 coins à user2");
			block1.addTransaction(user1.transfertMoney(user2.getPublicKey(), 150));
			miner.mine(block1, blockChain);

			//Afficher les comptes
			System.out.println();
			System.out.println("User1 balance: " +  user1.calculateBalance());
			System.out.println("User2 balance: " +  user2.calculateBalance());
			
			//User1 envoi 100 coins à User2
			Block block2 = new Block(block1.getHash());
			System.out.println("User1 envoi 100 coins à user2");
			block2.addTransaction(user1.transfertMoney(user2.getPublicKey(), 100));
			miner.mine(block2, blockChain);
			System.out.println();
			System.out.println("User1 balance: " +  user1.calculateBalance());
			System.out.println("User2 balance: " +  user2.calculateBalance());

			//User2 envoi 100 coins à user1
			Block block3 = new Block(block2.getHash());
			System.out.println("User2 envoi 100 coins à user1");
			block3.addTransaction(user2.transfertMoney(user1.getPublicKey(), 100));
			miner.mine(block3, blockChain);
			System.out.println();
			System.out.println("User1 balance: " +  user1.calculateBalance());
			System.out.println("User2 balance: " +  user2.calculateBalance());
			
			System.out.println();
			System.out.println("Miner'reward: " +  miner.getReward());
			
			//User1 envoi plus de coins qu'il n'en possède à User2
			Block block4 = new Block(block3.getHash());
			System.out.println();
			System.out.println("User1 envoi 600 coins à user2");
			block4.addTransaction(user1.transfertMoney(user2.getPublicKey(), 600));
			miner.mine(block4, blockChain);
			System.out.println();
			System.out.println("User1 balance: " +  user1.calculateBalance());
			System.out.println("User2 balance: " +  user2.calculateBalance());

		}
		catch(Exception e)
		{
			System.out.println("Erreur: " + e.getMessage());
		}
	}
	
}