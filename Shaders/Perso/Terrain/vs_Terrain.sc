$input a_position, a_texcoord0, i_data0, i_data1, i_data2
$output v_uv0, v_color0

#include <bgfx_shader.sh>

uniform vec4 u_gridDim;
uniform vec4 u_LoDDistances[3];

float GetLodDistanceToUse(int lod)
{
	int vec4ToUse = lod / 4;
	int idInVec4 = lod & 0x3;

	return u_LoDDistances[vec4ToUse][idInVec4];
}

float GetMorphValue(float dist, vec4 LoD)
{
	int currentLod = (int)LoD.x;

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

	vec3 newVertexPos = inPosition - vec4(partToRemove.x, 0, partToRemove.y, 0);

	float posX = bbBox.x + (bbBox.z - bbBox.x) * newVertexPos.x;
	float posZ = bbBox.y + (bbBox.w - bbBox.y) * newVertexPos.z;
	
    return vec4(posX, 0.0, posZ, 1.0);
}

vec4 ComputeWorldGridPosition(vec3 inPosition, vec4 bbBox, vec4 LoD)
{
	float posX = bbBox.x + (bbBox.z - bbBox.x) * inPosition.x;
	float posZ = bbBox.y + (bbBox.w - bbBox.y) * inPosition.z;

	return ModifyWorldPositionBasedOnGridPosition(inPosition, vec4(posX, 0.0, posZ, 1.0), bbBox, LoD); // vec4(posX, 0.1, posZ, 1.0); //
}

void main()
{
	vec4 position4 = ComputeWorldGridPosition(a_position, i_data0, i_data2);
	
	gl_Position = mul(u_modelViewProj, position4 );
	v_uv0 = a_texcoord0;
	v_color0 = i_data1;
}