package com.mcreater.betterui.animation;

import java.util.function.ToDoubleFunction;

public interface AnimationGenerator extends ToDoubleFunction<AnimationNode> {
    AnimationGenerator LINEAR = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        return c * t / d + b;
    };
    AnimationGenerator QUADRATIC_EASEIN = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        return c * (t / d) * (t / d) + b;
    };
    AnimationGenerator QUADRATIC_EASEOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        return -c * (t / d) * (t / d - 2) + b;
    };
    AnimationGenerator QUADRATIC_EASEINOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double i1 = t / d * 2;
        if (i1 < 1) return c / 2 * i1 * i1 + b;
        double i2 = i1 - 1;
        return -c / 2 * (i2 * (i2 - 2) - 1) + b;
    };
    AnimationGenerator SINUSOIDAL_EASEIN = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        return -c * Math.cos(t / d * (Math.PI / 2)) + c + b;
    };

    AnimationGenerator SINUSOIDAL_EASEOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        return c * Math.sin(t / d * (Math.PI / 2)) + b;
    };

    AnimationGenerator SINUSOIDAL_EASEINOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        return -c / 2 * (Math.cos(Math.PI * t / d) - 1) + b;
    };

    AnimationGenerator EXPONENTIAL_EASEIN = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        if (t == 0) return b;
        return c * Math.pow(2, 10 * (t / d - 1)) + b;
    };

    AnimationGenerator EXPONENTIAL_EASEOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        if (t == d) return b + c;
        return c * (-Math.pow(2, -10 * t / d) + 1) + b;
    };

    AnimationGenerator EXPONENTIAL_EASEINOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        if (t == 0) return b;
        if( t == d) return b + c;
        double i1 = t / d * 2;
        if (i1 < 1) return c / 2 * Math.pow(2, 10 * (i1 - 1)) + b;
        return c / 2 * (-Math.pow(2, -10 * (i1 - 1)) + 2) + b;
    };

    AnimationGenerator CIRCULAR_EASEIN = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        double i1 = t / d;
        return -c * (Math.sqrt(1 - i1 * i1) - 1) + b;
    };

    AnimationGenerator CIRCULAR_EASEOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        double i1 = t / d - 1;
        return c * Math.sqrt(1 - i1 * i1) + b;
    };

    AnimationGenerator CIRCULAR_EASEINOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double i1 = t / d * 2;
        if (i1 < 1) return -c / 2 * (Math.sqrt(1 - i1 * i1) - 1) + b;
        double i2 = i1 - 2;
        return c / 2 * (Math.sqrt(1 - i2 * i2) + 1) + b;
    };

    AnimationGenerator CUBIC_EASEIN = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        double i1 = t / d;
        return c * i1 * i1 * i1 + b;
    };

    AnimationGenerator CUBIC_EASEOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        double i1 = t / d - 1;
        return c * (i1 * i1 * i1 + 1) + b;
    };

    AnimationGenerator CUBIC_EASEINOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double i1 = t / d * 2;
        if (i1 < 1) return c / 2 * i1 * i1 * i1 + b;
        double i2 = i1 - 2;
        return c / 2 * (i2 * i2 * i2 + 2) + b;
    };

    AnimationGenerator QUARTIC_EASEIN = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double i1 = t / d;
        return c * i1 * i1 * i1 * i1 + b;
    };

    AnimationGenerator QUARTIC_EASEOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double i1 = t / d - 1;
        return -c * (i1 * i1 * i1 * i1 - 1) + b;
    };

    AnimationGenerator QUARTIC_EASEINOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double i1 = t / d * 2;
        if (i1 < 1) return c / 2 * i1 * i1 * i1 * i1 + b;
        double i2 = i1 - 2;
        return -c / 2 * (i2 * i2 * i2 * i2 - 2) + b;
    };

    AnimationGenerator QUINTIC_EASEIN = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double i1 = t / d;
        return c * i1 * i1 * i1 * i1 * i1 + b;
    };

    AnimationGenerator QUINTIC_EASEOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double i1 = t / d - 1;
        return c * (i1 * i1 * i1 * i1 * i1 + 1) + b;
    };

    AnimationGenerator QUINTIC_EASEINOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double i1 = t / d * 2;
        if (i1 < 1) return c / 2 * i1 * i1 * i1 * i1 * i1 + b;
        double i2 = i1 - 2;
        return c / 2 * (i2 * i2 * i2 * i2 * i2 + 2) + b;
    };

    AnimationGenerator ELASTIC_EASEIN = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double p;
        double a = c;
        if (t == 0) return b;
        t = t / d;
        if (t == 1) return b + c;
        p = d * 0.3;
        double s;
        if (a < Math.abs(c)) {
            a = c;
            s = p / 4;
        }
        else {
            s = p / (2 * Math.PI) * Math.asin(c / a);
        }

        double i1 = t - 1;
        return -(a * Math.pow(2, 10 * i1) * Math.sin((i1 * d - s) * (2 * Math.PI) / p)) + b;
    };

    AnimationGenerator ELASTIC_EASEOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double p;
        double a = c;
        if (t == 0) return b;
        t = t / d;
        if (t == 1) return b + c;
        p = d * 0.3;
        double s;
        if (a < Math.abs(c)) {
            a = c;
            s = p / 4;
        }
        else {
            s = p / (2 * Math.PI) * Math.asin(c / a);
        }
        return a * Math.pow(2, -10 * t) * Math.sin((t * d - s) * (2 * Math.PI) / p) + c + b;
    };

    AnimationGenerator ELASTIC_EASEINOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double p;
        double a = c;
        if (t == 0) return b;
        double i1 = t / d * 2;
        if (i1 == 2) return b + c;
        p = d * (0.3 * 1.5);
        double s;
        if (a < Math.abs(c)) {
            a = c;
            s = p / 4;
        }
        else {
            s = p / (2 * Math.PI) * Math.asin(c / a);
        }

        if (i1 < 1) {
            double i2 = i1 - 1;
            return -0.5 * (a * Math.pow(2, 10 * i2) * Math.sin((i2 * d - s) * (2 * Math.PI) / p)) + b;
        }
        double i3 = i1 - 1;
        return a * Math.pow(2, -10 * i3) * Math.sin((i3 * d - s) * (2 * Math.PI) / p) * 0.5 + c + b;
    };

    AnimationGenerator BACK_EASEIN = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double s = 1.70158;
        double i1 = t / d;
        return c * i1 * i1 * ((s + 1) * i1 - s) + b;
    };

    AnimationGenerator BACK_EASEOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double s = 1.70158;
        double i1 = t / d - 1;
        return c * (i1 * i1 * ((s + 1) * i1 + s) + 1) + b;
    };

    AnimationGenerator BACK_EASEINOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double s = 1.70158;
        double i1 = t / d * 2;
        double i2 = s * 1.525;
        if (i1 < 1) return c / 2 * (i1 * i1 * ((i2 + 1) * i1 - i2)) + b;

        double i3 = i1 - 2;
        double i4 = s * 1.525;
        return c / 2 * (i3 * i3 * ((i4 + 1) * i3 + i4) + 2) + b;
    };

    AnimationGenerator BOUNCE_EASEOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double i1 = t / d;
        if (i1 < 1 / 2.75) return c * (7.5625 * i1 * i1) + b;
        if (i1 < 2 / 2.75) {
            i1 = i1 - (1.5 / 2.75);
            return c * (7.5625 * i1 * i1 + 0.75) + b;
        }
        if (i1 < 2.5 / 2.75) {
            i1 = i1 - (2.25 / 2.75);
            return c * (7.5625 * i1 * i1 + 0.9375) + b;
        }
        i1 = i1 - (2.625 / 2.75);
        return c * (7.5625 * i1 * i1 + 0.984375);
    };

    AnimationGenerator BOUNCE_EASEIN = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        return c - BOUNCE_EASEOUT.applyAsDouble(new AnimationNode((int) (d - t), (int) d, 0, c)) + b;
    };

    AnimationGenerator BOUNCE_EASEINOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        if (t < d / 2) return BOUNCE_EASEIN.applyAsDouble(new AnimationNode((int) (t * 2), (int) d, 0, c)) * 0.5 + b;
        return BOUNCE_EASEOUT.applyAsDouble(new AnimationNode((int) (t * 2 - d), (int) d, 0, c)) * 0.5 + c * 0.5 + b;
    };
}
