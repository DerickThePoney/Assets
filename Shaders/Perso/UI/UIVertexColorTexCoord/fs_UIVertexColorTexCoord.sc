$input v_color0, v_uv0

#include <bgfx_shader.sh>

SAMPLER2D(s_uiTexture,  0);

void main()
{
	gl_FragColor = v_color0 * texture2D(s_uiTexture, v_uv0);
}