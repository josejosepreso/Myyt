MVN = mvn

run:
	$(MVN) exec:java -Dexec.args="$(filter-out $@,$(MAKECMDGOALS))"

test:
	$(MVN) clean test

package:
	$(MVN) clean package -DskipTests
