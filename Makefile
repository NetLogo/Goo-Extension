ifeq ($(origin NETLOGO), undefined)
  NETLOGO=../..
endif

ifeq ($(origin SCALA_HOME), undefined)
  SCALA_HOME=../..
endif

SRCS=$(wildcard src/*.scala)

goo.jar: $(SRCS) manifest.txt
	mkdir -p classes
	$(SCALA_HOME)/bin/scalac -g -deprecation -unchecked -encoding us-ascii -classpath $(NETLOGO)/NetLogo.jar:$(JARS) -d classes $(SRCS)
	jar cmf manifest.txt goo.jar -C classes .

goo.zip: goo.jar
	rm -rf goo
	mkdir goo
	cp -rp goo.jar README.md Goo\ Tester.nlogo Makefile src manifest.txt goo
	zip -rv goo.zip goo
	rm -rf goo
