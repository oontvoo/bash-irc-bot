#! /bin/sh
#  subscribe channel
#  author Vy Nguyen
#

arg1=$1 # sender (the person I am talking to)
arg2=$2 # channel


#echo "arg1: $arg1, $@" >> output

shift 2
java -jar ./modules/send/send-bot.jar "$arg1" ./modules/send/emails.txt "$@" >> output.txt



