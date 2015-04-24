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
public class Uni 
{
    private int ClaCLi;
    private String NomCli;
    private String StsCli;
    private String Juris;
    private int NumJur;
    private String Muni;
    private String Cons;
    private String TipUni;

    public Uni() {
    }

    public Uni(int ClaCLi, String NomCli, String StsCli, String Juris, int NumJur, String Muni, String Cons, String TipUni) {
        this.ClaCLi = ClaCLi;
        this.NomCli = NomCli;
        this.StsCli = StsCli;
        this.Juris = Juris;
        this.NumJur = NumJur;
        this.Muni = Muni;
        this.Cons = Cons;
        this.TipUni = TipUni;
    }

    public int getClaCLi() {
        return ClaCLi;
    }

    public void setClaCLi(int ClaCLi) {
        this.ClaCLi = ClaCLi;
    }

    public String getNomCli() {
        return NomCli;
    }

    public void setNomCli(String NomCli) {
        this.NomCli = NomCli;
    }

    public String getStsCli() {
        return StsCli;
    }

    public void setStsCli(String StsCli) {
        this.StsCli = StsCli;
    }

    public String getJuris() {
        return Juris;
    }

    public void setJuris(String Juris) {
        this.Juris = Juris;
    }

    public int getNumJur() {
        return NumJur;
    }

    public void setNumJur(int NumJur) {
        this.NumJur = NumJur;
    }

    public String getMuni() {
        return Muni;
    }

    public void setMuni(String Muni) {
        this.Muni = Muni;
    }

    public String getCons() {
        return Cons;
    }

    public void setCons(String Cons) {
        this.Cons = Cons;
    }

    public String getTipUni() {
        return TipUni;
    }

    public void setTipUni(String TipUni) {
        this.TipUni = TipUni;
    }
    
    
    
    
}
