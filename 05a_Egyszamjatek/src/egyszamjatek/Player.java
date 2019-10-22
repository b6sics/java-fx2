/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egyszamjatek;

/**
 *
 * @author KjG
 */
public class Player {

    private final String NAME;
    private final String[] CHOISES;

    public String getNAME() {
        return NAME;
    }

    public String[] getCHOISES() {
        return CHOISES;
    }

    public Player(String row) {
        CHOISES = row.split(" ");
        this.NAME = CHOISES[0];
        //System.out.println("> " + this.NAME);
    }
}
