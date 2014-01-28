
CC := g++
SRCDIR := src
BUILDDIR := bin
TARGET := bin/client
SRCEXT := cpp
SOURCES := $(shell find $(SRCDIR) -type f -name *.$(SRCEXT))
OBJECTS := $(patsubst $(SRCDIR)/%,$(BUILDDIR)/%,$(SOURCES:.$(SRCEXT)=.o))
CFLAGS := -g -Wall -Weffc++
LFLAGS := -L/usr/local/lib
INC := -Linclude
 
$(TARGET): $(OBJECTS)
	@echo " Linking..."
	@echo " $(CC) $^ -o $(TARGET) $(LFLAGS)"; $(CC) $^ -o $(TARGET) $(LFLAGS) -lboost_system -lboost_thread -L/usr/lib -lopencv_core -lopencv_imgproc -lopencv_highgui -lopencv_objdetect
 
$(BUILDDIR)/%.o: $(SRCDIR)/%.$(SRCEXT)
	@mkdir -p $(BUILDDIR)
	@echo " $(CC) $(CFLAGS) $(INC) -c -o $@ $<"; $(CC) $(CFLAGS) $(INC) -c -o $@ $<
 
clean:
	@echo " Cleaning...";
	@echo " $(RM) -r $(BUILDDIR) $(TARGET)"; $(RM) -r $(BUILDDIR) $(TARGET)
