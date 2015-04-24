/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author anonimus
 */
public class ifq 
{
    private String clave;
    private int claUni;
    private int cajas;

    public ifq() {
    }

    public ifq(String clave, int claUni, int cajas) {
        this.clave = clave;
        this.claUni = claUni;
        this.cajas = cajas;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getClaUni() {
        return claUni;
    }

    public void setClaUni(int claUni) {
        this.claUni = claUni;
    }

    public int getCajas() {
        return cajas;
    }

    public void setCajas(int cajas) {
        this.cajas = cajas;
    }
    
    
    
}
