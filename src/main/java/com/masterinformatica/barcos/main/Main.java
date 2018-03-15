package com.masterinformatica.barcos.main;

import com.masterinformatica.barcos.actors.Manager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Main {

	public static enum Msg {
        SALUDO, RESPONSE;
    }

    public static void main(String[] args) {
    	akka.Main.main(new String[]{Manager.class.getName()});
    }
    
}
