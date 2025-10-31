#!/usr/bin/env bash
#   Use this script to test if a given TCP host/port are available

set -e

HOST=$1
shift
PORT=$1
shift

TIMEOUT=30
QUIET=0
CMD="$@"

echoerr() { if [[ $QUIET -ne 1 ]]; then echo "$@" 1>&2; fi }

usage()
{
    cat << USAGE >&2
Usage:
    wait-for-it.sh host port [-t timeout] [-- command args]
    -t TIMEOUT    Timeout in seconds, default: 30
    -- COMMAND ARGS  Execute command after the test succeeds
USAGE
    exit 1
}

while [[ $# -gt 0 ]]
do
    case "$1" in
        -t)
            TIMEOUT="$2"
            shift 2
            ;;
        --)
            shift
            break
            ;;
        *)
            usage
            ;;
    esac
done

if [[ "$HOST" == "" || "$PORT" == "" ]]; then
    usage
fi

for i in $(seq $TIMEOUT) ; do
    nc -z "$HOST" "$PORT" >/dev/null 2>&1 && break
    echo "Waiting for $HOST:$PORT ($i/$TIMEOUT)..."
    sleep 1
done

if ! nc -z "$HOST" "$PORT" >/dev/null 2>&1; then
    echo "Error: Timeout after waiting $TIMEOUT seconds for $HOST:$PORT" >&2
    exit 1
fi

if [[ "$CMD" != "" ]]; then
    exec $CMD
fi
