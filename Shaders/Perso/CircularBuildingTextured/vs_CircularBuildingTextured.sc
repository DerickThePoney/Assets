$input a_position, a_normal, a_texcoord0
$output v_uv0, v_normal

#include <bgfx_shader.sh>
#include "../Common/CircularCoodinates.sh"

void main()
{
    //Get the data
    CircularData data = ComputeCircularData(a_position, a_normal);

    // multiply by u_viewProj to get the projected point
	gl_Position = mul(u_viewProj, data.ObjectPosition);
    v_normal = data.WorldNormal;
	v_uv0 = a_texcoord0;
}