$input a_position, a_normal
$output v_color0

#include <bgfx_shader.sh>
#include "../Common/CircularCoodinates.sh"
#include "../Common/Picking.sh"

void main()
{
    //Get the data
    CircularData data = ComputeCircularData(a_position, a_normal);

    // multiply by u_viewProj to get the projected point
    gl_Position = mul(u_viewProj, data.ObjectPosition);
	v_color0 = u_PickingId;
}