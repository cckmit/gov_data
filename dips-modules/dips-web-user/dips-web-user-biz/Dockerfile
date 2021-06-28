FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER swj956@gmail.com

RUN mkdir -p /dips-cloud/bin/com.cloud/dips-web-user

WORKDIR /dips-cloud/bin/com.cloud/dips-web-user

EXPOSE 7003

ADD ./dips-modules/dips-gms-biz/target/dips-web-user-biz.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar dips-web-user-biz.jar
