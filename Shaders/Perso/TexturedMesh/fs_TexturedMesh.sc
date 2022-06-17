$input v_uv0, v_normal

#include <bgfx_shader.sh>

#include "../Common/Lighting.sh"

SAMPLER2D(s_diffuseColor,  0);

void main()
{
	vec4 color =  texture2D(s_diffuseColor, v_uv0);
	gl_FragColor = computeLightedColor(v_normal, color);
}