echo "Building app..."
./mvnw clean package

echo "Deploy files to server..."
scp -r   target/be.jar root@152.42.226.77:/var/www/be/

ssh  root@152.42.226.77 <<EOF
pid=\$(sudo lsof -t -i :8080)

if [ -z "\$pid" ]; then
    echo "Start server..."
else
    echo "Restart server..."
    sudo kill -9 "\$pid"
fi
cd /var/www/be
java -jar be.jar
EOF
exit
echo "Done!"