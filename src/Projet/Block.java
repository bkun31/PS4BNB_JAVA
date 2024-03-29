package Projet;

/**
 * @author Bastien Canto bastien.canto@univ-tlse3.fr
 * @author Omar Kired omar.kired@univ-tlse3.fr
 */

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
	 * @param String hashPrecedent
	 * @param String[] listeTransaction
	 * @param nbTransaction : nombre de transaction
	 * @param numBlock Numero du block dans la chain
	 * @param User mineur du block
	 */
	public Block(String hashPrecedent, String[] listeTransaction, int nbTransaction, int numBlock, User user) {
		this.hashPrecedent = hashPrecedent;
		this.listeTransaction = listeTransaction;
		this.nbTransaction = nbTransaction;
		this.numBlock = numBlock;
		this.user = user;
		this.Nonce = 0;
		this.date = new Date();
		// faire le merkel des transaction
		this.merkel = merkel();
	}

	/**
	 * Hash le block en incrementant le nonce jusqu'a que le block commence par X fois "0"
	 * @param BLock a hash
	 * @return String Hash du block
	 */
	public String hashBlock(int difficulte) {
		String dif = "";
		for (int i=0; i<difficulte; i++) {
			dif = dif +"0";
		}
		hashBlock = HashUtil.applySha256(hashPrecedent + merkel + dateToString(date) + Nonce + numBlock + stringListeTx() + user.getNom()); //Concatenation des info + hash
		while (!hashBlock.substring(0, difficulte).equals(dif)) { //Verifie que les 4 premier caracter du hash soit "0" x difficulte
			Nonce++;
			hashBlock = HashUtil.applySha256(hashPrecedent + merkel + dateToString(date) + Nonce + numBlock + stringListeTx() + user.getNom());
		}
		return hashBlock;
	}
	
	/**
	 * Hash le block avec le nonce
	 * @return String Hash du block
	 */
	public String hashBlockNonce() {
		return HashUtil.applySha256(hashPrecedent + merkel + dateToString(date) + Nonce + numBlock + stringListeTx() + user.getNom());
	}
	
	/**
	 * lance merkel hash avec les info du block
	 * @see merkelhash()
	 * @return String merkel
	 */
	public String merkel() {
		if (nbTransaction == 1) {
			return HashUtil.applySha256(listeTransaction[0]);
		}
		return merkelHash(listeTransaction, nbTransaction);
	}
	
	/**
	 * @return String merkel
	 */
	public String merkelHash(String tabTx[], int nbTx) {
		int i=0;
		int nbTxHash=0;
		String tab[] = Arrays.copyOf(tabTx, nbTx); //crée une copie de tabTx[] de longeur nbTx
		String [] tabHash = tab;
		if (nbTx == 1) { //condition de fin de recursion 
			return tab[0];
		}
		while (i<nbTx-1) {
			tabHash[nbTxHash]= HashUtil.applySha256(tab[i] + tab[i+1]); //concatene et hash 2 transaction 
			nbTxHash++;
			i+=2;
		}
		if (nbTx % 2 != 0) {
			tabHash[nbTxHash]= HashUtil.applySha256(tab[i] + tab[i]); // Si nbTx impaire concatene et hash 2 fois la derniere transaction
			nbTxHash++;
		}
		return merkelHash(tabHash, nbTxHash); //appel récursif de merkelhash()
	}
	
	/**
	 * @return String concatenation de toute les transaction
	 */
	public String stringListeTx() {
		String liste = "";
		for (int i=0; i<nbTransaction;i++) {
			liste = liste + listeTransaction[i] + "\n";
		}
		return liste;
	}
	
	/**
	 * Print toutes les info du block
	 */
	public void printBlock() {
		System.out.println("Block numero (Index) : " + numBlock);
		System.out.println("Previous hash : " + hashPrecedent);
		System.out.println("Current hash : " + hashBlock);
		System.out.println("Timestamp : " + dateToString(date));
		System.out.println("Nb transaction : " + nbTransaction);
		System.out.println("Liste transaction {");
		System.out.println(stringListeTx() + "}");
		System.out.println("Merkel tree root : " + merkel);
		System.out.println("Miner : " + user.getNom());
		System.out.println("Nonce : " + Nonce);
	}
	
	/**
	 * @return true si le merkel est valide
	 */
	public boolean verifMerkel() {
		if (this.merkel.equals(merkel())) {
			return true;
		} else {
			return false;
		}
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
	
	/**
	 * @return the numBlock
	 */
	public int getNumBlock() {
		return numBlock;
	}
	
}	
	
	

