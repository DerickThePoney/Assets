#define SKINNING
$input a_position, a_normal, a_color0, a_indices
$output v_color0, v_normal

#include <bgfx_shader.sh>
#include "../Common/CircularCoodinates.sh"
#include "../Common/SkinnedVertexAttributes.sh"

void main()
{
    VertexAttributes attributes = GetSkinnedAttributes(a_position, a_normal, a_indices.x);

    //Get the data
    CircularData data = ComputeCircularData(attributes.VertexPosition, attributes.VertexNormal);

    // multiply by u_viewProj to get the projected point
    gl_Position = mul(u_viewProj, data.ObjectPosition);
    v_normal = data.WorldNormal;
	v_color0 = a_color0;
}