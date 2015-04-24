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
public class invUni
{
    private int claUNi;
    private String claMed;
    private int cajas;
    private int pz;
    private int pres;
    private int status;

    public invUni() {
    }
    
    

    public invUni(int claUNi, String claMed, int cajas, int pz, int pres, int status) {
        this.claUNi = claUNi;
        this.claMed = claMed;
        this.cajas = cajas;
        this.pz = pz;
        this.pres = pres;
        this.status = status;
    }

    public int getClaUNi() {
        return claUNi;
    }

    public void setClaUNi(int claUNi) {
        this.claUNi = claUNi;
    }

    public String getClaMed() {
        return claMed;
    }

    public void setClaMed(String claMed) {
        this.claMed = claMed;
    }

    public int getCajas() {
        return cajas;
    }

    public void setCajas(int cajas) {
        this.cajas = cajas;
    }

    public int getPz() {
        return pz;
    }

    public void setPz(int pz) {
        this.pz = pz;
    }

    public int getPres() {
        return pres;
    }

    public void setPres(int pres) {
        this.pres = pres;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
}
