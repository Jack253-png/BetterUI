#version 120

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

float sdfCircle2(vec2 st, vec2 center, float radius) {
    float blur = 0.002;

    //float pct = distance(st, center);//计算任意点到圆心的距离

    vec2 tC = st - center; //计算圆心到任意点的向量
    //float pct = length(tC);//使用length函数求出长度
    float pct = sqrt(tC.x*tC.x+tC.y*tC.y);//使用开平方的方法求出长度

    return 1.0-smoothstep(radius,radius+blur,pct);
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

    x1 += Radius;
    x2 -= Radius;
    y1 += Radius;
    y2 -= Radius;

    for (float xtemp = x1; xtemp <= x2; xtemp += radiusr/2) {
        for (float ytemp = y1; ytemp <= y2; ytemp += radiusr/2) {
            float v = sdfCircle2(texCoord * InSize, vec2(xtemp, InSize[1] - ytemp), radiusr);
            if (v == 1) {
                gl_FragColor = mix(texture2D(DiffuseSampler, texCoord), Color, v == 1 ? Opacity : 0);
                gl_FragColor.w = 1.0;
                return;
            }
        }
    }

    gl_FragColor = texture2D(DiffuseSampler, texCoord);
    gl_FragColor.w = 1.0;
}