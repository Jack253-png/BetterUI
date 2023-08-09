#version 120
#extension GL_EXT_gpu_shader4 : enable

uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;

varying vec2 texCoord;
varying vec2 oneTexel;

uniform vec2 InSize;
uniform vec2 OutSize;

uniform float BlendFactor = 0.75;

uniform float Radius = 50;
uniform vec2 Center1 = vec2(0, 0);
uniform vec2 Center2 = vec2(100, 100);
uniform vec4 Color = vec4(0.5, 0.5, 0.5, 1.0);
uniform float Opacity = 0.5;
uniform float BlurSamples = 8.0;
uniform vec2 BlurDir = vec2(1.0, 0.0);

#define PI2 6.2831853072

float dctCircle(vec2 st, vec2 center, float radius) {
    float blur = 0.75;

    float pct = distance(st, center);

    /*vec2 tC = st - center;
    //float pct = length(tC);
    float pct = sqrt(tC.x*tC.x+tC.y*tC.y);*/

    return 1.0-smoothstep(radius,radius+blur,pct);
}

bool inRect(vec2 st, vec2 p1, vec2 p2) {
    float x1 = p1[0];
    float y1 = p1[1];
    float x2 = p2[0];
    float y2 = p2[1];

    float xwrap = 0;
    float ywrap = 0;

    if (x1 > x2) {
        xwrap = x2;
        x2 = x1;
        x1 = xwrap;
    }
    if (y1 > y2) {
        ywrap = y2;
        y2 = y1;
        y1 = ywrap;
    }

    float tx = st[0];
    float ty = st[1];

    return x1 < tx && tx < x2 && y1 < ty && ty < y2;
}

void set(float ot, float rad_x, float rad_y) {
    vec4 resColor = texture2D(DiffuseSampler, texCoord);

    vec4 blurred = vec4(0.0);
    float totalStrength = 0.0;
    float totalAlpha = 0.0;
    float totalSamples = 0.0;
    float progRadius = float(BlurSamples);
    for(float r = -progRadius; r <= progRadius; r += 1.0) {
        vec4 sample2 = texture2D(DiffuseSampler, texCoord + oneTexel * r * BlurDir);

        // Accumulate average alpha
        totalAlpha = totalAlpha + sample2.a;
        totalSamples = totalSamples + 1.0;

        // Accumulate smoothed blur
        float strength = 1.0 - abs(r / progRadius);
        totalStrength = totalStrength + strength;
        blurred = blurred + sample2;
    }
    resColor = vec4(blurred.rgb / (progRadius * 2.0 + 1.0), totalAlpha);

    gl_FragColor = mix(resColor, Color, BlurSamples == 0 ? Opacity * ot : 0);
    gl_FragColor.w = 1.0;
}

void main() {
    float x1 = Center1[0];
    float y1 = Center1[1];
    float x2 = Center2[0];
    float y2 = Center2[1];

    float radiusr = Radius;

    float width = abs(x1 - x2);
    float height = abs(y1 - y2);

    if (Radius > width / 2 || Radius > height / 2) {
        radiusr = min(width, height) / 2;
    }

    float xwrap = 0;
    float ywrap = 0;

    if (x1 > x2) {
        xwrap = x2;
        x2 = x1;
        x1 = xwrap;
    }
    if (y1 > y2) {
        ywrap = y2;
        y2 = y1;
        y1 = ywrap;
    }

    float rct_x1 = x1;
    float rct_x2 = x1 + radiusr;
    float rct_x3 = x2 - radiusr;
    float rct_x4 = x2;

    float rct_y1 = y1;
    float rct_y2 = y1 + radiusr;
    float rct_y3 = y2 - radiusr;
    float rct_y4 = y2;

    x1 += radiusr;
    x2 -= radiusr;
    y1 += radiusr;
    y2 -= radiusr;

    vec2 st = texCoord * InSize;

    float hg = InSize[1];

    if (
    inRect(st, vec2(rct_x2, hg-rct_y2), vec2(rct_x3, hg-rct_y3)) ||
    inRect(st, vec2(rct_x2, hg-rct_y1), vec2(rct_x3, hg-rct_y2)) ||
    inRect(st, vec2(rct_x1, hg-rct_y2), vec2(rct_x2, hg-rct_y3)) ||
    inRect(st, vec2(rct_x3, hg-rct_y2), vec2(rct_x4, hg-rct_y3)) ||
    inRect(st, vec2(rct_x2, hg-rct_y3), vec2(rct_x3, hg-rct_y4))) {
        set(1.0, width, height);
        return;
    }

    float v1 = dctCircle(st, vec2(rct_x2, hg - rct_y2), radiusr);
    if (v1 != 0) {
        set(v1, width, height);
        return;
    }

    float v2 = dctCircle(st, vec2(rct_x2, hg - rct_y3), radiusr);
    if (v2 != 0) {
        set(v2, width, height);
        return;
    }

    float v3 = dctCircle(st, vec2(rct_x3, hg - rct_y2), radiusr);
    if (v3 != 0) {
        set(v3, width, height);
        return;
    }

    float v4 = dctCircle(st, vec2(rct_x3, hg - rct_y3), radiusr);
    if (v4 != 0) {
        set(v4, width, height);
        return;
    }

    gl_FragColor = texture2D(DiffuseSampler, texCoord);
    gl_FragColor.w = 1.0;
}