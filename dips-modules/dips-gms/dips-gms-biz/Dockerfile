FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER swj956@gmail.com

RUN mkdir -p /dips-cloud/bin/com.cloud/dips-gms

WORKDIR /dips-cloud/bin/com.cloud/dips-gms

EXPOSE 6001

ADD ./dips-modules/dips-gms-biz/target/dips-gms-biz.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar dips-gms-biz.jar
