package com.mcreater.genshinui.render;

import com.mcreater.genshinui.screens.ScreenHelper;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static com.mcreater.genshinui.GenshinUIClient.MOD_ID;

public class Icons extends DrawableHelper {
    public static final LogoProvider OK = LogoHelper.create(new Identifier(MOD_ID, "textures/icon/ok.png"), 16, 16);
    public static void renderOKIcon(MatrixStack stack, int x, int y) {
        ScreenHelper.drawTexture(stack, OK, 1, x, y, 16, 16);
    }
}
