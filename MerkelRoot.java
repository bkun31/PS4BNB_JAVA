
package Projet;

import java.util.Random;

public class MerkelRoot {
	
	/**
	 * @return String transaction entierement aleatoire
	 */
	public static String generateTransaction() {
		String[] tab = {"Alex","Damien","Paul","Pierre","Lisa","Chloé","Astèrix", "Obélix","Thomas","Clement","Steven","Bilel","Cédric","Omar","Bastien"};
		int tablength = tab.length;
		Random random = new Random();
		String name1 = tab[random.nextInt(tablength)];
		String name2= tab[random.nextInt(tablength)];
		int Bnb = random.nextInt(1000);
		
		while (name1 == name2) {
			name2 = tab[random.nextInt(tablength)];
		}
		return (name1 + " envoie " + Bnb +" Bnb à " + name2);
	}
	
	/**
	 * @param String Premiere transaction
	 * @param String Deuxième transaction
	 * @return String Hash des deux transaction
	 */
	public static String hash2Transaction(String trans_1, String trans_2) {
		String transaction = trans_1 + trans_2;
		transaction = HashUtil.applySha256(transaction);
		return transaction;
	}
	
	public static String hashAllTransaction(int nbTransaction) {
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
