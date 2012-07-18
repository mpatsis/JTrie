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

package jtrie.dictionary;


import java.util.ArrayList;
import java.util.HashSet;

import jtrie.exceptions.InvalidCharacterException;
import jtrie.exceptions.InvalidNumberException;
import jtrie.structures.Trie;


/**
 * @author Karabatsis Rafael - Michael <mpatsis13@gmail.com>.
 *
 */
public class TrieDictionary implements Dictionary {

	private final Trie trie;
	private int totalWords;
	private HashSet<String> closestWords;

	
	/**
	 * 
	 */
	public TrieDictionary() {
		trie = new Trie();
		totalWords = 0;
	}

		
	
	/**
	 * 
	 * @param word
	 */
	public void insert( String word ) {
		trie.insert( Trie.capitalize( word ) );
		totalWords++;
	}
	
	
	/**
	 * 
	 * @param word
	 */
	public void delete( String word ) {
		trie.delete( word );
		totalWords--;
	}
		
		
	/**
	 * 
	 */
	public boolean contains( ArrayList<Byte> word ) {
		return trie.contains( word );
	}
		
		
	/**
	 * 
	 * @param word
	 * @return
	 */
	public boolean contains( String word ) {
		return trie.contains( word );
	}	
		
		
	/**
	 * 
	 * @return The size of the dictionary.
	 */
	public int size() {
		return totalWords;
	}
	
	
	private void computeClosestWords( String word ) 
				throws InvalidCharacterException, InvalidNumberException {
		
		word = Trie.capitalize( word );
		System.out.println( word );
		
		//	if( !word.matches( onlyGreekRegExp ) ) return word;
		
		ArrayList<Byte> byteWord = new ArrayList<Byte>();
		byteWord.add( (byte)0 );
		for( int i = 0; i < word.length(); i++ ) 
			byteWord.add( Trie.charToNumber( word.charAt(i) ) );
		
		if( contains( byteWord ) ) {
			System.out.println( "Vrethike mesa to idio to tag: word" );
		}
		else {
			trie.computeMinimumLevenshteinDistance( byteWord );
		}
		
		ArrayList< ArrayList<Byte> > closestWordsArrayList = trie.getClosestWords();
		
		closestWords = new HashSet<String>();
		for( ArrayList<Byte> closestWordArrayList : closestWordsArrayList ) {
			StringBuilder closestWord = new StringBuilder();
			for( byte b : closestWordArrayList ) 
				closestWord.append( Trie.numberToChar( b ) );
				closestWords.add( closestWord.toString() );
			}
				
		}
		
		
		public HashSet<String> getClosestWords( String word ) 
					throws InvalidCharacterException, InvalidNumberException {

			computeClosestWords( word );
			return closestWords;
		}

}
