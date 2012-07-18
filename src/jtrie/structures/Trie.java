/**
 * Copyright 2012 Karabatsis Rafael - Michael <mpatsis13@gmail.com>.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jtrie.structures;


import java.util.ArrayList;

import jtrie.exceptions.InvalidCharacterException;
import jtrie.exceptions.InvalidNumberException;


/**
 * @author Karabatsis Rafael - Michael <mpatsis13@gmail.com>.
 *
 */
public class Trie {

    private final TrieNode root;
    private int minLevDist, searchSize;
    private int distanceTreshold = 2;
    
    private ArrayList<Byte> currentPrefix, closestWord;
    private ArrayList< ArrayList<Byte> > closestWords;

    
    /**
     * 
     */
    public Trie() {
        root = new TrieNode( (byte) 0 );
        currentPrefix = new ArrayList<Byte>();
        closestWords = new ArrayList< ArrayList<Byte> >();
    }

    
    /**
     * 
     * @param word
     */
    public void insert( String word ) {

    	String upperCaseWord = capitalize( word );
        int length = word.length();
        TrieNode current = this.root;

        if ( length == 0 ) return;
       
        for ( int index = 0; index < length; index++ ) {

            byte letter;
			try {
				letter = charToNumber( upperCaseWord.charAt( index ) );
			} 
			catch (InvalidCharacterException e) {
				System.err.println( "Error: Insert operation of word: " 
						+ word + " could not be executed." );
				System.err.println( "An InvalidNumberException was thrown!" );
				return;
			}
			
            if( letter == 0 ) return;
            
            TrieNode child = current.getChild( letter );

            if (child != null) {
                current = child;
                if ( index == length - 1 ) {
            		current = new FullTrieNode( current );
            	}
            } 
            else {
            	if ( index == length - 1 ) {
            		current.getChildren().put( letter, new FullTrieNode( letter ) );
            	}
            	else {
            		current.getChildren().put( letter, new TrieNode( letter ) );
            		current = current.getChild( letter );
            	}
            }
            
        }
    }
    
    
    /**
     * 
     * @param word
     */
    public void delete( String word ) {

    	String upperCaseWord = capitalize( word );
        int length = word.length();
        TrieNode current = this.root;

        if ( length == 0 ) return;
       
        for ( int index = 0; index < length; index++ ) {

            byte letter;
			try {
				letter = charToNumber( upperCaseWord.charAt( index ) );
			} 
			catch ( InvalidCharacterException ice ) {
				System.err.println( "Error: Delete operation of word: " 
								+ word + " could not be executed." );
				System.err.println( "An InvalidCharacterException was thrown!" );
				return;
			}
			
            if( letter == 0 ) return;
            
            TrieNode child = current.getChild( letter );

            if (child != null) current = child;
            else return;
          
            
            if ( index == length - 1 ) {
                 if( current.getChildren().size() > 0 ) {
                	 current = new TrieNode( current );
                 }
                 else current = null;
            }
        }
    }
    
    
    /**
     * 
     */
    public boolean contains( String word ) {
    	int wordLength = word.length();
    	String UpperCaseWord = capitalize( word );
    	ArrayList<Byte> byteWord = new ArrayList<Byte>();
    	
    	byteWord.add( (byte) 0 );
    	for( int i = 0 ; i < wordLength; i++ ) {
    		try {
				byteWord.add( charToNumber( UpperCaseWord.charAt( i ) ) );
			} 
    		catch ( InvalidCharacterException ice ) {
    			System.err.println( "Error: Contains operation for word: " 
						+ word + " could not be executed." );
    			System.err.println( "An InvalidCharacterException was thrown!" );
    			return false;
			}
    	}
    	
    	return contains( byteWord );
    }
    
    
    /**
     * 
     * @param word
     * @return
     */
    public boolean contains( ArrayList<Byte> word ) {
    	int wordSize = word.size();
    	TrieNode node = root;
    	
    	
    	int i = ( word.get( 0 ) == 0 ) ? 1 : 0;
    	for ( ; i < wordSize; i++ ) {
        	byte letter = word.get( i );
        	TrieNode nextNode = node.getChild( letter );
        	if( nextNode == null ) return false;
        	node = nextNode;
        }
    	
    	return node.isWord();
    }
    
    
    /**
     * Computes the minimum Levenshtein Distance between the given word (represented as an arrayList of bytes ) 
     * and all the words stored in the Trie. 
     * 
     * @param ArrayList<Character> word - The characters of an input word as an ArrayList<Byte>  representation
     * @return int - The minimum Levenshtein Distance between the given word and all the words stored in the Trie.
     */
    public int computeMinimumLevenshteinDistance( ArrayList<Byte> word ) {

        minLevDist = Integer.MAX_VALUE;
        closestWords = new ArrayList<ArrayList<Byte>>();
        

        int wordSize = word.size();
        searchSize = wordSize + 1;
        int[] currentRow = new int[searchSize];

        for ( int i = 0; i <= wordSize; i++ ) {
            currentRow[i] = i;
        }
        currentRow[0] = -1;

        for ( int i = 0; i < wordSize; i++ ) {
        	byte letter = word.get( i );
        	
        	if( root.getChild( letter ) != null ) {
        		currentPrefix = new ArrayList<Byte>();
        	//	currentPrefix.add( letter );
        		traverseTrie( root, letter, word, currentRow );
        	//	currentPrefix.remove( 0 );
        	}
        }
        
        return minLevDist;
    }

    
    /**
     * Recursive helper function. Traverses theTrie in search of the minimum Levenshtein Distance.
     * 
     * @param TrieNode node - the current TrieNode
     * @param byte letter - the current character of the current word we're working with
     * @param ArrayList<Byte> word - an ArrayList of bytes representation of the current word
     * @param int[] previousRow - a row in the Levenshtein Distance matrix
     */
    private void traverseTrie( TrieNode node, byte letter, ArrayList<Byte> word, int[] previousRow ) {

        int[] currentRow = new int[searchSize];
        currentRow[0] = previousRow[0] + 1;

        int minimumElement = currentRow[0];
        int insertCost, deleteCost, replaceCost;

        for (int i = 1; i < searchSize; i++) {
            insertCost = currentRow[i - 1] + 1;
            deleteCost = previousRow[i] + 1;
            replaceCost = word.get(i - 1) == letter ? previousRow[i - 1] : previousRow[i - 1] + 1;

            currentRow[i] = minimum( insertCost, deleteCost, replaceCost );

            if ( currentRow[i] < minimumElement ) {
                minimumElement = currentRow[i];
            }
        }

        /* Checks if the distance is less than the minimum distance!!! */
        if ( currentRow[searchSize - 1] < minLevDist && node.isWord() ) {
            minLevDist = currentRow[searchSize - 1];
            closestWord = (ArrayList<Byte>) currentPrefix.clone();
        }
        
        /* Checks if the distance is less than the treshold */
        if( currentRow[searchSize - 1] < distanceTreshold  && node.isWord() ) {
        	closestWords.add( (ArrayList<Byte>) currentPrefix.clone() );
        }
        

  //      if ( minimumElement < minLevDist ) {

            for ( Byte b : node.getChildren().keySet()) {
            	currentPrefix.add( b );
                traverseTrie( node.getChildren().get( b ), b, word, currentRow );
                currentPrefix.remove( currentPrefix.size() -1 );
            }
    //    }
    }


    /**
     * Returns the minimum of the three numbers given as arguments.
     * @param insertCost
     * @param deleteCost
     * @param replaceCost
     * @return
     */
	private int minimum( int insertCost, int deleteCost, int replaceCost ) {
		int min = insertCost;
		if( deleteCost < min ) min = deleteCost;
		if( replaceCost < min ) min = replaceCost;
		
		return min;
	}
	
	
	/**
	 * Method which transforms the given letter to  number. If input is an invalid character throws InvalidCharacterException.
	 * @param letter The char to transform to Number
	 * @return The number representation of parameter character
	 * @throws InvalidLetterException if parameter character is not valid( if is not a valid char ).
	 */
	public static byte charToNumber( char character ) throws InvalidCharacterException {
		if ( character > 930 ) return (byte) ( character - 913 );
		else if ( character > 912 ) return (byte) ( character - 912 );
		else if ( character >= 'A' && character <= 'Z' ) return (byte) ( character - 64 + 24 );
		else if ( character >= '0' && character <= '9' ) return (byte)  ( character - 47 + 50 );
		else throw new InvalidCharacterException();
	}
		
		
	/**
	 * 
	 * Method which transforms the given number to a char. If input is an invalid character throws InvalidNumberException.
	 * @param letter The char to transform to Number
	 * @return The char representation of parameter number
	 * @throws InvalidLetterException if parameter letter is not valid( if is not a Greek Capital letter ).
	 */
	public static char numberToChar( byte number ) throws InvalidNumberException  {
		if( number <= 17  ) return (char) (number + 912);
		else if( number < 25 ) return (char) (number + 913);
		else if( number < 51 ) return (char) (number - 24 + 64 );
		else return (char) (number - 50 + 47);
	}
	
	
	/**
	 * 
	 * @param word
	 * @return
	 */
	public static String capitalize( String word ) {
		StringBuilder upperCase = new StringBuilder();
		for( int i = 0; i < word.length(); i++ ) {
			upperCase.append( toUpperCase( word.charAt( i ) ) );
		}
		
		return upperCase.toString();
	}
	
	
	/**
	 * 
	 * @param ch
	 * @return
	 */
	public static char toUpperCase( char ch ) {
		switch( ch ) {
	 		case 'ά' :
	 		case 'Ά' :
	 			return 'Α';
	 			
	 		case 'έ' : 
	 		case 'Έ' :
	 			return 'Ε';
	 		
	 		case 'ί' :
	 		case 'ϊ' : 
	 		case 'ΐ' : 
	 		case 'Ί' :
	 		case 'Ϊ' :
	 			return 'Ι';
	 		
	 		case 'ή' : 
	 		case 'Ή' :
	 			return 'Η';
	 		
	 		case 'ό' : 
	 		case 'Ό' :
	 			return 'Ο';
	 		
	 		case 'ώ' : 
	 		case 'Ώ' :
	 			return 'Ω';
	 		
	 		case 'ύ' : 
	 		case 'ϋ' : 
	 		case 'ΰ' : 
	 		case 'Ύ' :
	 		case 'Ϋ' :
	 			return 'Υ';
	 		
	 		default : return Character.toUpperCase( ch );
		}
	}

	


	/**
	 * @return the root
	 */
	public TrieNode getRoot() {
		return root;
	}


	/**
	 * @return the minLevDist
	 */
	public int getMinLevDist() {
		return minLevDist;
	}


	/**
	 * @return the currentPrefix
	 */
	public ArrayList<Byte> getCurrentPrefix() {
		return currentPrefix;
	}


	/**
	 * @return the closestWords
	 */
	public ArrayList< ArrayList<Byte> > getClosestWords() {
		return closestWords;
	}

    
}


