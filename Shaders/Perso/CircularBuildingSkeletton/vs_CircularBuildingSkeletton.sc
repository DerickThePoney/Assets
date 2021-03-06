$input a_position, a_normal, a_color0, a_indices
$output v_color0, v_normal

#include <bgfx_shader.sh>
#include "../Common/CircularCoodinates.sh"

uniform mat4 u_skinningMatrices[BGFX_CONFIG_MAX_BONES];

void main()
{
    vec3 skinnedVextexPosition = mul(u_skinningMatrices[a_indices.x], vec4(a_position, 1.0));
    vec3 skinnedNormal = mul(u_skinningMatrices[a_indices.x], vec4(a_normal, 0.0));

    //Get the data
    CircularData data = ComputeCircularData(skinnedVextexPosition, skinnedNormal);

    // multiply by u_viewProj to get the projected point
    gl_Position = mul(u_viewProj, data.ObjectPosition);
    v_normal = data.WorldNormal;
	v_color0 = a_color0;
}