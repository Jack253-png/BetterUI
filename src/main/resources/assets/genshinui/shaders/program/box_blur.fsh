#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;

uniform vec2 BlurDir;
uniform float Radius;

out vec4 fragColor;

void main() {
    vec4 blurred = vec4(0.0);
    float totalStrength = 0.0;
    float totalAlpha = 0.0;
    float totalSamples = 0.0;
    float progRadius = floor(Radius);
    for(float r = -progRadius; r <= progRadius; r += 1.0) {
        vec4 sample2 = texture(DiffuseSampler, texCoord + oneTexel * r * BlurDir);

        // Accumulate average alpha
        totalAlpha = totalAlpha + sample2.a;
        totalSamples = totalSamples + 1.0;

        // Accumulate smoothed blur
        float strength = 1.0 - abs(r / progRadius);
        totalStrength = totalStrength + strength;
        blurred = blurred + sample2;
    }
    fragColor = vec4(blurred.rgb / (progRadius * 2.0 + 1.0), totalAlpha);
}