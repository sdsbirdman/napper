package com.hs.model;

import javax.ws.rs.WebApplicationException;

/**
 * Created by gte619n on 1/24/16.
 */
public class ApplicationException extends WebApplicationException {
    /*--------------------------------------------
    |             C O N S T A N T S             |
	============================================*/

	/*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
	============================================*/

	/*--------------------------------------------
    |         C O N S T R U C T O R S           |
	============================================*/

    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }


    /*--------------------------------------------
	|   P U B L I C    A P I    M E T H O D S   |
	============================================*/

	/*--------------------------------------------
	|    N O N - P U B L I C    M E T H O D S   |
	============================================*/

	/*--------------------------------------------
	|  A C C E S S O R S / M O D I F I E R S    |
	============================================*/

	/*--------------------------------------------
	|       I N L I N E    C L A S S E S        |
	============================================*/
}
