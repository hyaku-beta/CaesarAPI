import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



/**
 * This is a simple console API for implementing Caesar Cipher.
 */
/**
 * Main functionalities:
 * 1.Input original message and key, output encrypted text;
 * 2.Input encrypted text and key, output original message;
 * 3.Input encrypted text, output all possible original messages.
 */
/**
 * Assumption:
 * 1.No symbols and blanks will be encrypted/decrypted.
 * 2.Keys are integers.
 */
/**
 * Usage Instruction: 
 * 1.Open terminal console, input command "cd [path of the CaesarAPI.java file]", press Enter;
 * 2.Input command "javac CaesarAPI.java" and press Enter to compile the java file;
 * 3.Input command "java CaesarAPI [function] [text file path] [key(optional)]" and press Enter to run the API;
 */

/**
 * @author Yang Bai(hyaku-beta)
 *
 */

public class CaesarAPI {
	
	public static String USAGE = "Usage:\n" 
			+"\n"
			+ "java CaesarAPI [function] [text file path] [key(optional)]\n"
			+"\n"
			+ "-[function]: encrypt|decrypt|cryptanalyse\n"
			+ "-[text file path]: the path of txt file containing the input text\n"
			+ "-[key(optional]: single integer number (no need for cryptanalyse function)\n";
	public static String FILE_ERROR = "Invalid file! Please input the path of a TXT file.";
	public static String KEY_ERROR = "Invalid key! Please input an integer number as the key.";
	public static String WARNING_CRYPTANALYSE = "WARNING: Cryptanalyse function does not need a key parameter.\n"
			+ "Result above is got from analysis discarding the input key value.\n";
	
	/**
	 * Encrypts original message using input key.
	 * @param plaintext, key
	 * @return ciphertext
	 */
	public static String encrypt(String plaintext, int key) {
		//Create a string for storing encrypted message letter by letter
		String ciphertext = "";
	
		//Handle the plaintext letter by letter
		for(int i = 0; i < plaintext.length(); i++) {
			//Value in current index
			char letter = plaintext.charAt(i);
			//Check if the value in current index is a letter instead of punctuation or blank
			if(Character.isLetter(letter)) {
				//Get encrypted letter by shifting the original letter by key value in ASCII table
				char encryptedLetter = (char) (letter + key);
				
				//Validate encrypted letter
				encryptedLetter = validateEncryptedLetter(letter, encryptedLetter);
				
				//Add the correct encrypted letter to the output message
				ciphertext += encryptedLetter;
			} else {
				//If current position is not alphabet, add the value directly to the encrypted message
				ciphertext += letter;
			}
		}
		return ciphertext;
	}
	
	/**
	 * Decrypts encrypted message using input key.
	 * @param ciphertext, key
	 * @return plaintext
	 */
	public static String decrypt(String ciphertext, int key) {
		//Create a string for storing decrypted message letter by letter
		String plaintext = "";
	
		//Handle the ciphertext letter by letter
		for(int i = 0; i < ciphertext.length(); i++) {
			//Value in current index
			char letter = ciphertext.charAt(i);
			//Check if the value in current index is a letter instead of punctuation or blank
			if(Character.isLetter(letter)) {
				//Get encrypted letter by shifting the original letter by key value in ASCII table
				char decryptedLetter = (char) (letter - key);
				
				//Validate encrypted letter
				decryptedLetter = validateDecryptedLetter(letter, decryptedLetter);
				
				//Add the correct encrypted letter to the output message
				plaintext += decryptedLetter;
			} else {
				//If current position is not alphabet, add the value directly to the encrypted message
				plaintext += letter;
			}
		}
		return plaintext;
	}
	
	/**
	 * Analyzes given ciphertext and output 26 possible plaintext as result.
	 * @param ciphertext
	 * @output all possible plaintext
	 */
	public static void cryptanalyse(String ciphertext) {
		//Separator
		System.out.println("------All possible plaintext displayed below------");
		
		String possiblePlaintext = "";
		//Decrypt the encrypted messaged using 26 validate keys to get all possible original message(assume 0 is acceptable)
		for(int key = 0; key < 25; key++) {
			possiblePlaintext = decrypt(ciphertext, key);
			//Print out current decrypted message
			System.out.println("key " + key + ": " + possiblePlaintext);
		}
		//Separator
		System.out.println("------All possible plaintext displayed above------");
	}
	
	/**
	 * Validates input key, which cannot be negative and larger than 25.
	 * @param initial key
	 * @return validated key
	 */
	public static int validateKey(int key) {
		//if key is negative or larger than 25, return the valid key by modulo the original key with 26
		if (key < 0 || key > 25) {
			key = key % 26;
		}
		return key;
	}
	
	/**
	 * Checks if the shifted letter out of range of alphabets in ASCII table,
	 * if so then shift the letter back to the position after the letter right before 'a' or 'A' 
	 * in corresponding case by the exceeded value.
	 * @param initial encrypted letter
	 * @return validated encrypted letter
	 */
	public static char validateEncryptedLetter(char letter, char encryptedLetter) {
		//Lower case range
		if(Character.isLowerCase(letter)) {
			if(encryptedLetter > 'z' ) {
				encryptedLetter =  (char)(('a' - 1) + (encryptedLetter - 'z'));
			} 
		//Upper case range
		} else if(Character.isUpperCase(letter)) {
			if(encryptedLetter > 'Z' ) {
				encryptedLetter =  (char)(('A' - 1) + (encryptedLetter - 'Z'));
			} 
		}
		return encryptedLetter;
	}
	
	/**
	 * Checks if the shifted letter out of range of alphabets in ASCII table,
	 * if so then shift the letter back to the position before the letter right after 'z' or 'Z' 
	 * in corresponding case by the exceeded value.
	 * @param initial decrypted letter
	 * @return validated decrypted letter
	 */
	public static char validateDecryptedLetter(char letter, char decryptedLetter) {
		//Lower case range
		if(Character.isLowerCase(letter)) {
			if(decryptedLetter < 'a' ) {
				decryptedLetter =  (char)(('z' + 1) - ('a' - decryptedLetter));
			} 
		//Upper case range
		} else if(Character.isUpperCase(letter)) {
			if(decryptedLetter < 'A' ) {
				decryptedLetter =  (char)(('Z' + 1) - ('A' - decryptedLetter));
			} 
		}
		return decryptedLetter;
	}
	
	/**
	 * Handles input file format.
	 * @param file name
	 * @return check result
	 */
	public static Boolean checkFile(String fileName) {
		
		//If the length of the file is equal to or less than 4 then return false 
		if(fileName.length() <= 4) {
			return false;
		//Else if the last four characters of the file name is not ".txt" then return false
		} else if (!(fileName.substring(fileName.length() - 4).equals(".txt"))) {
			return false;
		} else {
			return true;
		}
		
	}
	
	/**
	 * Reads input text file.
	 * @param file name
	 * @return content of the file
	 */
	public static String readFile(String file) {
		//Create a string to store file content
		String content = "";
		//Create a string to store one line at a time
		String line = "";
		
		try {
			//Read text file into a fileReader
			FileReader fileReader = new FileReader(file);
			//Wrap fileReader in a bufferedReader
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			//Read the file line by line and add them into the content string
			while((line = bufferedReader.readLine()) != null) {
				content += line + "\n";
			}
			
			//Close file readers
			bufferedReader.close();
			fileReader.close();
		//Handle the caught exceptions
		} catch(FileNotFoundException ex) {
			System.err.println("Unable to open file '" + file + "'");	
		} catch(IOException ex) {
			System.err.println("Error reading file '" + file + "'");
		}
		
		return content;
	}

	/**
	 * Checks if the input key is integer number.
	 * @param input key
	 * @return check result
	 */
	public static Boolean isInteger(String key) {
		if (key == null) return false;
		if (key.length() == 0) return false;
		
		int i = 0;
		//Check the possibility of negative number
		if (key.charAt(0) == '-') {
			if (key.length() == 1) return false;
			i = 1;
		}
		for (; i < key.length(); i++) {
			if (key.charAt(i) < '0' || key.charAt(i) > '9') return false;
		}
		return true;
	}
	
	/**
	 * Executes corresponding function using input function name after checking all exceptions.
	 * @param input function
	 */
	public static void executeFunction(String function, String file, String key) {
		//A string variable for storing checked file name
		String checkedFile;
		//A string variable for storing content read from the checked file
		String input;
		//An integer number for storing checked key value
		int checkedKey;
		
		//Check if file format is correct
		if(!(checkFile(file))) {
			System.err.println(FILE_ERROR);
		} else {
			//Read file and get the return content of the file
			checkedFile = file;
			input = readFile(checkedFile);
			
			//Check if the input key can be an integer type
			if(!isInteger(key)) {
				System.err.println(KEY_ERROR);
			} else {
				try {
					//Parse the input key string into integer type
					checkedKey = Integer.parseInt(key);
					//Execute relative function as commanded
					if (function.equals("decrypt")) {
						System.out.println(decrypt(input, checkedKey));
					} else {
						System.out.println(encrypt(input, checkedKey));
					}
				//Catch the parsing exception
				} catch (NumberFormatException ex) {
					System.err.println("Convert input key failed.");
				}
			}
		}
	}
	/**
	 * Main method for handling inputs and outputs.
	 * @param args = function selection | text | key(optional)
	 */
	public static void main(String[] args) {
		//A string variable for storing checked file name
		String file;
		//A string variable for storing return content read from the file
		String input;
		
		//If the argument length is less than 2 or more than 3 then announce the usage instruction again
		if(args.length < 2 || args.length > 3) {
			System.err.println(USAGE);
		} 
		//If the argument length is 2 then check the first argument
		else if(args.length == 2) {
			//If the first argument is not cryptanalyse then announce the usage instruction again
			if(!(args[0].equals("cryptanalyse"))) {
				System.err.println(USAGE);
			} else {
				//Check if file format is txt file
				if(!(checkFile(args[1]))) {
					System.err.println(FILE_ERROR);
				//All checked, read the file, execute the cryptanalyse function
				} else {
					file = args[1];
					input = readFile(file);
					cryptanalyse(input);
				}
			}
		}
		//If the argument length is 3 then check if the first argument is encrypt or decrypt
		else if(args.length == 3) {
			//If the first argument is not encrypt or decrypt, check if it is cryptanalyse
			if(!((args[0].equals("encrypt")) || (args[0].equals("decrypt")))) {
				//If the first argument is crytanalyse then check the file format
				if(args[0].equals("cryptanalyse")) {
					//Check file format
					if(!(checkFile(args[1]))) {
						System.err.println(FILE_ERROR);
					} else {
						//Read file and execute cryptanalyse function with a warning announcement
						file = args[1];
						input = readFile(file);
						cryptanalyse(input);
						System.err.println(WARNING_CRYPTANALYSE);
					}
				//If the first argument is not crytanalyse then announce the usage again
				} else {
					System.err.println(USAGE);
				}
			//If the first argument is encrypt or decrypt, execute the corresponding function
			} else {
				executeFunction(args[0], args[1], args[2]);
			}
		}

	}

}
