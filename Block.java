package projet;
import  java.util.Random;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
public class Block {
	private static final String Transaction = null;
	static String hashPrecedent="0000560ece167038a904f5e3502fb8f6407dbf48fe6c9f16a164ab4ff8347bc1";
	static String listeTransaction[];
	static int nbTransaction=10;
	static int Nonce;
	static String HashBlock;
	
	public Block(String hashPrecedent, String[] listeTransaction, int nbTransaction, int nonce, LocalTime date,
			String hashBlock) {
		super();
		this.hashPrecedent = hashPrecedent;
		this.listeTransaction = listeTransaction;
		this.nbTransaction = nbTransaction;
		Nonce = nonce;
		HashBlock = hashBlock;
	}
	public static String Date() {
	    SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    Date date = new Date();
	    String date2 = s.format(date);
	    return date2;
	  }
	public  static  String  generateTransaction () {
	String [] tab = { " Alex " , " Damien " , " Paul " , " Pierre " , " Lisa " , " Chloé " , " Astèrix " , " Obélix " , " Thomas " , " Clement " , " Steven " , " Bilel " ," Cédric " ," Omar " , " Bastien " };
	int tablength = tab.length;
	Random r = new Random();
	int n = r.nextInt(tablength);
	int n1 = r.nextInt(tablength);
	while(n1==n) 
	n1=r.nextInt(tablength);
	String name1 = tab [n];
	String name2 = tab [n1];
	int  Bnb  = r.nextInt( 1000 );
	String Transaction= name1 + "envoie " + Bnb +" Bnb à"+ name2;
	String liste_transaction="";
	for (int j=0;j<nbTransaction;j++) {
		liste_transaction=liste_transaction+Transaction;
	}
	return liste_transaction;
	}
	public static String Hachage() throws NoSuchAlgorithmException {
		String HachEnCours = Date()+hashPrecedent+Nonce+generateTransaction();
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(HachEnCours.getBytes());
	    byte[] tab1 = md.digest();
	    //convertir le tableau de bits en une format hexadécimal
	    StringBuffer sb = new StringBuffer();
	    String tab2[] = new String[tab1.length];
	    for (int i = 0; i < tab1.length; i++) {
	    	sb.append(Integer.toString((tab1[i] & 0xff) + 0x100, 16).substring(1));
	    	tab2[i]=sb.toString();
	    }
	    System.out.println(tab2);
	    int k=0;
	    String hash = "";
	    while(tab2[k]!="0" && tab2[k+1]!="0" && tab2[k+2]!="0" && tab2[k+3]!="0") {
	    	Nonce++;
		    md.update(HachEnCours.getBytes());
		    tab1 = md.digest();
		    //convertir le tableau de bits en une format hexadécimal
		    sb = new StringBuffer();
		    tab2 = new String[tab1.length];
		    for (int i = 0; i < tab1.length; i++) {
		    	sb.append(Integer.toString((tab1[i] & 0xff) + 0x100, 16).substring(1));
		    	tab2[i]=sb.toString();
	    }
	    for (int j=0;j<tab1.length;j++) {
			hash=hash+tab2[j];
		}
	    }
		return hash;
	}
	public static String afficher() throws NoSuchAlgorithmException {
		return Hachage()+" Block n° : 1" + "   " + " Nonce = " +Nonce;
	}
	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.println("-------------------------------------Hélicpter-------------------------------------");
		}
}

