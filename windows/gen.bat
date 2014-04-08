@echo off
set protoc_exe=%~dp0..\protobuf_project\protobuf\vsprojects\Release\protoc.exe
%protoc_exe% -I..\proto --javamicro_out=java_simple_parsefrom=true,java_use_json=true,android_use_parcel=true:..\ProtobufJsonDemo\src ..\proto\field_test.proto
pause
