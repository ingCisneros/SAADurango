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
public interface consDao 
{
    public List<cons> diario();
    public List<cons> listFec(String date);
    public boolean delete(String fol, String nombre);
    public List<cons> ByUni(int uni);
}
