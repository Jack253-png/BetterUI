package com.mcreater.genshinui.screens.widget;

import com.mcreater.genshinui.animation.AnimatedValue;
import com.mcreater.genshinui.animation.AnimationProvider;
import com.mcreater.genshinui.screens.ScreenHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.*;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.mcreater.genshinui.render.InternalFonts.TITLE;
import static com.mcreater.genshinui.screens.ScreenHelper.drawCenteredTextWithoutShadow;
import static com.mcreater.genshinui.screens.ScreenHelper.getNarrationCharacterColor;

public class GenshinNarrationWidget extends DrawableHelper implements Drawable {
    private final Deque<Narration> narrations = new ArrayDeque<>();
    private final MinecraftClient client;
    private static final double HEIGHT_SCALE = 78;
    private static final double SPAC_HEIGHT_SCALE = 74;
    private static final double SPAC_WIDTH_SCALE = 0.74;
    private double width;
    private double height;
    public GenshinNarrationWidget(MinecraftClient client) {
        this.client = client;
    }

    public void pushNarration(Narration narration) {
        narrations.add(narration);
    }
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        width = client.getWindow().getScaledWidth();
        height = client.getWindow().getScaledHeight();

        double narrationNameHeight = height - HEIGHT_SCALE;
        double narrationSplitHeight = height - SPAC_HEIGHT_SCALE;
        Narration nrr = narrations.peek();
        // 525
        // 709 92
        double lineWidth = width * SPAC_WIDTH_SCALE;
        if (nrr != null) {
            narrationNameHeight -= client.textRenderer.fontHeight;
            drawCenteredTextWithoutShadow(
                    matrices,
                    client.textRenderer,
                    nrr.narrationCharacter.name.fillStyle(Style.EMPTY.withFont(TITLE)),
                    (int) width / 2,
                    (int) narrationNameHeight,
                    getNarrationCharacterColor()
            );
            ScreenHelper.fill(
                    matrices.peek().getPositionMatrix(),
                    (width - lineWidth) / 2D,
                    narrationSplitHeight,
                    (width + lineWidth) / 2D,
                    narrationSplitHeight - 1,
                    getNarrationCharacterColor(155)
            );
        }
    }
    public boolean hasNarration() {
        return !narrations.isEmpty();
    }

    public Narration popNarration() {
        return narrations.pop();
    }

    public void clearNarration() {
        narrations.clear();
    }

    public static class Narration {
        private final AnimatedValue textValue = new AnimatedValue(1, 0, 1000, animationNode -> AnimationProvider.generate(animationNode, AnimationProvider.AnimationType.EASE_IN_OUT, AnimationProvider.AnimationMode.EXPONENTIAL));
        public final MutableText text;
        public final NarrationCharacter narrationCharacter;
        public Narration(MutableText text, NarrationCharacter narrationCharacter) {
            this.text = text;
            this.narrationCharacter = narrationCharacter;
        }
        public void startNarration() {
            textValue.setExpectedValue(text.getString().length());
        }
        public int getTextIndex() {
            return (int) textValue.getCurrentValue();
        }
        public Text getRebasedText() {
            return new LiteralText(text.getString().substring(0, getTextIndex()));
        }
    }

    public record NarrationCharacter(MutableText name) {
        public static NarrationCharacter PAIMON = new NarrationCharacter(new TranslatableText("ui.narration.character.paimon"));
    }
}
