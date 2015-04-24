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
public class cons 
{
    private int idFac;
    private int ClaCli;
    private int idLot;
    private int cajas;
    private String date;
    private int stats;
    private int solicitado;
    private String nomCli;

    public cons() {
    }

    public cons(int idFac, int ClaCli, int idLot, int cajas, String date, int stats, int solicitado,String nomCli) {
        this.idFac = idFac;
        this.ClaCli = ClaCli;
        this.idLot = idLot;
        this.cajas = cajas;
        this.date = date;
        this.stats = stats;
        this.solicitado = solicitado;
        this.nomCli=nomCli;
    }

    public String getNomCli() {
        return nomCli;
    }

    public void setNomCli(String nomCli) {
        this.nomCli = nomCli;
    }

    
    public int getIdFac() {
        return idFac;
    }

    public void setIdFac(int idFac) {
        this.idFac = idFac;
    }

    public int getClaCli() {
        return ClaCli;
    }

    public void setClaCli(int ClaCli) {
        this.ClaCli = ClaCli;
    }

    public int getIdLot() {
        return idLot;
    }

    public void setIdLot(int idLot) {
        this.idLot = idLot;
    }

    public int getCajas() {
        return cajas;
    }

    public void setCajas(int cajas) {
        this.cajas = cajas;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStats() {
        return stats;
    }

    public void setStats(int stats) {
        this.stats = stats;
    }

    public int getSolicitado() {
        return solicitado;
    }

    public void setSolicitado(int solicitado) {
        this.solicitado = solicitado;
    }
    
    
}
