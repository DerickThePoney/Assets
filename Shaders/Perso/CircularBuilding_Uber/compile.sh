#!/bin/bash
../../../../External/BGFX/ToolBinaries/shadercRelease.exe --varyingdef varying.def.sc -f vs_CircularBuilding_Uber.sc -o vs_CircularBuilding_Uber_FLATCOLOR.bin -p vs_5_0 -i ../../../../External/BGFX/bgfx/src/ --type vertex --platform windows -O 3 -V --define FLATCOLOR

../../../../External/BGFX/ToolBinaries/shadercRelease.exe --varyingdef varying.def.sc -f fs_CircularBuilding_Uber.sc -o fs_CircularBuilding_Uber_FLATCOLOR.bin -p ps_5_0 -i ../../../../External/BGFX/bgfx/src/ --type fragment --platform windows -O 3 -V --define FLATCOLOR