FROM podbox/tomcat8
MAINTAINER gxf 393590643@qq.com
USER root
WORKDIR /apache-tomcat
RUN rm -rf webapps/*
ADD ROOT.war webapps/
CMD ["/bin/sh"]
#设置启动命令
ENTRYPOINT ["/apache-tomcat/bin/catalina.sh","run"]
