package tk.simplexclient.ui.elements;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.SimplexClient;

import java.awt.*;

public class InputField extends Gui {
    private String text;
    private final String textPrefix;
    private int maximumLength;
    private boolean focused;
    private int textColor;
    private final int width;
    private final int height;
    private boolean hideText;
    private int cursorPosition;
    private int firstRenderedCharacterPosition;
    private int lastRenderedCharacterPosition;
    private boolean textSelected;
    private int stringHeight;
    private final int blinkingUnderscoreWidth;
    private InputField.InputFlavor inputFlavor;
    public int x;
    public int y;

    private int color = -1;

    private boolean visible = true;

    public InputField(int width, int height, boolean hideText, String defaultText, String textPrefix, int maximumLength, InputField.InputFlavor inputFlavor) {
        this(-1, -1, width, height, hideText, defaultText, textPrefix, maximumLength, inputFlavor);
    }

    public InputField(int x, int y, int width, int height, boolean hideText, InputField.InputFlavor inputFlavor) {
        this(x, y, width, height, hideText, "", "", -1, inputFlavor);
    }

    public InputField(int x, int y, int width, int height, boolean hideText, String defaultText, String textPrefix, int maximumLength, InputField.InputFlavor inputFlavor) {
        this.text = "";
        this.focused = false;
        this.textColor = -1;
        this.cursorPosition = 0;
        this.textSelected = false;

        if (hideText && !textPrefix.isEmpty()) {
            throw new IllegalArgumentException("Cannot have a text prefix is text is being hidden!");
        } else {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.hideText = hideText;
            this.text = defaultText;
            this.textPrefix = textPrefix;
            this.maximumLength = maximumLength;
            this.inputFlavor = inputFlavor;
            this.firstRenderedCharacterPosition = 0;
            this.lastRenderedCharacterPosition = textPrefix.length() + defaultText.length();
            Keyboard.enableRepeatEvents(true);

            if (this.height > 14) {
                this.stringHeight = this.height - 4;
            } else {
                this.stringHeight = this.height;
            }

            this.blinkingUnderscoreWidth = (int) SimplexClient.getInstance().getSmoothFont().getWidth("_");
        }
    }

    public InputField(int x, int y, int width, int height, boolean hideText, String defaultText, String textPrefix, int maximumLength, InputField.InputFlavor inputFlavor, int textColor) {
        this.text = "";
        this.focused = false;
        this.textColor = textColor;
        this.color = textColor;
        this.cursorPosition = 0;
        this.textSelected = false;

        if (hideText && !textPrefix.isEmpty()) {
            throw new IllegalArgumentException("Cannot have a text prefix is text is being hidden!");
        } else {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.hideText = hideText;
            this.text = defaultText;
            this.textPrefix = textPrefix;
            this.maximumLength = maximumLength;
            this.inputFlavor = inputFlavor;
            this.firstRenderedCharacterPosition = 0;
            this.lastRenderedCharacterPosition = textPrefix.length() + defaultText.length();
            Keyboard.enableRepeatEvents(true);

            if (this.height > 14) {
                this.stringHeight = this.height - 4;
            } else {
                this.stringHeight = this.height;
            }

            this.blinkingUnderscoreWidth = (int) SimplexClient.getInstance().getSmoothFont().getWidth("_");
        }
    }

    public void keyTyped(char character, int keyCode) {
        if (this.isVisible()) {
            if (this.focused) {
                if (keyCode != 1 && keyCode != 28) {
                    if (this.text.length() > 0) {
                        if (keyCode == 14) {

                            if (this.textSelected) {
                                this.text = "";
                                this.textSelected = false;
                                this.resetCursorPosition();
                            } else {
                                if (this.cursorPosition == 0) {
                                    return;
                                }

                                this.text = this.text.substring(0, this.cursorPosition - 1) + this.text.substring(this.cursorPosition);

                                --this.cursorPosition;

                                --this.lastRenderedCharacterPosition;
                                this.fitTextInFieldDeletion();
                            }

                            return;
                        }

                        if (keyCode == 211) {
                            if (this.textSelected) {
                                this.text = "";
                                this.textSelected = false;
                                this.resetCursorPosition();
                            } else {

                                if (this.cursorPosition == this.text.length()) {
                                    return;
                                }

                                this.text = this.text.substring(0, this.cursorPosition) + this.text.substring(this.cursorPosition + 1);
                                --this.lastRenderedCharacterPosition;
                                this.fitTextInFieldDeletion();
                            }

                            return;
                        }
                    }

                    Character character1 = this.checkInputCharacter(character);

                    if (character1 != null) {
                        if (this.textSelected) {
                            this.text = "";
                            this.textSelected = false;
                            this.resetCursorPosition();
                        }

                        if (this.text.length() != this.maximumLength) {
                            this.text = this.text.substring(0, this.cursorPosition) + character1 + this.text.substring(this.cursorPosition, this.text.length());
                            ++this.cursorPosition;
                            this.textSelected = false;
                            this.fitTextInFieldKeyTyped();
                        }
                    } else if (keyCode == 203) {
                        if (this.cursorPosition > 0) {
                            --this.cursorPosition;
                        }

                        this.textSelected = false;
                    } else if (keyCode == 205) {
                        if (this.cursorPosition < this.text.length()) {
                            ++this.cursorPosition;
                        }

                        this.textSelected = false;
                    } else {
                        if (GuiScreen.isCtrlKeyDown()) {
                            switch (keyCode) {
                                case 30:
                                    this.textSelected = true;
                                    return;

                                case 46:
                                    if (!this.hideText && this.textSelected) {
                                        GuiScreen.setClipboardString(this.text);
                                    }

                                    return;

                                case 47:
                                    if (this.textSelected) {
                                        this.text = "";
                                        this.textSelected = false;
                                        this.resetCursorPosition();
                                    }

                                    String s = GuiScreen.getClipboardString();

                                    for (char c0 : s.toCharArray()) {
                                        character1 = this.checkInputCharacter(c0);

                                        if (character1 != null) {
                                            if (this.text.length() == this.maximumLength) {
                                                return;
                                            }

                                            this.text = this.text + character1;
                                            ++this.cursorPosition;
                                        }
                                    }
                            }
                        }
                    }
                } else {
                    this.focused = false;
                    this.textSelected = false;
                    this.resetCursorPosition();
                }
            }
        }
    }

    private Character checkInputCharacter(char character) {
        if (this.isVisible()) {
            if (this.inputFlavor.contains(character)) {
                if (this.inputFlavor == InputField.InputFlavor.HEX_COLOR) {
                    character = String.valueOf(character).toUpperCase().toCharArray()[0];
                }

                return character;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void render() {
        if (this.isVisible()) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            //Wrapper.getInstance().getBlTextureManager().bindTextureMipmapped(GuiButton.backToGameButton);
            int i = this.width;
            int j = this.height;
            //Gui.drawModalRectWithCustomSizedTexture(this.x, this.y, (float)i, (float)j, i, j, (float)i, (float)j);
            String s = this.getTextToDraw();
            this.fitTextInField();
            int k = (int) SimplexClient.getInstance().getSmoothFont().getWidth(s.substring(this.firstRenderedCharacterPosition, this.lastRenderedCharacterPosition));
            int l = this.lastRenderedCharacterPosition;

            if (this.isFocused() && System.currentTimeMillis() % 1060L >= 530L) {
                if (this.cursorPosition == this.text.length()) {
                    s = s + "_";
                    ++l;
                } else {
                    int i1 = (int) SimplexClient.getInstance().getSmoothFont().getWidth(s.substring(this.firstRenderedCharacterPosition, this.cursorPosition + this.textPrefix.length()));
                    Gui.drawRect(this.x + 5 + i1, this.y + 2, this.x + 5 + i1 + 1, this.y + this.height - 10, -570425345);
                    //Gui.drawRect(this.x + 5 + i1, this.y + 2, this.x + 5 + i1 + 1, this.y + this.height - 10, -1);
                }
            }

            if (this.textSelected) {
                Gui.drawRect(this.x + 5, this.y + 1, this.x + 5 + k, this.y + this.height - 10, new Color(51, 144, 255).getRGB());
                setTextColor(-1);
            } else {
                setTextColor(this.color);
            }

            SimplexClient.getInstance().getSmoothFont().drawString(s.substring(this.firstRenderedCharacterPosition, l), this.x + 5, this.y, textColor);

            /*
            if (!this.isFocused() && s.substring(this.firstRenderedCharacterPosition, l).equalsIgnoreCase("")) {
                String text = "";
                if (this.inputFlavor == InputFlavor.EMAIL) {
                    text = "Email...";
                } else if (this.inputFlavor == InputFlavor.PASSWORD) {
                    text = "Password...";
                }
                SimplexClient.openSans2.drawString(text, this.x + 5, this.y, new Color(0, 0, 0).getRGB());
            }
             */
        }
    }

    public void update(int mouseX, int mouseY) {
    }

    public boolean onClick(int mouseX, int mouseY, int mouseButton) {
        if (this.isVisible()) {
            this.focused = false;

            if (mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height) {
                this.focused = true;
                this.textSelected = false;
                this.resetCursorPosition();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private String getTextToDraw() {
        if (this.isVisible()) {
            String s = this.textPrefix + this.text;

            if (this.hideText) {
                s = StringUtils.leftPad("", this.text.length(), '*');
            }

            return s;
        } else {
            return null;
        }
    }

    private void fitTextInFieldKeyTyped() {
        if (this.isVisible()) {
            String s = this.getTextToDraw();
            int i = this.width - 10;

            if (this.cursorPosition == s.length()) {
                i -= this.blinkingUnderscoreWidth;
            }

            for (int j = s.length(); j >= this.firstRenderedCharacterPosition; --j) {
                int k = (int) SimplexClient.getInstance().getSmoothFont().getWidth(s.substring(this.firstRenderedCharacterPosition, j));

                if (k <= i) {
                    this.lastRenderedCharacterPosition = j;
                    break;
                }
            }
        }
    }

    private void fitTextInFieldDeletion() {
        if (this.isVisible()) {
            String s = this.getTextToDraw();
            int i = this.width - 10;

            if (this.cursorPosition == s.length()) {
                i -= this.blinkingUnderscoreWidth;
            }

            if (this.lastRenderedCharacterPosition == s.length()) {
                for (int j = 0; j <= this.lastRenderedCharacterPosition; ++j) {
                    int k = (int) SimplexClient.getInstance().getSmoothFont().getWidth(s.substring(j, this.lastRenderedCharacterPosition));

                    if (k <= i) {
                        this.firstRenderedCharacterPosition = j;
                        break;
                    }
                }
            } else {
                for (int l = s.length(); l >= this.firstRenderedCharacterPosition; --l) {
                    int i1 = (int) SimplexClient.getInstance().getSmoothFont().getWidth(s.substring(this.firstRenderedCharacterPosition, l));

                    if (i1 <= i) {
                        this.lastRenderedCharacterPosition = l;
                        break;
                    }
                }
            }
        }
    }

    private void fitTextInField() {
        if (this.isVisible()) {
            String s = this.getTextToDraw();
            int i = this.width - 10;

            if (this.cursorPosition == s.length() - this.textPrefix.length()) {
                i -= this.blinkingUnderscoreWidth;
            }

            int j = (int) SimplexClient.getInstance().getSmoothFont().getWidth(s.substring(this.firstRenderedCharacterPosition, this.lastRenderedCharacterPosition));

            if (j > i && this.lastRenderedCharacterPosition == s.length()) {
                for (int k = 0; k < s.length(); ++k) {
                    int l = (int) SimplexClient.getInstance().getSmoothFont().getWidth(s.substring(k));

                    if (l <= i) {
                        this.firstRenderedCharacterPosition = k;
                        break;
                    }
                }
            }

            if (this.cursorPosition < this.firstRenderedCharacterPosition) {
                int i1 = this.firstRenderedCharacterPosition - this.cursorPosition;
                this.firstRenderedCharacterPosition -= i1;

                for (int k1 = s.length(); k1 >= this.firstRenderedCharacterPosition; --k1) {
                    j = (int) SimplexClient.getInstance().getSmoothFont().getWidth(s.substring(this.firstRenderedCharacterPosition, k1));

                    if (j <= i) {
                        this.lastRenderedCharacterPosition = k1;
                        break;
                    }
                }
            } else if (this.cursorPosition > this.lastRenderedCharacterPosition) {
                int j1 = this.cursorPosition - this.lastRenderedCharacterPosition;
                this.lastRenderedCharacterPosition += j1;

                for (int l1 = 0; l1 <= this.lastRenderedCharacterPosition; ++l1) {
                    j = (int) SimplexClient.getInstance().getSmoothFont().getWidth(s.substring(l1, this.lastRenderedCharacterPosition));

                    if (j <= i) {
                        this.firstRenderedCharacterPosition = l1;
                        break;
                    }
                }
            }
        }
    }

    public boolean isFocused() {
        return this.focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;

        if (focused) {
            this.resetCursorPosition();
        }
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
        this.firstRenderedCharacterPosition = 0;
        this.lastRenderedCharacterPosition = this.textPrefix.length() + text.length();
    }

    public int getMaximumLength() {
        return this.maximumLength;
    }

    public void reset() {
        this.focused = false;
        this.text = "";
        this.textSelected = false;
        this.resetCursorPosition();
    }

    private void resetCursorPosition() {
        this.cursorPosition = this.text.length();
        this.firstRenderedCharacterPosition = 0;
        this.lastRenderedCharacterPosition = this.textPrefix.length() + this.text.length();
    }

    public int getTextColor() {
        return this.textColor;
    }

    public void setTextColor(int color) {
        this.textColor = color;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public static class InputFlavor {
        public static final InputField.InputFlavor EMAIL = new InputField.InputFlavor("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!#$%&\'*+-/=?^_`{|}~;.@\"(),:;<>@[\\]");
        public static final InputField.InputFlavor HEX_COLOR = new InputField.InputFlavor("abcdefABCDEF0123456789");
        public static final InputField.InputFlavor MOD_PROFILE_NAME = new InputField.InputFlavor("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-(),. ");
        public static final InputField.InputFlavor PASSWORD = new InputField.InputFlavor("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!#$%&\'*+-/=?^_`{|}~;.@\"(),:;<>@[\\] ");
        private final String flavor;

        public InputFlavor(String flavor) {
            this.flavor = flavor;
        }

        public boolean contains(char ch) {
            return this.flavor.contains(String.valueOf(ch));
        }
    }
}
