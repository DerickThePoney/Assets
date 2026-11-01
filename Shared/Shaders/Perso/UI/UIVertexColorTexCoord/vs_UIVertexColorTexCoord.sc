$input a_position, a_color0, a_texcoord0
$output v_color0, v_uv0

#include <bgfx_shader.sh>

void main()
{
	vec4 position4 = vec4(a_position.xy, 0.0, 1.0);
	gl_Position = mul(u_modelViewProj, position4 );
	v_color0 = a_color0;
	v_uv0 = vec2(a_texcoord0.x, a_texcoord0.y);
}