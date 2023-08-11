package com.mcreater.genshinui.screens.widget.controls;

import net.minecraft.text.Text;

public abstract class AbstractRectWidget extends ShapeWidget {
    private boolean clicked;
    public AbstractRectWidget(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    public void onClick(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);
        clicked = true;
    }

    public void onRelease(double mouseX, double mouseY) {
        super.onRelease(mouseX, mouseY);
        clicked = false;
    }

    public State getState() {
        if (!this.active) return State.DISABLED;
        if (!this.isHovered()) return State.ACTIVE;
        if (!clicked) return State.HOVERED;
        return State.CLICKED;
    }
}
