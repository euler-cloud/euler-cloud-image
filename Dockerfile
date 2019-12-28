FROM openjdk:8-jdk

ARG app_port=8080
ARG app_path=/app
ARG app_conf_path=${app_path}/conf
ARG app_runtime_path=${app_path}/runtime
ARG app_log_path=${app_runtime_path}/log

RUN mkdir -p ${app_path} ${app_conf_path} ${app_runtime_path} ${app_log_path}

ENV EULER_APP_LOG_PATH=${app_log_path}
ENV EULER_JAVA_OPTS="-Dlogging.file.path=${app_log_path} \
-Dserver.port=${app_port} \
-Dspring.config.additional-location=file:${app_conf_path}/ \
-Deuler.application.runtime-path=${app_runtime_path}"

EXPOSE ${app_port}

WORKDIR ${app_path}

VOLUME ${app_runtime_path}

COPY init.sh ${app_path}/init.sh

RUN chmod 755 ${app_path}/init.sh

COPY target/*.jar ${app_path}/app.jar

ENTRYPOINT [ "./init.sh" ]
