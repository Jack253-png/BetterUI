#version 120

uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;

varying vec2 texCoord;
varying vec2 oneTexel;

uniform vec2 InSize;
uniform vec2 OutSize;

uniform float BlendFactor = 0.75;

uniform float Radius = 50;
uniform vec2 Center = vec2(100, 100);
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
    float v = sdfCircle2(texCoord * InSize, vec2(Center[0], InSize[1] - Center[1]), Radius);
    gl_FragColor = mix(texture2D(DiffuseSampler, texCoord), Color, v == 1 ? Opacity : 0);
    gl_FragColor.w = 1.0;
}