$input a_position, a_texcoord0, i_data0, i_data1, i_data2
$output v_uv0, v_color0

#include <bgfx_shader.sh>

SAMPLER2D(s_terrainHeightMap,  0);

uniform vec4 u_gridDim;
uniform vec4 u_LoDDistances[3];

float GetLodDistanceToUse(uint lod)
{
	uint vec4ToUse = lod / 4;
	uint idInVec4 = lod & 0x3;

	return u_LoDDistances[vec4ToUse][idInVec4];
}

float GetMorphValue(float dist, vec4 LoD)
{
	uint currentLod = (uint)LoD.x;

	float low = 0.0;
    if(currentLod != 0){
        low = GetLodDistanceToUse(currentLod - 1);
    }
    float high = GetLodDistanceToUse(currentLod);
    float delta = high - low;
    float factor = (dist - low) / delta;
    return clamp(factor / 0.5 - 1.0, 0.0, 1.0);
}

vec4 ModifyWorldPositionBasedOnGridPosition(vec3 inPosition, vec4 inPositionWorld, vec4 bbBox, vec4 LoD)
{
	vec4 viewPos = mul(u_view, inPositionWorld);
	float dist = length(viewPos.xyz);

	float morphK = GetMorphValue(dist, LoD);

	vec2 gridPos = inPosition.xz;
	vec2 gridDim = u_gridDim.xy;
	vec2 fracPart = frac( gridPos.xy * gridDim.xy * 0.5 ) * 2.0 / gridDim.xy;

	vec2 quadScale = vec2((bbBox.z - bbBox.x) , (bbBox.w - bbBox.y));
	vec2 partToRemove = fracPart * morphK;

	vec3 newVertexPos = inPosition - vec3(partToRemove.x, 0.0, partToRemove.y);

	float posX = bbBox.x + (bbBox.z - bbBox.x) * newVertexPos.x;
	float posZ = bbBox.y + (bbBox.w - bbBox.y) * newVertexPos.z;
	
    return vec4(posX, 1.0, posZ, 1.0);
}

vec2 GetUVForTextureSampling(vec4 inPositionWorld)
{
	vec2 uvToUseForSampling = inPositionWorld.xz / u_gridDim.zw;
	
	return uvToUseForSampling;
}

vec4 ComputeWorldGridPosition(vec3 inPosition, vec4 bbBox, vec4 LoD)
{
	float posX = bbBox.x + (bbBox.z - bbBox.x) * inPosition.x;
	float posZ = bbBox.y + (bbBox.w - bbBox.y) * inPosition.z;

	vec4 res = ModifyWorldPositionBasedOnGridPosition(inPosition, vec4(posX, 0.0, posZ, 1.0), bbBox, LoD); // vec4(posX, 0.1, posZ, 1.0); //

	/*vec2 minUV = bbBox.xy / u_gridDim.zw;
	vec2 maxUV = bbBox.zw / u_gridDim.zw;

	vec2 uvToUseForSampling = minUV + (maxUV - minUV) * res.xz; //vec2(posX, posZ);
	float height = texture2DLod(s_terrainHeightMap, uvToUseForSampling, 0).x;
	res.y = height * 10.0;*/
	/*

	res.y = height * 100.0;*/

	return res;
}


void main()
{
	vec4 position4 = ComputeWorldGridPosition(a_position, i_data0, i_data2);
	vec2 uvToUseForSampling = GetUVForTextureSampling(position4);
	float height = texture2DLod(s_terrainHeightMap, uvToUseForSampling, 0).x;
	position4.y = height * 50.0;
	
	gl_Position = mul(u_modelViewProj, position4 );

	v_uv0 = a_texcoord0;
	v_color0 = i_data1; //vec4(uvToUseForSampling, 0.0,1.0);
}