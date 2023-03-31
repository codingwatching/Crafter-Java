#version 410 core

// Frag is for tri texture mapping.
// This shader is a bit fancier

in vec2 outputTextureCoordinate;
in vec4 newColoring;

out vec4 fragColor;

uniform sampler2D textureSampler;


void main() {

    vec4 pixelColor = texture(textureSampler, outputTextureCoordinate);

    float alpha = newColoring.w < pixelColor.w ? newColoring.w : pixelColor.w;

    if (alpha <= 0.0) {
         discard;
    }

    vec4 rgba = vec4 (newColoring.x, newColoring.y, newColoring.z, alpha);

    fragColor = rgba;
}