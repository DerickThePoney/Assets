#!/bin/bash
../../../../External/BGFX/ToolBinaries/shadercRelease.exe --varyingdef varying.def.sc -f vs_Feedbacks.sc -o vs_Feedbacks.bin -p vs_5_0 -i ../../../../External/BGFX/bgfx/src/ --type vertex --platform windows -O 3 -V

../../../../External/BGFX/ToolBinaries/shadercRelease.exe --varyingdef varying.def.sc -f fs_Feedbacks.sc -o fs_Feedbacks.bin -p ps_5_0 -i ../../../../External/BGFX/bgfx/src/ --type fragment --platform windows -O 3 -V