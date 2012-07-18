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

package jtrie.exceptions;

/**
 * Class for an exception if a letter is invalid.
 * @author Karabatsis Rafael - Michael <mpatsis13@gmail.com>.
 *
 */
public class InvalidNumberException extends Exception {
	
	/**
	 * Method which creates a new InvalidNumberException with the default error message for this Exception.
	 */
	public InvalidNumberException() {
		this( "Error: Invalid Number. Cannot convert this number to a character." );
	}
	
	
	/**
	 * Method which creates a new InvalidNumberException with error message the String that is given as parameter.
	 * @param detailMessage A String representation of this Exception error message.
	 */
	public InvalidNumberException( String detailMessage ) {
		super( detailMessage );
	}

}
