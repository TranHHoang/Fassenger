/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.exception;

/**
 *
 * @author TranHoang
 */
public class InternalException extends FassengerException {

    public InternalException(String message) {
        super(message, 500);
    }    
}
