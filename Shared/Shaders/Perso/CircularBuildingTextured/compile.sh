#!/bin/bash
../../../../External/BGFX/ToolBinaries/shadercRelease.exe --varyingdef varying.def.sc -f vs_CircularBuildingTextured.sc -o vs_CircularBuildingTextured.bin -p vs_5_0 -i ../../../../External/BGFX/bgfx/src/ --type vertex --platform windows -O 3 -V

../../../../External/BGFX/ToolBinaries/shadercRelease.exe --varyingdef varying.def.sc -f fs_CircularBuildingTextured.sc -o fs_CircularBuildingTextured.bin -p ps_5_0 -i ../../../../External/BGFX/bgfx/src/ --type fragment --platform windows -O 3 -V