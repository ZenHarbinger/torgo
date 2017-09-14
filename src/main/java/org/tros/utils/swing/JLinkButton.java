/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.net.URL;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

/**
 * Adds a button which acts as a hyper-link.
 *
 * @author matta
 */
public final class JLinkButton extends JButton {

    public enum LinkBehavior {

        ALWAYS_UNDERLINE,
        HOVER_UNDERLINE,
        NEVER_UNDERLINE,
        SYSTEM_DEFAULT
    }

    private LinkBehavior linkBehavior;
    private Color linkColor;
    private Color colorPressed;
    private Color visitedLinkColor;
    private Color disabledLinkColor;
    private URL buttonURL;
    private Action defaultAction;
    private boolean isLinkVisited;

    /**
     * Default Constructor.
     */
    public JLinkButton() {
        this(null, null, null);
    }

    /**
     * Constructor with action.
     *
     * @param action the action to perform.
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public JLinkButton(Action action) {
        this();
        setAction(action);
    }

    /**
     * Constructor with icon.
     *
     * @param icon the icon for the button.
     */
    public JLinkButton(Icon icon) {
        this(null, icon, null);
    }

    /**
     * Constructor with text.
     *
     * @param s the text of the button.
     */
    public JLinkButton(String s) {
        this(s, null, null);
    }

    /**
     * Constructor with URL.
     *
     * @param url the URL target.
     */
    public JLinkButton(URL url) {
        this(null, null, url);
    }

    /**
     * Constructor with text and url.
     *
     * @param s the text.
     * @param url the URL.
     */
    public JLinkButton(String s, URL url) {
        this(s, null, url);
    }

    /**
     * Constructor with icon and url.
     *
     * @param icon the icon.
     * @param url the URL.
     */
    public JLinkButton(Icon icon, URL url) {
        this(null, icon, url);
    }

    /**
     * Constructor with text, icon, and url.
     *
     * @param text the text.
     * @param icon the icon.
     * @param url the URL.
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public JLinkButton(String text, Icon icon, URL url) {
        super(text, icon);
        linkBehavior = LinkBehavior.SYSTEM_DEFAULT;
        linkColor = Color.blue;
        colorPressed = Color.red;
        visitedLinkColor = new Color(128, 0, 128);
        if (text == null && url != null) {
            setText(url.toExternalForm());
        }
        setLinkURL(url);
        setCursor(Cursor.getPredefinedCursor(12));
        setBorderPainted(false);
        setContentAreaFilled(false);
        setRolloverEnabled(true);
        addActionListener(defaultAction);
    }

    @Override
    public void updateUI() {
        setUI(BasicLinkButtonUI.createUI(this));
    }

    @Override
    public String getUIClassID() {
        return "LinkButtonUI";
    }

    protected void setupToolTipText() {
        String tip = null;
        if (buttonURL != null) {
            tip = buttonURL.toExternalForm();
        }
        setToolTipText(tip);
    }

    public void setLinkBehavior(LinkBehavior bnew) {
        LinkBehavior old = linkBehavior;
        linkBehavior = bnew;
        firePropertyChange("linkBehavior", old, bnew);
        repaint();
    }

    public LinkBehavior getLinkBehavior() {
        return linkBehavior;
    }

    public void setLinkColor(Color color) {
        Color colorOld = linkColor;
        linkColor = color;
        firePropertyChange("linkColor", colorOld, color);
        repaint();
    }

    public Color getLinkColor() {
        return linkColor;
    }

    public void setActiveLinkColor(Color colorNew) {
        Color colorOld = colorPressed;
        colorPressed = colorNew;
        firePropertyChange("activeLinkColor", colorOld, colorNew);
        repaint();
    }

    public Color getActiveLinkColor() {
        return colorPressed;
    }

    public void setDisabledLinkColor(Color color) {
        Color colorOld = disabledLinkColor;
        disabledLinkColor = color;
        firePropertyChange("disabledLinkColor", colorOld, color);
        if (!isEnabled()) {
            repaint();
        }
    }

    public Color getDisabledLinkColor() {
        return disabledLinkColor;
    }

    public void setVisitedLinkColor(Color colorNew) {
        Color colorOld = visitedLinkColor;
        visitedLinkColor = colorNew;
        firePropertyChange("visitedLinkColor", colorOld, colorNew);
        repaint();
    }

    public Color getVisitedLinkColor() {
        return visitedLinkColor;
    }

    public URL getLinkURL() {
        return buttonURL;
    }

    public void setLinkURL(URL url) {
        URL urlOld = buttonURL;
        buttonURL = url;
        setupToolTipText();
        firePropertyChange("linkURL", urlOld, url);
        revalidate();
        repaint();
    }

    public void setLinkVisited(boolean flagNew) {
        boolean flagOld = isLinkVisited;
        isLinkVisited = flagNew;
        firePropertyChange("linkVisited", flagOld, flagNew);
        repaint();
    }

    public boolean isLinkVisited() {
        return isLinkVisited;
    }

    public void setDefaultAction(Action actionNew) {
        Action actionOld = defaultAction;
        defaultAction = actionNew;
        firePropertyChange("defaultAction", actionOld, actionNew);
    }

    public Action getDefaultAction() {
        return defaultAction;
    }

    @Override
    protected String paramString() {
        String str;
        switch (linkBehavior) {
            case ALWAYS_UNDERLINE:
                str = "ALWAYS_UNDERLINE";
                break;
            case HOVER_UNDERLINE:
                str = "HOVER_UNDERLINE";
                break;
            case NEVER_UNDERLINE:
                str = "NEVER_UNDERLINE";
                break;
            default:
                str = "SYSTEM_DEFAULT";
                break;
        }

        String colorStr = linkColor == null ? "" : linkColor.toString();
        String colorPressStr = colorPressed == null ? "" : colorPressed
                .toString();
        String disabledLinkColorStr = disabledLinkColor == null ? ""
                : disabledLinkColor.toString();
        String visitedLinkColorStr = visitedLinkColor == null ? ""
                : visitedLinkColor.toString();
        String buttonURLStr = buttonURL == null ? "" : buttonURL.toString();
        String isLinkVisitedStr = isLinkVisited ? "true" : "false";
        return super.paramString() + ",linkBehavior=" + str + ",linkURL="
                + buttonURLStr + ",linkColor=" + colorStr + ",activeLinkColor="
                + colorPressStr + ",disabledLinkColor=" + disabledLinkColorStr
                + ",visitedLinkColor=" + visitedLinkColorStr
                + ",linkvisitedString=" + isLinkVisitedStr;
    }
}

/**
 * Basic Button UI.
 *
 * @author matta
 */
class BasicLinkButtonUI extends MetalButtonUI {

    private static final BasicLinkButtonUI UI = new BasicLinkButtonUI();

    BasicLinkButtonUI() {
    }

    public static ComponentUI createUI(JComponent jcomponent) {
        return UI;
    }

    @Override
    protected void paintText(Graphics g, JComponent com, Rectangle rect,
            String s) {
        JLinkButton bn = (JLinkButton) com;
        ButtonModel bnModel = bn.getModel();
        Color color = bn.getForeground();
        Object obj = null;
        if (bnModel.isEnabled()) {
            if (bnModel.isPressed()) {
                bn.setForeground(bn.getActiveLinkColor());
            } else if (bn.isLinkVisited()) {
                bn.setForeground(bn.getVisitedLinkColor());
            } else {
                bn.setForeground(bn.getLinkColor());
            }
        } else {
            if (bn.getDisabledLinkColor() != null) {
                bn.setForeground(bn.getDisabledLinkColor());
            }
        }
        super.paintText(g, com, rect, s);
        JLinkButton.LinkBehavior behaviour = bn.getLinkBehavior();
        boolean drawLine = false;
        if (behaviour == JLinkButton.LinkBehavior.HOVER_UNDERLINE) {
            if (bnModel.isRollover()) {
                drawLine = true;
            }
        } else if (behaviour == JLinkButton.LinkBehavior.ALWAYS_UNDERLINE || behaviour == JLinkButton.LinkBehavior.SYSTEM_DEFAULT) {
            drawLine = true;
        }
        if (!drawLine) {
            return;
        }
        FontMetrics fm = g.getFontMetrics();
        int x = rect.x + getTextShiftOffset();
        int y = (rect.y + fm.getAscent() + fm.getDescent() + getTextShiftOffset()) - 1;
        if (bnModel.isEnabled()) {
            g.setColor(bn.getForeground());
            g.drawLine(x, y, (x + rect.width) - 1, y);
        } else {
            g.setColor(bn.getBackground().brighter());
            g.drawLine(x, y, (x + rect.width) - 1, y);
        }
    }
}
