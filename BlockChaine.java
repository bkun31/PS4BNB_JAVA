package Projet;

public class BlockChaine {
	private Block chaine [] = new Block[100];
	
	/**
	 * @param 
	 */
	public BlockChaine() {
	}
	
	/**
	 * @return Block genesis avec une seul transaction
	 */
	private Block createGenesis() {
		String [] tab = new String[1];
		tab[0] = "genesis envoie 10 Bnb à genesis";
		Block genesis = new Block("00047930ee9b51c10e438ff3a2ad25c7eca5b890213b4e77d12012cc307017c0", tab, 1);
		return genesis;
	}
	
	public void boucleChaine(int longChaine) {
		chaine[0] = createGenesis();
		String hashPrecedent = hashBlock(chaine[0]); 
		System.out.println("Genesis : " + "nb Transaction : " + chaine[0].getNbTransaction() + " / Nonce : " + chaine[0].getNonce() + " / hash : " + chaine[0].getHashBlock());
		
		for (int i=1; i<longChaine; i++) {
			chaine[i] = new Block(hashPrecedent);
			hashPrecedent = hashBlock(chaine[i]);
			System.out.println("nb Transaction : " + chaine[i].getNbTransaction() + " / Nonce : " + chaine[i].getNonce() + " / hash : " + chaine[i].getHashBlock());
		}
	}
	
	public String hashBlock(Block block) {
		String HashP = block.getHashPrecedent();
		String Trans = block.getMerkel();
		String Date = block.getDate();
		int Nonce = 0;
		String hash = HashUtil.applySha256(HashP + Trans + Date + Nonce);
		while (!hash.substring(0, 3).equals("000")) {
			Nonce++;
			hash = HashUtil.applySha256(HashP + Trans + Date + Nonce);
		}
		block.setHashBlock(hash);
		block.setNonce(Nonce);
		return hash;
	}
	
}
