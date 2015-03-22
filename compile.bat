chcp 65001
javac -encoding UTF8 -d bin/major src/major/*.java -d bin/minor src/minor/*.java -d bin src/*.java
cd bin
java LogBase