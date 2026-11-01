$input v_uv0

#include <bgfx_shader.sh>

SAMPLER2D(s_GeometryTexture,  0);
SAMPLER2D(s_FeedbackTexture,  1);

void main()
{
	vec4 colorGeometry = texture2D(s_GeometryTexture, v_uv0);
    vec4 colorFeedback = texture2D(s_FeedbackTexture, v_uv0);

    gl_FragColor = vec4(colorFeedback.xyz + (1 - colorFeedback.a) * colorGeometry.xyz , 1.0);
}