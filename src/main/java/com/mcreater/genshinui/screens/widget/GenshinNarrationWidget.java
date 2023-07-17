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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static com.mcreater.genshinui.render.InternalFonts.CHAR_NAME;
import static com.mcreater.genshinui.render.InternalFonts.STANDARD;
import static com.mcreater.genshinui.screens.ScreenHelper.*;

public class GenshinNarrationWidget extends DrawableHelper implements Drawable {
    private final Deque<Narration> narrations = new ArrayDeque<>();
    private final Deque<Narration> narrations_backup = new ArrayDeque<>();
    private final AnimatedValue opacity = new AnimatedValue(0, 0, 250, a -> AnimationProvider.generate(a, AnimationProvider.AnimationType.EASE_OUT, AnimationProvider.AnimationMode.EXPONENTIAL));
    private final MinecraftClient client;
    private static final double HEIGHT_SCALE = 78;
    private static final double SPAC_HEIGHT_SCALE = 74;
    private static final double TEXT_HEIGHT_SCALE = 69;
    private static final double SPAC_WIDTH_SCALE = 0.56; // 0.74;
    private static final double SPAC_WIDTH_SCALE_MIN = 0.15;
    private double width;
    private double height;
    public GenshinNarrationWidget(MinecraftClient client) {
        this.client = client;
    }

    public void pushNarration(Narration narration) {
        narrations.add(narration);
        narrations_backup.add(narration);
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
        Narration nrr = narrations_backup.peek();
        double lineWidth = width * SPAC_WIDTH_SCALE;
        if (nrr != null) {
            opacity.setExpectedValue(narrations.contains(nrr) ? 1 : 0);
            double opaci = opacity.getCurrentValue();
            if (!nrr.needOpacityIn && narrations.contains(nrr)) opaci = 1;
            if (!nrr.needOpacityOut && !narrations.contains(nrr)) opaci = 1;
            double opaci2 = opaci;

            drawRect(
                    matrices,
                    0,
                    (int) narrationNameHeight - 12,
                    (int) width / 2,
                    (int) height,
                    new VertexColor(0, 0, 0, (float) (0.4F * opaci)),
                    TRANSPARENT,
                    TRANSPARENT,
                    TRANSPARENT
            );

            drawRect(
                    matrices,
                    0,
                    (int) narrationNameHeight - 15,
                    (int) width / 2,
                    (int) narrationNameHeight - 12,
                    TRANSPARENT,
                    TRANSPARENT,
                    TRANSPARENT,
                    new VertexColor(0, 0, 0, (float) (0.4F * opaci))
            );

            drawRect(
                    matrices,
                    (int) width / 2,
                    (int) narrationNameHeight - 12,
                    (int) width,
                    (int) height,
                    TRANSPARENT,
                    new VertexColor(0, 0, 0, (float) (0.4F * opaci)),
                    TRANSPARENT,
                    TRANSPARENT
            );

            drawRect(
                    matrices,
                    (int) width / 2,
                    (int) narrationNameHeight - 15,
                    (int) width,
                    (int) narrationNameHeight - 12,
                    TRANSPARENT,
                    TRANSPARENT,
                    new VertexColor(0, 0, 0, (float) (0.4F * opaci)),
                    TRANSPARENT
            );

            lineWidth = Math.min(
                    lineWidth,
                    client.textRenderer.getTextHandler().wrapLines(nrr.text.getString(), (int) lineWidth - 20, Style.EMPTY.withFont(STANDARD))
                            .stream()
                            .map(StringVisitable::getString)
                            .mapToInt(client.textRenderer::getWidth)
                            .max()
                            .orElse((int) lineWidth)
            );
            lineWidth = Math.max(lineWidth, width * SPAC_WIDTH_SCALE_MIN);

            narrationNameHeight -= client.textRenderer.fontHeight;
            if ((opaci * 255) >= 5) {
                drawCenteredTextWithoutShadow(
                        matrices,
                        client.textRenderer,
                        nrr.narrationCharacter.name.fillStyle(Style.EMPTY.withFont(CHAR_NAME)),
                        (int) width / 2,
                        (int) narrationNameHeight,
                        getNarrationCharacterColor((int) (opaci * 255))
                );
                ScreenHelper.fill(
                        matrices.peek().getPositionMatrix(),
                        (width - lineWidth - 20) / 2D,
                        narrationSplitHeight,
                        (width + lineWidth + 20) / 2D,
                        narrationSplitHeight - 1,
                        getNarrationCharacterColor((int) (85 * opaci))
                );
            }

            List<StringVisitable> texts = client.textRenderer.getTextHandler().wrapLines(nrr.getRebasedText().getString(), (int) lineWidth, Style.EMPTY.withFont(STANDARD));
            List<StringVisitable> fullText = client.textRenderer.getTextHandler().wrapLines(nrr.text.getString(), (int) lineWidth, Style.EMPTY.withFont(STANDARD));
            if (!texts.isEmpty() && !fullText.isEmpty()) {
                AtomicInteger finalLineWidth = new AtomicInteger((int) lineWidth);
                texts.forEach(text -> {
                    String textS = text.getString();
                    finalLineWidth.set(client.textRenderer.getWidth(fullText.get(texts.indexOf(text))));

                    if (textS.length() >= 1) {
                        String pre = textS.substring(0, textS.length() - 1);
                        if ((opaci2 * 255) >= 5) drawTextWithoutShadow(
                                matrices,
                                client.textRenderer,
                                new LiteralText(pre).fillStyle(Style.EMPTY.withFont(STANDARD)),
                                (int) (width - finalLineWidth.get()) / 2,
                                narrationTextHeight.get().intValue(),
                                getTextBaseColor((int) (opaci2 * 255))
                        );
                        if ((nrr.getLastTextTransparency() * opaci2) >= 5) drawTextWithoutShadow(
                                matrices,
                                client.textRenderer,
                                new LiteralText(textS.substring(textS.length() - 1)).fillStyle(Style.EMPTY.withFont(STANDARD)),
                                (int) (width - finalLineWidth.get()) / 2 + client.textRenderer.getWidth(new LiteralText(pre).fillStyle(Style.EMPTY.withFont(STANDARD))),
                                narrationTextHeight.get().intValue(),
                                getTextBaseColor((int) (nrr.getLastTextTransparency() * opaci2))
                        );
                    }

                    narrationTextHeight.updateAndGet(v -> v + client.textRenderer.getWrappedLinesHeight(text.getString(), finalLineWidth.get()));
                });
            }

            if (opacity.getCurrentValue() < 0.0011 && opacity.getExpectedValue() != 1) narrations_backup.remove(nrr);
            if (opacity.getCurrentValue() > 0.995) nrr.startNarration();
        }
    }
    public boolean hasNarration() {
        return !narrations_backup.isEmpty() && Optional.ofNullable(narrations_backup.peek()).map(Narration::isHideHud).orElse(false);
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
        public final boolean needOpacityIn;
        public final boolean needOpacityOut;
        public final boolean hideHud;
        public Narration(MutableText text, NarrationCharacter narrationCharacter) {
            this(text, narrationCharacter, 200, true, true, true);
        }

        public boolean isHideHud() {
            return hideHud;
        }

        public Narration(MutableText text, NarrationCharacter narrationCharacter, int duration, boolean needOpacityIn, boolean needOpacityOut, boolean hideHud) {
            this.text = text;
            this.needOpacityIn = needOpacityIn;
            this.needOpacityOut = needOpacityOut;
            this.narrationCharacter = narrationCharacter;
            this.hideHud = hideHud;
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
            return (int) Math.ceil(textValue.getCurrentValue());
        }
        public int getLastTextTransparency() {
            double opa = textValue.getCurrentValue() - Math.floor(textValue.getCurrentValue());
            return (int) Math.round(255 * opa);
        }
        public Text getRebasedText() {
            return new LiteralText(getText().substring(0, getTextIndex()));
        }
    }

    public record NarrationCharacter(MutableText name) {
        public static NarrationCharacter PAIMON = new NarrationCharacter(new TranslatableText("ui.narration.character.paimon"));
    }
}
