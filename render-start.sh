#!/bin/sh
set -eu

if [ -n "${DATABASE_URL:-}" ] && [ -z "${SPRING_DATASOURCE_URL:-}" ]; then
  database_url_without_scheme="${DATABASE_URL#postgresql://}"
  database_url_without_scheme="${database_url_without_scheme#postgres://}"
  database_credentials="${database_url_without_scheme%@*}"
  database_host_path="${database_url_without_scheme#*@}"

  export SPRING_DATASOURCE_URL="jdbc:postgresql://${database_host_path}"

  if [ -z "${SPRING_DATASOURCE_USERNAME:-}" ]; then
    export SPRING_DATASOURCE_USERNAME="${database_credentials%%:*}"
  fi

  if [ -z "${SPRING_DATASOURCE_PASSWORD:-}" ]; then
    export SPRING_DATASOURCE_PASSWORD="${database_credentials#*:}"
  fi
fi

exec java -jar app.jar
