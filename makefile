JFLAGS = -g
JC = javac
JCR = java

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Program2.java \
	ValveOpener.java \
	RandomSelector.java \
	OddSelector.java

MAIN = Program2

default: classes run

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	@$(JCR) $(MAIN)

clean:
	$(RM) *.class *~
