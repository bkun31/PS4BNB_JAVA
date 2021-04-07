package Projet;

import java.util.Random;

public class MerkelRoot {
	
	/**
	 * @param String Premiere transaction
	 * @param String Deuxième transaction
	 * @return String Hash des deux transaction
	 */
	public String hash2Transaction(String trans_1, String trans_2) {
		String transaction = trans_1 + trans_2;
		transaction = HashUtil.applySha256(transaction);
		return transaction;
	}
	
	public String hashAllTransaction(int nbTransaction) {
		if (nbTransaction == 1) {
			return HashUtil.applySha256(generateTransaction() );
		}
		else if (nbTransaction == 2) {
			return hash2Transaction(generateTransaction(), generateTransaction() );
		}
		else if (nbTransaction%2 == 0 && (nbTransaction/2)%2 == 0) {
			nbTransaction/=2;
			return hash2Transaction(hashAllTransaction(nbTransaction), hashAllTransaction(nbTransaction) );
		}
		else if (nbTransaction%2 == 0) {
			nbTransaction/=2;
			return hash2Transaction(hashAllTransaction(nbTransaction+1), hashAllTransaction(nbTransaction-1) );
		}
		else {
			nbTransaction/=2;
			return hash2Transaction(hashAllTransaction(nbTransaction+1), hashAllTransaction(nbTransaction) );
		}
	}
}
