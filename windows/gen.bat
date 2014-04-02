@echo off
set protoc_exe=%~dp0protoc.exe
%protoc_exe% -I..\proto --javamicro_out=.\ ..\proto\field_test.proto
pause
