#ifndef FLATCOLOR
    #ifdef SKINNING
        $input a_position, a_normal, a_color0, a_indices
    #else
        $input a_position, a_normal, a_color0
    #endif
#else
    #ifdef SKINNING
        $input a_position, a_normal, a_indices
    #else
        $input a_position, a_normal
    #endif
#endif