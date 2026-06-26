#!/usr/bin/env sh
set -e
if command -v mise >/dev/null 2>&1; then
  exec mise x java@25 -- gradle "$@"
fi
exec gradle "$@"
