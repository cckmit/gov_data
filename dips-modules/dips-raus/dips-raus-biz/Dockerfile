FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER swj956@gmail.com

RUN mkdir -p /dips-cloud/bin/com.cloud/dips-raus

WORKDIR /dips-cloud/bin/com.cloud/dips-raus

EXPOSE 9001

ADD ./dips-modules/dips-raus-biz/target/dips-raus-biz.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar dips-raus-biz.jar
