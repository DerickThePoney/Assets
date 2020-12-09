$input a_position, a_color0
$output v_color0, v_interPosition

#include <bgfx_shader.sh>

void main()
{
	v_interPosition = vec4(a_position, 1.0);
	gl_Position = mul(u_modelViewProj, v_interPosition);
	v_color0 = a_color0;
}