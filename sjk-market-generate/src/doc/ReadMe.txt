模块说明 ：

1：本模块完成的功能是生成market静态页面

2：主要是通过模板技术来实现展现和解析逻辑分离

3：前端制作好页面好后，取数据使用 velocity标记语言来取数据

4:页面所需要要的信息已经放入App对象中，如取app名称${App.name}

5:前端需要掌握一定的velocity标记语言

6:具体执行方法：
1：）将模板放入 app-config.properties 中指定的 模板存放 路径 （app-generate-template-base-dir指定的路径 ）

2:)	在工程的上级目录执行 mvn clean package -Dmaven.test.skip=true -pl sjk-market-generate

3:)到工程的target目录，找到含有executable字样的jar文件，在控制台，运行,这时就会有文件生成 

4:) app-config.properties 中属性 generate-date 是指生成 的天数设置 ，-1表示昨天，-2表示前天，如此类推

7：模拟文件具体写法可以参考/sjk-market-generate/src/main/resources/template/app.html 这个文件 

8:有任何问题可以咨询 huyouzhi

2012-09-11