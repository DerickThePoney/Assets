$input v_uv0

#include <bgfx_shader.sh>

SAMPLER2D(s_InitialTexture,  0);

float s_Filter[] = {0.006,0.061,0.242,0.383,0.242,0.061,0.006};
int s_FilterOffset = 3;

void main()
{
    vec2 initTexSize = bgfxTextureSize(s_InitialTexture, 0);
    vec2 texelSize = 1.0/initTexSize;
    //vec2 texelOffset = vec2(texelSize.x, 0);

    float currentA = texture2D(s_InitialTexture, v_uv0).a;

    if(currentA > 0)
        discard;

    float alpha = 0;

    for(int i = -3; i < 4; ++i)
    {
        for(int j = -3; j < 4; ++j)
        {
            alpha = max(alpha, texture2D(s_InitialTexture, v_uv0 - vec2(i * texelSize.x, j * texelSize.y)).a);
        }
    }
	gl_FragColor = vec4_splat(alpha);
}