#!/bin/bash
../../../../External/BGFX/ToolBinaries/shadercRelease.exe --varyingdef varying.def.sc -f vs_UIVertexColorTexCoord.sc -o vs_UIVertexColorTexCoord.bin -p vs_5_0 -i ../../../../External/BGFX/bgfx/src/ --type vertex --platform windows -O 3 -V

../../../../External/BGFX/ToolBinaries/shadercRelease.exe --varyingdef varying.def.sc -f fs_UIVertexColorTexCoord.sc -o fs_UIVertexColorTexCoord.bin -p ps_5_0 -i ../../../../External/BGFX/bgfx/src/ --type fragment --platform windows -O 3 -V