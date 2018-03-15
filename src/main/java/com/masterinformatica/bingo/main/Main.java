package com.masterinformatica.bingo.main;

import com.masterinformatica.barcos.actors.Manager;
import com.masterinformatica.bingo.entities.Bombo;
import com.masterinformatica.bingo.entities.Carton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Main {
	
    public static void main(String[] args) {    	
    	Bombo bombo = new Bombo();
    	Carton carton = new Carton();
    	
    	System.err.println(bombo.toString());
    	System.err.println(carton.toString());
    	
    	akka.Main.main(new String[]{Manager.class.getName()});
    }
    
}
