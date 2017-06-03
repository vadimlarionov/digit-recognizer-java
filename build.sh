mvn clean assembly:assembly
if [ $? -eq 0 ]; then
	cp target/digit-recognizer-1.0.0-jar-with-dependencies.jar digit-recognizer.jar
	echo "Success"
	echo "Use: java -jar digit-recognizer.jar"
else
	echo "Build error"
fi
