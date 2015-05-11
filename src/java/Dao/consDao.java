/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Modelos.cons;
import java.util.List;

/**
 *
 * @author anonimus
 */
public interface consDao {

    public List<cons> diario();

    public List<cons> listFec(String date);

    public boolean delete(String fol, String nombre);

    public List<cons> ByUni(int uni);

    public List<cons> ByUnFec(int uni, String fec);

    public List<cons> Rural();

    public List<cons> SubUrb();
    
    public List<cons> AlmJur();
    
    public List<cons> Caravanas();

    public List<cons> listFecRur(String date);

    public List<cons> ByUnFecRural(int uni, String fec);
    
    public List<cons> listFecSubUrb(String date);
    
    public List<cons> listFecCarav(String date);
    
    public List<cons> listFecAlm(String date);
    
    public List<cons> ByUnFecSub(int uni, String fec);
    
    public List<cons> ByUnFecAlm(int uni, String fec);
}
