# simple项目权限版
带有权限系统，简单系统结构

以开发环境打包：clean package -Pdev
以测试环境打包：clean package -Ptest
以预生产环境打包：clean package -PproductPre 
以生产环境打包：clean package -Pproduct 


clean tomcat7:run -Dmaven.tomcat.port=9999