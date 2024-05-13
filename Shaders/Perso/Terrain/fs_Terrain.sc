$input v_uv0, v_normal, v_color0

#include <bgfx_shader.sh>
#include "../Common/Lighting.sh"

void main()
{
	gl_FragColor = computeLightedColor(normalize(v_normal), vec4(0,1,0,1));
}