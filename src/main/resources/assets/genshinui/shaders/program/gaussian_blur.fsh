#version 120
#extension GL_EXT_gpu_shader4 : enable

uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;

varying vec2 texCoord;
varying vec2 oneTexel;

uniform vec2 InSize;
uniform vec2 OutSize;

void main() {
    int samples = 20;
    vec4 O = vec4(0.0);
    float r = float(samples)*0.5;
    float sigma = r*0.5;
    float f = 1./(6.28318530718*sigma*sigma);

    int s2 = samples*samples;
    for (int i = 0; i<s2; i++) {
        vec2 d = vec2(i%samples, i/samples) - r;
        O += texture2D(DiffuseSampler, texCoord + oneTexel * d) * exp(-0.5 * dot(d/=sigma, d)) * f;
    }

    gl_FragColor = O/O.a;
    gl_FragColor.w = 1.0;
}