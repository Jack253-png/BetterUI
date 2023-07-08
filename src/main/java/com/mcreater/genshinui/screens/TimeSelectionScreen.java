package com.mcreater.genshinui.screens;

import com.mcreater.genshinui.animation.AnimatedValue;
import com.mcreater.genshinui.animation.AnimationProvider;
import com.mcreater.genshinui.screens.widget.GenshinNormalButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import static com.mcreater.genshinui.screens.ScreenHelper.fillScreen;

@IgnoredScreen
public class TimeSelectionScreen extends Screen {
    private AnimatedValue value;
    public TimeSelectionScreen(Text title) {
        super(title);
    }

    public void removed() {
        value.setExpectedValue(0);
    }

    protected void init() {
        super.init();
        this.addDrawableChild(new GenshinNormalButtonWidget(0, 0, 300, 20, new LiteralText("test"), a -> {}));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (value == null) value = new AnimatedValue(115, 0, 500, animationNode -> AnimationProvider.generate(animationNode, AnimationProvider.AnimationType.EASE_IN_OUT, AnimationProvider.AnimationMode.EXPONENTIAL));
        fillScreen(matrices, (int) value.getCurrentValue());
        super.render(matrices, mouseX, mouseY, delta);
    }
}
