package Projet;

import java.util.Random;
import java.text.DateFormat;
import java.util.Date;

public class Block {
	private String hashPrecedent;
	private String listeTransaction[];
	private int nbTransaction;
	private int Nonce;
	private String merkel;
	private String HashBlock;
	private Date date;
	
	/**
	 * Contructeur avec liste de transaction
	 * @param String hashPrecedent
	 * @param String[] listeTransaction
	 * @param nbTransaction : nombre de transaction
	 */
	public Block(String hashPrecedent, String[] listeTransaction, int nbTransaction) {
		this.hashPrecedent = hashPrecedent;
		this.listeTransaction = listeTransaction;
		this.nbTransaction = nbTransaction;
		Nonce = 0;
		date = new Date();
		// faire le merkel des transaction
		merkel = "54247930ee9b51c10e438ff3a2ad25c7eca5b890213b4e77d12012cc307017c0";
	}
	
	/**
	 * Contructeur avec generation aleatoire de la liste de transaction
	 * @param String hashPrecedent
	 */
	public Block(String hashPrecedent) {
		this.hashPrecedent = hashPrecedent;
		this.Nonce = 0;
		this.date = new Date(); //Date au moment de la creation d'un block
		
		Random random = new Random();
		this.nbTransaction = random.nextInt(10)+1; //Genere un nombre aleatoire de transaction
		this.listeTransaction = new String[nbTransaction];
		for (int i=0; i<this.nbTransaction; i++ ) {
			listeTransaction[i] = generateTransaction(); // Met nbTransaction dans le tableau
		}
		// faire le merkel des transaction
		merkel = "846047930ee9b51c10e438ff3a2ad25c7eca5b890213b4e77d12012cc307017c0";
		
	}

	/**
	 * @return String transaction aleatoire
	 */
	public static String generateTransaction() {
		String[] tab = {"Alex","Damien","Paul","Pierre","Lisa","Chloé","Astèrix", "Obélix","Thomas","Clement","Steven","Bilel","Cédric","Omar","Bastien"};
		int tablength = tab.length;
		Random random = new Random();
		String name1 = tab[random.nextInt(tablength)];
		String name2= tab[random.nextInt(tablength)];
		int Bnb = random.nextInt(1000)+1; //genere un montant pour la transaction entre 1 et 1000
		
		while (name1 == name2) { //Verifie que les deux nom soit different 
			name2 = tab[random.nextInt(tablength)];
		}
		return (name1 + " envoie " + Bnb +" Bnb à " + name2);
	}

	/**
	 * @return the nonce
	 */
	public int getNonce() {
		return Nonce;
	}

	/**
	 * @param nonce the nonce to set
	 */
	public void setNonce(int nonce) {
		Nonce = nonce;
	}

	/**
	 * @return the hashBlock
	 */
	public String getHashBlock() {
		return HashBlock;
	}

	/**
	 * @param hashBlock the hashBlock to set
	 */
	public void setHashBlock(String hashBlock) {
		HashBlock = hashBlock;
	}

	/**
	 * @return the hashPrecedent
	 */
	public String getHashPrecedent() {
		return hashPrecedent;
	}

	/**
	 * @return the listeTransaction
	 */
	public String[] getListeTransaction() {
		return listeTransaction;
	}

	/**
	 * @return the nbTransaction
	 */
	public int getNbTransaction() {
		return nbTransaction;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM);
	    return shortDateFormat.format(date);
	  }

	/**
	 * @return the merkel
	 */
	public String getMerkel() {
		return merkel;
	}
	
	
	
}	
	
	

