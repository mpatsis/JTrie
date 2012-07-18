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

package jtrie.app;

import jtrie.dictionary.Dictionary;
import jtrie.dictionary.TrieDictionary;

/**
 * @author Karabatsis Rafael - Michael <mpatsis13@gmail.com>.
 *
 */
public class TrieDictionaryApp {

	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		Dictionary dictionary = new TrieDictionary();
		
		dictionary.insert( "Τεστ" );
		dictionary.insert( "test" );
		
		System.out.println( "Τεστ:" + dictionary.contains( "Τεστ" ) );
		System.out.println( "test:" + dictionary.contains( "test" ) );

		System.out.println( "τεστ:" + dictionary.contains( "τεστ" ) );
		System.out.println( "Test:" + dictionary.contains( "Test" ) );
	}

}
