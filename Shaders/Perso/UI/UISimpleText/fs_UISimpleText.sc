$input v_color0, v_texcoord0

#include <bgfx_shader.sh>

SAMPLER2D(s_fontTexture, 0);

void main()
{
	float texel = texture2D(s_fontTexture, v_texcoord0).a;
	gl_FragColor = vec4(v_color0.rgb*texel, v_color0.a * texel);
}
