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

import java.util.HashMap;
import java.util.Map;


/**
 * @author Karabatsis Rafael - Michael <mpatsis13@gmail.com>.
 *
 */
public class TrieNode {

    public static final int ALPHABET = 26;

    private final byte letter;
    private Map<Byte, TrieNode> children;

    
    /**
     * Constructor. Creates a new Trie node.
     * @param letter
     */
    public TrieNode( byte letter ) {
        this.letter = letter;
        children = new HashMap<Byte, TrieNode>( ALPHABET );
    }
    
    
    /**
     * Constructor. Creates a new Trie node.
     * @param letter
     */
    public TrieNode( TrieNode node ) {
        this.letter = node.getLetter();
        this.children = node.getChildren();
    }

    
    /**
     * 
     * @param letter
     * @return
     */
    public TrieNode getChild( byte letter ) {

        if ( children != null ) {
            if ( children.containsKey( letter ) ) {
                return children.get( letter ); 
            }
        }
        return null;
    }

    
	/**
	 * @return the alphabet
	 */
	public static int getAlphabet() {
		return ALPHABET;
	}

	
	/**
	 * @return the letter
	 */
	public byte getLetter() {
		return letter;
	}

	
	/**
	 * Returns if this TrieNode contains a word.
	 * @return false.
	 */
	public boolean isWord() {
		return false;
	}

	
	/**
	 * @return the children
	 */
	public Map<Byte, TrieNode> getChildren() {
		return children;
	}
    
    
}
