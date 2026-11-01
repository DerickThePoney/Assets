$input v_uv0, v_color0

#include <bgfx_shader.sh>
#include "../Common/Lighting.sh"

uniform vec4 u_gridDim;
SAMPLER2D(s_terrainNormalMap,  1);

vec2 GetUVForTextureSampling(vec4 inPositionWorld)
{
	vec2 uvToUseForSampling = saturate(inPositionWorld.xz / u_gridDim.zw);
	
	return uvToUseForSampling;
}

void main()
{
	vec2 uvToUseForSampling = GetUVForTextureSampling(v_uv0);
	vec3 normal;
	normal.xy = texture2D(s_terrainNormalMap, uvToUseForSampling).xy * 2.0 - 1.0;
	normal.z = sqrt(1.0 - dot(normal.xy, normal.xy) );
	gl_FragColor = computeLightedColor(normalize(normal), vec4(0,1,0,1));
}