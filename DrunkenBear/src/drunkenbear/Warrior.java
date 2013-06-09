/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drunkenbear;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Warrior extends Turtle {
	public Warrior(Grid g, Patch p) {
		super(g, p);
		setFriendly(true);
		setMove(3);
		setRange(0);
		setHealth(100);
		setShield(5);
		setDamage(20);
		setMaxHealth(110);
		try {
			getStates().add(ImageIO.read(new File("res/Warrior/Warrior.gif")));
		} catch (IOException e) {
		}

		try {
			getStates().add(ImageIO.read(new File("res/Warrior/Warrior2.gif")));
		} catch (IOException e) {
		}
		try {
			setActive(ImageIO.read(new File("res/Warrior/Warrior-Active.gif")));
		} catch (IOException e) {
		}
		try {
			setExhausted(ImageIO.read(new File(
					"res/Warrior/Warrior-Exhausted.gif")));
		} catch (IOException e) {
		}
		try {
			setAttacking(ImageIO.read(new File(
					"res/Warrior/Warrior-Attacking.gif")));
		} catch (IOException e) {
		}
		try {
			// defending image nonexistent?
			setDefending(ImageIO.read(new File(
					"res/Warrior/Warrior-Attacking.gif")));
		} catch (IOException e) {
		}
		setImage(getStates().get(0));

	}

	@Override
	public String toString() {
		return "Warrior";
	}

}