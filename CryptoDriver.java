package assignment3;

public class CryptoDriver {
	
	public static void main(String[] args) {
		System.out.println(CryptoManager.vigenereEncryption("JONATHAN ALAPATT","CMSC"));
		System.out.println(CryptoManager.caesarEncryption("JONATHAN ALAPATT",27));
		System.out.println(CryptoManager.playfairEncryption("JONATHAN ALAPATT","CMSC"));
		
		System.out.println(CryptoManager.vigenereDecryption(CryptoManager.vigenereEncryption("!@# JONATHAN","CMSC"),"CMSC"));
		System.out.println(CryptoManager.caesarDecryption(CryptoManager.caesarEncryption("!@# JONATHAN",3),3));
		System.out.println(CryptoManager.playfairDecryption(CryptoManager.playfairEncryption("!@# JONATHAN","CMSC"),"CMSC"));
	}
}
