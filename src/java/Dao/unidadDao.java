/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Modelos.Uni;
import java.util.List;

/**
 *
 * @author anonimus
 */
public interface unidadDao
{
 public  List<Uni> rutaDiaria();
 public  List<Uni> rutaDiariaDesc(String desc);
 public String tipUni(int uni);
 public List<Uni> byNomUni(String uniName);
 public  List<Uni> rural(String desc);
 public  List<Uni> subUrb(String desc);
 public  List<Uni> almaJur(String desc);
}
