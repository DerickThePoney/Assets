$input v_uv0

#include <bgfx_shader.sh>

SAMPLER2D(s_InitialTexture,  0);

uniform vec4 u_SelectionColor;

void main()
{
    vec2 initTexSize = bgfxTextureSize(s_InitialTexture, 0);
    vec2 texelSize = 1.0/initTexSize;

    float currentA = texture2D(s_InitialTexture, v_uv0).a;

    float alpha = 0;

    if(currentA > 0)
    {
        bool found = false;
        for(int i = -1; i < 2 && !found; ++i)
        {
            for(int j = -1; j < 2 && !found; ++j)
            {
                float thisA = texture2D(s_InitialTexture, v_uv0 - vec2(i * texelSize.x, j * texelSize.y)).a;
                if(thisA > 0.0 && thisA != currentA)
                {
                    alpha = currentA;
                    found = true;
                }
            }
        }
    }
    else
    {  
        for(int i = -3; i < 4; ++i)
        {
            for(int j = -3; j < 4; ++j)
            {
                alpha = max(alpha, texture2D(s_InitialTexture, v_uv0 - vec2(i * texelSize.x, j * texelSize.y)).a);
            }
        }
    }
	gl_FragColor = u_SelectionColor;
    gl_FragColor.a = alpha;
}