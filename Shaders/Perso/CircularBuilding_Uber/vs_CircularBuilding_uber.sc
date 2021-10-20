#ifndef FLATCOLOR
$input a_position, a_normal, a_color0
#else
$input a_position, a_normal
#endif

#ifndef FLATCOLOR
$output v_color0, v_normal
#else
$output v_color0
#endif

#include <bgfx_shader.sh>
#include "../Common/CircularCoodinates.sh"

#ifdef FLATCOLOR
#include "../Common/Picking.sh"
#endif

void main()
{
    //Get the data
    CircularData data = ComputeCircularData(a_position, a_normal);

    // multiply by u_viewProj to get the projected point
    gl_Position = mul(u_viewProj, data.ObjectPosition);
    #ifndef FLATCOLOR
    v_normal = data.WorldNormal;
	v_color0 = a_color0;
    #else
    v_color0 = u_PickingId;
    #endif
}