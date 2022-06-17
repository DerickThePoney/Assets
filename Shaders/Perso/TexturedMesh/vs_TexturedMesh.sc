$input a_position, a_normal, a_texcoord0
$output v_uv0, v_normal

#include <bgfx_shader.sh>

void main()
{
	vec4 position4 = vec4(a_position, 1.0);
	gl_Position = mul(u_modelViewProj, position4 );
	v_uv0 = a_texcoord0;
	v_normal = mul(u_model[0], vec4(a_normal, 0.0));
}