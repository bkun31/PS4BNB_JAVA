package Projet;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;
import java.util.ArrayList;

public class BlockChaine {
	private ArrayList<Block> chaine = new ArrayList<Block>();
	private int difficulte;
	private int nbUser;
	private int numBlock;
	private int nbBlockDiv;
	private long recompence;
	private Queue<String> queue = new LinkedList<>();
	private User listeUser [] = new User[100];
	private String hashPrecedent;
	
	
	
	/**
	 * @param difficulte
	 * @param nbUser
	 * @param nbBlockDiv
	 */
	public BlockChaine(int difficulte, int nbUser, int nbBlockDiv, long recompense) {
		this.difficulte = difficulte;
		this.nbUser = nbUser;
		this.nbBlockDiv = nbBlockDiv;
		this.recompence = bnbToSato(recompense);
		this.hashPrecedent = "0000000000000000000000000000000";
		this.numBlock = 0;
		String[] tab = {"Alex","Damien","Paul","Pierre","Lisa","Chloé","Astèrix", "Obélix","Thomas","Clement","Steven","Bilel","Cédric","Omar","Bastien"};
		if (nbUser >= tab.length) {
			System.out.println("Erreur pas assez de prenom");
		}
		else {
			for (int i=0; i<nbUser; i++) {
				listeUser[i] = new User(tab[i]);
			}
		}
	}

	/**
	 * @return Block genesis avec une seul transaction
	 */
	private Block createGenesis() {
		String [] tab = new String[1]; // Crée un tableau de 1 transaction
		tab[0] = "Coinbase envoie 50 Bnb à creator";
		User user = new User("Creator");
		Block genesis = new Block(hashPrecedent, tab, 1, 0, user);
		return genesis;
	}
	
	/**
	 * Genere un tableau de block et print des information 
	 * @param int nombre de block dans la chaine
	 */
	public void boucleChaine(int blockMax) {
		System.out.println("Difficulty = " + difficulte);
		System.out.println("Initial reward in satoBnb = " + recompence);
		// --------------- Genesis ----------------
		
		System.out.println("----------------- Genesis -----------------");
		Block block = createGenesis(); //init le block genesis
		hashPrecedent = block.hashBlock(difficulte);  //hash le genesis
		chaine.add(block); //met le genesis en position 0 dans la blockchaine
		numBlock++;
		System.out.println("Block Mined!!! : " + block.getHashBlock() + " n° : " + numBlock + " Nonce = " + block.getNonce());
		
		// --------------- Helico ----------------
		
		System.out.println("----------------- Hélicoptère -----------------");
		Random random = new Random();
		//Helico pour tout les user
		for (int i = 0; i<nbUser; i++) {
			// Crée un tableau de 1 transaction
			String [] tab = new String[1]; 
			tab[0] = "Coinbase envoie 50 Bnb à " + listeUser[i].getNom();
			//creation et hash block
			block = new Block(hashPrecedent, tab, 1, numBlock, listeUser[random.nextInt(nbUser)]);
			hashPrecedent = block.hashBlock(difficulte);
			chaine.add(block);
			numBlock++;
			System.out.println("Block Mined!!! : " + block.getHashBlock() + " n° : " + numBlock + " Nonce = " + block.getNonce());
		}
		
		// --------------- Inflation ----------------
		
		System.out.println("----------------- Marché -----------------");
		while(numBlock < blockMax) {
			//div recompence 
			if (recompence != 0) {
				if(numBlock % nbBlockDiv == 0) {
					System.out.println("Reward = " + recompence + " Money supply = " + recompence/2 + " (" + satoToBnb(recompence/2)+" Bnb)");
					recompence/=2; 
				}
			}
			//creation de rng transaction dans la queue
			for (int j=0; j<random.nextInt(9)+1;j++) {
				queue.add(Block.generateTransaction());
			}
			//minage
			 minage(listeUser[random.nextInt(nbUser)]); //Met un utilisateur aleatoire
		}
		// --------------- Print all block ----------------
		System.out.println("----------------- Liste des block -----------------");
		for (int i=numBlock-1; i>=0 ;i--) {
			chaine.get(i).printBlock();
			System.out.println("-------------------");
		}
	}
	
	
	public void minage(User user) {
		String tabTx[] = new String [10];
		Random random = new Random();
		int nbTx = random.nextInt(9)+1;
		int i;
		
		//Met dans tabTx les transaction de la queue
		for (i=0; i<nbTx;i++) {
			tabTx[i]=queue.poll(); 
			if (tabTx[i] == null) { //Si la queue est vide : reduire le nombre de transaction
				nbTx=i-1;
				break;
			}
		}
		//Si en phase d'inflation ajouter a recompence
		if (recompence != 0) {  
			tabTx[i] = "Coinbase envoie 50 Bnb à " + user.getNom();
			nbTx++;
		}
		//creation et hash block
		Block block = new Block(hashPrecedent, tabTx, nbTx, numBlock, user);	
		hashPrecedent = block.hashBlock(difficulte);
		chaine.add(block);
		numBlock++;
		System.out.println("Block Mined!!! : " + block.getHashBlock() + " n° : " + numBlock + " Nonce = " + block.getNonce());
	}
	
	public long bnbToSato(long bnb) {
		return bnb*100000000;
	}
	
	public double satoToBnb(long sato) {
		return (double)sato/100000000;
	}
	
}
