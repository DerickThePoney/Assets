$input a_position, a_texcoord0, i_data0, i_data1, i_data2
$output v_uv0, v_normal, v_color0

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
	vec2 uvToUseForSampling = saturate(inPositionWorld.xz / u_gridDim.zw);
	
	return uvToUseForSampling;
}

vec4 ComputeWorldGridPosition(vec3 inPosition, vec4 bbBox, vec4 LoD)
{
	float posX = bbBox.x + (bbBox.z - bbBox.x) * inPosition.x;
	float posZ = bbBox.y + (bbBox.w - bbBox.y) * inPosition.z;

	vec4 res = ModifyWorldPositionBasedOnGridPosition(inPosition, vec4(posX, 0.0, posZ, 1.0), bbBox, LoD); // vec4(posX, 0.1, posZ, 1.0); //

	return res;
}

vec4 ComputeHeightOfSample(vec4 position, float LoD)
{
	vec2 uvToUseForSampling = GetUVForTextureSampling(position);
	float height = texture2DLod(s_terrainHeightMap, uvToUseForSampling, 0).x;
	vec4 result = position;
	result.y = height * 50.0;
	return result;
}

vec3 ComputeNormal(vec3 inPosition, vec4 bbBox, vec4 LoD)
{
	vec2 increment = vec2(1,1) / u_gridDim.xy;

	vec4 positionN1 = ComputeWorldGridPosition(inPosition - vec3(increment.x,0,0), bbBox, LoD);
	vec4 positionN2 = ComputeWorldGridPosition(inPosition + vec3(increment.x,0,0), bbBox, LoD);
	vec4 positionN3 = ComputeWorldGridPosition(inPosition - vec3(0,0,increment.y), bbBox, LoD);
	vec4 positionN4 = ComputeWorldGridPosition(inPosition + vec3(0,0,increment.y), bbBox, LoD);

	positionN1 = ComputeHeightOfSample(positionN1, LoD.x);
	positionN2 = ComputeHeightOfSample(positionN2, LoD.x);
	positionN3 = ComputeHeightOfSample(positionN3, LoD.x);
	positionN4 = ComputeHeightOfSample(positionN4, LoD.x);
	
	return normalize(vec3((positionN1.y - positionN2.y)/(2*increment.x), (positionN3.y - positionN4.y)/(2*increment.x), 1));
}

void main()
{
	
	vec4 position4 = ComputeWorldGridPosition(a_position, i_data0, i_data2);

	position4 = ComputeHeightOfSample(position4, i_data2.x);
	
	gl_Position = mul(u_modelViewProj, position4 );	
	
	vec3 normal = ComputeNormal(a_position, i_data0, i_data2.x);
	v_normal = normal;
	v_uv0 = a_texcoord0;
	v_color0 = i_data1; //vec4(uvToUseForSampling, 0.0,1.0);
}