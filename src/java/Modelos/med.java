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
public class med 
{
    private String clave;
    private String desc;
    private String estatus;
    private int tipMed;
    private double cost;
    private String pres;

    public med() {
    }

    public med(String clave, String desc, String estatus, int tipMed, double cost, String pres) {
        this.clave = clave;
        this.desc = desc;
        this.estatus = estatus;
        this.tipMed = tipMed;
        this.cost = cost;
        this.pres = pres;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getTipMed() {
        return tipMed;
    }

    public void setTipMed(int tipMed) {
        this.tipMed = tipMed;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }
    
    
    
}
