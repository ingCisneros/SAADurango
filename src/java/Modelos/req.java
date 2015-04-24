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
public class req 
{
    private int claUni;
    private String claveMed;
    private int cajas;
    private int status;

    public req() {
    }

    public req(int claUni, String claveMed, int cajas, int status) {
        this.claUni = claUni;
        this.claveMed = claveMed;
        this.cajas = cajas;
        this.status = status;
    }

    public int getClaUni() {
        return claUni;
    }

    public void setClaUni(int claUni) {
        this.claUni = claUni;
    }

    public String getClaveMed() {
        return claveMed;
    }

    public void setClaveMed(String claveMed) {
        this.claveMed = claveMed;
    }

    public int getCajas() {
        return cajas;
    }

    public void setCajas(int cajas) {
        this.cajas = cajas;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
    
}
