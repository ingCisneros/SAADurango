/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Modelos.med;
import java.util.List;

/**
 *
 * @author anonimus
 */
public interface descDao 
{
    public List<med> byDes(String desc);
    public List<med>bydDescClave(String desc);
    
}
