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
public class Provedor 
{
    private int FclavePro;
    private String NomPro;

    public Provedor() {
    }

    public Provedor(int FclavePro, String NomPro) {
        this.FclavePro = FclavePro;
        this.NomPro = NomPro;
    }

    public int getFclavePro() {
        return FclavePro;
    }

    public void setFclavePro(int FclavePro) {
        this.FclavePro = FclavePro;
    }

    public String getNomPro() {
        return NomPro;
    }

    public void setNomPro(String NomPro) {
        this.NomPro = NomPro;
    }
    
    
    
}
