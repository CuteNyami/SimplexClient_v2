package tk.simplexclient.render.ui;

import tk.simplexclient.render.UIRenderer;

public class TestUI extends UIRenderer {

    @Override
    public void render(int mouseX, int mouseY) {
        /*
        if (RenderEngine.texture(new ResourceLocation("simplex/menu/main/splash.png"), -21 / 90, -1 / 90, 0.0f, 0.0f, this.width + 20, this.height + 20, (float) (this.width + 21), (float) (this.height + 20))) {
            System.out.println("clicked");
        }

         */
       // this.mc.getTextureManager().bindTexture(new ResourceLocation("simplex/menu/main/splash.png"));
        //Gui.drawModalRectWithCustomSizedTexture(-21 / 90, -1 / 90, 0.0f, 0.0f, this.width + 20, this.height + 20, (float) (this.width + 21), (float) (this.height + 20));
        super.render(mouseX, mouseY);
    }
}
