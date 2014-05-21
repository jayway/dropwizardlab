#Allows you to perform extra checks after maven has built. Return anything other than 0 to fail the build.
java -jar target/stackexchangeview-0.1.0-SNAPSHOT.jar 1>/dev/null

[ $? -eq 0 ] && exit 0
echo "target/stackexchangeview-0.1.0-SNAPSHOT.jar is not a self contained executable"
exit 1