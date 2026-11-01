$input a_position
$output v_color0

#include <bgfx_shader.sh>
#include "../Common/Picking.sh"

void main()
{
	vec4 position4 = vec4(a_position, 1.0);
	gl_Position = mul(u_modelViewProj, position4 );
	v_color0 = u_PickingId;

	
}