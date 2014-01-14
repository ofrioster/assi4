################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src2/Client.cpp \
../src2/ConnectFrame.cpp \
../src2/ConnectionHandler.cpp \
../src2/Console.cpp \
../src2/DisconnectFrame.cpp \
../src2/Follower.cpp \
../src2/Header.cpp \
../src2/Network.cpp \
../src2/SendFrame.cpp \
../src2/StompFrame.cpp \
../src2/SubscribeFrame.cpp \
../src2/UnSubscribeFrame.cpp \
../src2/clientSide.cpp 

OBJS += \
./src2/Client.o \
./src2/ConnectFrame.o \
./src2/ConnectionHandler.o \
./src2/Console.o \
./src2/DisconnectFrame.o \
./src2/Follower.o \
./src2/Header.o \
./src2/Network.o \
./src2/SendFrame.o \
./src2/StompFrame.o \
./src2/SubscribeFrame.o \
./src2/UnSubscribeFrame.o \
./src2/clientSide.o 

CPP_DEPS += \
./src2/Client.d \
./src2/ConnectFrame.d \
./src2/ConnectionHandler.d \
./src2/Console.d \
./src2/DisconnectFrame.d \
./src2/Follower.d \
./src2/Header.d \
./src2/Network.d \
./src2/SendFrame.d \
./src2/StompFrame.d \
./src2/SubscribeFrame.d \
./src2/UnSubscribeFrame.d \
./src2/clientSide.d 


# Each subdirectory must supply rules for building sources it contributes
src2/%.o: ../src2/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -I/usr/include/boost -I/usr/include -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


