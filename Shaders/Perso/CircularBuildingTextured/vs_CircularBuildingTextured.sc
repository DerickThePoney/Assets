$input a_position, a_texcoord0
$output v_uv0

#include <bgfx_shader.sh>

const float arcLength = 2.0;
const float buildingLength = 2.0;
const float buildingWidth = 1.0;

void main()
{
    //reconstruct circular world matrix
    vec4 pos = vec4(u_model[0][0].w, u_model[0][1].w, u_model[0][2].w,1.0);
    vec4 Z = normalize(vec4(pos.x, 0.0, pos.z,0.0));
    vec4 X = vec4(-Z.z, 0.0, Z.x, 0.0);
    vec4 Y = vec4(0.0, 1.0, 0.0, 0.0);
    mat4 modelMat = mtxFromCols(X,Y,Z, pos);

    // get radius
    float r = length(pos.xz);

    //Compute total arc angle at distance r using arc = r*theta
    float thetaArc = 2.0 / r;

    // Project the point onto the x axis and compute interpolant for angle
    float interp = 0.5 * (a_position.x + 1.0);

    // current object angle (ouch trig)
    float thetaObject = atan2(pos.z, pos.x);

    // Compute current angle for the vertex
    float thetaVertex = thetaObject + lerp(0.5*thetaArc , -0.5 *thetaArc, interp);

    // compute radius for the vertex
    float rVertex = (r + a_position.z);

    // compute the world position (ouch could be optimised)
	vec4 positionWorld = mul(modelMat, vec4(a_position, 1.0));

    // Compute vertex world circular position
    vec4 posCircular = vec4(rVertex * cos(thetaVertex), positionWorld.y, rVertex * sin(thetaVertex), 1.0);

    // multiply by u_viewProj to get the projected point
	gl_Position = mul(u_viewProj, posCircular);
	v_uv0 = a_texcoord0;
}