#! /bin/bash

while true; do
    d=$(date +'%Y-%m-%d %H:%M:%S')
    c0=$(sensors | grep 'Core 0' | sed -E 's/[^ 0-9]//g;s/ +/\;/g' | cut -f3 -d";")
    c1=$(sensors | grep 'Core 1' | sed -E 's/[^ 0-9]//g;s/ +/\;/g' | cut -f3 -d";")
    curl -s -X POST 'http://localhost:8080/sensors/1/samples' -H 'Content-type:application/json' -d "{\"sensor\": {\"id\":1}, \"date_sampled\":\"${d}\" , \"sample\": ${c0}}"
    echo ""
    curl -s -X POST 'http://localhost:8080/sensors/2/samples' -H 'Content-type:application/json' -d "{\"sensor\": {\"id\":2}, \"date_sampled\":\"${d}\" , \"sample\": ${c1}}"
    echo ""
    sleep 2
done
