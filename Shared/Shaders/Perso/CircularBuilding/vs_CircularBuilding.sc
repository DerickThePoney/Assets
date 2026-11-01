$input a_position, a_normal, a_color0
$output v_color0, v_normal

#include <bgfx_shader.sh>
#include "../Common/CircularCoodinates.sh"

void main()
{
    //Get the data
    CircularData data = ComputeCircularData(a_position, a_normal);

    // multiply by u_viewProj to get the projected point
    gl_Position = mul(u_viewProj, data.ObjectPosition);
    v_normal = data.WorldNormal;
	v_color0 = a_color0;
}