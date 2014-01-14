################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/Client.cpp \
../src/ConnectFrame.cpp \
../src/ConnectionHandler.cpp \
../src/Console.cpp \
../src/DisconnectFrame.cpp \
../src/HTMLwrite.cpp \
../src/SendFrame.cpp \
../src/StompFrame.cpp \
../src/SubscribeFrame.cpp \
../src/UnsubscribeFrame.cpp 

OBJS += \
./src/Client.o \
./src/ConnectFrame.o \
./src/ConnectionHandler.o \
./src/Console.o \
./src/DisconnectFrame.o \
./src/HTMLwrite.o \
./src/SendFrame.o \
./src/StompFrame.o \
./src/SubscribeFrame.o \
./src/UnsubscribeFrame.o 

CPP_DEPS += \
./src/Client.d \
./src/ConnectFrame.d \
./src/ConnectionHandler.d \
./src/Console.d \
./src/DisconnectFrame.d \
./src/HTMLwrite.d \
./src/SendFrame.d \
./src/StompFrame.d \
./src/SubscribeFrame.d \
./src/UnsubscribeFrame.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -I/usr/include/boost -I/usr/include -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


