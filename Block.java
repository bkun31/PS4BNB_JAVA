package Projet;

import java.util.Random;
import java.text.DateFormat;
import java.util.Date;
import java.util.Arrays;

public class Block {
	private String hashPrecedent;
	private String listeTransaction[];
	private int nbTransaction;
	private int Nonce;
	private String merkel;
	private String hashBlock;
	private Date date;
	private User user;
	private int numBlock;
	
	/**
	 * Contructeur avec liste de transaction
	 * @param String hashPrecedent
	 * @param String[] listeTransaction
	 * @param nbTransaction : nombre de transaction
	 */
	public Block(String hashPrecedent, String[] listeTransaction, int nbTransaction, int numBlock, User user) {
		this.hashPrecedent = hashPrecedent;
		this.listeTransaction = listeTransaction;
		this.nbTransaction = nbTransaction;
		this.numBlock = numBlock;
		this.user = user;
		Nonce = 0;
		date = new Date();
		// faire le merkel des transaction
		merkel = merkel();
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
		merkel = merkel();
		
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
	 * Hash le block en incrementant le nonce jusqu'a que le block commence par 3x "000"
	 * @param BLock a hash
	 * @return String Hash avec  "0" x difficulte devant
	 */
	public String hashBlock(int difficulte) {
		String dif = "";
		for (int i=0; i<difficulte; i++) {
			dif = dif +"0";
		}
		hashBlock = HashUtil.applySha256(hashPrecedent + merkel + dateToString(date) + Nonce); //Concatenation des info + hash
		while (!hashBlock.substring(0, difficulte).equals(dif)) { //Verifie que les 4 premier caracter du hash soit "0" x difficulte
			Nonce++;
			hashBlock = HashUtil.applySha256(hashPrecedent + merkel + dateToString(date) + Nonce + numBlock + stringListeTx() + user.getNom());
		}
		return hashBlock;
	}
	
	public String merkel() {
		return merkelHash(listeTransaction, nbTransaction);
	}
	
	public String merkelHash(String tabTx[], int nbTx) {
		int i=0;
		int nbTxHash=0;
		String tab[] = Arrays.copyOf(tabTx, nbTx); //crée une copie de tabTx[] de longeur nbTx
		String [] tabHash = tab;
		if (nbTx == 1) {
			return tab[0];
		}
		while (i<nbTx-1) {
			tabHash[nbTxHash]= HashUtil.applySha256(tab[i] + tab[i+1]);
			nbTxHash++;
			i+=2;
		}
		if (nbTx % 2 != 0) {
			tabHash[nbTxHash]= HashUtil.applySha256(tab[i] + tab[i]);
			nbTxHash++;
		}
		return merkelHash(tabHash, nbTxHash);
	}
	
	public String stringListeTx() {
		String liste = "";
		for (int i=0; i<nbTransaction;i++) {
			liste = liste + listeTransaction[i] + "\n";
		}
		return liste;
	}
	
	public void printBlock() {
		System.out.println("Block numero (Index) : " + numBlock);
		System.out.println("Previous hash : " + hashPrecedent);
		System.out.println("Current hash : " + hashBlock);
		System.out.println("Timestamp : " + dateToString(date));
		System.out.println("Nb transaction : " + nbTransaction);
		System.out.println("Liste transaction {");
		System.out.println(stringListeTx());
		System.out.println("}");
		System.out.println("Merkel tree root : " + merkel);
		System.out.println("Miner : " + user.getNom());
		System.out.println("Nonce : " + Nonce);
	}
	
	/**
	 * @return the nonce
	 */
	public int getNonce() {
		return Nonce;
	}
	
	/**
	 * @return the hashBlock
	 */
	public String getHashBlock() {
		return hashBlock;
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
	public Date getDate() {
	    return date;
	  }
	
	/**
	 * @return the date
	 */
	public static String dateToString(Date date) {
		DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM);
	    return shortDateFormat.format(date);
	  }

	/**
	 * @return the merkel
	 */
	public String getMerkel() {
		return merkel;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	
	
	
	
	
	
}	
	
	

