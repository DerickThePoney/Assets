$input a_position, a_color0, i_data0, i_data1, i_data2, i_data3
$output v_color0


#include <bgfx_shader.sh>

uniform vec4 u_color;

void main()
{
	mat4 model = mtxFromCols(i_data0, i_data1, i_data2, i_data3);

	vec4 position4 = vec4(a_position, 1.0);
	vec4 worldPos = mul(model, position4);
	gl_Position = mul(u_viewProj, worldPos);
	v_color0 = a_color0 * u_color;
}