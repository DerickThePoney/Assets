$input v_color0, v_interPosition

#include <bgfx_shader.sh>

vec4 u_innerOuterCircles;
vec4 u_arcAngle;

float GetMultiplierForCircle(float distance, vec2 circleParams)
{
    float s_m = sign(distance - circleParams.y);
    float s_M = sign(circleParams.x - distance);
    return (s_m * s_M) > 0.0;
}

float GetRadius()
{
    // get the radius
    return 0.25 * (u_innerOuterCircles.x + u_innerOuterCircles.y + u_innerOuterCircles.z + u_innerOuterCircles.w);
}

float GetMultiplierForRadialLines(vec2 pos2D, vec2 minMaxDistance, float distance)
{
    // get angle
    float angle = atan2(pos2D.y, pos2D.x);

    float p = u_arcAngle.x;
    float a = (angle + 3.1416) / p;
    float a_i = floor(a);

    float multDistance = GetMultiplierForCircle(distance, minMaxDistance);

    return multDistance * ((a-a_i) - 0.99 > 0);
}

void main()
{
    float l = length(v_interPosition.xz);
    float multSmallCircle = GetMultiplierForCircle(l, u_innerOuterCircles.xy);
    float multBigCircle = GetMultiplierForCircle(l, u_innerOuterCircles.zw);

    float multLine = GetMultiplierForRadialLines(v_interPosition.xz, u_innerOuterCircles.xw, l);

    float mult = max(max(multLine, multSmallCircle), multBigCircle);
	gl_FragColor = mult * v_color0;
}