#Allows you to perform extra checks after maven has built. Return anything other than 0 to fail the build.
[ -f target/stackexchangeview-0.1.0-SNAPSHOT.jar ] && exit 0
echo "Could not find required artifact target/stackexchangeview-0.1.0-SNAPSHOT.jar"
exit 1