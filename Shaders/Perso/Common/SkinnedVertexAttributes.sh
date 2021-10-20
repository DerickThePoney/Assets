#ifndef __VERTATTR__
#define __VERTATTR__
#include <bgfx_shader.sh>

#ifdef SKINNING
struct VertexAttributes
{
	vec3 VertexPosition;
	vec3 VertexNormal;
};

uniform mat4 u_skinningMatrices[BGFX_CONFIG_MAX_BONES];

VertexAttributes GetSkinnedAttributes(vec3 position,vec3 normal, int indice)
{
	VertexAttributes res;
	res.VertexPosition = mul(u_skinningMatrices[indice], vec4(position, 1.0)).xyz;
    res.VertexNormal = mul(u_skinningMatrices[indice], vec4(normal, 0.0)).xyz;
    return res;
}
#endif

#endif // __VERTATTR__