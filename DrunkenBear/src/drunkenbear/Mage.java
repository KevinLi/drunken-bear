package drunkenbear;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Mage extends Turtle {

	public Mage(Grid g, Patch p) {
		super(g, p);
		setFriendly(true);
		setMove(2);
		setRange(1);
		setHealth(60);
		setShield(0);
		setDamage(35);
		setMaxHealth(85);
		try {
			getStates().add(ImageIO.read(new File("res/Mage/Mage.gif")));
		} catch (IOException e) {
		}

		try {
			getStates().add(ImageIO.read(new File("res/Mage/Mage2.gif")));
		} catch (IOException e) {
		}
		try {
			setActive(ImageIO.read(new File("res/Mage/Mage-Active.gif")));
		} catch (IOException e) {
		}
		try {
			setExhausted(ImageIO.read(new File("res/Mage/Mage-Exhausted.gif")));
		} catch (IOException e) {
		}
		try {
			setAttacking(ImageIO.read(new File("res/Mage/Mage-Attacking.gif")));
		} catch (IOException e) {
		}
		try {
			setDefending(ImageIO.read(new File("res/Mage/Mage-Defending.gif")));
		} catch (IOException e) {
		}

	}

	public void learnSkillOne() {
		getSkills().add("Icicle Riser");
	}

	public void learnSkillTwo() {
		getSkills().add("Blood Lightning");
	}

	public void learnSkillThree() {
		getSkills().add("Protometeor");
	}

	public void useSkillOne(Render render) {
		render.setActivePatches(getPatchesInRange(3));

	}

	public void useSkillTwo(Render render) {
	}

	public void useSkillThree(Render render) {
		// render.getCSManager().mageSkill3();
		int totalXP = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				Turtle currentTurtle = getGrid().getPatch(i, j).getTurtle();
				if (currentTurtle != null
						&& currentTurtle.getFriendly() == false) {
					currentTurtle.takeDamage((int) (getDamage() * 1.5));
					if (currentTurtle.getHealth() <= 0) {
						totalXP += currentTurtle.getXP();
					}
				}
			}
		}
		if (totalXP > 0) {
			Object[] options = { "Okay!" };
			int n = JOptionPane.showOptionDialog(render.getFrame(),
					"Things died! " + getClass().toString().substring(18)
							+ " gains " + totalXP + " experience!",
					"Combat Reporter", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			gainXP(totalXP);
		}
		setAttacking(false);
		activate(false);
		setExhausted(true);
		render.tick();
		render.getCSManager().mageSkill3();
		render.drawPatches();
		// render.getCSManager().startCutSceneOne();
		render.getCSDisplay().repaint();
		double startTime = System.currentTimeMillis();
		render.repaint();
		render.tick();
		// while (startTime + 2000 > System.currentTimeMillis()){}
		// render.getCSDisplay().remove(render.getCSManager().getMage());
		render.repaint();
		render.tick();

	}

	public String toString() {
		return "Mage";
	}
}