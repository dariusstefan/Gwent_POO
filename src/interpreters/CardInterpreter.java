package interpreters;

import cards.*;
import fileio.CardInput;

import java.util.ArrayList;

public class CardInterpreter {
    static public Card getCardObject(CardInput card) {
        String name = card.getName();
        int mana = card.getMana();
        String description = card.getDescription();
        ArrayList<String> colors = card.getColors();
        int attackDamage = card.getAttackDamage();
        int health = card.getHealth();

        switch (name) {
            case "Sentinel":
                MinionCard sentinel = new MinionCard(mana, description, colors, name);
                sentinel.setMinionStats(health, attackDamage, false, false);
                sentinel.setFirstRow(false);
                return sentinel;

            case "Berserker":
                MinionCard berserker = new MinionCard(mana, description, colors, name);
                berserker.setMinionStats(health, attackDamage, false, false);
                berserker.setFirstRow(false);
                return berserker;

            case "Goliath":
                MinionCard goliath = new MinionCard(mana, description, colors, name);
                goliath.setMinionStats(health, attackDamage, false, true);
                goliath.setFirstRow(true);
                return goliath;

            case "Warden":
                MinionCard warden = new MinionCard(mana, description, colors, name);
                warden.setMinionStats(health, attackDamage, false, true);
                warden.setFirstRow(true);
                return warden;

            case "The Ripper":
                TheRipper ripper = new TheRipper(mana, description, colors, name);
                ripper.setMinionStats(health, attackDamage, false, false);
                ripper.setFirstRow(true);
                return ripper;

            case "Miraj":
                Miraj miraj = new Miraj(mana, description, colors, name);
                miraj.setMinionStats(health, attackDamage, false, false);
                miraj.setFirstRow(true);
                return miraj;

            case "The Cursed One":
                TheCursedOne cursed = new TheCursedOne(mana, description, colors, name);
                cursed.setMinionStats(health, attackDamage, false, false);
                cursed.setFirstRow(false);
                return cursed;

            case "Disciple":
                Disciple disciple = new Disciple(mana, description, colors, name);
                disciple.setMinionStats(health, attackDamage, false, false);
                disciple.setFirstRow(false);
                return disciple;

            case "Firestorm":
                return new Firestorm(mana, description, colors, name);

            case "Winterfell":
                return new Winterfell(mana, description, colors, name);

            case "Heart Hound":
                return new HeartHound(mana, description, colors, name);

            case "Lord Royce":
                return new LordRoyce(mana, description, colors, name);

            case "General Kocioraw":
                return new GeneralKocioraw(mana, description, colors, name);

            case "Empress Thorina":
                return new EmpressaThorina(mana, description, colors, name);

            case "King Mudface":
                return new KingMudface(mana, description, colors, name);

            default:
                return null;
        }
    }
}
