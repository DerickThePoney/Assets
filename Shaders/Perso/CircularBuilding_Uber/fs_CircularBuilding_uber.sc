#ifndef FLATCOLOR
$input v_color0, v_normal
#else
$input v_color0
#endif

#include <bgfx_shader.sh>

#ifndef FLATCOLOR
#include "../Common/Lighting.sh"
#endif

void main()
{
    #ifndef FLATCOLOR
    gl_FragColor = computeLightedColor(normalize(v_normal), v_color0);
    #else
    gl_FragColor = v_color0;
    #endif
}