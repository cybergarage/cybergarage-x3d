set JDK=c:\jdk1.2\
cl -I%JDK%include -I%JDK%include\win32 -LD Joystick.cpp -LD winmm.lib -Fejoystick.dll