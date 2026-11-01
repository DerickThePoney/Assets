#include "../Common/VertexShaderInput.sh"
#include "../Common/VertexShaderOutput.sh"


#include <bgfx_shader.sh>
#include "../Common/CircularCoodinates.sh"

#ifdef SKINNING
#include "../Common/SkinnedVertexAttributes.sh"
#endif

#ifdef FLATCOLOR
#include "../Common/Picking.sh"
#endif

void main()
{
    vec3 position = a_position;
    vec3 normal = a_normal;
#ifdef SKINNING
    VertexAttributes attributes = GetSkinnedAttributes(a_position, a_normal, a_indices.x);
    position = attributes.VertexPosition;
    normal = attributes.VertexNormal;
#endif

    //Get the data
    CircularData data = ComputeCircularData(position, normal);

    // multiply by u_viewProj to get the projected point
    gl_Position = mul(u_viewProj, data.ObjectPosition);
    #ifndef FLATCOLOR
    v_normal = data.WorldNormal;
	v_color0 = a_color0;
    #else
    v_color0 = u_PickingId;
    #endif
}