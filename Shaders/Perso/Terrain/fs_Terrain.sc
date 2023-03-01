$input v_uv0, v_color0

#include <bgfx_shader.sh>

vec4 u_LoDColor;

void main()
{
	gl_FragColor = u_LoDColor; //v_color0;
}