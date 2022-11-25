package tk.simplexclient.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * This class is blatantly stolen from iChunUtils with permission.
 *
 * @author iChun
 */
public class GuiSlider extends GuiButtonExt {
    /**
     * The value of this slider control.
     */
    public double sliderValue = 1.0F;

    public String dispString = "";

    /**
     * Is this slider control being dragged.
     */
    public boolean dragging = false;
    public boolean showDecimal = false;

    public double minValue = 0.0D;
    public double maxValue = 5.0D;
    public int precision = 1;

    public ISlider parent = null;

    public String suffix = "";

    public boolean drawString = true;

    public Field field;

    public GuiSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr) {
        this(id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr, null, null);
    }

    public GuiSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, Field field) {
        this(id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr, null, field);
    }

    public GuiSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, ISlider par, Field field) {
        super(id, xPos, yPos, width, height, prefix);
        this.field = field;
        minValue = minVal;
        maxValue = maxVal;
        sliderValue = (currentVal - minValue) / (maxValue - minValue);
        dispString = prefix;
        parent = par;
        suffix = suf;
        showDecimal = showDec;
        String val;

        if (showDecimal) {
            val = Double.toString(sliderValue * (maxValue - minValue) + minValue);
            precision = Math.min(val.substring(val.indexOf(".") + 1).length(), 4);
        } else {
            val = Integer.toString((int) Math.round(sliderValue * (maxValue - minValue) + minValue));
            precision = 0;
        }

        //displayString = dispString + val + suffix;
        displayString = dispString + suffix;

        drawString = drawStr;
        if (!drawString) {
            displayString = "";
        }
    }

    public GuiSlider(int id, int xPos, int yPos, String displayStr, double minVal, double maxVal, double currentVal, ISlider par) {
        this(id, xPos, yPos, 150, 20, displayStr, "", minVal, maxVal, currentVal, true, true, par, null);
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    @Override
    public int getHoverState(boolean par1) {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    @Override
    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
        if (this.visible) {
            if (this.dragging) {
                this.sliderValue = (par2 - (this.xPosition + 4)) / (float) (this.width - 8);
                updateSlider();
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            //this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
            int x = this.xPosition + (int) (this.sliderValue * (this.width - 8));
            GLRectUtils.drawRoundedRect(x, (this.yPosition + 2), (x + 10), (this.yPosition + 12), 5.0F, (new Color(0, 148, 255)).getRGB());
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    @Override
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
        if (super.mousePressed(par1Minecraft, par2, par3)) {
            this.sliderValue = (float) (par2 - (this.xPosition + 4)) / (float) (this.width - 8);
            updateSlider();
            this.dragging = true;
            return true;
        } else {
            return false;
        }
    }

    public void updateSlider() {
        if (this.sliderValue < 0.0F) {
            this.sliderValue = 0.0F;
        }

        if (this.sliderValue > 1.0F) {
            this.sliderValue = 1.0F;
        }

        String val;

        // code for module settings
        if (field != null && field.getType() == int.class) {
            for (ModuleCreator module : SimplexClient.getInstance().getModuleManager().getModules()) {
                Class<?> clazz = module.getClass();
                if (Arrays.asList(clazz.getDeclaredFields()).contains(field)) {
                    try {
                        field.setAccessible(true);
                        field.setInt(module, (int) getValue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /*
        if (showDecimal) {
            val = Double.toString(sliderValue * (maxValue - minValue) + minValue);

            if (val.substring(val.indexOf(".") + 1).length() > precision) {
                val = val.substring(0, val.indexOf(".") + precision + 1);

                if (val.endsWith(".")) {
                    val = val.substring(0, val.indexOf(".") + precision);
                }
            } else {
                while (val.substring(val.indexOf(".") + 1).length() < precision) {
                    val = val + "0";
                }
            }
        } else {
            val = Integer.toString((int) Math.round(sliderValue * (maxValue - minValue) + minValue));
        }

        if (drawString) {
            displayString = dispString + val + suffix;
        }

         */

        if (parent != null) {
            parent.onChangeSliderValue(this);
        }
    }

    @Override
    public void mouseReleased(int par1, int par2) {
        this.dragging = false;
    }

    public int getValueInt() {
        return (int) Math.round(sliderValue * (maxValue - minValue) + minValue);
    }

    public double getValue() {
        return sliderValue * (maxValue - minValue) + minValue;
    }

    public void setValue(double d) {
        this.sliderValue = (d - minValue) / (maxValue - minValue);
    }

    public static interface ISlider {
        void onChangeSliderValue(GuiSlider slider);
    }
}