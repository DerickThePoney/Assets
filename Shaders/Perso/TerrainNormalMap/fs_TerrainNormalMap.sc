$input v_uv0, v_color0

#include <bgfx_shader.sh>
#include "../Common/Lighting.sh"

SAMPLER2D(s_terrainNormalMap,  1);
void main()
{
	vec3 normal;
	normal.xy = texture2D(s_terrainNormalMap, v_uv0).xy * 2.0 - 1.0;
	normal.z = sqrt(1.0 - dot(normal.xy, normal.xy) );
	gl_FragColor = computeLightedColor(normalize(normal), vec4(0,1,0,1));
}