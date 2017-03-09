json="{\"name\": \"$1\"}"
echo "Sending: $json"
curl -H'Content-Type: application/json' -XPOST -d"$json" -vks localhost:9000/api/topics
echo
