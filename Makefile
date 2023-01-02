# Ensimag 2A POO - TP 2018/19
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     L'archive bin/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: testInvader testAffichage testScenario0 testScenario1 testEteintTout

testInvader:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestInvader.java

testAffichage:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestAffichage.java

testScenario0:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestScenario0.java

testScenario1:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestScenario1.java

testEteintTout:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestEteintTout.java

# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath bin:bin/gui.jar TestInvader
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeInvader
exeInvader: 
	java -classpath bin:bin/gui.jar TestInvader

exeAffichage:
	java -classpath bin:bin/gui.jar TestAffichage

exeScenario0:
	java -classpath bin:bin/gui.jar TestScenario0

exeScenario1:
	java -classpath bin:bin/gui.jar TestScenario1

exeEteintTout:
	java -classpath bin:bin/gui.jar TestEteintTout

clean:
	rm -rf bin/*.class bin/carte bin/enums bin/evenement bin/incendies bin/io bin/nouvellesException bin/robots bin/simulation
