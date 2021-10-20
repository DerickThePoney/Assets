$input v_uv0

#include <bgfx_shader.sh>
#include "../../Common/Picking.sh"

SAMPLER2D(s_InitialTexture,  0);



void main()
{
    vec2 initTexSize = bgfxTextureSize(s_InitialTexture, 0);
    vec2 texelSize = 1.0/initTexSize;

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
	gl_FragColor = alpha * u_PickingId;
}