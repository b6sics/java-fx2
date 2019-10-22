/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egyszamjatek;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author KjG
 */
public class Main {

    private final String dataSource = "egyszamjatek1.txt";
    private final String codePage = "utf8";
    private final List<Player> gameArray = new ArrayList<>();
    private final String averageFormat = "%.2f\n";

    private void fillGameArray() {
        System.out.print("2.feladat: ...");
        try (Scanner in = new Scanner(new File(dataSource), codePage)) {
            while (in.hasNextLine()) {
                gameArray.add(new Player(in.nextLine()));
            }
        } catch (Exception ex) {
            System.err.println("> " + ex.getMessage());
        }
    }

    private int fetchNumberOfPlayers() {
        System.out.print("3.feladat: Játékosok száma: ");
        return gameArray.size();
    }

    private int getRound() {
        System.out.print("4.feladat: Kérem a forduló sorszámát: ");
        Scanner in = new Scanner(System.in, codePage);
        return in.nextInt();
    }

    private double roundAverage(int round) {
        System.out.print("5.feladat: A megadott forduló tippjeinek átlaga: ");
        double sum = 0;
        for (Player player : gameArray) {
            sum += Integer.valueOf(player.getCHOISES()[round]);
        }
        return sum / gameArray.size();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main newPlay = new Main();
        newPlay.fillGameArray();
        System.out.println("adatbeolvasás kész!");
        System.out.println(newPlay.fetchNumberOfPlayers() + " fő");
        System.out.printf(newPlay.averageFormat,
                newPlay.roundAverage(newPlay.getRound()));
    }

}
