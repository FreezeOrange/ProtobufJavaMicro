@echo off
set protoc_exe=%~dp0protoc.exe
%protoc_exe% -I..\proto --javamicro_out=..\ProtobufJsonDemo\src ..\proto\field_test.proto
pause
