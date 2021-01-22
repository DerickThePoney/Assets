$input v_color0, v_normal

#include <bgfx_shader.sh>
#include "../Common/Lighting.sh"

void main()
{
    gl_FragColor = computeLightedColor(normalize(v_normal), v_color0);
}