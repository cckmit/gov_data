FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER swj956@gmail.com

RUN mkdir -p /dips-cloud/bin/com.cloud/dips-policy-theme

WORKDIR /dips-cloud/bin/com.cloud/dips-policy-theme

EXPOSE 9002

ADD ./dips-modules/dips-policy-theme-biz/target/dips-policy-theme-biz.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar dips-policy-theme-biz.jar
