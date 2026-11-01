
#ifndef __LIGHTING__
#define __LIGHTING__
#include <bgfx_shader.sh>

float computeDiffuseLightingIntensity(vec3 normalDirection, vec3 lightDirection)
{
    return max(0, dot(normalDirection, lightDirection));
}

float computeAmbiantLighting()
{
    return 0.2;
}

vec4 computeLightedColor(vec3 normalDirection, vec4 unlightedColor)
{
    vec3 lightDirection = normalize(vec3(1.0,1.0,0.0));

    float ambiantLight = computeAmbiantLighting();

    float diffuseLighting = computeDiffuseLightingIntensity(normalDirection, lightDirection);

    float lightingIntensity = (ambiantLight + diffuseLighting) / (1.0 + ambiantLight);

    return vec4(lightingIntensity * unlightedColor.rgb, unlightedColor.a);
}

#endif // __LIGHTING__