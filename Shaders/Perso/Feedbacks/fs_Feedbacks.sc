$input v_color0, v_interPosition

#include <bgfx_shader.sh>

vec4 u_circleRadius;

void main()
{
    float l = length(v_interPosition.xyz);
    float s_m = sign(l - u_circleRadius.y);
    float s_M = sign(u_circleRadius.x - l);
    float mult = (s_m * s_M) > 0.0;
	gl_FragColor = mult * v_color0;
}