package com.mcreater.genshinui.screens.widget;

import com.mcreater.genshinui.animation.AnimatedValue;
import com.mcreater.genshinui.animation.AnimationProvider;
import com.mcreater.genshinui.screens.ScreenHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.*;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static com.mcreater.genshinui.render.InternalFonts.STANDARD;
import static com.mcreater.genshinui.render.InternalFonts.TITLE;
import static com.mcreater.genshinui.screens.ScreenHelper.*;

public class GenshinNarrationWidget extends DrawableHelper implements Drawable {
    private final Deque<Narration> narrations = new ArrayDeque<>();
    private final MinecraftClient client;
    private static final double HEIGHT_SCALE = 78;
    private static final double SPAC_HEIGHT_SCALE = 74;
    private static final double TEXT_HEIGHT_SCALE = 69;
    private static final double SPAC_WIDTH_SCALE = 0.56; // 0.74;
    private double width;
    private double height;
    public GenshinNarrationWidget(MinecraftClient client) {
        this.client = client;
    }

    public void pushNarration(Narration narration) {
        narrations.add(narration);
    }
    private void updateSize() {
        width = client.getWindow().getScaledWidth();
        height = client.getWindow().getScaledHeight();
    }
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        updateSize();

        double narrationNameHeight = height - HEIGHT_SCALE;
        double narrationSplitHeight = height - SPAC_HEIGHT_SCALE;
        AtomicReference<Double> narrationTextHeight = new AtomicReference<>(height - TEXT_HEIGHT_SCALE);
        Narration nrr = narrations.peek();
        // 525
        // 709 92
        double lineWidth = width * SPAC_WIDTH_SCALE;
        if (nrr != null) {
            fillGradient(matrices, 0, (int) narrationSplitHeight, (int) width, (int) (height - (SPAC_HEIGHT_SCALE / 4)), new Color(0, 0, 0, 20).getRGB(), new Color(0, 0, 0, 0).getRGB());

            nrr.startNarration();
            lineWidth = Math.min(
                    lineWidth,
                    client.textRenderer.getTextHandler().wrapLines(nrr.text.getString(), (int) lineWidth - 20, Style.EMPTY.withFont(STANDARD))
                            .stream()
                            .map(StringVisitable::getString)
                            .mapToInt(client.textRenderer::getWidth)
                            .max()
                            .orElse((int) lineWidth)
            );
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
                    (width - lineWidth - 20) / 2D,
                    narrationSplitHeight,
                    (width + lineWidth + 20) / 2D,
                    narrationSplitHeight - 1,
                    getNarrationCharacterColor(85)
            );

            List<StringVisitable> texts = client.textRenderer.getTextHandler().wrapLines(nrr.getRebasedText().getString(), (int) lineWidth, Style.EMPTY.withFont(STANDARD));
            List<StringVisitable> fullText = client.textRenderer.getTextHandler().wrapLines(nrr.text.getString(), (int) lineWidth, Style.EMPTY.withFont(STANDARD));
            if (!texts.isEmpty() && !fullText.isEmpty()) {
                AtomicInteger finalLineWidth = new AtomicInteger((int) lineWidth);
                texts.forEach(text -> {
                    String textS = text.getString();
                    finalLineWidth.set(client.textRenderer.getWidth(fullText.get(texts.indexOf(text))));
                    drawTextWithoutShadow(
                            matrices,
                            client.textRenderer,
                            new LiteralText(textS).fillStyle(Style.EMPTY.withFont(STANDARD)),
                            (int) (width - finalLineWidth.get()) / 2,
                            narrationTextHeight.get().intValue(),
                            getTextBaseColor()
                    );
                    narrationTextHeight.updateAndGet(v -> v + client.textRenderer.getWrappedLinesHeight(text.getString(), finalLineWidth.get()));
                });
            }
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
        private final AnimatedValue textValue;
        public final MutableText text;
        private boolean started = false;
        public final NarrationCharacter narrationCharacter;
        public Narration(MutableText text, NarrationCharacter narrationCharacter) {
            this(text, narrationCharacter, 500);
        }
        public Narration(MutableText text, NarrationCharacter narrationCharacter, int duration) {
            this.text = text;
            this.narrationCharacter = narrationCharacter;
            textValue = new AnimatedValue(0, 0, duration, animationNode -> AnimationProvider.generate(animationNode, AnimationProvider.AnimationType.EASE_IN_OUT, AnimationProvider.AnimationMode.LINEAR));
        }
        private String getText() {
            return text.getString();
        }
        public void startNarration() {
            if (!started) {
                textValue.setExpectedValue(getText().length());
                started = true;
            }
        }
        public int getTextIndex() {
            return (int) Math.round(textValue.getCurrentValue());
        }
        public Text getRebasedText() {
            return new LiteralText(getText().substring(0, getTextIndex()));
        }
    }

    public record NarrationCharacter(MutableText name) {
        public static NarrationCharacter PAIMON = new NarrationCharacter(new TranslatableText("ui.narration.character.paimon"));
    }
}
