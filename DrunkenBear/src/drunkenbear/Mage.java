package drunkenbear;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Mage extends Turtle implements MouseListener {
    private boolean skill1;
    private boolean skill2;
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
        try {
            setInfoPic(ImageIO.read(new File(
                    "res/Mage/MageInfo.gif")));
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
        render.setSpecial(true);
        skill1 = true;
        render.setActivePatches(getPatchesInRange(3));
        for (int i = 0; i < render.getImages().size(); i++) {
            render.getDisplay().remove(render.getImages().get(i));
        }
        System.out.println("Ticking");
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (getGrid().getPatch(i, j).getTurtle() == null) {
                } else {
                    render.getImages().add(render.getDisplay().add(new DisplayTurtle(getGrid().getPatch(i, j).getTurtle())));
                    render.getImages().get(render.getImages().size() - 1).setLocation(getGrid().getPatch(i, j).getTurtle().getX() * 48, getGrid().getPatch(i, j).getTurtle().getY() * 48);
                    render.getImages().get(render.getImages().size() - 1).addMouseListener(this);
                }
            }
        }
        for (int i = 0; i < render.getActivePatches().size(); i++) {
            render.getActivePatches().get(i).setAttacking(true);

        }
        Component[][] patches = render.getPatches();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (patches[i][j] != null) {
                    render.getPatchDisplay().remove(patches[i][j]);
                }
            }
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                patches[i][j] = render.getPatchDisplay().add(new DisplayImage(getGrid().getPatch(i, j).getImage()));
            }
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                patches[i][j].setLocation(i * 48, j * 48);
                if (getGrid().getPatch(i, j).getActive() || getGrid().getPatch(i, j).getAttacking()) {
                    patches[i][j].addMouseListener(this);
                }
            }
        }
        for (int i = 0; i < render.getImages().size(); i++) {
            render.getImages().get(i).addMouseListener(this);
        }
        //_patchDisplay.repaint();
    }

    public void useSkillTwo(Render render) {
        render.setSpecial(true);
        skill2 = true;
        render.setActivePatches(getPatchesInRange(2));
        for (int i = 0; i < render.getImages().size(); i++) {
            render.getDisplay().remove(render.getImages().get(i));
        }
        System.out.println("Ticking");
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (getGrid().getPatch(i, j).getTurtle() == null) {
                } else {
                    render.getImages().add(render.getDisplay().add(new DisplayTurtle(getGrid().getPatch(i, j).getTurtle())));
                    render.getImages().get(render.getImages().size() - 1).setLocation(getGrid().getPatch(i, j).getTurtle().getX() * 48, getGrid().getPatch(i, j).getTurtle().getY() * 48);
                    render.getImages().get(render.getImages().size() - 1).addMouseListener(this);
                }
            }
        }
        for (int i = 0; i < render.getActivePatches().size(); i++) {
            render.getActivePatches().get(i).setAttacking(true);

        }
        Component[][] patches = render.getPatches();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (patches[i][j] != null) {
                    render.getPatchDisplay().remove(patches[i][j]);
                }
            }
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                patches[i][j] = render.getPatchDisplay().add(new DisplayImage(getGrid().getPatch(i, j).getImage()));
            }
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                patches[i][j].setLocation(i * 48, j * 48);
                if (getGrid().getPatch(i, j).getActive() || getGrid().getPatch(i, j).getAttacking()) {
                    patches[i][j].addMouseListener(this);
                }
            }
        }
        for (int i = 0; i < render.getImages().size(); i++) {
            render.getImages().get(i).addMouseListener(this);
        }
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
            Object[] options = {"Okay!"};
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

    public void mouseClicked(MouseEvent e) {
        if (e.getComponent() instanceof DisplayImage) {
            Turtle target = getGrid().getPatch(e.getComponent().getX() / 48, e.getComponent().getY() / 48).getTurtle();
            if (target == null || (target.getFriendly() == true)) {
            } else {
                setAttacking(true);
                target.setDefending(true);
                String skillname = "";
                if (skill1) skillname = "Icicle Riser";
                else if (skill2) skillname = "Blood Lightning";
                Object[] options = {"Okay!"};
                int n = JOptionPane.showOptionDialog(getGrid().getRender().getFrame(),
                        "" + skillname+ " deals " + (getDamage() * 2 - target.getShield()) + " damage to " + target.getClass().toString(),
                        "Combat Reporter",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
                target.takeDamage(getDamage() * 2);
                if (target.getHealth() <= 0) {
                    int m = JOptionPane.showOptionDialog(getGrid().getRender().getFrame(),
                            "" + target.getClass().toString().substring(18) + " has died! " + getClass().toString().substring(18) + " gains " + target.getXP() + " experience!",
                            "Combat Reporter",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                    gainXP(target.getXP());
                }
                if (skill1){ getGrid().getRender().getCSManager().mageSkill1();}
                else if (skill2) getGrid().getRender().getCSManager().mageSkill2();
                setExhausted(true);
                setAttacking(false);
                target.setDefending(false);
            }
            activate(false);
            for (int i = 0; i < getGrid().getRender().getActivePatches().size(); i++) {
                getGrid().getRender().getActivePatches().get(i).setAttacking(false);
            }
            getGrid().getRender().setSpecial(false);
            getGrid().getRender().getActivePatches().clear();
            getGrid().getRender().tick();
            getGrid().getRender().drawPatches();
        } else if (e.getComponent() instanceof DisplayTurtle) {
            Turtle target = getGrid().getPatch(e.getComponent().getX() / 48, e.getComponent().getY() / 48).getTurtle();
            if (target == null || (target.getFriendly() == true)) {
            } else {
                setAttacking(true);
                target.setDefending(true);
                Object[] options = {"Okay!"};
                int n = JOptionPane.showOptionDialog(getGrid().getRender().getFrame(),
                        "Icicle Riser deals " + (getDamage() * 2 - target.getShield()) + " damage to " + target.getClass().toString(),
                        "Combat Reporter",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
                target.takeDamage(getDamage() * 2);
                if (target.getHealth() <= 0) {
                    int m = JOptionPane.showOptionDialog(getGrid().getRender().getFrame(),
                            "" + target.getClass().toString().substring(18) + " has died! " + getClass().toString().substring(18) + " gains " + target.getXP() + " experience!",
                            "Combat Reporter",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                    gainXP(target.getXP());
                }
                if (skill1){ getGrid().getRender().getCSManager().mageSkill1();}
                else if (skill2) getGrid().getRender().getCSManager().mageSkill2();
                setExhausted(true);
                setAttacking(false);
                target.setDefending(false);
            }
            activate(false);
            for (int i = 0; i < getGrid().getRender().getActivePatches().size(); i++) {
                getGrid().getRender().getActivePatches().get(i).setAttacking(false);
            }
            getGrid().getRender().setSpecial(false);
            getGrid().getRender().getActivePatches().clear();
            getGrid().getRender().tick();
            getGrid().getRender().drawPatches();
        }
        skill1 = false;
        skill2 = false;
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}