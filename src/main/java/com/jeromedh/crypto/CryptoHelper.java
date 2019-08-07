
package com.jeromedh.crypto;

/**
 * CryptoHelper 
 *
 * <b>Les helpers pour le hashage SHA-256, la génération des clés et la
 * vérification de la signature</b>
 *
 * @date 28/07/2019
 * 
 * @author Jerome Dh
 *
 */

import java.security.MessageDigest;
import java.security.KeyPair;
import java.security.spec.ECGenParameterSpec;
import java.security.SecureRandom;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.Signature;

public class CryptoHelper
{

	/**
	 * Hasher un objet
	 *
	 * @param data La donnée à hasher
	 *
	 * @return String La valeur hashé
	 *
	 * @throws Exception
	 */
	public static String generateHash(String data) throws Exception
	{

		MessageDigest digest = MessageDigest.getInstance("SHA-256");

		byte[] hash = digest.digest(data.getBytes("UTF-8"));

		StringBuffer chaine = new StringBuffer();

		//Traduire HEX => String
		for(int i = 0; i < hash.length; ++i)
		{
			String hex = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1)
			{
				chaine.append(0);
			}
			chaine.append(hex);
		}

		return chaine.toString();

	}

	/**
	 * Obtenir la pair de clé privée et publique
	 *
	 * @return KeyPair Le couple clé privée et clé publique
	 *
	 * @throws Exception
	 */
	public static KeyPair ellipticCurveCrypto() throws Exception
	{
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

		ECGenParameterSpec params = new ECGenParameterSpec("secp192k1");

		keyPairGenerator.initialize(params, secureRandom);

		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		return keyPair;

	}
	
	/**
	 * Générer la signature d'un message
	 *
	 * @param privateKey La clé privée
	 * @param msg Le msg
	 *
	 * @return byte[] Le message signé
	 *
	 * @throws Exception
	 */
	public static byte[] applyECDSASignature(PrivateKey privateKey, String msg) throws Exception
	{
		byte[] output = new byte[0];

		Signature signature = Signature.getInstance("ECDSA", "BC");

		signature.initSign(privateKey);

		byte[] msgByte = msg.getBytes();

		signature.update(msgByte);

		return signature.sign();

	}
	
	/**
	 * Valider si une transaction est valide ou non
	 *
	 * @param publicKey La clé publique
	 * @param msg Le message
	 * @param signature La signature
	 *
	 * @return boolean true si exacte et false sinon
	 *
	 * @throws Exception
	 */
	public static boolean verifyECDSASignature(PublicKey publicKey, byte[] signature, String msg) throws Exception
	{
		Signature ecdsaSignature = Signature.getInstance("ECDSA", "BC");

		ecdsaSignature.initVerify(publicKey);

		ecdsaSignature.update(msg.getBytes());

		return ecdsaSignature.verify(signature);

	}
	
	
}