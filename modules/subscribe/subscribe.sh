#! /bin/sh
#  subscribing to 
#  author Vy Nguyen
#

arg1=$1 # sender (the person I am talking to)
arg2=$2 # channel



shift 2
echo "$@" >> ./modules/send/emails.txt

echo "PRIVMSG $arg2 :$arg1: Done adding you to the mailing-list"

